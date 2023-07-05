package com.example.popular_module.controller;

import com.example.popular_module.service.PopularService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PopularController {
    private final PopularService popularService;

    public PopularController(PopularService popularService) {
        this.popularService = popularService;
    }

    @GetMapping("/api/v1/popular")
    public ResponseEntity<?> getListPopularSearch() {
        try {
            return popularService.getListPopularSearch();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
