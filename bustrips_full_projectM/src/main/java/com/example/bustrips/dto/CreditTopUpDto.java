package com.example.bustrips.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class CreditTopUpDto {
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    public BigDecimal amount;
}
