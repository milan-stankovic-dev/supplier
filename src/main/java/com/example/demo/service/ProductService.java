package com.example.demo.service;

import com.example.demo.converter.impl.ProductReplenishConverter;
import com.example.demo.domain.Product;
import com.example.demo.dto.ProductReplenishDTO;
import com.example.demo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public record ProductService(ProductRepository repository,
                             ProductReplenishConverter converter) {

    public ProductReplenishDTO replenishStock(
            @NotNull ProductReplenishDTO request) {

        if(request.amount() <= 0) {
            log.warning("Illegal state of stock passed to server.");
            throw new IllegalArgumentException("Your stock update must be positive.");
        }

        var productFromDb = repository.findById(request.id())
                .orElseThrow(() ->
                        new EntityNotFoundException("Product with ID %d does not exist."
                                .formatted(request.id())));

        productFromDb.increaseStockBy(request.amount());
        final Product result = repository.save(productFromDb);

        return converter.toDto(result);
    }
}