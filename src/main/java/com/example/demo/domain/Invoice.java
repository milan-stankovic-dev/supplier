package com.example.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLInsert;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_invoice")
@SQLInsert(sql = "INSERT INTO tbl_invoice (contents, received_at, serviced_at) " +
        "VALUES (XMLPARSE(DOCUMENT ?), ?, ?)")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
            columnDefinition = "XML")
    @NotEmpty
    private String contents;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDate receivedAt;

    @NotNull
    @PastOrPresent
    private LocalDate servicedAt;
}
