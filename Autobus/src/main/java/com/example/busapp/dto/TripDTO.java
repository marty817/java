
package com.example.busapp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TripDTO {
    public Integer id;
    public String origin;
    public String destination;
    public LocalDateTime departureTime;
    public BigDecimal price;
}
