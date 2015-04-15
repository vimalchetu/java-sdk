package com.velocity.model.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.velocity.models.transactions.query.response.models.Addendum;

/**
 * This class defines the attributes for BankcardTransactionResponse processing velocity transactions.
 * 
 * @author anitk
 * @date 13-January-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "BankcardTransactionResponsePro", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard/Pro")
public class BankcardTransactionResponsePro {

	/* Xml attributes for BankcardTransactionResponse. */
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
	
	@XmlElement(name = "ServiceTransactionDateTime", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected ServiceTransactionDateTime serviceTransactionDateTime;
	
	@XmlElement(name = "Addendum", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions", nillable = true)
	protected Addendum addendum;
	
	@XmlElement(name = "CaptureState", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected String captureState;
	
	@XmlElement(name = "TransactionState", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected String transactionState;
	
	@XmlElement(name = "IsAcknowledged", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected boolean acknowledged;
	
	@XmlElement(name = "PrepaidCard", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String prepaidCard;
	
	@XmlElement(name = "Reference", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	protected String reference;
	
	@XmlElement(name = "Amount", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String amount;
	
	@XmlElement(name = "CardType", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String cardType;
	
	@XmlElement(name = "FeeAmount", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String feeAmount;
	
	@XmlElement(name = "ApprovalCode", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String approvalCode;
	
	@XmlElement(name = "AVSResult", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected AVSResult avsResult;
	
	@XmlElement(name = "BatchId", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String batchId;
	
	@XmlElement(name = "CVResult", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String cVResult;
	
	@XmlElement(name = "CardLevel", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String cardLevel;
	
	@XmlElement(name = "DowngradeCode", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String downgradeCode;
	
	@XmlElement(name = "MaskedPAN", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String maskedPAN;
	
	@XmlElement(name = "PaymentAccountDataToken", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String paymentAccountDataToken;
	
	@XmlElement(name = "RetrievalReferenceNumber", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String retrievalReferenceNumber;
	
	@XmlElement(name = "Resubmit", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String resubmit;
	
	@XmlElement(name = "SettlementDate", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String settlementDate;
	
	@XmlElement(name = "FinalBalance", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String finalBalance;
	
	@XmlElement(name = "OrderId", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String orderId;
	
	@XmlElement(name = "CashBackAmount", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String cashBackAmount;
	
	@XmlElement(name = "AdviceResponse", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard/Pro")
	protected String adviceResponse;
	
	@XmlElement(name = "CommercialCardResponse", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard/Pro")
	protected String commercialCardResponse;
	
	@XmlElement(name = "ReturnedACI", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard/Pro")
	protected String returnedACI;

	public String getRetrievalReferenceNumber() {
		return retrievalReferenceNumber;
	}

	public void setRetrievalReferenceNumber(String retrievalReferenceNumber) {
		this.retrievalReferenceNumber = retrievalReferenceNumber;
	}

	public String getPaymentAccountDataToken() {
		return paymentAccountDataToken;
	}

	public void setPaymentAccountDataToken(String paymentAccountDataToken) {
		this.paymentAccountDataToken = paymentAccountDataToken;
	}

	public String getMaskedPAN() {
		return maskedPAN;
	}

	public void setMaskedPAN(String maskedPAN) {
		this.maskedPAN = maskedPAN;
	}

	public String getDowngradeCode() {
		return downgradeCode;
	}

	public void setDowngradeCode(String downgradeCode) {
		this.downgradeCode = downgradeCode;
	}

	public String getCardLevel() {
		return cardLevel;
	}

	public void setCardLevel(String cardLevel) {
		this.cardLevel = cardLevel;
	}

	public String getcVResult() {
		return cVResult;
	}

	public void setcVResult(String cVResult) {
		this.cVResult = cVResult;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public AVSResult getAvsResult() {
		return avsResult;
	}

	public void setAvsResult(AVSResult avsResult) {
		this.avsResult = avsResult;
	}

	public String getApprovalCode() {
		return approvalCode;
	}

	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}

	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

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

	public String getCommercialCardResponse() {
		return commercialCardResponse;
	}

	public void setCommercialCardResponse(String commercialCardResponse) {
		this.commercialCardResponse = commercialCardResponse;
	}

	public boolean isAcknowledged() {
		return acknowledged;
	}

	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}

	public String getPrepaidCard() {
		return prepaidCard;
	}

	public void setPrepaidCard(String prepaidCard) {
		this.prepaidCard = prepaidCard;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAdviceResponse() {
		return adviceResponse;
	}

	public void setAdviceResponse(String adviceResponse) {
		this.adviceResponse = adviceResponse;
	}

	public String getReturnedACI() {
		return returnedACI;
	}

	public void setReturnedACI(String returnedACI) {
		this.returnedACI = returnedACI;
	}

	public Addendum getAddendum() {
		return addendum;
	}

	public void setAddendum(Addendum addendum) {
		this.addendum = addendum;
	}

	public String getResubmit() {
		return resubmit;
	}

	public void setResubmit(String resubmit) {
		this.resubmit = resubmit;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getFinalBalance() {
		return finalBalance;
	}

	public void setFinalBalance(String finalBalance) {
		this.finalBalance = finalBalance;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCashBackAmount() {
		return cashBackAmount;
	}

	public void setCashBackAmount(String cashBackAmount) {
		this.cashBackAmount = cashBackAmount;
	}
}
