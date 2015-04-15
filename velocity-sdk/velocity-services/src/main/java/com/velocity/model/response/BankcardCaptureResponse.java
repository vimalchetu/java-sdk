package com.velocity.model.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.velocity.model.request.capture.Addendum;
import com.velocity.model.request.capture.Reference;

/**
 * This class defines the attributes for processing the response XML for capture
 * transaction.
 * 
 * @author Vimal Kumar
 * @date 07-January-2015
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "BankcardCaptureResponse", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
public class BankcardCaptureResponse {

	/* Xml attributes for CaptureResponse. */
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

	@XmlElement(name = "ServiceTransactionId", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected String serviceTransactionId;

	@XmlElement(name = "ServiceTransactionDateTime", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions", required = true)
	protected ServiceTransactionDateTime serviceTransactionDateTime;

	@XmlElement(name = "date", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String date;

	@XmlElement(name = "time", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String time;

	@XmlElement(name = "timezone", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String timezone;

	@XmlElement(name = "Addendum", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected Addendum addendum;

	@XmlElement(name = "CaptureState", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected String captureState;

	@XmlElement(name = "TransactionState", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected String transactionState;

	@XmlElement(name = "IsAcknowledged", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected boolean acknowledged;

	@XmlElement(name = "Reference", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected Reference reference;

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

	public String getServiceTransactionId() {
		return serviceTransactionId;
	}

	public void setServiceTransactionId(String serviceTransactionId) {
		this.serviceTransactionId = serviceTransactionId;
	}

	public ServiceTransactionDateTime getServiceTransactionDateTime() {
		return serviceTransactionDateTime;
	}

	public void setServiceTransactionDateTime(
			ServiceTransactionDateTime serviceTransactionDateTime) {
		this.serviceTransactionDateTime = serviceTransactionDateTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
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

	public boolean isAcknowledged() {
		return acknowledged;
	}

	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}

	public Addendum getAddendum() {
		return addendum;
	}

	public void setAddendum(Addendum addendum) {
		this.addendum = addendum;
	}

	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
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

	public TransactionSummaryData getTransactionSummaryData() {
		return transactionSummaryData;
	}

	public void setTransactionSummaryData(
			TransactionSummaryData transactionSummaryData) {
		this.transactionSummaryData = transactionSummaryData;
	}

	public String getPrepaidCard() {
		return prepaidCard;
	}

	public void setPrepaidCard(String prepaidCard) {
		this.prepaidCard = prepaidCard;
	}

	/*
	 * @XmlElement(name = "Amount", namespace =
	 * "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard") protected
	 * String amount;
	 * 
	 * 
	 * @XmlElement(name = "CardType", namespace =
	 * "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard") protected
	 * String cardType;
	 * 
	 * @XmlElement(name = "FeeAmount", namespace =
	 * "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard") protected
	 * String feeAmount;
	 * 
	 * @XmlElement(name = "ApprovalCode", namespace =
	 * "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard") protected
	 * String approvalCode;
	 * 
	 * @XmlElement(name = "AVSResult", namespace =
	 * "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard") protected
	 * String aVSResult;
	 * 
	 * @XmlElement(name = "CVResult", namespace =
	 * "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard") protected
	 * String cVResult;
	 * 
	 * @XmlElement(name = "CardLevel", namespace =
	 * "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard") protected
	 * String cardLevel;
	 * 
	 * @XmlElement(name = "DowngradeCode", namespace =
	 * "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard") protected
	 * String downgradeCode;
	 * 
	 * @XmlElement(name = "MaskedPAN", namespace =
	 * "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard") protected
	 * String maskedPAN;
	 * 
	 * @XmlElement(name = "PaymentAccountDataToken", namespace =
	 * "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard") protected
	 * String paymentAccountDataToken ;
	 * 
	 * @XmlElement(name = "RetrievalReferenceNumber", namespace =
	 * "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard") protected
	 * String retrievalReferenceNumber;
	 * 
	 * @XmlElement(name = "AdviceResponse", namespace =
	 * "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard/Pro")
	 * protected String adviceResponse;
	 * 
	 * @XmlElement(name = "CommercialCardResponse", namespace =
	 * "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard/Pro")
	 * protected String commercialCardResponse;
	 * 
	 * @XmlElement(name = "ReturnedACI", namespace =
	 * "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard/Pro")
	 * protected String returnedACI;
	 */

}
