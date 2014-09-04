import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class XmlConfigurationParser {

  public Document loadFile(String url) throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document document = dBuilder.parse(new URL(url).openStream());
    document.getDocumentElement().normalize();
    return document;
  }

  public Element getRootElement(Document document) {
    return document.getDocumentElement();
  }

  public NodeList getNodeListByTagName(Element document, String tag) {
    return document.getElementsByTagName(tag);
  }

  public List<Node> getNodesByTagName(Element document, String tag, NodeType nodeType) {
    NodeList nodeList = getNodeListByTagName(document, tag);
    List<Node> nodes = new ArrayList<>();
    for (int index = 0; index < nodeList.getLength(); index++) {
      Node node = nodeList.item(index);
      if (getNodeType(node) == nodeType) {
        nodes.add(node);
      }
    }
    return nodes;
  }

  public NodeList getNodeListByTagName(Document document, String tag) {
    return document.getElementsByTagName(tag);
  }

  public List<Node> getNodesByTagName(Document document, String tag, NodeType nodeType) {
    NodeList nodeList = getNodeListByTagName(document, tag);
    List<Node> nodes = new ArrayList<>();
    for (int index = 0; index < nodeList.getLength(); index++) {
      Node node = nodeList.item(index);
      if (getNodeType(node) == nodeType) {
        nodes.add(node);
      }
    }
    return nodes;
  }

  public NamedNodeMap getElementAttributesNamedNodeMap(Element element) {
    return element.getAttributes();
  }

  public String getElementAttributeByName(Element element, String attributeName) {
    return element.getAttribute(attributeName);
  }

  public String getTagName(Element element) {
    return element.getTagName();
  }

  public String getNodeName(Node node) {
    return node.getNodeName();
  }

  public NodeType getNodeType(Node node) {
    return NodeType.valueOfInt(node.getNodeType());
  }

  public List<Node> getChildNodes(Node menu, NodeType nodeType) {
    NodeList nodeList = menu.getChildNodes();
    List<Node> nodes = new ArrayList<>();
    for (int index = 0; index < nodeList.getLength(); index++) {
      Node node = nodeList.item(index);
      if (getNodeType(node) == nodeType) {
        nodes.add(node);
      }
    }
    return nodes;
  }

  public List<Node> getChildNodesByTagName(Node parentNode,String tag, NodeType nodeType) {
    NodeList nodeList = parentNode.getChildNodes();
    List<Node> nodes = new ArrayList<>();
    for (int index = 0; index < nodeList.getLength(); index++) {
      Node node = nodeList.item(index);
      if (getNodeType(node) == nodeType && tag.equals(getNodeName(node))) {
        nodes.add(node);
      }
    }
    return nodes;
  }

  public String getCData(Node parentNode) {
    StringBuilder cData=new StringBuilder();
    NodeList nodeList = parentNode.getChildNodes();
    for (int index = 0; index < nodeList.getLength(); index++) {
      Node node = nodeList.item(index);
      if (getNodeType(node) == NodeType.CDATA_SECTION_NODE) {
        cData=cData.append(node.getNodeValue()).append("\n");
      }
    }
    return cData.toString().trim();
  }
}
