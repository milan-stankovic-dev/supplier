package com.example.demo.constants;

public interface XMLConstants {
    final String invoiceSchema = """
            <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema">
                              <xs:element name="invoice">
                                  <xs:complexType>
                                      <xs:sequence>
                                          <xs:element name="issue_date" type="xs:date"/>
                                          <xs:element name="due_date" type="xs:dateTime"/>
                                          <xs:element name="ordering_from" type="xs:string"/>
                                          <xs:element name="total_cost" type="xs:double"/>
                                          <xs:element name="Invoice_items">
                                              <xs:complexType>
                                                  <xs:sequence>
                                                      <xs:element name="item" maxOccurs="unbounded">
                                                          <xs:complexType>
                                                              <xs:sequence>
                                                                  <xs:element name="order_amount" type="xs:int"/>
                                                                  <xs:element name="product_name" type="xs:string"/>
                                                              </xs:sequence>
                                                          </xs:complexType>
                                                      </xs:element>
                                                  </xs:sequence>
                                              </xs:complexType>
                                          </xs:element>
                                      </xs:sequence>
                                  </xs:complexType>
                              </xs:element>
                          </xs:schema>
            """;
}
