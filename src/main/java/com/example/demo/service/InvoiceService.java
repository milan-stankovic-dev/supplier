package com.example.demo.service;

import com.example.demo.converter.impl.ProductReplenishConverter;
import com.example.demo.domain.Invoice;
import com.example.demo.domain.Product;
import com.example.demo.dto.ProductReplenishDTO;
import com.example.demo.exception.NonExistingProductException;
import com.example.demo.exception.UnderstockedProductException;
import com.example.demo.repository.InvoiceRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.constants.XMLConstants;
import org.example.dto.OrderRequest;
import org.example.dto.OrderResponse;
import org.example.util.XMLParser;
import org.example.validator.XMLValidator;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.example.constants.XMLConstants.INVOICE_SCHEMA;

@Service
@Slf4j
public record InvoiceService(InvoiceRepository invoiceRepository,
                             ProductRepository productRepository,
                             ProductReplenishConverter converter,
                             XMLValidator validator,
                             XMLParser parser) {

    public OrderResponse serviceInvoices(OrderRequest request)
            throws ParserConfigurationException, IOException, SAXException {

            val invoice = request.XMLContents();

            validator.validateXMLFully(invoice, INVOICE_SCHEMA);

            val savedInvoice = insertInvoice(invoice);
            final Map<UUID, Integer> items = parser
                    .parseAllItems(invoice);
            logAllItems(items);

            try {
                this.decreaseProductStockForAll(items);
            } catch (NonExistingProductException | UnderstockedProductException e) {
                log.error("ERROR OCCURRED DURING PROCESSING REQUEST {}", e.getMessage());
                return new OrderResponse(request.id(), request.XMLContents(), false,
                        false);
            }

        invoiceRepository.updateServicedAtById(savedInvoice.getId(), LocalDate.now());
        return new OrderResponse(request.id(), request.XMLContents(),
                true, true);
    }

    private Invoice insertInvoice(@NotNull @NotEmpty String contents) {
        val invoiceToSave =
                new Invoice(null, contents,
                        LocalDate.now(),
                        null);

        return invoiceRepository.save(invoiceToSave);
    }

    private void logAllItems(Map<UUID, Integer> items) {
        items.forEach((key, value) -> log.info("Key: " + key + ", Value: " + value));
    }

    private void decreaseProductStockForAll(
            @NotNull Map<UUID, Integer> backlog)
            throws NonExistingProductException, UnderstockedProductException {

        for(val entry : backlog.entrySet()) {
            var wantedProduct = productRepository
                    .findByCode(entry.getKey())
                    .orElseThrow(()-> new NonExistingProductException(
                            "Product with code " + entry.getKey() +
                                    " does not exist."));

            final int wantedAmount = entry.getValue();
            checkForStock(wantedProduct, wantedAmount);
            wantedProduct.decreaseStockBy(wantedAmount);
            productRepository.save(wantedProduct);
        }
    }

    private void checkForStock(Product p, int orderAmount)
            throws UnderstockedProductException {

        if (p.getCurrentStock() - orderAmount < 0) {
            log.warn("Stock for product {} fully depleted" ,p.getProductName());

            throw new UnderstockedProductException(
                    "We currently do not have enough products. Try again later");
        }
    }
}

