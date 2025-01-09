package org.poo.command.specific.splitpayment;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.user.User;

public class AcceptSplitPayment extends BaseCommand {
    private String email;
    private String splitPaymentType;

    /**
     * Instantiates a new Base command.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public AcceptSplitPayment(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        User user = Input.getInstance().getUsers().getUserByEmail(email);
        SplitPaymentParticipant payment = user.getFirstSplitPayment(splitPaymentType);
        if (payment == null) {
            System.out.println(user.getEmail() + " doesn't have any split payment of type " +
                    splitPaymentType);
            return;
        }

        payment.acceptPayment();
    }
}
