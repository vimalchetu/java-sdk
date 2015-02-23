/**
 * 
 */
package com.northamericanbancard.servlet;

import java.io.IOException; 

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.northamericanbancard.constants.NABConstants;
import com.northamericanbancard.logger.AppLogger;
import com.velocity.enums.VelocityEnums;
import com.velocity.models.request.adjust.Adjust;
import com.velocity.models.request.authorizeAndCapture.AuthorizeAndCaptureTransaction;
import com.velocity.models.request.capture.ChangeTransaction;
import com.velocity.models.request.returnById.ReturnById;
import com.velocity.models.request.returnUnLinked.ReturnTransaction;
import com.velocity.models.request.undo.Undo;
import com.velocity.models.request.verify.AuthorizeTransaction;
import com.velocity.models.response.VelocityResponse;
import com.velocity.transaction.processor.velocity.VelocityProcessor;



/**
 * This class processes the incoming request from the Velocity sample TestApp
 * @author anitk
 * @date 19-January-2015
 */
public class NABClientServlet extends HttpServlet {

	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
			{
		AppLogger.logDebug(getClass(), "service", "Entering...");
		/* Set response content type */
		resp.setContentType("text/html");

		try {

			String identityToken = req.getParameter("identityToken");

			String workFlowId = req.getParameter("workFlowId");

			String appProfileId = req.getParameter("appProfileId");

			String merchantProfileId = req.getParameter("merchantProfileId");

			String enableTestMode = req.getParameter("enableTestMode");

			String txnName = req.getParameter("txn_name");

			String paymentAccountDataToken = null;

			VelocityResponse velocityResponse = null;

			HttpSession session = req.getSession(true);

			AppLogger.logDebug(getClass(), "service", "enableTestMode >>>>>>>>>>>> "+enableTestMode);

			boolean isTestAccount = (enableTestMode != null) ? true : false; 

			AppLogger.logDebug(getClass(), "service", "isTestAccount >>>>>>>>>>>> "+isTestAccount);
			
			String sessionToken = null;
			
			if(session.getAttribute("sessionToken") != null)
			{
				sessionToken = (String) session.getAttribute("sessionToken");
			}
			
			/* Creating the VelocityProcessor object */
			VelocityProcessor velocityProcessor = new VelocityProcessor(sessionToken, identityToken, appProfileId, merchantProfileId, workFlowId, isTestAccount);
			AppLogger.logDebug(getClass(), "service", "Session token >>>>>>>>>>>> "+velocityProcessor.getSessionToken());
			/* Setting the attribute to the session for encrypted SessionToken */
			session.setAttribute("sessionToken", velocityProcessor.getSessionToken());

			/* Setting the attribute to the session for identityToken */
			session.setAttribute("identityToken", identityToken);

			/*Invokes the Verify transaction from the VelocityTestApp*/
			if(txnName != null && txnName.equalsIgnoreCase(NABConstants.VERIFY))
			{
				velocityResponse = velocityProcessor.invokeVerifyRequest(getVerifyRequestAuthorizeTransactionInstance(req));
				if(velocityResponse != null && velocityResponse.getBankcardTransactionResponse() != null)
				{
					paymentAccountDataToken = velocityResponse.getBankcardTransactionResponse().getPaymentAccountDataToken();
					session.setAttribute("paymentAccountDataToken", paymentAccountDataToken);
				}
				session.setAttribute("verifyRequestXML", velocityProcessor.getTxnRequestXML());
				session.setAttribute("verifyResponseXML", velocityProcessor.getTxnResponseXML());
				session.setAttribute("txnName", null);
				AppLogger.logDebug(this.getClass(), "Verify", "VelocityResponse >>>>>>>>>> "+ velocityResponse);
			}
			/* Invokes the Authorize transaction from the VelocityTestApp */
			else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.AUTHORIZE))
			{
				velocityResponse = velocityProcessor.invokeAuthorizeRequest(getAuthorizeRequestAuthorizeTransactionInstance(req));
				if(velocityResponse.getBankcardTransactionResponse() != null)
				{
					session.setAttribute("transactionId", velocityResponse.getBankcardTransactionResponse().getTransactionId());
				}
				session.setAttribute("txnName", "Authorize");
				AppLogger.logDebug(this.getClass(), "Authorize", "VelocityResponse >>>>>>>>>> "+ velocityResponse);

			}
			/* Invokes the AUthorize transaction without token from the VelocityTestApp */
			else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.AUTHORIZEWOTOKEN))
			{
				com.velocity.models.request.authorize.AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance(req);
				objAuthorizeTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setValue("");
				velocityResponse = velocityProcessor.invokeAuthorizeRequest(objAuthorizeTransaction);
				if(velocityResponse.getBankcardTransactionResponse() != null)
				{
					session.setAttribute("transactionId", velocityResponse.getBankcardTransactionResponse().getTransactionId());
				}
				AppLogger.logDebug(this.getClass(), "AuthorizeWotoken", "VelocityResponse >>>>>>>>>> "+ velocityResponse);
				session.setAttribute("txnName", "AuthorizeWithoutToken");

			}
			/* Invokes the AUthorizeAndCapture transaction from the VelocityTestApp */
			else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.AUTHORIZEANDCAPTURE))
			{	
				velocityResponse = velocityProcessor.invokeAuthorizeAndCaptureRequest(getAuthorizeAndCaptureTransactionInstance(req));
				session.setAttribute("txnName", "AuthorizeAndCapture");
				AppLogger.logDebug(this.getClass(), "AuthAndCapture", "VelocityResponse >>>>>>>>>> "+ velocityResponse);
			}
			/* Invokes the AUthorizeAndCapture transaction without token from the VelocityTestApp */
			else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.AUTHORIZEANDCAPTUREWOTOKEN))
			{	
				AuthorizeAndCaptureTransaction objAuthorizeAndCaptureTransaction = getAuthorizeAndCaptureTransactionInstance(req);
				objAuthorizeAndCaptureTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setValue("");
				velocityResponse = velocityProcessor.invokeAuthorizeAndCaptureRequest(objAuthorizeAndCaptureTransaction);
				session.setAttribute("txnName", "AuthorizeAndCaptureWithoutToken");
				AppLogger.logDebug(this.getClass(), "AuthAndCaptureWoToken", "VelocityResponse >>>>>>>>>> "+ velocityResponse);
			}
			/* Invokes the Capture transaction from the VelocityTestApp */
			else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.CAPTURE))
			{	
				ChangeTransaction objChangeTransaction = getCaptureTransactionInstance(req);
				objChangeTransaction.getDifferenceData().setTransactionId((String)session.getAttribute("transactionId"));
				velocityResponse = velocityProcessor.invokeCaptureRequest(objChangeTransaction);
				if(velocityResponse.getBankcardCaptureResponse() != null)
				{
					session.setAttribute("transactionId", velocityResponse.getBankcardCaptureResponse().getTransactionId());
				}
				session.setAttribute("txnName", "Capture");
				AppLogger.logDebug(this.getClass(), "invokeCaptureRequest", "VelocityResponse >>>>>>>>>> "+ velocityResponse);
			}

			/* Invokes the Undo transaction from the VelocityTestApp */
			else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.UNDO))
			{	
				Undo objUndo  = getUndoTransactionInstance(req);
				objUndo.setTransactionId((String)session.getAttribute("transactionId"));
				velocityResponse = velocityProcessor.invokeUndoRequest(objUndo);
				AppLogger.logDebug(this.getClass(), "invokeUndoRequest", "VelocityResponse >>>>>>>>>> "+ velocityResponse);
				session.setAttribute("txnName", "Undo");
			}

			/* Invokes the Adjust transaction from the VelocityTestApp */
			else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.ADJUST))
			{	
				Adjust objAdjust  = getAdjustTransactionInstance(req);
				objAdjust.getDifferenceData().setTransactionId((String)session.getAttribute("transactionId"));
				velocityResponse = velocityProcessor.invokeAdjustRequest(objAdjust);
				AppLogger.logDebug(this.getClass(), "invokeAdjustRequest", "VelocityResponse >>>>>>>>>> "+ velocityResponse);
				session.setAttribute("txnName", "Adjust");
			}

			/* Invokes the ReturnById transaction from the VelocityTestApp */
			else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.RETURNBYID))
			{	
				ReturnById objReturnById  = getReturnByIdTransactionInstance(req);
				objReturnById.getDifferenceData().setTransactionId((String)session.getAttribute("transactionId"));
				velocityResponse = velocityProcessor.invokeReturnByIdRequest(objReturnById);
				AppLogger.logDebug(this.getClass(), "invokeReturnByIdRequest", "VelocityResponse >>>>>>>>>> "+ velocityResponse);
				session.setAttribute("txnName", "ReturnById");
			}

			/* Invokes the ReturnUnLinked transaction from the VelocityTestApp */
			else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.RETURNUNLINKED))
			{
				velocityResponse = velocityProcessor.invokeReturnUnlinkedRequest(getReturnTransactionInstance(req));
				if(velocityResponse.getBankcardTransactionResponse() != null)
				{
					session.setAttribute("transactionId", velocityResponse.getBankcardTransactionResponse().getTransactionId());
				}
				session.setAttribute("txnName", "ReturnUnlinked");
				AppLogger.logDebug(this.getClass(), "ReturnUnlinkedRequest", "VelocityResponse >>>>>>>>>> "+ velocityResponse);
			}

			/* Invokes the ReturnUnlinked transaction without token from the VelocityTestApp */
			else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.RETURNUNLINKEDWOTOKEN))
			{

				ReturnTransaction objReturnTransaction = getReturnTransactionInstance(req);
				objReturnTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setValue("");
				velocityResponse = velocityProcessor.invokeReturnUnlinkedRequest(objReturnTransaction);
				if(velocityResponse.getBankcardTransactionResponse() != null)
				{
					session.setAttribute("transactionId", velocityResponse.getBankcardTransactionResponse().getTransactionId());
				}
				session.setAttribute("txnName", "ReturnUnlinkedWithoutToken");
				AppLogger.logDebug(this.getClass(), "ReturnUnlinkedWoToken", "VelocityResponse >>>>>>>>>> "+ velocityResponse);
			}

			session.setAttribute("velocityProcessor", velocityProcessor);
			session.setAttribute("velocityResponse", velocityResponse);
			req.setAttribute("statusCode", velocityResponse.getStatusCode());
			req.setAttribute("message", velocityResponse.getMessage());

		}
		catch (Exception e) {

			AppLogger.logError(getClass(), "service", e);
		}

		RequestDispatcher view = req.getRequestDispatcher("result.jsp");
		view.forward(req, resp);

}

	/**
	 * Generating the Verify input AuthorizeTransaction instance from the User inputs. 
	 * @author vimalk2
	 * @param -req of the type HttpServlet
	 * @return- of the type authorizeTransaction.
	 */
	AuthorizeTransaction getVerifyRequestAuthorizeTransactionInstance(HttpServletRequest req)
	{
		/*Creating the object for AuthorizeTransaction  */
		AuthorizeTransaction authorizeTransaction = new AuthorizeTransaction();

		/*Setting the values for Authorize XML*/
		authorizeTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
		authorizeTransaction.getTransaction().getTenderData().getCardData().setCardType(req.getParameter("cardtype"));
		authorizeTransaction.getTransaction().getTenderData().getCardData().setCardholderName(req.getParameter("cardHolderName"));
		authorizeTransaction.getTransaction().getTenderData().getCardData().setPanNumber(req.getParameter("panNumber"));
		authorizeTransaction.getTransaction().getTenderData().getCardData().setExpiryDate(req.getParameter("month") + req.getParameter("year"));
		authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCardholderName().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setStreet(req.getParameter("street"));
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setCity(req.getParameter("city"));
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setStateProvince(req.getParameter("state"));
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setPostalCode(req.getParameter("zip"));
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setPhone(req.getParameter("phone"));
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getEmail().setValue(req.getParameter("email"));
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().setCvDataProvided("Provided");
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().setCvData(req.getParameter("cvc"));
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getKeySerialNumber().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getPin().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setAmount(req.getParameter("amount"));
		authorizeTransaction.getTransaction().getTransactionData().setCurrencyCode(req.getParameter("currencyCode"));
		authorizeTransaction.getTransaction().getTransactionData().setTransactiondateTime(req.getParameter("transactionDateTime"));
		authorizeTransaction.getTransaction().getTransactionData().setCustomerPresent(req.getParameter("industryType"));
		authorizeTransaction.getTransaction().getTransactionData().setEmployeeId(req.getParameter("empID"));
		authorizeTransaction.getTransaction().getTransactionData().setEntryMode("Keyed");
		authorizeTransaction.getTransaction().getTransactionData().setIndustryType(req.getParameter("industryType"));
		authorizeTransaction.getTransaction().getTransactionData().setInvoiceNumber("802");
		authorizeTransaction.getTransaction().getTransactionData().setOrderNumber("629203");
		authorizeTransaction.getTransaction().getTransactionData().setTipAmount(req.getParameter("tipAmount"));
		return authorizeTransaction;
	}

	/**
	 * Generating the Authorize input AuthorizeTransaction instance from the User inputs. 
	 * @author vimalk2
	 * @param req - of the type HttpServletRequest
	 * @return - of the type AuthorizeTransaction
	 */
	com.velocity.models.request.authorize.AuthorizeTransaction getAuthorizeRequestAuthorizeTransactionInstance(HttpServletRequest req)
	{
		
		HttpSession session = req.getSession(false);
		/*Creating the object for AuthorizeTransaction */
		com.velocity.models.request.authorize.AuthorizeTransaction authorizeTransaction = new com.velocity.models.request.authorize.AuthorizeTransaction();

		authorizeTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);

		/*Setting the values for Authorize XML*/
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getName().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStreet1(req.getParameter("street"));
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCity(req.getParameter("city"));
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStateProvince(req.getParameter("state"));
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setPostalCode(req.getParameter("zip"));
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCountryCode("USA");
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().setBusinessName("MomCorp");
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getPhone().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getFax().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getEmail().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().setCustomerId(req.getParameter("empID"));
		authorizeTransaction.getTransaction().getCustomerData().getCustomerTaxId().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getShippingData().setNillable(true);
		authorizeTransaction.getTransaction().getReportingData().setComment("a test comment");
		authorizeTransaction.getTransaction().getReportingData().setDescription("a test description");
		authorizeTransaction.getTransaction().getReportingData().setReference("001");
		if(session.getAttribute("paymentAccount" + "") != null)
		{
			AppLogger.logDebug(getClass(), "getAuthorizeRequestAuthorizeTransactionInstance", "paymentAccountDataToken == "+(String) session.getAttribute("paymentAccountDataToken"));
			authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setValue((String) session.getAttribute("paymentAccountDataToken"));
		}
		else
		{
			authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setValue("");
		}
		
		authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getEncryptionKeyId().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getCardData().setCardType(req.getParameter("cardtype"));
		authorizeTransaction.getTransaction().getTenderData().getCardData().setPan(req.getParameter("panNumber"));
		authorizeTransaction.getTransaction().getTenderData().getCardData().setExpiryDate(req.getParameter("month") + req.getParameter("year"));
		authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setAmount(req.getParameter("amount"));
		authorizeTransaction.getTransaction().getTransactionData().setCurrencyCode(req.getParameter("currencyCode"));
		authorizeTransaction.getTransaction().getTransactionData().setTransactionDateTime(req.getParameter("transactionDateTime"));
		authorizeTransaction.getTransaction().getTransactionData().getCampaignId().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setReference("xyt");
		authorizeTransaction.getTransaction().getTransactionData().setAccountType("NotSet");
		authorizeTransaction.getTransaction().getTransactionData().getApprovalCode().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setCashBackAmount("0.0");
		authorizeTransaction.getTransaction().getTransactionData().setCustomerPresent("Present");
		authorizeTransaction.getTransaction().getTransactionData().setEmployeeId(req.getParameter("empID"));
		authorizeTransaction.getTransaction().getTransactionData().setEntryMode("Keyed");
		authorizeTransaction.getTransaction().getTransactionData().setGoodsType("NotSet");
		authorizeTransaction.getTransaction().getTransactionData().setIndustryType(req.getParameter("industryType"));
		authorizeTransaction.getTransaction().getTransactionData().getInternetTransactionData().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setInvoiceNumber("");
		authorizeTransaction.getTransaction().getTransactionData().setOrderNumber("629203");
		authorizeTransaction.getTransaction().getTransactionData().setPartialShipment(false);
		authorizeTransaction.getTransaction().getTransactionData().setSignatureCaptured(false);
		authorizeTransaction.getTransaction().getTransactionData().setFeeAmount("1000.05");
		authorizeTransaction.getTransaction().getTransactionData().getTerminalId().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().getLaneId().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setTipAmount(req.getParameter("tipAmount"));
		authorizeTransaction.getTransaction().getTransactionData().getBatchAssignment().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setPartialApprovalCapable("NotSet");
		authorizeTransaction.getTransaction().getTransactionData().getScoreThreshold().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setQuasiCash(false);

		return authorizeTransaction ;
	}

	/**
	 * Generating the Authorize input AuthorizeTransaction instance from the User inputs.
	 * @author vimalk2
	 * @param req - of the type HttpServletRequest
	 * @return - of the type AuthorizeAndCaptureTransaction
	 */
	AuthorizeAndCaptureTransaction getAuthorizeAndCaptureTransactionInstance(HttpServletRequest req)
	{
		
		HttpSession session = req.getSession(false);
		
		/*Creating the object for AuthorizeAndCaptureTransaction */
		AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction = new AuthorizeAndCaptureTransaction();
		
		/*Setting the values for AuthorizeAndCapture  XML*/
		authorizeAndCaptureTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getName().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStreet1(req.getParameter("street"));
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCity(req.getParameter("city"));
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStateProvince(req.getParameter("state"));
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setPostalCode(req.getParameter("zip"));
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCountryCode("USA");
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().setBusinessName("MomCorp");
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getPhone().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getFax().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getEmail().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().setCustomerId(req.getParameter("empID"));
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getCustomerTaxId().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getShippingData().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getReportingData().setComment("a test comment");
		authorizeAndCaptureTransaction.getTransaction().getReportingData().setDescription("a test description");
		authorizeAndCaptureTransaction.getTransaction().getReportingData().setReference("001");
		if(session.getAttribute("paymentAccountDataToken") != null)
		{
			AppLogger.logDebug(getClass(), "getAuthorizeAndCaptureTransactionInstance", "paymentAccountDataToken == "+(String) session.getAttribute("paymentAccountDataToken"));
			authorizeAndCaptureTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setValue(((String) session.getAttribute("paymentAccountDataToken")));
		}
		authorizeAndCaptureTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTenderData().getEncryptionKeyId().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTenderData().getSwipeStatus().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().setCardType(req.getParameter("cardtype"));
		authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().setPan(req.getParameter("panNumber"));
		authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().setExpiryDate(req.getParameter("month") + req.getParameter("year"));
		authorizeAndCaptureTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setAmount(req.getParameter("amount"));
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setCurrencyCode(req.getParameter("currencyCode"));
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setTransactionDateTime(req.getParameter("transactionDateTime"));
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().getCampaignId().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setReference("xyt");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setAccountType("NotSet");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().getApprovalCode().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setCashBackAmount("0.0");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setCustomerPresent("Present");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setEmployeeId(req.getParameter("empID"));
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setEntryMode("Keyed");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setGoodsType("NotSet");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setIndustryType("Ecommerce");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().getInternetTransactionData().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setInvoiceNumber("");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setOrderNumber("629203");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setPartialShipment(false);
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setSignatureCaptured(false);
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setFeeAmount("0.0");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().getTerminalId().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().getLaneId().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setTipAmount(req.getParameter("tipAmount"));
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().getBatchAssignment().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setPartialApprovalCapable("NotSet");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().getScoreThreshold().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setQuasiCash(false);

		return authorizeAndCaptureTransaction;
	}
	
	/**
	 * Generating the Capture instance from the User inputs.
	 * @author vimalk2
	 * @param req - of the type HttpServletRequest
	 * @return - of the type ChangeTransaction
	 */
	ChangeTransaction getCaptureTransactionInstance(HttpServletRequest req)
	{
        /*Creating  object for Capture Transaction*/
		ChangeTransaction captureTransaction = new ChangeTransaction();

		/*Setting the values for Capture  XML*/
		captureTransaction.getDifferenceData().setType(VelocityEnums.BankcardCapture);
		captureTransaction.getDifferenceData().setAmount(req.getParameter("amount")); 
		captureTransaction.getDifferenceData().setTipAmount(req.getParameter("tipAmount"));

		return captureTransaction;
	}
	
	
	    /**
	     * Generating the Void(Undo) instance from the User inputs.
	     * @author vimalk2
	     * @param req - of the type HttpServletRequest
	     * @return - of the type Undo
	     */
	    Undo getUndoTransactionInstance(HttpServletRequest req){
	    	 
	    	/*Creating  object for UndoTransaction*/
	    	Undo undoTransaction = new Undo();
		   
	     /*Setting the values for Undo  XML*/
	       undoTransaction.setType(VelocityEnums.Undo);
	  	   undoTransaction.getBatchIds().setNillable(true);
	  	   undoTransaction.getDifferenceData().setNillable(true);
	  	   
		   
		   return undoTransaction;
	   }
	    
	    /**
	     * Generating the Adjust instance from the User inputs.
	     * @author vimalk2
	     * @param req - of the type HttpServletRequest
	     * @return - of the type Adjust
	     */
	    Adjust getAdjustTransactionInstance(HttpServletRequest req){
	 	   
	    	/*Creating  object for AdjustTransaction*/
	 	   Adjust adjustTransaction = new Adjust();
	 	   
	 	  /*Setting the values for Adjust  XML*/
	 	  adjustTransaction.setType(VelocityEnums.Adjust);
	 	  adjustTransaction.getBatchIds().setNillable(true);
	 	  adjustTransaction.getDifferenceData().setAmount(req.getParameter("amount"));
	 	
	 	   
	 	   return adjustTransaction;
	    }
	    
	    /**
	     * Generating the Adjust instance from the User inputs.
	     * @author vimalk2
	     * @param req - of the type HttpServletRequest
	     * @return - of the type ReturnById
	     */
	    ReturnById getReturnByIdTransactionInstance(HttpServletRequest req){

			ReturnById returnByIdTransaction = new ReturnById();
			
			/*Setting the values for ReturnById  XML*/
			returnByIdTransaction.setType(VelocityEnums.ReturnById);
			returnByIdTransaction.getBatchIds().setNillable(true);
			returnByIdTransaction.getDifferenceData().setAmount(req.getParameter("amount"));


			return returnByIdTransaction;
		}
	    
	    /**
	     * Generating the ReturnUnlinked instance from the User inputs.
	     * @author vimalk2
	     * @param req - of the type HttpServletRequest
	     * @return - of the type ReturnTransaction
	     */
	    ReturnTransaction getReturnTransactionInstance(HttpServletRequest req)
		{
	    	HttpSession session = req.getSession(false);
	    	
	    	/*Creating  object for ReturnUnlinkedTransaction*/
			ReturnTransaction returnUnlinkedTransaction = new ReturnTransaction();

			/*Setting the values for ReturnUnlinked XML*/
			returnUnlinkedTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
			returnUnlinkedTransaction.getBatchIds().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getName().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStreet1(req.getParameter("street"));
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCity(req.getParameter("city"));
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStateProvince(req.getParameter("state"));
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setPostalCode(req.getParameter("zip"));
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCountryCode("USA");
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().setBusinessName("MomCorp");
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getPhone().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getFax().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getEmail().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getCustomerData().setCustomerId(req.getParameter("empID"));
			returnUnlinkedTransaction.getTransaction().getCustomerData().getCustomerTaxId().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getCustomerData().getShippingData().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getReportingData().setComment("a test comment");
			returnUnlinkedTransaction.getTransaction().getReportingData().setDescription("a test description");
			returnUnlinkedTransaction.getTransaction().getReportingData().setReference("001");
			if(session.getAttribute("paymentAccountDataToken" + "") != null)
			{
				AppLogger.logDebug(getClass(), "getReturnTransactionInstance", "paymentAccountDataToken == "+(String) session.getAttribute("paymentAccountDataToken"));
				returnUnlinkedTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setValue(((String) session.getAttribute("paymentAccountDataToken")));
			}
			returnUnlinkedTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTenderData().getEncryptionKeyId().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTenderData().getSwipeStatus().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setCardType(req.getParameter("cardtype"));
			returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setpAN(req.getParameter("panNumber"));
			returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setExpire(req.getParameter("month") + req.getParameter("year"));
			returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setAmount(req.getParameter("amount"));
			returnUnlinkedTransaction.getTransaction().getTransactionData().setCurrencyCode(req.getParameter("currencyCode"));
			returnUnlinkedTransaction.getTransaction().getTransactionData().setTransactionDateTime(req.getParameter("transactionDateTime"));
			returnUnlinkedTransaction.getTransaction().getTransactionData().getCampaignId().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setReference("xyt");
			returnUnlinkedTransaction.getTransaction().getTransactionData().setAccountType("NotSet");
			returnUnlinkedTransaction.getTransaction().getTransactionData().getApprovalCode().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setCashBackAmount("0.0");
			returnUnlinkedTransaction.getTransaction().getTransactionData().setCustomerPresent("Present");
			returnUnlinkedTransaction.getTransaction().getTransactionData().setEmployeeId(req.getParameter("empID"));
			returnUnlinkedTransaction.getTransaction().getTransactionData().setEntryMode("Keyed");
			returnUnlinkedTransaction.getTransaction().getTransactionData().setGoodsType("NotSet");
			returnUnlinkedTransaction.getTransaction().getTransactionData().setIndustryType("NotSet");
			returnUnlinkedTransaction.getTransaction().getTransactionData().getInternetTransactionData().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setInvoiceNumber("802");
			returnUnlinkedTransaction.getTransaction().getTransactionData().setOrderNumber("629203");
			returnUnlinkedTransaction.getTransaction().getTransactionData().setPartialShipment(false);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setSignatureCaptured(false);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setFeeAmount("0.0");
			returnUnlinkedTransaction.getTransaction().getTransactionData().getTerminalId().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTransactionData().getLaneId().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setTipAmount(req.getParameter("tipAmount"));  
			returnUnlinkedTransaction.getTransaction().getTransactionData().getBatchAssignment().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setPartialApprovalCapable("NotSet");
			returnUnlinkedTransaction.getTransaction().getTransactionData().getScoreThreshold().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setIsQuasiCash("false");


			return returnUnlinkedTransaction;
		}

}
