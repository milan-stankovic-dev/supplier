package com.example.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "You must input a product name")
    @NaturalId
    @Column(name = "product_name")
    private String productName;

    @Min(value = 0, message = "You may not have less than 0 products.")
    @Column(name = "current_stock")
    private int currentStock;

    @DecimalMin(value = "0", inclusive = false)
    @DecimalMax(value = "1000000")
    private BigDecimal price;

    public void increaseStockBy(int amount) {
        this.currentStock += amount;
    }
    public void decreaseStockBy(int amount) {
        this.currentStock -= amount;
    }
}