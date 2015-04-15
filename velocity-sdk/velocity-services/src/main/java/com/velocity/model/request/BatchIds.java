package com.velocity.model.request;

/**
 * This class holds the data for BatchIds for undo method.
 * 
 * @author Vimal Kumar
 * @date 08-January-2015
 */
public class BatchIds {

	/* Attribute for BatchIds value exists or not. */
	private boolean nillable = true;

	private String value = "";

	public String getValue() {
		return value;
	}

	public boolean isNillable() {
		return nillable;
	}

	public void setNillable(boolean nillable) {
		this.nillable = nillable;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
