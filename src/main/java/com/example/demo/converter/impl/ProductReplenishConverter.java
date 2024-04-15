package com.example.demo.converter.impl;

import com.example.demo.converter.DTOEntityConverter;
import com.example.demo.domain.Product;
import com.example.demo.dto.ProductReplenishDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductReplenishConverter
        implements DTOEntityConverter<ProductReplenishDTO, Product> {
    @Override
    public ProductReplenishDTO toDto(Product e) {
        return e == null ? null :
                new ProductReplenishDTO(e.getId(), e.getCurrentStock());
    }

    @Override
    public Product toEntity(ProductReplenishDTO d) {
        return d == null ? null :
                Product.builder()
                        .id(d.id())
                        .build();
    }
}
