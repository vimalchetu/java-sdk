/**
 * 
 */
package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attributes for TransactionClassTypePair
 * 
 * @author Vimal Kumar
 * @date 12-March-2015
 */
public class TransactionClassTypePair {

	@SerializedName("TransactionClass")
	private String transactionClass;

	@SerializedName("TransactionType")
	private String transactionType;

	public String getTransactionClass() {
		return transactionClass;
	}

	public void setTransactionClass(String transactionClass) {
		this.transactionClass = transactionClass;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

}
