package com.velocity.request.transaction.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.velocity.exceptions.VelocityException;
import com.velocity.exceptions.VelocityIllegalArgumentException;
import com.velocity.exceptions.VelocityNotFoundException;
import com.velocity.exceptions.VelocityRestInvokeException;
import com.velocity.model.request.ReturnById;

/**
 * This class creates the request XML for ReturnById transaction
 * 
 * @author Vimal Kumar
 * @date April 14, 2015
 */
public class ReturnByIdXML {

    private static final Logger LOG = Logger.getLogger(ReturnByIdXML.class);

    /**
     * @param returnByIdTransaction - holds the value for the type ReturnById Transaction
     * @param appProfileId - Application profile Id for transaction initiation.
     * @param merchantProfileId - Merchant profile Id for transaction initiation.
     * @return String - Returns the instance of the type String.
     * @throws VelocityException - Exception for Velocity transaction
     * @throws VelocityIllegalArgumentException - Thrown when illegal argument supplied.
     * @throws VelocityNotFoundException - Thrown when some resource not found.
     * @throws VelocityRestInvokeException - Thrown when exception occurs at invoking REST API .
     */
    public String returnByIdXML(ReturnById returnByIdTransaction, String appProfileId, String merchantProfileId) throws VelocityException, VelocityIllegalArgumentException, VelocityNotFoundException, VelocityRestInvokeException {
        /* Creating the instance for DOM parsing */
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try{
            docBuilder = docFactory.newDocumentBuilder();
            // root elements
            Document doc = docBuilder.newDocument();
            createReturnById(doc, returnByIdTransaction, appProfileId, merchantProfileId);
            return VelocityXMLUtil.prettyPrint(doc);
        }catch (ParserConfigurationException ex){
            LOG.error("Error Occurred :", ex);
        }catch (Exception ex){
            LOG.error("Error Occurred :", ex);
        }
        return null;
    }
    /**
     * This method creates the root elements, attributes and segments with text using the
     * VelocityXMLUtil class.
     */
    public static void createReturnById(Document doc, ReturnById returnByIdTransaction, String appProfileId, String merchantProfileId) {
        Element returnByIdElement = VelocityXMLUtil.rootElement(doc, "ReturnById");
        VelocityXMLUtil.addAttr(doc, returnByIdElement, "xmlns:i", "http://www.w3.org/2001/XMLSchema-instance");
        VelocityXMLUtil.addAttr(doc, returnByIdElement, "xmlns", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest");
        VelocityXMLUtil.addAttr(doc, returnByIdElement, "i:type", "ReturnById");
        VelocityXMLUtil.generateSegmentsWithText(returnByIdElement, doc, "ApplicationProfileId", appProfileId);
        Element batchIdsElement = VelocityXMLUtil.generateXMLElement(returnByIdElement, doc, "BatchIds");
        VelocityXMLUtil.addAttr(doc, batchIdsElement, "xmlns:d2p1", "http://schemas.microsoft.com/2003/10/Serialization/Arrays");
        VelocityXMLUtil.addAttr(doc, batchIdsElement, "i:nil", String.valueOf(returnByIdTransaction.getBatchIds().isNillable()));
        VelocityXMLUtil.addTextToElement(batchIdsElement, doc, returnByIdTransaction.getBatchIds().getValue());
        // create DifferenceData
        createDifferenceData(returnByIdElement, doc, returnByIdTransaction);
        VelocityXMLUtil.generateSegmentsWithText(returnByIdElement, doc, "MerchantProfileId", merchantProfileId);
    }
    private static void createDifferenceData(Element element, Document doc, ReturnById returnByIdTransaction) {
        Element differenceDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "DifferenceData");
        VelocityXMLUtil.addAttr(doc, differenceDataElement, "xmlns:ns1", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard");
        VelocityXMLUtil.addAttr(doc, differenceDataElement, "i:type", "ns1:BankcardReturn");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(differenceDataElement, doc, "ns2:TransactionId", returnByIdTransaction.getDifferenceData().getTransactionId(), "xmlns:ns2", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithText(differenceDataElement, doc, "ns1:Amount", returnByIdTransaction.getDifferenceData().getAmount());
    }
}
