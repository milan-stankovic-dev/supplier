package com.example.demo.dto;

import java.util.List;

public record InvoiceResponseDTO(
    List<String> XMLContents,
    boolean priceMatch,
    boolean successful
) { }
