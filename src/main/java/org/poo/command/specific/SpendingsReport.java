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

public class SpendingsReport extends BaseCommand {
    private String account;
    private int startTimestamp;
    private int endTimestamp;

    public SpendingsReport(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        final Account userAccount = input.getUsers().getAccountByIBAN(account);

        final JsonObject outputObject = new JsonObject();
        outputObject.addProperty("IBAN", account);
        outputObject.addProperty("balance", userAccount.getBalance());
        outputObject.addProperty("currency", userAccount.getCurrency());
        final List<BaseTransaction> transactions = new ArrayList<>();
        final List<Commerciant> commerciants = new ArrayList<>();
        for (final BaseTransaction baseTransaction : userAccount.getTransactionsHistory()) {
            if (baseTransaction.getTimestamp() >= startTimestamp &&
                    baseTransaction.getTimestamp() <= endTimestamp) {
                if (baseTransaction.getDescription().equals("Card payment")) {
                    final PaymentTransaction paymentTransaction = (PaymentTransaction) baseTransaction;
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

        outputObject.add("transactions", JsonUtils.getGson().toJsonTree(transactions));
        outputObject.add("commerciants", JsonUtils.getGson().toJsonTree(commerciants));


        setOutput(outputObject.toString());
    }

    private static class Commerciant implements Comparable<Commerciant> {
        private final String commerciant;
        private double total;

        public Commerciant(final String commerciant, final double total) {
            this.commerciant = commerciant;
            this.total = total;
        }

        @Override
        public int compareTo(final Commerciant o) {
            return this.commerciant.compareTo(o.commerciant);
        }
    }
}
