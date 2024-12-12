package org.poo.transactions.specific;

import org.poo.transactions.BaseTransaction;

public class TransferTransaction extends BaseTransaction {
    private final String senderIBAN;
    private final String receiverIBAN;
    private final String amount;
    private final String transferType;

    public TransferTransaction(final int timestamp, final String description, final String senderIBAN,
                               final String receiverIBAN, final String amount, final String transferType) {
        super(description, timestamp);
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount;
        this.transferType = transferType;
    }
}
