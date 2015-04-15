package com.velocity.request.transaction.xml;

import java.io.StringWriter;
import java.io.Writer;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This Utility class will use to generate XML tag with or without attribute and text values as per
 * requirement we have different util methods created for different purposes for different velocity
 * request.
 * 
 * @author Vimal kumar
 * @date 14-April-2015
 */
public class VelocityXMLUtil {

    private static final Logger LOG = Logger.getLogger(VelocityXMLUtil.class);

    /**
     * Will use to generate Root element of XML for any request type
     * 
     * @param doc - holds the parser object
     * @param elementName - holds the name of element
     * @return element - Returns the instance of the Element type
     */
    public static Element rootElement(Document doc, String elementName) {
        Element element = doc.createElement(elementName.trim());
        doc.appendChild(element);
        return element;
    }
    /**
     * This will append attribute with its value to any XML element.
     * 
     * @param doc - holds the parser object
     * @param element - holds the element object data
     * @param attributeName - holds the name of the XML attribute
     * @param attrValue - holds the value of the XML attribute
     */
    public static void addAttr(Document doc, Element element, String attributeName, String attrValue) {
        Attr aTElementAttr1 = doc.createAttribute(attributeName.trim());
        aTElementAttr1.setValue(attrValue);
        element.setAttributeNode(aTElementAttr1);
    }
    
    /**
     * This will generate normal XML segment.
     * 
     * @param element - holds the element object data
     * @param doc - holds the parser object
     * @param elementName - holds the name of the element
     * @return element - Returns the instance of the Element type
     */
    public static Element generateXMLElement(Element element, Document doc, String elementName) {
        Element element2 = doc.createElement(elementName.trim());
        element.appendChild(element2);
        return element2;
    }
    
    /**
     * This will generate the XML segment with text.
     * 
     * @param element - holds the element object data
     * @param doc - holds the parser object
     * @param elementName - holds the name of the element
     * @param value - holds the element value.
     */
    public static void generateSegmentsWithText(Element element, Document doc, String elementName, String value) {
        Element element2 = doc.createElement(elementName.trim());
        element2.appendChild(doc.createTextNode(value));
        element.appendChild(element2);
    }
   
    /**
     * This will generate the XML segment with text and attribute.
     * 
     * @param element - holds the element object data
     * @param doc - holds the parser object
     * @param elementName - holds the name of the element
     * @param value - holds the element value.
     * @param attributeName - holds the name of the attribute.
     * @param attrValue - holds the value of the attribute.
     */
    public static void generateSegmentsWithTextAndAttr(Element element, Document doc, String elementName, String value, String attributeName, String attrValue) {
        Element element2 = doc.createElement(elementName.trim());
        element2.appendChild(doc.createTextNode(value));
        Attr attr = doc.createAttribute(attributeName.trim());
        attr.setValue(attrValue);
        element2.setAttributeNode(attr);
        element.appendChild(element2);
    }
    /**
     * This will generate the XML segment with attribute.
     * 
     * @param element - holds the element object data 
     * @param doc - holds the parser object
     * @param elementName - holds the name of the element
     * @param attributeName - holds the name of the attribute.
     * @param value - holds the value of the attribute.
     */
    public static void generateSegmentsWithAttribute(Element element, Document doc, String elementName, String attributeName, String value) {
        Element element2 = doc.createElement(elementName.trim());
        Attr attr = doc.createAttribute(attributeName.trim());
        attr.setValue(value);
        element2.setAttributeNode(attr);
        element.appendChild(element2);
    }
    /**
     * This will add text to the the created element.
     * 
     * @param element - holds the element object data 
     * @param doc - holds the parser object
     * @param textValue - holds the value for the created element
     */
    public static void addTextToElement(Element element, Document doc, String textValue) {
        element.appendChild(doc.createTextNode(textValue));
    }
    /**
     * This will use to format any generated XML.
     * 
     * @param document - holds the created XML object data
     * @return String - Returns the instance of the type String 
     * @throws Exception - thrown if any exception occurs
     */
    public static final String prettyPrint(Document document) throws Exception {
        OutputFormat format = new OutputFormat(document);
        format.setLineWidth(150);
        format.setIndenting(true);
        format.setIndent(4);
        Writer out = new StringWriter();
        XMLSerializer serializer = new XMLSerializer(out, format);
        serializer.serialize(document);
        LOG.debug("Request XML:\n " + out.toString());
        return out.toString().trim();
    }
}
