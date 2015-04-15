package com.velocity.model.request.capture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class holds the data for SaleTotals for response capture XML.
 * 
 * @author Vimal Kumar
 * @date 07-January-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SaleTotals", propOrder = {})
@XmlRootElement(name = "SaleTotals")
public class SaleTotals {

	/* Processing the response xml for NetAmount */
	@XmlElement(name = "NetAmount", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	private String netAmount;

	/* Processing the response xml for Count */
	@XmlElement(name = "Count", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	private String count;

	public String getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
}
