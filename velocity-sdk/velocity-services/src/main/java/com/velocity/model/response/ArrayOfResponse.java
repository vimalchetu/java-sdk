package com.velocity.model.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class holds attributes for ArrayOfResponse
 * 
 * @author Vimal Kumar
 * @date 13-04-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "ArrayOfResponse", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
public class ArrayOfResponse {

	@XmlElement(name = "Response", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions")
	private Response response;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

}
