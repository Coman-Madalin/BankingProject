package org.poo.command.specific.splitpayment;

import lombok.Getter;
import org.poo.input.Input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class SplitPaymentInstance {
    private List<SplitPaymentParticipant> subscribers = new ArrayList<>();
    private Map<SplitPaymentParticipant, Boolean> subscribers_responses = new HashMap<>();
    private SplitPaymentCommand paymentCommand;

    public SplitPaymentInstance(SplitPaymentCommand paymentCommand) {
        this.paymentCommand = paymentCommand;
    }

    public void notify(SplitPaymentParticipant sender, boolean accepted) {
        if (!accepted) {
            // TODO: one user rejected the payment
            Input.getInstance().getSplitPaymentInstances().remove(this);
            for (SplitPaymentParticipant subscriber : subscribers) {
                subscriber.getAccount().getUser().removeSplitPayment(subscriber);
            }
            return;
        }

        subscribers_responses.put(sender, accepted);

        if (subscribers_responses.size() == subscribers.size()) {
            notifyEveryone();
        }
    }

    public void add(SplitPaymentParticipant subscriber) {
        subscribers.add(subscriber);
        subscriber.setMediator(this);
    }

    private void notifyEveryoneInsufficientFunds(String insufficientFundsIban) {
        for (SplitPaymentParticipant subscriber : subscribers) {
            //TODO: add a transaction log
            subscriber.getAccount().getUser().removeSplitPayment(subscriber);
        }
    }

    public void notifyEveryone() {
        for (SplitPaymentParticipant subscriber : subscribers) {
            boolean enoughFunds = subscriber.checkForFunds();
            if (!enoughFunds) {
                notifyEveryoneInsufficientFunds(subscriber.getAccount().getIban());
                Input.getInstance().getSplitPaymentInstances().remove(this);
                break;
            }
        }

        for (SplitPaymentParticipant subscriber : subscribers) {
            subscriber.proceedPayment();
        }

        Input.getInstance().getSplitPaymentInstances().remove(this);
    }
}
