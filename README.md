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

<h2>1.1 invokeAuthorizeRequest(...) </h2><br/>
The method is responsible for the invocation of authorize operation on the Velocity REST server.<br/>
<b>public VelocityResponse invokeAuthorizeRequest(com.velocity.models.request.authorize.AuthorizeTransaction authorizeTransaction) throws VelocityIllegalArgument, VelocityGenericException, VelocityRestInvokeException, VelocityNotFound</b><br/>

@parameter <b>authorizeTrsansaction </b> - holds the values for the authorize request AuthorizeTransaction <br/>

@returnType  <b>VelocityResponse</b>  <br/>  

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
