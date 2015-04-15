/**
 * 
 */
package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;
import com.velocity.enums.CaptureState;
import com.velocity.enums.IsAcknowledged;
import com.velocity.enums.Status;
import com.velocity.enums.TransactionState;

/**
 * This class defines the attributes for TransactionInformation
 * 
 * @author Vimal Kumar
 * @date 12-March-2015
 */
public class TransactionInformation {

	@SerializedName("Amount")
	private String amount;

	@SerializedName("ApprovalCode")
	private String approvalCode;

	@SerializedName("BankcardData")
	private BankcardData bankcardData;

	@SerializedName("BatchId")
	private String batchId;

	@SerializedName("CapturedAmount")
	private float capturedAmount;

	@SerializedName("CaptureDateTime")
	private String captureDateTime;

	@SerializedName("CaptureState")
	private CaptureState captureState;

	@SerializedName("CaptureStatusMessage")
	private String captureStatusMessage;

	@SerializedName("CustomerId")
	private String customerId;

	@SerializedName("ElectronicCheckData")
	private ElectronicCheckData electronicCheckData;

	@SerializedName("IsAcknowledged")
	private IsAcknowledged isAcknowledged;

	@SerializedName("MaskedPAN")
	private String maskedPAN;

	@SerializedName("MerchantProfileId")
	private String merchantProfileId;

	@SerializedName("OriginatorTransactionId")
	private String originatorTransactionId;

	@SerializedName("Reference")
	private String reference;

	@SerializedName("ServiceId")
	private String serviceId;

	@SerializedName("ServiceKey")
	private String serviceKey;

	@SerializedName("ServiceTransactionId")
	private String serviceTransactionId;

	@SerializedName("Status")
	private Status status;

	@SerializedName("StoredValueData")
	private StoredValueData storedValueData;

	@SerializedName("TransactionClassTypePair")
	private TransactionClassTypePair transactionClassTypePair;

	@SerializedName("TransactionId")
	private String transactionId;

	@SerializedName("TransactionState")
	private TransactionState transactionState;

	@SerializedName("TransactionStatusCode")
	private String transactionStatusCode;

	@SerializedName("TransactionTimestamp")
	private String transactionTimestamp;

	public BankcardData getBankcardData() {
		return bankcardData;
	}

	public void setBankcardData(BankcardData bankcardData) {
		this.bankcardData = bankcardData;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public float getCapturedAmount() {
		return capturedAmount;
	}

	public void setCapturedAmount(float capturedAmount) {
		this.capturedAmount = capturedAmount;
	}

	public String getCaptureDateTime() {
		return captureDateTime;
	}

	public void setCaptureDateTime(String captureDateTime) {
		this.captureDateTime = captureDateTime;
	}
	public CaptureState getCaptureState() {
        return captureState;
    }

    public void setCaptureState(CaptureState captureState) {
        this.captureState = captureState;
    }

    public String getCaptureStatusMessage() {
		return captureStatusMessage;
	}

	public void setCaptureStatusMessage(String captureStatusMessage) {
		this.captureStatusMessage = captureStatusMessage;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public IsAcknowledged getIsAcknowledged() {
		return isAcknowledged;
	}

	public void setIsAcknowledged(IsAcknowledged isAcknowledged) {
		this.isAcknowledged = isAcknowledged;
	}

	public String getMaskedPAN() {
		return maskedPAN;
	}

	public void setMaskedPAN(String maskedPAN) {
		this.maskedPAN = maskedPAN;
	}

	public String getMerchantProfileId() {
		return merchantProfileId;
	}

	public void setMerchantProfileId(String merchantProfileId) {
		this.merchantProfileId = merchantProfileId;
	}

	public String getOriginatorTransactionId() {
		return originatorTransactionId;
	}

	public void setOriginatorTransactionId(String originatorTransactionId) {
		this.originatorTransactionId = originatorTransactionId;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public String getServiceTransactionId() {
		return serviceTransactionId;
	}

	public void setServiceTransactionId(String serviceTransactionId) {
		this.serviceTransactionId = serviceTransactionId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public TransactionClassTypePair getTransactionClassTypePair() {
		return transactionClassTypePair;
	}

	public void setTransactionClassTypePair(
			TransactionClassTypePair transactionClassTypePair) {
		this.transactionClassTypePair = transactionClassTypePair;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public TransactionState getTransactionState() {
        return transactionState;
    }

    public void setTransactionState(TransactionState transactionState) {
        this.transactionState = transactionState;
    }

    public String getTransactionStatusCode() {
		return transactionStatusCode;
	}

	public void setTransactionStatusCode(String transactionStatusCode) {
		this.transactionStatusCode = transactionStatusCode;
	}

	public String getTransactionTimestamp() {
		return transactionTimestamp;
	}

	public void setTransactionTimestamp(String transactionTimestamp) {
		this.transactionTimestamp = transactionTimestamp;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getApprovalCode() {
		return approvalCode;
	}

	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}

	public StoredValueData getStoredValueData() {
		return storedValueData;
	}

	public void setStoredValueData(StoredValueData storedValueData) {
		this.storedValueData = storedValueData;
	}

	public ElectronicCheckData getElectronicCheckData() {
		return electronicCheckData;
	}

	public void setElectronicCheckData(ElectronicCheckData electronicCheckData) {
		this.electronicCheckData = electronicCheckData;
	}

}
