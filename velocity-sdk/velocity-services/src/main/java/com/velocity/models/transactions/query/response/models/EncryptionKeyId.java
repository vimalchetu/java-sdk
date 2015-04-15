package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attributes for EncryptionKeyId
 * 
 * @author Vimal Kumar
 * @date 11-March-2015
 * 
 */
public class EncryptionKeyId {

	/* Attribute for EncryptionKeyId value exists or not. */
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
