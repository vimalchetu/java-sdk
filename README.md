# java-sdk
This is the velocity java SDK implementation. <br/>
It has the implementation of all the transaction payment solution methods for a merchant application who wants to access the Velocity payment gateway. <br/><br/>
At the centre of this SDK, there is the class <b>com.velocity.transaction.processor.velocity.VelocityProcessor</b>. <br/>
The signature of the constructor of this class is as below: <br/>
<b>public VelocityProcessor(String sessionToken, String identityToken, String appProfileId, String merchantProfileId, String workFlowId, boolean isTestAccount) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException</b> <br/>

@parameter  <b>sessionToken </b> - initializes the value for session token.  <br/>
@parameter  <b>identityToken </b> - initializes the value for identity token.  <br/>
@parameter  <b>appProfileId </b> - initializes the value for application profile Id.  <br/>
@parameter  <b>merchantProfileId </b> - initializes the value for merchant profile Id.  <br/>
@parameter  <b>workFlowId </b> - initializes the value for workflow Id.  <br/>
@parameter  <b>isTestAccount </b> - works as a flag for the TestAccount.  <br/>

<b>Sample Code:</b>

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



 <h2>1. VelocityProcessor </h2><br/>
This class provides the implementation of the following methods: <br/>
     1. signOn  <br/>
     2. verify   <br/>
     3. authorize  <br/>
     4. authorizeAndCapture     <br/>
     5. capture  <br/>
     6. undo     <br/>
     7. adjust   <br/>
     8. returnById    <br/>
     9. returnUnlinked     
     10. queryTransactionsDetail <br/>
     11. captureAll <br/>
     
<h2>1.1 signOn(...) </h2><br/>

   The method accepts the identity token as argument and provides the session token.  <br/>

<b>public String invokeSignOn(String identityToken) throws VelocityIllegalArgument, VelocityRestInvokeException</b>

@parameter <b>identityToken </b> - holds the value for encrypted identity token.

@returnType  <b>String</b> - Session token generated on the velocity server. <br/>

<b>Sample Code:</b>

     /**
	 * This is the Test case method for Velocity processor signOn method.
	 */
	@Test
	public void testInvokeSignOn()
	{
		try {
			String sessionToken = velocityProcessor.signOn(identityToken);
			assert(sessionToken != null);
			AppLogger.logDebug(getClass(), "testInvokeVerifyRequest", "sessionToken >>>>>>>>>> "+sessionToken);
		}
		catch(Exception ex)
		{
			AppLogger.logError(getClass(),"testInvokeSignOn", ex);
		}
	}


<h2>1.2 verify(...) </h2><br/>

   The method is responsible for the invocation of verify operation on the Velocity REST server.<br/>

<b> public VelocityResponse verify(AuthorizeTransaction authorizeTransaction) throws VelocityGenericException, VelocityIllegalArgument, VelocityNotFound, VelocityRestInvokeException</b><br/><br/>
@parameter <b>authorizeTrsansaction </b> - holds the values for the verify request AuthorizeTransaction <br/>

@returnType  <b>VelocityResponse</b>  <br/>  

<b>Sample Code:</b>

    /**
	 * This method test's the verify request through the Velocity REST server.
	 * The AuthorizeTransaction instance is responsible for invoking the verifyRequest method 
       and reads the the data for XML generation through VelocityProcessor class.
	 */

	@Test
	public void testInvokeVerifyRequest()
	{
		try {
			AuthorizeTransaction objAuthorizeTransaction = getVerifyRequestAuthorizeTransactionInstance();

			VelocityResponse objVelocityResponse = velocityProcessor.verify(objAuthorizeTransaction);
                 
            /* Getting the BankcardTransactionResponse from the Velocity response. */
            BankcardTransactionResponse objBankcardTransactionResponse = objVelocityResponse.getBankcardTransactionResponse();
            if(objBankcardTransactionResponse != null)
			{
				AppLogger.logDebug(getClass(), "testInvokeVerifyRequest", "Status >>>>>>>>>> "+objBankcardTransactionResponse.getStatus());
			}
			else if(objBankcardTransactionResponse.getErrorResponse() != null)
			{
				AppLogger.logDebug(getClass(), "testInvokeVerifyRequest", "Message >>>>>>>>>> "+objBankcardTransactionResponse.getErrorResponse().getReason() + " " + objBankcardTransactionResponse.getErrorResponse().getValidationErrors().getValidationErrorList().get(0).getRuleMessage());
			}
		} catch (Exception e) {
			
			AppLogger.logError(this.getClass()," testInvokeVerifyRequest", e);
		}
	}


     /** 
	 * This method sets the value for the VerifyRequest Instance 
	 * @return- Returns the AuthoizeTransaction instance.
	 */
   
    AuthorizeTransaction getVerifyRequestAuthorizeTransactionInstance()
    {
   
    AuthorizeTransaction authorizeTransaction = new AuthorizeTransaction();

    /* Setting the values for Verify request XML */
          
	authorizeTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
	
	authorizeTransaction.getTransaction().getTenderData().getCardData().setCardType("Visa");
		
	authorizeTransaction.getTransaction().getTenderData().getCardData().setCardholderName("ashish");
		
	authorizeTransaction.getTransaction().getTenderData().getCardData().setPanNumber("4012888812348882");
	
	authorizeTransaction.getTransaction().getTenderData().getCardData().setExpiryDate("0113");
	
	authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack2Data().setNillable(true);

    authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCardholderName().setNillable(true);

    authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setStreet("4 corporate sq");
		
	authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setCity("Denver");
		
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
    
 <b>Verify with Swipe Data :</b>

    private AuthorizeTransaction getVerifyRequestAuthorizeTransactionInstance()
    {
     AuthorizeTransaction authorizeTransaction = new AuthorizeTransaction();
        
     authorizeTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
        
     authorizeTransaction.getTransaction().getTenderData().getCardData().setCardType("Visa");
     
     authorizeTransaction.getTransaction().getTenderData().getCardData().setTrack1Data1("%B4012000033330026^VIMAL/KUMAR^0904101100001100000000123456780?");
   
     authorizeTransaction.getTransaction().getTenderData().getCardData().setTrack2Data2("4012000033330026=09041011000012345678");
    
     authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCardholderName().setNillable(true);
        
     authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setStreet("4 corporate sq");
             
     authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setCity("Denver");
        
     authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setStateProvince("CO");
        
     authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setPostalCode("80202");
        
     authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setPhone("7849477899");
        
     authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getEmail().setNillable(true);
        
     authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().setCvDataProvided("Provided");
        
     authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getCvData().setValue("123");
        
     authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getKeySerialNumber().setNillable(true);
        
     authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getPin().setNillable(true);
        
     authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().setNillable(true);
       
     authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);
        
     authorizeTransaction.getTransaction().getTransactionData().setAmount("10000.00");
        
     authorizeTransaction.getTransaction().getTransactionData().setCurrencyCode("USD");
        
     authorizeTransaction.getTransaction().getTransactionData().setTransactiondateTime("");
        
     authorizeTransaction.getTransaction().getTransactionData().setAccountType("NotSet");
        
     authorizeTransaction.getTransaction().getTransactionData().setCustomerPresent("Present");
        
     authorizeTransaction.getTransaction().getTransactionData().setEmployeeId("11");
        
     authorizeTransaction.getTransaction().getTransactionData().setEntryMode("TrackDataFromMSR");
        
     authorizeTransaction.getTransaction().getTransactionData().setIndustryType("Restaurant");
        
     authorizeTransaction.getTransaction().getTransactionData().setInvoiceNumber("802");
        
     authorizeTransaction.getTransaction().getTransactionData().setOrderNumber("629203");
        
     authorizeTransaction.getTransaction().getTransactionData().setTipAmount("2.00");
        
     return authorizeTransaction;
    }

<h2>1.3 authorize(...) </h2><br/>

   The method is responsible for the invocation of authorize operation on the Velocity REST server.<br/>

<b>public VelocityResponse authorize(AuthorizeTransaction authorizeTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityRestInvokeException, VelocityNotFound</b><br/>

@parameter <b>authorizeTrsansaction </b> - holds the values for the authorize request AuthorizeTransaction <br/>

@returnType  <b>VelocityResponse</b>  <br/>  

<b>Sample Code:</b>

    /**
	 * This method test's the Authorize request through the Velocity REST server.
	 * The VelocityResponse object reads the data for AuthorizeTransaction and prepares 
       for the response.
	 */
	  @Test
	 public void testInvokeAuthorizeRequest()
	 {
		try {
			com.velocity.models.request.authorize.AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance();
            AuthorizeTransaction objAuthorizeTransaction = getP2PEAuthorizeRequestAuthorizeTransactionInstance();
			VelocityResponse objVelocityResponse = velocityProcessor.authorize(objAuthorizeTransaction);
            /* Getting the BankcardTransactionResponse from the Velocity response. */
             BankcardTransactionResponse objBankcardTransactionResponse = objVelocityResponse.getBankcardTransactionResponse();
			if(objBankcardTransactionResponse != null)
			{
				AppLogger.logDebug(getClass(), "testInvokeAuthorizeRequest", "Status >>>>>>>>>> "+objBankcardTransactionResponse.getStatus());
			}
			else if(objBankcardTransactionResponse.getErrorResponse() != null)
			{
				AppLogger.logDebug(getClass(), "testInvokeAuthorizeRequest", "Error >>>>>>>>>> "+objBankcardTransactionResponse.getErrorResponse().getReason());
				if(objBankcardTransactionResponse.getErrorResponse().getValidationErrors() != null)
				{
					AppLogger.logDebug(getClass(), "testInvokeAuthorizeRequest", "Error >>>>>>>>>> "+objBankcardTransactionResponse.getErrorResponse().getValidationErrors().getValidationErrorList().get(0).getRuleKey());
				}
			}
			
		} catch(Exception ex)
		{
			AppLogger.logError(this.getClass(),"testInvokeAuthorizeRequest", ex);
		}
 	}

     /**
	 * This method prepares the AuthorizeTransaction instance for AuthorizeTransaction
	 * The method sets the values for Authorize XML elements.
	 * @return- Returns the authoizeTransaction instance.
	 */
	
        com.velocity.models.request.authorize.AuthorizeTransaction getAuthorizeRequestAuthorizeTransactionInstance()
	    {
		com.velocity.models.request.authorize.AuthorizeTransaction authorizeTransaction = new com.velocity.models.request.authorize.AuthorizeTransaction();

        /* Setting the values for Authorize request XML */
     
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
        
        authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack2Data().setNillable(true);
     
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
	   
<b>For P2PE Authorize:</b>	  
	  
	   private AuthorizeTransaction getP2PEAuthorizeRequestAuthorizeTransactionInstance()
    {
        AuthorizeTransaction authorizeTransaction = new AuthorizeTransaction();
        // Setting the values for Authorize XML
        authorizeTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
        
        authorizeTransaction.getTransaction().getRelayResponseUrl().setNillable(true);
       
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().getName().setNillable(true);
       
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStreet1("1400 16th St");
      
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
        
        authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setNillable(true);
        
        authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setValue("576F2E197D5804F2B6201F         B2578DCD1DDDC7BAE692FE48E9C368E678914233561FB953DF47E29F88");
        
        authorizeTransaction.getTransaction().getTenderData().getEncryptionKeyId().setValue("9010010B257DC7000084");
        
        authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().setValue("61403000");
        
        authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().setValue(
        "10CB07E3D25EF91A5DAD25629D1E4A673F016A7B6E6C760F6AAEC985E77B02E796981928AEEE94618C34E2801F4A76E32BCEF984144D51F2");
        
        authorizeTransaction.getTransaction().getTransactionData().setAmount("10.00");
        
        authorizeTransaction.getTransaction().getTransactionData().setCurrencyCode("USD");
        
        authorizeTransaction.getTransaction().getTransactionData().setTransactionDateTime("2012-12-11T10:28:11");
        
        authorizeTransaction.getTransaction().getTransactionData().getCampaignId().setNillable(true);
        
        authorizeTransaction.getTransaction().getTransactionData().setReference("xyt");
        
        authorizeTransaction.getTransaction().getTransactionData().setEmployeeId("11");
        
        authorizeTransaction.getTransaction().getTransactionData().setEntryMode("TrackDataFromMSR");
        
        authorizeTransaction.getTransaction().getTransactionData().setGoodsType("NotSet");
        
        authorizeTransaction.getTransaction().getTransactionData().setIndustryType("Retail");
        
        authorizeTransaction.getTransaction().getTransactionData().setInvoiceNumber("");
        
        return authorizeTransaction;
       }
       
 <b>Authorize with Swipe Data :</b>


    private AuthorizeTransaction getAuthorizeRequestAuthorizeTransactionInstance()
    {
      AuthorizeTransaction authorizeTransaction = new AuthorizeTransaction();
      
      // Setting the values for Authorize XML
    
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
    
    authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setNillable(true); 
    
    authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setNillable(true);
    
    authorizeTransaction.getTransaction().getTenderData().getEncryptionKeyId().setNillable(true);
    
    authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().setNillable(true);
    
    authorizeTransaction.getTransaction().getTenderData().getCardData().setCardType("MasterCard");
    
    authorizeTransaction.getTransaction().getTenderData().getCardData().setTrack1Data1("%B4012000033330026^VIMAL/KUMAR^0904101100001100000000123456780?"); 
    
    authorizeTransaction.getTransaction().getTenderData().getCardData().setTrack2Data2("4012000033330026=09041011000012345678"); 
    
    authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);
    
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
    
    authorizeTransaction.getTransaction().getTransactionData().setEntryMode("TrackDataFromMSR");
    
    authorizeTransaction.getTransaction().getTransactionData().setGoodsType("NotSet");
    
    authorizeTransaction.getTransaction().getTransactionData().setIndustryType("Restaurant");
    
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
        
     return authorizeTransaction;
    }
            

<h2>1.4 authorizeAndCapture(...) </h2><br/>

   The method is responsible for the invocation of authorizeAndCapture operation on the Velocity REST server.<br/>

 <b>                                                                                                                       public VelocityResponse authorizeAndCapture(AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException</b><br/>

@parameter <b>authorizeAndCaptureTransaction </b> - holds the values for the authorizeAndCapture request 
AuthorizeAndCaptureTransaction <br/>

@returnType  <b>VelocityResponse</b>  <br/>  

 <b>Sample Code:</b>
  
     /**
	 * This method test's the AuthorizeAndCapture request through the Velocity REST server.
	 * The VelocityResponse object reads the data for AuthorizeAndCaptureTransaction 
       and prepares for the response.
	 */
	@Test
	public void testInvokeAuthorizeAndCaptureRequest()
	{
		try {
			AuthorizeAndCaptureTransaction objAuthorizeAndCaptureTransaction = getAuthorizeAndCaptureTransactionInstance();
            AuthorizeAndCaptureTransaction objAuthorizeAndCaptureTransaction = getP2PEAuthorizeAndCaptureRequestAuthorizeTransactionInstance();
			BankcardTransactionResponse objBankcardTransactionResponse =  velocityProcessor.authorizeAndCapture(objAuthorizeAndCaptureTransaction);
			AppLogger.logDebug(getClass(), "invokeAuthorizeAndCaptureRequest", "Status >>>>>>>>>> "+objBankcardTransactionResponse.getStatus());
		} catch(Exception ex)
		{
			AppLogger.logError(this.getClass(),"testInvokeAuthorizeAndCaptureRequest", ex);
		}
	}


     /**
	 * This method prepares the AuthorizeAndCaptureTransaction instance for AuthorizeAndCaptureTransaction
	 * The method sets the values for Authorize XML elements.
	 * @return of the type authorizeAndCaptureTransaction instance
	 */
    
     AuthorizeAndCaptureTransaction getAuthorizeAndCaptureTransactionInstance()
	 {
    AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction = new AuthorizeAndCaptureTransaction();

    /* Setting the values for AuthorizeAndCapture request XML */
   
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
		
    authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().setNillable(true);
    
    authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().getTrack2Data().setNillable(true);
		
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
     
 <b>For P2PE AuthorizeAndCapture :</b>
     
     
       private AuthorizeAndCaptureTransaction getP2PEAuthorizeAndCaptureRequestAuthorizeTransactionInstance()
       {
         AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction = new AuthorizeAndCaptureTransaction();
       
        // Setting the values for AuthorizeAndCapture XML
        authorizeAndCaptureTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
       
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getName().setNillable(true);
        
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStreet1("1400 16th St.");
        
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
       
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setNillable(true);
        
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setValue("2540E479632A5FBACD3BDB8A3798104BC5C06105421D5E6369C7F78CBEA85647434D966CF8B4DAD1");;
        
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getEncryptionKeyId().setValue("9010010B257DC7000083");
        
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getSwipeStatus().setValue("61403000");
        
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().setValue("10CB07E3D25EF91A5DAD25629D1E4A673F016A7B6E6C760F6AAEC985E77B02E796981928AEEE94618C34E2801F4A76E32BCEF984144D51F2");
        
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setAmount("100.00");
        
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setCurrencyCode("USD");
        
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setTransactionDateTime("2013-04-03T13:50:16");
        
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().getCampaignId().setNillable(true);
        
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setReference("xyt");
        
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setAccountType("NotSet");
        
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().getApprovalCode().setNillable(true);
        
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setCashBackAmount("0.0");
        
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setCustomerPresent("Present");
        
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setEmployeeId("11");
        
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setEntryMode("TrackDataFromMSR");
       
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setGoodsType("NotSet");
       
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setIndustryType("Restaurant");
        
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().getInternetTransactionData().setNillable(true);
        
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setInvoiceNumber("802");
        
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
   
<b>AuthorizeAndCapture with Swipe Data :</b>

    private AuthorizeAndCaptureTransaction getAuthorizeAndCaptureTransactionInstance() {
    
    AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction = new     AuthorizeAndCaptureTransaction();
    // Setting the values for AuthorizeAndCapture XML
    
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
    
    authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().setTrack2Data2("4012000033330026=09041011000012345678");
    
    authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().setTrack1Data1("%B4012000033330026^VIMAL/KUMAR ^0904101100001100000000123456780?");
    
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
    
    authorizeAndCaptureTransaction.getTransaction().getTransactionData().setEntryMode("TrackDataFromMSR");
    
    authorizeAndCaptureTransaction.getTransaction().getTransactionData().setGoodsType("NotSet");
    
    authorizeAndCaptureTransaction.getTransaction().getTransactionData().setIndustryType("Restaurant");
    
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
	
<h2>1.5 capture(...) </h2><br/>

   The method is responsible for the invocation of capture operation on the Velocity REST server.<br/>

<b>                                                                                                                      public VelocityResponse capture(ChangeTransaction captureTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException</b><br/>

@parameter <b>captureTransaction </b> - holds the values for the capture request ChangeTransaction <br/>

@returnType  <b>VelocityResponse</b>  <br/> 

<b>Sample Code:</b>          
 
	/**
	 * This method tests the Capture request through the Velocity Velocity REST server.
	 * The method performs the Authorize operations before the invoking the 
       capture transaction in order to obtain the Transaction Id.
	 */
	@Test
	public void testinvokeCaptureRequest()
	{
		try {

			com.velocity.models.request.authorize.AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance();
			VelocityResponse objVelocityResponse = velocityProcessor.authorize(objAuthorizeTransaction);

			ChangeTransaction objChangeTransaction = getCaptureTransactionInstance();

		    objChangeTransaction.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardTransactionResponse().getTransactionId());

			VelocityResponse captureVelocityResponse = velocityProcessor.capture(objChangeTransaction);
			
	            /* Getting the BankcardCaptureResponse from the Velocity response. */
                  BankcardCaptureResponse objbankcardCaptureResponse = captureVelocityResponse.getBankcardCaptureResponse();
			if(objbankcardCaptureResponse != null)
			{
				AppLogger.logDebug(this.getClass(), "invokeCaptureRequest", "Status >>>>>>>>>> "+ objbankcardCaptureResponse.getCaptureState());
			}
			else if(objbankcardCaptureResponse.getErrorResponse() != null)
			{
				AppLogger.logDebug(this.getClass(), "invokeCaptureRequest", "Status >>>>>>>>>> "+ objbankcardCaptureResponse.getErrorResponse().getErrorId());
			}
		} catch(Exception ex)
		{

			AppLogger.logError(this.getClass(),"testinvokeCaptureRequest", ex);

		}
	}          
        
     /**
	 * This method prepares the CaptureTransaction instance for CaptureTransaction
	 * The method sets the values for Capture XML elements.
	 * @return- Returns the captureTransaction instance.
	 */
       
     com.velocity.models.request.capture.ChangeTransaction getCaptureTransactionInstance()
      {

    com.velocity.models.request.capture.ChangeTransaction captureTransaction = new com.velocity.models.request.capture.ChangeTransaction();

     /* Setting the values for Capture request XML */
       
     captureTransaction.getDifferenceData().setType(VelocityEnums.BankcardCapture);
		
     captureTransaction.getDifferenceData().setTransactionId("B877F283052443119721C5699CD5C408");
		
     captureTransaction.getDifferenceData().setAmount("100.00");
		
     captureTransaction.getDifferenceData().setTipAmount("3.00");

     return captureTransaction;
	 }    

<h2>1.6 undo(...) </h2><br/>

   The method is responsible for the invocation of undo operation on the Velocity REST server.<br/>

<b>                                                                                                                      public VelocityResponse undo(Undo undoTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException</b><br/>

@parameter <b>undoTransaction </b> - holds the values for the undo request Undo <br/>

@returnType  <b>VelocityResponse</b>  <br/> 

<b>Sample Code:</b>  
   
   
   
    /**
     * This method tests the Undo request through the Velocity REST server.
     * The method performs the Authorize operations before the invoking the Undo 
       transaction and obtains the transactionId.
	 */
	@Test
	public void testinvokeUndoRequest()  
	{
		try {

			com.velocity.models.request.authorize.AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance();
			VelocityResponse objVelocityResponse = velocityProcessor.authorize(objAuthorizeTransaction);

			com.velocity.models.request.undo.Undo objUndo  = getUndoTransactionInstance();
			Undo objUndo1 = getUndoTransactionInstance();

			objUndo1.setTransactionId(objVelocityResponse.getBankcardTransactionResponse().getTransactionId());

			VelocityResponse objVelocityResponse2 = velocityProcessor.undo(objUndo1);
			AppLogger.logDebug(getClass(), "testinvokeUndoRequest", "Status >>>>>>>>>> "+ objVelocityResponse2.getBankcardTransactionResponse().getStatus());
		} catch(Exception ex)
		{

			AppLogger.logError(this.getClass(),"testinvokeUndoRequest", ex);

		}
	}

    /**This method sets value for the Undo XML.
	 * @return- Returns the undoTransaction instance.
	 */
	  
        Undo getUndoTransactionInstance()
        {
       
	   Undo undoTransaction = new Undo();
           
        /* Setting the values for Undo request XML */

	   undoTransaction.setType(VelocityEnums.Undo);
		
	   undoTransaction.getBatchIds().setNillable(true);
	
	   undoTransaction.getDifferenceData().setNillable(true);
	
	   undoTransaction.setTransactionId("8C17F72F1BF649AA88EE1366AF699D40");

           return undoTransaction;
	    }
	
	
	

<h2>1.7 adjust(...) </h2><br/>

   The method is responsible for the invocation of adjust operation on the Velocity REST server.<br/>

<b>                                                                                                                      public VelocityResponse adjust(Adjust adjustTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException</b><br/>

@parameter <b>adjustTransaction </b> - holds the values for the adjust request Adjust <br/>

@returnType  <b>VelocityResponse</b>  <br/> 

 <b>Sample Code:</b>     

 	 /**
	 * This method tests the Adjust request through the Velocity REST server.
	 * The method performs the Authorize operations before the invoking the Adjust 
       transaction and obtains the transactionId.
	 */

	@Test
	public void testinvokeAdjustRequest()  
	{
		try {

			com.velocity.models.request.authorize.AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance();
			VelocityResponse objVelocityResponse = velocityProcessor.authorize(objAuthorizeTransaction);
         /* Getting the BankcardTransactionResponse from the Velocity response. */
          BankcardTransactionResponse objBankcardTransactionResponse = objVelocityResponse.getBankcardTransactionResponse();
			if(objBankcardTransactionResponse != null)
			{
				AppLogger.logDebug(getClass(), "testinvokeAdjustRequest", "Authorize Status >>>>>>>>>> "+ objBankcardTransactionResponse.getStatus());

				ChangeTransaction objChangeTransaction = getCaptureTransactionInstance();

				objChangeTransaction.getDifferenceData().setTransactionId(objBankcardTransactionResponse.getTransactionId());

				objBankcardTransactionResponse = velocityProcessor.capture(objChangeTransaction);

				if(objBankcardTransactionResponse != null)
				{
					AppLogger.logDebug(getClass(), "invokeAdjustRequest", "Capture Status >>>>>>>>>> "+ objBankcardTransactionResponse.getStatus());

					AppLogger.logDebug(getClass(), "invokeAdjustRequest", "Capture Transaction Id >>>>>>>>>> "+ objBankcardTransactionResponse.getTransactionId());

					com.velocity.models.request.adjust.Adjust objAdjust  = getAdjustTransactionInstance();

					objAdjust.getDifferenceData().setTransactionId(objBankcardTransactionResponse.getTransactionId());
					objBankcardTransactionResponse = velocityProcessor.adjust(objAdjust);
					AppLogger.logDebug(getClass(), "invokeAdjustRequest", "Adjust Status >>>>>>>>>> "+ objBankcardTransactionResponse.getStatus());

				}
			}
		} catch(Exception ex)
		{

			AppLogger.logError(this.getClass(),"testinvokeUndoRequest", ex);

		}
	}

    /**
	 * This method sets the values for the Adjust XML
	 * @return- Returns the Adjust instance.
	 */
	
       Adjust getAdjustTransactionInstance()
       {
      Adjust adjustTransaction = new Adjust();
	
	 /* Setting the values for Adjust request XML */

	adjustTransaction.setType(VelocityEnums.Adjust);
	
	adjustTransaction.getBatchIds().setNillable(true);
		
	/* Setting the amount for adjust */	
	
	adjustTransaction.getDifferenceData().setAmount("3.00");
		
	adjustTransaction.getDifferenceData().setTransactionId("8A416AE9BB9840199BD56BA011F80AC2");

	return adjustTransaction;
	}


<h2>1.8 returnById(...) </h2><br/>

   The method is responsible for the invocation of returnById operation on the Velocity REST server.<br/>

<b>                                                                                                                      public VelocityResponse invokeReturnByIdRequest(ReturnById returnByIdTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException</b><br/>

@parameter <b>returnByIdTransaction </b> - holds the values for the returnById request ReturnById <br/>

@returnType  <b>VelocityResponse</b>  <br/> 

<b>Sample Code:</b>

   
    /**
	 * This method tests the ReturnById request through the Velocity REST server.
	 */
	@Test
	public void testinvokeReturnByIdRequest()  
	{
		try {

			com.velocity.models.request.authorize.AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance();
			VelocityResponse objVelocityResponse = velocityProcessor.authorize(objAuthorizeTransaction);
		  /* Getting the BankcardTransactionResponse from the Velocity response. */
          BankcardTransactionResponse objBankcardTransactionResponse = objVelocityResponse.getBankcardTransactionResponse();

			if(objBankcardTransactionResponse != null)
			{
				AppLogger.logDebug(getClass(), "testinvokeReturnByIdRequest", "Authorize Status >>>>>>>>>> "+ objBankcardTransactionResponse.getStatus());

				ChangeTransaction objChangeTransaction = getCaptureTransactionInstance();

				objChangeTransaction.getDifferenceData().setTransactionId(objBankcardTransactionResponse.getTransactionId());

				objBankcardTransactionResponse = velocityProcessor.capture(objChangeTransaction);

				if(objBankcardTransactionResponse != null)
				{
					AppLogger.logDebug(getClass(), "invokeAdjustRequest", "Capture Status >>>>>>>>>> "+ objBankcardTransactionResponse.getStatus());
					com.velocity.models.request.returnById.ReturnById objReturnById  = getReturnByIdTransactionInstance();

					objReturnById.getDifferenceData().setTransactionId(objBankcardTransactionResponse.getTransactionId());
					objBankcardTransactionResponse = velocityProcessor.returnById(objReturnById);
					AppLogger.logDebug(getClass(), "invokeReturnByIdRequest", "ReturnById Status >>>>>>>>>> "+ objBankcardTransactionResponse.getStatus());

				}
			}
		} catch(Exception ex)
		{

			AppLogger.logError(this.getClass(),"testinvokeReturnByIdRequest", ex);

		}
	}
	
       
     /**
	 * This method sets the values for the ReturnById XML.
	 * @return- Returns the ReturnById instance.
	 */
	  
         ReturnById getReturnByIdTransactionInstance()
         {
	     ReturnById returnByIdTransaction = new ReturnById();

         /* Setting the values for ReturnById request XML */
	  
	     returnByIdTransaction.setType(VelocityEnums.ReturnById);
	   
	     returnByIdTransaction.getBatchIds().setNillable(true);
	   
	     returnByIdTransaction.getDifferenceData().setTransactionId("EACD2B6739724FD9A322B9BEE396CB14");
	   
	      /* Setting the amount to perform returnByIdTransaction */
	   
	     returnByIdTransaction.getDifferenceData().setAmount("50.00");

         return returnByIdTransaction;
	  }


<h2>1.9 returnUnlinked(...) </h2><br/>

   The method is responsible for the invocation of returnUnLinked operation on the Velocity REST server.<br/>

<b>                                                                                                                      public VelocityResponse returnUnlinked(ReturnTransaction returnUnlinkedTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException</b><br/>

@parameter <b>returnUnlinkedTransaction </b> - holds the values for the returnUnlinked request ReturnTransaction<br/>

@returnType  <b>VelocityResponse</b>  <br/> 

 <b>Sample Code:</b>
   
   
    /**
	 * This method tests the ReturnUnlinked request through the Velocity REST server.
	 */
    @Test
	public void testinvokeReturnUnlinkedRequest()
	{
		try {
			ReturnTransaction objReturnTransaction = getReturnTransactionInstance();
            ReturnTransaction objReturnTransaction = getP2PEReturnTransactionInstance();
			VelocityResponse objVelocityResponse = velocityProcessor.returnUnlinked(objReturnTransaction );
			AppLogger.logDebug(getClass(), "invokeReturnUnlinkedRequest", "Status >>>>>>>>>> "+objVelocityResponse.getBankcardTransactionResponse().getStatus());
		} catch(Exception ex)
		{
			AppLogger.logError(this.getClass(),"testinvokeReturnUnlinkedRequest", ex);
		}
	}
    

    /**
	 * This method sets the values for the ReturnUnlinked XML.
	 * @return - Returns the type ReturnTransaction instance
	 */
	
     ReturnTransaction getReturnTransactionInstance()
	{
	ReturnTransaction returnUnlinkedTransaction = new ReturnTransaction();

	/*Setting the values for ReturnUnlinked request XML*/

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
	
	returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().setNillable(true);
	
	returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().getTrack2Data().setNillable(true);
	
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
	

<b>For P2PE ReturnUnlinked :</b>	

    private ReturnTransaction getP2PEReturnTransactionInstance()
    {
        ReturnTransaction returnUnlinkedTransaction = new ReturnTransaction();
      
        /* Setting the values for ReturnUnlinked XML */
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
        
        returnUnlinkedTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setValue("2540E479632A5FBACD3BDB8A3798104BC5C06105421D5E6369C7F78CBEA85647434D966CF8B4DAD1");
       
        returnUnlinkedTransaction.getTransaction().getTenderData().getEncryptionKeyId().setValue("9010010B257DC7000083");
        
        returnUnlinkedTransaction.getTransaction().getTenderData().getSwipeStatus().setNillable(true);
        
        returnUnlinkedTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().setValue("10CB07E3D25EF91A5DAD25629D1E4A673F016A7B6E6C760F6AAEC985E77B02E796981928AEEE94618C34E2801F4A76E32BCEF984144D51F2");
        
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
        
        returnUnlinkedTransaction.getTransaction().getTransactionData().setQuasiCash(false);
       
        return returnUnlinkedTransaction;
     }	
     
 <b>ReturnUnlinked with Swipe Data :</b>

    private ReturnTransaction getReturnTransactionInstance()
    {
        ReturnTransaction returnUnlinkedTransaction = new ReturnTransaction();
       
        /* Setting the values for ReturnUnlinked XML */
        
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
        
        returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setCardType("Visa");
        
        returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setTrack1Data1("%B4012000033330026^VIMAL/KUMAR ^0904101100001100000000123456780?");
        
        returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setTrack2Data("4012000033330026=09041011000012345678");
        
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
        
        returnUnlinkedTransaction.getTransaction().getTransactionData().setEntryMode("TrackDataFromMSR");
        
        returnUnlinkedTransaction.getTransaction().getTransactionData().setGoodsType("NotSet");
        
        returnUnlinkedTransaction.getTransaction().getTransactionData().setIndustryType("Restaurant");
        
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
        
        returnUnlinkedTransaction.getTransaction().getTransactionData().setQuasiCash(false);
        
        return returnUnlinkedTransaction;
    }
     
     
<h2>1.10 queryTransactionsDetail(...) </h2><br/>

The method queries the specified transactions and returns both summary details
      and full transaction details.<br/>

<b> public VelocityResponse queryTransactionsDetail(QueryTransactionsDetail queryTransactionsDetail) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException</b><br/>

@parameter <b>queryTransactionsDetail</b> - holds the value for queryTransactionsDetail request QueryTransactionsDetail transaction.

@returnType  <b>VelocityResponse</b>  <br/> 

<b>Sample Code:</b>

    /**
     * This method tests the QueryTransactionsDetail request through the REST API.
     * The method tests the data by sending the request through JSON object.
     * @return returns the type void.
     */
    @Test
    public void testInvokeQueryTransactionDetailRequest()
    {
        try{
    QueryTransactionsDetail queryTransactionsDetail = new QueryTransactionsDetail();
    
     /* Setting request parameters for Query Transaction Detail */
    queryTransactionsDetail.setTransactionDetailFormat(com.velocity.enums.TransactionDetailFormat.CWSTransaction);
    
    queryTransactionsDetail.getPagingParameters().setPage(0);
    
    queryTransactionsDetail.getPagingParameters().setPageSize(4);
   
    queryTransactionsDetail.getQueryTransactionsParameters().getAmounts().add(100f);
    
    queryTransactionsDetail.getQueryTransactionsParameters().getApprovalCodes().add("VI0000");
   
    queryTransactionsDetail.getQueryTransactionsParameters().getBatchIds().add("0539");  
   
    queryTransactionsDetail.getQueryTransactionsParameters().getCaptureDateRange().setStartDateTime("2015-03-13 02:03:40");
   
    queryTransactionsDetail.getQueryTransactionsParameters().getCaptureDateRange().setEndDateTime("2015-03-17 02:03:40");
    
    queryTransactionsDetail.getQueryTransactionsParameters().getMerchantProfileIds().add("PrestaShop Global HC"); 
    
    queryTransactionsDetail.getQueryTransactionsParameters().getServiceIds().add("2317000001";
   
    queryTransactionsDetail.getQueryTransactionsParameters().getServiceKeys().add("FF3BB6DC58300001");
   
    queryTransactionsDetail.getQueryTransactionsParameters().setQueryType(QueryType.OR);
    
    queryTransactionsDetail.getQueryTransactionsParameters().setCaptureState(CaptureState.Captured);
    
    queryTransactionsDetail.getQueryTransactionsParameters().setCardTypes(TypeCardType.Visa);
    
    queryTransactionsDetail.getQueryTransactionsParameters().setIsAcknowledged("false");
    
    queryTransactionsDetail.getQueryTransactionsParameters().getOrderNumbers().add("629203");
    
    queryTransactionsDetail.getQueryTransactionsParameters().getTransactionDateRange().setEndDateTime("2015-03-17 02:03:40");
    
    queryTransactionsDetail.getQueryTransactionsParameters().getTransactionDateRange().setStartDateTime("2015-03-13 02:03:40");
   
    queryTransactionsDetail.getQueryTransactionsParameters().setTransactionState(TransactionState.Authorized);
    
    queryTransactionsDetail.getQueryTransactionsParameters().getTransactionIds().add("191DB098CC8E402DA8D4B37B5FC6ACE3");
    
    queryTransactionsDetail.getQueryTransactionsParameters().getTransactionIds().add("8D90B6F54CAC440B9B67727437EE27CD");
    
    queryTransactionsDetail.getQueryTransactionsParameters().getTransactionClassTypePairs().put("CREDIT", "AUTH");
    
    VelocityResponse  velocityResponse=velocityProcessor.queryTransactionsDetail(queryTransactionsDetail);
    
    LOG.debug("QueryTransaction response>>>\n" + velocityResponse.getResult());
    
    LOG.debug("TransactionDetailList size >>>>>> " + velocityResponse.getTransactionDetailList().size());
           
        }catch (Exception ex)
        {
            LOG.error("testInvokeQueryTransactionDetailRequest", ex);
        }
    }
    
 
<h2>1.11 captureAll(...) </h2><br/>

  
   The method is used to flag all transactions for settlement 
   that have been successfully authorized using the Authorize operation.

<b>public VelocityResponse captureAll(CaptureAll captureAllTransaction) throws VelocityIllegalArgumentException, VelocityException, VelocityNotFoundException, VelocityRestInvokeException<b/>

@parameter <b>captureAllTransaction</b> - holds the value for captureAllTransaction request CaptureAllTransaction.

<b>Sample Code:</b>


    /**
     * This method tests the CaptureAll request through the REST API.
     * The method performs the Authorize operations before the invoking the
     * captureAll transaction in order to obtain the Transaction Id.
     */
    @Test
    public void testInvokeCaptureAllRequest()
    {
        try{
      CaptureAll objCaptureAll = getCaptureAllInstance();
    VelocityResponse captureAllVelocityResponse=velocityProcessor.captureAll(objCaptureAll);
     if(captureAllVelocityResponse.getArrayOfResponse() != null)
     {
    LOG.debug("invokeCaptureAllRequest Status >>>>>>>>>> " + captureAllVelocityResponse.getArrayOfResponse().getResponse().getStatus());
     }
    else if(captureAllVelocityResponse.getErrorResponse() != null)
    {
     LOG.debug("invokeCaptureAllRequest Status >>>>>>>>>> " + captureAllVelocityResponse.getErrorResponse().getErrorId());
    }
    }catch (Exception ex)
        {
            LOG.error("testinvokeCaptureAllRequest", ex);
        }
    }
	
	

<h2>2. VelocityResponse </h2><br/>

This class implements the responses coming from the Velocity server for a payment transaction request. <br/>
It has the following attributes with name and datatype.<br/>
     1.  statusCode - String <br/>
     2.  message - String <br/>
     3.  bankcardTransactionResponse - com.velocity.models.response.BankcardTransactionResponsePro  <br/>
     4.  bankcardCaptureResponse - com.velocity.models.response.BankcardCaptureResponse    <br/>
     5.  errorResponse - com.velocity.models.response.ErrorResponse    <br/><br/>

<h2>2.1 BankcardTransactionResponsePro</h2><br/>

This class has the following main attributes with its name and datatype. <br/>
     1.   status - String     <br/>
     2.   statusCode - String     <br/>
     3.   statusMessage - String     <br/>
     4.   transactionId - String     <br/>
     5.   originatorTransactionId - String     <br/>
     6.   serviceTransactionId - String     <br/>
     7.   serviceTransactionDateTime - ServiceTransactionDateTime   <br/>
     8.   addendum - Addendum    <br/>
     9.   captureState - String     <br/>
     10.  transactionState - String     <br/>
     11.  acknowledged - boolean   <br/>
     12.  prepaidCard - String     <br/>
     13.  reference - String     <br/>
     14.  amount - String     <br/>
     15.  cardType - String     <br/>
     16.  feeAmount - String    <br/>
     17.  approvalCode - String     <br/>
     18.  avsResult - AVSResult     <br/>
     19.  batchId - String    <br/>
     20.  cVResult - String   <br/>
     21.  cardLevel - String   <br/>
     22.  downgradeCode - String  <br/>
     23.  maskedPAN - String    <br/>
     24.  paymentAccountDataToken - String    <br/>
     25.  retrievalReferenceNumber - String      <br/>
     26.  resubmit - String    <br/>
     27.  settlementDate - String   <br/>
     28.  finalBalance - String     <br/>
     29.  orderId - String       <br/>
     30.  cashBackAmount - String    <br/>
     31.  adviceResponse - String     <br/>
     32.  commercialCardResponse -  String     <br/>
     33.  returnedACI - String       <br/><br/>
     
<h2>2.2 BankcardCaptureResponse </h2><br/>     
   
This class has the following main attributes with its name and datatype. <br/>     
     1.   status - String     <br/>
     2.   statusCode - String     <br/>
     3.   statusMessage - String     <br/>
     4.   transactionId - String     <br/>
     5.   originatorTransactionId - String     <br/>
     6.   serviceTransactionId - String     <br/>
     7.   serviceTransactionDateTime - ServiceTransactionDateTime   <br/>
     8.   date - String   <br/>
     9.   time - String   <br/>
     10.  timezone - String     <br/>
     11.  addendum - Addendum   <br/>
     12.  captureState - String    <br/>
     13.  transactionState - String    <br/>
     14.  acknowledged - String   <br/>
     15.  reference - String       <br/>
     16.  batchId - String       <br/> 
     17.  industryType - String     <br/>
     18.  transactionSummaryData - TransactionSummaryData     <br/>
     19.  prepaidCard - String      <br/>
     
 <h2>2.3 ErrorResponse </h2><br/>     
 
This class has the following main attributes with its name and datatype. <br/>     
   1.  errorId - String   <br/>
   2.  helpUrl - String   <br/>
   3.  operation - String    <br/>
   4.  reason - String   <br/>
   5.  validationErrors - ValidationErrors    <br/>
   
<h2>2.4 ArrayOfResponse</h2><br/>

This class has the following main attributes with its name and datatype. <br/>

   1.   status - String     <br/>
   2.   statusCode - String     <br/>
   3.   statusMessage - String     <br/>
   4.   transactionId - String     <br/>
   5.   originatorTransactionId - String     <br/>
   6.   serviceTransactionId - String     <br/>
   7.   serviceTransactionDateTime - ServiceTransactionDateTime   <br/>
   8.   addendum - Addendum    <br/>
   9.   captureState - String     <br/>
   10.  transactionState - String     <br/>
   11.  acknowledged - boolean   <br/>
   12.  reference - Reference  <br/>
   13.  batchId - BatchId  <br/>
   14.  industryType - String  <br/>
   15.  transactionSummaryData - TransactionSummaryData  <br/>
   16.  prepaidCard - String      <br/>
  


<h2>3. Velocity Test Web Application </h2><br/>


The velocity Test Application is responsible for putting the Velocity Java-SDK for test purpose.  <br/>
It intends to perform the testing of transaction methods available on the Velocity payment gateway for a merchant. <br/>

When a transaction method needs to invoke from Velocity server then it sends the transaction request data and receives the response depending on the type of transaction performed on the velocity server.  <br/>
The request data is send through the User Interface form which includes the fields required for a transaction.  <br/>

The Velocity Test Web Application is able to test the following transaction methods through its user interface.  <br/>

1.<b> Verify </b>- The Verify operation is used to verify information about a payment account, such as address verification data (AVSData) on a credit card account, without reserving any funds.  <br/>
2. <b>Authorize </b>- The Authorize operation is used to authorize transactions by performing a check on card-holder's funds and reserves the authorization amount if sufficient funds are available. <br/>
3. <b>Authorize W/O token </b>- This method proceeds with the card details when payment account data token is not available. <br/>
4. <b>AuthorizeAndCapture </b>- The AuthorizeAndCapture operation is used to authorize transactions by performing a check on card-holder's funds and reserves the authorization amount if sufficient funds are available, and flags the transaction for capture (settlement) in a single invocation.  <br/>
5. <b>AuthorizeAndCapture W/O token </b> - This method proceeds with the card details and performs the capture operation in single invocation when the payment account data token is not available. <br/>
6. <b>Capture </b>- The Capture operation is used to capture a single transaction for settlement after it has been successfully authorized by the Authorize operation. <br/>
7. <b>Void(Undo) </b>- The Undo operation is used to release card-holder funds by performing a void (Credit Card) or reversal (PIN Debit) on a previously authorized transaction that has not been captured (flagged) for settlement.  <br/>
8. <b>Adjust </b>- The Adjust operation is used to make adjustments to a previously authorized amount (incremental or reversal) prior to capture and settlement. <br/>
9. <b>ReturnById </b>- The ReturnById operation is used to perform a linked credit to a card-holder’s account from the merchant’s account based on a previously authorized and settled(Captured) transaction.  <br/>
10. <b>ReturnUnlinked </b>- The ReturnUnlinked operation is used to perform an "unlinked", or standalone, credit to a card-holder’s account from the merchant’s account. <br/>
11. <b>ReturnUnlinked W/O token </b>- This method proceeds with the card details when payment account data token is not available.<br/>
12. <b>QueryTransactionsDetail </b>- This method queries the specified transactions and returns both summary details
and full transaction details.<br/>
13. <b>CaptureAll </b>- This method is used to flag all transactions for settlement that have been successfully authorized using the Authorize operation.

Depending upon the type of transaction performed with request input data, response is generated from the velocity server which can be viewed on the Result page. <br/>
In this test Application View log feature has been provided where one can view the Logs for a transaction request made. <br/>
   
<h2>4. Certificate Installation </h2><br/>

Import certificate to the cacerts store into server Java Home.  <br/>
To do it, execute the following commands from the locations: <br/>

1.	Java\jre7\lib\security  <br/>
2.	Java\jdk1.7.0_07\jre\lib\security  <br/>
    
Command :<b> keytool -import -alias nab -file nab.cer -keystore cacerts </b> <br/>

password: changeit  (Default) <br/>

<b>Reference:  </b>  <br/>

<b> http://azure.microsoft.com/en-in/documentation/articles/java-add-certificate-ca-store/ </b> <br/>

<b>Note:</b> Certificates for this release can be found from the location <b> java-sdk\velocity-sdk\certs</b>. 

<h2>5. Apache Maven Installation  </h2><br/>

The project is developed as maven project so it must be pre-installed to the system for succesful execution.<br/>

1) Download maven from the link mentioned below : <br/>

  <b>http://maven.apache.org/download.cgi</b>

2) Unzip and place Maven folder as for example :

<b> C:\apache-maven-3.0.5 </b>

3) Now add to the PATH of Operating system environment variable as :

<b>  PATH=%PATH%;C:\apache-maven-3.0.5\bin; </b>


<h2>6. Deployment Instructions for Java-SDK and Velocity Test Web Application </h2><br/>

<b>6.1 Velocity SDK</b> <br/>

The maven commands needs to be excuted to build the project as for example : <br/>

<b> java-sdk\velocity-sdk> mvn clean install </b> <br/>

This will ensure the compilation of both <b>velocity-services</b> as well as <b>northamericanbancard-web</b> projects.<br/>

Now Finally we have generated java Velocity SDK as <b>velocity-services-1.0.jar</b>. This can be obtained as below: <br/>

<b>java-sdk\velocity-sdk\velocity-services\target\velocity-services-1.0.jar</b> <br/>
 
 <b>6.2 Velocity Test WebApplication</b> <br/>
 
 1. Find the Test Web client application executable named <b>northamericanbancard.war</b> inside the folder  java-sdk\velocity-sdk\northamericanbancard-web\target <br/>

 2. Deploy <b> northamericanbancard.war </b> file into some web server like Tomcat. <br/>
 3. When the web server starts up, the open the below URL onto a Browser as: <br/>
 
 <b>http://localhost:8080/northamericanbancard/index.jsp</b> <br/>
 4. We can also run Test web application into in-built Jetty web server configured into Maven POM config of   
    <b>northamericanbancard-web</b> project by using following command: <br/><br/>
    <b>java-sdk\velocity-sdk\northamericanbancard-web> mvn jetty:run</b> <br/><br/>
    Now Once the Jetty server started, then open below URL into the browser:<br/>
    <b>http://localhost:8080/index.jsp</b>

<h2>7. Logging Instructions  </h2><br/>

Update log4j.xml file kept under <b> java-sdk\velocity-sdk\{project-name}\src\main\resources\log4j.xml </b>as below to see the application Logs.

      <appender name="DEFAULT-LOG" class="org.apache.log4j.RollingFileAppender"> 
        <param name="File" value="log/myLogs.log"/>
        <param name="Threshold" value="DEBUG" /> 
        <param name="MaxFileSize" value="100MB"/> 
        <param name="MaxBackupIndex" value="10"/> 
        <param name="Append" value="true"/> 
        
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%d{MM/dd/yyyy HH:mm:ss,SSS} [%t] %p %c - %m%n"/>
        </layout>
    </appender> 

    <root> 
     <priority value="DEBUG"/> 
     <appender-ref ref="DEFAULT-LOG"/> 
    </root>
 
 Here the project log file will be generated as <b> java-sdk\velocity-sdk\{project-name}\log\myLogs.log </b>.

<h2>8. Sample Codes  </h2><br/>

Sample codes for how to use Velocity transaction methods can be viewed into below file.

<b> java-sdk/velocity-sdk/velocity-services/src/test/java/com/velocity/services/TestVelocityProcessor.java </b>
