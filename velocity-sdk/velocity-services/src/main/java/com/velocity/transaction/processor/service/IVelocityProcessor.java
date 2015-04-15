package com.velocity.transaction.processor.service;

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
import com.velocity.model.request.capture.ChangeTransaction;
import com.velocity.model.response.VelocityResponse;
import com.velocity.models.transactions.query.QueryTransactionsDetail;

/**
 * Contains methods for processing the Velocity Transactions
 * 
 * @author Vimal Kumar
 * @date 07-January-2015
 */
public interface IVelocityProcessor {

    /**
     * This method gets the URL for the REST server.
     * 
     * @author anitk
     * @throws VelocityException - thrown when any exception occurs in setting the velocity rest
     *         server URL.
     * @throws VelocityIllegalArgumentException - thrown when any illegalArgument is passed.
     * @throws VelocityNotFoundException - thrown when the requested resource is not found.
     */
    public String getServiceUrl(boolean isTestAccount) throws VelocityException, VelocityIllegalArgumentException, VelocityNotFoundException;
    /**
     * This method will used to get session token from velocity server based on provided token
     * value.
     * 
     * @author anitk.
     * @param identityToken - Identity token for Velocity server.
     * @return String - Returns the session token from the Velocity server.
     * @throws VelocityIllegalArgumentException - thrown when illegal Argument is passed.
     * @throws VelocityRestInvokeException - thrown when the Velocity SessionToken is not found.
     */
    public String signOn(String identityToken) throws VelocityIllegalArgumentException, VelocityRestInvokeException, VelocityException, VelocityNotFoundException;
    /**
     * This method will use to Verify the card detail and address detail of customer. This method
     * send request to server by generating a request XML and handle response back from server.
     * 
     * @author anitk
     * @param authorizeTransaction - Stores the value for verifying request for AuthorizeTransaction
     * @return VelocityResponse - Instance of the type VelocityResponse
     * @throws VelocityException - thrown when the exception occurs during the transaction.
     * @throws VelocityIllegalArgumentException - thrown when Illegal argument is supplied.
     * @throws VelocityNotFoundException - thrown when the requested resource is not found.
     * @throws VelocityRestInvokeException -thrown when exception occurs in invoking the request.
     */
    public VelocityResponse verify(AuthorizeTransaction authorizeTransaction) throws VelocityException, VelocityIllegalArgumentException, VelocityNotFoundException, VelocityRestInvokeException;
    /**
     * This method Authorize a payment_method for a particular amount. This Method create
     * corresponding XML for gateway request. This Method send Request to gateway and handle the
     * response.
     * 
     * @author Vimal Kumar
     * @param authorizeTransaction - stores the value for AuthorizeTransaction.
     * @return VelocityResponse - Instance of the type VelocityResponse
     * @throws VelocityException - thrown when Exception occurs at invoking the AuthorizeRequest.
     * @throws VelocityIllegalArgumentException - thrown when null or bad data is passed.
     * @throws VelocityRestInvokeException - thrown when Rest methods invoke exception occured.
     * @throws VelocityNotFoundException - thrown when Velocity IO exception occured.
     */
    public VelocityResponse authorize(AuthorizeTransaction authorizeTransaction) throws VelocityIllegalArgumentException, VelocityException, VelocityRestInvokeException, VelocityNotFoundException;
    /**
     * This method will Captures an authorization. The default is to capture the full amount of the
     * authorization.
     * 
     * @author Vimal Kumar
     * @param captureTransaction - stores the value for ChangeTransaction
     * @return VelocityResponse - Instance of the type VelocityResponse
     * @throws VelocityException - thrown when Exception occurs at invoking the CaptureRequest
     * @throws VelocityIllegalArgumentException - thrown when null or bad data is passed.
     * @throws VelocityNotFoundException - thrown when the requested resource is not found.
     * @throws VelocityRestInvokeException - thrown when exception occurs in invoking the request.
     */
    public VelocityResponse capture(ChangeTransaction captureTransaction) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException;
    /**
     * This method will use to release cardholder's funds by performing a void (Credit Card) or
     * reversal (PIN Debit) on a previously authorized transaction that has not been captured
     * (flagged) for settlement.
     * 
     * @author Vimal Kumar
     * @param undoTransaction - Stores the value for UndoTransaction
     * @return VelocityResponse - Instance of the type VelocityResponse
     * @throws VelocityException - thrown when Exception occurs at invoking the UndoRequest.
     * @throws VelocityIllegalArgumentException - thrown when null or bad data is passed.
     * @throws VelocityNotFoundException - thrown when the requested resource is not found.
     * @throws VelocityRestInvokeException - thrown when exception occurs in invoking the request.
     */
    public VelocityResponse undo(Undo undoTransaction) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException;
    /**
     * This method will use to authorize transactions by performing a check on cardholder's funds
     * and reserves. The authorization amount is captured if sufficient funds are available
     * 
     * @author Vimal Kumar
     * @param authorizeAndCaptureTransaction - the type of AuthorizeAndCaptureTransaction.
     * @return VelocityResponse - Instance of the type VelocityResponse
     * @throws VelocityIllegalArgumentException - thrown when null or bad data is passed.
     * @throws VelocityException VelocityGenericException - thrown when Exception occurs at invoking
     *         the AuthorizeAndCaptureRequest.
     * @throws VelocityNotFoundException - thrown when the requested resource is not found.
     * @throws VelocityRestInvokeException - thrown when exception occurs in invoking the request.
     */
    public VelocityResponse authorizeAndCapture(AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException;
    /**
     * This method will use to Adjust a transaction If, that transaction has not been yet captured
     * and settled. It can be Adjust to A previously authorized amount (incremental or reversal)
     * prior to capture and settlement.
     * 
     * @author Vimal Kumar
     * @param adjustTransaction - holds the data for AdjustRequest.
     * @return VelocityResponse - Instance of the type VelocityResponse
     * @throws VelocityException - thrown when Exception occurs at invoking the AdjustRequest
     * @throws VelocityIllegalArgumentException - thrown when null or bad data is passed.
     * @throws VelocityNotFoundException - thrown when the requested resource is not found.
     * @throws VelocityRestInvokeException - thrown when exception occurs in invoking the request.
     */
    public VelocityResponse adjust(Adjust adjustTransaction) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException;
    /**
     * This method will use to perform a linked credit to a cardholder's account from the merchant's
     * account based on a previously authorized and settled transaction.
     * 
     * @author Vimal Kumar
     * @param returnByIdTransaction - object for ReturnById Transaction
     * @return VelocityResponse - Instance of the type VelocityResponse
     * @throws VelocityException - thrown when Exception occurs at generating the the
     *         ReturnByIdRequest
     * @throws VelocityIllegalArgumentException - thrown when null or bad data is passed.
     * @throws VelocityNotFoundException - thrown when the requested resource is not found.
     * @throws VelocityRestInvokeException - thrown when exception occurs in invoking the request.
     */
    public VelocityResponse returnById(ReturnById returnByIdTransaction) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException;
    /**
     * This method will use to perform an "unlinked", or stand alone, credit to a cardholder's
     * account from the merchant's account. This operation is useful when a return transaction is
     * not associated with a previously authorized and settled transaction.
     * 
     * @author Vimal Kumar
     * @param returnUnlinkedTransaction - stores ReturnUnlinked object data
     * @return VelocityResponse - Instance of the type VelocityResponse
     * @throws VelocityException - thrown when Exception occurs at invoking the
     *         ReturnUnlinkedRequest
     * @throws VelocityIllegalArgumentException - thrown when null or bad data is passed.
     * @throws VelocityNotFoundException - thrown when the requested resource is not found.
     * @throws VelocityRestInvokeException - thrown when exception occurs in invoking the request.
     */
    public VelocityResponse returnUnlinked(ReturnTransaction returnUnlinkedTransaction) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException;
    /**
     * This method queries the specified transactions and returns both summary details
     * and full transaction details.
     * 
     * @author Vimal Kumar
     * @param queryTransactionsDetail - stores the value for QueryTransactionsDetail
     * @throws VelocityException - thrown when Exception occurs at invoking the
     *         QueryTransactionsDetailRequest
     * @return VelocityResponse - Instance of the type VelocityResponse
     * @throws VelocityIllegalArgumentException - thrown when null or bad data is passed.
     * @throws VelocityNotFoundException - thrown when the requested resource is not found.
     * @throws VelocityRestInvokeException - thrown when exception occurs in invoking the request.
     */
    public VelocityResponse queryTransactionsDetail(QueryTransactionsDetail queryTransactionsDetail) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException;
    /**
     * This method is used to flag all transactions for settlement 
     * that have been successfully authorized using the Authorize operation.
     * 
     * @author Vimal Kumar
     * @param captureAllTransaction - stores the value for CaptureAll transaction
     * @return VelocityResponse - Instance of the type VelocityResponse
     * @throws VelocityIllegalArgumentException - thrown when null or bad data is passed.
     * @throws VelocityException -thrown when Exception occurs at invoking the CaptureAllRequest
     * @throws VelocityNotFoundException - thrown when the requested resource is not found.
     * @throws VelocityRestInvokeException - thrown when exception occurs in invoking the request.
     */
    public VelocityResponse captureAll(CaptureAll captureAllTransaction) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException;
}
