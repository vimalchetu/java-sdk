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
import com.velocity.model.request.Undo;

/**
 * This class creates the request XML for Undo transaction
 * 
 * @author Vimal Kumar
 * @date April 14, 2015
 */
public class UndoRequestXML {

    private static final Logger LOG = Logger.getLogger(UndoRequestXML.class);

    /**
     * This method generates the XML for Undo request.
     * 
     * @param undoTransaction - holds the value for Undo Transaction.
     * @param appProfileId - Application profile Id for transaction initiation.
     * @param merchantProfileId - Merchant profile Id for transaction initiation.
     * @return String - Returns the instance of the type String.
     * @throws VelocityException - Exception for Velocity transaction
     * @throws VelocityIllegalArgumentException - Thrown when illegal argument supplied.
     * @throws VelocityNotFoundException - Thrown when some resource not found.
     * @throws VelocityRestInvokeException - Thrown when exception occurs at invoking REST API .
     */
    public String undoXML(Undo undoTransaction, String appProfileId, String merchantProfileId) throws VelocityException, VelocityIllegalArgumentException, VelocityNotFoundException, VelocityRestInvokeException {
        /* Creating the instance for DOM parsing */
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try{
            docBuilder = docFactory.newDocumentBuilder();
            // root elements
            Document doc = docBuilder.newDocument();
            // Create Adjust root segment
            createUndo(doc, undoTransaction, appProfileId, merchantProfileId);
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
    public void createUndo(Document doc, Undo undoTransaction, String appProfileId, String merchantProfileId) {
        Element undoElement = VelocityXMLUtil.rootElement(doc, "Undo");
        VelocityXMLUtil.addAttr(doc, undoElement, "xmlns:i", "http://www.w3.org/2001/XMLSchema-instance");
        VelocityXMLUtil.addAttr(doc, undoElement, "xmlns", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest");
        VelocityXMLUtil.addAttr(doc, undoElement, "i:type", "Undo");
        VelocityXMLUtil.generateSegmentsWithText(undoElement, doc, "ApplicationProfileId", appProfileId);
        Element batchIdsElement = VelocityXMLUtil.generateXMLElement(undoElement, doc, "BatchIds");
        VelocityXMLUtil.addAttr(doc, batchIdsElement, "xmlns:d2p1", "http://schemas.microsoft.com/2003/10/Serialization/Arrays");
        VelocityXMLUtil.addAttr(doc, batchIdsElement, "i:nil", String.valueOf(undoTransaction.getBatchIds().isNillable()));
        VelocityXMLUtil.addTextToElement(batchIdsElement, doc, undoTransaction.getBatchIds().getValue());
        Element differenceDataElement = VelocityXMLUtil.generateXMLElement(undoElement, doc, "DifferenceData");
        VelocityXMLUtil.addAttr(doc, differenceDataElement, "xmlns:d2p1", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addAttr(doc, differenceDataElement, "i:nil", String.valueOf(undoTransaction.getDifferenceData().isNillable()));
        VelocityXMLUtil.addTextToElement(differenceDataElement, doc, undoTransaction.getDifferenceData().getValue());
        VelocityXMLUtil.generateSegmentsWithText(undoElement, doc, "MerchantProfileId", merchantProfileId);
        VelocityXMLUtil.generateSegmentsWithText(undoElement, doc, "TransactionId", undoTransaction.getTransactionId());
    }
}
