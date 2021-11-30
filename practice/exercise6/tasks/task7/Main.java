//sample partial solution of Task 6

import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            XMLOutputFactory xmlif = XMLOutputFactory.newInstance();

            // Инициализираме си изходния поток към конзолата.
            XMLStreamWriter xmlw = xmlif.createXMLStreamWriter(System.out);

            // Стартираме документа.
            xmlw.writeStartDocument();

            // Инициализираме корена на документа.
            xmlw.writeStartElement("bookstore");

            // Извеждаме трите книги. Няма да използвам индентация и нови редове, защото
            // ще става малко разместване заради начина, по който streamwriter-а ги принтира
            writeBook(xmlw, "COOKING", "en", "Everyday Italian", "Giada De Laurentiis", "2005", "30.00");
            writeBook(xmlw, "CHILDREN", "en", "Harry Potter", "J. K. Rowling", "2005", "29.99");
            writeBook(xmlw, "WEB", "en", "Learning XML", "Erik T. Ray", "2003", "39.95");

            // Затваряме корена на документа.
            xmlw.writeEndElement();

            // Затваряме потока.
            xmlw.close();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    // Помощна функция за извеждането на книгите, тъй като доста код щеше да се повтаря.
    // Би трябвало да е ясно какво се случва в нея.
    private static void writeBook(XMLStreamWriter xmlw, String category, String lang, String title, String author, String year, String price) throws XMLStreamException {

        // Отваряме си елемента book и добавяме атрибута му.
        xmlw.writeStartElement("book");
        xmlw.writeAttribute("category", category);

        // използваме помощната функция за поделементите му.
        writeBookElement(xmlw, "title", title, new HashMap<String,String>(){{put("lang", lang);}});
        writeBookElement(xmlw, "author", author, null);
        writeBookElement(xmlw, "year", year, null);
        writeBookElement(xmlw, "price", price, null);

        // Затваряме си елемента.
        xmlw.writeEndElement();
    }

    // Функция, която инициализира по-простите елементи на book.
    private static void writeBookElement(XMLStreamWriter xmlw, String name, String content, HashMap<String, String> attributes) throws XMLStreamException {
        xmlw.writeStartElement(name);
        if (attributes != null) {
            for (String attr : attributes.keySet()) {
                xmlw.writeAttribute(attr, attributes.get(attr));
            }
        }
        xmlw.writeCharacters(content);
        xmlw.writeEndElement();
    }
}
