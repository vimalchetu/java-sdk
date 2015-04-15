package com.velocity.model.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class defines the attributes for processing the response XML for
 * CaptureAll transaction.
 * 
 * @author Vimal Kumar
 * @date 14-04-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "Response", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
public class Response {

	@XmlElement(name = "Status", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected String status;

	@XmlElement(name = "StatusCode", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected String statusCode;

	@XmlElement(name = "StatusMessage", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected String statusMessage;

	@XmlElement(name = "TransactionId", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected String transactionId;

	@XmlElement(name = "OriginatorTransactionId", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected String originatorTransactionId;

	@XmlElement(name = "ServiceTransactionDateTime", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected String serviceTransactionDateTime;

	@XmlElement(name = "Addendum", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected String addendum;

	@XmlElement(name = "CaptureState", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected String captureState;

	@XmlElement(name = "TransactionState", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected String transactionState;

	@XmlElement(name = "IsAcknowledged", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected String isAcknowledged;

	@XmlElement(name = "Reference", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected String reference;

	@XmlElement(name = "BatchId", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String batchId;

	@XmlElement(name = "IndustryType", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String industryType;

	@XmlElement(name = "TransactionSummaryData", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected TransactionSummaryData transactionSummaryData;

	@XmlElement(name = "PrepaidCard", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String prepaidCard;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOriginatorTransactionId() {
		return originatorTransactionId;
	}

	public void setOriginatorTransactionId(String originatorTransactionId) {
		this.originatorTransactionId = originatorTransactionId;
	}

	public String getServiceTransactionDateTime() {
		return serviceTransactionDateTime;
	}

	public void setServiceTransactionDateTime(String serviceTransactionDateTime) {
		this.serviceTransactionDateTime = serviceTransactionDateTime;
	}

	public String getAddendum() {
		return addendum;
	}

	public void setAddendum(String addendum) {
		this.addendum = addendum;
	}

	public String getCaptureState() {
		return captureState;
	}

	public void setCaptureState(String captureState) {
		this.captureState = captureState;
	}

	public String getTransactionState() {
		return transactionState;
	}

	public void setTransactionState(String transactionState) {
		this.transactionState = transactionState;
	}

	public String getIsAcknowledged() {
		return isAcknowledged;
	}

	public void setIsAcknowledged(String isAcknowledged) {
		this.isAcknowledged = isAcknowledged;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getPrepaidCard() {
		return prepaidCard;
	}

	public void setPrepaidCard(String prepaidCard) {
		this.prepaidCard = prepaidCard;
	}

	public TransactionSummaryData getTransactionSummaryData() {
		return transactionSummaryData;
	}

	public void setTransactionSummaryData(
			TransactionSummaryData transactionSummaryData) {
		this.transactionSummaryData = transactionSummaryData;
	}

}
