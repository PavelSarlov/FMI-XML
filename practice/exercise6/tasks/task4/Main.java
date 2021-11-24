import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Main {
    private static boolean skipNL;

    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder builder = dbf.newDocumentBuilder();
        InputSource source = new InputSource("rss.xml");
        // InputSource source = new InputSource(new StringReader(""));
        Document document = builder.parse(source);
        System.out.println(printXML(document.getDocumentElement()));
    }

    private static String printXML(Node rootNode) {
        String tab = "";
        skipNL = false;
        return (printXML(rootNode, tab));
    }

    private static String printXML(Node rootNode, String tab) {
        String print = "";
        if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
            // Тука имаше излишен нов ред. Между другото, индентацията някак си
            // става автоматично, така и не разбрах как, никъде не се променя tab,
            // затова го премахнах.
            print += "<" + rootNode.getNodeName();
            NamedNodeMap attributes = rootNode.getAttributes();
            for (int j = 0; j < attributes.getLength(); j++) {
                Attr attr = (Attr) attributes.item(j);
                if (attr != null) {
                    // Прибавяме всеки атрибут на елемента към изходния текст.
                    print += String.format(" %s='%s'", attr.getNodeName(), attr.getNodeValue());
                }
            }
            print += ">";
        }
        NodeList nl = rootNode.getChildNodes();
        if (nl.getLength() > 0) {
            // Прибавяме рекурсивно съдържанието на всеки поделемент на текущия елемент
            for (int i = 0; i < nl.getLength(); i++) {
                print += printXML(nl.item(i), tab);
            }
        } else {
            if (rootNode.getNodeValue() != null) {
                // Съдържанието трябва да е с главни букви, hence toUpperCase().
                print = rootNode.getNodeValue().toUpperCase();
            }
            skipNL = true;
        }
        if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
            if (!skipNL) {
                print += "\n";
            }
            skipNL = false;
            // Слагаме затварящ таг за текущия елемент.
            print += "</" + rootNode.getNodeName() + ">";
        }
        return (print);
    }
}

