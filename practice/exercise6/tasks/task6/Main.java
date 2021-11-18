//sample partial solution of Task 6

import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class Main {
  public static void main(String[] args) {
    XMLInputFactory xmlif = XMLInputFactory.newInstance();
    XMLStreamReader xmlr = null;
    try {
      xmlr = xmlif.createXMLStreamReader(new FileReader("rss.xml"));
      while (xmlr.hasNext()) {
        printEvent(xmlr);
        xmlr.next();
      }
      xmlr.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  private static void printEvent(XMLStreamReader xmlr) {
    switch (xmlr.getEventType()) {
    case XMLStreamConstants.START_ELEMENT:
      System.out.print("<");
      printName(xmlr);
      printNamespaces(xmlr);
      printAttributes(xmlr);
      System.out.print(">");
      break;
    case XMLStreamConstants.END_ELEMENT:
      System.out.print("</");
      printName(xmlr);
      System.out.print(">");
      break;
    case XMLStreamConstants.SPACE:
    case XMLStreamConstants.CHARACTERS:
      int start = xmlr.getTextStart();
      int length = xmlr.getTextLength();
      System.out.print(new String(xmlr.getTextCharacters(),
        start,
        length).toUpperCase()); // Трябва да са с главни букви.
      break;
    case XMLStreamConstants.PROCESSING_INSTRUCTION:
      System.out.print("<?");
      if (xmlr.hasText())
        System.out.print(xmlr.getText());
      System.out.print("?>");
      break;
    case XMLStreamConstants.CDATA:
      System.out.print("<![CDATA[");
      start = xmlr.getTextStart();
      length = xmlr.getTextLength();
      System.out.print(new String(xmlr.getTextCharacters(),
        start,
        length));
      System.out.print("]]>");
      break;
    case XMLStreamConstants.COMMENT:
      System.out.print("<!--");
      if (xmlr.hasText())
        System.out.print(xmlr.getText());
      System.out.print("-->");
      break;
    case XMLStreamConstants.ENTITY_REFERENCE:
      System.out.print(xmlr.getLocalName() + "=");
      if (xmlr.hasText())
        System.out.print("[" + xmlr.getText() + "]");
      break;
    case XMLStreamConstants.START_DOCUMENT:
      System.out.print("<?xml");
      System.out.print(" version='" + xmlr.getVersion() + "'");
      System.out.print(" encoding='" + xmlr.getCharacterEncodingScheme() + "'");
      if (xmlr.isStandalone())
        System.out.print(" standalone='yes'");
      else
        System.out.print(" standalone='no'");
      System.out.print("?>\n");
      break;
    }
  }

  private static void printName(XMLStreamReader xmlr) {
    if (xmlr.hasName()) {
      String prefix = xmlr.getPrefix();
      String uri = xmlr.getNamespaceURI();
      String localName = xmlr.getLocalName();
      printName(prefix, uri, localName);
    }
  }

  private static void printName(String prefix,
    String uri,
    String localName) {
    if (uri != null && !("".equals(uri))) System.out.print("['" + uri + "']:");
    if (prefix != null && prefix.length() > 0) System.out.print(prefix + ":");
    if (localName != null) System.out.print(localName);
  }

  private static void printAttributes(XMLStreamReader xmlr) {
    for (int i = 0; i < xmlr.getAttributeCount(); i++) {
      // Отделяме атрибутите с интервал и ги извеждаме.
      System.out.print(" ");
      printAttribute(xmlr, i);
    }
  }

  private static void printAttribute(XMLStreamReader xmlr, int index) {
    // Използваме инструкциите и готовата функция за принтиране на имена.
    printName(xmlr.getAttributePrefix(index),
          xmlr.getAttributeNamespace(index),
          xmlr.getAttributeLocalName(index));
    // Извеждаме и стойността на атрибута.
    System.out.print("='" + xmlr.getAttributeValue(index) + "'");
  }

  private static void printNamespaces(XMLStreamReader xmlr) {
    for (int i = 0; i < xmlr.getNamespaceCount(); i++) {
      printNamespace(xmlr, i);
    }
  }

  private static void printNamespace(XMLStreamReader xmlr, int index) {
    String prefix = xmlr.getNamespacePrefix(index);
    String uri = xmlr.getNamespaceURI(index);
    System.out.print(" ");
    if (prefix == null)
      System.out.print("xmlns='" + uri + "'");
    else
      System.out.print("xmlns:" + prefix + "='" + uri + "'");
  }
}
