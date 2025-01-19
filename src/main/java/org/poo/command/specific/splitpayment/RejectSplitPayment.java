package org.poo.command.specific.splitpayment;

import com.google.gson.JsonObject;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.user.User;

/**
 * The type Reject split payment.
 */
public final class RejectSplitPayment extends BaseCommand {
    private String email;
    private String splitPaymentType;

    /**
     * Instantiates a new Reject split payment.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public RejectSplitPayment(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        final User user = Input.getInstance().getUsers().getUserByEmail(email);
        if (user == null) {
            // TODO: maybe make a builder for this, I use it too much
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

        payment.rejectPayment();
    }
}
