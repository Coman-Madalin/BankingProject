package org.poo.command.specific.splitpayment;

import lombok.Getter;
import org.poo.input.Input;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Split payment instance.
 */
@Getter
public final class SplitPaymentInstance {
    private final List<SplitPaymentParticipant> participants = new ArrayList<>();
    private final List<SplitPaymentParticipant> acceptedRequests = new ArrayList<>();
    private final SplitPaymentCommand paymentCommand;

    /**
     * Instantiates a new Split payment instance.
     *
     * @param paymentCommand the payment command
     */
    public SplitPaymentInstance(final SplitPaymentCommand paymentCommand) {
        this.paymentCommand = paymentCommand;
    }

    /**
     * Notify.
     *
     * @param sender   the sender
     * @param accepted the accepted
     */
    public void notify(final SplitPaymentParticipant sender, final boolean accepted) {
        if (!accepted) {
            for (final SplitPaymentParticipant participant : participants) {
                participant.invalidatePayment("One user rejected the payment.");
                participant.getAccount().getUser().removeSplitPayment(participant);
            }

            Input.getInstance().getSplitPaymentInstances().remove(this);
            for (final SplitPaymentParticipant subscriber : participants) {
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
    public void add(final SplitPaymentParticipant subscriber) {
        participants.add(subscriber);
        subscriber.setMediator(this);
    }

    private void notifyEveryoneInsufficientFunds(final String insufficientFundsIban) {
        for (final SplitPaymentParticipant participant : participants) {
            participant.invalidatePayment("Account " + insufficientFundsIban
                    + " has insufficient funds for a split payment.");
            participant.getAccount().getUser().removeSplitPayment(participant);
        }
    }

    /**
     * Notify everyone.
     */
    public void notifyEveryone() {
        for (final SplitPaymentParticipant participant : participants) {
            final boolean enoughFunds = participant.checkForFunds();

            if (!enoughFunds) {
                notifyEveryoneInsufficientFunds(participant.getAccount().getIban());
                Input.getInstance().getSplitPaymentInstances().remove(this);
                return;
            }
        }

        for (final SplitPaymentParticipant subscriber : participants) {
            subscriber.proceedPayment();
        }

        Input.getInstance().getSplitPaymentInstances().remove(this);
    }
}
