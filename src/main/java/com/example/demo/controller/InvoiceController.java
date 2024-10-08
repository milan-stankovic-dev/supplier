package com.example.demo.controller;

import com.example.demo.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.example.dto.OrderRequest;
import org.example.dto.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@RestController
@RequestMapping("/invoice")
@Log
public record InvoiceController(InvoiceService service) {

    @PostMapping("/process")
    public ResponseEntity<OrderResponse> serviceInvoice(
            @RequestBody @Valid OrderRequest request) throws ParserConfigurationException, IOException, SAXException {

        log.info("RECEIVED INVOICE REQUEST: " + request);
        return ResponseEntity.ok(service.serviceInvoices(request));
    }


}
