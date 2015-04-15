/**
 * 
 */
package com.velocity.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.apache.axis.encoding.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.velocity.models.transactions.query.QueryTransactionsDetail;
import com.velocity.models.transactions.query.response.models.TransactionDetail;

/**
 * @author anitk
 *
 */
public class TestQueryTransactionsDetail {
	
	
	public static void main(String[] args) throws Exception {
		
		HttpGet getRequest = new HttpGet("https://api.cert.nabcommerce.com/REST/2.0.18/SvcInfo/token");
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String identitytoken = "PHNhbWw6QXNzZXJ0aW9uIE1ham9yVmVyc2lvbj0iMSIgTWlub3JWZXJzaW9uPSIxIiBBc3NlcnRpb25JRD0iXzdlMDhiNzdjLTUzZWEtNDEwZC1hNmJiLTAyYjJmMTAzMzEwYyIgSXNzdWVyPSJJcGNBdXRoZW50aWNhdGlvbiIgSXNzdWVJbnN0YW50PSIyMDE0LTEwLTEwVDIwOjM2OjE4LjM3OVoiIHhtbG5zOnNhbWw9InVybjpvYXNpczpuYW1lczp0YzpTQU1MOjEuMDphc3NlcnRpb24iPjxzYW1sOkNvbmRpdGlvbnMgTm90QmVmb3JlPSIyMDE0LTEwLTEwVDIwOjM2OjE4LjM3OVoiIE5vdE9uT3JBZnRlcj0iMjA0NC0xMC0xMFQyMDozNjoxOC4zNzlaIj48L3NhbWw6Q29uZGl0aW9ucz48c2FtbDpBZHZpY2U+PC9zYW1sOkFkdmljZT48c2FtbDpBdHRyaWJ1dGVTdGF0ZW1lbnQ+PHNhbWw6U3ViamVjdD48c2FtbDpOYW1lSWRlbnRpZmllcj5GRjNCQjZEQzU4MzAwMDAxPC9zYW1sOk5hbWVJZGVudGlmaWVyPjwvc2FtbDpTdWJqZWN0PjxzYW1sOkF0dHJpYnV0ZSBBdHRyaWJ1dGVOYW1lPSJTQUsiIEF0dHJpYnV0ZU5hbWVzcGFjZT0iaHR0cDovL3NjaGVtYXMuaXBjb21tZXJjZS5jb20vSWRlbnRpdHkiPjxzYW1sOkF0dHJpYnV0ZVZhbHVlPkZGM0JCNkRDNTgzMDAwMDE8L3NhbWw6QXR0cmlidXRlVmFsdWU+PC9zYW1sOkF0dHJpYnV0ZT48c2FtbDpBdHRyaWJ1dGUgQXR0cmlidXRlTmFtZT0iU2VyaWFsIiBBdHRyaWJ1dGVOYW1lc3BhY2U9Imh0dHA6Ly9zY2hlbWFzLmlwY29tbWVyY2UuY29tL0lkZW50aXR5Ij48c2FtbDpBdHRyaWJ1dGVWYWx1ZT5iMTVlMTA4MS00ZGY2LTQwMTYtODM3Mi02NzhkYzdmZDQzNTc8L3NhbWw6QXR0cmlidXRlVmFsdWU+PC9zYW1sOkF0dHJpYnV0ZT48c2FtbDpBdHRyaWJ1dGUgQXR0cmlidXRlTmFtZT0ibmFtZSIgQXR0cmlidXRlTmFtZXNwYWNlPSJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcyI+PHNhbWw6QXR0cmlidXRlVmFsdWU+RkYzQkI2REM1ODMwMDAwMTwvc2FtbDpBdHRyaWJ1dGVWYWx1ZT48L3NhbWw6QXR0cmlidXRlPjwvc2FtbDpBdHRyaWJ1dGVTdGF0ZW1lbnQ+PFNpZ25hdHVyZSB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnIyI+PFNpZ25lZEluZm8+PENhbm9uaWNhbGl6YXRpb25NZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzEwL3htbC1leGMtYzE0biMiPjwvQ2Fub25pY2FsaXphdGlvbk1ldGhvZD48U2lnbmF0dXJlTWV0aG9kIEFsZ29yaXRobT0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnI3JzYS1zaGExIj48L1NpZ25hdHVyZU1ldGhvZD48UmVmZXJlbmNlIFVSST0iI183ZTA4Yjc3Yy01M2VhLTQxMGQtYTZiYi0wMmIyZjEwMzMxMGMiPjxUcmFuc2Zvcm1zPjxUcmFuc2Zvcm0gQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjZW52ZWxvcGVkLXNpZ25hdHVyZSI+PC9UcmFuc2Zvcm0+PFRyYW5zZm9ybSBBbGdvcml0aG09Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvMTAveG1sLWV4Yy1jMTRuIyI+PC9UcmFuc2Zvcm0+PC9UcmFuc2Zvcm1zPjxEaWdlc3RNZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjc2hhMSI+PC9EaWdlc3RNZXRob2Q+PERpZ2VzdFZhbHVlPnl3NVZxWHlUTUh5NUNjdmRXN01TV2RhMDZMTT08L0RpZ2VzdFZhbHVlPjwvUmVmZXJlbmNlPjwvU2lnbmVkSW5mbz48U2lnbmF0dXJlVmFsdWU+WG9ZcURQaUorYy9IMlRFRjNQMWpQdVBUZ0VDVHp1cFVlRXpESERwMlE2ZW92T2lhN0pkVjI1bzZjTk1vczBTTzRISStSUGRUR3hJUW9xa0paeEtoTzZHcWZ2WHFDa2NNb2JCemxYbW83NUFSWU5jMHdlZ1hiQUVVQVFCcVNmeGwxc3huSlc1ZHZjclpuUytkSThoc2lZZW4vT0VTOUdtZUpsZVd1WUR4U0xmQjZJZnd6dk5LQ0xlS0FXenBkTk9NYmpQTjJyNUJWQUhQZEJ6WmtiSGZwdUlablp1Q2l5OENvaEo1bHU3WGZDbXpHdW96VDVqVE0wU3F6bHlzeUpWWVNSbVFUQW5WMVVGMGovbEx6SU14MVJmdWltWHNXaVk4c2RvQ2IrZXpBcVJnbk5EVSs3NlVYOEZFSEN3Q2c5a0tLSzQwMXdYNXpLd2FPRGJJUFpEYitBPT08L1NpZ25hdHVyZVZhbHVlPjxLZXlJbmZvPjxvOlNlY3VyaXR5VG9rZW5SZWZlcmVuY2UgeG1sbnM6bz0iaHR0cDovL2RvY3Mub2FzaXMtb3Blbi5vcmcvd3NzLzIwMDQvMDEvb2FzaXMtMjAwNDAxLXdzcy13c3NlY3VyaXR5LXNlY2V4dC0xLjAueHNkIj48bzpLZXlJZGVudGlmaWVyIFZhbHVlVHlwZT0iaHR0cDovL2RvY3Mub2FzaXMtb3Blbi5vcmcvd3NzL29hc2lzLXdzcy1zb2FwLW1lc3NhZ2Utc2VjdXJpdHktMS4xI1RodW1icHJpbnRTSEExIj5ZREJlRFNGM0Z4R2dmd3pSLzBwck11OTZoQ2M9PC9vOktleUlkZW50aWZpZXI+PC9vOlNlY3VyaXR5VG9rZW5SZWZlcmVuY2U+PC9LZXlJbmZvPjwvU2lnbmF0dXJlPjwvc2FtbDpBc3NlcnRpb24+";
		
		String encIdentitytoken = new String(Base64.encode((identitytoken + ":").getBytes()));
		
		getRequest.addHeader("Authorization", "Basic "+encIdentitytoken);
		getRequest.addHeader("Content-type", "application/json");
		getRequest.addHeader("Accept", "");

		HttpResponse response = httpClient.execute(getRequest);
		
		
		System.out.println("Identity Token response :: "+response.getStatusLine().getReasonPhrase());
		// Get-Capture Complete application/xml body response
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		String output = null;
		System.out.println("============Output:============");
		
		String sessionToken = null;
				
		sessionToken = EntityUtils.toString(response.getEntity());
		System.out.println("sessionToken:"+sessionToken);
		sessionToken = sessionToken.substring(1, sessionToken.length() - 1);
		String encSessiontoken = new String(Base64.encode((sessionToken + ":").getBytes()));
		
		HttpPost postRequest = new HttpPost("https://api.cert.nabcommerce.com/REST/2.0.18/DataServices/TMS/transactionsDetail");
		
		Gson gson = new Gson();
		
		QueryTransactionsDetail queryTransactionsDetail = new QueryTransactionsDetail();
		
		/* Setting request parameters for Query Transaction Detail */
		queryTransactionsDetail.setTransactionDetailFormat(com.velocity.enums.TransactionDetailFormat.CWSTransaction);
		queryTransactionsDetail.getPagingParameters().setPage(0);
		queryTransactionsDetail.getPagingParameters().setPageSize(4);
	    //queryTransactionsDetail.getQueryTransactionsParameters().getAmounts().add(100f);
     	//queryTransactionsDetail.getQueryTransactionsParameters().getApprovalCodes().add("VI0000");
     	
     	//queryTransactionsDetail.getQueryTransactionsParameters().getBatchIds().add("null");
     	 //queryTransactionsDetail.getQueryTransactionsParameters().getCaptureDateRange().setStartDateTime("200154-05-30T09:30:10-06:00");
		//queryTransactionsDetail.getQueryTransactionsParameters().getCaptureDateRange().setEndDateTime("2014-12-19T18:16:48.437");
     	
     	//queryTransactionsDetail.getQueryTransactionsParameters().getMerchantProfileIds().add("PrestaShop Global HC");
//     	queryTransactionsDetail.getQueryTransactionsParameters().getServiceIds().add("2317000001");
//     	queryTransactionsDetail.getQueryTransactionsParameters().getServiceKeys().add("FF3BB6DC58300001");
     	//queryTransactionsDetail.getQueryTransactionsParameters().setQueryType(QueryType.OR);
		
		//queryTransactionsDetail.getQueryTransactionsParameters().setCaptureStates(CaptureStates.Captured);
		//queryTransactionsDetail.getQueryTransactionsParameters().setCardTypes(TypeCardType.AmericanExpress);
	   
 		//queryTransactionsDetail.getQueryTransactionsParameters().setIsAcknowledged("false");
 		//queryTransactionsDetail.getQueryTransactionsParameters().getOrderNumbers().add("629203");
 		//queryTransactionsDetail.getQueryTransactionsParameters().getTransactionIds().add("B877F283052443119721C5699CD5C408");
 		//queryTransactionsDetail.getQueryTransactionsParameters().getTransactionIds().add("191DB098CC8E402DA8D4B37B5FC6ACE3");
	     queryTransactionsDetail.getQueryTransactionsParameters().getTransactionIds().add("8D90B6F54CAC440B9B67727437EE27CD");
		// 2014-12-19T18:16:48.437
		
		 //queryTransactionsDetail.getQueryTransactionsParameters().getTransactionIds().add("EC417AFFD139420D85F2E5F9D46C4591");
		//queryTransactionsDetail.getQueryTransactionsParameters().getTransactionIds().add("A708DAB6DF2A4CD490E5F3C0F3DCF017");
		
 		//queryTransactionsDetail.getQueryTransactionsParameters().getTransactionClassTypePairs().add(new TransactionClassTypePair("CREDIT", "AUTH"));
 		//queryTransactionsDetail.getQueryTransactionsParameters().getTransactionClassTypePairs().add(new TransactionClassTypePair("DEBIT", "CAPTURE"));
// 		queryTransactionsDetail.getQueryTransactionsParameters().getTransactionClassTypePairs().setTransactionType("AUTH");
		
		String qtdJson = gson.toJson(queryTransactionsDetail);

		System.out.println("Query Transaction Detail JSON >>>>>>>>>>> "+qtdJson);
		
		postRequest.addHeader("Authorization", "Basic "+encSessiontoken);
		postRequest.addHeader("Content-type", "application/json");
		postRequest.addHeader("Accept", "");
		
		HttpEntity entity = new ByteArrayEntity(qtdJson.getBytes());
		postRequest.setEntity(entity);
		response = httpClient.execute(postRequest);
		
		String result = EntityUtils.toString(response.getEntity());
		
		System.out.println("Query Transaction Detail Response:: "+response.getStatusLine().getReasonPhrase());
		System.out.println("Query Transaction Detail Result:: "+result);
		
		/*Type collectionType = new TypeToken<Collection<TransactionInformation>>(){}.getType();
		Collection<TransactionInformation> transactionInformationCollection = gson.fromJson(result, collectionType);
		
		Iterator<TransactionInformation> itr = transactionInformationCollection.iterator();
		while(itr.hasNext())
		{
			TransactionInformation transactionInformation = (TransactionInformation) itr.next();
			System.out.println(transactionInformation.getApprovalCode());
		}*/
		
		TransactionDetail[] arrTransactionDetail = gson.fromJson(result, TransactionDetail[].class);
		List<TransactionDetail> list = Arrays.asList(arrTransactionDetail);
//		System.out.println("Hello Array Length >> "+arrTransactionDetail.length);
//		System.out.println("Hello Array >> "+gson.toJson(arrTransactionDetail));
//		System.out.println("Hello >> "+gson.toJson(arrTransactionDetail[0]));
//		
//		for (int i = 0; i < arrTransactionDetail.length; i++) {
//		
//		TransactionDetail transactionDetail = gson.fromJson(gson.toJson(arrTransactionDetail[i]), TransactionDetail.class);
//		
//		System.out.println("FamilyId : "+transactionDetail.getFamilyInformation().getFamilyId());
//		System.out.println("FamilySequenceCount : "+transactionDetail.getFamilyInformation().getFamilySequenceCount());
//		System.out.println("FamilySequenceNumber : "+transactionDetail.getFamilyInformation().getFamilySequenceNumber());
//		System.out.println("FamilyState : "+transactionDetail.getFamilyInformation().getFamilyState());
//		System.out.println("NetAmount : "+transactionDetail.getFamilyInformation().getNetAmount());
//		
//		System.out.println("ApprovalCode : "+transactionDetail.getTransactionInformation().getApprovalCode());
//		System.out.println("Amount : "+transactionDetail.getTransactionInformation().getAmount());
//		System.out.println("ActualResult : "+transactionDetail.getTransactionInformation().getBankcardData().getaVSResult().getActualResult());
//		
//		System.out.println("BatchId : "+transactionDetail.getTransactionInformation().getBatchId());
//		System.out.println("CapturedAmount : "+transactionDetail.getTransactionInformation().getCapturedAmount());
//		System.out.println("CaptureDateTime : "+transactionDetail.getTransactionInformation().getCaptureDateTime());
//		System.out.println("CaptureStates : "+transactionDetail.getTransactionInformation().getCaptureStates());
//		
//		System.out.println("CaptureStatusMessage : "+transactionDetail.getTransactionInformation().getCaptureStatusMessage());
//		System.out.println("CustomerId : "+transactionDetail.getTransactionInformation().getCustomerId());
//		System.out.println("IsAcknowledged : "+transactionDetail.getTransactionInformation().getIsAcknowledged());
//		System.out.println("MaskedPAN : "+transactionDetail.getTransactionInformation().getMaskedPAN());
//		System.out.println("MerchantProfileId : "+transactionDetail.getTransactionInformation().getMerchantProfileId());
//		System.out.println("OriginatorTransactionId : "+transactionDetail.getTransactionInformation().getOriginatorTransactionId());
//		System.out.println("Reference : "+transactionDetail.getTransactionInformation().getReference());
//		
//		System.out.println("ServiceId : "+transactionDetail.getTransactionInformation().getServiceId());
//		System.out.println("ServiceKey : "+transactionDetail.getTransactionInformation().getServiceKey());
//		System.out.println("ServiceTransactionId : "+transactionDetail.getTransactionInformation().getServiceTransactionId());
//		System.out.println("Status : "+transactionDetail.getTransactionInformation().getStatus());
//		System.out.println("TransactionClassTypePair : "+transactionDetail.getTransactionInformation().getTransactionClassTypePair());
//		System.out.println("TransactionId : "+transactionDetail.getTransactionInformation().getTransactionId());
//		System.out.println("TransactionStates : "+transactionDetail.getTransactionInformation().getTransactionStates());
//		System.out.println("TransactionStatusCode : "+transactionDetail.getTransactionInformation().getTransactionStatusCode());
//		System.out.println("TransactionTimestamp : "+transactionDetail.getTransactionInformation().getTransactionTimestamp());
//		
//		System.out.println("SerializedTransaction : "+transactionDetail.getCompleteTransaction().getSerializedTransaction());
//		System.out.println("ApplicationAttended : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().isApplicationAttended());
//		System.out.println("ApplicationLocation : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getApplicationLocation());
//
//		System.out.println("DeveloperId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getDeveloperId());
//      System.out.println("DeviceSerialNumber : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getDeviceSerialNumber());
//		
//      System.out.println("EncryptionType : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getEncryptionType());
//		System.out.println("HardwareType : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getHardwareType());
//		System.out.println("PINCapability : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getpINCapability());
//		System.out.println("PTLSSocketId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getpTLSSocketId());
//		System.out.println("ReadCapability : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getReadCapability());
//		System.out.println("SerialNumber : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getSerialNumber());
//		System.out.println("SoftwareVersion : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getSoftwareVersion());
//		System.out.println("SoftwareVersionDate : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getSoftwareVersionDate());
//		System.out.println("VendorId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getVendorId());
//		
//		System.out.println("City : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getMerchantProfileMerchantData().getAddress().getCity());
//		System.out.println("PostalCode : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getMerchantProfileMerchantData().getAddress().getPostalCode());
//		System.out.println("Street1 : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getMerchantProfileMerchantData().getAddress().getStreet1());
//		System.out.println("Street2 : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getMerchantProfileMerchantData().getAddress().getStreet2());
//		System.out.println("CountryCode : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getMerchantProfileMerchantData().getAddress().getCountryCode());
		
//		
//		System.out.println("Type : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getType());
//		System.out.println("Status : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getStatus());
//		System.out.println("AddressResult : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getAvsResult().getAddressResult());
//		System.out.println("StatusCode : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getStatusCode());
//		System.out.println("StatusMessage : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getStatusMessage());
//		System.out.println("TransactionId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getTransactionId());
//		System.out.println("OriginatorTransactionId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getOriginatorTransactionId());
//		System.out.println("ServiceTransactionId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getServiceTransactionId());
//		System.out.println("Date : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getServiceTransactionDateTime().getDate());
//		System.out.println("Time : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getServiceTransactionDateTime().getTime());
//		System.out.println("TimeZone : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getServiceTransactionDateTime().getTimeZone());
//		System.out.println("CaptureState : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getCaptureState());
//		
//		System.out.println("TransactionState : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getTransactionState());
//		System.out.println("isAcknowledged : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().isAcknowledged());
//		System.out.println("PrepaidCard : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getPrepaidCard());
//		
//		System.out.println("SwipeStatus : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getTransaction().getTenderData().getSwipeStatus().isNillable());
//		System.out.println("WorkflowId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getMetaData().getWorkflowId());
//		System.out.println("MaskedPAN : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getMetaData().getMaskedPAN());
//		System.out.println("SequenceNumber : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getMetaData().getSequenceNumber());
//		
//		}	
//		 System.out.println("yiw : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getTransaction().getCustomerData().getShippingData().isNillable());
		
		
		/*transactionDetail = gson.fromJson(gson.toJson(arr[1]), TransactionDetail.class);
		
		System.out.println("FamilyId : "+transactionDetail.getFamilyInformation().getFamilyId());
		System.out.println("FamilySequenceCount : "+transactionDetail.getFamilyInformation().getFamilySequenceCount());
		System.out.println("FamilySequenceNumber : "+transactionDetail.getFamilyInformation().getFamilySequenceNumber());
		System.out.println("FamilyState : "+transactionDetail.getFamilyInformation().getFamilyState());
		System.out.println("NetAmount : "+transactionDetail.getFamilyInformation().getNetAmount());
		
		System.out.println("ApprovalCode : "+transactionDetail.getTransactionInformation().getApprovalCode());
		System.out.println("Amount : "+transactionDetail.getTransactionInformation().getAmount());
		System.out.println("ActualResult : "+transactionDetail.getTransactionInformation().getBankcardData().getaVSResult().getActualResult());
		
		System.out.println("BatchId : "+transactionDetail.getTransactionInformation().getBatchId());
		System.out.println("CapturedAmount : "+transactionDetail.getTransactionInformation().getCapturedAmount());
		System.out.println("CaptureDateTime : "+transactionDetail.getTransactionInformation().getCaptureDateTime());
		System.out.println("CaptureStates : "+transactionDetail.getTransactionInformation().getCaptureStates());
		
		System.out.println("CaptureStatusMessage : "+transactionDetail.getTransactionInformation().getCaptureStatusMessage());
		System.out.println("CustomerId : "+transactionDetail.getTransactionInformation().getCustomerId());
		System.out.println("IsAcknowledged : "+transactionDetail.getTransactionInformation().getIsAcknowledged());
		System.out.println("MaskedPAN : "+transactionDetail.getTransactionInformation().getMaskedPAN());
		System.out.println("MerchantProfileId : "+transactionDetail.getTransactionInformation().getMerchantProfileId());
		System.out.println("OriginatorTransactionId : "+transactionDetail.getTransactionInformation().getOriginatorTransactionId());
		System.out.println("Reference : "+transactionDetail.getTransactionInformation().getReference());
		
		System.out.println("ServiceId : "+transactionDetail.getTransactionInformation().getServiceId());
		System.out.println("ServiceKey : "+transactionDetail.getTransactionInformation().getServiceKey());
		System.out.println("ServiceTransactionId : "+transactionDetail.getTransactionInformation().getServiceTransactionId());
		System.out.println("Status : "+transactionDetail.getTransactionInformation().getStatus());
		System.out.println("TransactionClassTypePair : "+transactionDetail.getTransactionInformation().getTransactionClassTypePair());
		System.out.println("TransactionId : "+transactionDetail.getTransactionInformation().getTransactionId());
		System.out.println("TransactionStates : "+transactionDetail.getTransactionInformation().getTransactionStates());
		System.out.println("TransactionStatusCode : "+transactionDetail.getTransactionInformation().getTransactionStatusCode());
		System.out.println("TransactionTimestamp : "+transactionDetail.getTransactionInformation().getTransactionTimestamp());
		
		System.out.println("SerializedTransaction : "+transactionDetail.getCompleteTransaction().getSerializedTransaction());
		System.out.println("ApplicationAttended : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().isApplicationAttended());
		System.out.println("ApplicationLocation : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getApplicationLocation());

		System.out.println("DeveloperId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getDeveloperId());
        System.out.println("DeviceSerialNumber : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getDeviceSerialNumber());
		
        System.out.println("EncryptionType : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getEncryptionType());
		System.out.println("HardwareType : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getHardwareType());
		System.out.println("PINCapability : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getpINCapability());
		System.out.println("PTLSSocketId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getpTLSSocketId());
		System.out.println("ReadCapability : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getReadCapability());
		System.out.println("SerialNumber : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getSerialNumber());
		System.out.println("SoftwareVersion : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getSoftwareVersion());
		System.out.println("SoftwareVersionDate : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getSoftwareVersionDate());
		System.out.println("VendorId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getVendorId());
		//BankCardTransactionResponsePro obj = gson.fromJson(gson.toJson(transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse()), BankCardTransactionResponsePro.class);
		//System.out.println("FamilyId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getStatusCode());
	
		System.out.println("FamilyId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getStatus());
		System.out.println("yiwtyituo : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getAvsResult().getAddressResult());*/
		
//		System.out.println("yiw : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getTransaction().getCustomerData().getShippingData().isNillable());
		
		
		
		//====================================================//
		
		/*int count = 0;
		
		QueryTransactionDetailResponse queryTransactionDetailResponse = new QueryTransactionDetailResponse();
		queryTransactionDetailResponse.setTransactionDetailList(list);
		queryTransactionDetailResponse.setStatus(response.getStatusLine().getReasonPhrase());
		queryTransactionDetailResponse.setStatuscode(response.getStatusLine().getStatusCode());
		
		System.out.println("*************************************************************************************************************************");
		
		System.out.println("QueryTransactionDetailResponse status:: "+queryTransactionDetailResponse.getStatus());
		System.out.println("QueryTransactionDetailResponse statusCode:: "+queryTransactionDetailResponse.getStatuscode());
		System.out.println("QueryTransactionDetailResponse  TransactionDetail List size:: "+queryTransactionDetailResponse.getTransactionDetailList().size());
		
		System.out.println("*************************************************************************************************************************");
		
		for(TransactionDetail transactionDetail : queryTransactionDetailResponse.getTransactionDetailList())
		{
			
			System.out.println("TransactionDetail item "+ ++count);
			System.out.println();
			System.out.println("FamilyId : "+transactionDetail.getFamilyInformation().getFamilyId());
			System.out.println("FamilySequenceCount : "+transactionDetail.getFamilyInformation().getFamilySequenceCount());
			System.out.println("FamilySequenceNumber : "+transactionDetail.getFamilyInformation().getFamilySequenceNumber());
			System.out.println("FamilyState : "+transactionDetail.getFamilyInformation().getFamilyState());
			System.out.println("NetAmount : "+transactionDetail.getFamilyInformation().getNetAmount());
			
			System.out.println("ApprovalCode : "+transactionDetail.getTransactionInformation().getApprovalCode());
			System.out.println("Amount : "+transactionDetail.getTransactionInformation().getAmount());
			System.out.println("ActualResult : "+transactionDetail.getTransactionInformation().getBankcardData().getaVSResult().getActualResult());
			
			System.out.println("BatchId : "+transactionDetail.getTransactionInformation().getBatchId());
			System.out.println("CapturedAmount : "+transactionDetail.getTransactionInformation().getCapturedAmount());
			System.out.println("CaptureDateTime : "+transactionDetail.getTransactionInformation().getCaptureDateTime());
			System.out.println("CaptureStates : "+transactionDetail.getTransactionInformation().getCaptureStates());
			
			System.out.println("CaptureStatusMessage : "+transactionDetail.getTransactionInformation().getCaptureStatusMessage());
			System.out.println("CustomerId : "+transactionDetail.getTransactionInformation().getCustomerId());
			System.out.println("IsAcknowledged : "+transactionDetail.getTransactionInformation().getIsAcknowledged());
			System.out.println("MaskedPAN : "+transactionDetail.getTransactionInformation().getMaskedPAN());
			System.out.println("MerchantProfileId : "+transactionDetail.getTransactionInformation().getMerchantProfileId());
			System.out.println("OriginatorTransactionId : "+transactionDetail.getTransactionInformation().getOriginatorTransactionId());
			System.out.println("Reference : "+transactionDetail.getTransactionInformation().getReference());
			
			System.out.println("ServiceId : "+transactionDetail.getTransactionInformation().getServiceId());
			System.out.println("ServiceKey : "+transactionDetail.getTransactionInformation().getServiceKey());
			System.out.println("ServiceTransactionId : "+transactionDetail.getTransactionInformation().getServiceTransactionId());
			System.out.println("Status : "+transactionDetail.getTransactionInformation().getStatus());
			System.out.println("TransactionClassTypePair : "+transactionDetail.getTransactionInformation().getTransactionClassTypePair());
			System.out.println("TransactionId : "+transactionDetail.getTransactionInformation().getTransactionId());
			System.out.println("TransactionStates : "+transactionDetail.getTransactionInformation().getTransactionStates());
			System.out.println("TransactionStatusCode : "+transactionDetail.getTransactionInformation().getTransactionStatusCode());
			System.out.println("TransactionTimestamp : "+transactionDetail.getTransactionInformation().getTransactionTimestamp());
			
			System.out.println("SerializedTransaction : "+transactionDetail.getCompleteTransaction().getSerializedTransaction());
			System.out.println("ApplicationAttended : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().isApplicationAttended());
			System.out.println("ApplicationLocation : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getApplicationLocation());

			System.out.println("DeveloperId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getDeveloperId());
	        System.out.println("DeviceSerialNumber : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getDeviceSerialNumber());
			
	        System.out.println("EncryptionType : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getEncryptionType());
			System.out.println("HardwareType : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getHardwareType());
			System.out.println("PINCapability : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getpINCapability());
			System.out.println("PTLSSocketId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getpTLSSocketId());
			System.out.println("ReadCapability : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getReadCapability());
			System.out.println("SerialNumber : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getSerialNumber());
			System.out.println("SoftwareVersion : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getSoftwareVersion());
			System.out.println("SoftwareVersionDate : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getSoftwareVersionDate());
			System.out.println("VendorId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getApplicationData().getVendorId());
			
			System.out.println("City : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getMerchantProfileMerchantData().getAddress().getCity());
			System.out.println("PostalCode : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getMerchantProfileMerchantData().getAddress().getPostalCode());
			System.out.println("Street1 : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getMerchantProfileMerchantData().getAddress().getStreet1());
			System.out.println("Street2 : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getMerchantProfileMerchantData().getAddress().getStreet2());
			System.out.println("CountryCode : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getMerchantProfileMerchantData().getAddress().getCountryCode());
			
			
			System.out.println("Type : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getType());
			System.out.println("Status : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getStatus());
			System.out.println("AddressResult : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getAvsResult().getAddressResult());
			System.out.println("StatusCode : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getStatusCode());
			System.out.println("StatusMessage : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getStatusMessage());
			System.out.println("TransactionId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getTransactionId());
			System.out.println("OriginatorTransactionId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getOriginatorTransactionId());
			System.out.println("ServiceTransactionId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getServiceTransactionId());
			System.out.println("Date : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getServiceTransactionDateTime().getDate());
			System.out.println("Time : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getServiceTransactionDateTime().getTime());
			System.out.println("TimeZone : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getServiceTransactionDateTime().getTimeZone());
			System.out.println("CaptureState : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getCaptureState());
			
			System.out.println("TransactionState : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getTransactionState());
			System.out.println("isAcknowledged : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().isAcknowledged());
			System.out.println("PrepaidCard : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getResponse().getPrepaidCard());
			
			System.out.println("SwipeStatus : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getTransaction().getTenderData().getSwipeStatus().isNillable());
			System.out.println("WorkflowId : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getMetaData().getWorkflowId());
			System.out.println("MaskedPAN : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getMetaData().getMaskedPAN());
			System.out.println("SequenceNumber : "+transactionDetail.getCompleteTransaction().getcWSTransaction().getMetaData().getSequenceNumber());
			
			System.out.println("*************************************************************************************************************************");
			
		}*/
		
	//================================================//	
		 
		//System.out.println("====================="+list.get(arr.length));
		/*for (int i = 0; i < list.size(); i++) {
			
			TransactionDetail transactionDetail=list.get(i);
			//System.out.println("family Id "+list.get(i).getFamilyInformation().getFamilyId());
			System.out.println("family Id "+transactionDetail.getFamilyInformation().getFamilyId());
		}*/
	}

}




 

