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
import com.velocity.model.request.capture.ChangeTransaction;

/**
 * This class creates the request XML for Capture transaction
 * 
 * @author Vimal Kumar
 * @date April 14, 2015
 */
public class CaptureRequestXML {

    private static final Logger LOG = Logger.getLogger(CaptureRequestXML.class);

    /**
     * This method generates the XML for Capture request.
     * 
     * @param captureTransaction -holds the value for the type Capture Transaction
     * @param appProfileId - Application profile Id for transaction initiation.
     * @param merchantProfileId - Merchant profile Id for transaction initiation.
     * @return String - Returns the instance of the type String.
     * @throws VelocityException - Exception for Velocity transaction
     * @throws VelocityIllegalArgumentException - Thrown when illegal argument supplied.
     * @throws VelocityNotFoundException - Thrown when some resource not found.
     * @throws VelocityRestInvokeException - Thrown when exception occurs at invoking REST API .
     */
    public String captureXML(ChangeTransaction captureTransaction, String appProfileId) throws VelocityException, VelocityIllegalArgumentException, VelocityNotFoundException, VelocityRestInvokeException {
        /* Creating the instance for DOM parsing */
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try{
            docBuilder = docFactory.newDocumentBuilder();
            // root elements
            Document doc = docBuilder.newDocument();
            // Create Adjust root segment
            createChangeTransaction(doc, captureTransaction, appProfileId);
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
    public void createChangeTransaction(Document doc, ChangeTransaction captureTransaction, String appProfileId) {
        Element undoElement = VelocityXMLUtil.rootElement(doc, "ChangeTransaction");
        VelocityXMLUtil.addAttr(doc, undoElement, "xmlns", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest");
        VelocityXMLUtil.addAttr(doc, undoElement, "xmlns:i", "http://www.w3.org/2001/XMLSchema-instance");
        VelocityXMLUtil.addAttr(doc, undoElement, "i:type", "Capture");
        VelocityXMLUtil.generateSegmentsWithText(undoElement, doc, "ApplicationProfileId", appProfileId);
        // create DifferenceData
        createDifferenceData(undoElement, doc, captureTransaction);
    }
    private static void createDifferenceData(Element element, Document doc, ChangeTransaction captureTransaction) {
        Element differenceDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "DifferenceData");
        VelocityXMLUtil.addAttr(doc, differenceDataElement, "xmlns:d2p1", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addAttr(doc, differenceDataElement, "xmlns:d2p2", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard");
        VelocityXMLUtil.addAttr(doc, differenceDataElement, "xmlns:d2p3", "http://schemas.ipcommerce.com/CWS/v2.0/TransactionProcessing");
        VelocityXMLUtil.addAttr(doc, differenceDataElement, "i:type", "d2p2:" + captureTransaction.getDifferenceData().getType());
        VelocityXMLUtil.generateSegmentsWithText(differenceDataElement, doc, "d2p1:TransactionId", captureTransaction.getDifferenceData().getTransactionId());
        VelocityXMLUtil.generateSegmentsWithText(differenceDataElement, doc, "d2p2:Amount", captureTransaction.getDifferenceData().getAmount());
        VelocityXMLUtil.generateSegmentsWithText(differenceDataElement, doc, "d2p2:TipAmount", captureTransaction.getDifferenceData().getTipAmount());
    }
}
