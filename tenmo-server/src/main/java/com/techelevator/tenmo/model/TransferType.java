package com.techelevator.tenmo.model;

public class TransferType {

    public static final int REQUEST_MONEY = 1;
    public static final int SEND_MONEY = 2;

    private int transfer_type_id;

    public TransferType(int transfer_type_id) {
        this.transfer_type_id = transfer_type_id;
    }

    public int getTransfer_type_id() {
        return transfer_type_id;
    }

    public void setTransfer_type_id(int transfer_type_id) {
        this.transfer_type_id = transfer_type_id;
    }
}
