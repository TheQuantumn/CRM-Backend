package com.example.cmr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/")
    public String home() {
        return "✅ JobTracker backend is running!";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
