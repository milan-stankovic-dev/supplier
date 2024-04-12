package com.example.demo.validator;

import lombok.val;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;

@Component
public class XMLValidator {
    public void XMLisWellFormed(String xml) throws ParserConfigurationException, IOException, SAXException {
        final DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = factory
                .newDocumentBuilder();

        builder.parse(new InputSource(new StringReader(xml)));
    }

    public void XMLIsValid(String xml, String xmlSchema) throws SAXException, IOException {
        final SchemaFactory schemaFactory =
                SchemaFactory.newInstance(
                        XMLConstants.W3C_XML_SCHEMA_NS_URI);
        final Schema schema = schemaFactory.newSchema(
                new StreamSource(new StringReader(xmlSchema)));
        val validator = schema.newValidator();

        validator.validate(new StreamSource(
                new StringReader(xml)));
    }
}
