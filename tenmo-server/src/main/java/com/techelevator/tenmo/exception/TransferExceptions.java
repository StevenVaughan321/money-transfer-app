package com.techelevator.tenmo.exception;

public class TransferExceptions extends RuntimeException{

    public static class TransferUnauthorizedException extends RuntimeException {
        public TransferUnauthorizedException(String message) {
            super(message);
        }
    }

    public static class TransferNotFoundException extends RuntimeException {
        public TransferNotFoundException(String message) {
            super(message);
        }
    }
}
