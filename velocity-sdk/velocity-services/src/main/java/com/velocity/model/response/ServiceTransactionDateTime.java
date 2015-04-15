package com.velocity.model.response;

import javax.xml.bind.annotation.XmlAccessType; 
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class defines the attributes for accessing the ServiceTransactionDateTime
 * 
 * @author Vimal Kumar
 * @date 09-February-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceTransactionDateTime", propOrder = {})
@XmlRootElement(name = "ServiceTransactionDateTime")
public class ServiceTransactionDateTime {    
	/* Xml attributes for ServiceTransactionDateTime. */
@XmlElement(name = "Date", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
protected String date;

@XmlElement(name = "Time", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
protected String time;

@XmlElement(name = "TimeZone", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
protected String timeZone;

public String getDate() {
	return date;
}

public void setDate(String date) {
	this.date = date;
}

public String getTime() {
	return time;
}

public void setTime(String time) {
	this.time = time;
}

public String getTimeZone() {
	return timeZone;
}

public void setTimeZone(String timeZone) {
	this.timeZone = timeZone;
}}
