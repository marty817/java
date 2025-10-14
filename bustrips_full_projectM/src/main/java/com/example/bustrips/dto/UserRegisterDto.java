package com.example.bustrips.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class UserRegisterDto {
    @Email @NotNull public String email;
    @NotNull public String password;
    @DecimalMin(value = "0.0", inclusive = true)
    public BigDecimal credit = BigDecimal.ZERO;
}
