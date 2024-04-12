package com.example.demo.service;

import com.example.demo.constants.XMLConstants;
import com.example.demo.dto.InvoiceDTO;
import com.example.demo.repository.InvoiceRepository;
import com.example.demo.validator.XMLValidator;
import lombok.val;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@Service
public record InvoiceService(InvoiceRepository repository,
                             XMLValidator validator) {
    public InvoiceDTO serviceInvoices(InvoiceDTO request) throws ParserConfigurationException, IOException, SAXException {
        //TODO: implement business logic
        for(val invoice : request.XMLContent()) {
            validator.XMLisWellFormed(invoice);
            validator.XMLIsValid(invoice, XMLConstants.invoiceSchema);
        }


        return request;
    }

}

