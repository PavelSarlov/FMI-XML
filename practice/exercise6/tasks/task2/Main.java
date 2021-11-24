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
            parser.setContentHandler(new SAXValidator(outputStreamWriter));
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

class SAXValidator implements ContentHandler {
    Locator locator;
    Integer indent;
    OutputStreamWriter outputStreamWriter;
    private final Integer TAB_SIZE = 4;

    // Ще държи текущия родител на върха на стека
    private Stack<String> currentParent = new Stack<String>();

    // За всеки поделемент на item ще държим съответен брояч
    private Map<String, Byte> itemSubel;

    // Брояч за item поделементите на channel
    private byte channelItemCount = 0;

    public SAXValidator(OutputStreamWriter outputStreamWriter) {
        this.outputStreamWriter = outputStreamWriter;
        indent = 0;
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        this.locator = locator;
    }

    @Override
    public void startDocument() throws SAXException {
        // ...
    }

    @Override
    public void endDocument() throws SAXException {
        // ...
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        // Спрямо името на елемента изпълняваме съответните действия.
        switch (qName) {
            case "rss": {
                // Валидираме version атрибута.
                if (!validateRssVersion(atts, "version"))
                    raiseError("Version attribute of rss must be a positive integer");
                break;
            }
            // В случай на channel нулираме брояча на item-ите му
            case "channel": {
                channelItemCount = 0;
                break;
            }
            // В случай на item нулираме броячите на поделементите му
            case "item": {
                itemSubel = new HashMap<String, Byte>() {
                    {
                        put("link", (byte) 0);
                        put("description", (byte) 0);
                        put("title", (byte) 0);
                    }
                };
                channelItemCount += 1;
                break;
            }
            // За всеки от елементите, ако сме в йерархията на item, му увеличаваме брояча
            case "title":
            case "description":
            case "link": {
                if (currentParent.peek() == "item")
                    itemSubel.put(qName, (byte) (itemSubel.get(qName) + 1));
                break;
            }
        }

        // Добавяме текущия елемент към стека
        currentParent.push(qName);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            // За всеки от поделементите проверяваме дали отговаря на изискванията
            case "item": {
                for (String key : itemSubel.keySet()) {
                    if (itemSubel.get(key) != (byte) 1)
                        raiseError("Subelements of item must be present exactly once.");
                }
                break;
            }
            // Проверяваме броя на item поделементите на channel
            case "channel": {
                if (channelItemCount < 2 || channelItemCount > 10)
                    raiseError("Items of channel must be between 2 and 10 inclusive.");
                break;
            }
        }

        // Махаме от стека текущия елемент
        currentParent.pop();
    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
        // ...
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

    private void printAttributes(Attributes atts) {
        if (atts.getLength() > 0) {
            // ++indent;

            // Беше направено атрибутите също да минават на нов ред, но на мен не ми
            // харесва така, та пред всеки атрибут ще добавям
            // един ' ', за да ги принтира на един ред.
            for (int i = 0; i < atts.getLength(); i++) {
                printIndented(" " + atts.getQName(i) + "=\"" + atts.getValue(i) + "\"", false, "");
            }

            // --indent;
        }
    }

    private boolean validateRssVersion(Attributes atts, String attName) {
        try {
            // assert не бачка, затова ще си хвърляме ние exception
            if (Integer.parseInt(atts.getValue(attName)) <= 0)
                throw new Exception();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Помощна фунцкия за изкарване на грешката
    private void raiseError(String msg) {
        printIndented("ERROR: " + msg + ", Line: " + locator.getLineNumber() + ", Column: " + locator.getColumnNumber(),
                false, "\n");
    }
}

