package com.velocity.model.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class defines the attributes for NetTotals
 * 
 * @author Vimal Kumar
 * @date 13-04-2015
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NetTotals", propOrder = {})
@XmlRootElement(name = "NetTotals", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
public class NetTotals {

	@XmlElement(name = "NetAmount", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String netAmount;

	@XmlElement(name = "Count", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String count;

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
