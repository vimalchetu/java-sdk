
<!-- Result page for the various transaction inputs provided through the sample webClient TestApp-->
<html>
<head>
  <%@ page contentType="text/html; charset=UTF-8"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.min.js"
	type="text/javascript"></script>
<script src="scripts/transparent_js.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="styles/stylesdk.css" />
</head>
<body>

	<div class="heading">
		<button class="viewlog btn-back" onclick="document.location.href='index.jsp';">Back</button>
		Velocity Test App (JAVA)


		<button onclick="document.location.href='velocityLogs.jsp';"
			style="float: right;">View Log</button>

	</div>

	<div id="response" class="sub-heading">
		<h2>Response</h2>
		<p />
		<label style="font-weight: bold">Status Code:</label> <label><c:out
				value="${statusCode}" /></label>
		<div class="clear"></div>

		<label style="font-weight: bold">Message:</label> <label><c:out
				value="${message}" /></label>
		<div class="clear"></div>

	</div>

	<!--Response result for BankcardTransactionResponse  -->

	<c:if test="${velocityResponse.bankcardTransactionResponse != null}">

		<div class="sub-heading">
			<h1>BankcardTransactionResponse</h1>
			<p />
			<label style="font-weight: bold">Status:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.status}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">StatusCode:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.statusCode}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">StatusMessage:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.statusMessage}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">TransactionId:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.transactionId}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">OriginatorTransactionId:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.originatorTransactionId}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">ServiceTransactionId:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.serviceTransactionId}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">ServiceTransactionDateTime</label>
			<div class="clear"></div>

			<label style="font-weight: bold; padding-left: 20px;">Date:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.serviceTransactionDateTime.date}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold; padding-left: 20px;">Time:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.serviceTransactionDateTime.time}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold; padding-left: 20px;">TimeZone:</label>
			<label><c:out
					value="${velocityResponse.bankcardTransactionResponse.serviceTransactionDateTime.timeZone}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">Addendum:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.addendum}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">CaptureState:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.captureState}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">TransactionState:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.transactionState}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">IsAcknowledged:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.acknowledged}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">PrepaidCard:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.prepaidCard}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">Reference:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.reference}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">Amount:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.amount}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">CardType:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.cardType}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">FeeAmount:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.feeAmount}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">ApprovalCode:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.approvalCode}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">AVSResult</label>
			<div class="clear"></div>
			
			 <label style="font-weight: bold; padding-left: 20px;">ActualResult:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.avsResult.actualResult}" /></label>
			<div class="clear"></div>
			
			<label style="font-weight: bold; padding-left: 20px;">CityResult:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.avsResult.cityResult}" /></label>
			<div class="clear"></div>
			
			<label style="font-weight: bold; padding-left: 20px;">AddressResult:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.avsResult.addressResult}" /></label>
			<div class="clear"></div>


            <label style="font-weight: bold; padding-left: 20px;">CountryResult:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.avsResult.countryResult}" /></label>
			<div class="clear"></div>
			
			<label style="font-weight: bold; padding-left: 20px;">StateResult:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.avsResult.stateResult}" /></label>
			<div class="clear"></div>
			
			<label style="font-weight: bold; padding-left: 20px;">PostalCodeResult:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.avsResult.postalCodeResult}" /></label>
			<div class="clear"></div>

            <label style="font-weight: bold; padding-left: 20px;">PhoneResult:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.avsResult.phoneResult}" /></label>
			<div class="clear"></div>
			
			<label style="font-weight: bold; padding-left: 20px;">CardholderNameResult:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.avsResult.cardholderNameResult}" /></label>
			<div class="clear"></div>
 
			<div class="clear"></div>

			<label style="font-weight: bold">BatchId:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.batchId}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">CVResult:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.cVResult}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">CardLevel:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.cardLevel}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">DowngradeCode:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.downgradeCode}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">MaskedPAN:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.maskedPAN}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">PaymentAccountDataToken:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.paymentAccountDataToken}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">RetrievalReferenceNumber:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.retrievalReferenceNumber}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">Resubmit:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.resubmit}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">SettlementDate:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.settlementDate}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">FinalBalance:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.finalBalance}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">OrderId:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.orderId}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">CashBackAmount:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.cashBackAmount}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">AdviceResponse:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.adviceResponse}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">CommercialCardResponse:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.commercialCardResponse}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>

			<label style="font-weight: bold">ReturnedACI:</label> <label><c:out
					value="${velocityResponse.bankcardTransactionResponse.returnedACI}" /></label>
			<div class="clear"></div>


		</div>

	</c:if>

	<!--Response result for BankcardCaptureResponse  -->

	<c:if test="${velocityResponse.bankcardCaptureResponse != null}">

		<div class="sub-heading">
			<h1>BankcardCaptureResponse</h1>
			<p />

			<label style="font-weight: bold">Status:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.status}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">StatusCode:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.statusCode}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">StatusMessage:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.statusMessage}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">TransactionId:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.transactionId}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">OriginatorTransactionId:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.originatorTransactionId}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">ServiceTransactionId:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.serviceTransactionId}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">ServiceTransactionDateTime</label>
			<div class="clear"></div>

			<label style="font-weight: bold; margin-left: 10px">Date:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.serviceTransactionDateTime.date}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold; margin-left: 10px">Time:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.serviceTransactionDateTime.time}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold; margin-left: 10px">TimeZone:</label>
			<label><c:out
					value="${velocityResponse.bankcardCaptureResponse.serviceTransactionDateTime.timeZone}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">Addendum:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.addendum.value}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">CaptureState:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.captureState}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">TransactionState:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.transactionState}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">IsAcknowledged:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.acknowledged}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">Reference:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.reference.value}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">BatchId:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.batchId}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">IndustryType:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.industryType}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">TransactionSummaryData:</label>
			<div class="clear"></div>

			<label style="font-weight: bold; margin-left: 14px">CashBackTotals:</label>
			
			 <div class="clear"></div>

			<label style="font-weight: bold; margin-left: 22px; padding-bottom: 12px;">NetAmount:</label>
			<label style="padding-bottom: 12px;"><c:out
					value="${velocityResponse.bankcardCaptureResponse.transactionSummaryData.cashBackTotals.netAmount}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold; margin-left: 22px;">Count:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.transactionSummaryData.cashBackTotals.count}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold; margin-left: 10px">NetTotals:</label>
			<div class="clear"></div>
			<label style="font-weight: bold; margin-left: 22px; padding-bottom: 12px;">NetAmount:</label>
			<label style="padding-bottom: 12px;"><c:out
					value="${velocityResponse.bankcardCaptureResponse.transactionSummaryData.netTotals.netAmount}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold; margin-left: 22px;">Count:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.transactionSummaryData.netTotals.count}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold; margin-left: 10px">ReturnTotals:</label>
			
			 <div class="clear"></div>
			 <label style="font-weight: bold; margin-left: 22px; padding-bottom: 12px;">NetAmount:</label>
			<label style="padding-bottom: 12px;"><c:out
					value="${velocityResponse.bankcardCaptureResponse.transactionSummaryData.returnTotals.netAmount}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold; margin-left: 22px;">Count:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.transactionSummaryData.returnTotals.count}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold; margin-left: 18px; padding-bottom: 12px;">SaleTotals:</label>
			<div class="clear"></div>

			<label style="font-weight: bold; margin-left: 22px; padding-bottom: 12px;">NetAmount:</label>
			<label style="padding-bottom: 12px;"><c:out
					value="${velocityResponse.bankcardCaptureResponse.transactionSummaryData.saleTotals.netAmount}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold; margin-left: 22px;">Count:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.transactionSummaryData.saleTotals.count}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold; margin-left: 10px">VoidTotals:</label>
			
			<div class="clear"></div>
			<label style="font-weight: bold; margin-left: 22px; padding-bottom: 12px;">NetAmount:</label>
			<label style="padding-bottom: 12px;"><c:out
					value="${velocityResponse.bankcardCaptureResponse.transactionSummaryData.voidTotals.netAmount}" /></label>
			<div class="clear"></div>
			
			<label style="font-weight: bold; margin-left: 22px;">Count:</label> <label><c:out
					value="${velocityResponse.bankcardCaptureResponse.transactionSummaryData.voidTotals.count}" /></label>
			<div class="clear"></div>

		</div>

	</c:if>

	<!-- Error Response for NABVelocity Transactions -->

	<c:if test="${velocityResponse.errorResponse != null}">

		<div class="sub-heading">
			<h1>ErrorResponse</h1>
			<p />
			<label style="font-weight: bold">ErrorId:</label> <label><c:out
					value="${velocityResponse.errorResponse.errorId}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">HelpUrl:</label> <label><c:out
					value="${velocityResponse.errorResponse.helpUrl}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">Operation:</label> <label><c:out
					value="${velocityResponse.errorResponse.operation}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">Reason:</label> <label><c:out
					value="${velocityResponse.errorResponse.reason}" /></label>
			<div class="clear"></div>
			
			<label style="font-weight: bold">ValidationErrors</label>
			<div class="clear"></div> 
			
			 <c:forEach items="${velocityResponse.errorResponse.validationErrors.validationErrorList}" var="current">
			 <label style="font-weight: bold; padding-left: 20px;"> <span>-</span> &nbsp; ValidationError</label>
			 <div class="clear"></div> 
			 <p>
			  <label style="font-weight: bold; padding-left: 40px;"><span>-</span> &nbsp;RuleKey</label> <label> : <c:out value="${current.ruleKey}" /></label><div class="clear"></div>
			  <label style="font-weight: bold; padding-left: 40px;"><span>-</span> &nbsp;RuleLocationKey</label> <label> : <c:out value="${current.ruleLocationKey}" /></label><div class="clear"></div>
			  <label style="font-weight: bold; padding-left: 40px;"><span>-</span> &nbsp;RuleMessage</label> <label> : <c:out value="${current.ruleMessage}" /></label><div class="clear"></div>
			  <label style="font-weight: bold; padding-left: 40px;"><span>-</span> &nbsp;TransactionId</label> <label> : <c:out value="${current.transactionId}" /></label><div class="clear"></div>
			  </p>
			</c:forEach>
			</label>
			<div class="clear"></div>

		</div>

	</c:if>
	
	<c:if test="${velocityResponse.jsonErrorResponse != null}">

		<div class="sub-heading">
			<h1>ErrorResponse</h1>
			<p />
			<label style="font-weight: bold">ErrorId:</label> <label><c:out
					value="${velocityResponse.jsonErrorResponse.errorId}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">HelpUrl:</label> <label><c:out
					value="${velocityResponse.jsonErrorResponse.helpUrl}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">Operation:</label> <label><c:out
					value="${velocityResponse.jsonErrorResponse.operation}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">Reason:</label> <label><c:out
					value="${velocityResponse.jsonErrorResponse.reason}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>

		</div>

	</c:if>
	
	<c:if test="${velocityResponse.transactionDetailList != null}">
		<div class="sub-heading">
			<h2>QueryTransactionDetail List in Response:</h2>
			<p />
			
			<c:forEach items="${velocityResponse.transactionDetailList}" var="current">
			<h3>QueryTransactionDetail:</h3>
			
			<label style="font-weight: bold">Status:</label> <label><c:out
					value="${current.transactionInformation.status}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">TransactionId:</label> <label><c:out
					value="${current.transactionInformation.transactionId}" /></label>
			<div class="clear"></div>
			<label style="font-weight: bold">ApprovalCode:</label> <label><c:out
					value="${current.transactionInformation.approvalCode}" /></label>
			<div class="clear"></div>
			<label style="font-weight: bold">Amount:</label> <label><c:out
					value="${current.transactionInformation.amount}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">BatchId:</label> <label><c:out
					value="${current.transactionInformation.batchId}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">CapturedAmount:</label> <label><c:out
					value="${current.transactionInformation.capturedAmount}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">CaptureDateTime:</label> <label><c:out
					value="${current.transactionInformation.captureDateTime}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">CaptureStates:</label> <label><c:out
					value="${current.transactionInformation.captureState}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">CaptureStatusMessage:</label> <label><c:out
					value="${current.transactionInformation.captureStatusMessage}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">MaskedPAN:</label> <label><c:out
					value="${current.transactionInformation.maskedPAN}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">CustomerId:</label> <label><c:out
					value="${current.transactionInformation.customerId}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">StoredValueData:</label> <label><c:out
					value="${current.transactionInformation.storedValueData}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">TransactionClassTypePair:</label> <label><c:out
					value="${current.transactionInformation.transactionClassTypePair}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">TransactionStates:</label> <label><c:out
					value="${current.transactionInformation.transactionState}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">TransactionTimestamp:</label> <label><c:out
					value="${current.transactionInformation.transactionTimestamp}" /></label>
			<div class="clear"></div>
			
			</c:forEach>
			
	</c:if>
	
	<c:if test="${velocityResponse.arrayOfResponse != null}">

		<div class="sub-heading">
			<h1>ArayOfResponse</h1>
			<p />
			<label style="font-weight: bold">Status:</label> <label><c:out
					value="${velocityResponse.arrayOfResponse.response.status}" /></label>
			
			<div class="clear"></div>

			<label style="font-weight: bold">StatusCode:</label> <label><c:out
					value="${velocityResponse.arrayOfResponse.response.statusCode}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>

			<label style="font-weight: bold">StatusMessage:</label> <label><c:out
					value="${velocityResponse.arrayOfResponse.response.statusMessage}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">TransactionId:</label> <label><c:out
					value="${velocityResponse.arrayOfResponse.response.transactionId}" /></label>
			<div class="clear"></div>

			<label style="font-weight: bold">OriginatorTransactionId:</label> <label><c:out
					value="${velocityResponse.arrayOfResponse.response.originatorTransactionId}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>

			<label style="font-weight: bold">ServiceTransactionDateTime:</label> <label><c:out
					value="${velocityResponse.arrayOfResponse.response.serviceTransactionDateTime}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">Addendum:</label> <label><c:out
					value="${velocityResponse.arrayOfResponse.response.addendum}" /></label>
			<div class="clear"></div>

			<div class="clear"></div>
			<label style="font-weight: bold">CaptureState:</label> <label><c:out
					value="${velocityResponse.arrayOfResponse.response.captureState}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">TransactionState:</label> <label><c:out
					value="${velocityResponse.arrayOfResponse.response.transactionState}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">IsAcknowledged:</label> <label><c:out
					value="${velocityResponse.arrayOfResponse.response.isAcknowledged}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">Reference:</label> <label><c:out
					value="${velocityResponse.arrayOfResponse.response.reference}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">BatchId:</label> <label><c:out
					value="${velocityResponse.arrayOfResponse.response.batchId}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">IndustryType:</label> <label><c:out
					value="${velocityResponse.arrayOfResponse.response.industryType}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>
			<label style="font-weight: bold">PrepaidCard:</label> <label><c:out
					value="${velocityResponse.arrayOfResponse.response.prepaidCard}" /></label>
			<div class="clear"></div>
			
			<div class="clear"></div>

            <label style="font-weight: bold">TransactionSummaryData</label>
			<div class="clear"></div>
			
			<label style="font-weight: bold">CashBackTotals</label>
			<div class="clear"></div>

            <label style="font-weight: bold; padding-left: 20px;">NetAmount:</label> <label><c:out
					value="${velocityResponse.arrayOfResponse.response.transactionSummaryData.cashBackTotals.netAmount}" /></label>
			<div class="clear"></div>
			<div class="clear"></div>
			<label style="font-weight: bold; margin-left: 15px">Count:</label> <label><c:out
					value="${velocityResponse.arrayOfResponse.response.transactionSummaryData.cashBackTotals.count}" /></label>
			<div class="clear"></div>
			
		</c:if>	
</body>
</html>