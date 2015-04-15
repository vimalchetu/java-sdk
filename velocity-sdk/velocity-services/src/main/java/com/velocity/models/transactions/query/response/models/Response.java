/**
 * 
 */
package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attributes for Response
 * 
 * @author Vimal Kumar
 * @date 12-March-2015
 */
public class Response {

	@SerializedName("$type")
	private String type;
	
	@SerializedName("Status")
	protected String status;

	@SerializedName("StatusCode")
	protected String statusCode;

	@SerializedName("StatusMessage")
	protected String statusMessage;

	@SerializedName("TransactionId")
	protected String transactionId;

	@SerializedName("OriginatorTransactionId")
	protected String originatorTransactionId;

	@SerializedName("ServiceTransactionId")
	protected String serviceTransactionId;

	@SerializedName("ServiceTransactionDateTime")
	protected ServiceTransactionDateTime serviceTransactionDateTime;

	@SerializedName("Addendum")
	protected Addendum addendum;

	@SerializedName("CaptureState")
	protected String captureState;

	@SerializedName("TransactionState")
	protected String transactionState;

	@SerializedName("IsAcknowledged")
	protected boolean acknowledged;

	@SerializedName("PrepaidCard")
	protected String prepaidCard;

	@SerializedName("Reference")
	protected String reference;

	@SerializedName("Amount")
	protected String amount;

	@SerializedName("CardType")
	protected String cardType;

	@SerializedName("FeeAmount")
	protected String feeAmount;

	@SerializedName("ApprovalCode")
	protected String approvalCode;

	@SerializedName("AVSResult")
	protected AVSResult avsResult;

	@SerializedName("BatchId")
	protected String batchId;

	@SerializedName("CVResult")
	protected String cVResult;

	@SerializedName("CardLevel")
	protected String cardLevel;

	@SerializedName("DowngradeCode")
	protected String downgradeCode;

	@SerializedName("MaskedPAN")
	protected String maskedPAN;

	@SerializedName("PaymentAccountDataToken")
	protected String paymentAccountDataToken;

	@SerializedName("RetrievalReferenceNumber")
	protected String retrievalReferenceNumber;

	@SerializedName("Resubmit")
	protected String resubmit;

	@SerializedName("SettlementDate")
	protected String settlementDate;

	@SerializedName("FinalBalance")
	protected String finalBalance;

	@SerializedName("OrderId")
	protected String orderId;

	@SerializedName("CashBackAmount")
	protected String cashBackAmount;

	@SerializedName("AdviceResponse")
	protected String adviceResponse;

	@SerializedName("CommercialCardResponse")
	protected String commercialCardResponse;

	@SerializedName("ReturnedACI")
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
		if (avsResult == null) {
			avsResult = new AVSResult();
		}
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
