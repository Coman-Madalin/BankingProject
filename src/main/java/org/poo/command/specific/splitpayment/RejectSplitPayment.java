package org.poo.command.specific.splitpayment;

import com.google.gson.JsonObject;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.user.User;

import static org.poo.input.Input.printLog;

public class RejectSplitPayment extends BaseCommand {
    private String email;
    private String splitPaymentType;

    /**
     * Instantiates a new Base command.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public RejectSplitPayment(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        User user = Input.getInstance().getUsers().getUserByEmail(email);
        if (user == null) {
            // TODO: maybe make a builder for this, I use it too much
            JsonObject outputJson = new JsonObject();
            outputJson.addProperty("description", "User not found");
            outputJson.addProperty("timestamp", getTimestamp());
            setOutput(outputJson.toString());

            printLog("RejectSplitPayment:UserNotFound", getTimestamp(), 0, 0, email);
            return;
        }

        SplitPaymentParticipant payment = user.getFirstSplitPayment(splitPaymentType);
        if (payment == null) {
            System.out.println(user.getEmail() + " doesn't have any split payment of type " +
                    splitPaymentType);
            return;
        }

        payment.rejectPayment();
    }
}
