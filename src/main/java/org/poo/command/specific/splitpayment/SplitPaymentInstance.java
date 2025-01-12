package org.poo.command.specific.splitpayment;

import lombok.Getter;
import org.poo.input.Input;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Split payment instance.
 */
@Getter
public class SplitPaymentInstance {
    private final List<SplitPaymentParticipant> participants = new ArrayList<>();
    private final List<SplitPaymentParticipant> acceptedRequests = new ArrayList<>();
    private final SplitPaymentCommand paymentCommand;

    /**
     * Instantiates a new Split payment instance.
     *
     * @param paymentCommand the payment command
     */
    public SplitPaymentInstance(SplitPaymentCommand paymentCommand) {
        this.paymentCommand = paymentCommand;
    }

    /**
     * Notify.
     *
     * @param sender   the sender
     * @param accepted the accepted
     */
    public void notify(SplitPaymentParticipant sender, boolean accepted) {
        if (!accepted) {
            // TODO: one user rejected the payment
            for (SplitPaymentParticipant participant : participants) {
                participant.invalidatePayment("One user rejected the payment.");
                participant.getAccount().getUser().removeSplitPayment(participant);
            }

            Input.getInstance().getSplitPaymentInstances().remove(this);
            for (SplitPaymentParticipant subscriber : participants) {
                subscriber.getAccount().getUser().removeSplitPayment(subscriber);
            }
            return;
        }

        acceptedRequests.add(sender);
        if (acceptedRequests.size() == participants.size()) {
            notifyEveryone();
        }
    }

    /**
     * Add.
     *
     * @param subscriber the subscriber
     */
    public void add(SplitPaymentParticipant subscriber) {
        participants.add(subscriber);
        subscriber.setMediator(this);
    }

    private void notifyEveryoneInsufficientFunds(String insufficientFundsIban) {
        for (SplitPaymentParticipant participant : participants) {
            //TODO: add a transaction log
            participant.invalidatePayment("Account " + insufficientFundsIban + " has insufficient funds for a split payment.");
            participant.getAccount().getUser().removeSplitPayment(participant);
        }
    }

    /**
     * Notify everyone.
     */
    public void notifyEveryone() {
        for (SplitPaymentParticipant participant : participants) {
            boolean enoughFunds = participant.checkForFunds();
            if (!enoughFunds) {
                notifyEveryoneInsufficientFunds(participant.getAccount().getIban());
                Input.getInstance().getSplitPaymentInstances().remove(this);
                return;
            }
        }

        for (SplitPaymentParticipant subscriber : participants) {
            subscriber.proceedPayment();
        }

        Input.getInstance().getSplitPaymentInstances().remove(this);
    }
}
