import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class Main { //replace SAX1 by Main for pasting it as Main.java in https://repl.it/
   public static void main(String[] args) {
      OutputStreamWriter outputStreamWriter = new OutputStreamWriter(System.out);
      try {
         XMLReader parser = XMLReaderFactory.createXMLReader();
         InputSource source = new InputSource("rss.xml");
         //InputSource source = new InputSource(new StringReader("")); //for using in https://ideone.com/ instead new InputSource("rss.xml")
         parser.setContentHandler(new SAXHandler(outputStreamWriter));
         parser.parse(source);
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            outputStreamWriter.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
}


class SAXHandler implements ContentHandler {
   Locator locator;
   Integer indent;
   OutputStreamWriter outputStreamWriter;
   private final Integer TAB_SIZE = 4;

   public SAXHandler(OutputStreamWriter outputStreamWriter) {
      this.outputStreamWriter = outputStreamWriter;
      indent = 0;
   }

   @Override
   public void setDocumentLocator(Locator locator) {
      this.locator = locator;
   }

   @Override
   public void startDocument() throws SAXException {
      printIndented("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", false, false);
   }

   @Override
   public void endDocument() throws SAXException {
      // ...
   }

   @Override
   public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
      printIndented(String.format("<%s", qName), true, false);
      printAttributes(atts);
      printIndented(">", false, true);
      ++indent;
   }

   @Override
   public void endElement(String uri, String localName, String qName) throws SAXException {
      --indent;
      printIndented(String.format("</%s>", qName), true, false);
   }

   @Override
   public void characters(char[] chars, int start, int length) throws SAXException {
      // Слагаме си масива в string от подаденото начало с подадената дължина и го
      // правим на главни букви като махаме и нежелани whitespaces с trim(),
      // след което го принтираме, ако има дължина над 0. На втория аргумент
      // слагаме true, за да се вмъкне нов ред.
      String str = new String(chars, start, length).toUpperCase().trim();
      if(str.length()>0) printIndented(str, true, false);
   }


   @Override
   public void startPrefixMapping(String prefix, String uri) throws SAXException {
      // ...
   }

   @Override
   public void endPrefixMapping(String prefix) throws SAXException {
      // ...
   }

   @Override
   public void ignorableWhitespace(char[] chars, int start, int length) throws SAXException {
      // ...
   }

   @Override
   public void processingInstruction(String target, String data) throws SAXException {
      // ...
   }

   @Override
   public void skippedEntity(String name) throws SAXException {
      // ...
   }

   private void printIndented(String what, boolean isEndOfElement, boolean isElement) {
      try {
         if(isEndOfElement) {
            outputStreamWriter.write("\r\n");
         }
         if (indent > 0 && !isElement) {
            outputStreamWriter.write(String.format("%1$" + (indent * TAB_SIZE) + "s", ""));
         }
         outputStreamWriter.write(what);
         outputStreamWriter.flush();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void printAttributes(Attributes atts) {
      if (atts.getLength() > 0) {
          //++indent;

          // Беше направено атрибутите също да минават на нов ред, но на мен не ми
          // харесва така, та ще си сложим indent на 0 и пред всеки атрибут ще добавям
          // един ' ', за да ги принтира на един ред, след което си връщаме indent
          int temp = indent;
          indent = 0;
          for(int i=0; i<atts.getLength(); i++) {
            printIndented(" " + atts.getQName(i) + "=\"" + atts.getValue(i) + "\"", false, false);
          }
          indent = temp;

          //--indent;
      }
   }
}
