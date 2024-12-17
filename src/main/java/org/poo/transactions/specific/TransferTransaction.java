package org.poo.transactions.specific;

import org.poo.transactions.BaseTransaction;

/**
 * The type Transfer transaction.
 */
public class TransferTransaction extends BaseTransaction {
    private final String senderIBAN;
    private final String receiverIBAN;
    private final String amount;
    private final String transferType;

    /**
     * Instantiates a new Transfer transaction.
     *
     * @param description  the description
     * @param timestamp    the timestamp
     * @param senderIBAN   the sender iban
     * @param receiverIBAN the receiver iban
     * @param amount       the amount
     * @param transferType the transfer type
     */
    public TransferTransaction(final String description, final int timestamp,
                               final String senderIBAN, final String receiverIBAN,
                               final String amount, final String transferType) {
        super(description, timestamp);
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount;
        this.transferType = transferType;
    }
}
