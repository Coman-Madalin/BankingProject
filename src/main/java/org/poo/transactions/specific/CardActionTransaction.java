package org.poo.transactions.specific;

import org.poo.transactions.BaseTransaction;

/**
 * The type Card action transaction.
 */
public class CardActionTransaction extends BaseTransaction {
    private final String account;
    private final String card;
    private final String cardHolder;

    /**
     * Instantiates a new Card action transaction.
     *
     * @param description the description
     * @param timestamp   the timestamp
     * @param account     the account
     * @param card        the card
     * @param cardHolder  the card holder
     */
    public CardActionTransaction(final String description, final int timestamp,
                                 final String account, final String card, final String cardHolder) {
        super(description, timestamp);
        this.account = account;
        this.card = card;
        this.cardHolder = cardHolder;
    }
}
