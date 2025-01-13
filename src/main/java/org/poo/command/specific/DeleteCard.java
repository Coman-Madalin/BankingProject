package org.poo.command.specific;

import org.poo.account.BaseAccount;
import org.poo.account.specific.BusinessAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.specific.CardActionTransaction;
import org.poo.user.Card;

import static org.poo.input.Input.printLog;

/**
 * The type Delete card.
 */
public final class DeleteCard extends BaseCommand {
    private String email;
    private String cardNumber;

    /**
     * Instantiates a new Delete card.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public DeleteCard(final String command, final int timestamp) {
        super(command, timestamp);
    }

    /**
     * Instantiates a new Delete card.
     *
     * @param command    the command
     * @param timestamp  the timestamp
     * @param email      the email
     * @param cardNumber the card number
     */
    public DeleteCard(final String command, final int timestamp, final String email,
                      final String cardNumber) {
        super(command, timestamp);
        this.email = email;
        this.cardNumber = cardNumber;
    }

    private void handleBusiness(BusinessAccount account, Card card) {
        String role = account.getEmployeeRole(email);
        switch (role) {
            case "owner", "manager" -> account.getCards().remove(card);

            case null -> System.out.println("Employee not found!");

            default ->
                    System.out.println("This employee doesn't have permisssion to delete the card!");
        }
    }

    @Override
    public void execute() {
        // TODO: managers can delete a card too
        final Card card = Input.getInstance().getUsers().getCardByCardNumber(cardNumber);

        if (card == null) {
            printLog("DeleteCard:CardNotFound", getTimestamp(), -1, 0, cardNumber);
            //TODO: Card not found
            return;
        }

        final BaseAccount account = card.getAccount();

        if (account.getType().equals("business")) {
            handleBusiness((BusinessAccount) account, card);
            return;
        }

        if (account.getBalance() != 0) {
            printLog("DeleteCard:FoundFunds", getTimestamp(), -1, account.getBalance(),
                    cardNumber);

            //TODO: account has funds, so we don't delete card, for some reason
            return;
        }

        if (!account.isValidEmail(email)) {
            System.out.println("Invalid email!");
            return;
        }

        account.getCards().remove(card);

        account.getTransactionsHistory().add(new CardActionTransaction(
                "The card has been destroyed",
                getTimestamp(),
                account.getIban(),
                card.getCardNumber(),
                account.getUser().getEmail()
        ));
    }

    public void forceExecute() {
        final Card card = Input.getInstance().getUsers().getCardByCardNumber(cardNumber);
        final BaseAccount account = card.getAccount();

        account.getCards().remove(card);

        account.getTransactionsHistory().add(new CardActionTransaction(
                "The card has been destroyed",
                getTimestamp(),
                account.getIban(),
                card.getCardNumber(),
                account.getUser().getEmail()
        ));
    }
}
