/**
 * 
 */
package com.velocity.models.request.authorizeAndCapture;

/**
 * This model class holds the data for Fax
 * 
 * @author vimalk2
 * @date 09-January-2015
 */
public class Fax {

	/* Attribute for Fax value exists or not. */
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
