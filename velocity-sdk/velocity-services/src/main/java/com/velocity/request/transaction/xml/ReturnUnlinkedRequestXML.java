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
import com.velocity.model.request.ReturnTransaction;

/**
 * This class creates the request XML for ReturnUnlinked transaction
 * 
 * @author Vimal Kumar
 * @date April 14, 2015
 */
public class ReturnUnlinkedRequestXML {

    private static final Logger LOG = Logger.getLogger(ReturnUnlinkedRequestXML.class);

    /**
     * @param returnTransaction - holds the value for the type Return Transaction
     * @param appProfileId - Application profile Id for transaction initiation.
     * @param merchantProfileId - Merchant profile Id for transaction initiation.
     * @return String - Returns the instance of the type String.
     * @throws VelocityException - Exception for Velocity transaction
     * @throws VelocityIllegalArgumentException - Thrown when illegal argument supplied.
     * @throws VelocityNotFoundException - Thrown when some resource not found.
     * @throws VelocityRestInvokeException - Thrown when exception occurs at invoking REST API .
     */
    public String returnUnlinkedXML(ReturnTransaction returnTransaction, String appProfileId, String merchantProfileId) throws VelocityException, VelocityIllegalArgumentException, VelocityNotFoundException, VelocityRestInvokeException {
        /* Creating the instance for DOM parsing */
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try{
            docBuilder = docFactory.newDocumentBuilder();
            // root elements
            Document doc = docBuilder.newDocument();
            createReturnTransaction(doc, returnTransaction, appProfileId, merchantProfileId);
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
    public static void createReturnTransaction(Document doc, ReturnTransaction returnTransaction, String appProfileId, String merchantProfileId) {
        Element returnTransactionElement = VelocityXMLUtil.rootElement(doc, "ReturnTransaction");
        VelocityXMLUtil.addAttr(doc, returnTransactionElement, "xmlns:i", "http://www.w3.org/2001/XMLSchema-instance");
        VelocityXMLUtil.addAttr(doc, returnTransactionElement, "xmlns", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest");
        VelocityXMLUtil.addAttr(doc, returnTransactionElement, "i:type", "ReturnTransaction");
        VelocityXMLUtil.generateSegmentsWithText(returnTransactionElement, doc, "ApplicationProfileId", appProfileId);
        Element batchIdsElement = VelocityXMLUtil.generateXMLElement(returnTransactionElement, doc, "BatchIds");
        VelocityXMLUtil.addAttr(doc, batchIdsElement, "xmlns:d2p1", "http://schemas.microsoft.com/2003/10/Serialization/Arrays");
        VelocityXMLUtil.addAttr(doc, batchIdsElement, "i:nil", String.valueOf(returnTransaction.getBatchIds().isNillable()));
        VelocityXMLUtil.addTextToElement(batchIdsElement, doc, returnTransaction.getBatchIds().getValue());
        VelocityXMLUtil.generateSegmentsWithText(returnTransactionElement, doc, "MerchantProfileId", merchantProfileId);
        // Transaction
        createTransaction(returnTransactionElement, doc, returnTransaction);
    }
    public static void createTransaction(Element element, Document doc, ReturnTransaction returnTransaction) {
        Element transactionElement = VelocityXMLUtil.generateXMLElement(element, doc, "Transaction");
        VelocityXMLUtil.addAttr(doc, transactionElement, "xmlns:ns1", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard");
        VelocityXMLUtil.addAttr(doc, transactionElement, "i:type", "ns1:BankcardTransaction");
        // Create Customer Data
        createCustomerData(transactionElement, doc, returnTransaction);
        // create Reporting Data
        createReportingData(transactionElement, doc, returnTransaction);
        // create Tender data
        createTenderData(transactionElement, doc, returnTransaction);
        // create TransactionData
        createTransactionData(transactionElement, doc, returnTransaction);
    }
    public static void createCustomerData(Element element, Document doc, ReturnTransaction returnTransaction) {
        Element customerDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns2:CustomerData");
        VelocityXMLUtil.addAttr(doc, customerDataElement, "xmlns:ns2", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        // Billing Data
        createBillingData(customerDataElement, doc, returnTransaction);
        VelocityXMLUtil.generateSegmentsWithText(customerDataElement, doc, "ns2:CustomerId", returnTransaction.getTransaction().getCustomerData().getCustomerId());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(customerDataElement, doc,
                "ns2:CustomerTaxId", returnTransaction.getTransaction().getCustomerData().getCustomerTaxId().getValue(),
                "i:nil", String.valueOf(returnTransaction.getTransaction().getCustomerData().getCustomerTaxId().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(customerDataElement, doc,
                "ns2:ShippingData", returnTransaction.getTransaction().getCustomerData().getShippingData().getValue(),
                "i:nil", String.valueOf(returnTransaction.getTransaction().getCustomerData().getShippingData().isNillable()));
    }
    private static void createBillingData(Element element, Document doc, ReturnTransaction returnTransaction) {
        Element BillingDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns2:BillingData");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(BillingDataElement, doc,
                "ns2:Name", returnTransaction.getTransaction().getCustomerData().getBillingData().getName().getValue(),
                "i:nil", String.valueOf(returnTransaction.getTransaction().getCustomerData().getBillingData().getName().isNillable()));
        // create address
        createAddress(BillingDataElement, doc, returnTransaction);
        VelocityXMLUtil.generateSegmentsWithText(BillingDataElement, doc, "ns2:BusinessName", returnTransaction.getTransaction().getCustomerData().getBillingData().getBusinessName());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(BillingDataElement, doc,
                "ns2:Phone", returnTransaction.getTransaction().getCustomerData().getBillingData().getPhone().getValue(),
                "i:nil", String.valueOf(returnTransaction.getTransaction().getCustomerData().getBillingData().getPhone().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(BillingDataElement, doc,
                "ns2:Fax", returnTransaction.getTransaction().getCustomerData().getBillingData().getFax().getValue(),
                "i:nil", String.valueOf(returnTransaction.getTransaction().getCustomerData().getBillingData().getFax().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(BillingDataElement, doc,
                "ns2:Email", returnTransaction.getTransaction().getCustomerData().getBillingData().getEmail().getValue(),
                "i:nil", String.valueOf(returnTransaction.getTransaction().getCustomerData().getBillingData().getEmail().isNillable()));
    }
    private static void createAddress(Element element, Document doc, ReturnTransaction returnTransaction) {
        Element addressElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns2:Address");
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns2:Street1", returnTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet1());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(addressElement, doc,
                "ns2:Street2", returnTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().getValue(),
                "i:nil", String.valueOf(returnTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns2:City", returnTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCity());
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns2:StateProvince", returnTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStateProvince());
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns2:PostalCode", returnTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getPostalCode());
        VelocityXMLUtil.generateSegmentsWithText(addressElement, doc, "ns2:CountryCode", returnTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCountryCode());
    }
    private static void createReportingData(Element element, Document doc, ReturnTransaction returnTransaction) {
        Element reportingDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns3:ReportingData");
        VelocityXMLUtil.addAttr(doc, reportingDataElement, "xmlns:ns3", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithText(reportingDataElement, doc, "ns3:Comment", returnTransaction.getTransaction().getReportingData().getComment());
        VelocityXMLUtil.generateSegmentsWithText(reportingDataElement, doc, "ns3:Description", returnTransaction.getTransaction().getReportingData().getDescription());
        VelocityXMLUtil.generateSegmentsWithText(reportingDataElement, doc, "ns3:Reference", returnTransaction.getTransaction().getReportingData().getReference());
    }
    private static void createTenderData(Element element, Document doc, ReturnTransaction returnTransaction) {
        Element tenderDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns1:TenderData");
        // PaymentAccountDataToken
        Element paymentAccountDataTokenElement = VelocityXMLUtil.generateXMLElement(tenderDataElement, doc, "ns4:PaymentAccountDataToken");
        VelocityXMLUtil.addAttr(doc, paymentAccountDataTokenElement, "xmlns:ns4", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addAttr(doc, paymentAccountDataTokenElement, "i:nil", String.valueOf(returnTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().isNillable()));
        VelocityXMLUtil.addTextToElement(paymentAccountDataTokenElement, doc, returnTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().getValue());
        // SecurePaymentAccountData
        Element securePaymentAccountDataElement = VelocityXMLUtil.generateXMLElement(tenderDataElement, doc, "ns5:SecurePaymentAccountData");
        VelocityXMLUtil.addAttr(doc, securePaymentAccountDataElement, "xmlns:ns5", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addAttr(doc, securePaymentAccountDataElement, "i:nil", String.valueOf(returnTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().isNillable()));
        VelocityXMLUtil.addTextToElement(securePaymentAccountDataElement, doc, returnTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().getValue());
        // EncryptionKeyId
        Element encryptionKeyIdElement = VelocityXMLUtil.generateXMLElement(tenderDataElement, doc, "ns6:EncryptionKeyId");
        VelocityXMLUtil.addAttr(doc, encryptionKeyIdElement, "xmlns:ns6", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addAttr(doc, encryptionKeyIdElement, "i:nil", String.valueOf(returnTransaction.getTransaction().getTenderData().getEncryptionKeyId().isNillable()));
        VelocityXMLUtil.addTextToElement(encryptionKeyIdElement, doc, returnTransaction.getTransaction().getTenderData().getEncryptionKeyId().getValue());
        // SwipeStatus
        Element swipeStatusElement = VelocityXMLUtil.generateXMLElement(tenderDataElement, doc, "ns7:SwipeStatus");
        VelocityXMLUtil.addAttr(doc, swipeStatusElement, "xmlns:ns7", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addAttr(doc, swipeStatusElement, "i:nil", String.valueOf(returnTransaction.getTransaction().getTenderData().getSwipeStatus().isNillable()));
        VelocityXMLUtil.addTextToElement(swipeStatusElement, doc, returnTransaction.getTransaction().getTenderData().getSwipeStatus().getValue());
        // create CardData
        createCardData(tenderDataElement, doc, returnTransaction);
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(tenderDataElement, doc,
                "ns1:EcommerceSecurityData", returnTransaction.getTransaction().getTenderData().getEcommerceSecurityData().getValue(),
                "i:nil", String.valueOf(returnTransaction.getTransaction().getTenderData().getEcommerceSecurityData().isNillable()));
    }
    public static void createCardData(Element element, Document doc, ReturnTransaction returnTransaction) {
        Element cardDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns1:CardData");
        VelocityXMLUtil.generateSegmentsWithText(cardDataElement, doc, "ns1:CardType", returnTransaction.getTransaction().getTenderData().getCardData().getCardType());
        VelocityXMLUtil.generateSegmentsWithText(cardDataElement, doc, "ns1:PAN", returnTransaction.getTransaction().getTenderData().getCardData().getPan());
        VelocityXMLUtil.generateSegmentsWithText(cardDataElement, doc, "ns1:Expire", returnTransaction.getTransaction().getTenderData().getCardData().getExpiryDate());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(cardDataElement, doc,
                "ns1:Track1Data", returnTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().getValue(),
                "i:nil", String.valueOf(returnTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().isNillable()));
    }
    public static void createTransactionData(Element element, Document doc, ReturnTransaction returnTransaction) {
        Element transactionDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "ns1:TransactionData");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns8:Amount", returnTransaction.getTransaction().getTransactionData().getAmount(), "xmlns:ns8", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns9:CurrencyCode", returnTransaction.getTransaction().getTransactionData().getCurrencyCode(), "xmlns:ns9", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns10:TransactionDateTime", returnTransaction.getTransaction().getTransactionData().getTransactionDateTime(), "xmlns:ns10", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        // CampaignId
        Element CampaignIdElement = VelocityXMLUtil.generateXMLElement(transactionDataElement, doc, "ns11:CampaignId");
        VelocityXMLUtil.addAttr(doc, CampaignIdElement, "xmlns:ns11", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.addAttr(doc, CampaignIdElement, "i:nil", String.valueOf(returnTransaction.getTransaction().getTransactionData().getCampaignId().isNillable()));
        VelocityXMLUtil.addTextToElement(CampaignIdElement, doc, returnTransaction.getTransaction().getTransactionData().getCampaignId().getValue());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc, "ns12:Reference", returnTransaction.getTransaction().getTransactionData().getReference(), "xmlns:ns12", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:AccountType", returnTransaction.getTransaction().getTransactionData().getAccountType());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:ApprovalCode", returnTransaction.getTransaction().getTransactionData().getApprovalCode().getValue(),
                "i:nil", String.valueOf(returnTransaction.getTransaction().getTransactionData().getApprovalCode().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:CashBackAmount", returnTransaction.getTransaction().getTransactionData().getCashBackAmount());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:CustomerPresent", returnTransaction.getTransaction().getTransactionData().getCustomerPresent());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:EmployeeId", returnTransaction.getTransaction().getTransactionData().getEmployeeId());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:EntryMode", returnTransaction.getTransaction().getTransactionData().getEntryMode());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:GoodsType", returnTransaction.getTransaction().getTransactionData().getGoodsType());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:IndustryType", returnTransaction.getTransaction().getTransactionData().getIndustryType());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:InternetTransactionData", returnTransaction.getTransaction().getTransactionData().getInternetTransactionData().getValue(),
                "i:nil", String.valueOf(returnTransaction.getTransaction().getTransactionData().getInternetTransactionData().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:InvoiceNumber", returnTransaction.getTransaction().getTransactionData().getInvoiceNumber());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:OrderNumber", returnTransaction.getTransaction().getTransactionData().getOrderNumber());
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:IsPartialShipment", String.valueOf(returnTransaction.getTransaction().getTransactionData().isPartialShipment()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:SignatureCaptured", String.valueOf(returnTransaction.getTransaction().getTransactionData().isSignatureCaptured()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:FeeAmount", returnTransaction.getTransaction().getTransactionData().getFeeAmount());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:TerminalId", returnTransaction.getTransaction().getTransactionData().getTerminalId().getValue(),
                "i:nil", String.valueOf(returnTransaction.getTransaction().getTransactionData().getTerminalId().isNillable()));
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:LaneId", returnTransaction.getTransaction().getTransactionData().getLaneId().getValue(),
                "i:nil", String.valueOf(returnTransaction.getTransaction().getTransactionData().getLaneId().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:TipAmount", returnTransaction.getTransaction().getTransactionData().getTipAmount());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:BatchAssignment", returnTransaction.getTransaction().getTransactionData().getBatchAssignment().getValue(),
                "i:nil", String.valueOf(returnTransaction.getTransaction().getTransactionData().getBatchAssignment().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:PartialApprovalCapable", returnTransaction.getTransaction().getTransactionData().getPartialApprovalCapable());
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(transactionDataElement, doc,
                "ns1:ScoreThreshold", returnTransaction.getTransaction().getTransactionData().getScoreThreshold().getValue(),
                "i:nil", String.valueOf(returnTransaction.getTransaction().getTransactionData().getScoreThreshold().isNillable()));
        VelocityXMLUtil.generateSegmentsWithText(transactionDataElement, doc, "ns1:IsQuasiCash", String.valueOf(returnTransaction.getTransaction().getTransactionData().isQuasiCash()));
    }
}
