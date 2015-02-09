# java-sdk
This is the velocity java SDK implementation.
It has the implemtation of all the transaction payment solution methods for a merchant application who wants to access the Velocity payment gateway. <br/>
At the centre of this SDK, there is the class <b>com.velocity.transaction.processor.velocity.VelocityProcessor</b>. <br/>
The signature of the constructor of this class is as below: <br/>
<b>public VelocityProcessor(String identityToken, String appProfileId, String merchantProfileId, String workFlowId, boolean isTestAccount) throws VelocityIllegalArgument, VelocityGenericException, VelocityNotFound, VelocityRestInvokeException</b> <br/>
This class provides the implementation of the following methods:
     1. invokeVerifyRequest
     2. invokeAuthorizeRequest
     3. invokeAuthorizeAndCaptureRequest
     4. invokeCaptureRequest
     5. invokeUndoRequest
     6. invokeAdjustRequest
     7. invokeReturnByIdRequest
     8. invokeReturnUnlinkedRequest
