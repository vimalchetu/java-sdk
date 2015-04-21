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
import com.velocity.model.request.AuthorizeTransaction;

/**
 * This class creates the request XML for Verify transaction
 * 
 * @author Vimal Kumar
 * @date April 14, 2015
 */
public class VerifyTransactionXML
{

    private static final Logger LOG = Logger.getLogger(VerifyTransactionXML.class);

    /**
     * This method generates the XML for verify request.
     * 
     * @param authorizeTransaction - holds the value for the type AuthorizeTransaction
     * @param appProfileId - Application profile Id for transaction initiation.
     * @param merchantProfileId - Merchant profile Id for transaction initiation.
     * @return String - Returns the instance of the type String.
     * @throws VelocityException - Exception for Velocity transaction
     * @throws VelocityIllegalArgumentException - Thrown when illegal argument supplied.
     * @throws VelocityNotFoundException - Thrown when some resource not found.
     * @throws VelocityRestInvokeException - Thrown when exception occurs at invoking REST API .
     */
    public String verifyXML(AuthorizeTransaction authorizeTransaction, String appProfileId, String merchantProfileId) throws VelocityException, VelocityIllegalArgumentException, VelocityNotFoundException, VelocityRestInvokeException {
        /* Creating the instance for DOM parsing */
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try{
            docBuilder = docFactory.newDocumentBuilder();
            // root elements
            Document doc = docBuilder.newDocument();
            createAuthorizedTransactions(doc, authorizeTransaction, appProfileId, merchantProfileId);
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
    public static void createAuthorizedTransactions(Document doc, AuthorizeTransaction authorizeTransaction, String appProfileId, String merchantProfileId) {
        Element ATElement = VelocityXMLUtil.rootElement(doc, "AuthorizeTransaction");
        VelocityXMLUtil.addAttr(doc, ATElement, "xmlns:i", "http://www.w3.org/2001/XMLSchema-instance");
        VelocityXMLUtil.addAttr(doc, ATElement, "xmlns", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest");
        VelocityXMLUtil.addAttr(doc, ATElement, "i:type", "AuthorizeTransaction");
        VelocityXMLUtil.generateSegmentsWithText(ATElement, doc, "ApplicationProfileId", appProfileId);
        VelocityXMLUtil.generateSegmentsWithText(ATElement, doc, "MerchantProfileId", merchantProfileId);
        // Transaction
        createTransaction(ATElement, doc, authorizeTransaction);
    }
    public static void createTransaction(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element transactionElement = VelocityXMLUtil.generateXMLElement(element, doc, "Transaction");
        VelocityXMLUtil.addAttr(doc, transactionElement, "xmlns:ns1", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard");
        VelocityXMLUtil.addAttr(doc, transactionElement, "i:type", "ns1:" + authorizeTransaction.getTransaction().getType());
        createTenderData(transactionElement, doc, authorizeTransaction);
        createTransactionData(transactionElement, doc, authorizeTransaction);
    }
    public static void createTenderData(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element tenderDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns1:TenderData");
        createCardData(tenderDataElement, doc, authorizeTransaction);
        createCardSecurityData(tenderDataElement, doc, authorizeTransaction);
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(tenderDataElement, doc,
                "ns1:EcommerceSecurityData", authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().isNillable()));
    }
    public static void createCardData(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element cardDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns1:CardData");
        if(authorizeTransaction.getTransaction().getTenderData().getCardData().getPan()!=null && authorizeTransaction.getTransaction().getTenderData().getCardData().getPan().length()!=0){
        VelocityXMLUtil.generateSegmentsWithText(cardDataElement, doc, "ns1:CardType", authorizeTransaction.getTransaction().getTenderData().getCardData().getCardType());
        VelocityXMLUtil.generateSegmentsWithText(cardDataElement, doc, "ns1:CardholderName", authorizeTransaction.getTransaction().getTenderData().getCardData().getCardholderName());
        VelocityXMLUtil.generateSegmentsWithText(cardDataElement, doc, "ns1:PAN", authorizeTransaction.getTransaction().getTenderData().getCardData().getPan());
        VelocityXMLUtil.generateSegmentsWithText(cardDataElement, doc, "ns1:Expire", authorizeTransaction.getTransaction().getTenderData().getCardData().getExpire());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(cardDataElement, doc,
                "ns1:Track1Data", authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(cardDataElement, doc,
              "ns1:Track2Data", authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack2Data().getValue(),
              "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack2Data().isNillable()));
        } else if(authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data1()!=null && authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data1().length()!=0){
            VelocityXMLUtil.generateSegmentsWithText(cardDataElement, doc, "ns1:CardType", authorizeTransaction.getTransaction().getTenderData().getCardData().getCardType());
            VelocityXMLUtil.generateSegmentsWithText(cardDataElement, doc, "ns1:CardholderName", authorizeTransaction.getTransaction().getTenderData().getCardData().getCardholderName());
            VelocityXMLUtil.generateSegmentsWithTextAndAttr(cardDataElement, doc,
                    "ns1:PAN", authorizeTransaction.getTransaction().getTenderData().getCardData().getPan1().getValue(),
                    "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardData().getPan1().isNillable()));
            
            VelocityXMLUtil.generateSegmentsWithTextAndAttr(cardDataElement, doc,
                    "ns1:Expire", authorizeTransaction.getTransaction().getTenderData().getCardData().getExpire1().getValue(),
                    "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardData().getExpire1().isNillable()));
            
            VelocityXMLUtil.generateSegmentsWithText(cardDataElement, doc,
                    "ns1:Track1Data", authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data1());
            VelocityXMLUtil.generateSegmentsWithTextAndAttr(cardDataElement, doc,
                  "ns1:Track2Data", authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack2Data().getValue(),
                  "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack2Data().isNillable()));
        } else if(authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack2Data2()!=null && authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack2Data2().length()!=0){
            VelocityXMLUtil.generateSegmentsWithText(cardDataElement, doc, "ns1:CardType", authorizeTransaction.getTransaction().getTenderData().getCardData().getCardType());
            VelocityXMLUtil.generateSegmentsWithText(cardDataElement, doc, "ns1:CardholderName", authorizeTransaction.getTransaction().getTenderData().getCardData().getCardholderName());
            VelocityXMLUtil.generateSegmentsWithTextAndAttr(cardDataElement, doc,
                    "ns1:PAN", authorizeTransaction.getTransaction().getTenderData().getCardData().getPan1().getValue(),
                    "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardData().getPan1().isNillable()));
            
            VelocityXMLUtil.generateSegmentsWithTextAndAttr(cardDataElement, doc,
                    "ns1:Expire", authorizeTransaction.getTransaction().getTenderData().getCardData().getExpire1().getValue(),
                    "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardData().getExpire1().isNillable()));
            VelocityXMLUtil.generateSegmentsWithText(cardDataElement, doc,
                    "ns1:Track2Data", authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack2Data2());
            VelocityXMLUtil.generateSegmentsWithTextAndAttr(cardDataElement, doc,
                  "ns1:Track1Data", authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().getValue(),
                  "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().isNillable()));
        }
        
    }
    public static void createCardSecurityData(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element cardSecurityDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns1:CardSecurityData");
        // create AVS Data
        createAVSData(cardSecurityDataElement, doc, authorizeTransaction);
        VelocityXMLUtil.generateSegmentsWithText(cardSecurityDataElement, doc, "ns1:CVDataProvided", authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getCvDataProvided());
        VelocityXMLUtil.generateSegmentsWithText(cardSecurityDataElement, doc, "ns1:CVData", authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getCvData().getValue());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(cardSecurityDataElement, doc,
                "ns1:KeySerialNumber", authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getKeySerialNumber().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getKeySerialNumber().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(cardSecurityDataElement, doc,
                "ns1:PIN", authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getPin().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getPin().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(cardSecurityDataElement, doc,
                "ns1:IdentificationInformation", authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().isNillable()));
    }
    public static void createAVSData(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element aVSDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns1:AVSData");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(aVSDataElement, doc,
                "ns1:CardholderName", authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCardholderName().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCardholderName().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(aVSDataElement, doc, "ns1:Street", authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getStreet());
        VelocityXMLUtil.generateSegmentsWithText(aVSDataElement, doc, "ns1:City", authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCity());
        VelocityXMLUtil.generateSegmentsWithText(aVSDataElement, doc, "ns1:StateProvince", authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getStateProvince());
        VelocityXMLUtil.generateSegmentsWithText(aVSDataElement, doc, "ns1:PostalCode", authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getPostalCode());
        VelocityXMLUtil.generateSegmentsWithText(aVSDataElement, doc, "ns1:Phone", authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getPhone());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(aVSDataElement, doc,
                "ns1:Email", authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getEmail().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getEmail().isNillable()));
    }
    public static void createTransactionData(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element transactionDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns1:TransactionData");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns8:Amount", authorizeTransaction.getTransaction().getTransactionData().getAmount(), "xmlns:ns8", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns9:CurrencyCode", authorizeTransaction.getTransaction().getTransactionData().getCurrencyCode(), "xmlns:ns9", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns10:TransactionDateTime", authorizeTransaction.getTransaction().getTransactionData().getTransactiondateTime(), "xmlns:ns10", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:AccountType", authorizeTransaction.getTransaction().getTransactionData().getAccountType());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:CustomerPresent", authorizeTransaction.getTransaction().getTransactionData().getCustomerPresent());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:EmployeeId", authorizeTransaction.getTransaction().getTransactionData().getEmployeeId());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:EntryMode", authorizeTransaction.getTransaction().getTransactionData().getEntryMode());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:IndustryType", authorizeTransaction.getTransaction().getTransactionData().getIndustryType());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:InvoiceNumber", authorizeTransaction.getTransaction().getTransactionData().getInvoiceNumber());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:OrderNumber", authorizeTransaction.getTransaction().getTransactionData().getOrderNumber());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:TipAmount", authorizeTransaction.getTransaction().getTransactionData().getTipAmount());
    }
}
