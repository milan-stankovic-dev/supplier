package com.example.demo.controller;

import com.example.demo.dto.ProductReplenishDTO;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequestMapping("/product")
public record ProductController(ProductService service) {
    @PostMapping("/replenish")
    public ResponseEntity<ProductReplenishDTO> replenishStock(
            @RequestBody @Valid ProductReplenishDTO request) {

        log.info("RECEIVED REPLENISH REQUEST: " + request);
        return ResponseEntity.ok(service.replenishStock(request));
    }
}
