import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element; // Нужно ни е за добавяне на атрибут.
import org.xml.sax.InputSource;

public class Main {
    private static boolean skipNL;
    private static Document doc; // Добре е документа да ни е достъпен в целия клас.

    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder builder = dbf.newDocumentBuilder();
        InputSource source = new InputSource("rss.xml");
        // InputSource source = new InputSource(new StringReader(""));
        doc = builder.parse(source);
        System.out.println(printXML(doc.getDocumentElement()));
    }

    private static String printXML(Node rootNode) {
        // Използвам дефинираните помощни функции за да изпълня условията на задачата.
        linkToAttribute(((Element) rootNode).getElementsByTagName("item"));
        keepFirstTenItems(((Element) rootNode).getElementsByTagName("item"));
        channelAddSponsor(((Element) rootNode).getElementsByTagName("channel"));

        String tab = "";
        skipNL = false;
        return (printXML(rootNode, tab));
    }

    // Фунцкия за трансформирането на link елемента на item към атрибут.
    // Приема лист от item елементи.
    private static void linkToAttribute(NodeList items) {
        // Итерираме по item елементите
        for (int i = 0; i < items.getLength(); i++) {
            // Добавяме атрибут към item елемента и премахваме детето link
            Element item = (Element) items.item(i);
            Node link = item.getElementsByTagName("link").item(0);
            // trim-ваме заради досадни whitespaces
            item.setAttribute("link", link.getChildNodes().item(0).getNodeValue().trim());
            item.removeChild(link);
        }
    }

    // Фунцкия за премахване на всички item елементи след 10-тия.
    // Приема лист от item елементи.
    private static void keepFirstTenItems(NodeList items) {
        // Взимаме 11-тия (с индекс 10) елемент и го премахваме, при което
        // дължината на items намалява с едно, т.е. цикъла ще спре
        // при items.getLength() == 10.
        for (int i = 10; i < items.getLength();) {
            items.item(i).getParentNode().removeChild(items.item(i));
        }
    }

    // Функция за добавяне на нов поделемент sponsor към channel.
    private static void channelAddSponsor(NodeList channels) {
        // Аналогично итерираме по тях и добавяме sponsor.
        for (int i = 0; i < channels.getLength(); i++) {
            Node sponsor = doc.createElement("sponsor");
            channels.item(i).appendChild(sponsor);
        }
    }

    private static String printXML(Node rootNode, String tab) {
        String print = "";
        if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
            // Между другото, индентацията и новите редове някак си стават
            // автоматично, така и не разбрах как, никъде не се променя tab,
            // затова ги премахнах.
            print += "<" + rootNode.getNodeName().trim();
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

