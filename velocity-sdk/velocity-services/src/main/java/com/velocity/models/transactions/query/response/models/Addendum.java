package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attributes for
 * 
 * @author Vimal Kumar
 * @date 13-03-2015
 */
public class Addendum {

	@SerializedName("Value")
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
