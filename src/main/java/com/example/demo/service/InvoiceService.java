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

@Service
@Log
public record InvoiceService(InvoiceRepository invoiceRepository,
                             ProductRepository productRepository,
                             ProductReplenishConverter converter) {
    public OrderResponse serviceInvoices(OrderRequest request)
            throws ParserConfigurationException, IOException, SAXException {

            val invoice = request.XMLContents();
            val validator = XMLValidator.getInstance();

            validator.validateXMLFully(invoice, XMLConstants.invoiceSchema);

            val savedInvoice = insertInvoice(invoice);
            val parser = XMLParser.getInstance();
            final Map<String, Integer> items = parser
                    .parseAllNamed(invoice, "item");
            for (Map.Entry<String, Integer> entry : items.entrySet()) {
                final String key = entry.getKey();
                final Integer value = entry.getValue();
                log.info("Key: " + key + ", Value: " + value);
            }
            try {
                this.decreaseProductStockForAll(items);
            } catch (NonExistingProductException | UnderstockedProductException e) {
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

    private void decreaseProductStockForAll(
            @NotNull Map<String, Integer> backlog)
            throws NonExistingProductException, UnderstockedProductException {

        for(final Map.Entry<String, Integer> entry: backlog.entrySet()) {
            var wantedProduct = productRepository
                    .findByProductName(entry.getKey())
                    .orElseThrow(()-> new NonExistingProductException(
                            "Product with name " + entry.getKey() +
                                    " does not exist."));
            val wantedAmount = entry.getValue();
            checkForStock(wantedProduct, wantedAmount);
            wantedProduct.decreaseStockBy(wantedAmount);
            productRepository.save(wantedProduct);
        }
    }

    private void checkForStock(Product p, int orderAmount)
            throws UnderstockedProductException {

        if(p.getCurrentStock() - orderAmount < 0)
            throw new UnderstockedProductException(
                    "We currently do not have enough products. Try again later");
    }

}

