package com.techelevator.tenmo.dto;

import java.math.BigDecimal;

public class TransferDto {

        private int userFrom;
        private int userTo;
        private BigDecimal amount;
        private int transferType;

        public TransferDto(int fromUserId, int toUserId, BigDecimal amount, int transferType) {
            this.userFrom = fromUserId;
            this.userTo = toUserId;
            this.amount = amount;
            this.transferType = transferType;
        }

        public long getUserFrom() {
            return userFrom;
        }

        public void setUserFrom(int userFrom) {
            this.userFrom = userFrom;
        }

        public long getUserTo() {
            return userTo;
        }

        public void setUserTo(int userTo) {
            this.userTo = userTo;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public int getTransferType() {
            return transferType;
        }


}
