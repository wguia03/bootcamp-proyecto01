package com.xyz.TransactionMs.exceptions;

public class DuplicateAccountNumber extends RuntimeException {
    public DuplicateAccountNumber(String message) {
        super(message);
    }
}
