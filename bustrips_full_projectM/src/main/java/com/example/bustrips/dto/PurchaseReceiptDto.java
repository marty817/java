package com.example.bustrips.dto;

import java.math.BigDecimal;

public record PurchaseReceiptDto(Integer userId, Integer tripId, BigDecimal charged, BigDecimal remainingBalance) {}
