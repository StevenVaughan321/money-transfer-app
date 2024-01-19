package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {
    private int account_id;
    private int user_id;
    private BigDecimal balance;

    public Account(int account_id, int user_id, BigDecimal balance) {
        this.account_id = account_id;
        this.user_id = user_id;
        this.balance = balance;
    }

    public Account() {
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean addToBalance(BigDecimal amount){
        int integerValue = amount.intValue();

        if (integerValue <= 0){
            return false;
        }

        BigDecimal maximumBalance = new BigDecimal("9999999999999.99");
        BigDecimal sum = balance.add(amount);
        int balanceMaxedOut = 1; //compareTo() returns 1 for "greater than"

        if (sum.compareTo(maximumBalance) == balanceMaxedOut){
            return false;
        }

        balance = balance.add(amount);
        return true;
    }

    public boolean subtractFromBalance(BigDecimal transferAmount){
        int balanceInsufficient = -1; //compareTo() returns -1 for "less than"

        if (balance.compareTo(transferAmount) == balanceInsufficient){
            return false;
        }

        balance = balance.subtract(transferAmount);
        return true;
    }

    @Override
    public String toString() {
        return "Account{" +
                "account_id = " + account_id +
                ", user_id = " + user_id +
                ", balance = " + balance +
                '}';
    }
}
