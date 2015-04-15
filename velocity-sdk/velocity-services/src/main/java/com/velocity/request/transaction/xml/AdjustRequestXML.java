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
import com.velocity.model.request.Adjust;

/**
 * This class creates the request XML for adjust transaction
 * 
 * @author Vimal Kumar
 * @date 14-April-2015
 */
public class AdjustRequestXML {

    private static final Logger LOG = Logger.getLogger(ReturnByIdXML.class);

    /**
     * This method generates the request input XML for Adjust transaction method .
     * 
     * @param adjustTransaction - holds the data for AdjustRequest
     * @param appProfileId - Application profile Id for transaction initiation.
     * @param merchantProfileId - Merchant profile Id for transaction initiation.
     * @return String - Returns the instance of the type String.
     * @throws VelocityException - Exception for Velocity transaction
     * @throws VelocityIllegalArgumentException - Thrown when illegal argument supplied.
     * @throws VelocityNotFoundException - Thrown when some resource not found.
     * @throws VelocityRestInvokeException - Thrown when exception occurs at invoking REST API .
     */
    public String adjustXML(Adjust adjustTransaction, String appProfileId, String merchantProfileId) throws VelocityException, VelocityIllegalArgumentException, VelocityNotFoundException, VelocityRestInvokeException {
        /* Creating the instance for DOM parsing */
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try{
            docBuilder = docFactory.newDocumentBuilder();
            // root elements
            Document doc = docBuilder.newDocument();
            /* Creating Adjust root segment */
            createAdjust(doc, adjustTransaction, appProfileId, merchantProfileId);
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
    public void createAdjust(Document doc, Adjust adjustTransaction, String appProfileId, String merchantProfileId) {
        Element adjustElement = VelocityXMLUtil.rootElement(doc, "Adjust");
        VelocityXMLUtil.addAttr(doc, adjustElement, "xmlns:i", "http://www.w3.org/2001/XMLSchema-instance");
        VelocityXMLUtil.addAttr(doc, adjustElement, "xmlns", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest");
        VelocityXMLUtil.addAttr(doc, adjustElement, "i:type", "Adjust");
        VelocityXMLUtil.generateSegmentsWithText(adjustElement, doc, "ApplicationProfileId", appProfileId);
        Element batchIdsElement = VelocityXMLUtil.generateXMLElement(adjustElement, doc, "BatchIds");
        VelocityXMLUtil.addAttr(doc, batchIdsElement, "xmlns:d2p1", "http://schemas.microsoft.com/2003/10/Serialization/Arrays");
        VelocityXMLUtil.addAttr(doc, batchIdsElement, "i:nil", String.valueOf(adjustTransaction.getBatchIds().isNillable()));
        VelocityXMLUtil.addTextToElement(batchIdsElement, doc, adjustTransaction.getBatchIds().getValue());
        VelocityXMLUtil.generateSegmentsWithText(adjustElement, doc, "MerchantProfileId", merchantProfileId);
        // create DifferenceData
        createDifferenceData(adjustElement, doc, adjustTransaction);
    }
    /**
     * This method creates the elements for DifferenceData using the VelocityXMLUtil class.
     */
    private static void createDifferenceData(Element element, Document doc, Adjust adjustTransaction) {
        Element differenceDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "DifferenceData");
        VelocityXMLUtil.addAttr(doc, differenceDataElement, "xmlns:ns1", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(differenceDataElement, doc, "ns2:Amount", adjustTransaction.getDifferenceData().getAmount(), "xmlns:ns2", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(differenceDataElement, doc, "ns3:TransactionId", adjustTransaction.getDifferenceData().getTransactionId(), "xmlns:ns3", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
    }
}
