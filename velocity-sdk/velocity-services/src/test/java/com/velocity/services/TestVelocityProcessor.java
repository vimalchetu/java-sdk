/**
 * 
 */
package com.velocity.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.velocity.enums.VelocityEnums;
import com.velocity.exceptions.VelocityGenericException;
import com.velocity.exceptions.VelocityIllegalArgument;
import com.velocity.exceptions.VelocityNotFound;
import com.velocity.exceptions.VelocityRestInvokeException;
import com.velocity.models.request.adjust.Adjust;
import com.velocity.models.request.authorizeAndCapture.AuthorizeAndCaptureTransaction;
import com.velocity.models.request.capture.ChangeTransaction;
import com.velocity.models.request.returnById.ReturnById;
import com.velocity.models.request.returnUnLinked.ReturnTransaction;
import com.velocity.models.request.undo.Undo;
import com.velocity.models.request.verify.AuthorizeTransaction;
import com.velocity.models.response.BankcardCaptureResponse;
import com.velocity.models.response.BankcardTransactionResponsePro;
import com.velocity.models.response.VelocityResponse;
import com.velocity.transaction.processor.BaseProcessor;
import com.velocity.transaction.processor.velocity.VelocityProcessor;
import com.velocity.utility.AppLogger;

/**
 * This class holds the test data for various for different transactions through JUNIT.
 * @author anitk
 * @date 12-January-2015
 */

public class TestVelocityProcessor {

	String identityToken = "PHNhbWw6QXNzZXJ0aW9uIE1ham9yVmVyc2lvbj0iMSIgTWlub3JWZXJzaW9uPSIxIiBBc3NlcnRpb25JRD0iXzdlMDhiNzdjLTUzZWEtNDEwZC1hNmJiLTAyYjJmMTAzMzEwYyIgSXNzdWVyPSJJcGNBdXRoZW50aWNhdGlvbiIgSXNzdWVJbnN0YW50PSIyMDE0LTEwLTEwVDIwOjM2OjE4LjM3OVoiIHhtbG5zOnNhbWw9InVybjpvYXNpczpuYW1lczp0YzpTQU1MOjEuMDphc3NlcnRpb24iPjxzYW1sOkNvbmRpdGlvbnMgTm90QmVmb3JlPSIyMDE0LTEwLTEwVDIwOjM2OjE4LjM3OVoiIE5vdE9uT3JBZnRlcj0iMjA0NC0xMC0xMFQyMDozNjoxOC4zNzlaIj48L3NhbWw6Q29uZGl0aW9ucz48c2FtbDpBZHZpY2U+PC9zYW1sOkFkdmljZT48c2FtbDpBdHRyaWJ1dGVTdGF0ZW1lbnQ+PHNhbWw6U3ViamVjdD48c2FtbDpOYW1lSWRlbnRpZmllcj5GRjNCQjZEQzU4MzAwMDAxPC9zYW1sOk5hbWVJZGVudGlmaWVyPjwvc2FtbDpTdWJqZWN0PjxzYW1sOkF0dHJpYnV0ZSBBdHRyaWJ1dGVOYW1lPSJTQUsiIEF0dHJpYnV0ZU5hbWVzcGFjZT0iaHR0cDovL3NjaGVtYXMuaXBjb21tZXJjZS5jb20vSWRlbnRpdHkiPjxzYW1sOkF0dHJpYnV0ZVZhbHVlPkZGM0JCNkRDNTgzMDAwMDE8L3NhbWw6QXR0cmlidXRlVmFsdWU+PC9zYW1sOkF0dHJpYnV0ZT48c2FtbDpBdHRyaWJ1dGUgQXR0cmlidXRlTmFtZT0iU2VyaWFsIiBBdHRyaWJ1dGVOYW1lc3BhY2U9Imh0dHA6Ly9zY2hlbWFzLmlwY29tbWVyY2UuY29tL0lkZW50aXR5Ij48c2FtbDpBdHRyaWJ1dGVWYWx1ZT5iMTVlMTA4MS00ZGY2LTQwMTYtODM3Mi02NzhkYzdmZDQzNTc8L3NhbWw6QXR0cmlidXRlVmFsdWU+PC9zYW1sOkF0dHJpYnV0ZT48c2FtbDpBdHRyaWJ1dGUgQXR0cmlidXRlTmFtZT0ibmFtZSIgQXR0cmlidXRlTmFtZXNwYWNlPSJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcyI+PHNhbWw6QXR0cmlidXRlVmFsdWU+RkYzQkI2REM1ODMwMDAwMTwvc2FtbDpBdHRyaWJ1dGVWYWx1ZT48L3NhbWw6QXR0cmlidXRlPjwvc2FtbDpBdHRyaWJ1dGVTdGF0ZW1lbnQ+PFNpZ25hdHVyZSB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnIyI+PFNpZ25lZEluZm8+PENhbm9uaWNhbGl6YXRpb25NZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzEwL3htbC1leGMtYzE0biMiPjwvQ2Fub25pY2FsaXphdGlvbk1ldGhvZD48U2lnbmF0dXJlTWV0aG9kIEFsZ29yaXRobT0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnI3JzYS1zaGExIj48L1NpZ25hdHVyZU1ldGhvZD48UmVmZXJlbmNlIFVSST0iI183ZTA4Yjc3Yy01M2VhLTQxMGQtYTZiYi0wMmIyZjEwMzMxMGMiPjxUcmFuc2Zvcm1zPjxUcmFuc2Zvcm0gQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjZW52ZWxvcGVkLXNpZ25hdHVyZSI+PC9UcmFuc2Zvcm0+PFRyYW5zZm9ybSBBbGdvcml0aG09Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvMTAveG1sLWV4Yy1jMTRuIyI+PC9UcmFuc2Zvcm0+PC9UcmFuc2Zvcm1zPjxEaWdlc3RNZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjc2hhMSI+PC9EaWdlc3RNZXRob2Q+PERpZ2VzdFZhbHVlPnl3NVZxWHlUTUh5NUNjdmRXN01TV2RhMDZMTT08L0RpZ2VzdFZhbHVlPjwvUmVmZXJlbmNlPjwvU2lnbmVkSW5mbz48U2lnbmF0dXJlVmFsdWU+WG9ZcURQaUorYy9IMlRFRjNQMWpQdVBUZ0VDVHp1cFVlRXpESERwMlE2ZW92T2lhN0pkVjI1bzZjTk1vczBTTzRISStSUGRUR3hJUW9xa0paeEtoTzZHcWZ2WHFDa2NNb2JCemxYbW83NUFSWU5jMHdlZ1hiQUVVQVFCcVNmeGwxc3huSlc1ZHZjclpuUytkSThoc2lZZW4vT0VTOUdtZUpsZVd1WUR4U0xmQjZJZnd6dk5LQ0xlS0FXenBkTk9NYmpQTjJyNUJWQUhQZEJ6WmtiSGZwdUlablp1Q2l5OENvaEo1bHU3WGZDbXpHdW96VDVqVE0wU3F6bHlzeUpWWVNSbVFUQW5WMVVGMGovbEx6SU14MVJmdWltWHNXaVk4c2RvQ2IrZXpBcVJnbk5EVSs3NlVYOEZFSEN3Q2c5a0tLSzQwMXdYNXpLd2FPRGJJUFpEYitBPT08L1NpZ25hdHVyZVZhbHVlPjxLZXlJbmZvPjxvOlNlY3VyaXR5VG9rZW5SZWZlcmVuY2UgeG1sbnM6bz0iaHR0cDovL2RvY3Mub2FzaXMtb3Blbi5vcmcvd3NzLzIwMDQvMDEvb2FzaXMtMjAwNDAxLXdzcy13c3NlY3VyaXR5LXNlY2V4dC0xLjAueHNkIj48bzpLZXlJZGVudGlmaWVyIFZhbHVlVHlwZT0iaHR0cDovL2RvY3Mub2FzaXMtb3Blbi5vcmcvd3NzL29hc2lzLXdzcy1zb2FwLW1lc3NhZ2Utc2VjdXJpdHktMS4xI1RodW1icHJpbnRTSEExIj5ZREJlRFNGM0Z4R2dmd3pSLzBwck11OTZoQ2M9PC9vOktleUlkZW50aWZpZXI+PC9vOlNlY3VyaXR5VG9rZW5SZWZlcmVuY2U+PC9LZXlJbmZvPjwvU2lnbmF0dXJlPjwvc2FtbDpBc3NlcnRpb24+";
	String appProfileId = "14644", merchantProfileId = "PrestaShop Global HC", workFlowId = "2317000001";

	boolean isTestAccount = true;

	private BaseProcessor velocityProcessor = null;

	@Before
	public void setUp() {

		try {
			try {
				velocityProcessor = new VelocityProcessor(identityToken, appProfileId, merchantProfileId, workFlowId, isTestAccount);
			} catch (VelocityRestInvokeException e) {
				
				AppLogger.logError(getClass(),"setUp",e);
			}
		} catch (VelocityIllegalArgument e) {
			
			AppLogger.logError(getClass(),"setUp",e);

		} catch (VelocityNotFound e) {
			
			AppLogger.logError(getClass(),"setUp",e);

		} catch (VelocityGenericException e) {

			AppLogger.logError(getClass(),"setUp",e);

		}
	}

	@After
	public void tearDown(){
		velocityProcessor = null;
	}

	@Test
	public void testSetVelocitySessionToken()
	{
		try {
			velocityProcessor.setVelocitySessionToken();
		} catch(Exception ex)
		{
			AppLogger.logError(getClass(),"testSetVelocitySessionToken",ex);
		}
	}

	/**
	 * This method test's the verify request through the REST API.
	 * @author anitk
	 * The AuthorizeTransaction instance is responsible for invoking the verifyRequest method and reads the the data for XML generation through VelocityProcessor class.
	 */

	@Test
	public void testInvokeVerifyRequest()
	{
		try {
			AuthorizeTransaction objAuthorizeTransaction = getVerifyRequestAuthorizeTransactionInstance();

			VelocityResponse objVelocityResponse = velocityProcessor.invokeVerifyRequest(objAuthorizeTransaction);

			if(objVelocityResponse.getBankcardTransactionResponse() != null)
			{
				AppLogger.logDebug(getClass(), "testInvokeVerifyRequest", "Status >>>>>>>>>> "+objVelocityResponse.getBankcardTransactionResponse().getStatus());
			}
			else if(objVelocityResponse.getErrorResponse() != null)
			{
				AppLogger.logDebug(getClass(), "testInvokeVerifyRequest", "Message >>>>>>>>>> "+objVelocityResponse.getErrorResponse().getReason() + " " + objVelocityResponse.getErrorResponse().getValidationErrors().getValidationErrorList().get(0).getRuleMessage());
			}
		} catch (Exception e) {
			
			AppLogger.logError(this.getClass()," testInvokeVerifyRequest", e);
		}
	}

	/** 
	 * This method sets the value for the VerifyRequest Instance 
	 * @author vimalk2
	 * @return- Returns the AuthoizeTransaction Object.
	 */
	AuthorizeTransaction getVerifyRequestAuthorizeTransactionInstance()
	{
		AuthorizeTransaction authorizeTransaction = new AuthorizeTransaction();

		authorizeTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
		authorizeTransaction.getTransaction().getTenderData().getCardData().setCardType("Visa");
		authorizeTransaction.getTransaction().getTenderData().getCardData().setCardholderName("ashish");
		authorizeTransaction.getTransaction().getTenderData().getCardData().setPanNumber("4012888812348882");
		authorizeTransaction.getTransaction().getTenderData().getCardData().setExpiryDate("0113");
		authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCardholderName().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setStreet("4 corporate sq");
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setCity("Denver");;
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setStateProvince("CO");
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setPostalCode("80202");
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setPhone("7849477899");
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getEmail().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().setCvDataProvided("Provided");
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().setCvData("123");
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getKeySerialNumber().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getPin().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setAmount("10000.00");
		authorizeTransaction.getTransaction().getTransactionData().setCurrencyCode("USD");
		authorizeTransaction.getTransaction().getTransactionData().setTransactiondateTime("");
		authorizeTransaction.getTransaction().getTransactionData().setCustomerPresent("Ecommerce");
		authorizeTransaction.getTransaction().getTransactionData().setEmployeeId("11");
		authorizeTransaction.getTransaction().getTransactionData().setEntryMode("Keyed");
		authorizeTransaction.getTransaction().getTransactionData().setIndustryType("Ecommerce");
		authorizeTransaction.getTransaction().getTransactionData().setInvoiceNumber("802");
		authorizeTransaction.getTransaction().getTransactionData().setOrderNumber("629203");
		authorizeTransaction.getTransaction().getTransactionData().setTipAmount("2.00");

		return authorizeTransaction;
	}



	/**
	 * This method test's the Authorize request through the REST API.
	 * @author vimalk2
	 * The VelocityResponse object reads the data for AuthorizeTransaction and prepares for the response.
	 */
	@Test
	public void testInvokeAuthorizeRequest()
	{
		try {
			com.velocity.models.request.authorize.AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance();
			VelocityResponse objVelocityResponse = velocityProcessor.invokeAuthorizeRequest(objAuthorizeTransaction);
			if(objVelocityResponse.getBankcardTransactionResponse() != null)
			{
				AppLogger.logDebug(getClass(), "testInvokeAuthorizeRequest", "Status >>>>>>>>>> "+objVelocityResponse.getBankcardTransactionResponse().getStatus());
			}
			else if(objVelocityResponse.getErrorResponse() != null)
			{
				AppLogger.logDebug(getClass(), "testInvokeAuthorizeRequest", "Error >>>>>>>>>> "+objVelocityResponse.getErrorResponse().getReason());
				if(objVelocityResponse.getErrorResponse().getValidationErrors() != null)
				{
					AppLogger.logDebug(getClass(), "testInvokeAuthorizeRequest", "Error >>>>>>>>>> "+objVelocityResponse.getErrorResponse().getValidationErrors().getValidationErrorList().get(0).getRuleKey());
				}
			}
			
		} catch(Exception ex)
		{
			AppLogger.logError(this.getClass(),"testInvokeAuthorizeRequest", ex);
		}
	}


	/**
	 * This method test's the AuthorizeAndCapture request through the REST API.
	 * @author vimalk2
	 * The VelocityResponse object reads the data for AuthorizeAndCaptureTransaction and prepares for the response.
	 */
	@Test
	public void testInvokeAuthorizeAndCaptureRequest()
	{
		try {
			AuthorizeAndCaptureTransaction objAuthorizeAndCaptureTransaction = getAuthorizeAndCaptureTransactionInstance();
			VelocityResponse objVelocityResponse = velocityProcessor.invokeAuthorizeAndCaptureRequest(objAuthorizeAndCaptureTransaction);
			AppLogger.logDebug(getClass(), "invokeAuthorizeAndCaptureRequest", "Status >>>>>>>>>> "+objVelocityResponse.getBankcardTransactionResponse().getStatus());
		} catch(Exception ex)
		{
			AppLogger.logError(this.getClass(),"testInvokeAuthorizeAndCaptureRequest", ex);
		}
	}

	AuthorizeAndCaptureTransaction getAuthorizeAndCaptureTransactionInstance()
	{
		AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction = new AuthorizeAndCaptureTransaction();

		//Setting the values for AuthorizeAndCapture XML
		authorizeAndCaptureTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getName().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStreet1("4 corporate sq");
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCity("Denver");
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStateProvince("CO");
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setPostalCode("80202");
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCountryCode("USA");
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().setBusinessName("MomCorp");
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getPhone().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getFax().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getEmail().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().setCustomerId("cust123x");
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getCustomerTaxId().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getCustomerData().getShippingData().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getReportingData().setComment("a test comment");
		authorizeAndCaptureTransaction.getTransaction().getReportingData().setDescription("a test description");
		authorizeAndCaptureTransaction.getTransaction().getReportingData().setReference("001");
		authorizeAndCaptureTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setValue("");
		authorizeAndCaptureTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTenderData().getEncryptionKeyId().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTenderData().getSwipeStatus().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().setCardType("MasterCard");
		authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().setPan("5428376000953619");
		authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().setExpiryDate("0320");
		authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().setNillable(true);;
		authorizeAndCaptureTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setAmount("2000.75");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setCurrencyCode("USD");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setTransactionDateTime("2013-04-03T13:50:16");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().getCampaignId().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setReference("xyt");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setAccountType("NotSet");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().getApprovalCode().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setCashBackAmount("0.0");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setCustomerPresent("Present");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setEmployeeId("11");
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
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setTipAmount("0.0");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().getBatchAssignment().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setPartialApprovalCapable("NotSet");
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().getScoreThreshold().setNillable(true);
		authorizeAndCaptureTransaction.getTransaction().getTransactionData().setQuasiCash(false);

		return authorizeAndCaptureTransaction;
	}

	/**
	 * This method prepares the AuthorizeTransaction instance for AuthorizeTransaction
	 * @author vimalk2
	 * The method sets the values for Authorize XML elements.
	 * @return of the type authorizeTransaction
	 */
	com.velocity.models.request.authorize.AuthorizeTransaction getAuthorizeRequestAuthorizeTransactionInstance()
	{
		com.velocity.models.request.authorize.AuthorizeTransaction authorizeTransaction = new com.velocity.models.request.authorize.AuthorizeTransaction();

		//Setting the values for Authorize XML
		authorizeTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getName().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStreet1("4 corporate sq");
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCity("Denver");
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStateProvince("CO");
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setPostalCode("80202");
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCountryCode("USA");
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().setBusinessName("MomCorp");
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getPhone().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getFax().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getEmail().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().setCustomerId("cust123x");
		authorizeTransaction.getTransaction().getCustomerData().getCustomerTaxId().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getShippingData().setNillable(true);
		authorizeTransaction.getTransaction().getReportingData().setComment("a test comment");
		authorizeTransaction.getTransaction().getReportingData().setDescription("a test description");
		authorizeTransaction.getTransaction().getReportingData().setReference("001");
		authorizeTransaction.getTransaction().getTenderData().setPaymentAccountDataToken(null);
		authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getEncryptionKeyId().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getCardData().setCardType("MasterCard");
		authorizeTransaction.getTransaction().getTenderData().getCardData().setPan("5428376000953619");
		authorizeTransaction.getTransaction().getTenderData().getCardData().setExpiryDate("0320");
		authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);;
		authorizeTransaction.getTransaction().getTransactionData().setAmount("2000.75");
		authorizeTransaction.getTransaction().getTransactionData().setCurrencyCode("USD");
		authorizeTransaction.getTransaction().getTransactionData().setTransactionDateTime("2013-04-03T13:50:16");
		authorizeTransaction.getTransaction().getTransactionData().getCampaignId().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setReference("xyt");
		authorizeTransaction.getTransaction().getTransactionData().setAccountType("NotSet");
		authorizeTransaction.getTransaction().getTransactionData().getApprovalCode().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setCashBackAmount("0.0");
		authorizeTransaction.getTransaction().getTransactionData().setCustomerPresent("Present");
		authorizeTransaction.getTransaction().getTransactionData().setEmployeeId("11");
		authorizeTransaction.getTransaction().getTransactionData().setEntryMode("Keyed");;
		authorizeTransaction.getTransaction().getTransactionData().setGoodsType("NotSet");
		authorizeTransaction.getTransaction().getTransactionData().setIndustryType("Ecommerce");
		authorizeTransaction.getTransaction().getTransactionData().getInternetTransactionData().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setInvoiceNumber("");
		authorizeTransaction.getTransaction().getTransactionData().setOrderNumber("629203");
		authorizeTransaction.getTransaction().getTransactionData().setPartialShipment(false);
		authorizeTransaction.getTransaction().getTransactionData().setSignatureCaptured(false);
		authorizeTransaction.getTransaction().getTransactionData().setFeeAmount("1000.05");
		authorizeTransaction.getTransaction().getTransactionData().getTerminalId().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().getLaneId().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setTipAmount("0.0");
		authorizeTransaction.getTransaction().getTransactionData().getBatchAssignment().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setPartialApprovalCapable("NotSet");
		authorizeTransaction.getTransaction().getTransactionData().getScoreThreshold().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setQuasiCash(false);

		return authorizeTransaction ;
	}


	/**
	 * 
	 * This method tests the Capture request through the REST API.
	 * @author vimalk2
	 * The method performs the Authorize operations before the invoking the capture transaction in order to obtain the Transaction Id.
	 */
	@Test
	public void testinvokeCaptureRequest()
	{
		try {

			com.velocity.models.request.authorize.AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance();
			VelocityResponse objVelocityResponse = velocityProcessor.invokeAuthorizeRequest(objAuthorizeTransaction);

			ChangeTransaction objChangeTransaction = getCaptureTransactionInstance();

		    objChangeTransaction.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardTransactionResponse().getTransactionId());

			VelocityResponse captureVelocityResponse = velocityProcessor.invokeCaptureRequest(objChangeTransaction);
			
			if(captureVelocityResponse.getBankcardCaptureResponse() != null)
			{
				AppLogger.logDebug(this.getClass(), "invokeCaptureRequest", "Status >>>>>>>>>> "+ captureVelocityResponse.getBankcardCaptureResponse().getCaptureState());
			}
			else if(captureVelocityResponse.getErrorResponse() != null)
			{
				AppLogger.logDebug(this.getClass(), "invokeCaptureRequest", "Status >>>>>>>>>> "+ captureVelocityResponse.getErrorResponse().getErrorId());
			}
		} catch(Exception ex)
		{

			AppLogger.logError(this.getClass(),"testinvokeCaptureRequest", ex);

		}
	}


	/**
	 * This method prepares the CaptureTransaction instance for CaptureTransaction
	 * @author vimalk2
	 * The method sets the values for Capture XML elements.
	 * @return of the type captureTransaction
	 */
	com.velocity.models.request.capture.ChangeTransaction getCaptureTransactionInstance(){

		com.velocity.models.request.capture.ChangeTransaction captureTransaction = new com.velocity.models.request.capture.ChangeTransaction();


		captureTransaction.getDifferenceData().setType(VelocityEnums.BankcardCapture);
		captureTransaction.getDifferenceData().setTransactionId("B877F283052443119721C5699CD5C408");
		captureTransaction.getDifferenceData().setAmount("100.00");
		captureTransaction.getDifferenceData().setTipAmount("3.00");

		return captureTransaction;
	}
	
	
	//==============================================================Undo Method=========================================================================================//  


	/**
	 * This method tests the Undo request through the REST API.
	 * @author vimalk2
	 * The method performs the Authorize operations before the invoking the Undo transaction and obtains the transactionId.
	 */
	@Test
	public void testinvokeUndoRequest()  
	{
		try {

			com.velocity.models.request.authorize.AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance();
			VelocityResponse objVelocityResponse = velocityProcessor.invokeAuthorizeRequest(objAuthorizeTransaction);

			com.velocity.models.request.undo.Undo objUndo  = getUndoTransactionInstance();
			Undo objUndo1 = getUndoTransactionInstance();

			objUndo1.setTransactionId(objVelocityResponse.getBankcardTransactionResponse().getTransactionId());

			VelocityResponse objVelocityResponse2 = velocityProcessor.invokeUndoRequest(objUndo1);
			AppLogger.logDebug(getClass(), "testinvokeUndoRequest", "Status >>>>>>>>>> "+ objVelocityResponse2.getBankcardTransactionResponse().getStatus());
		} catch(Exception ex)
		{

			AppLogger.logError(this.getClass(),"testinvokeUndoRequest", ex);

		}
	}


	/**This method sets value for the Undo XML.
	 * @author vimalk2
	 * @return - of the type undoTransaction
	 */
	Undo getUndoTransactionInstance(){

		Undo undoTransaction = new Undo();

		undoTransaction.setType(VelocityEnums.Undo);
		undoTransaction.getBatchIds().setNillable(true);
		undoTransaction.getDifferenceData().setNillable(true);
		undoTransaction.setTransactionId("8C17F72F1BF649AA88EE1366AF699D40");

		return undoTransaction;
	}




	//==================================================================Adjust Method===============================================================================// 

	/**
	 * This method tests the Adjust request through the REST API.
	 * @author vimalk2
	 * The method performs the Authorize operations before the invoking the Adjust transaction and obtains the transactionId.
	 */

	@Test
	public void testinvokeAdjustRequest()  
	{
		try {

			com.velocity.models.request.authorize.AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance();
			VelocityResponse objVelocityResponse = velocityProcessor.invokeAuthorizeRequest(objAuthorizeTransaction);

			if(objVelocityResponse.getBankcardTransactionResponse() != null)
			{
				AppLogger.logDebug(getClass(), "testinvokeAdjustRequest", "Authorize Status >>>>>>>>>> "+ objVelocityResponse.getBankcardTransactionResponse().getStatus());

				ChangeTransaction objChangeTransaction = getCaptureTransactionInstance();

				objChangeTransaction.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardTransactionResponse().getTransactionId());

				objVelocityResponse = velocityProcessor.invokeCaptureRequest(objChangeTransaction);

				if(objVelocityResponse.getBankcardCaptureResponse() != null)
				{
					AppLogger.logDebug(getClass(), "invokeAdjustRequest", "Capture Status >>>>>>>>>> "+ objVelocityResponse.getBankcardCaptureResponse().getStatus());

					AppLogger.logDebug(getClass(), "invokeAdjustRequest", "Capture Transaction Id >>>>>>>>>> "+ objVelocityResponse.getBankcardCaptureResponse().getTransactionId());

					com.velocity.models.request.adjust.Adjust objAdjust  = getAdjustTransactionInstance();

					objAdjust.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardCaptureResponse().getTransactionId());
					objVelocityResponse = velocityProcessor.invokeAdjustRequest(objAdjust);
					AppLogger.logDebug(getClass(), "invokeAdjustRequest", "Adjust Status >>>>>>>>>> "+ objVelocityResponse.getBankcardTransactionResponse().getStatus());

				}
			}
		} catch(Exception ex)
		{

			AppLogger.logError(this.getClass(),"testinvokeUndoRequest", ex);

		}
	}



	/**
	 * This method sets the values for the Adjust XML
	 * @author vimalk2
	 * @return - of the type Adjust
	 */
	Adjust getAdjustTransactionInstance(){

		Adjust adjustTransaction = new Adjust();

		adjustTransaction.setType(VelocityEnums.Adjust);
		adjustTransaction.getBatchIds().setNillable(true);
		adjustTransaction.getDifferenceData().setAmount("3.00");
		adjustTransaction.getDifferenceData().setTransactionId("8A416AE9BB9840199BD56BA011F80AC2");

		return adjustTransaction;
	}


	//==================================================================ReturnById Method===============================================================================//


	/**
	 * This method tests the ReturnById request through the REST API.
	 * @author vimalk2
	 */
	@Test
	public void testinvokeReturnByIdRequest()  
	{
		try {

			com.velocity.models.request.authorize.AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance();
			VelocityResponse objVelocityResponse = velocityProcessor.invokeAuthorizeRequest(objAuthorizeTransaction);

			if(objVelocityResponse.getBankcardTransactionResponse() != null)
			{
				AppLogger.logDebug(getClass(), "testinvokeReturnByIdRequest", "Authorize Status >>>>>>>>>> "+ objVelocityResponse.getBankcardTransactionResponse().getStatus());

				ChangeTransaction objChangeTransaction = getCaptureTransactionInstance();

				objChangeTransaction.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardTransactionResponse().getTransactionId());

				objVelocityResponse = velocityProcessor.invokeCaptureRequest(objChangeTransaction);

				if(objVelocityResponse.getBankcardCaptureResponse() != null)
				{
					AppLogger.logDebug(getClass(), "invokeAdjustRequest", "Capture Status >>>>>>>>>> "+ objVelocityResponse.getBankcardCaptureResponse().getStatus());
					com.velocity.models.request.returnById.ReturnById objReturnById  = getReturnByIdTransactionInstance();

					objReturnById.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardCaptureResponse().getTransactionId());
					objVelocityResponse = velocityProcessor.invokeReturnByIdRequest(objReturnById);
					AppLogger.logDebug(getClass(), "invokeReturnByIdRequest", "ReturnById Status >>>>>>>>>> "+ objVelocityResponse.getBankcardTransactionResponse().getStatus());

				}
			}
		} catch(Exception ex)
		{

			AppLogger.logError(this.getClass(),"testinvokeReturnByIdRequest", ex);

		}
	}


	/**
	 * This method sets the values for the ReturnById XML.
	 * @author vimalk2
	 * @return - of the type ReturnById
	 */
	ReturnById getReturnByIdTransactionInstance(){

		ReturnById returnByIdTransaction = new ReturnById();

		returnByIdTransaction.setType(VelocityEnums.ReturnById);
		returnByIdTransaction.getBatchIds().setNillable(true);
		returnByIdTransaction.getDifferenceData().setTransactionId("EACD2B6739724FD9A322B9BEE396CB14");
		returnByIdTransaction.getDifferenceData().setAmount("50.00");


		return returnByIdTransaction;
	}



	//==================================================================ReturnUnlinked Method===============================================================================//

	/**
	 *
	 * This method tests the ReturnUnlinked request through the REST API.
	 * @author vimalk2
	 */
	
	
	@Test
	public void testinvokeReturnUnlinkedRequest()
	{
		try {
			ReturnTransaction objReturnTransaction = getReturnTransactionInstance();
			VelocityResponse objVelocityResponse = velocityProcessor.invokeReturnUnlinkedRequest(objReturnTransaction );
			AppLogger.logDebug(getClass(), "invokeReturnUnlinkedRequest", "Status >>>>>>>>>> "+objVelocityResponse.getBankcardTransactionResponse().getStatus());
		} catch(Exception ex)
		{
			AppLogger.logError(this.getClass(),"testinvokeReturnUnlinkedRequest", ex);
		}
	}

	/**
	 * 
	 * This method sets the values for the ReturnUnlinked XML.
	 * @author vimalk2
	 * @return - of the type ReturnTransaction
	 */
	ReturnTransaction getReturnTransactionInstance()
	{
		ReturnTransaction returnUnlinkedTransaction = new ReturnTransaction();

		/*Setting the values for ReturnUnlinked XML*/

		returnUnlinkedTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
		returnUnlinkedTransaction.getBatchIds().setNillable(true);
		returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getName().setNillable(true);
		returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStreet1("1400 16th St");
		returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().setNillable(true);
		returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCity("Denver");
		returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStateProvince("CO");
		returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setPostalCode("80202");
		returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCountryCode("USA");
		returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().setBusinessName("MomCorp");
		returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getPhone().setNillable(true);
		returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getFax().setNillable(true);
		returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getEmail().setNillable(true);
		returnUnlinkedTransaction.getTransaction().getCustomerData().setCustomerId("cust123x");
		returnUnlinkedTransaction.getTransaction().getCustomerData().getCustomerTaxId().setNillable(true);
		returnUnlinkedTransaction.getTransaction().getCustomerData().getShippingData().setNillable(true);
		returnUnlinkedTransaction.getTransaction().getReportingData().setComment("a test comment");
		returnUnlinkedTransaction.getTransaction().getReportingData().setDescription("a test description");
		returnUnlinkedTransaction.getTransaction().getReportingData().setReference("001");
		returnUnlinkedTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setNillable(true);
		returnUnlinkedTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setNillable(true);
		returnUnlinkedTransaction.getTransaction().getTenderData().getEncryptionKeyId().setNillable(true);
		returnUnlinkedTransaction.getTransaction().getTenderData().getSwipeStatus().setNillable(true);
		returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setCardType("MasterCard");
		returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setpAN("5428376000953619");
		returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setExpire("0320");;
		returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().setNillable(true);;
		returnUnlinkedTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);
		returnUnlinkedTransaction.getTransaction().getTransactionData().setAmount("18.00");
		returnUnlinkedTransaction.getTransaction().getTransactionData().setCurrencyCode("USD");
		returnUnlinkedTransaction.getTransaction().getTransactionData().setTransactionDateTime("2013-04-03T13:50:16");
		returnUnlinkedTransaction.getTransaction().getTransactionData().getCampaignId().setNillable(true);
		returnUnlinkedTransaction.getTransaction().getTransactionData().setReference("xyt");
		returnUnlinkedTransaction.getTransaction().getTransactionData().setAccountType("NotSet");
		returnUnlinkedTransaction.getTransaction().getTransactionData().getApprovalCode().setNillable(true);
		returnUnlinkedTransaction.getTransaction().getTransactionData().setCashBackAmount("0.0");
		returnUnlinkedTransaction.getTransaction().getTransactionData().setCustomerPresent("Present");
		returnUnlinkedTransaction.getTransaction().getTransactionData().setEmployeeId("11");
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
		returnUnlinkedTransaction.getTransaction().getTransactionData().setTipAmount("0.0");
		returnUnlinkedTransaction.getTransaction().getTransactionData().getBatchAssignment().setNillable(true);
		returnUnlinkedTransaction.getTransaction().getTransactionData().setPartialApprovalCapable("NotSet");
		returnUnlinkedTransaction.getTransaction().getTransactionData().getScoreThreshold().setNillable(true);
		returnUnlinkedTransaction.getTransaction().getTransactionData().setIsQuasiCash("false");

		return returnUnlinkedTransaction;
	}


	private static String readFileAsString(String path) throws Exception
	{

		StringBuffer strBuff = new StringBuffer();

		try {
			FileInputStream fis = new FileInputStream(path);

			int content;
			while ((content = fis.read()) != -1) {
				// convert to char and display it
				strBuff.append((char) content);
			}
			fis.close();
		}
		catch(Exception ex)
		{
			return null;
		}
		return strBuff.toString();
	}

}
