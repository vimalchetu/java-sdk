/**
 * 
 */
package com.velocity.model.request.capture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * This class holds the data for PINDebitReturnTotals.
 * 
 * @author Vimal Kumar
 * @date 07-January-2015
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PINDebitReturnTotals", propOrder = {})
@XmlRootElement(name = "PINDebitReturnTotals")
public class PINDebitReturnTotals {

	/* Processing reponse xml for nil */
	@XmlAttribute(name = "nil", namespace = "http://www.w3.org/2001/XMLSchema-instance", required = true)
	private String nillable;

	/* Processing the response xml for value */
	@XmlValue
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getNillable() {
		return nillable;
	}

	public void setNillable(String nillable) {
		this.nillable = nillable;
	}
}
