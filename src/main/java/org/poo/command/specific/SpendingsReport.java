package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.json.JsonUtils;
import org.poo.transactions.BaseTransaction;
import org.poo.transactions.specific.PaymentTransaction;
import org.poo.user.Account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Spendings report.
 */
public final class SpendingsReport extends BaseCommand {
    private String account;
    private int startTimestamp;
    private int endTimestamp;

    /**
     * Instantiates a new Spendings report.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public SpendingsReport(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        final Account userAccount = input.getUsers().getAccountByIBAN(account);

        final JsonObject outputJson = new JsonObject();

        if (userAccount == null) {
            outputJson.addProperty("timestamp", getTimestamp());
            outputJson.addProperty("description", "Account not found");
            setOutput(outputJson.toString());
            return;
        }
        if (userAccount.getType().equals("savings")) {
            outputJson.addProperty("error",
                    "This kind of report is not supported for a saving account");
            setOutput(outputJson.toString());
            return;
        }

        outputJson.addProperty("IBAN", account);
        outputJson.addProperty("balance", userAccount.getBalance());
        outputJson.addProperty("currency", userAccount.getCurrency());
        final List<BaseTransaction> transactions = new ArrayList<>();
        final List<Commerciant> commerciants = new ArrayList<>();
        for (final BaseTransaction baseTransaction : userAccount.getTransactionsHistory()) {
            if (baseTransaction.getTimestamp() >= startTimestamp
                    && baseTransaction.getTimestamp() <= endTimestamp) {
                if (baseTransaction.getDescription().equals("Card payment")) {
                    final PaymentTransaction paymentTransaction =
                            (PaymentTransaction) baseTransaction;
                    transactions.add(paymentTransaction);

                    boolean commerciantAlreadyExists = false;

                    for (final Commerciant commerciant : commerciants) {
                        if (commerciant.commerciant.equals(paymentTransaction.getCommerciant())) {
                            commerciant.total += paymentTransaction.getAmount();
                            commerciantAlreadyExists = true;
                            break;
                        }
                    }

                    if (!commerciantAlreadyExists) {
                        commerciants.add(new Commerciant(
                                paymentTransaction.getCommerciant(),
                                paymentTransaction.getAmount()));
                    }
                }
            }
        }

        Collections.sort(commerciants);

        outputJson.add("transactions", JsonUtils.getGSON().toJsonTree(transactions));
        outputJson.add("commerciants", JsonUtils.getGSON().toJsonTree(commerciants));


        setOutput(outputJson.toString());
    }

    private static class Commerciant implements Comparable<Commerciant> {
        private final String commerciant;
        private double total;

        /**
         * Instantiates a new Commerciant.
         *
         * @param commerciant the commerciant
         * @param total       the total
         */
        Commerciant(final String commerciant, final double total) {
            this.commerciant = commerciant;
            this.total = total;
        }

        @Override
        public int compareTo(final Commerciant o) {
            return this.commerciant.compareTo(o.commerciant);
        }
    }
}
