package com.example.demo.controller;

import com.example.demo.dto.InvoiceDTO;
import com.example.demo.dto.InvoiceResponseDTO;
import com.example.demo.service.InvoiceService;
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
import java.util.List;

@RestController
@RequestMapping("/invoice")
@Log
public record InvoiceController(InvoiceService service) {

    @PostMapping("/process")
    public ResponseEntity<OrderResponse> serviceInvoice(
            @RequestBody OrderRequest request) throws ParserConfigurationException, IOException, SAXException {

        log.info("RECEIVED: " + request);
        return ResponseEntity.ok(service.serviceInvoices(request));
    }
}
