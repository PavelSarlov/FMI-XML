<?xml version="1.0" encoding="UTF-8"?>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema" elementFormDefault="qualified">
   <xs:element name="book">
      <xs:complexType>
         <xs:sequence>
            <xs:element ref="chapters"/>
         </xs:sequence>
      </xs:complexType>
   </xs:element>
   <xs:element name="chapters">
      <xs:complexType>
         <xs:sequence>
            <xs:element maxOccurs="unbounded" ref="chapter"/>
         </xs:sequence>
      </xs:complexType>
   </xs:element>
   <xs:element name="chapter">
      <xs:complexType>
         <xs:sequence>
            <xs:element ref="content"/>
         </xs:sequence>
      </xs:complexType>
   </xs:element>
   <xs:element name="content" type="xs:NCName"/>
</xs:schema>
