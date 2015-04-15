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
import com.velocity.model.request.AuthorizeAndCaptureTransaction;

/**
 * This class creates the request XML for P2PEAuthorizeAndCapture request
 * 
 * @author Vimal Kumar
 * @date April 14, 2015
 */
public class P2PEAuthorizeAndCaptureRequestXML {

    private static final Logger LOG = Logger.getLogger(P2PEAuthorizeAndCaptureRequestXML.class);

    /**
     * This method generates the input XML for P2PEAuthorizeAndCapture request.
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
    public String p2peAuthorizAndCaptureXML(AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction, String appProfileId, String merchantProfileId) throws VelocityException, VelocityIllegalArgumentException, VelocityNotFoundException, VelocityRestInvokeException {
        /* Creating the instance for DOM parsing */
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try{
            docBuilder = docFactory.newDocumentBuilder();
            // root elements
            Document doc = docBuilder.newDocument();
            createAuthorizeAndCaptureTransaction(doc, authorizeAndCaptureTransaction, appProfileId, merchantProfileId);
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
    public static void createAuthorizeAndCaptureTransaction(Document doc, AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction, String appProfileId, String merchantProfileId) {
        Element ACTElement = VelocityXMLUtil.rootElement(doc, "AuthorizeAndCaptureTransaction");
        VelocityXMLUtil.addAttr(doc, ACTElement, "xmlns:i", "http://www.w3.org/2001/XMLSchema-instance");
        VelocityXMLUtil.addAttr(doc, ACTElement, "xmlns", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest");
        VelocityXMLUtil.addAttr(doc, ACTElement, "i:type", "AuthorizeAndCaptureTransaction");
        VelocityXMLUtil.generateSegmentsWithText(ACTElement, doc, "ApplicationProfileId", appProfileId);
        VelocityXMLUtil.generateSegmentsWithText(ACTElement, doc, "MerchantProfileId", merchantProfileId);
        // Transaction
        createTransaction(ACTElement, doc, authorizeAndCaptureTransaction);
    }
    public static void createTransaction(Element element, Document doc, AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction) {
        Element transactionElement = VelocityXMLUtil.generateXMLElement(element, doc, "Transaction");
        VelocityXMLUtil.addAttr(doc, transactionElement, "xmlns:ns1", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard");
        VelocityXMLUtil.addAttr(doc, transactionElement, "i:type", "ns1:" + authorizeAndCaptureTransaction.getTransaction().getType());
        // Create Customer Data
        createCustomerData(transactionElement, doc, authorizeAndCaptureTransaction);
        // create Reporting Data
        createReportingData(transactionElement, doc, authorizeAndCaptureTransaction);
        // create Tender data
        createTenderData(transactionElement, doc, authorizeAndCaptureTransaction);
        // create TransactionData
        createTransactionData(transactionElement, doc, authorizeAndCaptureTransaction);
    }
    private static void createCustomerData(Element element, Document doc, AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction) {
        Element customerDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns2:CustomerData");
        VelocityXMLUtil.addAttr(doc, customerDataElement, "xmlns:ns2", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        // Billing Data
        createBillingData(customerDataElement, doc, authorizeAndCaptureTransaction);
        VelocityXMLUtil.generateSegmentsWithText(customerDataElement, doc, "ns2:CustomerId", authorizeAndCaptureTransaction.getTransaction().getCustomerData().getCustomerId());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(customerDataElement, doc,
                "ns2:CustomerTaxId", authorizeAndCaptureTransaction.getTransaction().getCustomerData().getCustomerTaxId().getValue(),
                "i:nil", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getCustomerData().getCustomerTaxId().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(customerDataElement, doc,
                "ns2:ShippingData", authorizeAndCaptureTransaction.getTransaction().getCustomerData().getShippingData().getValue(),
                "i:nil", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getCustomerData().getShippingData().isNillable()));
    }
    private static void createBillingData(Element element, Document doc, AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction) {
        Element BillingDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns2:BillingData");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(BillingDataElement, doc,
                "ns2:Name", authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getName().getValue(),
                "i:nil", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getName().isNillable()));
        // create address
        createAddress(BillingDataElement, doc, authorizeAndCaptureTransaction);
        VelocityXMLUtil.generateSegmentsWithText(BillingDataElement, doc, "ns2:BusinessName", authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getBusinessName());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(BillingDataElement, doc,
                "ns2:Phone", authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getPhone().getValue(),
                "i:nil", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getPhone().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(BillingDataElement, doc,
                "ns2:Fax", authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getFax().getValue(),
                "i:nil", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getFax().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(BillingDataElement, doc,
                "ns2:Email", authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getEmail().getValue(),
                "i:nil", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getEmail().isNillable()));
    }
    private static void createAddress(Element element, Document doc, AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction) {
        Element addressElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns2:Address");
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns2:Street1", authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet1());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(addressElement, doc,
                "ns2:Street2", authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().getValue(),
                "i:nil", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns2:City", authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCity());
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns2:StateProvince", authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStateProvince());
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns2:PostalCode", authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getPostalCode());
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns2:CountryCode", authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCountryCode());
    }
    private static void createReportingData(Element element, Document doc, AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction) {
        Element reportingDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns3:ReportingData");
        VelocityXMLUtil.addAttr(doc, reportingDataElement, "xmlns:ns3", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithText(reportingDataElement, doc, "ns3:Comment", authorizeAndCaptureTransaction.getTransaction().getReportingData().getComment());
        VelocityXMLUtil.generateSegmentsWithText(reportingDataElement, doc, "ns3:Description", authorizeAndCaptureTransaction.getTransaction().getReportingData().getDescription());
        VelocityXMLUtil.generateSegmentsWithText(reportingDataElement, doc, "ns3:Reference", authorizeAndCaptureTransaction.getTransaction().getReportingData().getReference());
    }
    private static void createTenderData(Element element, Document doc, AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction) {
        Element tenderDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns1:TenderData");
        // PaymentAccountDataToken
        Element paymentAccountDataTokenElement = VelocityXMLUtil.generateXMLElement(tenderDataElement, doc, "ns4:PaymentAccountDataToken");
        VelocityXMLUtil.addAttr(doc, paymentAccountDataTokenElement, "xmlns:ns4", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addAttr(doc, paymentAccountDataTokenElement, "i:nil", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().isNillable()));
        VelocityXMLUtil.addTextToElement(paymentAccountDataTokenElement, doc, authorizeAndCaptureTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().getValue());
        // SecurePaymentAccountData
        Element securePaymentAccountDataElement = VelocityXMLUtil.generateXMLElement(tenderDataElement, doc, "ns5:SecurePaymentAccountData");
        VelocityXMLUtil.addAttr(doc, securePaymentAccountDataElement, "xmlns:ns5", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addTextToElement(securePaymentAccountDataElement, doc, authorizeAndCaptureTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().getValue());
        // EncryptionKeyId
        Element encryptionKeyIdElement = VelocityXMLUtil.generateXMLElement(tenderDataElement, doc, "ns6:EncryptionKeyId");
        VelocityXMLUtil.addAttr(doc, encryptionKeyIdElement, "xmlns:ns6", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addTextToElement(encryptionKeyIdElement, doc, authorizeAndCaptureTransaction.getTransaction().getTenderData().getEncryptionKeyId().getValue());
        // SwipeStatus
        Element swipeStatusElement = VelocityXMLUtil.generateXMLElement(tenderDataElement, doc, "ns7:SwipeStatus");
        VelocityXMLUtil.addAttr(doc, swipeStatusElement, "xmlns:ns7", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addTextToElement(swipeStatusElement, doc, authorizeAndCaptureTransaction.getTransaction().getTenderData().getSwipeStatus().getValue());
        // create CardSecurityData
        createCardSecurityData(tenderDataElement, doc, authorizeAndCaptureTransaction);
    }
    public static void createCardSecurityData(Element element, Document doc, AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction) {
        Element cardSecurityDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns1:CardSecurityData");
        VelocityXMLUtil.generateSegmentsWithText(cardSecurityDataElement, doc, "ns1:IdentificationInformation", authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().getValue());
    }
    public static void createTransactionData(Element element, Document doc, AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction) {
        Element transactionDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns1:TransactionData");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns8:Amount", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getAmount(), "xmlns:ns8", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns9:CurrencyCode", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getCurrencyCode(), "xmlns:ns9", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns10:TransactionDateTime", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getTransactionDateTime(), "xmlns:ns10", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        // CampaignId
        Element CampaignIdElement = VelocityXMLUtil.generateXMLElement(transactionDataElement, doc, "ns11:CampaignId");
        VelocityXMLUtil.addAttr(doc, CampaignIdElement, "xmlns:ns11", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addAttr(doc, CampaignIdElement, "i:nil", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getTransactionData().getCampaignId().isNillable()));
        VelocityXMLUtil.addTextToElement(CampaignIdElement, doc, authorizeAndCaptureTransaction.getTransaction().getTransactionData().getCampaignId().getValue());
        // reference
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns12:Reference", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getReference(), "xmlns:ns12", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:AccountType", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getAccountType());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:ApprovalCode", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getApprovalCode().getValue(),
                "i:nil", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getTransactionData().getApprovalCode().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:CashBackAmount", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getCashBackAmount());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:CustomerPresent", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getCustomerPresent());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:EmployeeId", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getEmployeeId());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:EntryMode", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getEntryMode());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:GoodsType", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getGoodsType());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:IndustryType", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getIndustryType());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:InternetTransactionData", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getInternetTransactionData().getValue(),
                "i:nil", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getTransactionData().getInternetTransactionData().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:InvoiceNumber", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getInvoiceNumber());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:OrderNumber", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getOrderNumber());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:IsPartialShipment", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getTransactionData().isPartialShipment()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:SignatureCaptured", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getTransactionData().isSignatureCaptured()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:FeeAmount", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getFeeAmount());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:TerminalId", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getTerminalId().getValue(),
                "i:nil", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getTransactionData().getTerminalId().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:LaneId", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getLaneId().getValue(),
                "i:nil", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getTransactionData().getLaneId().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:TipAmount", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getTipAmount());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:BatchAssignment", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getBatchAssignment().getValue(),
                "i:nil", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getTransactionData().getBatchAssignment().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:PartialApprovalCapable", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getPartialApprovalCapable());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:ScoreThreshold", authorizeAndCaptureTransaction.getTransaction().getTransactionData().getScoreThreshold().getValue(),
                "i:nil", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getTransactionData().getScoreThreshold().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:IsQuasiCash", String.valueOf(authorizeAndCaptureTransaction.getTransaction().getTransactionData().isQuasiCash()));
    }
}
