package org.poo.command.specific.splitpayment;

import com.google.gson.JsonObject;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.user.User;

/**
 * The type Accept split payment.
 */
public final class AcceptSplitPayment extends BaseCommand {
    private String email;
    private String splitPaymentType;

    /**
     * Instantiates a new Accept split payment.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public AcceptSplitPayment(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        final User user = Input.getInstance().getUsers().getUserByEmail(email);

        if (user == null) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("description", "User not found");
            outputJson.addProperty("timestamp", getTimestamp());
            setOutput(outputJson.toString());

            return;
        }

        final SplitPaymentParticipant payment = user.getFirstSplitPayment(splitPaymentType);
        if (payment == null) {
            return;
        }

        payment.acceptPayment();
    }
}
