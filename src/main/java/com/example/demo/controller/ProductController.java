package com.example.demo.controller;

import com.example.demo.dto.ProductReplenishDTO;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log
@RestController
@RequestMapping("/product")
public record ProductController(ProductService service) {
    @PatchMapping("/replenish")
    public ResponseEntity<ProductReplenishDTO> replenishStock(
            @RequestBody @Valid ProductReplenishDTO request) {

        log.info("RECEIVED REPLENISH REQUEST: " + request);
        return ResponseEntity.ok(service.replenishStock(request));
    }
}
