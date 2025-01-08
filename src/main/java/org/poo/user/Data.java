package org.poo.user;

import lombok.Getter;

@Getter
public class Data {
    private int nrTransactions = 0;
    private double totalSpend = 0;
    private String type = null;

    public Data(){
    }

    public Data(int nrTransactions, double totalSpend) {
        this.nrTransactions = nrTransactions;
        this.totalSpend = totalSpend;
    }

    public Data(String type, int nrTransactions, double totalSpend) {
        this.nrTransactions = nrTransactions;
        this.totalSpend = totalSpend;
        this.type = type;
    }

    public void addTransaction(double amount) {
        this.nrTransactions++;
        this.totalSpend += amount;
    }

    public void increaseTransactions(){
        this.nrTransactions++;
    }

    public void increaseAmount(double amount){
        this.totalSpend += amount;
    }
}
