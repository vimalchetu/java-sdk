<!doctype html>
<html lang="en">
<head>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
  
  <title>JAVA SDK Test APP</title>
 
  <link type="text/css" rel="stylesheet" href="styles/stylesdk.css"/>
  
</head>
<body>
<div class="heading"><button class="btn-back" onclick="document.location.href='result.jsp';">Back</button>
Velocity Test App (Java)
<button class="btn-home" onclick="document.location.href='index.jsp';">Home</button>
</div>
<div id="tabs">
<div class="request-box">
  <ul>
    <li><a href="#tabs-1">SignOn Request & Response</a></li>
    <li><a href="#tabs-2">Verify Request & Response</a></li>
    <c:if test="${txnName != null}">
    <li><a href="#tabs-3">${txnName} Request & Response</a></li>
    </c:if>
  </ul>
  </div>
</div>
  <div id="tabs-1">
    <div id="request">
	<div id="reqheader">Request of SignOn IdentityToken</div>
	<div id="reqdata">
		${identityToken}
	</div>
	</div>
	<div id="response">
	<div id="resheader">Response of SignOn SessionToken</div>
	<div id="resdata">
		${sessionToken}
	</div>
	</div>
	<div style="clear:both"></div>
  </div>
  <div id="tabs-2">
    <div id="request">
	<div id="reqheader">Request of Verify Transaction</div>
	<div id="reqdata">
		<c:out value="${verifyRequestXML}" /> 
	</div>
	</div>
	<div id="response">
	<div id="resheader">Response of Verify Transaction</div>
	<div id="resdata">
		<c:out value="${verifyResponseXML}" /> 
	</div>
	</div>
	<div style="clear:both"></div>
  </div>
  <c:if test="${txnName != null }">
  <div id="tabs-3">
    
				<div id="request">
				<div id="reqheader">Request of ${txnName} </div>
				<div id="reqdata">
					<c:out value="${velocityProcessor.txnRequestLog}" /> 
				</div>
				</div>
				
				<div id="response">
				<div id="resheader">Response of ${txnName} </div>
				<div id="resdata">
					<c:out value="${velocityProcessor.txnResponseLog}" />  
				</div>
				
	<div style="clear:both"></div>
  </div>
  </c:if>
 
</body>
</html>
