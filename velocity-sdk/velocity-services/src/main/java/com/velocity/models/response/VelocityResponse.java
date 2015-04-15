/**
 * 
 */
package com.velocity.models.response;

/**
 * This class defines the entities for VelocityResponse.
 * 
 * @author anitk
 * @date 08-January-2015
 */

public class VelocityResponse {

	private int statusCode;

	private String message;

	private BankcardTransactionResponsePro bankcardTransactionResponse;

	private BankcardCaptureResponse bankcardCaptureResponse;

	private ErrorResponse errorResponse;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public BankcardTransactionResponsePro getBankcardTransactionResponse() {
		return bankcardTransactionResponse;
	}

	public void setBankcardTransactionResponse(
			BankcardTransactionResponsePro bankcardTransactionResponse) {
		this.bankcardTransactionResponse = bankcardTransactionResponse;
	}

	public BankcardCaptureResponse getBankcardCaptureResponse() {
		return bankcardCaptureResponse;
	}

	public void setBankcardCaptureResponse(
			BankcardCaptureResponse bankcardCaptureResponse) {
		this.bankcardCaptureResponse = bankcardCaptureResponse;
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
