package org.poo.transactions.specific;

import org.poo.transactions.BaseTransaction;

public class CardActionTransaction extends BaseTransaction {
    private final String account;
    private final String card;
    private final String cardHolder;

    public CardActionTransaction(final String description, final int timestamp,
                                 final String account, final String card, final String cardHolder) {
        super(description, timestamp);
        this.account = account;
        this.card = card;
        this.cardHolder = cardHolder;
    }
}
