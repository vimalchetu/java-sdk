package com.velocity.model.request.capture;

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * This class defines the attributes for accessing the ReturnTotals
 * 
 * @author Vimal Kumar
 * @date 07-January-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnTotals", propOrder = {})
@XmlRootElement(name = "ReturnTotals")
public class ReturnTotals {

	/* Proccessing the response xml for nill */
	@XmlAttribute(name = "nil", namespace = "http://www.w3.org/2001/XMLSchema-instance", required = true)
	private String nillable;

	/* Proccessing the response xml for value */
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
	/*
	 * Processing the response xml for NetAmount
	 * 
	 * @XmlElement(name = "NetAmount", namespace =
	 * "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard") private
	 * String netAmount;
	 * 
	 * Processing the response xml for Count
	 * 
	 * @XmlElement(name = "Count", namespace =
	 * "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard") private
	 * String count;
	 * 
	 * public String getNetAmount() { return netAmount; }
	 * 
	 * public void setNetAmount(String netAmount) { this.netAmount = netAmount;
	 * }
	 * 
	 * public String getCount() { return count; }
	 * 
	 * public void setCount(String count) { this.count = count; }
	 */
}
