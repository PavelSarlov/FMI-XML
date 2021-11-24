//sample partial solution of Task 6

import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class Main {
    public static void main(String[] args) {
        XMLOutputFactory xmlif = XMLOutputFactory.newInstance();
        XMLStreamWriter xmlw = null;
        try {
            // Инициализираме си изходния поток към конзолата.
            xmlw = xmlif.createXMLStreamWriter(System.out);

            // Стартираме документа.
            xmlw.writeStartDocument();

            // Инициализираме корена на документа.
            System.out.println();
            xmlw.writeStartElement("bookstore");

            // Извеждаме трите книги.
            writeBook(xmlw, "COOKING", "en", "Everyday Italian", "Giada De Laurentiis", "2005", "30.00");
            writeBook(xmlw, "CHILDREN", "en", "Harry Potter", "J. K. Rowling", "2005", "29.99");
            writeBook(xmlw, "WEB", "en", "Learning XML", "Erik T. Ray", "2003", "39.95");

            // Затваряме корена на документа.
            System.out.println();
            xmlw.writeEndElement();

            // Затваряме потока.
            xmlw.close();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    // Помощна функция за извеждането на книгите, тъй като доста код щеше да се
    // повтаря.
    // Би трябвало да е ясно какво се случва в нея.
    private static void writeBook(XMLStreamWriter xmlw, String category, String lang, String title, String author,
            String year, String price) throws XMLStreamException {

        System.out.print("\n\t");
        xmlw.writeStartElement("book");
        xmlw.writeAttribute("category", category);

        System.out.print("\n\t\t");
        xmlw.writeStartElement("title");
        xmlw.writeAttribute("lang", lang);
        xmlw.writeCharacters(title);
        xmlw.writeEndElement();

        System.out.print("\n\t\t");
        xmlw.writeStartElement("author");
        xmlw.writeCharacters(author);
        xmlw.writeEndElement();

        System.out.print("\n\t\t");
        xmlw.writeStartElement("year");
        xmlw.writeCharacters(year);
        xmlw.writeEndElement();

        System.out.print("\n\t\t");
        xmlw.writeStartElement("price");
        xmlw.writeCharacters(price);
        xmlw.writeEndElement();

        System.out.print("\n\t");
        xmlw.writeEndElement();
    }
}

