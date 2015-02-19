# java-sdk
This is the velocity java SDK implementation. <br/>
It has the implementation of all the transaction payment solution methods for a merchant application who wants to access the Velocity payment gateway. <br/><br/>
At the centre of this SDK, there is the class <b>com.velocity.transaction.processor.velocity.VelocityProcessor</b>. <br/>
The signature of the constructor of this class is as below: <br/>
<b>public VelocityProcessor(String identityToken, String appProfileId, String merchantProfileId, String workFlowId, boolean isTestAccount) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException</b> <br/>

 <h2>1. VelocityProcessor </h2><br/>
This class provides the implementation of the following methods: <br/>
     1. invokeVerifyRequest   <br/>
     2. invokeAuthorizeRequest  <br/>
     3. invokeAuthorizeAndCaptureRequest     <br/>
     4. invokeCaptureRequest  <br/>
     5. invokeUndoRequest     <br/>
     6. invokeAdjustRequest   <br/>
     7. invokeReturnByIdRequest    <br/>
     8. invokeReturnUnlinkedRequest     <br/><br/>

<h2>1.1 invokeVerifyRequest(...) </h2><br/>
The method is responsible for the invocation of verify operation on the Velocity REST server.<br/>

<b> public VelocityResponse invokeVerifyRequest(AuthorizeTransaction authorizeTransaction) throws VelocityGenericException, VelocityIllegalArgument, VelocityNotFound, VelocityRestInvokeException</b><br/><br/>

@parameter <b>authorizeTrsansaction </b> - holds the values for the verify request AuthorizeTransaction <br/>

@returnType  <b>VelocityResponse</b>  <br/>  

<b>Sample Code:</b>

  /*Invokes the Verify transaction from the VelocityTestApp*/
			if(txnName != null && txnName.equalsIgnoreCase(NABConstants.VERIFY))
			{
				velocityResponse = velocityProcessor.invokeVerifyRequest(getVerifyRequestAuthorizeTransactionInstance(req));
				if(velocityResponse != null && velocityResponse.getBankcardTransactionResponse() != null)
				{
					paymentAccountDataToken = velocityResponse.getBankcardTransactionResponse().getPaymentAccountDataToken();
					session.setAttribute("paymentAccountDataToken", paymentAccountDataToken);
				}

<h2>1.2 invokeAuthorizeRequest(...) </h2><br/>
The method is responsible for the invocation of authorize operation on the Velocity REST server.<br/>
<b>public VelocityResponse invokeAuthorizeRequest(com.velocity.models.request.authorize.AuthorizeTransaction authorizeTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityRestInvokeException, VelocityNotFound</b><br/>

@parameter <b>authorizeTrsansaction </b> - holds the values for the authorize request AuthorizeTransaction <br/>

@returnType  <b>VelocityResponse</b>  <br/>  

<b>Sample Code:</b>

   else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.AUTHORIZE))
			{
				velocityResponse = velocityProcessor.invokeAuthorizeRequest(getAuthorizeRequestAuthorizeTransactionInstance(req));
				if(velocityResponse.getBankcardTransactionResponse() != null)
				{
					session.setAttribute("transactionId", velocityResponse.getBankcardTransactionResponse().getTransactionId());
				}
				session.setAttribute("txnName", "Authorize");
				AppLogger.logDebug(this.getClass(), "Authorize", "VelocityResponse >>>>>>>>>> "+ velocityResponse);
            

<h2>1.3 invokeAuthorizeAndCaptureRequest(...) </h2><br/>
The method is responsible for the invocation of authorizeAndCapture operation on the Velocity REST server.<br/>
 <b>                                                                                                                       public VelocityResponse invokeAuthorizeAndCaptureRequest(com.velocity.models.request.authorizeAndCapture.AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException</b><br/>

@parameter <b>authorizeAndCaptureTransaction </b> - holds the values for the authorizeAndCapture request 
AuthorizeAndCaptureTransaction <br/>

@returnType  <b>VelocityResponse</b>  <br/>  

 <b>Sample Code:</b>
  
       else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.AUTHORIZEANDCAPTURE))
			{	
				velocityResponse = velocityProcessor.invokeAuthorizeAndCaptureRequest(getAuthorizeAndCaptureTransactionInstance(req));
				session.setAttribute("txnName", "AuthorizeAndCapture");
				AppLogger.logDebug(this.getClass(), "AuthAndCapture", "VelocityResponse >>>>>>>>>> "+ velocityResponse);
			}

<h2>1.4 invokeCaptureRequest(...) </h2><br/>
The method is responsible for the invocation of capture operation on the Velocity REST server.<br/>
<b>                                                                                                                      public VelocityResponse invokeCaptureRequest(com.velocity.models.request.capture.ChangeTransaction captureTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException</b><br/>

@parameter <b>captureTransaction </b> - holds the values for the capture request ChangeTransaction <br/>

@returnType  <b>VelocityResponse</b>  <br/> 

<b>Sample Code:</b>          
 
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
            

<h2>1.5 invokeUndoRequest(...) </h2><br/>
The method is responsible for the invocation of undo operation on the Velocity REST server.<br/>
<b>                                                                                                                      public VelocityResponse invokeUndoRequest(com.velocity.models.request.undo.Undo undoTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException</b><br/>

@parameter <b>undoTransaction </b> - holds the values for the undo request Undo <br/>

@returnType  <b>VelocityResponse</b>  <br/> 

<b>Sample Code:</b>  
   
   
   
    else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.UNDO))
			{	
				Undo objUndo  = getUndoTransactionInstance(req);
				objUndo.setTransactionId((String)session.getAttribute("transactionId"));
				velocityResponse = velocityProcessor.invokeUndoRequest(objUndo);
				AppLogger.logDebug(this.getClass(), "invokeUndoRequest", "VelocityResponse >>>>>>>>>> "+ velocityResponse);
				session.setAttribute("txnName", "Undo");
			}

<h2>1.6 invokeAdjustRequest(...) </h2><br/>
The method is responsible for the invocation of adjust operation on the Velocity REST server.<br/>
<b>                                                                                                                      public VelocityResponse invokeAdjustRequest(com.velocity.models.request.adjust.Adjust adjustTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException</b><br/>

@parameter <b>adjustTransaction </b> - holds the values for the adjust request Adjust <br/>

@returnType  <b>VelocityResponse</b>  <br/> 

 <b>Sample Code:</b>     

 		 /* Invokes the Adjust transaction from the VelocityTestApp */
			else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.ADJUST))
			{	
				Adjust objAdjust  = getAdjustTransactionInstance(req);
				objAdjust.getDifferenceData().setTransactionId((String)session.getAttribute("transactionId"));
				velocityResponse = velocityProcessor.invokeAdjustRequest(objAdjust);
				AppLogger.logDebug(this.getClass(), "invokeAdjustRequest", "VelocityResponse >>>>>>>>>> "+ velocityResponse);
				session.setAttribute("txnName", "Adjust");
			}



<h2>1.7 invokeReturnByIdRequest(...) </h2><br/>
The method is responsible for the invocation of returnById operation on the Velocity REST server.<br/>
<b>                                                                                                                      public VelocityResponse invokeReturnByIdRequest(com.velocity.models.request.returnById.ReturnById returnByIdTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException</b><br/>

@parameter <b>returnByIdTransaction </b> - holds the values for the returnById request ReturnById <br/>

@returnType  <b>VelocityResponse</b>  <br/> 

<b>Sample Code:</b>

   
    /* Invokes the ReturnById transaction from the VelocityTestApp */
			else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.RETURNBYID))
			{	
				ReturnById objReturnById  = getReturnByIdTransactionInstance(req);
				objReturnById.getDifferenceData().setTransactionId((String)session.getAttribute("transactionId"));
				velocityResponse = velocityProcessor.invokeReturnByIdRequest(objReturnById);
				AppLogger.logDebug(this.getClass(), "invokeReturnByIdRequest", "VelocityResponse >>>>>>>>>> "+ velocityResponse);
				session.setAttribute("txnName", "ReturnById");
			}


<h2>1.8 invokeReturnUnlinkedRequest(...) </h2><br/>
The method is responsible for the invocation of returnUnLinked operation on the Velocity REST server.<br/>
<b>                                                                                                                      public VelocityResponse invokeReturnUnlinkedRequest(com.velocity.models.request.returnUnLinked.ReturnTransaction returnUnlinkedTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException</b><br/>

@parameter <b>returnUnlinkedTransaction </b> - holds the values for the returnUnlinked request ReturnTransaction<br/>

@returnType  <b>VelocityResponse</b>  <br/> 

 <b>Sample Code:</b>
   
   
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
