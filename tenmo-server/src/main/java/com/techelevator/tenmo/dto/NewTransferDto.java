package com.techelevator.tenmo.dto;

import java.math.BigDecimal;

public class NewTransferDto {
    // user Id depends on who is receiving learned from type
    private  int userTo;
    private int userFrom;

    private  BigDecimal amount;

    private  int transferType;

    public NewTransferDto(int userTo, int userFrom, BigDecimal amount, int transferType) {
        this.userTo = userTo;
        this.userFrom = userFrom;
        this.amount = amount;
        this.transferType = transferType;
    }

    public int getUserFrom() {
        return userFrom;
    }

    public int getUserTo() {
        return userTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getTransferType() {
        return transferType;
    }
}
