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
 * This class creates the request XML for P2PEAuthorize request
 * 
 * @author Vimal Kumar
 * @date April 14, 2015
 */
public class P2PEAuthorizeRequestXML {

    private static final Logger LOG = Logger.getLogger(P2PEAuthorizeRequestXML.class);

    /**
     * This method generates the input XML for P2PEAuthorize request.
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
    public String p2peAuthorizeXML(AuthorizeTransaction authorizeTransaction, String appProfileId, String merchantProfileId) throws VelocityException, VelocityIllegalArgumentException, VelocityNotFoundException, VelocityRestInvokeException {
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
        Element relayResponseUrlElement = VelocityXMLUtil.generateXMLElement(transactionElement, doc, "ns2:RelayResponseUrl");
        VelocityXMLUtil.addAttr(doc, relayResponseUrlElement, "xmlns:ns2", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addAttr(doc, relayResponseUrlElement, "i:nil", String.valueOf(authorizeTransaction.getTransaction().getRelayResponseUrl().isNillable()));
        VelocityXMLUtil.addTextToElement(relayResponseUrlElement, doc, authorizeTransaction.getTransaction().getRelayResponseUrl().getValue());
        // Create Customer Data
        createCustomerData(transactionElement, doc, authorizeTransaction);
        // create Reporting Data
        createReportingData(transactionElement, doc, authorizeTransaction);
        // create Tender data
        createTenderData(transactionElement, doc, authorizeTransaction);
        // create TransactionData
        createTransactionData(transactionElement, doc, authorizeTransaction);
    }
    public static void createCustomerData(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element customerDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns3:CustomerData");
        VelocityXMLUtil.addAttr(doc, customerDataElement, "xmlns:ns3", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        // Billing Data
        createBillingData(customerDataElement, doc, authorizeTransaction);
        VelocityXMLUtil.generateSegmentsWithText(customerDataElement, doc, "ns3:CustomerId", authorizeTransaction.getTransaction().getCustomerData().getCustomerId());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(customerDataElement, doc,
                "ns3:CustomerTaxId", authorizeTransaction.getTransaction().getCustomerData().getCustomerTaxId().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getCustomerData().getCustomerTaxId().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(customerDataElement, doc,
                "ns3:ShippingData", authorizeTransaction.getTransaction().getCustomerData().getShippingData().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getCustomerData().getShippingData().isNillable()));
    }
    private static void createBillingData(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element BillingDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns3:BillingData");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(BillingDataElement, doc,
                "ns3:Name", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getName().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getCustomerData().getBillingData().getName().isNillable()));
        // create address
        createAddress(BillingDataElement, doc, authorizeTransaction);
        VelocityXMLUtil.generateSegmentsWithText(BillingDataElement, doc, "ns3:BusinessName", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getBusinessName());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(BillingDataElement, doc,
                "ns3:Phone", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getPhone().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getCustomerData().getBillingData().getPhone().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(BillingDataElement, doc,
                "ns3:Fax", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getFax().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getCustomerData().getBillingData().getFax().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(BillingDataElement, doc,
                "ns3:Email", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getEmail().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getCustomerData().getBillingData().getEmail().isNillable()));
    }
    private static void createAddress(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element addressElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns3:Address");
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns3:Street1", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet1());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(addressElement, doc,
                "ns3:Street2", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns3:City", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCity());
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns3:StateProvince", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStateProvince());
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns3:PostalCode", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getPostalCode());
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns3:CountryCode", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCountryCode());
    }
    private static void createReportingData(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element reportingDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns4:ReportingData");
        VelocityXMLUtil.addAttr(doc, reportingDataElement, "xmlns:ns4", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithText(reportingDataElement, doc, "ns4:Comment", authorizeTransaction.getTransaction().getReportingData().getComment());
        VelocityXMLUtil.generateSegmentsWithText(reportingDataElement, doc, "ns4:Description", authorizeTransaction.getTransaction().getReportingData().getDescription());
        VelocityXMLUtil.generateSegmentsWithText(reportingDataElement, doc, "ns4:Reference", authorizeTransaction.getTransaction().getReportingData().getReference());
    }
    private static void createTenderData(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element tenderDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns1:TenderData");
        // PaymentAccountDataToken
        Element paymentAccountDataTokenElement = VelocityXMLUtil.generateXMLElement(tenderDataElement, doc, "ns5:PaymentAccountDataToken");
        VelocityXMLUtil.addAttr(doc, paymentAccountDataTokenElement, "xmlns:ns5", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addAttr(doc, paymentAccountDataTokenElement, "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().isNillable()));
        VelocityXMLUtil.addTextToElement(paymentAccountDataTokenElement, doc, authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().getValue());
        // SecurePaymentAccountData
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(tenderDataElement, doc, "ns6:SecurePaymentAccountData", authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().getValue(), "xmlns:ns6", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        // EncryptionKeyId
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(tenderDataElement, doc, "ns7:EncryptionKeyId", authorizeTransaction.getTransaction().getTenderData().getEncryptionKeyId().getValue(), "xmlns:ns7", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        // SwipeStatus
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(tenderDataElement, doc, "ns8:SwipeStatus", authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().getValue(), "xmlns:ns8", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        // create CardSecurityData
        createCardSecurityData(tenderDataElement, doc, authorizeTransaction);
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(tenderDataElement, doc,
                "ns1:EBTVoucherApprovalCode", authorizeTransaction.getTransaction().getTenderData().getEbtVoucherApprovalCode().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getEbtVoucherApprovalCode().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(tenderDataElement, doc,
                "ns1:EBTVoucherNumber", authorizeTransaction.getTransaction().getTenderData().getEbtVoucherNumber().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getEbtVoucherNumber().isNillable()));
    }
    public static void createCardSecurityData(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element cardDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns1:CardSecurityData");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(cardDataElement, doc,
                "ns1:AVSData", authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(cardDataElement, doc,
                "ns1:CVData", authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(cardDataElement, doc,
                "ns1:KeySerialNumber", authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(cardDataElement, doc,
                "ns1:PIN", authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(cardDataElement, doc, "ns1:IdentificationInformation", authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().getValue());
    }
    public static void createTransactionData(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element transactionDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns1:TransactionData");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns9:Amount", authorizeTransaction.getTransaction().getTransactionData().getAmount(), "xmlns:ns9", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns10:CurrencyCode", authorizeTransaction.getTransaction().getTransactionData().getCurrencyCode(), "xmlns:ns10", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns11:TransactionDateTime", authorizeTransaction.getTransaction().getTransactionData().getTransactionDateTime(), "xmlns:ns11", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        // CampaignId
        Element CampaignIdElement = VelocityXMLUtil.generateXMLElement(transactionDataElement, doc, "ns12:CampaignId");
        VelocityXMLUtil.addAttr(doc, CampaignIdElement, "xmlns:ns12", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addAttr(doc, CampaignIdElement, "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTransactionData().getCampaignId().isNillable()));
        VelocityXMLUtil.addTextToElement(CampaignIdElement, doc, authorizeTransaction.getTransaction().getTransactionData().getCampaignId().getValue());
        // reference
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns13:Reference", authorizeTransaction.getTransaction().getTransactionData().getReference(), "xmlns:ns13", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:EmployeeId", authorizeTransaction.getTransaction().getTransactionData().getEmployeeId());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:EntryMode", authorizeTransaction.getTransaction().getTransactionData().getEntryMode());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:GoodsType", authorizeTransaction.getTransaction().getTransactionData().getGoodsType());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:IndustryType", authorizeTransaction.getTransaction().getTransactionData().getIndustryType());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:InvoiceNumber", authorizeTransaction.getTransaction().getTransactionData().getInvoiceNumber());
    }
}
