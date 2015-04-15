/**
 * 
 */
package com.velocity.transaction.processor;

import com.velocity.exceptions.VelocityGenericException; 
import com.velocity.exceptions.VelocityIllegalArgument;
import com.velocity.exceptions.VelocityNotFound;
import com.velocity.exceptions.VelocityRestInvokeException;
import com.velocity.models.request.verify.AuthorizeTransaction;
import com.velocity.models.response.VelocityResponse;



/**
 * This interfaces defines the methods for processing the Velocity Transactions.
 * @author vimalk2
 * @date 07-January-2015
 */
public interface BaseProcessor {

	/**
	 * This method gets the URL for the REST server.
	 * @author anitk
	 * @throws VelocityGenericException - thrown when any exception occurs in setting the velocity rest server URL. 
	 * @throws VelocityIllegalArgument - thrown when any illegalArgument is passed.
	 * @throws VelocityNotFound - thrown when the requested resource is not found.
	 */
	public void setVelocityRestServerURL() throws VelocityGenericException, VelocityIllegalArgument, VelocityNotFound;

	/**
	 * This method sets the SessionToken for VelocityProcessor
	 * @author anitk.
	 * @param identityToken - Identity token for Velocity server.
	 * @return String - Returns the session token from the Velocity server.
	 * @throws VelocityIllegalArgument - thrown when illegal Argument is passed.
	 * @throws VelocityRestInvokeException - thrown when the Velocity SessionToken is not found.
	 */
	public String invokeSignOn(String identityToken) throws VelocityIllegalArgument, VelocityRestInvokeException;
	
	/**
	 * This method invokes the Verify operation on velocity REST server.
	 * @author anitk
	 * @param  authorizeTransaction - Stores the value for verifying request for AuthorizeTransaction
	 * @return VelocityResponse - Instance of the type VelocityResponse
	 * @throws VelocityGenericException - thrown when the exception occurs during the transaction. 
	 * @throws VelocityIllegalArgument - thrown when Illegal argument is supplied.
	 * @throws VelocityNotFound - thrown when the requested resource is not found.
	 * @throws VelocityRestInvokeException -thrown when exception occurs in invoking the request.
	 */
	public VelocityResponse invokeVerifyRequest(AuthorizeTransaction authorizeTransaction) throws VelocityGenericException, VelocityIllegalArgument, VelocityNotFound, VelocityRestInvokeException;

	/**
	 * This method invokes the Authorize operation on velocity REST server.
	 * @author vimalk2
	 * @param  authorizeTransaction - stores the value for AuthorizeTransaction.
	 * @return VelocityResponse - Instance of the type VelocityResponse
	 * @throws VelocityGenericException - thrown when Exception occurs at invoking the AuthorizeRequest.
	 * @throws VelocityIllegalArgument - thrown when null or bad data is passed.
	 * @throws VelocityRestInvokeException - thrown when Rest methods invoke exception occured.
	 * @throws VelocityNotFound - thrown when Velocity IO exception occured.
	 */
	public VelocityResponse invokeAuthorizeRequest(com.velocity.models.request.authorize.AuthorizeTransaction authorizeTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityRestInvokeException, VelocityNotFound;

	/**
	 * This method invokes the Capture operation on velocity REST server.
	 * @author vimalk2
	 * @param  captureTransaction - stores the value for ChangeTransaction
	 * @return VelocityResponse - Instance of the type VelocityResponse
	 * @throws VelocityGenericException - thrown when Exception occurs at invoking the CaptureRequest
	 * @throws VelocityIllegalArgument - thrown when null or bad data is passed.
	 * @throws VelocityNotFound - thrown when the requested resource is not found.
	 * @throws VelocityRestInvokeException - thrown when exception occurs in invoking the request. 
	 */
	public VelocityResponse invokeCaptureRequest(com.velocity.models.request.capture.ChangeTransaction captureTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException ;

	/**
	 * This method invokes the Undo operation on the Velocity REST server.
	 * @author vimalk2
	 * @param  undoTransaction - Stores the value for UndoTransaction
	 * @return VelocityResponse - Instance of the type VelocityResponse
	 * @throws VelocityGenericException - thrown when Exception occurs at invoking the UndoRequest.
	 * @throws VelocityIllegalArgument - thrown when null or bad data is passed.
	 * @throws VelocityNotFound - thrown when the requested resource is not found.
	 * @throws VelocityRestInvokeException - thrown when exception occurs in invoking the request. 
	 */
	public VelocityResponse invokeUndoRequest(com.velocity.models.request.undo.Undo undoTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException;

	/**
	 * This method invokes the AuthorizeAndCapture operation on the Velocity REST server.
	 * @author vimalk2
	 * @param authorizeAndCaptureTransaction -  the type of AuthorizeAndCaptureTransaction.
	 * @return VelocityResponse - Instance of the type VelocityResponse
	 * @throws VelocityIllegalArgument - thrown when null or bad data is passed.
	 * @throws VelocityGenericException VelocityGenericException - thrown when Exception occurs at invoking the AuthorizeAndCaptureRequest.
	 * @throws VelocityNotFound - thrown when the requested resource is not found.
	 * @throws VelocityRestInvokeException - thrown when exception occurs in invoking the request.
	 */
	public VelocityResponse invokeAuthorizeAndCaptureRequest(com.velocity.models.request.authorizeAndCapture.AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException;

	/**
	 * This method invokes the Adjust operation on the Velocity REST server.
	 * @author vimalk2
	 * @param  adjustTransaction - holds the data for AdjustRequest.
	 * @return VelocityResponse - Instance of the type VelocityResponse
	 * @throws VelocityGenericException - thrown when Exception occurs at invoking the AdjustRequest
	 * @throws VelocityIllegalArgument - thrown when null or bad data is passed.
	 * @throws VelocityNotFound - thrown when the requested resource is not found.
	 * @throws VelocityRestInvokeException - thrown when exception occurs in invoking the request.
	 */
	public VelocityResponse invokeAdjustRequest(com.velocity.models.request.adjust.Adjust adjustTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException;

	/**
	 * This method invokes the ReturnById operation on velocity REST server.
	 * @author vimalk2
	 * @param  returnByIdTransaction - object for ReturnById Transaction
	 * @return VelocityResponse - Instance of the type VelocityResponse
	 * @throws VelocityGenericException - thrown when Exception occurs at generating the the ReturnByIdRequest
	 * @throws VelocityIllegalArgument - thrown when null or bad data is passed.
	 * @throws VelocityNotFound - thrown when the requested resource is not found.
	 * @throws VelocityRestInvokeException - thrown when exception occurs in invoking the request.
	 */
	public VelocityResponse invokeReturnByIdRequest(com.velocity.models.request.returnById.ReturnById returnByIdTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException;

	/**
	 * This method invokes the ReturnUnlinked operation on velocity REST server.
	 * @author vimalk2
	 * @param  returnUnlinkedTransaction - stores ReturnUnlinked object data 
	 * @return VelocityResponse - Instance of the type VelocityResponse
	 * @throws VelocityGenericException - thrown when Exception occurs at invoking the ReturnUnlinkedRequest
	 * @throws VelocityIllegalArgument - thrown when null or bad data is passed.
	 * @throws VelocityNotFound - thrown when the requested resource is not found.
	 * @throws VelocityRestInvokeException - thrown when exception occurs in invoking the request.
	 */
	public VelocityResponse invokeReturnUnlinkedRequest(com.velocity.models.request.returnUnLinked.ReturnTransaction returnUnlinkedTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException;


}
