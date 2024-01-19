package com.techelevator.tenmo.dto;

import javax.validation.constraints.NotEmpty;

public class TransferStatusUpdateDto {
    @NotEmpty
    private int transferStatus;

    public TransferStatusUpdateDto(int transferStatus) {
        this.transferStatus = transferStatus;
    }

    public int getTransferStatus() {
        return transferStatus;
    }
}
