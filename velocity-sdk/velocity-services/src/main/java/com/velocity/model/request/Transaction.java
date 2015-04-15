package com.velocity.model.request;

import com.velocity.enums.VelocityEnums;

/**
 * This class defines the attributes for Transaction
 * 
 * @author Vimal Kumar
 * @date 30-December-2014
 */
public class Transaction {

    private VelocityEnums type;
    private CustomerData customerData;
    private ReportingData reportingData;
    private TenderData tenderData;
    private TransactionData transactionData;
    private RelayResponseUrl relayResponseUrl;

    public VelocityEnums getType() {
        return type;
    }
    public void setType(VelocityEnums type) {
        this.type = type;
    }
    public CustomerData getCustomerData() {
        if(customerData == null){
            customerData = new CustomerData();
        }
        return customerData;
    }
    public void setCustomerData(CustomerData customerData) {
        this.customerData = customerData;
    }
    public ReportingData getReportingData() {
        if(reportingData == null){
            reportingData = new ReportingData();
        }
        return reportingData;
    }
    public void setReportingData(ReportingData reportingData) {
        this.reportingData = reportingData;
    }
    public TenderData getTenderData() {
        if(tenderData == null){
            tenderData = new TenderData();
        }
        return tenderData;
    }
    public void setTenderData(TenderData tenderData) {
        this.tenderData = tenderData;
    }
    public TransactionData getTransactionData() {
        if(transactionData == null){
            transactionData = new TransactionData();
        }
        return transactionData;
    }
    public void setTransactionData(TransactionData transactionData) {
        this.transactionData = transactionData;
    }
    public RelayResponseUrl getRelayResponseUrl() {
        if(relayResponseUrl == null){
            relayResponseUrl = new RelayResponseUrl();
        }
        return relayResponseUrl;
    }
    public void setRelayResponseUrl(RelayResponseUrl relayResponseUrl) {
        this.relayResponseUrl = relayResponseUrl;
    }
}
