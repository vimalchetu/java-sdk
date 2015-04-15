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
 * This class creates the request XML for Authorize transaction
 * 
 * @author Vimal Kumar
 * @date April 14, 2015
 */
public class AuthorizeTransactionXML {

    private static final Logger LOG = Logger.getLogger(AuthorizeTransactionXML.class);

    /**
     * This method generates the XML for Authorize request.
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
    public String authorizeXML(AuthorizeTransaction authorizeTransaction, String appProfileId, String merchantProfileId) throws VelocityException, VelocityIllegalArgumentException, VelocityNotFoundException, VelocityRestInvokeException {
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
        Element customerDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns2:CustomerData");
        VelocityXMLUtil.addAttr(doc, customerDataElement, "xmlns:ns2", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        // Billing Data
        createBillingData(customerDataElement, doc, authorizeTransaction);
        VelocityXMLUtil.generateSegmentsWithText(customerDataElement, doc, "ns2:CustomerId", "cust123x");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(customerDataElement, doc,
                "ns2:CustomerTaxId", authorizeTransaction.getTransaction().getCustomerData().getCustomerTaxId().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getCustomerData().getCustomerTaxId().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(customerDataElement, doc,
                "ns2:ShippingData", authorizeTransaction.getTransaction().getCustomerData().getShippingData().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getCustomerData().getShippingData().isNillable()));
    }
    private static void createBillingData(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element BillingDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns2:BillingData");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(BillingDataElement, doc,
                "ns2:Name", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getName().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getCustomerData().getBillingData().getName().isNillable()));
        // create address
        createAddress(BillingDataElement, doc, authorizeTransaction);
        VelocityXMLUtil.generateSegmentsWithText(BillingDataElement, doc, "ns2:BusinessName", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getBusinessName());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(BillingDataElement, doc,
                "ns2:Phone", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getPhone().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getCustomerData().getBillingData().getPhone().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(BillingDataElement, doc,
                "ns2:Fax", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getFax().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getCustomerData().getBillingData().getFax().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(BillingDataElement, doc,
                "ns2:Email", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getEmail().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getCustomerData().getBillingData().getEmail().isNillable()));
    }
    private static void createAddress(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element addressElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns2:Address");
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns2:Street1", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet1());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(addressElement, doc,
                "ns2:Street2", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns2:City", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCity());
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns2:StateProvince", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStateProvince());
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns2:PostalCode", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getPostalCode());
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns2:CountryCode", authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCountryCode());
    }
    private static void createReportingData(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element reportingDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns3:ReportingData");
        VelocityXMLUtil.addAttr(doc, reportingDataElement, "xmlns:ns3", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithText(reportingDataElement, doc, "ns3:Comment", authorizeTransaction.getTransaction().getReportingData().getComment());
        VelocityXMLUtil.generateSegmentsWithText(reportingDataElement, doc, "ns3:Description", authorizeTransaction.getTransaction().getReportingData().getDescription());
        VelocityXMLUtil.generateSegmentsWithText(reportingDataElement, doc, "ns3:Reference", authorizeTransaction.getTransaction().getReportingData().getReference());
    }
    private static void createTenderData(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element tenderDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns1:TenderData");
        // PaymentAccountDataToken
        Element paymentAccountDataTokenElement = VelocityXMLUtil.generateXMLElement(tenderDataElement, doc, "ns4:PaymentAccountDataToken");
        VelocityXMLUtil.addAttr(doc, paymentAccountDataTokenElement, "xmlns:ns4", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addAttr(doc, paymentAccountDataTokenElement, "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().isNillable()));
        VelocityXMLUtil.addTextToElement(paymentAccountDataTokenElement, doc, authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().getValue());
        // SecurePaymentAccountData
        Element securePaymentAccountDataElement = VelocityXMLUtil.generateXMLElement(tenderDataElement, doc, "ns5:SecurePaymentAccountData");
        VelocityXMLUtil.addAttr(doc, securePaymentAccountDataElement, "xmlns:ns5", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addAttr(doc, securePaymentAccountDataElement, "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().isNillable()));
        VelocityXMLUtil.addTextToElement(securePaymentAccountDataElement, doc, authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().getValue());
        // EncryptionKeyId
        Element encryptionKeyIdElement = VelocityXMLUtil.generateXMLElement(tenderDataElement, doc, "ns6:EncryptionKeyId");
        VelocityXMLUtil.addAttr(doc, encryptionKeyIdElement, "xmlns:ns6", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addAttr(doc, encryptionKeyIdElement, "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getEncryptionKeyId().isNillable()));
        VelocityXMLUtil.addTextToElement(encryptionKeyIdElement, doc, authorizeTransaction.getTransaction().getTenderData().getEncryptionKeyId().getValue());
        // SwipeStatus
        Element swipeStatusElement = VelocityXMLUtil.generateXMLElement(tenderDataElement, doc, "ns7:SwipeStatus");
        VelocityXMLUtil.addAttr(doc, swipeStatusElement, "xmlns:ns7", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addAttr(doc, swipeStatusElement, "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().isNillable()));
        VelocityXMLUtil.addTextToElement(swipeStatusElement, doc, authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().getValue());
        // create CardData
        createCardData(tenderDataElement, doc, authorizeTransaction);
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(tenderDataElement, doc,
                "ns1:EcommerceSecurityData", authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().isNillable()));
    }
    public static void createCardData(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element cardDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns1:CardData");
        VelocityXMLUtil.generateSegmentsWithText(cardDataElement, doc, "ns1:CardType", authorizeTransaction.getTransaction().getTenderData().getCardData().getCardType());
        VelocityXMLUtil.generateSegmentsWithText(cardDataElement, doc, "ns1:PAN", authorizeTransaction.getTransaction().getTenderData().getCardData().getPan());
        VelocityXMLUtil.generateSegmentsWithText(cardDataElement, doc, "ns1:Expire", authorizeTransaction.getTransaction().getTenderData().getCardData().getExpiryDate());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(cardDataElement, doc,
                "ns1:Track1Data", authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().isNillable()));
    }
    public static void createTransactionData(Element element, Document doc, AuthorizeTransaction authorizeTransaction) {
        Element transactionDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns1:TransactionData");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns8:Amount", authorizeTransaction.getTransaction().getTransactionData().getAmount(), "xmlns:ns8", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns9:CurrencyCode", authorizeTransaction.getTransaction().getTransactionData().getCurrencyCode(), "xmlns:ns9", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns10:TransactionDateTime", authorizeTransaction.getTransaction().getTransactionData().getTransactionDateTime(), "xmlns:ns10", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        // CampaignId
        Element CampaignIdElement = VelocityXMLUtil.generateXMLElement(transactionDataElement, doc, "ns11:CampaignId");
        VelocityXMLUtil.addAttr(doc, CampaignIdElement, "xmlns:ns11", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addAttr(doc, CampaignIdElement, "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTransactionData().getCampaignId().isNillable()));
        VelocityXMLUtil.addTextToElement(CampaignIdElement, doc, authorizeTransaction.getTransaction().getTransactionData().getCampaignId().getValue());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns12:Reference", authorizeTransaction.getTransaction().getTransactionData().getReference(), "xmlns:ns12", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:AccountType", authorizeTransaction.getTransaction().getTransactionData().getAccountType());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:ApprovalCode", authorizeTransaction.getTransaction().getTransactionData().getApprovalCode().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTransactionData().getApprovalCode().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:CashBackAmount", authorizeTransaction.getTransaction().getTransactionData().getCashBackAmount());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:CustomerPresent", authorizeTransaction.getTransaction().getTransactionData().getCustomerPresent());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:EmployeeId", authorizeTransaction.getTransaction().getTransactionData().getEmployeeId());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:EntryMode", authorizeTransaction.getTransaction().getTransactionData().getEntryMode());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:GoodsType", authorizeTransaction.getTransaction().getTransactionData().getGoodsType());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:IndustryType", authorizeTransaction.getTransaction().getTransactionData().getIndustryType());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:InternetTransactionData", authorizeTransaction.getTransaction().getTransactionData().getInternetTransactionData().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTransactionData().getInternetTransactionData().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:InvoiceNumber", authorizeTransaction.getTransaction().getTransactionData().getInvoiceNumber());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:OrderNumber", authorizeTransaction.getTransaction().getTransactionData().getOrderNumber());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:IsPartialShipment", String.valueOf(authorizeTransaction.getTransaction().getTransactionData().isPartialShipment()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:SignatureCaptured", String.valueOf(authorizeTransaction.getTransaction().getTransactionData().isSignatureCaptured()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:FeeAmount", authorizeTransaction.getTransaction().getTransactionData().getFeeAmount());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:TerminalId", authorizeTransaction.getTransaction().getTransactionData().getTerminalId().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTransactionData().getTerminalId().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:LaneId", authorizeTransaction.getTransaction().getTransactionData().getLaneId().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTransactionData().getLaneId().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:TipAmount", authorizeTransaction.getTransaction().getTransactionData().getTipAmount());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:BatchAssignment", authorizeTransaction.getTransaction().getTransactionData().getBatchAssignment().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTransactionData().getBatchAssignment().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:PartialApprovalCapable", authorizeTransaction.getTransaction().getTransactionData().getPartialApprovalCapable());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:ScoreThreshold", authorizeTransaction.getTransaction().getTransactionData().getScoreThreshold().getValue(),
                "i:nil", String.valueOf(authorizeTransaction.getTransaction().getTransactionData().getScoreThreshold().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:IsQuasiCash", String.valueOf(authorizeTransaction.getTransaction().getTransactionData().isQuasiCash()));
    }
}
