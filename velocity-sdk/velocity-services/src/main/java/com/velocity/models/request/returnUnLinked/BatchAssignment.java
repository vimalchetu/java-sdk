/**
 * 
 */
package com.velocity.models.request.returnUnLinked;

/**
 * This class holds the data for BatchAssignment
 * @author vimalk2
 * @date 19-January-2015
 */
public class BatchAssignment {
	
	
	/* Attribute for BatchAssignment value exists or not. */
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
