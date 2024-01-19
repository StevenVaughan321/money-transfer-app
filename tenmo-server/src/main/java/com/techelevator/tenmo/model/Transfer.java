package com.techelevator.tenmo.model;


import org.springframework.data.annotation.Id;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Transfer {
    @NotNull
    @Id
    private long id;
    @NotNull
    private int transferStatus;
    @NotNull
    private int transferType;
    @NotNull
    private int accountFrom;
    @NotNull
    private int accountTo;
    @NotNull
    private BigDecimal amount;

    public Transfer(long id,  int transferType, int transfer_status, int accountFrom, int accountTo, BigDecimal amount ) {
        this.id = id;
        this.transferStatus = transfer_status;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.transferType = transferType;
    }

    public Transfer(int transferType, int transfer_status, int accountFrom, int accountTo, BigDecimal amount) {
        this.transferType = transferType;
        this.transferStatus = transfer_status;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Transfer() {
    }

    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(int transferStatus) {
        this.transferStatus = transferStatus;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.HALF_DOWN);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return id == transfer.id &&
                Objects.equals(accountFrom, transfer.accountFrom);

//                activated == user.activated &&
//                Objects.equals(username, user.username) &&
//                Objects.equals(password, user.password) &&
//                Objects.equals(authorities, user.authorities);
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", transferStatus=" + transferStatus +
                ", transferType=" + transferType +
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", amount=" + amount +
                '}';
    }
}
