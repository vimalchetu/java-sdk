/**
 * 
 */
package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attributes for Transaction
 * 
 * @author Vimal Kumar
 * @date 12-March-2015
 */
public class Transaction {

	@SerializedName("$type")
	private String type;

	@SerializedName("ApplicationConfigurationData")
	private String applicationConfigurationData;

	@SerializedName("CustomerData")
	private CustomerData customerData;

	@SerializedName("ReportingData")
	private ReportingData reportingData;

	@SerializedName("TenderData")
	private TenderData tenderData;

	@SerializedName("TransactionData")
	private TransactionData transactionData;

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CustomerData getCustomerData() {
		return customerData;
	}

	public void setCustomerData(CustomerData customerData) {
		this.customerData = customerData;
	}

	public ReportingData getReportingData() {
		return reportingData;
	}

	public void setReportingData(ReportingData reportingData) {
		this.reportingData = reportingData;
	}

	public TenderData getTenderData() {
		return tenderData;
	}

	public void setTenderData(TenderData tenderData) {
		this.tenderData = tenderData;
	}

	public TransactionData getTransactionData() {
		return transactionData;
	}

	public void setTransactionData(TransactionData transactionData) {
		this.transactionData = transactionData;
	}

	public String getApplicationConfigurationData() {
		return applicationConfigurationData;
	}

	public void setApplicationConfigurationData(
			String applicationConfigurationData) {
		this.applicationConfigurationData = applicationConfigurationData;
	}

}
