/**
 * 
 */
package com.velocity.models.request.undo;

/**
 * This class holds the data for DifferenceData for Undo method.
 * @author vimalk2
 * @date 08-January-2015
 */
public class DifferenceData {

	/* Attribute for DifferenceData value exists or not. */
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
