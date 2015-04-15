/**
 * 
 */
package com.velocity.models.request.verify;

/**
 * This model class holds the data for TransactionData
 * 
 * @author anitk
 * @date 13-January-2015
 */
public class TransactionData {

	private String amount;

	private String currencyCode;

	private String transactiondateTime;

	private String customerPresent;

	private String employeeId;

	private String entryMode;

	private String industryType;

	private String accountType;

	private String invoiceNumber;

	private String orderNumber;

	private String tipAmount;

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getTipAmount() {
		return tipAmount;
	}

	public void setTipAmount(String tipAmount) {
		this.tipAmount = tipAmount;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getTransactiondateTime() {
		return transactiondateTime;
	}

	public void setTransactiondateTime(String transactiondateTime) {
		this.transactiondateTime = transactiondateTime;
	}

	public String getCustomerPresent() {
		return customerPresent;
	}

	public void setCustomerPresent(String customerPresent) {
		this.customerPresent = customerPresent;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEntryMode() {
		return entryMode;
	}

	public void setEntryMode(String entryMode) {
		this.entryMode = entryMode;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

}
