import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.*;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class Main { // replace SAX1 by Main for pasting it as Main.java in https://repl.it/
    public static void main(String[] args) {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(System.out);
        try {
            XMLReader parser = XMLReaderFactory.createXMLReader();
            InputSource source = new InputSource("rss.xml");
            // InputSource source = new InputSource(new StringReader("")); //for using in
            // https://ideone.com/ instead new InputSource("rss.xml")
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

    // Показва дали сме в йерархията на item
    private boolean inItem = false;

    // Държи името на елементът родител.
    private String parentNode = "";

    // Таблица със стойностите на поделементите на item.
    private Map<String, String> itemElements = new HashMap<String, String>() {
        {
            put("title", "");
            put("link", "");
            put("description", "");
        }
    };

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
        // Стартираме си документа, както подобава на един html документ,
        // като предварително си отваряме и таблицата със заглавния ред.
        printIndented("<!DOCTYPE html>", true, "\n");
        printIndented("<html>", true, "\n");
        ++indent;
        printIndented("<head></head>", true, "\n");
        printIndented("<body>", true, "\n");
        ++indent;
        printIndented("<table border='1px'>", true, "\n");
        ++indent;
        printIndented("<tr><th>title</th><th>link</th><th>description</th></tr>", true, "\n");
    }

    @Override
    public void endDocument() throws SAXException {
        // Затваряме таблицата и завършаме документа.
        --indent;
        printIndented("</table>", true, "\n");
        --indent;
        printIndented("</body>", true, "\n");
        --indent;
        printIndented("</html>", true, "\n");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        switch (localName) {
            case "item": {
                // В случай че слизаме в елемента item
                // си отваряме реда и отбелязваме, че сме в йерархията му.
                printIndented("<tr>", true, "\n");
                ++indent;
                inItem = true;
                break;
            }
        }
        parentNode = localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (localName) {
            case "item": {
                // Извеждаме поделементите в указания ред.
                printItemRow();

                // Затваряме реда.
                --indent;
                printIndented("</tr>", true, "\n");

                // Отбелязваме, че сме излезнали от йерархията на item
                // и зануляваме стойностите от Map-а.
                inItem = false;
                itemElements.replaceAll((key, value) -> "");
            }
        }
    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
        // Проверяваме дали сме в йерархията на item и дали текущия елемент се съдържа в
        // нашия Map. В такъв случай му добавяме стойността.
        if (inItem && itemElements.containsKey(parentNode)) {
            itemElements.put(parentNode, (itemElements.get(parentNode) + new String(chars, start, length)).trim());
        }
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

    // Малко си промених принтиращата фунцкия да приема bool дали да индентира,
    // както и низ, който да сложи в края (дали ще е нов ред или не зависи от
    // потребителя)
    private void printIndented(String what, boolean toIndent, String ending) {
        try {
            if (toIndent && indent > 0)
                outputStreamWriter.write(String.format("%1$" + (indent * TAB_SIZE) + "s", ""));
            outputStreamWriter.write(what + ending);
            outputStreamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Помощна функция с която извеждаме поделементите на item в указания ред.
    private void printItemRow() {
        printIndented(String.format("<td>%s</td>", itemElements.get("title")), true, "\n");
        printIndented(String.format("<td>%s</td>", itemElements.get("link")), true, "\n");
        printIndented(String.format("<td>%s</td>", itemElements.get("description")), true, "\n");
    }
}

