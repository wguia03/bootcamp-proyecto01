package com.xyz.AccountMs.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransaccionRequest(
        @NotNull
        @Positive
        Double monto
) {
}
