package com.example.math;

import org.springframework.stereotype.Service;

@Service
public class MathService {

    public double add(double a, double b) {
        return a + b;
    }

    public double sub(double a, double b) {
        return a - b;
    }

    public double mul(double a, double b) {
        return a * b;
    }

    public double div(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Divisione per zero non consentita!");
        }
        return a / b;
    }
}