/**
 * 
 */
package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attributes for CompleteTransaction .
 * 
 * @author Vimal Kumar
 * @date 11-March-2015
 */
public class CompleteTransaction {

	@SerializedName("CWSTransaction")
	private CWSTransaction cWSTransaction;

	@SerializedName("SerializedTransaction")
	private String serializedTransaction;

	public CWSTransaction getcWSTransaction() {
		return cWSTransaction;
	}

	public void setcWSTransaction(CWSTransaction cWSTransaction) {
		this.cWSTransaction = cWSTransaction;
	}

	public String getSerializedTransaction() {
		return serializedTransaction;
	}

	public void setSerializedTransaction(String serializedTransaction) {
		this.serializedTransaction = serializedTransaction;
	}

}
