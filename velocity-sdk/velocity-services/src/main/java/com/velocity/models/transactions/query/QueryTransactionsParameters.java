package com.velocity.models.transactions.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.velocity.enums.CaptureState;
import com.velocity.enums.QueryType;
import com.velocity.enums.TransactionState;
import com.velocity.enums.TypeCardType;

/**
 * This class defines the attributes for QueryTransactionsParameters
 * 
 * @author Vimal Kumar
 * @date 12-March-2015
 */
public class QueryTransactionsParameters {

	@SerializedName("Amounts")
	private List<Float> amounts;

	@SerializedName("ApprovalCodes")
	private List<String> approvalCodes;

	@SerializedName("BatchIds")
	private List<String> batchIds;

	@SerializedName("CaptureDateRange")
	private CaptureDateRange captureDateRange;

	@SerializedName("CaptureState")
	private CaptureState captureState;

	@SerializedName("TypeCardType")
	private TypeCardType cardTypes;

	@SerializedName("QueryType")
	private QueryType queryType;

	@SerializedName("IsAcknowledged")
	private String isAcknowledged = "NotSet";

	@SerializedName("MerchantProfileIds")
	private List<String> merchantProfileIds;

	@SerializedName("OrderNumbers")
	private List<String> orderNumbers;

	@SerializedName("ServiceIds")
	private List<String> serviceIds;

	@SerializedName("ServiceKeys")
	private List<String> serviceKeys;

	@SerializedName("TransactionClassTypePair")
	private Map<String, String> transactionClassTypePairs;

	@SerializedName("TransactionDateRange")
	private TransactionDateRange transactionDateRange;

	@SerializedName("TransactionIds")
	private List<String> transactionIds;

	@SerializedName("TransactionState")
	private TransactionState transactionState;

	public List<Float> getAmounts() {
		if (amounts == null) {
			amounts = new ArrayList<Float>();
		}
		return amounts;
	}

	public void setAmounts(List<Float> amounts) {
		this.amounts = amounts;
	}

	public List<String> getApprovalCodes() {
		if (approvalCodes == null) {
			approvalCodes = new ArrayList<String>();
		}

		return approvalCodes;
	}

	public void setApprovalCodes(List<String> approvalCodes) {
		this.approvalCodes = approvalCodes;
	}

	public List<String> getBatchIds() {
		if (batchIds == null) {
			batchIds = new ArrayList<String>();
		}

		return batchIds;
	}

	public void setBatchIds(List<String> batchIds) {
		this.batchIds = batchIds;
	}

	public CaptureDateRange getCaptureDateRange() {
		if (captureDateRange == null) {
			captureDateRange = new CaptureDateRange();
		}
		return captureDateRange;
	}

	public void setCaptureDateRange(CaptureDateRange captureDateRange) {
		this.captureDateRange = captureDateRange;
	}

	public CaptureState getCaptureState() {
		return captureState;
	}

	public void setCaptureState(CaptureState captureState) {
		this.captureState = captureState;
	}

	public TypeCardType getCardTypes() {
		return cardTypes;
	}

	public void setCardTypes(TypeCardType cardTypes) {
		this.cardTypes = cardTypes;
	}

	public List<String> getMerchantProfileIds() {
		if (merchantProfileIds == null) {
			merchantProfileIds = new ArrayList<String>();
		}

		return merchantProfileIds;
	}

	public void setMerchantProfileIds(List<String> merchantProfileIds) {
		this.merchantProfileIds = merchantProfileIds;
	}

	public List<String> getOrderNumbers() {
		if (orderNumbers == null) {
			orderNumbers = new ArrayList<String>();
		}

		return orderNumbers;
	}

	public void setOrderNumbers(List<String> orderNumbers) {
		this.orderNumbers = orderNumbers;
	}

	public QueryType getQueryType() {
		return queryType;
	}

	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}

	public List<String> getServiceIds() {
		if (serviceIds == null) {
			serviceIds = new ArrayList<String>();
		}
		return serviceIds;
	}

	public void setServiceIds(List<String> serviceIds) {
		this.serviceIds = serviceIds;
	}

	public List<String> getServiceKeys() {
		if (serviceKeys == null) {
			serviceKeys = new ArrayList<String>();
		}

		return serviceKeys;
	}

	public void setServiceKeys(List<String> serviceKeys) {
		this.serviceKeys = serviceKeys;
	}

	public TransactionDateRange getTransactionDateRange() {

		if (transactionDateRange == null) {
			transactionDateRange = new TransactionDateRange();
		}
		return transactionDateRange;
	}

	public void setTransactionDateRange(
			TransactionDateRange transactionDateRange) {
		this.transactionDateRange = transactionDateRange;
	}

	public List<String> getTransactionIds() {
		if (transactionIds == null) {
			transactionIds = new ArrayList<String>();
		}
		return transactionIds;
	}

	public void setTransactionIds(List<String> transactionIds) {
		this.transactionIds = transactionIds;
	}

	public TransactionState getTransactionState() {
		return transactionState;
	}

	public void setTransactionState(TransactionState transactionState) {
		this.transactionState = transactionState;
	}

	public String getIsAcknowledged() {
		return isAcknowledged;
	}

	public void setIsAcknowledged(String isAcknowledged) {
		this.isAcknowledged = isAcknowledged;
	}

	public Map<String, String> getTransactionClassTypePairs() {
		if (transactionClassTypePairs == null) {
			transactionClassTypePairs = new HashMap<String, String>();
		}

		return transactionClassTypePairs;
	}

	public void setTransactionClassTypePairs(
			Map<String, String> transactionClassTypePairs) {
		this.transactionClassTypePairs = transactionClassTypePairs;
	}

}
