package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * This class holds the data for SecurePaymentAccountData
 * 
 * @author Vimal Kumar
 * @date 12-March-2015
 */
public class SecurePaymentAccountData {

	/* Attribute for SecurePaymentAccountData value exists or not. */
	@SerializedName("Nillable")
	private boolean nillable = true;

	@SerializedName("Value")
	private String value = "";

	public boolean isNillable() {
		return nillable;
	}

	public void setNillable(boolean nillable) {
		this.nillable = nillable;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
