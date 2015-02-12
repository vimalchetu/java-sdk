/**
 * 
 */
package com.velocity.models.request.verify;

/**
 * This model class holds the data for IdentificationInformation
 * 
 * @author vimalk2
 * @date 13-January-2015
 */
public class IdentificationInformation {

	/* Attribute for IdentificationInformation value exists or not. */
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
