package com.velocity.model.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attributes for accessing JsonErrorResponse
 * 
 * @author Vimal Kumar
 * @date 13-04-2015
 */
public class JsonErrorResponse {

	@SerializedName("ErrorId")
	private String errorId;
	@SerializedName("HelpUrl")
	private String helpUrl;
	@SerializedName("Messages")
	private List<String> messages;
	@SerializedName("Operation")
	private String operation;
	@SerializedName("Reason")
	private String reason;
	@SerializedName("ValidationErrors")
	private List<String> validationErrors;

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

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
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

	public List<String> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<String> validationErrors) {
		this.validationErrors = validationErrors;
	}
}
