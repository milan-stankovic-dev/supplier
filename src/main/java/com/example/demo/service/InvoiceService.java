package com.example.demo.service;

import com.example.demo.dto.InvoiceDTO;
import com.example.demo.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record InvoiceService(InvoiceRepository repository) {

    public InvoiceDTO serviceInvoices(InvoiceDTO request) {
        //TODO: implement business logic
        return request;
    }
}

