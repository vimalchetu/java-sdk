/**
 * 
 */
package com.velocity.transaction.processor.velocity;

import java.io.IOException; 

import org.apache.axis.encoding.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.velocity.exceptions.VelocityGenericException;
import com.velocity.exceptions.VelocityIllegalArgument;
import com.velocity.exceptions.VelocityNotFound;
import com.velocity.exceptions.VelocityRestInvokeException;
import com.velocity.models.request.verify.AuthorizeTransaction;
import com.velocity.models.response.BankcardCaptureResponse;
import com.velocity.models.response.BankcardTransactionResponsePro;
import com.velocity.models.response.ErrorResponse;
import com.velocity.models.response.VelocityResponse;
import com.velocity.transaction.processor.BaseProcessor;
import com.velocity.utility.AppLogger;
import com.velocity.utility.CommonUtils;
import com.velocity.utility.VelocityConfigReader;
import com.velocity.utility.VelocityConstants;

/**
 * This class implements the methods required for the Velocity transaction.
 * @author vimalk2
 * @date 15-January-2015
 */
public class VelocityProcessor implements BaseProcessor {
	
	/* Variable for Velocity identity token. */
	private String identityToken;
	
	/* Variable for Application profile Id. */
	private String appProfileId;
	
	/* Variable for Merchant profile Id*/
	private String merchantProfileId;
	
	/* Variable for Work flow Id */
	private String workFlowId;
	
	/* Flag for Test account. */
	private boolean isTestAccount;
	
	/* Variable for Session token */
	private String sessionToken;
	
	/* Variable for Encrypted Session token */
	private String encSessionToken;
	
	/* Reference for Rest Server URL. */
	private String serverURL;
	
	/*Reference for transaction request XML*/
	private String txnRequestXML;
	
	/*Reference for transaction response XML*/
	private String txnResponseXML;
	
	/**
	 * Constructor for VelocityProcessor class.
	 * @author vimalk2
	 * @param sessionToken - Session token of the Velocity server.
	 * @param identityToken - Encrypted data to initiate transaction.
	 * @param appProfileId - Application profile Id for transaction initiation.
	 * @param merchantProfileId- Merchant profile Id for transaction initiation.
	 * @param workFlowId - Attached with the REST URL for various transaction.
	 * @param isTestAccount -Works as flag for the Test Account.
	 * @throws VelocityIllegalArgument - Thrown when illegal argument supplied.
	 * @throws VelocityNotFound - Thrown when some resource not found. 
	 * @throws VelocityGenericException - Generic Exception for Velocity transaction.
	 * @throws VelocityRestInvokeException - Thrown when exception occurs at invoking REST API
	 */
	public VelocityProcessor(String sessionToken, String identityToken, String appProfileId, String merchantProfileId, String workFlowId, boolean isTestAccount) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException 
	{
		AppLogger.logDebug(this.getClass(), "VelocityProcessor constructor", "Entering VelocityProcessor constructor");
		this.sessionToken = sessionToken;
		this.identityToken = identityToken;
		this.appProfileId = appProfileId;
		this.merchantProfileId = merchantProfileId;
		this.workFlowId = workFlowId;
		this.isTestAccount = isTestAccount;
		/* Setting Velocity REST server URL. */
		setVelocityRestServerURL();
		
		if((sessionToken == null || sessionToken.isEmpty()) && (identityToken != null && !identityToken.isEmpty()))
		{
			/* Setting Velocity session token. */
			this.sessionToken = invokeSignOn(identityToken);
		}
		AppLogger.logDebug(this.getClass(), "VelocityProcessor constructor", "sessionToken >>>> "+this.sessionToken);
		/* Encrypting the Velocity server session token. */
		if(this.sessionToken != null && !this.sessionToken.isEmpty())
		{
			encSessionToken = new String(Base64.encode((this.sessionToken + ":").getBytes()));
		}
		AppLogger.logDebug(this.getClass(), "VelocityProcessor constructor", "Encrypted session token >>>> "+encSessionToken);
		AppLogger.logDebug(this.getClass(), "VelocityProcessor constructor", "Exiting VelocityProcessor constructor");
		if(encSessionToken == null || encSessionToken.isEmpty())
		{
			throw new VelocityGenericException("Unable to create VelocityProcessor instance with a valid session.");
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.velocity.transaction.processor.BaseProcessor#setVelocityRestServerURL()
	 */
	public void setVelocityRestServerURL() throws VelocityGenericException, VelocityIllegalArgument, VelocityNotFound
	{
		AppLogger.logDebug(this.getClass(), "getVelocityRestServerURL", "Entering...");
		
		if(isTestAccount) 
		{
			serverURL = VelocityConfigReader.getProperty("velocity.rest.test.server");
		}
		else
		{
			serverURL = VelocityConfigReader.getProperty("velocity.rest.server");
		}
		AppLogger.logDebug(this.getClass(), "getVelocityRestServerURL", "Exiting...");
		
	}

	
	
	/* (non-Javadoc)
	 * @see com.velocity.transaction.processor.BaseProcessor#invokeSignOn(java.lang.String)
	 */
	public String invokeSignOn(String identityToken) throws VelocityIllegalArgument, VelocityRestInvokeException
	{
		AppLogger.logDebug(this.getClass(), "invokeSignOn", "Entering...");
		
		if(identityToken == null || identityToken.isEmpty())
		{
			throw new VelocityIllegalArgument("identityToken param cannot be null or empty.");
		}
		
		/* Encrypting the Identity Token */
		String encIdentitytoken = new String(Base64.encode((identityToken + ":").getBytes()));
		AppLogger.logDebug(this.getClass(), "invokeSignOn", "Encrypted Identity token>>>>>>>>>"+encIdentitytoken);
		String signOnURL = serverURL + "/SvcInfo/token";
		AppLogger.logDebug(this.getClass(), "invokeSignOn", "SignOnURL>>>>>>>>>"+signOnURL);
		HttpGet signOnRequest = new HttpGet(signOnURL);

		/* Generating request headers for signOn request. */
		signOnRequest.addHeader("Authorization", "Basic "+encIdentitytoken);
		signOnRequest.addHeader("Content-type", "application/json");
		signOnRequest.addHeader("Accept", "");

		String sessionToken = null;

		try {
			/* Creating the HttpClient instance. */
			CloseableHttpClient httpClient = HttpClients.createDefault();
			/* Getting the HttpResponse for signOnRequest. */
			HttpResponse response = httpClient.execute(signOnRequest);
			/* Getting the sessionToken for transactions. */
			sessionToken = EntityUtils.toString(response.getEntity());

			if(sessionToken != null && sessionToken.startsWith("\"") && sessionToken.endsWith("\""))
			{
				sessionToken = sessionToken.substring(1, sessionToken.length() - 1);
			}
			AppLogger.logDebug(this.getClass(), "invokeSignOn", "sessionToken >>>> "+sessionToken);
			/* Closing the HTTP connection. */
			httpClient.close();

		} catch(IOException ex)
		{
			AppLogger.logError(this.getClass(), "invokeSignOn", ex);
			throw new VelocityRestInvokeException("Velocity server IO Exception during signOn occured::", ex);
		}
		AppLogger.logDebug(this.getClass(), "invokeSignOn", "Exiting...");
		return sessionToken;
	}
	
	
	/**
	 * This method generates the XML for verify request.
	 * @author vimalk2
	 * @param  authorizeTransaction - stores the value for the type AuthorizeTransaction..
	 * @return String - Instance of the type String.
	 * @throws VelocityGenericException - thrown when an Exception is thrown at the verifying the request XML.
	 * @throws VelocityIllegalArgument - thrown when null or bad data is passed.
	 */
	private String generateVerifyRequestXMLInput(AuthorizeTransaction authorizeTransaction) throws VelocityGenericException,VelocityIllegalArgument
	{
		AppLogger.logDebug(this.getClass(), "generateVerifyRequestXMLInput", "Entering...");
		/* StringBuilder instance to generate verifyRequestXML string. */
		StringBuilder verifyRequestXML = null;
		try {
			/* Providing the Verify request XML data for generating its request XML. */
			verifyRequestXML = new StringBuilder("<AuthorizeTransaction xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"AuthorizeTransaction\">");
			verifyRequestXML.append("<ApplicationProfileId>"+  appProfileId + "</ApplicationProfileId>");
			verifyRequestXML.append("<MerchantProfileId>"+  merchantProfileId + "</MerchantProfileId>");
			verifyRequestXML.append("<Transaction xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" i:type=\"ns1:"+authorizeTransaction.getTransaction().getType()+"\">");
			verifyRequestXML.append("<ns1:TenderData>");
			verifyRequestXML.append("<ns1:CardData>"); 
			verifyRequestXML.append("<ns1:CardType>"+authorizeTransaction.getTransaction().getTenderData().getCardData().getCardType()+"</ns1:CardType>");
			verifyRequestXML.append("<ns1:CardholderName>"+authorizeTransaction.getTransaction().getTenderData().getCardData().getCardholderName()+"</ns1:CardholderName>");
			verifyRequestXML.append("<ns1:PAN>"+authorizeTransaction.getTransaction().getTenderData().getCardData().getPanNumber()+"</ns1:PAN>");
			verifyRequestXML.append("<ns1:Expire>"+authorizeTransaction.getTransaction().getTenderData().getCardData().getExpiryDate()+"</ns1:Expire>");
			verifyRequestXML.append("<ns1:Track1Data i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().isNillable()+"\">"+ authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().getValue() +"</ns1:Track1Data>");
			verifyRequestXML.append("</ns1:CardData>");
			verifyRequestXML.append("<ns1:CardSecurityData>");
			verifyRequestXML.append("<ns1:AVSData>");
			verifyRequestXML.append("<ns1:CardholderName i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCardholderName().isNillable()+"\">"+ authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCardholderName().getValue() +"</ns1:CardholderName>");
			verifyRequestXML.append("<ns1:Street>"+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getStreet()+"</ns1:Street>");
			verifyRequestXML.append("<ns1:City>"+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCity()+"</ns1:City>");
			verifyRequestXML.append("<ns1:StateProvince>"+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getStateProvince()+"</ns1:StateProvince>");
			verifyRequestXML.append("<ns1:PostalCode>"+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getPostalCode()+"</ns1:PostalCode>");
			verifyRequestXML.append("<ns1:Phone>"+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getPostalCode()+"</ns1:Phone>");
			verifyRequestXML.append("<ns1:Email i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getEmail().isNillable()+"\">" + authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getEmail().getValue() + "</ns1:Email>");
			verifyRequestXML.append("</ns1:AVSData>");
			verifyRequestXML.append("<ns1:CVDataProvided>"+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getCvDataProvided()+"</ns1:CVDataProvided>");
			verifyRequestXML.append("<ns1:CVData>"+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getCvData()+"</ns1:CVData>");
			verifyRequestXML.append("<ns1:KeySerialNumber i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getKeySerialNumber().isNillable()+"\">"+ authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getKeySerialNumber().getValue() +"</ns1:KeySerialNumber>");
			verifyRequestXML.append("<ns1:PIN i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getPin().isNillable()+"\">"+ authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getPin().getValue() + "</ns1:PIN>");
			verifyRequestXML.append("<ns1:IdentificationInformation i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().isNillable()+"\">"+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().getValue() +"</ns1:IdentificationInformation>");
			verifyRequestXML.append("</ns1:CardSecurityData>");
			verifyRequestXML.append("<ns1:EcommerceSecurityData i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().isNillable()+"\">"+ authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().getValue() +"</ns1:EcommerceSecurityData>");
			verifyRequestXML.append("</ns1:TenderData>");
			verifyRequestXML.append("<ns1:TransactionData>");
			verifyRequestXML.append("<ns8:Amount xmlns:ns8=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeTransaction.getTransaction().getTransactionData().getAmount()+"</ns8:Amount>");
			verifyRequestXML.append("<ns9:CurrencyCode xmlns:ns9=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeTransaction.getTransaction().getTransactionData().getCurrencyCode()+"</ns9:CurrencyCode>");
			verifyRequestXML.append("<ns10:TransactionDateTime xmlns:ns10=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" >"+authorizeTransaction.getTransaction().getTransactionData().getTransactiondateTime()+"</ns10:TransactionDateTime>");
			verifyRequestXML.append("<ns1:CustomerPresent>"+authorizeTransaction.getTransaction().getTransactionData().getCustomerPresent()+"</ns1:CustomerPresent>");
			verifyRequestXML.append("<ns1:EmployeeId>"+authorizeTransaction.getTransaction().getTransactionData().getEmployeeId()+"</ns1:EmployeeId>");
			verifyRequestXML.append("<ns1:EntryMode>"+authorizeTransaction.getTransaction().getTransactionData().getEntryMode()+"</ns1:EntryMode>");
			verifyRequestXML.append("<ns1:IndustryType>"+authorizeTransaction.getTransaction().getTransactionData().getIndustryType()+"</ns1:IndustryType>");
			verifyRequestXML.append("<ns1:InvoiceNumber>"+authorizeTransaction.getTransaction().getTransactionData().getInvoiceNumber()+"</ns1:InvoiceNumber>");
			verifyRequestXML.append("<ns1:OrderNumber>"+authorizeTransaction.getTransaction().getTransactionData().getOrderNumber()+"</ns1:OrderNumber>");
			verifyRequestXML.append("<ns1:TipAmount>"+authorizeTransaction.getTransaction().getTransactionData().getTipAmount()+"</ns1:TipAmount>");
			verifyRequestXML.append("</ns1:TransactionData></Transaction></AuthorizeTransaction>");
			AppLogger.logDebug(this.getClass(), "generateVerifyRequestXMLInput", "XML input :: "+verifyRequestXML.toString());
			AppLogger.logDebug(this.getClass(), "generateVerifyRequestXMLInput", "Exiting...");
			return verifyRequestXML.toString();
		} catch(Exception ex)
		{
			 AppLogger.logError(this.getClass(), "generateVerifyRequestXMLInput", ex);
			 throw new VelocityGenericException("Exception occured into generating verify request XML:: "+ex.getMessage(), ex);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.velocity.transaction.processor.BaseProcessor#invokeVerifyRequest(com.velocity.models.request.verify.AuthorizeTransaction)
	 */
	public VelocityResponse invokeVerifyRequest(AuthorizeTransaction authorizeTransaction) throws VelocityGenericException, VelocityIllegalArgument, VelocityNotFound, VelocityRestInvokeException
	{
		AppLogger.logDebug(getClass(), "invokeVerifyRequest", "Entering...");
		VelocityResponse velocityResponse = null;

		if(authorizeTransaction == null)
		{
			throw new VelocityIllegalArgument("AuthorizeTransaction can not be null or empty.");
		}
		
		/* Validating the session token. */
		if(encSessionToken == null || encSessionToken.isEmpty())
		{
			/* Setting Velocity session token. */
			sessionToken = invokeSignOn(identityToken);
			/* Encrypting the Velocity server session token. */
			encSessionToken = new String(Base64.encode((sessionToken + ":").getBytes()));
		}

		/* Generating verify XML input request. */
		String verifyTxnRequestXML =  generateVerifyRequestXMLInput(authorizeTransaction);
		AppLogger.logDebug(getClass(), "invokeVerifyRequest", "VerifyXMLInput >>>>>>>>>>>> "+verifyTxnRequestXML);
		/*Invoking URL for the XML input request*/
		String invokeURL = serverURL + "/Txn/" + workFlowId + "/verify";
		AppLogger.logDebug(getClass(), "invokeVerifyRequest", "InvokeURL == "+invokeURL);
		velocityResponse = generateVelocityResponse(VelocityConstants.POST_METHOD, invokeURL, "Basic "+encSessionToken, "application/xml", verifyTxnRequestXML);
		txnRequestXML = verifyTxnRequestXML ;
		AppLogger.logDebug(getClass(), "invokeVerifyRequest", "AuthorizeTransaction response message == "+ velocityResponse.getMessage());
		AppLogger.logDebug(getClass(), "invokeVerifyRequest", "Exiting...");
		return velocityResponse;
	}
	
	
	/**
	 * This method generates the Velocity Response for the request XMLs
	 * @author vimalk2
	 * @param requestType - defines the the type of request with which transaction proceeds
	 * @param invokeURL -invokes the URL for the different transactions
	 * @param authorizationHeader - provides access authentication
	 * @param contentType - is the type of content for the response.
	 * @param xmlPayload - contains the HttpRequest body
	 * @return VelocityResponse - Instance of the type VelocityResponse
	 * @throws VelocityGenericException - thrown when any exception occurs at generating the VelocityResponse
	 * @throws VelocityIllegalArgument - thrown when null or no data is provided.
	 * @throws VelocityNotFound - thrown when VelocityResponse is not found.
	 * @throws VelocityRestInvokeException - thrown when Velocity Rest calls exception occured.
	 */
	private VelocityResponse generateVelocityResponse(String requestType, String invokeURL, String authorizationHeader, String contentType, String xmlPayload) throws VelocityGenericException, VelocityIllegalArgument, VelocityNotFound, VelocityRestInvokeException
	{
		AppLogger.logDebug(getClass(), "generateVelocityResponse", "Entering...");
		
		if(requestType == null || requestType.isEmpty())
		{
			throw new VelocityIllegalArgument("requestType parameter can not be null or empty.");
		}
		
		if(authorizationHeader == null || authorizationHeader.isEmpty())
		{
			throw new VelocityIllegalArgument("Authorization request header can not be null or empty.");
		}
		
		if(contentType == null || contentType.isEmpty())
		{
			throw new VelocityIllegalArgument("Request content type can not be null or empty.");
		}
		
		VelocityResponse velocityResponse = new VelocityResponse();
		
		/* Creating Http client for a transaction request. */
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		/* Generating Http response for a transaction request.  */
		HttpEntity entity = new ByteArrayEntity(xmlPayload.getBytes());
		
		/* Generating the HttpRequest for a transaction. */
		HttpRequestBase httpRequest = null;
		
		if(requestType.equals(VelocityConstants.POST_METHOD))
		{
			httpRequest = new HttpPost(invokeURL);
			((HttpPost)httpRequest).setEntity(entity);
		}
		else if(requestType.equals(VelocityConstants.PUT_METHOD))
		{
			httpRequest = new HttpPut(invokeURL);
			((HttpPut)httpRequest).setEntity(entity);
		}
		
		/* Setting request headers for a transaction request. */
		httpRequest.addHeader("Authorization", authorizationHeader);
		httpRequest.addHeader("Content-type", contentType);
		httpRequest.addHeader("Accept", "");
		
		/* Getting the HttpResponse for a transaction request. */
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpRequest);
			
			AppLogger.logDebug(getClass(), "generateVelocityResponse", "Response status code >>>>>>>>>>>>>>>>>> "+response.getStatusLine().getStatusCode());
			AppLogger.logDebug(getClass(), "generateVelocityResponse", "Response status message >>>>>>>>>>>>>>>>>> "+response.getStatusLine().getReasonPhrase());
			
			velocityResponse.setStatusCode(response.getStatusLine().getStatusCode());
			velocityResponse.setMessage(response.getStatusLine().getReasonPhrase());
			
			String txnResponseXML = EntityUtils.toString(response.getEntity());
			/* Setting the transaction response XML. */
			this.txnResponseXML = txnResponseXML;
			if(txnResponseXML != null && txnResponseXML.contains(VelocityConstants.ERROR_RESPONSE))
			{
				/* Generating ErrorResponse instance from verify request response string. */
				ErrorResponse errorResponse =  CommonUtils.generateErrorResponse(txnResponseXML);
				AppLogger.logDebug(getClass(), "generateVelocityResponse", "ErrorResponse error Id >>>>>>>>>>>>>>>>>> "+errorResponse.getErrorId());
				/* Checking the error code for session expired. */
				if(errorResponse != null && errorResponse.getErrorId().equals(VelocityConstants.SESSION_EXPIRED_ERROR_CODE))
				{
					AppLogger.logDebug(getClass(), "generateVelocityResponse", "Session Token has been Expired.....");
					AppLogger.logDebug(getClass(), "generateVelocityResponse", "Getting a new Session token.....");
					
					/* Setting Velocity session token. */
					sessionToken = invokeSignOn(identityToken);
					/* Encrypting the Velocity server session token. */
					encSessionToken = new String(Base64.encode((sessionToken + ":").getBytes()));
					
					authorizationHeader = "Basic "+encSessionToken;
					/* Generating request headers for verify request. */
					httpRequest = new HttpPost(invokeURL);
					httpRequest.addHeader("Authorization", authorizationHeader);
					httpRequest.addHeader("Content-type", contentType);
					httpRequest.addHeader("Accept", "");
					((HttpPost)httpRequest).setEntity(entity);
					
					response = httpClient.execute(httpRequest);
					
					AppLogger.logDebug(getClass(), "generateVelocityResponse", "Response status code >>>>>>>>>>>>>>>>>> "+response.getStatusLine().getStatusCode());
					AppLogger.logDebug(getClass(), "generateVelocityResponse", "Response status message >>>>>>>>>>>>>>>>>> "+response.getStatusLine().getReasonPhrase());
					
					velocityResponse.setStatusCode(response.getStatusLine().getStatusCode());
					velocityResponse.setMessage(response.getStatusLine().getReasonPhrase());
					
					/* Getting response body string.  */
					txnResponseXML = EntityUtils.toString(response.getEntity());
				}
				else
				{
					velocityResponse.setErrorResponse(errorResponse);
					
				}
			}
			
			AppLogger.logDebug(getClass(), "generateVelocityResponse", "Velocity Response >>>>>>>>>>>>> "+txnResponseXML);
			AppLogger.logDebug(getClass(), "generateVelocityResponse", " Status Code :: " + velocityResponse.getStatusCode() + " Velocity Response message >>>>>>>>>>>>> "+velocityResponse.getMessage());
			
			/* Getting the Success response from the Velocity server. */
			if(txnResponseXML.contains(VelocityConstants.BANCARD_TRANSACTION_RESPONSE))
			{
				AppLogger.logDebug(getClass(), "generateVelocityResponse", "Velocity Response for BankcardTransactionResponsePro >>>>>>>>>>>>> ");
				/* Generating BankcardTransactionResponsePro instance from verify request response string. */
				BankcardTransactionResponsePro bankcardTransactionResp =  CommonUtils.generateBankcardTransactionResponsePro(txnResponseXML);
				AppLogger.logDebug(getClass(), "generateVelocityResponse", "BankcardTransactionResponsePro Status >>>>>>>>>>>>> "+ bankcardTransactionResp.getStatus());
				velocityResponse.setBankcardTransactionResponse(bankcardTransactionResp);
				return velocityResponse;
			}
			else if(txnResponseXML.contains(VelocityConstants.BANCARD_CAPTURE_RESPONSE))
			{
				AppLogger.logDebug(getClass(), "generateVelocityResponse", "Velocity Response for BankcardTransactionResponsePro >>>>>>>>>>>>> ");
				/* Generating BankcardTransactionResponsePro instance from verify request response string. */
				BankcardCaptureResponse bankcardCaptureResponse =  CommonUtils.generateBankcardCaptureResponse(txnResponseXML);
				AppLogger.logDebug(getClass(), "generateVelocityResponse", "BankcardCaptureResponse Status >>>>>>>>>>>>> "+ bankcardCaptureResponse.getStatus());
				velocityResponse.setBankcardCaptureResponse(bankcardCaptureResponse);
				return velocityResponse;
			}
			/* Getting the Error response from the Velocity server. */
			else if(txnResponseXML.contains(VelocityConstants.ERROR_RESPONSE))
			{
				AppLogger.logDebug(getClass(), "generateVelocityResponse", "Velocity Response for ErrorResponse >>>>>>>>>>>>> ");
				/* Generating ErrorResponse instance from verify request response string. */
				ErrorResponse errorResponse =  CommonUtils.generateErrorResponse(txnResponseXML);
				velocityResponse.setErrorResponse(errorResponse);
				return velocityResponse;
			}
			
			/* Closing the HTTP connection. */
			httpClient.close();
			
		} catch (ClientProtocolException ex) {
			AppLogger.logError(this.getClass(), "generateVelocityResponse", ex);
			throw new VelocityRestInvokeException("Velocity Rest Invoke exception occured::"+ex.getMessage(), ex);
			
		} catch (IOException ex) {
			AppLogger.logError(this.getClass(), "generateVelocityResponse", ex);
			throw new VelocityNotFound("Velocity IO exception occured::"+ex.getMessage(), ex);
		}
		catch (Exception ex) {
			AppLogger.logError(this.getClass(), "generateVelocityResponse", ex);
			throw new VelocityGenericException("Velocity Generic exception occured::"+ex.getMessage(), ex);
		}
		return null;
	}
	
	
	 
	
	//=======================================================================Authorize=============================================================================================//

	/**
	 * This method generates the XML for Authorize request.
	 * @author vimalk2
	 * @param  authorizeTransaction - stores the value for the type AuthorizeTransaction.
	 * @return String - Instance of the type String.
	 * @throws VelocityGenericException - thrown when the exception occurs during the transaction.
	 * @throws VelocityIllegalArgument - thrown when null or bad data is passed.
	 */
	private String generateAuthorizeRequestXMLInput(com.velocity.models.request.authorize.AuthorizeTransaction authorizeTransaction) throws VelocityIllegalArgument , VelocityGenericException
	{
		AppLogger.logDebug(this.getClass(), "generateAuthorizeRequestXMLInput", "Entering...");
		
		/* StringBuilder instance to generate authorizeRequestXML string. */
		StringBuilder authorizeRequestXML = null;
		
		try {
			
		if(authorizeTransaction == null)
		{
			throw new VelocityIllegalArgument("AuthorizeTransaction param can not be null.");
		}
		/* Providing the Authorize request data for generating its request XML. */
		authorizeRequestXML = new StringBuilder("<AuthorizeTransaction xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"AuthorizeTransaction\">");
		authorizeRequestXML.append("<ApplicationProfileId>"+ appProfileId +"</ApplicationProfileId>");
		authorizeRequestXML.append("<MerchantProfileId>"+ merchantProfileId + "</MerchantProfileId>");
		authorizeRequestXML.append("<Transaction xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" i:type=\"ns1:"+authorizeTransaction.getTransaction().getType()+"\">");
		authorizeRequestXML.append("<ns2:CustomerData xmlns:ns2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
		authorizeRequestXML.append("<ns2:BillingData>");
		authorizeRequestXML.append("<ns2:Name i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getName().isNillable()+"\">"+ authorizeTransaction.getTransaction().getCustomerData().getBillingData().getName().getValue() +"</ns2:Name>");
		authorizeRequestXML.append("<ns2:Address>");
		authorizeRequestXML.append("<ns2:Street1>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet1()+"</ns2:Street1>");
		authorizeRequestXML.append("<ns2:Street2 i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getName().isNillable()+"\">"+ authorizeTransaction.getTransaction().getCustomerData().getBillingData().getName().getValue() +"</ns2:Street2>");
		authorizeRequestXML.append("<ns2:City>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCity()+"</ns2:City>");
		authorizeRequestXML.append("<ns2:StateProvince>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStateProvince()+"</ns2:StateProvince>");
		authorizeRequestXML.append("<ns2:PostalCode>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getPostalCode()+"</ns2:PostalCode>");
		authorizeRequestXML.append("<ns2:CountryCode>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCountryCode()+"</ns2:CountryCode>");
		authorizeRequestXML.append("</ns2:Address>");
		authorizeRequestXML.append("<ns2:BusinessName>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getBusinessName()+"</ns2:BusinessName>");
		authorizeRequestXML.append("<ns2:Phone i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getPhone().isNillable()+"\">"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getPhone().getValue() +"</ns2:Phone>");
		authorizeRequestXML.append("<ns2:Fax i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getFax().isNillable()+"\">"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getFax().getValue() +"</ns2:Fax>");
		authorizeRequestXML.append("<ns2:Email i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getEmail().isNillable()+"\">"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getEmail().getValue() +"</ns2:Email>");
		authorizeRequestXML.append("</ns2:BillingData>");
		authorizeRequestXML.append("<ns2:CustomerId>"+authorizeTransaction.getTransaction().getCustomerData().getCustomerId() + "</ns2:CustomerId>");
		authorizeRequestXML.append("<ns2:CustomerTaxId i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getCustomerTaxId().isNillable()+"\">"+authorizeTransaction.getTransaction().getCustomerData().getCustomerTaxId().getValue() +"</ns2:CustomerTaxId>");
		authorizeRequestXML.append("<ns2:ShippingData i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getShippingData().isNillable()+"\">"+authorizeTransaction.getTransaction().getCustomerData().getShippingData().getValue() +"</ns2:ShippingData>");
		authorizeRequestXML.append("</ns2:CustomerData>");
		authorizeRequestXML.append("<ns3:ReportingData xmlns:ns3=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
		authorizeRequestXML.append("<ns3:Comment>"+authorizeTransaction.getTransaction().getReportingData().getComment() + "</ns3:Comment>");
		authorizeRequestXML.append("<ns3:Description>"+authorizeTransaction.getTransaction().getReportingData().getDescription()+ "</ns3:Description>");
		authorizeRequestXML.append("<ns3:Reference>"+authorizeTransaction.getTransaction().getReportingData().getReference()+ "</ns3:Reference>");
		authorizeRequestXML.append("</ns3:ReportingData>");
		authorizeRequestXML.append("<ns1:TenderData>");
		authorizeRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().isNillable()+"\">"+authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().getValue()+ "</ns4:PaymentAccountDataToken>");
		authorizeRequestXML.append("<ns5:SecurePaymentAccountData xmlns:ns5=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().isNillable()+"\">" + authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().getValue()+"</ns5:SecurePaymentAccountData>");
		authorizeRequestXML.append("<ns6:EncryptionKeyId xmlns:ns6=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getEncryptionKeyId().isNillable()+"\">" +authorizeTransaction.getTransaction().getTenderData().getEncryptionKeyId().getValue() +"</ns6:EncryptionKeyId>");
		authorizeRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().isNillable()+"\">"+authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().getValue() +"</ns7:SwipeStatus>");
		authorizeRequestXML.append("<ns1:CardData>");
		authorizeRequestXML.append("<ns1:CardType>"+authorizeTransaction.getTransaction().getTenderData().getCardData().getCardType()+"</ns1:CardType>");
		authorizeRequestXML.append("<ns1:PAN>"+authorizeTransaction.getTransaction().getTenderData().getCardData().getPan()+"</ns1:PAN>");
		authorizeRequestXML.append("<ns1:Expire>"+authorizeTransaction.getTransaction().getTenderData().getCardData().getExpiryDate()+"</ns1:Expire>");
		authorizeRequestXML.append("<ns1:Track1Data i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().isNillable()+"\">" +authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().getValue() +"</ns1:Track1Data>");
		authorizeRequestXML.append("</ns1:CardData>");
		authorizeRequestXML.append("<ns1:EcommerceSecurityData i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().isNillable()+"\">"+authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().getValue() +"</ns1:EcommerceSecurityData>");
		authorizeRequestXML.append("</ns1:TenderData>");
		authorizeRequestXML.append("<ns1:TransactionData>");
		authorizeRequestXML.append("<ns8:Amount xmlns:ns8=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeTransaction.getTransaction().getTransactionData().getAmount()+"</ns8:Amount>");
		authorizeRequestXML.append("<ns9:CurrencyCode xmlns:ns9=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeTransaction.getTransaction().getTransactionData().getCurrencyCode()+"</ns9:CurrencyCode>");
		authorizeRequestXML.append("<ns10:TransactionDateTime xmlns:ns10=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeTransaction.getTransaction().getTransactionData().getTransactionDateTime()+"</ns10:TransactionDateTime>");
		authorizeRequestXML.append("<ns11:CampaignId xmlns:ns11=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getCampaignId().isNillable()+"\">"+authorizeTransaction.getTransaction().getTransactionData().getCampaignId().getValue() +"</ns11:CampaignId>");
		authorizeRequestXML.append("<ns12:Reference xmlns:ns12=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeTransaction.getTransaction().getTransactionData().getReference()+"</ns12:Reference>");
		authorizeRequestXML.append("<ns1:AccountType>"+authorizeTransaction.getTransaction().getTransactionData().getAccountType()+ "</ns1:AccountType>");
		authorizeRequestXML.append("<ns1:ApprovalCode i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getApprovalCode().isNillable()+"\">" + authorizeTransaction.getTransaction().getTransactionData().getApprovalCode().getValue() +"</ns1:ApprovalCode>");
		authorizeRequestXML.append("<ns1:CashBackAmount>"+authorizeTransaction.getTransaction().getTransactionData().getCashBackAmount()+ "</ns1:CashBackAmount>");
		authorizeRequestXML.append("<ns1:CustomerPresent>"+authorizeTransaction.getTransaction().getTransactionData().getCustomerPresent()+"</ns1:CustomerPresent>");
		authorizeRequestXML.append("<ns1:EmployeeId>"+authorizeTransaction.getTransaction().getTransactionData().getEmployeeId()+"</ns1:EmployeeId>");
		authorizeRequestXML.append("<ns1:EntryMode>"+authorizeTransaction.getTransaction().getTransactionData().getEntryMode()+"</ns1:EntryMode>");
		authorizeRequestXML.append("<ns1:GoodsType>"+authorizeTransaction.getTransaction().getTransactionData().getGoodsType()+"</ns1:GoodsType>");
		authorizeRequestXML.append("<ns1:IndustryType>"+authorizeTransaction.getTransaction().getTransactionData().getIndustryType()+"</ns1:IndustryType>");
		authorizeRequestXML.append("<ns1:InternetTransactionData i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getInternetTransactionData().isNillable()+"\">" + authorizeTransaction.getTransaction().getTransactionData().getInternetTransactionData().getValue() +"</ns1:InternetTransactionData>");
		authorizeRequestXML.append("<ns1:InvoiceNumber>"+authorizeTransaction.getTransaction().getTransactionData().getInvoiceNumber()+"</ns1:InvoiceNumber>");
		authorizeRequestXML.append("<ns1:OrderNumber>"+authorizeTransaction.getTransaction().getTransactionData().getOrderNumber()+"</ns1:OrderNumber>");
		authorizeRequestXML.append("<ns1:IsPartialShipment>"+authorizeTransaction.getTransaction().getTransactionData().isPartialShipment()+"</ns1:IsPartialShipment>");
		authorizeRequestXML.append("<ns1:SignatureCaptured>"+authorizeTransaction.getTransaction().getTransactionData().isSignatureCaptured()+"</ns1:SignatureCaptured>");
		authorizeRequestXML.append("<ns1:FeeAmount>"+authorizeTransaction.getTransaction().getTransactionData().getFeeAmount()+"</ns1:FeeAmount>");
		authorizeRequestXML.append("<ns1:TerminalId i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getTerminalId().isNillable()+"\">" +authorizeTransaction.getTransaction().getTransactionData().getTerminalId().getValue() +"</ns1:TerminalId>");
		authorizeRequestXML.append("<ns1:LaneId i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getTerminalId().isNillable()+"\">"+authorizeTransaction.getTransaction().getTransactionData().getTerminalId().getValue() +"</ns1:LaneId>");
		authorizeRequestXML.append("<ns1:TipAmount>"+authorizeTransaction.getTransaction().getTransactionData().getTipAmount()+"</ns1:TipAmount>");
		authorizeRequestXML.append("<ns1:BatchAssignment i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getBatchAssignment().isNillable()+"\">"+ authorizeTransaction.getTransaction().getTransactionData().getBatchAssignment().getValue() +"</ns1:BatchAssignment>");
		authorizeRequestXML.append("<ns1:PartialApprovalCapable>"+authorizeTransaction.getTransaction().getTransactionData().getPartialApprovalCapable()+"</ns1:PartialApprovalCapable>");
		authorizeRequestXML.append("<ns1:ScoreThreshold i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getScoreThreshold().isNillable()+"\">"+authorizeTransaction.getTransaction().getTransactionData().getScoreThreshold().getValue() +"</ns1:ScoreThreshold>");
		authorizeRequestXML.append("<ns1:IsQuasiCash>"+authorizeTransaction.getTransaction().getTransactionData().isQuasiCash()+"</ns1:IsQuasiCash>");
		authorizeRequestXML.append("</ns1:TransactionData></Transaction></AuthorizeTransaction>");
		AppLogger.logDebug(this.getClass(), "generateAuthorizeRequestXMLInput", "XML input :: "+ authorizeRequestXML.toString());
		AppLogger.logDebug(this.getClass(), "generateAuthorizeRequestXMLInput", "Exiting...");
		return authorizeRequestXML.toString();
		} catch(Exception ex)
		{
			AppLogger.logError(this.getClass(), "generateAuthorizeRequestXMLInput", ex);
			throw new VelocityGenericException("Exception occured into generating authorize request XML:: "+ex.getMessage(), ex);
		}
	}
	  
    
	/* (non-Javadoc)
	 * @see com.velocity.transaction.processor.BaseProcessor#invokeAuthorizeRequest(com.velocity.models.request.authorize.AuthorizeTransaction)
	 */
	public VelocityResponse invokeAuthorizeRequest(com.velocity.models.request.authorize.AuthorizeTransaction authorizeTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityRestInvokeException, VelocityNotFound
	{
		AppLogger.logDebug(getClass(), "invokeAuthorizeRequest", "Entering...");
			
		    /* Validating the session token. */
			if(encSessionToken == null || encSessionToken.isEmpty())
			{
				/* Setting Velocity session token. */
				sessionToken = invokeSignOn(identityToken);
				/* Encrypting the Velocity server session token. */
				encSessionToken = new String(Base64.encode((sessionToken + ":").getBytes()));
			}
			
			if(authorizeTransaction == null)
			{
				throw new VelocityIllegalArgument("AuthorizeTransaction can not be null or empty.");
			}
			
			/* Creating Http client for authorize request. */
			CloseableHttpClient httpClient = HttpClients.createDefault();
			/* Generating authorize XML input request. */
			String authorizeTxnRequestXML =  generateAuthorizeRequestXMLInput(authorizeTransaction);
			AppLogger.logDebug(getClass(), "invokeAuthorizeRequest", "Authorize XML input >>>>>>>>>>>>> "+authorizeTxnRequestXML);
			/*Invoking the Authorize request URL*/
			String invokeURL = serverURL + "/Txn/" + workFlowId;
			AppLogger.logDebug(getClass(), "invokeAuthorizeRequest", "Authorize request invokeURL == "+invokeURL);
			VelocityResponse velocityResponse = generateVelocityResponse(VelocityConstants.POST_METHOD, invokeURL, "Basic "+encSessionToken, "application/xml", authorizeTxnRequestXML);
			txnRequestXML = authorizeTxnRequestXML ;
			AppLogger.logDebug(getClass(), "invokeAuthorizeRequest", "AuthorizeTransaction response message == "+ velocityResponse.getMessage());
			AppLogger.logDebug(getClass(), "invokeAuthorizeRequest", "Exiting...");
			return velocityResponse;

	}
	
	
	//====================================================================AuthorizeAndCapture===============================================================================//
	
	/* (non-Javadoc)
	 * @see com.velocity.transaction.processor.BaseProcessor#invokeAuthorizeAndCaptureRequest(com.velocity.models.request.authorizeAndCapture.AuthorizeAndCaptureTransaction)
	 */
	public VelocityResponse invokeAuthorizeAndCaptureRequest(com.velocity.models.request.authorizeAndCapture.AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException
	{
		AppLogger.logDebug(getClass(), "invokeAuthorizeAndCaptureRequest", "Entering...");
        /* Validating the session token. */
		if(encSessionToken == null || encSessionToken.isEmpty())
		{
			/* Setting Velocity session token. */
			sessionToken = invokeSignOn(identityToken);
			/* Encrypting the Velocity server session token. */
			encSessionToken = new String(Base64.encode((sessionToken + ":").getBytes()));
		}

		if(authorizeAndCaptureTransaction == null)
		{
			throw new VelocityIllegalArgument("AuthorizeAndCaptureTransaction param can not be null or empty.");
		}

		/* Generating authorizeAndCapture XML input request. */
		String authorizeAndCaptureTxnRequestXML =  generateAuthorizeAndCaptureRequestXMLInput(authorizeAndCaptureTransaction);
		AppLogger.logDebug(getClass(), "invokeAuthorizeAndCaptureRequest", "AuthorizeAndCapture XML input == "+authorizeAndCaptureTxnRequestXML);
		/*Invoking URL for the XML input request*/
		String invokeURL = serverURL + "/Txn/"+ workFlowId;
		AppLogger.logDebug(getClass(), "invokeAuthorizeAndCaptureRequest", "AuthorizeAndCaptureTransaction request invokeURL == "+invokeURL);
		VelocityResponse velocityResponse = generateVelocityResponse(VelocityConstants.POST_METHOD, invokeURL, "Basic "+encSessionToken, "application/xml", authorizeAndCaptureTxnRequestXML);
		txnRequestXML = authorizeAndCaptureTxnRequestXML ;
		AppLogger.logDebug(getClass(), "invokeAuthorizeAndCaptureRequest", "AuthorizeAndCaptureTransaction response message == "+velocityResponse.getMessage());
		AppLogger.logDebug(getClass(), "invokeAuthorizeAndCaptureRequest", "Exiting...");
		return velocityResponse;

	}
	
	
	/**
	 * This method generates the input XML for AuthorizeAndCapture request.
	 * @author vimalk2
	 * @param authorizeAndCaptureTransaction - Object for AuthorizeAndCaptureTransaction Transaction.
	 * @return String - Generated AuthorizeAndCapture request XML.
	 * @throws VelocityGenericException - Thrown for AuthorizeAndCapture request XML generation.
	 */
	private String generateAuthorizeAndCaptureRequestXMLInput(com.velocity.models.request.authorizeAndCapture.AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction) throws VelocityIllegalArgument , VelocityGenericException
	{
		AppLogger.logDebug(this.getClass(), "generateAuthorizeAndCaptureRequestXMLInput", "Entering...");
		
		/* StringBuilder instance to generate authorizeAndCaptureRequestXML string. */
		StringBuilder authorizeAndCaptureRequestXML=null;
		
		try {
			
			if(authorizeAndCaptureTransaction == null)
			{
				throw new VelocityIllegalArgument("AuthorizeAndCaptureTransaction param can not be null.");
			}
			
			/* Providing the AuthorizeAndCapture request data for generating its request XML. */
			authorizeAndCaptureRequestXML= new StringBuilder("<AuthorizeAndCaptureTransaction xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"AuthorizeAndCaptureTransaction\">");
			authorizeAndCaptureRequestXML.append("<ApplicationProfileId>"+ appProfileId +"</ApplicationProfileId>");
			authorizeAndCaptureRequestXML.append("<MerchantProfileId>"+ merchantProfileId +"</MerchantProfileId>");
			authorizeAndCaptureRequestXML.append("<Transaction xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" i:type=\"ns1:"+authorizeAndCaptureTransaction.getTransaction().getType()+"\">");
			authorizeAndCaptureRequestXML.append("<ns2:CustomerData xmlns:ns2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
			authorizeAndCaptureRequestXML.append("<ns2:BillingData>");
			authorizeAndCaptureRequestXML.append("<ns2:Name i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getName().isNillable()+"\">"+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getName().getValue() +"</ns2:Name>");
			authorizeAndCaptureRequestXML.append("<ns2:Address>");
			authorizeAndCaptureRequestXML.append("<ns2:Street1>"+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet1()+"</ns2:Street1>");
			authorizeAndCaptureRequestXML.append("<ns2:Street2 i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().isNillable()+"\">"+ authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().getValue() +"</ns2:Street2>");
			authorizeAndCaptureRequestXML.append("<ns2:City>"+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCity()+"</ns2:City>");
			authorizeAndCaptureRequestXML.append("<ns2:StateProvince>"+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStateProvince()+"</ns2:StateProvince>");
			authorizeAndCaptureRequestXML.append("<ns2:PostalCode>"+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getPostalCode()+"</ns2:PostalCode>");
			authorizeAndCaptureRequestXML.append("<ns2:CountryCode>"+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCountryCode()+"</ns2:CountryCode>");
			authorizeAndCaptureRequestXML.append("</ns2:Address>");
			authorizeAndCaptureRequestXML.append("<ns2:BusinessName>"+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getBusinessName()+"</ns2:BusinessName>");
			authorizeAndCaptureRequestXML.append("<ns2:Phone i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getPhone().isNillable()+"\">"+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getPhone().getValue() +"</ns2:Phone>");
			authorizeAndCaptureRequestXML.append("<ns2:Fax i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getFax().isNillable()+"\">"+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getFax().getValue() +"</ns2:Fax>");
			authorizeAndCaptureRequestXML.append("<ns2:Email i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getEmail().isNillable()+"\">"+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getEmail().getValue() +"</ns2:Email>");
			authorizeAndCaptureRequestXML.append("</ns2:BillingData>");
			authorizeAndCaptureRequestXML.append("<ns2:CustomerId>"+authorizeAndCaptureTransaction.getTransaction().getCustomerData(). getCustomerId()+"</ns2:CustomerId>");
			authorizeAndCaptureRequestXML.append("<ns2:CustomerTaxId i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getCustomerTaxId().isNillable()+"\">" +authorizeAndCaptureTransaction.getTransaction().getCustomerData().getCustomerTaxId().getValue() +"</ns2:CustomerTaxId>");
			authorizeAndCaptureRequestXML.append("<ns2:ShippingData i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getShippingData().isNillable()+"\">"+authorizeAndCaptureTransaction.getTransaction().getCustomerData().getShippingData().getValue() +"</ns2:ShippingData>");
			authorizeAndCaptureRequestXML.append("</ns2:CustomerData>");
			authorizeAndCaptureRequestXML.append("<ns3:ReportingData xmlns:ns3=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
			authorizeAndCaptureRequestXML.append("<ns3:Comment>"+authorizeAndCaptureTransaction.getTransaction().getReportingData().getComment()+"</ns3:Comment>");
			authorizeAndCaptureRequestXML.append("<ns3:Description>"+authorizeAndCaptureTransaction.getTransaction().getReportingData().getDescription()+ "</ns3:Description>");
			authorizeAndCaptureRequestXML.append("<ns3:Reference>"+authorizeAndCaptureTransaction.getTransaction().getReportingData().getReference()+ "</ns3:Reference>");
			authorizeAndCaptureRequestXML.append("</ns3:ReportingData>");
			authorizeAndCaptureRequestXML.append("<ns1:TenderData>");
			authorizeAndCaptureRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().isNillable()+"\">" +authorizeAndCaptureTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().getValue()+"</ns4:PaymentAccountDataToken>");
			authorizeAndCaptureRequestXML.append("<ns5:SecurePaymentAccountData xmlns:ns5=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().isNillable()+"\">" + authorizeAndCaptureTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().getValue() +"</ns5:SecurePaymentAccountData>");
			authorizeAndCaptureRequestXML.append("<ns6:EncryptionKeyId xmlns:ns6=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getTenderData().getEncryptionKeyId().isNillable()+"\">"+authorizeAndCaptureTransaction.getTransaction().getTenderData().getEncryptionKeyId().getValue() +"</ns6:EncryptionKeyId>");
			authorizeAndCaptureRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getTenderData().getSwipeStatus().isNillable()+"\">"+authorizeAndCaptureTransaction.getTransaction().getTenderData().getSwipeStatus().getValue() +"</ns7:SwipeStatus>");
			authorizeAndCaptureRequestXML.append("<ns1:CardData>");
			authorizeAndCaptureRequestXML.append("<ns1:CardType>"+authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().getCardType()+"</ns1:CardType>");
			authorizeAndCaptureRequestXML.append("<ns1:PAN>"+authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().getPan()+"</ns1:PAN>");
			authorizeAndCaptureRequestXML.append("<ns1:Expire>"+authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().getExpiryDate()+"</ns1:Expire>");
			authorizeAndCaptureRequestXML.append("<ns1:Track1Data i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().isNillable()+"\">"+authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().getValue() +"</ns1:Track1Data>");
			authorizeAndCaptureRequestXML.append("</ns1:CardData>");
			authorizeAndCaptureRequestXML.append("<ns1:EcommerceSecurityData i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getTenderData().getEcommerceSecurityData().isNillable()+"\">"+authorizeAndCaptureTransaction.getTransaction().getTenderData().getEcommerceSecurityData().getValue() +"</ns1:EcommerceSecurityData>");
			authorizeAndCaptureRequestXML.append("</ns1:TenderData> ");
			authorizeAndCaptureRequestXML.append("<ns1:TransactionData>");
			authorizeAndCaptureRequestXML.append("<ns8:Amount xmlns:ns8=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getAmount()+"</ns8:Amount>");
			authorizeAndCaptureRequestXML.append("<ns9:CurrencyCode xmlns:ns9=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getCurrencyCode()+"</ns9:CurrencyCode>");
			authorizeAndCaptureRequestXML.append("<ns10:TransactionDateTime xmlns:ns10=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getTransactionDateTime()+"</ns10:TransactionDateTime>");
			authorizeAndCaptureRequestXML.append("<ns11:CampaignId xmlns:ns11=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getCampaignId().isNillable()+"\">"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getCampaignId().getValue() +"</ns11:CampaignId>");
			authorizeAndCaptureRequestXML.append("<ns12:Reference xmlns:ns12=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getReference()+"</ns12:Reference>");
			authorizeAndCaptureRequestXML.append("<ns1:AccountType>"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getAccountType()+ "</ns1:AccountType>");
			authorizeAndCaptureRequestXML.append("<ns1:ApprovalCode i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getApprovalCode().isNillable()+"\">"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getApprovalCode().getValue() +"</ns1:ApprovalCode>");
			authorizeAndCaptureRequestXML.append("<ns1:CashBackAmount>"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getCashBackAmount()+ "</ns1:CashBackAmount>");
			authorizeAndCaptureRequestXML.append("<ns1:CustomerPresent>"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getCustomerPresent()+"</ns1:CustomerPresent>");
			authorizeAndCaptureRequestXML.append("<ns1:EmployeeId>"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getEmployeeId()+"</ns1:EmployeeId>");
			authorizeAndCaptureRequestXML.append("<ns1:EntryMode>"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getEntryMode()+"</ns1:EntryMode>");
			authorizeAndCaptureRequestXML.append("<ns1:GoodsType>"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getGoodsType()+"</ns1:GoodsType>");
			authorizeAndCaptureRequestXML.append("<ns1:IndustryType>"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getIndustryType()+"</ns1:IndustryType>");
			authorizeAndCaptureRequestXML.append("<ns1:InternetTransactionData i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getInternetTransactionData().isNillable()+"\">"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getInternetTransactionData().getValue() +"</ns1:InternetTransactionData>");
			authorizeAndCaptureRequestXML.append("<ns1:InvoiceNumber>"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getInvoiceNumber()+"</ns1:InvoiceNumber>");
			authorizeAndCaptureRequestXML.append("<ns1:OrderNumber>"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getOrderNumber()+"</ns1:OrderNumber>");
			authorizeAndCaptureRequestXML.append("<ns1:IsPartialShipment>"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().isPartialShipment()+"</ns1:IsPartialShipment>");
			authorizeAndCaptureRequestXML.append("<ns1:SignatureCaptured>"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().isSignatureCaptured()+"</ns1:SignatureCaptured>");
			authorizeAndCaptureRequestXML.append("<ns1:FeeAmount>"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getFeeAmount()+"</ns1:FeeAmount>");
			authorizeAndCaptureRequestXML.append("<ns1:TerminalId i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getTerminalId().isNillable()+"\">"+ authorizeAndCaptureTransaction.getTransaction().getTransactionData().getTerminalId().getValue() +"</ns1:TerminalId>");
			authorizeAndCaptureRequestXML.append("<ns1:LaneId i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getLaneId().isNillable()+"\">"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getLaneId().getValue() +"</ns1:LaneId>");
			authorizeAndCaptureRequestXML.append("<ns1:TipAmount>"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getTipAmount()+"</ns1:TipAmount>");
			authorizeAndCaptureRequestXML.append("<ns1:BatchAssignment i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getBatchAssignment().isNillable()+"\">"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getBatchAssignment().getValue() +"</ns1:BatchAssignment>");
			authorizeAndCaptureRequestXML.append("<ns1:PartialApprovalCapable>"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getPartialApprovalCapable()+"</ns1:PartialApprovalCapable>");
			authorizeAndCaptureRequestXML.append("<ns1:ScoreThreshold i:nil=\""+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getScoreThreshold().isNillable()+"\">"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().getScoreThreshold().getValue() +"</ns1:ScoreThreshold>");
			authorizeAndCaptureRequestXML.append("<ns1:IsQuasiCash>"+authorizeAndCaptureTransaction.getTransaction().getTransactionData().isQuasiCash()+"</ns1:IsQuasiCash>");
			authorizeAndCaptureRequestXML.append("</ns1:TransactionData></Transaction></AuthorizeAndCaptureTransaction>");
			AppLogger.logDebug(this.getClass(), "generateAuthorizeAndCaptureRequestXMLInput", "XML input :: "+ authorizeAndCaptureRequestXML.toString());
			AppLogger.logDebug(this.getClass(), "generateAuthorizeAndCaptureRequestXMLInput", "Exiting...");			
			
			return authorizeAndCaptureRequestXML.toString();
			
		}catch(Exception ex)
		
		{
			AppLogger.logError(this.getClass(), "generateAuthorizeAndCaptureRequestXMLInput", ex);
		  throw	new VelocityGenericException("Exception occured into generating authorize and capture request XML:: "+ex.getMessage(), ex);
		}

	}

//==========================================================================Capture Method==============================================================================//	
	
	/**
	 * This method generates the XML for Capture request.
	 * @author vimalk2
	 * @param  captureTransaction - stores the value for the type ChangeTransaction.
	 * @return String - Instance of the type String.
	 * @throws VelocityGenericException - thrown when the exception occurs during the transaction.
	 * @throws VelocityIllegalArgument - thrown when null or bad data is passed.
	 */
	
	private String generateCaptureRequestXMLInput(com.velocity.models.request.capture.ChangeTransaction captureTransaction) throws VelocityGenericException,VelocityIllegalArgument
	{
		
		AppLogger.logDebug(this.getClass(), "generateCaptureRequestXMLInput", "Entering...");
		
		/* StringBuilder instance to generate captureRequestXML string. */
		StringBuilder captureRequestXML=null;
		
       try {
			
			if(captureTransaction == null)
			{
				throw new VelocityIllegalArgument("ChangeTransaction param can not be null.");
			}
		
		/* Providing the Capture request data for generating its request XML. */
		captureRequestXML= new StringBuilder("<ChangeTransaction xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" i:type=\"Capture\">");
		captureRequestXML.append("<ApplicationProfileId>"+ appProfileId +"</ApplicationProfileId>");
		captureRequestXML.append("<DifferenceData xmlns:d2p1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" xmlns:d2p2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" xmlns:d2p3=\"http://schemas.ipcommerce.com/CWS/v2.0/TransactionProcessing\" i:type=\"d2p2:"+ captureTransaction.getDifferenceData().getType() +"\">");
		captureRequestXML.append("<d2p1:TransactionId>"+captureTransaction.getDifferenceData().getTransactionId()+"</d2p1:TransactionId>");
		captureRequestXML.append("<d2p2:Amount>"+captureTransaction.getDifferenceData().getAmount()+"</d2p2:Amount>");
		captureRequestXML.append("<d2p2:TipAmount>"+captureTransaction.getDifferenceData().getTipAmount()+"</d2p2:TipAmount>");
		captureRequestXML.append("</DifferenceData>");
		captureRequestXML.append("</ChangeTransaction>");
		AppLogger.logDebug(this.getClass(), "generateCaptureRequestXMLInput", "XML input :: "+ captureRequestXML.toString());
		AppLogger.logDebug(this.getClass(), "generateCaptureRequestXMLInput", "Exiting...");
		
		return captureRequestXML.toString();
		
       }catch(Exception ex)
		
		{
			AppLogger.logError(this.getClass(), "generateCaptureRequestXMLInput", ex);
			throw new VelocityGenericException("Exception occured into generating capture request XML:: "+ex.getMessage(), ex);
		}	
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.velocity.transaction.processor.BaseProcessor#invokeCaptureRequest(com.velocity.models.request.capture.ChangeTransaction)
	 */
	public VelocityResponse invokeCaptureRequest(com.velocity.models.request.capture.ChangeTransaction captureTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException
	{
		AppLogger.logDebug(getClass(), "invokeCaptureRequest", "Entering...");
		
			
			if(captureTransaction == null)
			{
				throw new VelocityGenericException("ChangeTransaction param can not be null or empty.");
			}
			
			/* Validating the session token. */
			if(encSessionToken == null || encSessionToken.isEmpty())
			{
				/* Setting Velocity session token. */
				sessionToken = invokeSignOn(identityToken);
				/* Encrypting the Velocity server session token. */
				encSessionToken = new String(Base64.encode((sessionToken + ":").getBytes()));
			}
			
			/* Generating Capture XML input request. */
			String captureTxnRequestXML =  generateCaptureRequestXMLInput(captureTransaction);
			AppLogger.logDebug(getClass(), "invokeCaptureRequest", "Capture XML input == "+captureTxnRequestXML);
			/*Invoking URL for the XML input request*/
			String invokeURL = serverURL + "/Txn/"+ workFlowId + "/" + captureTransaction.getDifferenceData().getTransactionId();
			AppLogger.logDebug(getClass(), "invokeCaptureRequest", "CaptureRequest request invokeURL == "+invokeURL);
			VelocityResponse velocityResponse = generateVelocityResponse(VelocityConstants.PUT_METHOD, invokeURL, "Basic "+encSessionToken, "application/xml", captureTxnRequestXML);
			txnRequestXML = captureTxnRequestXML ;
			AppLogger.logDebug(getClass(), "invokeCaptureRequest", "ChangeTransaction response message == "+velocityResponse.getMessage());
			AppLogger.logDebug(getClass(), "invokeCaptureRequest", "Exiting...");
			return velocityResponse;
			
	}
	
	
	//=========================================================================Undo Method==================================================================================\\
	
	/**
	 * This method generates the XML for Undo request.
	 * @author vimalk2
	 * @param  undoTransaction - stores the value for UndoTransaction
	 * @return String - Instance of the type String.
	 * @throws VelocityGenericException - thrown when the exception occurs during the transaction. 
	 * @throws VelocityIllegalArgument - thrown when Illegal argument is supplied.
	 */
	
	private String generateUndoRequestXMLInput(com.velocity.models.request.undo.Undo undoTransaction) throws VelocityGenericException, VelocityIllegalArgument
	{
		
		AppLogger.logDebug(this.getClass(), "generateUndoRequestXMLInput", "Entering...");
		
		/* StringBuilder instance to generate undoRequestXML string. */
		StringBuilder undoRequestXML=null;

		try {

			if(undoTransaction == null)
			{
				throw new VelocityIllegalArgument("Undo param can not be null.");
			}

			/* Providing the undo request data for generating its request XML. */
			undoRequestXML= new StringBuilder("<Undo xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"Undo\">");
			undoRequestXML.append("<ApplicationProfileId>"+ appProfileId+"</ApplicationProfileId>");
			undoRequestXML.append("<BatchIds xmlns:d2p1=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\" i:nil=\""+undoTransaction.getBatchIds().isNillable()+"\">"+ undoTransaction.getBatchIds().getValue() +"</BatchIds>");
			undoRequestXML.append("<DifferenceData xmlns:d2p1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+undoTransaction.getDifferenceData().isNillable()+"\">"+undoTransaction.getDifferenceData().getValue() +"</DifferenceData>");
			undoRequestXML.append("<MerchantProfileId>"+ merchantProfileId+"</MerchantProfileId>");
			undoRequestXML.append("<TransactionId>"+ undoTransaction.getTransactionId() +"</TransactionId>");
			undoRequestXML.append("</Undo>");
			AppLogger.logDebug(this.getClass(), "generateUndoRequestXMLInput", "XML input :: "+ undoRequestXML.toString());
			AppLogger.logDebug(this.getClass(), "generateUndoRequestXMLInput", "Exiting...");

			return undoRequestXML.toString();

		}
		catch(Exception ex)
		{
			AppLogger.logError(this.getClass(), "generateUndoRequestXMLInput", ex);
			throw new VelocityGenericException("Exception occured into generating Undo request XML:: "+ex.getMessage(), ex);
		}	

	}
	
	
	/* (non-Javadoc)
	 * @see com.velocity.transaction.processor.BaseProcessor#invokeUndoRequest(com.velocity.models.request.undo.Undo)
	 */
	public VelocityResponse invokeUndoRequest(com.velocity.models.request.undo.Undo undoTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException
	{
		AppLogger.logDebug(getClass(), "invokeUndoRequest", "Entering...");
			
			if(undoTransaction == null)
			{
				throw new VelocityIllegalArgument("Undo param can not be null or empty.");
			}
			
			/* Validating the session token. */
			if(encSessionToken == null || encSessionToken.isEmpty())
			{
				/* Setting Velocity session token. */
				sessionToken = invokeSignOn(identityToken);
				/* Encrypting the Velocity server session token. */
				encSessionToken = new String(Base64.encode((sessionToken + ":").getBytes()));
			}
			
			/* Generating Undo XML input request. */
			String undoTxnRequestXML =  generateUndoRequestXMLInput(undoTransaction);
			AppLogger.logDebug(getClass(), "invokeUndoRequest", "Undo XML input == "+undoTxnRequestXML);
			/*Invoking URL for the XML input request*/
			String invokeURL = serverURL + "/Txn/"+ workFlowId + "/" + undoTransaction.getTransactionId();
			AppLogger.logDebug(getClass(), "invokeUndoRequest", "UndoRequest request invokeURL == "+invokeURL);
			VelocityResponse velocityResponse = generateVelocityResponse(VelocityConstants.PUT_METHOD, invokeURL, "Basic "+encSessionToken, "application/xml", undoTxnRequestXML);
			txnRequestXML = undoTxnRequestXML;
			AppLogger.logDebug(getClass(), "invokeUndoRequest", "Undo response message == "+velocityResponse.getMessage());
			AppLogger.logDebug(getClass(), "UndoRequest", "Exiting...");
			return velocityResponse;
		
	}
	
//===============================================================================Adjust================================================================================//
	
	/**
	 * This method generates the input XML for Adjust request.
	 * @author vimalk2
	 * @param  adjustTransaction - stores the value for Adjust Transaction
	 * @return String - Instance of the type String.
	 * @throws VelocityGenericException - thrown when Exception occurs at invoking the AdjustRequest
	 * @throws VelocityIllegalArgument - thrown when null or bad data is passed.
	 */
	
	private String generateAdjustRequestXMLInput(com.velocity.models.request.adjust.Adjust adjustTransaction) throws VelocityGenericException,VelocityIllegalArgument
	{
		AppLogger.logDebug(this.getClass(), "generateAdjustRequestXMLInput", "Entering...");
		
		/* StringBuilder instance to generate adjustRequestXML string. */
		StringBuilder adjustRequestXML=null;


		try {

			if(adjustTransaction == null)
			{
				throw new VelocityIllegalArgument("Adjust param can not be null.");
			}

			/* Providing the adjust request data for generating its request XML. */
			adjustRequestXML= new StringBuilder("<Adjust xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"Adjust\">");
			adjustRequestXML.append("<ApplicationProfileId>"+ appProfileId+"</ApplicationProfileId>");
			adjustRequestXML.append("<BatchIds xmlns:d2p1=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\" i:nil=\""+adjustTransaction.getBatchIds().isNillable()+"\">" +adjustTransaction.getBatchIds().getValue() +"</BatchIds>");
			adjustRequestXML.append("<MerchantProfileId>"+ merchantProfileId+"</MerchantProfileId>");
			adjustRequestXML.append("<DifferenceData xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
			adjustRequestXML.append("<ns2:Amount xmlns:ns2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">" + adjustTransaction.getDifferenceData().getAmount()+"</ns2:Amount>");
			adjustRequestXML.append("<ns3:TransactionId xmlns:ns3=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+ adjustTransaction.getDifferenceData().getTransactionId() +"</ns3:TransactionId>");
			adjustRequestXML.append("</DifferenceData>");
			adjustRequestXML.append("</Adjust>");
			AppLogger.logDebug(this.getClass(), "generateAdjustRequestXMLInput", "XML input :: "+ adjustRequestXML.toString());
			AppLogger.logDebug(this.getClass(), "generateAdjustRequestXMLInput", "Exiting...");

			return adjustRequestXML.toString();

		}
		catch(Exception ex)
		{
			AppLogger.logError(this.getClass(), "generateAdjustRequestXMLInput", ex);
			throw new VelocityGenericException("Exception occured into generating Adjust request XML:: "+ex.getMessage(), ex);
		}	

	}
	
	/* (non-Javadoc)
	 * @see com.velocity.transaction.processor.BaseProcessor#invokeAdjustRequest(com.velocity.models.request.adjust.Adjust)
	 */
	public VelocityResponse invokeAdjustRequest(com.velocity.models.request.adjust.Adjust adjustTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException
	{
		AppLogger.logDebug(getClass(), "invokeAdjustRequest", "Entering...");
			
			if(adjustTransaction == null)
			{
				throw new VelocityIllegalArgument("Adjust param can not be null or empty.");
			}
			
			/* Validating the session token. */
			if(encSessionToken == null || encSessionToken.isEmpty())
			{
				/* Setting Velocity session token. */
				sessionToken = invokeSignOn(identityToken);
				/* Encrypting the Velocity server session token. */
				encSessionToken = new String(Base64.encode((sessionToken + ":").getBytes()));
			}
			
			/* Generating Adjust XML input request. */
			String adjustTxnRequestXML =  generateAdjustRequestXMLInput(adjustTransaction);
			AppLogger.logDebug(getClass(), "invokeAdjustRequest", "Adjust XML input == "+adjustTxnRequestXML);
			/*Invoking URL for the XML input request*/
			String invokeURL = serverURL + "/Txn/"+ workFlowId + "/" + adjustTransaction.getDifferenceData().getTransactionId();
			AppLogger.logDebug(getClass(), "invokeAdjustRequest", "AdjustRequest request invokeURL == "+invokeURL);
			VelocityResponse velocityResponse = generateVelocityResponse(VelocityConstants.PUT_METHOD, invokeURL, "Basic "+encSessionToken, "application/xml", adjustTxnRequestXML);
			txnRequestXML = adjustTxnRequestXML;
			AppLogger.logDebug(getClass(), "invokeAdjustRequest", "Adjust response message == "+velocityResponse.getMessage());
			AppLogger.logDebug(getClass(), "AdjustRequest", "Exiting...");
			return velocityResponse;
		
	}
	
	//===============================================================================ReturnById================================================================================//
	
	/**
	 * This method generates the input XML for ReturnById request.
	 * @author vimalk2
	 * @param  returnByIdTransaction - object for ReturnById Transaction
	 * @return String - Instance of the type String.
	 * @throws VelocityGenericException - thrown when Exception occurs at generating the the ReturnByIdRequest
	 * @throws VelocityIllegalArgument - thrown when null or bad data is passed.
	 */
	
	private String generateReturnByIdRequestXMLInput(com.velocity.models.request.returnById.ReturnById returnByIdTransaction) throws VelocityGenericException,VelocityIllegalArgument
	{
		AppLogger.logDebug(this.getClass(), "generateReturnByIdRequestXMLInput", "Entering...");
		
		/* StringBuilder instance to generate returnByIdRequestXML string. */
		StringBuilder returnByIdRequestXML=null;

		try {

			if(returnByIdTransaction == null)
			{
				throw new VelocityIllegalArgument("ReturnById param can not be null.");
			}

			/* Providing the returnById request data for generating its request XML.*/ 
			returnByIdRequestXML= new StringBuilder("<ReturnById xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"ReturnById\">");
			returnByIdRequestXML.append("<ApplicationProfileId>"+ appProfileId+"</ApplicationProfileId>");
			returnByIdRequestXML.append("<BatchIds xmlns:d2p1=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\" i:nil=\""+returnByIdTransaction.getBatchIds().isNillable()+"\">"+returnByIdTransaction.getBatchIds().getValue() +"</BatchIds>");
			returnByIdRequestXML.append("<DifferenceData xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" i:type=\"ns1:BankcardReturn\">");
			returnByIdRequestXML.append("<ns2:TransactionId xmlns:ns2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+ returnByIdTransaction.getDifferenceData().getTransactionId() +"</ns2:TransactionId>");
			returnByIdRequestXML.append("<ns1:Amount>"+ returnByIdTransaction.getDifferenceData().getAmount()+"</ns1:Amount>");
			returnByIdRequestXML.append("</DifferenceData>");
			returnByIdRequestXML.append("<MerchantProfileId>"+ merchantProfileId+"</MerchantProfileId>");
			returnByIdRequestXML.append("</ReturnById>");
			AppLogger.logDebug(this.getClass(), "generateReturnByIdRequestXMLInput", "XML input :: "+ returnByIdRequestXML.toString());
			AppLogger.logDebug(this.getClass(), "generateReturnByIdRequestXMLInput", "Exiting...");
			
			return returnByIdRequestXML.toString();

		}
		catch(Exception ex)
		{
			AppLogger.logError(this.getClass(), "generateReturnByIdRequestXMLInput", ex);
			throw new VelocityGenericException("Exception occured into generating ReturnById request XML:: "+ex.getMessage(), ex);
		}	

	}
	
	
	/* (non-Javadoc)
	 * @see com.velocity.transaction.processor.BaseProcessor#invokeReturnByIdRequest(com.velocity.models.request.returnById.ReturnById)
	 */
	public VelocityResponse invokeReturnByIdRequest(com.velocity.models.request.returnById.ReturnById returnByIdTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException
	{
		AppLogger.logDebug(getClass(), "invokeReturnByIdRequest", "Entering...");
			
			if(returnByIdTransaction == null)
			{
				throw new VelocityIllegalArgument("ReturnById param can not be null.");
			}
			
			/* Validating the session token. */
			if(encSessionToken == null || encSessionToken.isEmpty())
			{
				/* Setting Velocity session token. */
				sessionToken = invokeSignOn(identityToken);
				/* Encrypting the Velocity server session token. */
				encSessionToken = new String(Base64.encode((sessionToken + ":").getBytes()));
			}
			
			/* Generating ReturnById XML input request. */
			String returnByIdTxnRequestXML =  generateReturnByIdRequestXMLInput(returnByIdTransaction);
			AppLogger.logDebug(getClass(), "invokeReturnByIdRequest", "ReturnById XML input == "+returnByIdTxnRequestXML);
			/*Invoking URL for the XML input request*/
			String invokeURL = serverURL + "/Txn/"+ workFlowId;
			AppLogger.logDebug(getClass(), "invokeReturnByIdRequest", "ReturnByIdRequest request invokeURL == "+invokeURL);
			VelocityResponse velocityResponse = generateVelocityResponse(VelocityConstants.POST_METHOD, invokeURL, "Basic "+encSessionToken, "application/xml", returnByIdTxnRequestXML);
			txnRequestXML = returnByIdTxnRequestXML;
			AppLogger.logDebug(getClass(), "invokeReturnByIdRequest", "ReturnById response message == "+velocityResponse.getMessage());
			AppLogger.logDebug(getClass(), "ReturnByIdRequest", "Exiting...");
			return velocityResponse;
		
	}

	//===============================================================================ReturnUnlinked========================================================================//
	
	/**
	 * This method generates the input XML for ReturnUnlinked request.
	 * @author vimalk2
	 * @param  returnUnlinkedTransaction - stores ReturnUnlinked data 
	 * @return String - Instance of the type String.
	 * @throws VelocityGenericException - thrown when Exception occurs at generating the the ReturnUnlinkedRequest
	 * @throws VelocityIllegalArgument - thrown when null or bad data is passed.
	 */
	
	private String generateReturnUnlinkedRequestXMLInput(com.velocity.models.request.returnUnLinked.ReturnTransaction returnUnlinkedTransaction) throws VelocityGenericException, VelocityIllegalArgument
	{
		AppLogger.logDebug(this.getClass(), "generateReturnUnlinkedRequestXMLInput", "Entering...");
		
		/* StringBuilder instance to generate returnUnlinkedRequestXML string. */
		StringBuilder returnUnlinkedRequestXML=null;

		try {

			if(returnUnlinkedTransaction == null)
			{
				throw new VelocityIllegalArgument("ReturnUnlinked param can not be null.");
			}

			/* Providing the returnUnlinked request data for generating its request XML.*/ 

			returnUnlinkedRequestXML= new StringBuilder("<ReturnTransaction xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"ReturnTransaction\">");
			returnUnlinkedRequestXML.append("<ApplicationProfileId>"+ appProfileId+"</ApplicationProfileId>");
			returnUnlinkedRequestXML.append("<BatchIds xmlns:d2p1=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\" i:nil=\""+returnUnlinkedTransaction.getBatchIds().isNillable()+"\">"+ returnUnlinkedTransaction.getBatchIds().getValue() +"</BatchIds>");
			returnUnlinkedRequestXML.append("<MerchantProfileId>"+ merchantProfileId+"</MerchantProfileId>");
			returnUnlinkedRequestXML.append("<Transaction xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" i:type=\"ns1:BankcardTransaction\">");
			returnUnlinkedRequestXML.append("<ns2:CustomerData xmlns:ns2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
			returnUnlinkedRequestXML.append("<ns2:BillingData>");
			returnUnlinkedRequestXML.append("<ns2:Name i:nil=\""+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getName().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getName().getValue() +"</ns2:Name>");
			returnUnlinkedRequestXML.append("<ns2:Address>");
			returnUnlinkedRequestXML.append("<ns2:Street1>"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet1()+"</ns2:Street1>");
			returnUnlinkedRequestXML.append("<ns2:Street2 i:nil=\""+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().isNillable()+"\">"+ returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().getValue() +"</ns2:Street2>");
			returnUnlinkedRequestXML.append("<ns2:City>"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCity()+"</ns2:City>");
			returnUnlinkedRequestXML.append("<ns2:StateProvince>"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStateProvince()+"</ns2:StateProvince>");
			returnUnlinkedRequestXML.append("<ns2:PostalCode>"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getPostalCode()+"</ns2:PostalCode>");
			returnUnlinkedRequestXML.append("<ns2:CountryCode>"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCountryCode()+"</ns2:CountryCode>");
			returnUnlinkedRequestXML.append("</ns2:Address>");
			returnUnlinkedRequestXML.append("<ns2:BusinessName>"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getBusinessName()+"</ns2:BusinessName>");
			returnUnlinkedRequestXML.append("<ns2:Phone i:nil=\""+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getPhone().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getPhone().getValue() +"</ns2:Phone>");
			returnUnlinkedRequestXML.append("<ns2:Fax i:nil=\""+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getFax().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getFax().getValue() +"</ns2:Fax>");
			returnUnlinkedRequestXML.append("<ns2:Email i:nil=\""+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getEmail().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getEmail().getValue() +"</ns2:Email>");
			returnUnlinkedRequestXML.append("</ns2:BillingData>");
			returnUnlinkedRequestXML.append("<ns2:CustomerId>"+returnUnlinkedTransaction.getTransaction().getCustomerData(). getCustomerId()+"</ns2:CustomerId>");
			returnUnlinkedRequestXML.append("<ns2:CustomerTaxId i:nil=\""+returnUnlinkedTransaction.getTransaction().getCustomerData().getCustomerTaxId().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getCustomerData().getCustomerTaxId().getValue() +"</ns2:CustomerTaxId>");
			returnUnlinkedRequestXML.append("<ns2:ShippingData i:nil=\""+returnUnlinkedTransaction.getTransaction().getCustomerData().getShippingData().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getCustomerData().getShippingData().getValue() +"</ns2:ShippingData>");
			returnUnlinkedRequestXML.append("</ns2:CustomerData>");
			returnUnlinkedRequestXML.append("<ns3:ReportingData xmlns:ns3=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
			returnUnlinkedRequestXML.append("<ns3:Comment>"+returnUnlinkedTransaction.getTransaction().getReportingData().getComment()+"</ns3:Comment>");
			returnUnlinkedRequestXML.append("<ns3:Description>"+returnUnlinkedTransaction.getTransaction().getReportingData().getDescription()+ "</ns3:Description>");
			returnUnlinkedRequestXML.append("<ns3:Reference>"+returnUnlinkedTransaction.getTransaction().getReportingData().getReference()+ "</ns3:Reference>");
			returnUnlinkedRequestXML.append("</ns3:ReportingData>");
			returnUnlinkedRequestXML.append("<ns1:TenderData>");
			returnUnlinkedRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+returnUnlinkedTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().isNillable()+"\">" + returnUnlinkedTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().getValue() +"</ns4:PaymentAccountDataToken>");
			returnUnlinkedRequestXML.append("<ns5:SecurePaymentAccountData xmlns:ns5=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+returnUnlinkedTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().isNillable()+"\">" + returnUnlinkedTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().getValue() + "</ns5:SecurePaymentAccountData>");
			returnUnlinkedRequestXML.append("<ns6:EncryptionKeyId xmlns:ns6=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+returnUnlinkedTransaction.getTransaction().getTenderData().getEncryptionKeyId().isNillable()+"\">"+ returnUnlinkedTransaction.getTransaction().getTenderData().getEncryptionKeyId().getValue() +"</ns6:EncryptionKeyId>");
			returnUnlinkedRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+returnUnlinkedTransaction.getTransaction().getTenderData().getSwipeStatus().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getTenderData().getSwipeStatus().getValue() +"</ns7:SwipeStatus>");
			returnUnlinkedRequestXML.append("<ns1:CardData>");
			returnUnlinkedRequestXML.append("<ns1:CardType>"+returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().getCardType()+"</ns1:CardType>");
			returnUnlinkedRequestXML.append("<ns1:PAN>"+returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().getpAN()+"</ns1:PAN>");
			returnUnlinkedRequestXML.append("<ns1:Expire>"+returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().getExpire()+"</ns1:Expire>");
			returnUnlinkedRequestXML.append("<ns1:Track1Data i:nil=\""+returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().isNillable()+"\">"+ returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().getValue() +"</ns1:Track1Data>");
			returnUnlinkedRequestXML.append("</ns1:CardData>");
			returnUnlinkedRequestXML.append("<ns1:EcommerceSecurityData i:nil=\""+returnUnlinkedTransaction.getTransaction().getTenderData().getEcommerceSecurityData().isNillable()+"\">"+ returnUnlinkedTransaction.getTransaction().getTenderData().getEcommerceSecurityData().getValue() +"</ns1:EcommerceSecurityData>");
			returnUnlinkedRequestXML.append("</ns1:TenderData>");
			returnUnlinkedRequestXML.append("<ns1:TransactionData>");
			returnUnlinkedRequestXML.append("<ns8:Amount xmlns:ns8=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getAmount()+"</ns8:Amount>");
			returnUnlinkedRequestXML.append("<ns9:CurrencyCode xmlns:ns9=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getCurrencyCode()+"</ns9:CurrencyCode>");
			returnUnlinkedRequestXML.append("<ns10:TransactionDateTime xmlns:ns10=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getTransactionDateTime()+"</ns10:TransactionDateTime>");
			returnUnlinkedRequestXML.append("<ns11:CampaignId xmlns:ns11=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+returnUnlinkedTransaction.getTransaction().getTransactionData().getCampaignId().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getCampaignId().getValue() +"</ns11:CampaignId>");
			returnUnlinkedRequestXML.append("<ns12:Reference xmlns:ns12=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getReference()+"</ns12:Reference>");
			returnUnlinkedRequestXML.append("<ns1:AccountType>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getAccountType()+ "</ns1:AccountType>");
			returnUnlinkedRequestXML.append("<ns1:ApprovalCode i:nil=\""+returnUnlinkedTransaction.getTransaction().getTransactionData().getApprovalCode().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getApprovalCode().getValue() +"</ns1:ApprovalCode>");
			returnUnlinkedRequestXML.append("<ns1:CashBackAmount>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getCashBackAmount()+ "</ns1:CashBackAmount>");
			returnUnlinkedRequestXML.append("<ns1:CustomerPresent>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getCustomerPresent()+"</ns1:CustomerPresent>");
			returnUnlinkedRequestXML.append("<ns1:EmployeeId>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getEmployeeId()+"</ns1:EmployeeId>");
			returnUnlinkedRequestXML.append("<ns1:EntryMode>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getEntryMode()+"</ns1:EntryMode>");
			returnUnlinkedRequestXML.append("<ns1:GoodsType>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getGoodsType()+"</ns1:GoodsType>");
			returnUnlinkedRequestXML.append("<ns1:IndustryType>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getIndustryType()+"</ns1:IndustryType>");
			returnUnlinkedRequestXML.append("<ns1:InternetTransactionData i:nil=\""+returnUnlinkedTransaction.getTransaction().getTransactionData().getInternetTransactionData().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getInternetTransactionData().getValue() +"</ns1:InternetTransactionData>");
			returnUnlinkedRequestXML.append("<ns1:InvoiceNumber>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getInvoiceNumber()+"</ns1:InvoiceNumber>");
			returnUnlinkedRequestXML.append("<ns1:OrderNumber>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getOrderNumber()+"</ns1:OrderNumber>");
			returnUnlinkedRequestXML.append("<ns1:IsPartialShipment>"+returnUnlinkedTransaction.getTransaction().getTransactionData().isPartialShipment()+"</ns1:IsPartialShipment>");
			returnUnlinkedRequestXML.append("<ns1:SignatureCaptured>"+returnUnlinkedTransaction.getTransaction().getTransactionData().isSignatureCaptured()+"</ns1:SignatureCaptured>");
			returnUnlinkedRequestXML.append("<ns1:FeeAmount>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getFeeAmount()+"</ns1:FeeAmount>");
			returnUnlinkedRequestXML.append("<ns1:TerminalId i:nil=\""+returnUnlinkedTransaction.getTransaction().getTransactionData().getTerminalId().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getTerminalId().getValue() +"</ns1:TerminalId>");
			returnUnlinkedRequestXML.append("<ns1:LaneId i:nil=\""+returnUnlinkedTransaction.getTransaction().getTransactionData().getLaneId().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getLaneId().getValue() +"</ns1:LaneId>");
			returnUnlinkedRequestXML.append("<ns1:TipAmount>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getTipAmount()+"</ns1:TipAmount>");
			returnUnlinkedRequestXML.append("<ns1:BatchAssignment i:nil=\""+returnUnlinkedTransaction.getTransaction().getTransactionData().getBatchAssignment().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getBatchAssignment().getValue() +"</ns1:BatchAssignment>");
			returnUnlinkedRequestXML.append("<ns1:PartialApprovalCapable>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getPartialApprovalCapable()+"</ns1:PartialApprovalCapable>");
			returnUnlinkedRequestXML.append("<ns1:ScoreThreshold i:nil=\""+returnUnlinkedTransaction.getTransaction().getTransactionData().getScoreThreshold().isNillable()+"\">"+ returnUnlinkedTransaction.getTransaction().getTransactionData().getScoreThreshold().getValue() +"</ns1:ScoreThreshold>");
			returnUnlinkedRequestXML.append("<ns1:IsQuasiCash>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getIsQuasiCash()+"</ns1:IsQuasiCash>");
			returnUnlinkedRequestXML.append("</ns1:TransactionData></Transaction></ReturnTransaction>");
			AppLogger.logDebug(this.getClass(), "generateReturnUnlinkedRequestXMLInput", "XML input :: "+ returnUnlinkedRequestXML.toString());
			AppLogger.logDebug(this.getClass(), "generateReturnUnlinkedRequestXMLInput", "Exiting...");
			
			return returnUnlinkedRequestXML.toString();

		}
		catch(Exception ex)
		{
			AppLogger.logError(this.getClass(), "generateReturnUnlinkedRequestXMLInput", ex);
			throw new VelocityGenericException("Exception occured into generating ReturnUnlinked request XML:: "+ex.getMessage(), ex);
		}	

	}
	
	
	/* (non-Javadoc)
	 * @see com.velocity.transaction.processor.BaseProcessor#invokeReturnUnlinkedRequest(com.velocity.models.request.returnUnLinked.ReturnTransaction)
	 */
	public VelocityResponse invokeReturnUnlinkedRequest(com.velocity.models.request.returnUnLinked.ReturnTransaction returnUnlinkedTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException
	{
		AppLogger.logDebug(getClass(), "invokeReturnUnlinkedRequest", "Entering...");
			
			if(returnUnlinkedTransaction == null)
			{
				throw new VelocityIllegalArgument("ReturnUnlinked param can not be null.");
			}
			
			/* Validating the session token. */
			if(encSessionToken == null || encSessionToken.isEmpty())
			{
				/* Setting Velocity session token. */
				sessionToken = invokeSignOn(identityToken);
				/* Encrypting the Velocity server session token. */
				encSessionToken = new String(Base64.encode((sessionToken + ":").getBytes()));
			}
			
			/* Generating ReturnUnlinked XML input request. */
			String returnUnlinkedTxnRequestXML =  generateReturnUnlinkedRequestXMLInput(returnUnlinkedTransaction);
			AppLogger.logDebug(getClass(), "invokeReturnUnlinkedRequest", "ReturnUnlinked XML input == "+returnUnlinkedTxnRequestXML);
			/*Invoking URL for the XML input request*/
			String invokeURL = serverURL + "/Txn/"+ workFlowId;
			AppLogger.logDebug(getClass(), "invokeReturnUnlinkedRequest", "ReturnUnlinkedRequest request invokeURL == "+invokeURL);
			VelocityResponse velocityResponse = generateVelocityResponse(VelocityConstants.POST_METHOD, invokeURL, "Basic "+encSessionToken, "application/xml", returnUnlinkedTxnRequestXML);
			txnRequestXML = returnUnlinkedTxnRequestXML ;
			AppLogger.logDebug(getClass(), "invokeReturnUnlinkedRequest", "ReturnUnlinked response message == "+velocityResponse.getMessage());
			AppLogger.logDebug(getClass(), "ReturnUnlinkedRequest", "Exiting...");
			return velocityResponse;
		
	}
	
	public String getSessionToken() {
		return sessionToken;
	}

	public String getTxnRequestXML() {
		return txnRequestXML;
	}

	public void setTxnRequestXML(String txnRequestXML) {
		this.txnRequestXML = txnRequestXML;
	}

	public String getTxnResponseXML() {
		return txnResponseXML;
	}

	public void setTxnResponseXML(String txnResponseXML) {
		this.txnResponseXML = txnResponseXML;
	}

}
