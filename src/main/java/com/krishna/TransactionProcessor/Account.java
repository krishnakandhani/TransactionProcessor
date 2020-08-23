package com.krishna.TransactionProcessor;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

    private @Id String accountId;
    private double balance;
    private boolean frozen;

    Account() {
    }

    Account(String accountId, double balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setAccountId(String name) {
        this.accountId = name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
