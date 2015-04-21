package com.velocity.services;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.velocity.enums.CaptureState;
import com.velocity.enums.QueryType;
import com.velocity.enums.TransactionState;
import com.velocity.enums.TypeCardType;
import com.velocity.enums.VelocityEnums;
import com.velocity.exceptions.VelocityException;
import com.velocity.exceptions.VelocityIllegalArgumentException;
import com.velocity.exceptions.VelocityNotFoundException;
import com.velocity.exceptions.VelocityRestInvokeException;
import com.velocity.model.request.Adjust;
import com.velocity.model.request.AuthorizeAndCaptureTransaction;
import com.velocity.model.request.AuthorizeTransaction;
import com.velocity.model.request.CaptureAll;
import com.velocity.model.request.ReturnById;
import com.velocity.model.request.ReturnTransaction;
import com.velocity.model.request.Undo;
import com.velocity.model.request.capture.ChangeTransaction;
import com.velocity.model.response.ArrayOfResponse;
import com.velocity.model.response.VelocityResponse;
import com.velocity.models.transactions.query.QueryTransactionsDetail;
import com.velocity.models.transactions.query.response.models.TransactionDetail;
import com.velocity.transaction.processor.service.IVelocityProcessor;
import com.velocity.transaction.processor.service.impl.VelocityProcessor;
import com.velocity.utility.CommonUtils;

/**
 * This class holds the test data for various for different transactions through JUNIT.
 * 
 * @author Vimal Kumar
 * @date 12-January-2015
 */
public class TestVelocityProcessor {

    private static final Logger LOG = Logger.getLogger(TestVelocityProcessor.class);
    String identityToken =
            "PHNhbWw6QXNzZXJ0aW9uIE1ham9yVmVyc2lvbj0iMSIgTWlub3JWZXJzaW9uPSIxIiBBc3NlcnRpb25JRD0iXzdlMDhiNzdjLTUzZWEtNDEwZC1hNmJiLTAyYjJmMTAzMzEwYyIgSXNzdWVyPSJJcGNBdXRoZW50aWNhdGlvbiIgSXNzdWVJbnN0YW50PSIyMDE0LTEwLTEwVDIwOjM2OjE4LjM3OVoiIHhtbG5zOnNhbWw9InVybjpvYXNpczpuYW1lczp0YzpTQU1MOjEuMDphc3NlcnRpb24iPjxzYW1sOkNvbmRpdGlvbnMgTm90QmVmb3JlPSIyMDE0LTEwLTEwVDIwOjM2OjE4LjM3OVoiIE5vdE9uT3JBZnRlcj0iMjA0NC0xMC0xMFQyMDozNjoxOC4zNzlaIj48L3NhbWw6Q29uZGl0aW9ucz48c2FtbDpBZHZpY2U+PC9zYW1sOkFkdmljZT48c2FtbDpBdHRyaWJ1dGVTdGF0ZW1lbnQ+PHNhbWw6U3ViamVjdD48c2FtbDpOYW1lSWRlbnRpZmllcj5GRjNCQjZEQzU4MzAwMDAxPC9zYW1sOk5hbWVJZGVudGlmaWVyPjwvc2FtbDpTdWJqZWN0PjxzYW1sOkF0dHJpYnV0ZSBBdHRyaWJ1dGVOYW1lPSJTQUsiIEF0dHJpYnV0ZU5hbWVzcGFjZT0iaHR0cDovL3NjaGVtYXMuaXBjb21tZXJjZS5jb20vSWRlbnRpdHkiPjxzYW1sOkF0dHJpYnV0ZVZhbHVlPkZGM0JCNkRDNTgzMDAwMDE8L3NhbWw6QXR0cmlidXRlVmFsdWU+PC9zYW1sOkF0dHJpYnV0ZT48c2FtbDpBdHRyaWJ1dGUgQXR0cmlidXRlTmFtZT0iU2VyaWFsIiBBdHRyaWJ1dGVOYW1lc3BhY2U9Imh0dHA6Ly9zY2hlbWFzLmlwY29tbWVyY2UuY29tL0lkZW50aXR5Ij48c2FtbDpBdHRyaWJ1dGVWYWx1ZT5iMTVlMTA4MS00ZGY2LTQwMTYtODM3Mi02NzhkYzdmZDQzNTc8L3NhbWw6QXR0cmlidXRlVmFsdWU+PC9zYW1sOkF0dHJpYnV0ZT48c2FtbDpBdHRyaWJ1dGUgQXR0cmlidXRlTmFtZT0ibmFtZSIgQXR0cmlidXRlTmFtZXNwYWNlPSJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcyI+PHNhbWw6QXR0cmlidXRlVmFsdWU+RkYzQkI2REM1ODMwMDAwMTwvc2FtbDpBdHRyaWJ1dGVWYWx1ZT48L3NhbWw6QXR0cmlidXRlPjwvc2FtbDpBdHRyaWJ1dGVTdGF0ZW1lbnQ+PFNpZ25hdHVyZSB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnIyI+PFNpZ25lZEluZm8+PENhbm9uaWNhbGl6YXRpb25NZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzEwL3htbC1leGMtYzE0biMiPjwvQ2Fub25pY2FsaXphdGlvbk1ldGhvZD48U2lnbmF0dXJlTWV0aG9kIEFsZ29yaXRobT0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnI3JzYS1zaGExIj48L1NpZ25hdHVyZU1ldGhvZD48UmVmZXJlbmNlIFVSST0iI183ZTA4Yjc3Yy01M2VhLTQxMGQtYTZiYi0wMmIyZjEwMzMxMGMiPjxUcmFuc2Zvcm1zPjxUcmFuc2Zvcm0gQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjZW52ZWxvcGVkLXNpZ25hdHVyZSI+PC9UcmFuc2Zvcm0+PFRyYW5zZm9ybSBBbGdvcml0aG09Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvMTAveG1sLWV4Yy1jMTRuIyI+PC9UcmFuc2Zvcm0+PC9UcmFuc2Zvcm1zPjxEaWdlc3RNZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjc2hhMSI+PC9EaWdlc3RNZXRob2Q+PERpZ2VzdFZhbHVlPnl3NVZxWHlUTUh5NUNjdmRXN01TV2RhMDZMTT08L0RpZ2VzdFZhbHVlPjwvUmVmZXJlbmNlPjwvU2lnbmVkSW5mbz48U2lnbmF0dXJlVmFsdWU+WG9ZcURQaUorYy9IMlRFRjNQMWpQdVBUZ0VDVHp1cFVlRXpESERwMlE2ZW92T2lhN0pkVjI1bzZjTk1vczBTTzRISStSUGRUR3hJUW9xa0paeEtoTzZHcWZ2WHFDa2NNb2JCemxYbW83NUFSWU5jMHdlZ1hiQUVVQVFCcVNmeGwxc3huSlc1ZHZjclpuUytkSThoc2lZZW4vT0VTOUdtZUpsZVd1WUR4U0xmQjZJZnd6dk5LQ0xlS0FXenBkTk9NYmpQTjJyNUJWQUhQZEJ6WmtiSGZwdUlablp1Q2l5OENvaEo1bHU3WGZDbXpHdW96VDVqVE0wU3F6bHlzeUpWWVNSbVFUQW5WMVVGMGovbEx6SU14MVJmdWltWHNXaVk4c2RvQ2IrZXpBcVJnbk5EVSs3NlVYOEZFSEN3Q2c5a0tLSzQwMXdYNXpLd2FPRGJJUFpEYitBPT08L1NpZ25hdHVyZVZhbHVlPjxLZXlJbmZvPjxvOlNlY3VyaXR5VG9rZW5SZWZlcmVuY2UgeG1sbnM6bz0iaHR0cDovL2RvY3Mub2FzaXMtb3Blbi5vcmcvd3NzLzIwMDQvMDEvb2FzaXMtMjAwNDAxLXdzcy13c3NlY3VyaXR5LXNlY2V4dC0xLjAueHNkIj48bzpLZXlJZGVudGlmaWVyIFZhbHVlVHlwZT0iaHR0cDovL2RvY3Mub2FzaXMtb3Blbi5vcmcvd3NzL29hc2lzLXdzcy1zb2FwLW1lc3NhZ2Utc2VjdXJpdHktMS4xI1RodW1icHJpbnRTSEExIj5ZREJlRFNGM0Z4R2dmd3pSLzBwck11OTZoQ2M9PC9vOktleUlkZW50aWZpZXI+PC9vOlNlY3VyaXR5VG9rZW5SZWZlcmVuY2U+PC9LZXlJbmZvPjwvU2lnbmF0dXJlPjwvc2FtbDpBc3NlcnRpb24+";
    String appProfileId = "14644";
    String merchantProfileId = "PrestaShop Global HC";
    String workFlowId = "2317000001";
//     String appProfileId = "14560", merchantProfileId = "PrestaShop Global HC", workFlowId = "BBBAAA0001";
//     String appProfileId = "15464", merchantProfileId = "GlobalEastTCEBT", workFlowId = "A39DF00001"; 
//    String identityToken = "PHNhbWw6QXNzZXJ0aW9uIE1ham9yVmVyc2lvbj0iMSIgTWlub3JWZXJzaW9uPSIxIiBBc3NlcnRpb25JRD0iXzQ2ZTdkZDAzLTIwYzctNGJlZS1hNTdhLWRiNmE4MTA5MDlkNiIgSXNzdWVyPSJJcGNBdXRoZW50aWNhdGlvbiIgSXNzdWVJbnN0YW50PSIyMDE0LTExLTA3VDIxOjQ5OjU2Ljg3N1oiIHhtbG5zOnNhbWw9InVybjpvYXNpczpuYW1lczp0YzpTQU1MOjEuMDphc3NlcnRpb24iPjxzYW1sOkNvbmRpdGlvbnMgTm90QmVmb3JlPSIyMDE0LTExLTA3VDIxOjQ5OjU2Ljg3N1oiIE5vdE9uT3JBZnRlcj0iMjA0NC0xMS0wN1QyMTo0OTo1Ni44NzdaIj48L3NhbWw6Q29uZGl0aW9ucz48c2FtbDpBZHZpY2U+PC9zYW1sOkFkdmljZT48c2FtbDpBdHRyaWJ1dGVTdGF0ZW1lbnQ+PHNhbWw6U3ViamVjdD48c2FtbDpOYW1lSWRlbnRpZmllcj4xQzA4MTc1OEVFNzAwMDAxPC9zYW1sOk5hbWVJZGVudGlmaWVyPjwvc2FtbDpTdWJqZWN0PjxzYW1sOkF0dHJpYnV0ZSBBdHRyaWJ1dGVOYW1lPSJTQUsiIEF0dHJpYnV0ZU5hbWVzcGFjZT0iaHR0cDovL3NjaGVtYXMuaXBjb21tZXJjZS5jb20vSWRlbnRpdHkiPjxzYW1sOkF0dHJpYnV0ZVZhbHVlPjFDMDgxNzU4RUU3MDAwMDE8L3NhbWw6QXR0cmlidXRlVmFsdWU+PC9zYW1sOkF0dHJpYnV0ZT48c2FtbDpBdHRyaWJ1dGUgQXR0cmlidXRlTmFtZT0iU2VyaWFsIiBBdHRyaWJ1dGVOYW1lc3BhY2U9Imh0dHA6Ly9zY2hlbWFzLmlwY29tbWVyY2UuY29tL0lkZW50aXR5Ij48c2FtbDpBdHRyaWJ1dGVWYWx1ZT40OTJhNWU0Yi02NWE0LTRkOTktYjQ0MS1iMzJjOTdmODNkNzY8L3NhbWw6QXR0cmlidXRlVmFsdWU+PC9zYW1sOkF0dHJpYnV0ZT48c2FtbDpBdHRyaWJ1dGUgQXR0cmlidXRlTmFtZT0ibmFtZSIgQXR0cmlidXRlTmFtZXNwYWNlPSJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcyI+PHNhbWw6QXR0cmlidXRlVmFsdWU+MUMwODE3NThFRTcwMDAwMTwvc2FtbDpBdHRyaWJ1dGVWYWx1ZT48L3NhbWw6QXR0cmlidXRlPjwvc2FtbDpBdHRyaWJ1dGVTdGF0ZW1lbnQ+PFNpZ25hdHVyZSB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnIyI+PFNpZ25lZEluZm8+PENhbm9uaWNhbGl6YXRpb25NZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzEwL3htbC1leGMtYzE0biMiPjwvQ2Fub25pY2FsaXphdGlvbk1ldGhvZD48U2lnbmF0dXJlTWV0aG9kIEFsZ29yaXRobT0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnI3JzYS1zaGExIj48L1NpZ25hdHVyZU1ldGhvZD48UmVmZXJlbmNlIFVSST0iI180NmU3ZGQwMy0yMGM3LTRiZWUtYTU3YS1kYjZhODEwOTA5ZDYiPjxUcmFuc2Zvcm1zPjxUcmFuc2Zvcm0gQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjZW52ZWxvcGVkLXNpZ25hdHVyZSI+PC9UcmFuc2Zvcm0+PFRyYW5zZm9ybSBBbGdvcml0aG09Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvMTAveG1sLWV4Yy1jMTRuIyI+PC9UcmFuc2Zvcm0+PC9UcmFuc2Zvcm1zPjxEaWdlc3RNZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjc2hhMSI+PC9EaWdlc3RNZXRob2Q+PERpZ2VzdFZhbHVlPlQ2QmZhUDB2bXgwRitsT3JrRDVja0h4U2lYRT08L0RpZ2VzdFZhbHVlPjwvUmVmZXJlbmNlPjwvU2lnbmVkSW5mbz48U2lnbmF0dXJlVmFsdWU+VHBOalhUNnFMejZ5K2RYVU5yQlRQV0hqVitWbmVkTlNNNTNqdzB5N1RxK1NndEI1OEcvWjdKTEFoNUVLRTBqRERpMHRuQ3cvdmF3bGZ6TjU3VVBxeERzZVpmb1FobmJpQzVxVm5CNmZyOVFZRTlYQ0d1OG01bXhLYno2djl3QzVkVlFEMmxXenRFT0trcnZWL1kwRFVOR2drOEZpdFhmbk1rMVpvakdnNzUvaVFHYW4vUFlWaTBNZDYvc3JLZ1IzdkVsTTlUMm5GWVNkSmlrZUFvM3cweUlEZDNPbG5PL2UyNE1GTzQxdlE3d3lIZDBZUkdDZ2I1YVU4K0ZYelJRbXlyK00rU1RpQVlHT3MwcGRPVE9RNlBleGRITndFS1YzVzJkSUExaElIR2EvUmY0WWc0d0p2aTNublJHd2Z2b1h3RlZYckNsd1d4SVV4ODR2eGtDNitnPT08L1NpZ25hdHVyZVZhbHVlPjxLZXlJbmZvPjxvOlNlY3VyaXR5VG9rZW5SZWZlcmVuY2UgeG1sbnM6bz0iaHR0cDovL2RvY3Mub2FzaXMtb3Blbi5vcmcvd3NzLzIwMDQvMDEvb2FzaXMtMjAwNDAxLXdzcy13c3NlY3VyaXR5LXNlY2V4dC0xLjAueHNkIj48bzpLZXlJZGVudGlmaWVyIFZhbHVlVHlwZT0iaHR0cDovL2RvY3Mub2FzaXMtb3Blbi5vcmcvd3NzL29hc2lzLXdzcy1zb2FwLW1lc3NhZ2Utc2VjdXJpdHktMS4xI1RodW1icHJpbnRTSEExIj5ZREJlRFNGM0Z4R2dmd3pSLzBwck11OTZoQ2M9PC9vOktleUlkZW50aWZpZXI+PC9vOlNlY3VyaXR5VG9rZW5SZWZlcmVuY2U+PC9LZXlJbmZvPjwvU2lnbmF0dXJlPjwvc2FtbDpBc3NlcnRpb24+";
     
   
    boolean isProduction = true;
    private IVelocityProcessor velocityProcessor = null;

    @Before
    public void setUp() throws VelocityException, IOException {
        try{
            velocityProcessor = new VelocityProcessor(null, identityToken, appProfileId, merchantProfileId, workFlowId, isProduction);
        }catch (VelocityRestInvokeException ex){
            LOG.error("Error at Setup: ", ex);
        }catch (VelocityIllegalArgumentException ex){
            LOG.error("Error at Setup: ", ex);
        }catch (VelocityNotFoundException ex){
            LOG.error("Error at Setup: ", ex);
        }catch (VelocityException ex){
            LOG.error("Error at Setup: ", ex);
        }
    }
    @After
    public void tearDown() {
        velocityProcessor = null;
    }
    /**
     * This is the Test case method for Velocity processor signOn method.
     */
    @Test
    public void testInvokeSignOn()
    {
        try{
            String sessionToken = velocityProcessor.signOn(identityToken);
            assert (sessionToken != null);
            LOG.debug("Session Token...>>>" + sessionToken);
        }catch (Exception ex)
        {
            LOG.error("Error at testInvokeSignOn: ", ex);
        }
    }
    /**
     * 
     * This method test's the verify request through the REST API. The AuthorizeTransaction
     * instance is responsible for invoking the verifyRequest method and reads the the data
     * for XML generation through VelocityProcessor class.
     * @author  Vimal Kumar
     */
    @Test
    public void testInvokeVerifyRequest()
    {
        try{
            AuthorizeTransaction objAuthorizeTransaction = getVerifyRequestAuthorizeTransactionInstance();
            VelocityResponse objVelocityResponse = velocityProcessor.verify(objAuthorizeTransaction);
            if(objVelocityResponse.getBankcardTransactionResponse() != null)
            {
                LOG.debug("testInvokeVerifyRequest...>>>" + objVelocityResponse.getBankcardTransactionResponse().getStatus());
            }
            else if(objVelocityResponse.getErrorResponse() != null)
            {
                LOG.debug("Error at testInvokeVerifyRequest...>>>" + objVelocityResponse.getErrorResponse().getReason() + " " + objVelocityResponse.getErrorResponse().getValidationErrors().getValidationErrorList().get(0).getRuleMessage());
            }
        }catch (Exception ex){
            LOG.error("Error at testInvokeVerifyRequest: ", ex);
        }
    }
    /**
     * This method sets the value for the VerifyRequest Instance
     * 
     * @author Vimal Kumar
     * @return- Returns the AuthoizeTransaction Object.
     */
    private AuthorizeTransaction getVerifyRequestAuthorizeTransactionInstance()
    {
        AuthorizeTransaction authorizeTransaction = new AuthorizeTransaction();
        authorizeTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
        authorizeTransaction.getTransaction().getTenderData().getCardData().setCardType("Visa");
        authorizeTransaction.getTransaction().getTenderData().getCardData().setCardholderName("vimal");
        authorizeTransaction.getTransaction().getTenderData().getCardData().setPan("4012888812348882");
        authorizeTransaction.getTransaction().getTenderData().getCardData().setExpire("0320");
        authorizeTransaction.getTransaction().getTenderData().getCardData().setTrack1Data1("");
        authorizeTransaction.getTransaction().getTenderData().getCardData().setTrack2Data2("");
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
        authorizeTransaction.getTransaction().getTransactionData().setEntryMode("Keyed");
        authorizeTransaction.getTransaction().getTransactionData().setIndustryType("Restaurant");
        authorizeTransaction.getTransaction().getTransactionData().setInvoiceNumber("802");
        authorizeTransaction.getTransaction().getTransactionData().setOrderNumber("629203");
        authorizeTransaction.getTransaction().getTransactionData().setTipAmount("2.00");
        return authorizeTransaction;
    }
    /**
     * This method test's the Authorize request through the REST API.
     * 
     * @author Vimal Kumar 
     * The VelocityResponse object reads the data for AuthorizeTransaction and
     * prepares for the response.
     */
    @Test
    public void testInvokeAuthorizeRequest()
    {
        LOG.debug("AppProfileId >>>>>>>>>>>>>" + appProfileId);
        LOG.debug("MerchantProfileId >>>>>>>>>>>>>" + merchantProfileId);
        LOG.debug("WorlflowId >>>>>>>>>>>>>" + workFlowId);
        LOG.debug("Identity Token >>>>>>>>>>>>>" + identityToken);
        try{
             
            AuthorizeTransaction objAuthorizeTransaction =getAuthorizeRequestAuthorizeTransactionInstance();
//          AuthorizeTransaction objAuthorizeTransaction = getP2PEAuthorizeRequestAuthorizeTransactionInstance();
            VelocityResponse objVelocityResponse = velocityProcessor.authorize(objAuthorizeTransaction);
            if(objVelocityResponse.getBankcardTransactionResponse() != null)
            {
                LOG.debug("testInvokeAuthorizeRequest..Status >>>>>>>>>>>>>" + objVelocityResponse.getBankcardTransactionResponse().getStatus());
            }
            else if(objVelocityResponse.getErrorResponse() != null)
            {
                LOG.debug("Error at testInvokeAuthorizeRequest. >>>>>>>>>>>>>" + objVelocityResponse.getErrorResponse().getReason());
                if(objVelocityResponse.getErrorResponse().getValidationErrors() != null)
                {
                    LOG.debug("Error at testInvokeAuthorizeRequest......>>>>>>>" + objVelocityResponse.getErrorResponse().getValidationErrors().getValidationErrorList().get(0).getRuleKey());
                }
            }
        }catch (Exception ex)
        {
            LOG.error("Error at testInvokeAuthorizeRequest", ex);
        }
    }
    /**
     * This method prepares the AuthorizeTransaction instance for AuthorizeTransaction
     * 
     * @author Vimal Kumar 
     * The method sets the values for Authorize XML elements.
     * @return of the type authorizeTransaction
     */
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
        authorizeTransaction.getTransaction().getTenderData().getCardData().setPan("");
        authorizeTransaction.getTransaction().getTenderData().getCardData().setExpire("");
        authorizeTransaction.getTransaction().getTenderData().getCardData().setTrack1Data1("");
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
    /**
     * This method test's the AuthorizeAndCapture request through the REST API.
     * 
     * @author Vimal Kumar 
     * The VelocityResponse object reads the data for AuthorizeAndCaptureTransaction
     * and prepares for the response.
     */
    @Test
    public void testInvokeAuthorizeAndCaptureRequest()
    {
        try{
            AuthorizeAndCaptureTransaction objAuthorizeAndCaptureTransaction = getAuthorizeAndCaptureTransactionInstance();
//          AuthorizeAndCaptureTransaction objAuthorizeAndCaptureTransaction = getP2PEAuthorizeAndCaptureRequestAuthorizeTransactionInstance();
            VelocityResponse objVelocityResponse = velocityProcessor.authorizeAndCapture(objAuthorizeAndCaptureTransaction);
            LOG.debug("invokeAuthorizeAndCaptureRequest..Status >>>>>>>>>>>>" + objVelocityResponse.getBankcardTransactionResponse().getStatus());
        }catch (Exception ex)
        {
            LOG.error("Error at testInvokeAuthorizeAndCaptureRequest", ex);
        }
    }
    private AuthorizeAndCaptureTransaction getAuthorizeAndCaptureTransactionInstance()
    {
        AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction = new AuthorizeAndCaptureTransaction();
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
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().setPan("");
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().setExpire("");
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().setTrack2Data2("");
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().setTrack1Data1("%B4012000033330026^NAJEER/SHAIK ^0904101100001100000000123456780?");
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
    /**
     * 
     * This method tests the Capture request through the REST API.
     * 
     * @author Vimal Kumar 
     * The method performs the Authorize operations before the invoking the capture
     * transaction in order to obtain the Transaction Id.
     */
    @Test
    public void testinvokeCaptureRequest()
    {
        try{
            AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance();
            VelocityResponse objVelocityResponse = velocityProcessor.authorize(objAuthorizeTransaction);
            ChangeTransaction objChangeTransaction = getCaptureTransactionInstance();
            objChangeTransaction.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardTransactionResponse().getTransactionId());
            VelocityResponse captureVelocityResponse = velocityProcessor.capture(objChangeTransaction);
            if(captureVelocityResponse.getBankcardCaptureResponse() != null)
            {
                LOG.debug("capture Status >>>>>>>>>> " + captureVelocityResponse.getBankcardCaptureResponse().getCaptureState());
            }
            else if(captureVelocityResponse.getErrorResponse() != null)
            {
                LOG.debug("capture Status >>>>>>>>>> " + captureVelocityResponse.getErrorResponse().getErrorId());
            }
        }catch (Exception ex)
        {
            LOG.error("Error at testinvokeCaptureRequest", ex);
        }
    }
    /**
     * This method prepares the CaptureTransaction instance for CaptureTransaction
     * 
     * @author Vimal Kumar 
     * The method sets the values for Capture XML elements.
     * @return of the type captureTransaction
     */
    public ChangeTransaction getCaptureTransactionInstance() {
        com.velocity.model.request.capture.ChangeTransaction captureTransaction = new com.velocity.model.request.capture.ChangeTransaction();
        captureTransaction.getDifferenceData().setType(VelocityEnums.BankcardCapture);
        captureTransaction.getDifferenceData().setTransactionId("B877F283052443119721C5699CD5C408");
        captureTransaction.getDifferenceData().setAmount("100.00");
        captureTransaction.getDifferenceData().setTipAmount("3.00");
        return captureTransaction;
    }
    /**
     * This method tests the Undo request through the REST API.
     * 
     * @author Vimal Kumar 
     * The method performs the Authorize operations before the invoking the Undo
     * transaction and obtains the transactionId.
     */
    @Test
    public void testinvokeUndoRequest()
    {
        try{
            AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance();
            VelocityResponse objVelocityResponse = velocityProcessor.authorize(objAuthorizeTransaction);
            Undo objUndo = getUndoTransactionInstance();
            objUndo.setTransactionId(objVelocityResponse.getBankcardTransactionResponse().getTransactionId());
            VelocityResponse objVelocityResponse2 = velocityProcessor.undo(objUndo);
            LOG.debug("testinvokeUndoRequest   Status >>>>>>>>>> " + objVelocityResponse2.getBankcardTransactionResponse().getStatus());
        }catch (Exception ex)
        {
            LOG.error("Error at testinvokeUndoRequest", ex);
        }
    }
    /**
     * This method sets value for the Undo XML.
     * 
     * @author Vimal Kumar
     * @return - of the type undoTransaction
     */
    private Undo getUndoTransactionInstance() {
        Undo undoTransaction = new Undo();
        undoTransaction.setType(VelocityEnums.Undo);
        undoTransaction.getBatchIds().setNillable(true);
        undoTransaction.getDifferenceData().setNillable(true);
        undoTransaction.setTransactionId("8C17F72F1BF649AA88EE1366AF699D40");
        return undoTransaction;
    }
    /**
     * This method tests the Adjust request through the REST API.
     * 
     * @author Vimal Kumar The method performs the Authorize operations before the invoking the Adjust
     *         transaction and obtains the transactionId.
     */
    @Test
    public void testinvokeAdjustRequest()
    {
        try{
            AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance();
            // call Authorize Request API
            VelocityResponse objVelocityResponse = velocityProcessor.authorize(objAuthorizeTransaction);
            if(objVelocityResponse.getBankcardTransactionResponse() != null)
            {
                LOG.debug("testinvokeAdjustRequest  Authorize Status >>>>>>>>>>" + objVelocityResponse.getBankcardTransactionResponse().getStatus());
                ChangeTransaction objChangeTransaction = getCaptureTransactionInstance();
                objChangeTransaction.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardTransactionResponse().getTransactionId());
                // call Capture eRequest API
                objVelocityResponse = velocityProcessor.capture(objChangeTransaction);
                if(objVelocityResponse.getBankcardCaptureResponse() != null)
                {
                    LOG.debug("adjust  Capture Status >>>>>>>>>> " + objVelocityResponse.getBankcardCaptureResponse().getStatus());
                    LOG.debug("adjust  Capture Transaction Id >>>>>>>>>> " + objVelocityResponse.getBankcardCaptureResponse().getTransactionId());
                    Adjust objAdjust = getAdjustTransactionInstance();
                    objAdjust.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardCaptureResponse().getTransactionId());
                    // call Adjust tRequest API
                    objVelocityResponse = velocityProcessor.adjust(objAdjust);
                    LOG.debug("adjust Adjust Status >>>>>>>>>> " + objVelocityResponse.getBankcardTransactionResponse().getStatus());
                }
            }
        }catch (Exception ex)
        {
            LOG.error("Error at testinvokeUndoRequest ", ex);
        }
    }
    /**
     * This method sets the values for the Adjust XML
     * 
     * @author Vimal Kumar
     * @return - of the type Adjust
     */
    private Adjust getAdjustTransactionInstance() {
        Adjust adjustTransaction = new Adjust();
        adjustTransaction.setType(VelocityEnums.Adjust);
        adjustTransaction.getBatchIds().setNillable(true);
        adjustTransaction.getDifferenceData().setAmount("3.00");
        adjustTransaction.getDifferenceData().setTransactionId("8A416AE9BB9840199BD56BA011F80AC2");
        return adjustTransaction;
    }
    /**
     * This method tests the ReturnById request through the REST API.
     * 
     * @author Vimal Kumar
     */
    @Test
    public void testinvokeReturnByIdRequest()
    {
        try{
            AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance();
            VelocityResponse objVelocityResponse = velocityProcessor.authorize(objAuthorizeTransaction);
            if(objVelocityResponse.getBankcardTransactionResponse() != null)
            {
                LOG.debug("testinvokeReturnByIdRequest   Authorize Status >>>>>>>>>>" + objVelocityResponse.getBankcardTransactionResponse().getStatus());
                ChangeTransaction objChangeTransaction = getCaptureTransactionInstance();
                objChangeTransaction.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardTransactionResponse().getTransactionId());
                objVelocityResponse = velocityProcessor.capture(objChangeTransaction);
                if(objVelocityResponse.getBankcardCaptureResponse() != null)
                {
                    LOG.debug("invokeAdjustRequest   Capture Status >>>>>>>>>> " + objVelocityResponse.getBankcardCaptureResponse().getStatus());
                    ReturnById objReturnById = getReturnByIdTransactionInstance();
                    objReturnById.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardCaptureResponse().getTransactionId());
                    objVelocityResponse = velocityProcessor.returnById(objReturnById);
                    LOG.debug("invokeReturnByIdRequest  ReturnById Status >>>>>>>>>>" + objVelocityResponse.getBankcardTransactionResponse().getStatus());
                }
            }
        }catch (Exception ex)
        {
            LOG.error("Error at testinvokeReturnByIdRequest", ex);
        }
    }
    /**
     * This method sets the values for the ReturnById XML.
     * 
     * @author Vimal Kumar
     * @return - of the type ReturnById
     */
    private ReturnById getReturnByIdTransactionInstance() {
        ReturnById returnByIdTransaction = new ReturnById();
        returnByIdTransaction.setType(VelocityEnums.ReturnById);
        returnByIdTransaction.getBatchIds().setNillable(true);
        returnByIdTransaction.getDifferenceData().setTransactionId("EACD2B6739724FD9A322B9BEE396CB14");
        returnByIdTransaction.getDifferenceData().setAmount("50.00");
        return returnByIdTransaction;
    }
    /**
     * 
     * This method tests the ReturnUnlinked request through the REST API.
     * 
     * @author Vimal Kumar
     */
    @Test
    public void testinvokeReturnUnlinkedRequest()
    {
        try{
            ReturnTransaction objReturnTransaction = getReturnTransactionInstance();
//          ReturnTransaction objReturnTransaction = getP2PEReturnTransactionInstance();
            VelocityResponse objVelocityResponse = velocityProcessor.returnUnlinked(objReturnTransaction);
            if(objVelocityResponse.getBankcardTransactionResponse() != null)
            {
                LOG.debug("returnUnlinked..Status >>>>>>>>>>>>>" + objVelocityResponse.getBankcardTransactionResponse().getStatus());
            }
            else if(objVelocityResponse.getErrorResponse() != null)
            {
                LOG.debug("Error at testinvokeReturnUnlinkedRequest. >>>>>>>>>>>>>" + objVelocityResponse.getErrorResponse().getReason());
                if(objVelocityResponse.getErrorResponse().getValidationErrors() != null)
                {
                    LOG.debug("Error at returnUnlinked......>>>>>>>" + objVelocityResponse.getErrorResponse().getValidationErrors().getValidationErrorList().get(0).getRuleKey());
                }
            }
        }catch (Exception ex)
        {
            LOG.error("Error at testinvokeReturnUnlinkedRequest ", ex);
        }
    }
    /**
     * 
     * This method sets the values for the ReturnUnlinked XML.
     * 
     * @author Vimal Kumar
     * @return - of the type ReturnTransaction
     */
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
        returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setPan("4012888812348882");
        returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setExpire("0320");
        returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setTrack2Data2("");
        returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setTrack1Data1("");
        
//      returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setTrack2Data("4012000033330026=09041011000012345678");
//      returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setPan("");
//      returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setExpiryDate("");
//        returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().setNillable(true);
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
    /**
     * This method tests the QueryTransactionsDetail request through the REST API. The method tests
     * the data by sending the request through JSON object.
     * 
     * @author Vimal Kumar
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
            queryTransactionsDetail.getQueryTransactionsParameters().getServiceIds().add("2317000001");
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
            VelocityResponse velocityResponse = velocityProcessor.queryTransactionsDetail(queryTransactionsDetail);
            LOG.debug("QueryTransaction response>>>\n" + velocityResponse.getResult());
            LOG.debug("TransactionDetailList size >>>>>> " + velocityResponse.getTransactionDetailList().size());
            for(TransactionDetail transactionDetail : velocityResponse.getTransactionDetailList())
            {
                LOG.debug("Transaction Id >>>>>>>>>> " + transactionDetail.getTransactionInformation().getTransactionId());
                LOG.debug("Approval Code >>>>>>>>>> " + transactionDetail.getTransactionInformation().getApprovalCode());
                LOG.debug("Amount >>>>>>>>>> " + transactionDetail.getTransactionInformation().getAmount());
                LOG.debug("BatchId >>>>>>>>>> " + transactionDetail.getTransactionInformation().getBatchId());
                LOG.debug("CapturedAmount >>>>>>>>>> " + transactionDetail.getTransactionInformation().getCapturedAmount());
                LOG.debug("CaptureDateTime >>>>>>>>>> " + transactionDetail.getTransactionInformation().getCaptureDateTime());
                LOG.debug("CaptureStatusMessage >>>>>>>>>> " + transactionDetail.getTransactionInformation().getCaptureStatusMessage());
                LOG.debug("CustomerId >>>>>>>>>> " + transactionDetail.getTransactionInformation().getCustomerId());
                LOG.debug("MaskedPAN >>>>>>>>>> " + transactionDetail.getTransactionInformation().getMaskedPAN());
                LOG.debug("MerchantProfileId >>>>>>>>>> " + transactionDetail.getTransactionInformation().getMerchantProfileId());
                LOG.debug("OriginatorTransactionId >>>>>>>>>> " + transactionDetail.getTransactionInformation().getOriginatorTransactionId());
                LOG.debug("Reference >>>>>>>>>> " + transactionDetail.getTransactionInformation().getReference());
                LOG.debug("ServiceId >>>>>>>>>> " + transactionDetail.getTransactionInformation().getServiceId());
                LOG.debug("ServiceKey >>>>>>>>>> " + transactionDetail.getTransactionInformation().getServiceKey());
                LOG.debug("ServiceTransactionId >>>>>>>>>> " + transactionDetail.getTransactionInformation().getServiceTransactionId());
                LOG.debug("TransactionStatusCode >>>>>>>>>> " + transactionDetail.getTransactionInformation().getTransactionStatusCode());
                LOG.debug("TransactionTimestamp >>>>>>>>>> " + transactionDetail.getTransactionInformation().getTransactionTimestamp());
                LOG.debug("OrderId >>>>>>>>>> " + transactionDetail.getTransactionInformation().getBankcardData().getOrderId());
            }
        }catch (Exception ex)
        {
            LOG.error("testInvokeQueryTransactionDetailRequest", ex);
        }
    }
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
        authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setValue("576F2E197D5804F2B6201FB2578DCD1DDDC7BAE692FE48E9C368E678914233561FB953DF47E29F88");
        authorizeTransaction.getTransaction().getTenderData().getEncryptionKeyId().setValue("9010010B257DC7000084");
        authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().setValue("61403000");
        authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().setValue("10CB07E3D25EF91A5DAD25629D1E4A673F016A7B6E6C760F6AAEC985E77B02E796981928AEEE94618C34E2801F4A76E32BCEF984144D51F2");
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
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setValue("2540E479632A5FBACD3BDB8A3798104BC5C06105421D5E6369C7F78CBEA85647434D966CF8B4DAD1");
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
    /**
     * 
     * This method sets the values for the P2PEReturnUnlinked XML.
     * 
     * @author Vimal Kumar
     * @return - of the type ReturnTransaction
     */
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
    /**
     * This method tests the CaptureAll request through the REST API.
     * 
     * @author Vimal Kumar 
     * The method performs the Authorize operations before the invoking the
     * captureAll transaction in order to obtain the Transaction Id.
     */
    @Test
    public void testInvokeCaptureAllRequest()
    {
        try{
            CaptureAll objCaptureAll = getCaptureAllInstance();
            VelocityResponse captureAllVelocityResponse = velocityProcessor.captureAll(objCaptureAll);
            if(captureAllVelocityResponse.getArrayOfResponse() != null)
            {
                LOG.debug("invokeCaptureAllRequest Status >>>>>>>>>> " + captureAllVelocityResponse.getArrayOfResponse().getResponse().getStatus());
                LOG.debug("invokeCaptureAllRequest Amount >>>>>>>>>> " + captureAllVelocityResponse.getArrayOfResponse().getResponse().getTransactionSummaryData().getCashBackTotals().getNetAmount());
                LOG.debug("invokeCaptureAllRequest Count >>>>>>>>>> " + captureAllVelocityResponse.getArrayOfResponse().getResponse().getTransactionSummaryData().getReturnTotals().getCount());
                LOG.debug("invokeCaptureAllRequest Amount >>>>>>>>>> " + captureAllVelocityResponse.getArrayOfResponse().getResponse().getTransactionSummaryData().getNetTotals().getNetAmount());
                LOG.debug("invokeCaptureAllRequest Amount >>>>>>>>>> " + captureAllVelocityResponse.getArrayOfResponse().getResponse().getTransactionSummaryData().getVoidTotals().getNetAmount());
                LOG.debug("invokeCaptureAllRequest Amount >>>>>>>>>> " + captureAllVelocityResponse.getArrayOfResponse().getResponse().getTransactionSummaryData().getSaleTotals().getNetAmount());
                LOG.debug("invokeCaptureAllRequest Amount >>>>>>>>>> " + captureAllVelocityResponse.getArrayOfResponse().getResponse().getTransactionSummaryData().getPinDebitSaleTotals().getNetAmount());
                LOG.debug("invokeCaptureAllRequest Amount >>>>>>>>>> " + captureAllVelocityResponse.getArrayOfResponse().getResponse().getTransactionSummaryData().getPinDebitReturnTotals().getNetAmount());
                LOG.debug("invokeCaptureAllRequest Amount >>>>>>>>>> " + captureAllVelocityResponse.getArrayOfResponse().getResponse().getPrepaidCard());
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
    /**
     * This method prepares the CaptureAll instance for CaptureAllTransaction
     * 
     * @author Vimal Kumar 
     * The method sets the values for CaptureAll XML elements.
     * @return of the type captureTransaction
     */
    private CaptureAll getCaptureAllInstance() {
        CaptureAll captureAllTransaction = new CaptureAll();
        captureAllTransaction.getBatchIds().setNillable(true);
        captureAllTransaction.getDifferenceData().setNillable(true);
        captureAllTransaction.getDifferenceData().setNillable(true);
        return captureAllTransaction;
    }
    @Test
    public void testGenerateArrayOfResponse()
    {
        try{
            String result = readFileAsString("D:\\test.xml");
            // System.out.println(result);
            ArrayOfResponse objArrayOfResponse = CommonUtils.generateArrayOfResponse(result);
            LOG.debug("TransactionId>>>>" + objArrayOfResponse.getResponse().getTransactionId());
            LOG.debug("OriginatorTransactionId>>>>" + objArrayOfResponse.getResponse().getOriginatorTransactionId());
            LOG.debug("ServiceTransactionDateTime>>>>" + objArrayOfResponse.getResponse().getServiceTransactionDateTime());
            LOG.debug("Addendum>>>>" + objArrayOfResponse.getResponse().getAddendum());
            LOG.debug("CaptureState>>>>" + objArrayOfResponse.getResponse().getCaptureState());
            LOG.debug("TransactionState>>>>" + objArrayOfResponse.getResponse().getTransactionState());
            LOG.debug("IsAcknowledged>>>>" + objArrayOfResponse.getResponse().getIsAcknowledged());
            LOG.debug("Reference>>>>" + objArrayOfResponse.getResponse().getReference());
            LOG.debug("BatchId>>>>" + objArrayOfResponse.getResponse().getBatchId());
            LOG.debug("IndustryType>>>>" + objArrayOfResponse.getResponse().getIndustryType());
            LOG.debug("NetAmount>>>>" + objArrayOfResponse.getResponse().getTransactionSummaryData().getCashBackTotals().getNetAmount());
            LOG.debug("Count>>>>" + objArrayOfResponse.getResponse().getTransactionSummaryData().getCashBackTotals().getNetAmount());
            LOG.debug("PrepaidCard>>>>" + objArrayOfResponse.getResponse().getPrepaidCard());
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private String readFileAsString(String path) throws Exception
    {
        StringBuffer strBuff = new StringBuffer();
        try{
            FileInputStream fis = new FileInputStream(path);
            int content;
            while ((content = fis.read()) != -1){
                // convert to char and display it
                strBuff.append((char) content);
            }
            fis.close();
        }catch (Exception ex)
        {
            return null;
        }
        return strBuff.toString();
    }
}
