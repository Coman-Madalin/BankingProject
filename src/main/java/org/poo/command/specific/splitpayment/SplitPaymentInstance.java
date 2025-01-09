package org.poo.command.specific.splitpayment;

import lombok.Getter;
import org.poo.input.Input;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SplitPaymentInstance {
    private List<SplitPaymentParticipant> participants = new ArrayList<>();
    private List<SplitPaymentParticipant> acceptedRequests = new ArrayList<>();
    private SplitPaymentCommand paymentCommand;

    public SplitPaymentInstance(SplitPaymentCommand paymentCommand) {
        this.paymentCommand = paymentCommand;
    }

    public void notify(SplitPaymentParticipant sender, boolean accepted) {
        if (!accepted) {
            // TODO: one user rejected the payment
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

    public void add(SplitPaymentParticipant subscriber) {
        participants.add(subscriber);
        subscriber.setMediator(this);
    }

    private void notifyEveryoneInsufficientFunds(String insufficientFundsIban) {
        for (SplitPaymentParticipant subscriber : participants) {
            //TODO: add a transaction log
            subscriber.invalidatePayment("Account " + insufficientFundsIban + " has insufficient funds for a split payment.");
            subscriber.getAccount().getUser().removeSplitPayment(subscriber);
        }
    }

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
