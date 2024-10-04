package com.xyz.AccountMs.exceptions;

public record ErrorMessage(
        String title,
        int status,
        String detail
) {
}
