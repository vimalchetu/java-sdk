package com.velocity.model.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class defines the attributes for accessing the ErrorResponse
 * 
 * @author anitk
 * @date 13-January-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "ErrorResponse", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Rest")
public class ErrorResponse {

	@XmlElement(name = "ErrorId", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Rest")
	protected String errorId;

	@XmlElement(name = "HelpUrl", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Rest")
	protected String helpUrl;

	@XmlElement(name = "Operation", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Rest")
	protected String operation;

	@XmlElement(name = "Reason", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Rest")
	protected String reason;

	@XmlElement(name = "ValidationErrors", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Rest")
	protected ValidationErrors validationErrors;

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public String getHelpUrl() {
		return helpUrl;
	}

	public void setHelpUrl(String helpUrl) {
		this.helpUrl = helpUrl;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public ValidationErrors getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(ValidationErrors validationErrors) {
		this.validationErrors = validationErrors;
	}

}
