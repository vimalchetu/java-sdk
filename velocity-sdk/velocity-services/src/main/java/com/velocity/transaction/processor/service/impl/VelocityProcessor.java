package com.velocity.transaction.processor.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.velocity.config.VelocityConfigManager;
import com.velocity.constants.VelocityConstants;
import com.velocity.exceptions.VelocityException;
import com.velocity.exceptions.VelocityIllegalArgumentException;
import com.velocity.exceptions.VelocityNotFoundException;
import com.velocity.exceptions.VelocityRestInvokeException;
import com.velocity.model.request.Adjust;
import com.velocity.model.request.AuthorizeAndCaptureTransaction;
import com.velocity.model.request.AuthorizeTransaction;
import com.velocity.model.request.CaptureAll;
import com.velocity.model.request.ReturnById;
import com.velocity.model.request.ReturnTransaction;
import com.velocity.model.request.Undo;
import com.velocity.model.request.VelocityRequest;
import com.velocity.model.request.capture.ChangeTransaction;
import com.velocity.model.response.ArrayOfResponse;
import com.velocity.model.response.BankcardCaptureResponse;
import com.velocity.model.response.BankcardTransactionResponsePro;
import com.velocity.model.response.ErrorResponse;
import com.velocity.model.response.JsonErrorResponse;
import com.velocity.model.response.VelocityResponse;
import com.velocity.models.transactions.query.QueryTransactionsDetail;
import com.velocity.models.transactions.query.response.models.TransactionDetail;
import com.velocity.request.transaction.xml.AdjustRequestXML;
import com.velocity.request.transaction.xml.AuthorizeAndCaptureRequestXML;
import com.velocity.request.transaction.xml.AuthorizeTransactionXML;
import com.velocity.request.transaction.xml.CaptureAllRequestXML;
import com.velocity.request.transaction.xml.CaptureRequestXML;
import com.velocity.request.transaction.xml.P2PEAuthorizeAndCaptureRequestXML;
import com.velocity.request.transaction.xml.P2PEAuthorizeRequestXML;
import com.velocity.request.transaction.xml.P2PEReturnUnlinkedRequestXML;
import com.velocity.request.transaction.xml.ReturnByIdXML;
import com.velocity.request.transaction.xml.ReturnUnlinkedRequestXML;
import com.velocity.request.transaction.xml.UndoRequestXML;
import com.velocity.request.transaction.xml.VerifyTransactionXML;
import com.velocity.transaction.processor.service.IVelocityProcessor;
import com.velocity.utility.CommonUtils;
import com.velocity.utility.VelocityConnection;

/**
 * This is main class for process all velocity Transactions. This class contains
 * all related methods and calls for payment transaction with Velocity server
 * 
 * @author Vimal Kumar
 * @date 15-Jan-2015
 */
public class VelocityProcessor implements IVelocityProcessor {
    private static final Logger LOG = Logger.getLogger(VelocityProcessor.class);
    private VelocityConfigManager velocityConfigManager;
    /* Variable for Velocity identity token. */
    private String identityToken;
    /* Variable for Application profile Id. */
    private String appProfileId;
    /* Variable for Merchant profile Id */
    private String merchantProfileId;
    /* Variable for Work flow Id */
    private String workFlowId;
    /* Flag for Test account. */
    private boolean isTestAccount;
    /* Variable for Session token */
    private String sessionToken;
    /* Reference for transaction request Log */
    private String txnRequestLog;
    /* Reference for transaction response XML */
    private String txnResponseLog;

    /* Initializes properties */
    public VelocityProcessor(String sessionToken, String identityToken, String appProfileId, String merchantProfileId, String workFlowId, boolean isTestAccount) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException, VelocityException, IOException {
        this.identityToken = identityToken;
        this.appProfileId = appProfileId;
        this.merchantProfileId = merchantProfileId;
        this.workFlowId = workFlowId;
        this.isTestAccount = isTestAccount;
        velocityConfigManager = new VelocityConfigManager();
        /*
         * If session token is not provided by merchants then call SignOn to
         * velocity server to get new session token based on identityToken value
         */
        if((sessionToken == null || sessionToken.isEmpty()) && (this.identityToken != null && !this.identityToken.isEmpty())){
            this.sessionToken = signOn(identityToken);
        }else{
            this.sessionToken = sessionToken;
        }
    }

    /**
     * This method will used to get session token from velocity server based on provided token
     * value.
     */
    public String signOn(String identityToken) throws VelocityIllegalArgumentException, VelocityRestInvokeException, VelocityException, VelocityNotFoundException {
        CommonUtils.checkNullStr(identityToken);
        // Get session token & remove invalid char from session token
        String URL = getServiceUrl(isTestAccount) + "/SvcInfo/token";
        VelocityRequest velocityRequest = new VelocityRequest();
        velocityRequest.setRequestType(VelocityConstants.GET_METHOD);
        velocityRequest.setUrl(URL);
        LOG.debug("SignOn URL >>>>>>>>>>>" + URL);
        velocityRequest.setAuthToken(identityToken);
        velocityRequest.setContentType(VelocityConstants.JSON_REQUEST_TYPE);
        VelocityResponse velocityResponse = VelocityConnection.connectPost(velocityRequest);
        LOG.debug("VelocityResponse >>>>>>>>>>>" + velocityResponse);
        sessionToken = CommonUtils.removeInvalidChar(velocityResponse.getResult());
        LOG.debug("SessionToken >>>>>>>>>>>" + sessionToken);
        return sessionToken;
    }

    /**
     * This method will use to Verify the card detail and address detail of customer.
     * This method send request to server by generating a request XML and handle response back from
     * server
     * */
    public VelocityResponse verify(AuthorizeTransaction authorizeTransaction) throws VelocityException, VelocityIllegalArgumentException, VelocityNotFoundException, VelocityRestInvokeException {
        CommonUtils.checkNullObj(authorizeTransaction);
        // Generating XML for verify request.
        VerifyTransactionXML authorizeTransactionXML = new VerifyTransactionXML();
        String verifyTxnRequestXML = authorizeTransactionXML.verifyXML(authorizeTransaction, appProfileId, merchantProfileId);
        txnRequestLog = verifyTxnRequestXML;
        // Invoking URL for the XML input request
        String invokeURL = getServiceUrl(isTestAccount) + "/Txn/" + workFlowId + "/verify";
        // Set request data
        VelocityRequest velocityRequest = new VelocityRequest();
        velocityRequest.setRequestType(VelocityConstants.POST_METHOD);
        velocityRequest.setUrl(invokeURL);
        velocityRequest.setAuthToken(sessionToken);
        velocityRequest.setContentType(VelocityConstants.XML_REQUEST_TYPE);
        velocityRequest.setPayload(verifyTxnRequestXML.getBytes());
        // Post request data to velocity Server
        VelocityResponse velocityResponse = VelocityConnection.connectPost(velocityRequest);
        velocityResponse = verifyVelocityResponse(velocityResponse, velocityRequest);
        // IF error in response
        if(velocityResponse.isError()){
            return velocityResponse;
        }else if(velocityResponse.getResult().contains(VelocityConstants.BANCARD_TRANSACTION_RESPONSE)){
            BankcardTransactionResponsePro bankcardTransactionResp = CommonUtils.generateBankcardTransactionResponsePro(velocityResponse.getResult());
            velocityResponse.setBankcardTransactionResponse(bankcardTransactionResp);
            return velocityResponse;
        }
        return velocityResponse;
    }

    /**
     * This method Authorize a payment_method for a particular amount. This Method create
     * corresponding XML for gateway request. This Method send Request to gateway and handle the
     * response.
     */
    public VelocityResponse authorize(AuthorizeTransaction authorizeTransaction) throws VelocityIllegalArgumentException, VelocityException, VelocityRestInvokeException, VelocityNotFoundException {
        CommonUtils.checkNullObj(authorizeTransaction);
        boolean isP2PE = false;
        String authorizeTxnRequestXML = null;
        if(authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData() != null && !authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().getValue().trim().isEmpty()){
            isP2PE = true;
        }
        if(isP2PE){
            // Getting the Authorize XML input for P2PE.
            P2PEAuthorizeRequestXML p2peAuthorizeRequestXML = new P2PEAuthorizeRequestXML();
            authorizeTxnRequestXML = p2peAuthorizeRequestXML.p2peAuthorizeXML(authorizeTransaction, appProfileId, merchantProfileId);
        }else{
            // Generating authorize XML input request.
            AuthorizeTransactionXML authorizeTxnRequest = new AuthorizeTransactionXML();
            authorizeTxnRequestXML = authorizeTxnRequest.authorizeXML(authorizeTransaction, appProfileId, merchantProfileId);
        }
        txnRequestLog = authorizeTxnRequestXML;
        // Invoking the Authorize request URL
        String invokeURL = getServiceUrl(isTestAccount) + "/Txn/" + workFlowId;
        VelocityRequest velocityRequest = new VelocityRequest();
        velocityRequest.setRequestType(VelocityConstants.POST_METHOD);
        velocityRequest.setUrl(invokeURL);
        velocityRequest.setAuthToken(sessionToken);
        velocityRequest.setContentType(VelocityConstants.XML_REQUEST_TYPE);
        velocityRequest.setPayload(authorizeTxnRequestXML.getBytes());
        // Post request data to velocity Server
        VelocityResponse velocityResponse = VelocityConnection.connectPost(velocityRequest);
        velocityResponse = verifyVelocityResponse(velocityResponse, velocityRequest);
        // IF error in response
        if(velocityResponse.isError()){
            return velocityResponse;
        }else if(velocityResponse.getResult().contains(VelocityConstants.BANCARD_TRANSACTION_RESPONSE)){
            BankcardTransactionResponsePro bankcardTransactionResp = CommonUtils.generateBankcardTransactionResponsePro(velocityResponse.getResult());
            velocityResponse.setBankcardTransactionResponse(bankcardTransactionResp);
            return velocityResponse;
        }
        return velocityResponse;
    }

    /**
     * This method will use to authorize transactions by performing a check on cardholder's funds
     * and reserves. The authorization amount is captured if sufficient funds are available
     */
    public VelocityResponse authorizeAndCapture(AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException {
        CommonUtils.checkNullObj(authorizeAndCaptureTransaction);
        boolean isP2PEenable = false;
        // Condition for P2PE.
        if(authorizeAndCaptureTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().getValue() != null && !authorizeAndCaptureTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().getValue().trim().isEmpty()){
            isP2PEenable = true;
        }
        // Generating authorizeAndCapture XML input request.
        String authorizeAndCaptureTxnRequestXML = null;
        if(isP2PEenable){
            P2PEAuthorizeAndCaptureRequestXML p2peAuthorizeAndCaptureRequestXML = new P2PEAuthorizeAndCaptureRequestXML();
            authorizeAndCaptureTxnRequestXML = p2peAuthorizeAndCaptureRequestXML.p2peAuthorizAndCaptureXML(authorizeAndCaptureTransaction, appProfileId, merchantProfileId);
        }else{
            AuthorizeAndCaptureRequestXML authorizeAndCaptureRequestXML = new AuthorizeAndCaptureRequestXML();
            authorizeAndCaptureTxnRequestXML = authorizeAndCaptureRequestXML.authorizeAndCaptureXML(authorizeAndCaptureTransaction, appProfileId, merchantProfileId);
        }
        txnRequestLog = authorizeAndCaptureTxnRequestXML;
        // Invoking URL for the XML input request
        String invokeURL = getServiceUrl(isTestAccount) + "/Txn/" + workFlowId;
        // Set request data
        VelocityRequest velocityRequest = new VelocityRequest();
        velocityRequest.setRequestType(VelocityConstants.POST_METHOD);
        velocityRequest.setUrl(invokeURL);
        velocityRequest.setAuthToken(sessionToken);
        velocityRequest.setContentType(VelocityConstants.XML_REQUEST_TYPE);
        velocityRequest.setPayload(authorizeAndCaptureTxnRequestXML.getBytes());
        // Post request data to velocity Server
        VelocityResponse velocityResponse = VelocityConnection.connectPost(velocityRequest);
        velocityResponse = verifyVelocityResponse(velocityResponse, velocityRequest);
        // IF error in response
        if(velocityResponse.isError()){
            return velocityResponse;
        }else if(velocityResponse.getResult().contains(VelocityConstants.BANCARD_TRANSACTION_RESPONSE)){
            BankcardTransactionResponsePro bankcardTransactionResp = CommonUtils.generateBankcardTransactionResponsePro(velocityResponse.getResult());
            velocityResponse.setBankcardTransactionResponse(bankcardTransactionResp);
            return velocityResponse;
        }
        return velocityResponse;
    }

    /***
     * This method will Captures an authorization. The default is to capture the full amount of the
     * authorization.
     */
    public VelocityResponse capture(ChangeTransaction captureTransaction) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException {
        CommonUtils.checkNullObj(captureTransaction);
        // Generating Capture XML input request.
        CaptureRequestXML captureRequestXML = new CaptureRequestXML();
        String captureTxnRequestXML = captureRequestXML.captureXML(captureTransaction, appProfileId);
        txnRequestLog = captureTxnRequestXML;
        // Invoking URL for the XML input request
        String invokeURL = getServiceUrl(isTestAccount) + "/Txn/" + workFlowId + "/" + captureTransaction.getDifferenceData().getTransactionId();
        /* Set request data */
        VelocityRequest velocityRequest = new VelocityRequest();
        velocityRequest.setRequestType(VelocityConstants.PUT_METHOD);
        velocityRequest.setUrl(invokeURL);
        velocityRequest.setAuthToken(sessionToken);
        velocityRequest.setContentType(VelocityConstants.XML_REQUEST_TYPE);
        velocityRequest.setPayload(captureTxnRequestXML.getBytes());
        // Post request data to velocity Server
        VelocityResponse velocityResponse = VelocityConnection.connectPost(velocityRequest);
        velocityResponse = verifyVelocityResponse(velocityResponse, velocityRequest);
        // IF error in response
        if(velocityResponse.isError()){
            return velocityResponse;
        }else{
            BankcardCaptureResponse bankcardCaptureResponse = CommonUtils.generateBankcardCaptureResponse(velocityResponse.getResult());
            velocityResponse.setBankcardCaptureResponse(bankcardCaptureResponse);
            return velocityResponse;
        }
    }

    /**
     * This method will use to release cardholder's funds by performing a void (Credit Card) or
     * reversal (PIN Debit) on a previously authorized transaction that has not been captured
     * (flagged) for settlement.
     */
    public VelocityResponse undo(Undo undoTransaction) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException {
        CommonUtils.checkNullObj(undoTransaction);
        // Generating Undo XML input request.
        UndoRequestXML undoRequestXML = new UndoRequestXML();
        String undoTxnRequestXML = undoRequestXML.undoXML(undoTransaction, appProfileId, merchantProfileId);
        txnRequestLog = undoTxnRequestXML;
        /* Invoking URL for the XML input request */
        String invokeURL = getServiceUrl(isTestAccount) + "/Txn/" + workFlowId + "/" + undoTransaction.getTransactionId();
        // Set request data
        VelocityRequest velocityRequest = new VelocityRequest();
        velocityRequest.setRequestType(VelocityConstants.PUT_METHOD);
        velocityRequest.setUrl(invokeURL);
        velocityRequest.setAuthToken(sessionToken);
        velocityRequest.setContentType(VelocityConstants.XML_REQUEST_TYPE);
        velocityRequest.setPayload(undoTxnRequestXML.getBytes());
        // Post request data to velocity Server
        VelocityResponse velocityResponse = VelocityConnection.connectPost(velocityRequest);
        velocityResponse = verifyVelocityResponse(velocityResponse, velocityRequest);
        // IF error in response
        if(velocityResponse.isError()){
            return velocityResponse;
        }else if(velocityResponse.getResult().contains(VelocityConstants.BANCARD_TRANSACTION_RESPONSE)){
            BankcardTransactionResponsePro bankcardTransactionResp = CommonUtils.generateBankcardTransactionResponsePro(velocityResponse.getResult());
            velocityResponse.setBankcardTransactionResponse(bankcardTransactionResp);
            return velocityResponse;
        }
        return null;
    }

    /**
     * This method will use to Adjust a transaction If, that transaction has not been yet captured
     * and settled. It can be Adjust to A previously authorized amount (incremental or reversal)
     * prior to capture and settlement.
     */
    public VelocityResponse adjust(Adjust adjustTransaction) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException {
        CommonUtils.checkNullObj(adjustTransaction);
        // Generating Adjust XML input request.
        AdjustRequestXML adjustRequestXML = new AdjustRequestXML();
        String adjustTxnRequestXML = adjustRequestXML.adjustXML(adjustTransaction, appProfileId, merchantProfileId);
        txnRequestLog = adjustTxnRequestXML;
        // Invoking URL for the XML input request
        String invokeURL = getServiceUrl(isTestAccount) + "/Txn/" + workFlowId + "/" + adjustTransaction.getDifferenceData().getTransactionId();
        // Set request data
        VelocityRequest velocityRequest = new VelocityRequest();
        velocityRequest.setRequestType(VelocityConstants.PUT_METHOD);
        velocityRequest.setUrl(invokeURL);
        velocityRequest.setAuthToken(sessionToken);
        velocityRequest.setContentType(VelocityConstants.XML_REQUEST_TYPE);
        velocityRequest.setPayload(adjustTxnRequestXML.getBytes());
        // Post request data to velocity Server
        VelocityResponse velocityResponse = VelocityConnection.connectPost(velocityRequest);
        velocityResponse = verifyVelocityResponse(velocityResponse, velocityRequest);
        // IF error in response
        if(velocityResponse.isError()){
            return velocityResponse;
        }else if(velocityResponse.getResult().contains(VelocityConstants.BANCARD_TRANSACTION_RESPONSE)){
            BankcardTransactionResponsePro bankcardTransactionResp = CommonUtils.generateBankcardTransactionResponsePro(velocityResponse.getResult());
            velocityResponse.setBankcardTransactionResponse(bankcardTransactionResp);
            return velocityResponse;
        }
        return null;
    }

    /**
     * This method will use to perform a linked credit to a cardholder's account from the merchant's
     * account based on a previously authorized and settled transaction.
     */
    public VelocityResponse returnById(ReturnById returnByIdTransaction) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException {
        CommonUtils.checkNullObj(returnByIdTransaction);
        // Generating ReturnById XML input request.
        ReturnByIdXML returnByIdXML = new ReturnByIdXML();
        String returnByIdTxnRequestXML = returnByIdXML.returnByIdXML(returnByIdTransaction, appProfileId, merchantProfileId);
        txnRequestLog = returnByIdTxnRequestXML;
        // Invoking URL for the XML input request
        String invokeURL = getServiceUrl(isTestAccount) + "/Txn/" + workFlowId;
        /* Set request data */
        VelocityRequest velocityRequest = new VelocityRequest();
        velocityRequest.setRequestType(VelocityConstants.POST_METHOD);
        velocityRequest.setUrl(invokeURL);
        velocityRequest.setAuthToken(sessionToken);
        velocityRequest.setContentType(VelocityConstants.XML_REQUEST_TYPE);
        velocityRequest.setPayload(returnByIdTxnRequestXML.getBytes());
        // Post request data to velocity Server
        VelocityResponse velocityResponse = VelocityConnection.connectPost(velocityRequest);
        velocityResponse = verifyVelocityResponse(velocityResponse, velocityRequest);
        // IF error in response
        if(velocityResponse.isError()){
            return velocityResponse;
        }else if(velocityResponse.getResult().contains(VelocityConstants.BANCARD_TRANSACTION_RESPONSE)){
            BankcardTransactionResponsePro bankcardTransactionResp = CommonUtils.generateBankcardTransactionResponsePro(velocityResponse.getResult());
            velocityResponse.setBankcardTransactionResponse(bankcardTransactionResp);
            return velocityResponse;
        }
        return null;
    }

    /**
     * This method will use to perform an "unlinked", or stand alone, credit to a cardholder's
     * account from the merchant's account. This operation is useful when a return transaction is
     * not associated with a previously authorized and settled transaction.
     */
    public VelocityResponse returnUnlinked(ReturnTransaction returnUnlinkedTransaction) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException {
        CommonUtils.checkNullObj(returnUnlinkedTransaction);
        boolean isP2PEenabled = false;
        if(returnUnlinkedTransaction.getTransaction().getTenderData().getSecurePaymentAccountData() != null && !returnUnlinkedTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().getValue().trim().isEmpty()){
            isP2PEenabled = true;
        }
        // Generating P2PEReturnUnlinked XML input request.
        String returnUnlinkedTxnRequestXML = null;
        if(isP2PEenabled){
            P2PEReturnUnlinkedRequestXML p2peReturnUnlinkedRequestXML = new P2PEReturnUnlinkedRequestXML();
            returnUnlinkedTxnRequestXML = p2peReturnUnlinkedRequestXML.p2peReturnUnlinkedXML(returnUnlinkedTransaction, appProfileId, merchantProfileId);
        }else{
            // Generating ReturnUnlinked XML input request.
            ReturnUnlinkedRequestXML returnUnlinkedRequestXML = new ReturnUnlinkedRequestXML();
            returnUnlinkedTxnRequestXML = returnUnlinkedRequestXML.returnUnlinkedXML(returnUnlinkedTransaction, appProfileId, merchantProfileId);
        }
        txnRequestLog = returnUnlinkedTxnRequestXML;
        // Invoking URL for the XML input request
        String invokeURL = getServiceUrl(isTestAccount) + "/Txn/" + workFlowId;
        // Set request data
        VelocityRequest velocityRequest = new VelocityRequest();
        velocityRequest.setRequestType(VelocityConstants.POST_METHOD);
        velocityRequest.setUrl(invokeURL);
        velocityRequest.setAuthToken(sessionToken);
        velocityRequest.setContentType(VelocityConstants.XML_REQUEST_TYPE);
        velocityRequest.setPayload(returnUnlinkedTxnRequestXML.getBytes());
        // Post request data to velocity Server
        VelocityResponse velocityResponse = VelocityConnection.connectPost(velocityRequest);
        velocityResponse = verifyVelocityResponse(velocityResponse, velocityRequest);
        // IF error in response
        if(velocityResponse.isError()){
            return velocityResponse;
        }else if(velocityResponse.getResult().contains(VelocityConstants.BANCARD_TRANSACTION_RESPONSE)){
            BankcardTransactionResponsePro bankcardTransactionResp = CommonUtils.generateBankcardTransactionResponsePro(velocityResponse.getResult());
            velocityResponse.setBankcardTransactionResponse(bankcardTransactionResp);
            return velocityResponse;
        }
        return null;
    }
    
    /**
     * This method queries the specified transactions and returns both summary details
     * and full transaction details.
     */
    public VelocityResponse queryTransactionsDetail(QueryTransactionsDetail queryTransactionsDetail) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException {
        CommonUtils.checkNullObj(queryTransactionsDetail);
        // Generating QueryTransactionDetail JSON input request.
        String qtdJSONRequest = generateQueryTransactionDetailRequestJSONInput(queryTransactionsDetail);
        txnRequestLog = qtdJSONRequest;
        // Invoking URL for the JSON input request
        String invokeURL = getServiceUrl(isTestAccount) + "/DataServices/TMS/transactionsDetail";
        // Set request data
        VelocityRequest velocityRequest = new VelocityRequest();
        velocityRequest.setRequestType(VelocityConstants.POST_METHOD);
        velocityRequest.setUrl(invokeURL);
        velocityRequest.setAuthToken(sessionToken);
        velocityRequest.setContentType(VelocityConstants.JSON_REQUEST_TYPE);
        velocityRequest.setPayload(qtdJSONRequest.getBytes());
        // Post request data to velocity Server
        VelocityResponse velocityResponse = VelocityConnection.connectPost(velocityRequest);
        velocityResponse = verifyVelocityResponse(velocityResponse, velocityRequest);
        // IF error in response
        if(velocityResponse.isError()){
            return velocityResponse;
        }else{
            LOG.debug("generateVelocityResponse Velocity Response for BankcardTransactionResponsePro >>>>>>>>>>>>> ");
            // Creating the Gson instance
            Gson gson = new Gson();
            if(velocityResponse.getResult() != null && velocityResponse.getResult().contains(VelocityConstants.ERROR_ID)){
                // Converting the JSON response object to java object.
                JsonErrorResponse jsonErrorResponse = gson.fromJson(velocityResponse.getResult(), JsonErrorResponse.class);
                velocityResponse.setJsonErrorResponse(jsonErrorResponse);
                return velocityResponse;
            }else{
                // Converting the JSON response object to java object.
                TransactionDetail[] arrTransactionDetail = gson.fromJson(velocityResponse.getResult(), TransactionDetail[].class);
                // Storing the TransactionDetail object as List type
                List<TransactionDetail> transactionDetailList = Arrays.asList(arrTransactionDetail);
                // Populating the QueryTransactionDetailResponse for the response.
                velocityResponse.setTransactionDetailList(transactionDetailList);
                return velocityResponse;
            }
        }
    }

    /**
     * This method is used to flag all transactions for settlement 
     * that have been successfully authorized using the Authorize operation.
     */
    public VelocityResponse captureAll(CaptureAll captureAllTransaction) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException {
        CommonUtils.checkNullObj(captureAllTransaction);
        // Generating Capture XML input request.
        CaptureAllRequestXML captureAllRequestXML = new CaptureAllRequestXML();
        String captureAllTxnRequestXML = captureAllRequestXML.captureAllXML(captureAllTransaction, appProfileId, merchantProfileId);
        txnRequestLog = captureAllTxnRequestXML;
        // Invoking URL for the XML input request
        String invokeURL = getServiceUrl(isTestAccount) + "/Txn/" + workFlowId;
        // Set request data
        VelocityRequest velocityRequest = new VelocityRequest();
        velocityRequest.setRequestType(VelocityConstants.PUT_METHOD);
        velocityRequest.setUrl(invokeURL);
        velocityRequest.setAuthToken(sessionToken);
        velocityRequest.setContentType(VelocityConstants.XML_REQUEST_TYPE);
        velocityRequest.setPayload(captureAllTxnRequestXML.getBytes());
        // Post request data to velocity Server
        VelocityResponse velocityResponse = VelocityConnection.connectPost(velocityRequest);
        velocityResponse = verifyVelocityResponse(velocityResponse, velocityRequest);
        // IF error in response then return response with having errors
        if(velocityResponse.isError()){
            return velocityResponse;
        }else{
            /*
             * Generating ArrayOfResponse instance from verify request response
             * string.
             */
            ArrayOfResponse arrayOfResponse = CommonUtils.generateArrayOfResponse(velocityResponse.getResult());
            velocityResponse.setArrayOfResponse(arrayOfResponse);
            return velocityResponse;
        }
    }

    /**
     * Method will check VelocityResponse if contains error then it goes to
     * check if session expired then again going to call SignOn(..) method to
     * get session Id
     */
    private VelocityResponse verifyVelocityResponse(VelocityResponse velocityResponse, VelocityRequest velocityRequest) throws VelocityIllegalArgumentException, VelocityException, VelocityRestInvokeException, VelocityNotFoundException {
        if(velocityResponse != null){
            // Checking the error code for session expired then again trying to generate new
            // session token.
            if(String.valueOf(velocityResponse.getStatusCode()).equals(VelocityConstants.SESSION_EXPIRED_ERROR_CODE)){
                LOG.debug("Session Token has been Expired.....");
                LOG.debug("Getting a new Session token.....");
                // Call signOn() to get new session token
                this.sessionToken = signOn(this.identityToken);
                // set new session key
                velocityRequest.setAuthToken(this.sessionToken);
                // Post data to server again with new session token
                velocityResponse = VelocityConnection.connectPost(velocityRequest);
                return velocityResponse;
            }
            this.txnResponseLog = velocityResponse.getResult();
            if(velocityResponse.getResult() != null && (velocityResponse.getResult().contains(VelocityConstants.ERROR_RESPONSE) ||
                    velocityResponse.getStatusCode() == VelocityConstants.SERVER_BAD_REQUEST_STATUS_CODE)){
                // convert velocity server response to ErrorResponse Object
                ErrorResponse errorResponse = CommonUtils.generateErrorResponse(velocityResponse.getResult());
                velocityResponse.setErrorResponse(errorResponse);
                velocityResponse.setError(Boolean.TRUE);
            }
            return velocityResponse;
        }else{
            throw new VelocityRestInvokeException("Error occured from service request to velocity server");
        }
    }

    /**
     * This will use to get server URL based on 'isTestAccount' parameter either
     * [Testing/Production]
     */
    public String getServiceUrl(boolean isTestAccount) throws VelocityException, VelocityIllegalArgumentException, VelocityNotFoundException {
        if(!isTestAccount){
            return velocityConfigManager.getProperty(VelocityConstants.VELOCITY_PRODUCTION_SERVER);
        }else{
            return velocityConfigManager.getProperty(VelocityConstants.VELOCITY_TEST_SERVER);
        }
    }

    /** This method generates the JSON input for QueryTransactionDetail request */
    private String generateQueryTransactionDetailRequestJSONInput(QueryTransactionsDetail queryTransactionsDetail) throws VelocityException, VelocityIllegalArgumentException {
        Gson gson = new Gson();
        String qtdJson = gson.toJson(queryTransactionsDetail);
        return qtdJson;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getTxnRequestLog() {
        return txnRequestLog;
    }

    public void setTxnRequestLog(String txnRequestLog) {
        this.txnRequestLog = txnRequestLog;
    }

    public String getTxnResponseLog() {
        return txnResponseLog;
    }

    public void setTxnResponseLog(String txnResponseLog) {
        this.txnResponseLog = txnResponseLog;
    }
}
