# java-sdk
This is the velocity java SDK implementation. <br/>
It has the implemtation of all the transaction payment solution methods for a merchant application who wants to access the Velocity payment gateway. <br/><br/>
At the centre of this SDK, there is the class <b>com.velocity.transaction.processor.velocity.VelocityProcessor</b>. <br/>
The signature of the constructor of this class is as below: <br/>
<b>public VelocityProcessor(String identityToken, String appProfileId, String merchantProfileId, String workFlowId, boolean isTestAccount) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException</b> <br/>
This class provides the implementation of the following methods: <br/>
     1. invokeVerifyRequest   <br/>
     2. invokeAuthorizeRequest  <br/>
     3. invokeAuthorizeAndCaptureRequest     <br/>
     4. invokeCaptureRequest  <br/>
     5. invokeUndoRequest     <br/>
     6. invokeAdjustRequest   <br/>
     7. invokeReturnByIdRequest    <br/>
     8. invokeReturnUnlinkedRequest     <br/>
