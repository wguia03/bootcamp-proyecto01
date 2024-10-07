package com.xyz.TransactionMs.exceptions;

public record ErrorMessage(
        String title,
        int status,
        String detail
) {
}
