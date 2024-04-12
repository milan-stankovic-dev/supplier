package com.example.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,
            columnDefinition = "XML")
    @NotEmpty
    private String contents;

    @Column(nullable = false)
    private boolean serviced;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDate receivedAt;

    @NotNull
    @PastOrPresent
    private LocalDate servicedAt;
}
