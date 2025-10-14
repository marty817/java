
package com.example.busapp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AuthDTOs {

    public static class RegisterRequest {
        public String email;
        public String password;
        public BigDecimal credit; // optional
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }

    public static class LoginResponse {
        public String token;
        public String tokenType = "Bearer";
        public LoginResponse(String token) { this.token = token; }
    }

    public static class Receipt {
        public Integer userId;
        public Integer tripId;
        public BigDecimal charged;
        public BigDecimal remainingBalance;
        public Receipt(Integer userId, Integer tripId, BigDecimal charged, BigDecimal remainingBalance){
            this.userId = userId; this.tripId = tripId; this.charged = charged; this.remainingBalance = remainingBalance;
        }
    }
}
