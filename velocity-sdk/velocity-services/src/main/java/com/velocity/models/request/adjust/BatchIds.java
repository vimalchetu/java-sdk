/**
 * 
 */
package com.velocity.models.request.adjust;

/**
 * This class holds the data for BatchIds for Adjust method
 * @author vimalk2
 * @date 13-January-2015
 */
public class BatchIds {

	/* Attribute for BatchIds value exists or not. */
	private boolean nillable = true;

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
