package com.example.math;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/math")
public class MathController {

    private final MathService mathService;

    public MathController(MathService mathService) {
        this.mathService = mathService;
    }

    @GetMapping("/add/{a}/{b}")
    public ResponseEntity<Double> add(@PathVariable double a, @PathVariable double b) {
        return ResponseEntity.ok(mathService.add(a, b));
    }

    @GetMapping("/sub/{a}/{b}")
    public ResponseEntity<Double> sub(@PathVariable double a, @PathVariable double b) {
        return ResponseEntity.ok(mathService.sub(a, b));
    }

    @GetMapping("/mul/{a}/{b}")
    public ResponseEntity<Double> mul(@PathVariable double a, @PathVariable double b) {
        return ResponseEntity.ok(mathService.mul(a, b));
    }

    @GetMapping("/div/{a}/{b}")
    public ResponseEntity<Double> div(@PathVariable double a, @PathVariable double b) {
        return ResponseEntity.ok(mathService.div(a, b));
    }
}