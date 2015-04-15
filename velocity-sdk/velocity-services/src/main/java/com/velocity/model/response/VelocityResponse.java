package com.velocity.model.response;

import java.util.List;

import com.velocity.models.transactions.query.response.models.TransactionDetail;

/**
 * This class defines the entities for VelocityResponse.
 * 
 * @author anitk
 * @date 08-January-2015
 */
public class VelocityResponse {

    private int statusCode;
    private String message;
    private String result;
    private BankcardTransactionResponsePro bankcardTransactionResponse;
    private BankcardCaptureResponse bankcardCaptureResponse;
    private ArrayOfResponse arrayOfResponse;
    private List<TransactionDetail> transactionDetailList;
    private ErrorResponse errorResponse;
    private JsonErrorResponse jsonErrorResponse;
    private boolean isError;

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
    public List<TransactionDetail> getTransactionDetailList() {
        return transactionDetailList;
    }
    public void setTransactionDetailList(
            List<TransactionDetail> transactionDetailList) {
        this.transactionDetailList = transactionDetailList;
    }
    public ArrayOfResponse getArrayOfResponse() {
        return arrayOfResponse;
    }
    public void setArrayOfResponse(ArrayOfResponse arrayOfResponse) {
        this.arrayOfResponse = arrayOfResponse;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public boolean isError() {
        return isError;
    }
    public void setError(boolean isError) {
        this.isError = isError;
    }
    public JsonErrorResponse getJsonErrorResponse() {
        return jsonErrorResponse;
    }
    public void setJsonErrorResponse(JsonErrorResponse jsonErrorResponse) {
        this.jsonErrorResponse = jsonErrorResponse;
    }
    @Override
    public String toString() {
        return "Servere Status Code=" + getStatusCode()
                + "\nServer response result" + getResult();
    }
}
