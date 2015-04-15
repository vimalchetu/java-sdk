package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attributes for ShippingData
 * 
 * @author Vimal Kumar
 * @date 12-March-2015
 */
public class ShippingData {

	/* Attribute for ShippingData value exists or not. */
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
