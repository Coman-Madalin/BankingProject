package org.poo.command.specific.splitpayment;

import lombok.Getter;
import org.poo.account.BaseAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Split payment command.
 */
@Getter
public final class SplitPaymentCommand extends BaseCommand {
    private double amount;
    private String currency;
    private List<String> accounts;
    private String splitPaymentType;
    private List<Double> amountForUsers;

    private SplitPaymentInstance splitPaymentInstance;

    /**
     * Instantiates a new Split payment command.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public SplitPaymentCommand(final String command, final int timestamp) {
        super(command, timestamp);
    }

    private void createEqualSplitPayment() {
        splitPaymentInstance = new SplitPaymentInstance(this);
        final double amountPerPerson = amount / accounts.size();

        amountForUsers = new ArrayList<>();
        amountForUsers.add(amountPerPerson);

        for (final String accountIban : accounts) {
            final BaseAccount account = Input.getInstance().getUsers()
                    .getAccountByIBAN(accountIban);

            if (account == null) {
                return;
            }

            final SplitPaymentParticipant splitPaymentParticipant = new SplitPaymentParticipant(
                    account,
                    amountPerPerson,
                    currency);
            splitPaymentInstance.add(splitPaymentParticipant);
            account.getUser().getSplitPaymentParticipantList().add(splitPaymentParticipant);
        }
    }

    private void createCustomSplitPayment() {
        splitPaymentInstance = new SplitPaymentInstance(this);

        for (int i = 0; i < accounts.size(); i++) {
            final BaseAccount account = Input.getInstance().getUsers()
                    .getAccountByIBAN(accounts.get(i));

            if (account == null) {
                return;
            }

            final SplitPaymentParticipant splitPaymentParticipant = new SplitPaymentParticipant(
                    account,
                    amountForUsers.get(i),
                    currency);
            splitPaymentInstance.add(splitPaymentParticipant);
            account.getUser().getSplitPaymentParticipantList().add(splitPaymentParticipant);
        }
    }

    @Override
    public void execute() {
        switch (splitPaymentType) {
            case "equal" -> createEqualSplitPayment();
            case "custom" -> createCustomSplitPayment();
            default -> {
            }
        }
    }
}
