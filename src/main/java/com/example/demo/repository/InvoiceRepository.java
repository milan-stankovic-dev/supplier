package com.example.demo.repository;

import com.example.demo.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Invoice i SET i.servicedAt = :date WHERE i.id = :id")
    void updateServicedAtById(Long id, LocalDate date);
}