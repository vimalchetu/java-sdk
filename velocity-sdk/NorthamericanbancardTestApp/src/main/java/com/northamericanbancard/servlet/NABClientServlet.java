package com.northamericanbancard.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.northamericanbancard.constants.NABConstants;
import com.velocity.enums.VelocityEnums;
import com.velocity.model.request.Adjust;
import com.velocity.model.request.AuthorizeAndCaptureTransaction;
import com.velocity.model.request.AuthorizeTransaction;
import com.velocity.model.request.CaptureAll;
import com.velocity.model.request.ReturnById;
import com.velocity.model.request.ReturnTransaction;
import com.velocity.model.request.Undo;
import com.velocity.model.request.capture.ChangeTransaction;
import com.velocity.model.response.VelocityResponse;
import com.velocity.models.transactions.query.QueryTransactionsDetail;
import com.velocity.transaction.processor.service.impl.VelocityProcessor;

/**
 * This class processes the incoming request from the Velocity sample TestApp
 * 
 * @author anitk
 * @date 19-January-2015
 */
public class NABClientServlet extends HttpServlet {

    private static final long serialVersionUID = -3346452918558323980L;
    private static final Logger LOG = Logger.getLogger(NABClientServlet.class);

    public void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        /* Set response content type */
        resp.setContentType("text/html");
        try{
            String identityToken = req.getParameter("identityToken");
            String workFlowId = req.getParameter("workFlowId");
            String appProfileId = req.getParameter("appProfileId");
            String merchantProfileId = req.getParameter("merchantProfileId");
            String enableTestMode = req.getParameter("enableTestMode");
            String txnName = req.getParameter("txn_name");
            String[] qtdTxns = req.getParameterValues("qtdTxns");
            String paymentAccountDataToken = null;
            VelocityResponse velocityResponse = null;
            HttpSession session = req.getSession(true);
            boolean isTestAccount = (enableTestMode != null) ? true : false;
            LOG.debug("isTestAccount >>>>>>>>>>>> " + isTestAccount);
            String sessionToken = null;
            if(session.getAttribute("sessionToken") != null)
            {
                sessionToken = (String) session.getAttribute("sessionToken");
            }
            /* Creating the VelocityProcessor object */
            VelocityProcessor velocityProcessor = new VelocityProcessor(sessionToken, identityToken, appProfileId, merchantProfileId, workFlowId, isTestAccount);
            LOG.debug("Session token >>>>>>>>>>>> " + velocityProcessor.getSessionToken());
            /* Setting the attribute to the session for encrypted SessionToken */
            session.setAttribute("sessionToken", velocityProcessor.getSessionToken());
            /* Setting the attribute to the session for identityToken */
            session.setAttribute("identityToken", identityToken);
          
            /* Invokes the Verify transaction from the VelocityTestApp */
            if(txnName != null && txnName.equalsIgnoreCase(NABConstants.VERIFY))
            {
                velocityResponse = velocityProcessor.verify(getVerifyRequestAuthorizeTransactionInstance(req));
                LOG.debug("velocityResponse >>>>>>>>>>>> " + velocityResponse);
                if(velocityResponse != null && velocityResponse.getBankcardTransactionResponse() != null)
                {
                    paymentAccountDataToken = velocityResponse.getBankcardTransactionResponse().getPaymentAccountDataToken();
                    session.setAttribute("paymentAccountDataToken", paymentAccountDataToken);
                }
                session.setAttribute("verifyRequestXML", velocityProcessor.getTxnRequestLog());
                session.setAttribute("verifyResponseXML", velocityProcessor.getTxnResponseLog());
                session.setAttribute("txnName", null);
                LOG.debug("VelocityResponse >>>>>>>>>> " + velocityResponse);
            }
            /* Invokes the Authorize transaction from the VelocityTestApp */
            else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.AUTHORIZE))
            {
                AuthorizeTransaction objAuthorizeTransaction = null;
                if(req.getParameter("securePaymentAccountData") == null || req.getParameter("securePaymentAccountData").trim().isEmpty())
                {
                    objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance(req);
                }
                else
                {
                    objAuthorizeTransaction = getP2PEAuthorizeRequestAuthorizeTransactionInstance(req);
                }
                velocityResponse = velocityProcessor.authorize(objAuthorizeTransaction);
                if(velocityResponse != null && velocityResponse.getBankcardTransactionResponse() != null)
                {
                    session.setAttribute("transactionId", velocityResponse.getBankcardTransactionResponse().getTransactionId());
                }
                if(session.getAttribute("txnList") == null)
                {
                    List<String> txnList = new ArrayList<String>();
                    if(velocityResponse != null && velocityResponse.getBankcardTransactionResponse() != null)
                    {
                        txnList.add(velocityResponse.getBankcardTransactionResponse().getTransactionId());
                    }
                    session.setAttribute("txnList", txnList);
                }
                else
                {
                    List<String> txnList = (List<String>) session.getAttribute("txnList");
                    if(velocityResponse != null && velocityResponse.getBankcardTransactionResponse() != null)
                    {
                        txnList.add(velocityResponse.getBankcardTransactionResponse().getTransactionId());
                    }
                    session.setAttribute("txnList", txnList);
                }
                session.setAttribute("txnName", "Authorize");
                LOG.debug("Authorize VelocityResponse >>>>>>>>>> " + velocityResponse);
            }
            /* Invokes the Authorize transaction without token from the VelocityTestApp */
            else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.AUTHORIZEWOTOKEN))
            {
                AuthorizeTransaction objAuthorizeTransaction = null;
                if(req.getParameter("securePaymentAccountData") == null || req.getParameter("securePaymentAccountData").trim().isEmpty())
                {
                    objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance(req);
                }
                else
                {
                    objAuthorizeTransaction = getP2PEAuthorizeRequestAuthorizeTransactionInstance(req);
                }
                objAuthorizeTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setValue("");
                velocityResponse = velocityProcessor.authorize(objAuthorizeTransaction);
                if(velocityResponse.getBankcardTransactionResponse() != null)
                {
                    session.setAttribute("transactionId", velocityResponse.getBankcardTransactionResponse().getTransactionId());
                }
                if(session.getAttribute("txnList") == null)
                {
                    List<String> txnList = new ArrayList<String>();
                    txnList.add(velocityResponse.getBankcardTransactionResponse().getTransactionId());
                    session.setAttribute("txnList", txnList);
                }
                else
                {
                    List<String> txnList = (List<String>) session.getAttribute("txnList");
                    txnList.add(velocityResponse.getBankcardTransactionResponse().getTransactionId());
                    session.setAttribute("txnList", txnList);
                }
                LOG.debug("AuthorizeWotoken VelocityResponse >>>>>>>>>> " + velocityResponse);
                session.setAttribute("txnName", "AuthorizeWithoutToken");
            }
            /* Invokes the AuthorizeAndCapture transaction from the VelocityTestApp */
            else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.AUTHORIZEANDCAPTURE))
            {
                AuthorizeAndCaptureTransaction objAuthorizeAndCaptureTransaction = null;
                if(req.getParameter("securePaymentAccountData") == null || req.getParameter("securePaymentAccountData").trim().isEmpty())
                {
                    objAuthorizeAndCaptureTransaction = getAuthorizeAndCaptureTransactionInstance(req);
                }
                else
                {
                    objAuthorizeAndCaptureTransaction = getP2PEAuthorizeAndCaptureRequestAuthorizeTransactionInstance(req);
                }
                velocityResponse = velocityProcessor.authorizeAndCapture(objAuthorizeAndCaptureTransaction);
                session.setAttribute("txnName", "AuthorizeAndCapture");
                LOG.debug("AuthAndCapture VelocityResponse >>>>>>>>>> " + velocityResponse);
            }
            /* Invokes the AUthorizeAndCapture transaction without token from the VelocityTestApp */
            else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.AUTHORIZEANDCAPTUREWOTOKEN))
            {
                AuthorizeAndCaptureTransaction objAuthorizeAndCaptureTransaction = null;
                if(req.getParameter("securePaymentAccountData") == null || req.getParameter("securePaymentAccountData").trim().isEmpty())
                {
                    objAuthorizeAndCaptureTransaction = getAuthorizeAndCaptureTransactionInstance(req);
                }
                else
                {
                    objAuthorizeAndCaptureTransaction = getP2PEAuthorizeAndCaptureRequestAuthorizeTransactionInstance(req);
                }
                objAuthorizeAndCaptureTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setValue("");
                velocityResponse = velocityProcessor.authorizeAndCapture(objAuthorizeAndCaptureTransaction);
                session.setAttribute("txnName", "AuthorizeAndCaptureWithoutToken");
                LOG.debug("AuthAndCaptureWoToken VelocityResponse >>>>>>>>>> " + velocityResponse);
            }
            /* Invokes the Capture transaction from the VelocityTestApp */
            else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.CAPTURE))
            {
                AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance(req);
                VelocityResponse objVelocityResponse = velocityProcessor.authorize(objAuthorizeTransaction);
                ChangeTransaction objChangeTransaction = getCaptureTransactionInstance(req);
                objChangeTransaction.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardTransactionResponse().getTransactionId());
                velocityResponse = velocityProcessor.capture(objChangeTransaction);
              
                session.setAttribute("txnName", "Capture");
                LOG.debug("capture VelocityResponse >>>>>>>>>> " + velocityResponse);
            }
            /* Invokes the Undo transaction from the VelocityTestApp */
            else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.UNDO))
            {
                AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance(req);
                VelocityResponse objVelocityResponse = velocityProcessor.authorize(objAuthorizeTransaction);
                Undo objUndo = getUndoTransactionInstance(req);
                objUndo.setTransactionId(objVelocityResponse.getBankcardTransactionResponse().getTransactionId());
                velocityResponse = velocityProcessor.undo(objUndo);
                LOG.debug("undo VelocityResponse >>>>>>>>>> " + velocityResponse);
                session.setAttribute("txnName", "Undo");
            }
            /* Invokes the Adjust transaction from the VelocityTestApp */
            else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.ADJUST))
            {
                AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance(req);
                VelocityResponse objVelocityResponse = velocityProcessor.authorize(objAuthorizeTransaction);
             
                if(objVelocityResponse.getBankcardTransactionResponse() != null)
                {
                    ChangeTransaction objChangeTransaction = getCaptureTransactionInstance(req);
                    objChangeTransaction.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardTransactionResponse().getTransactionId());
                
                    objVelocityResponse = velocityProcessor.capture(objChangeTransaction);
                    if(objVelocityResponse.getBankcardCaptureResponse() != null)
                    {
                        Adjust objAdjust = getAdjustTransactionInstance(req);
                        objAdjust.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardCaptureResponse().getTransactionId());
                        velocityResponse = velocityProcessor.adjust(objAdjust);
                    }
                
                }               
               
                LOG.debug("adjust VelocityResponse >>>>>>>>>> " + velocityResponse);
                session.setAttribute("txnName", "Adjust");
            }
            /* Invokes the ReturnById transaction from the VelocityTestApp */
            else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.RETURNBYID))
            {
                AuthorizeTransaction objAuthorizeTransaction = getAuthorizeRequestAuthorizeTransactionInstance(req);
                VelocityResponse objVelocityResponse = velocityProcessor.authorize(objAuthorizeTransaction);
                
                if(objVelocityResponse.getBankcardTransactionResponse() != null)
                {
                    ChangeTransaction objChangeTransaction = getCaptureTransactionInstance(req);
                    objChangeTransaction.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardTransactionResponse().getTransactionId());
                    objVelocityResponse = velocityProcessor.capture(objChangeTransaction);
                    if(objVelocityResponse.getBankcardCaptureResponse() != null)
                    {
                        ReturnById objReturnById = getReturnByIdTransactionInstance(req);
                        objReturnById.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardCaptureResponse().getTransactionId());
                        velocityResponse = velocityProcessor.returnById(objReturnById);
                    }
                }
                
                LOG.debug("invokeReturnByIdRequest VelocityResponse >>>>>>>>>> " + velocityResponse);
                session.setAttribute("txnName", "ReturnById");
            }
            /* Invokes the ReturnUnLinked transaction from the VelocityTestApp */
            else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.RETURNUNLINKED))
            {
                ReturnTransaction objreturnUnlinkedTransaction = null;
                if(req.getParameter("securePaymentAccountData") == null || req.getParameter("securePaymentAccountData").trim().isEmpty())
                {
                    objreturnUnlinkedTransaction = getReturnTransactionInstance(req);
                }
                else
                {
                    objreturnUnlinkedTransaction = getP2PEReturnTransactionInstance(req);
                }
                velocityResponse = velocityProcessor.returnUnlinked(objreturnUnlinkedTransaction);
                if(velocityResponse.getBankcardTransactionResponse() != null)
                {
                    session.setAttribute("transactionId", velocityResponse.getBankcardTransactionResponse().getTransactionId());
                }
                session.setAttribute("txnName", "ReturnUnlinked");
                LOG.debug("ReturnUnlinkedRequest VelocityResponse >>>>>>>>>> " + velocityResponse);
            }
            /* Invokes the ReturnUnlinked transaction without token from the VelocityTestApp */
            else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.RETURNUNLINKEDWOTOKEN))
            {
                ReturnTransaction objreturnUnlinkedTransaction = null;
                if(req.getParameter("securePaymentAccountData") == null || req.getParameter("securePaymentAccountData").trim().isEmpty())
                {
                    objreturnUnlinkedTransaction = getReturnTransactionInstance(req);
                }
                else
                {
                    objreturnUnlinkedTransaction = getP2PEReturnTransactionInstance(req);
                }
                objreturnUnlinkedTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setValue("");
                velocityResponse = velocityProcessor.returnUnlinked(objreturnUnlinkedTransaction);
                if(velocityResponse.getBankcardTransactionResponse() != null)
                {
                    session.setAttribute("transactionId", velocityResponse.getBankcardTransactionResponse().getTransactionId());
                }
                session.setAttribute("txnName", "ReturnUnlinkedWithoutToken");
                LOG.debug("ReturnUnlinkedWoToken VelocityResponse >>>>>>>>>> " + velocityResponse);
            }
            /* Invokes the QueryTransactionsDetail from the VelocityTestApp */
            else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.QUERYTRANSACTIONDETAIL))
            {
                session.setAttribute("txnName", "QueryTransactionsDetail");
                QueryTransactionsDetail objQueryTransactionsDetail = getQueryTransactionsDetailInstance(req);
                if(qtdTxns != null && qtdTxns.length > 0)
                {
                    LOG.debug("InvokeQueryTransactionDetailRequest Query Transactionssssss >>>>>> " + qtdTxns);
                    for(String qtd : qtdTxns)
                    {
                        objQueryTransactionsDetail.getQueryTransactionsParameters().getTransactionIds().add(qtd.trim());
                    }
                }
                velocityResponse = velocityProcessor.queryTransactionsDetail(objQueryTransactionsDetail);
                if(velocityResponse.getTransactionDetailList() != null)
                {
                    LOG.debug("InvokeQueryTransactionDetailRequest TransactionDetailList size >>>>>> " + velocityResponse.getTransactionDetailList().size());
                    req.setAttribute("transactionDetailList", velocityResponse.getTransactionDetailList());
                }
                else if(velocityResponse.getJsonErrorResponse() != null)
                {}
            }
          
            /* Invokes the CaptureAll from the VelocityTestApp */
            else if(txnName != null && txnName.equalsIgnoreCase(NABConstants.CAPTURE_ALL))
            {
                CaptureAll objCaptureAll = getCaptureAllInstance(req);
                velocityProcessor.signOn(identityToken);
                velocityResponse = velocityProcessor.captureAll(objCaptureAll);
                if(velocityResponse.getArrayOfResponse() != null)
                {
                    session.setAttribute("transactionId", velocityResponse.getArrayOfResponse().getResponse().getTransactionId());
                }
                session.setAttribute("txnName", "captureAll");
                LOG.debug("captureAll VelocityResponse >>>>>>>>>> " + velocityResponse);
            }
            LOG.debug("WorkFlowId >>>>>>>>>>>> " + workFlowId);
            session.setAttribute("velocityProcessor", velocityProcessor);
            session.setAttribute("velocityResponse", velocityResponse);
            req.setAttribute("statusCode", velocityResponse.getStatusCode());
            req.setAttribute("message", velocityResponse.getMessage());
        }catch (Exception e){
            LOG.error("service", e);
        }
        RequestDispatcher view = req.getRequestDispatcher("result.jsp");
        view.forward(req, resp);
    }
    /**
     * Generating the Verify input AuthorizeTransaction instance from the User inputs.
     * 
     * @author Vimal Kumar
     * @param -req of the type HttpServlet
     * @return- of the type authorizeTransaction.
     */
    AuthorizeTransaction getVerifyRequestAuthorizeTransactionInstance(HttpServletRequest req)
    {
        /* Creating the object for AuthorizeTransaction */
        AuthorizeTransaction authorizeTransaction = new AuthorizeTransaction();
        /* Setting the values for Authorize XML */
        authorizeTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
        authorizeTransaction.getTransaction().getTenderData().getCardData().setCardType(req.getParameter("cardtype"));
        authorizeTransaction.getTransaction().getTenderData().getCardData().setCardholderName(req.getParameter("cardHolderName"));
        authorizeTransaction.getTransaction().getTenderData().getCardData().setPanNumber(req.getParameter("panNumber"));
        authorizeTransaction.getTransaction().getTenderData().getCardData().setExpiryDate(req.getParameter("month") + req.getParameter("year"));
        authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().setNillable(true);
        authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCardholderName().setNillable(true);
        authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setStreet(req.getParameter("street"));
        authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setCity(req.getParameter("city"));
        authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setStateProvince(req.getParameter("state"));
        authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setPostalCode(req.getParameter("zip"));
        authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setPhone(req.getParameter("phone"));
        authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getEmail().setValue(req.getParameter("email"));
        authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().setCvDataProvided("Provided");
        authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getCvData().setValue(req.getParameter("cvc"));;
        authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getKeySerialNumber().setNillable(true);
        authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getPin().setNillable(true);
        authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().setNillable(true);
        authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);
        authorizeTransaction.getTransaction().getTransactionData().setAmount(req.getParameter("amount"));
        authorizeTransaction.getTransaction().getTransactionData().setCurrencyCode(req.getParameter("currencyCode"));
        authorizeTransaction.getTransaction().getTransactionData().setTransactiondateTime(req.getParameter("transactionDateTime"));
        authorizeTransaction.getTransaction().getTransactionData().setAccountType(req.getParameter("accountType"));
        authorizeTransaction.getTransaction().getTransactionData().setCustomerPresent(req.getParameter("industryType"));
        authorizeTransaction.getTransaction().getTransactionData().setEmployeeId(req.getParameter("empID"));
        authorizeTransaction.getTransaction().getTransactionData().setEntryMode(req.getParameter("entryMode"));
        authorizeTransaction.getTransaction().getTransactionData().setIndustryType(req.getParameter("industryType"));
        authorizeTransaction.getTransaction().getTransactionData().setInvoiceNumber("802");
        authorizeTransaction.getTransaction().getTransactionData().setOrderNumber("629203");
        authorizeTransaction.getTransaction().getTransactionData().setTipAmount(req.getParameter("tipAmount"));
        return authorizeTransaction;
    }
    /**
     * Generating the Authorize input AuthorizeTransaction instance from the User inputs.
     * 
     * @author Vimal Kumar
     * @param req - of the type HttpServletRequest
     * @return - of the type AuthorizeTransaction
     */
    AuthorizeTransaction getAuthorizeRequestAuthorizeTransactionInstance(HttpServletRequest req)
    {
        HttpSession session = req.getSession(false);
        /* Creating the object for AuthorizeTransaction */
        AuthorizeTransaction authorizeTransaction = new AuthorizeTransaction();
        authorizeTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
        /* Setting the values for Authorize XML */
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().getName().setNillable(true);
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStreet1(req.getParameter("street"));
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().setNillable(true);
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCity(req.getParameter("city"));
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStateProvince(req.getParameter("state"));
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setPostalCode(req.getParameter("zip"));
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCountryCode("USA");
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().setBusinessName(req.getParameter("businessName"));
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().getPhone().setNillable(true);
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().getFax().setNillable(true);
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().getEmail().setNillable(true);
        authorizeTransaction.getTransaction().getCustomerData().setCustomerId(req.getParameter("empID"));
        authorizeTransaction.getTransaction().getCustomerData().getCustomerTaxId().setNillable(true);
        authorizeTransaction.getTransaction().getCustomerData().getShippingData().setNillable(true);
        authorizeTransaction.getTransaction().getReportingData().setComment(req.getParameter("comment"));
        authorizeTransaction.getTransaction().getReportingData().setDescription(req.getParameter("description"));
        authorizeTransaction.getTransaction().getReportingData().setReference(req.getParameter("reference"));
        if(session.getAttribute("paymentAccount" + "") != null)
        {
            LOG.debug("getAuthorizeRequestAuthorizeTransactionInstance paymentAccountDataToken == " + (String) session.getAttribute("paymentAccountDataToken"));
            authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setValue((String) session.getAttribute("paymentAccountDataToken"));
        }
        else
        {
            authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setValue("");
        }
        authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setNillable(true);
        authorizeTransaction.getTransaction().getTenderData().getEncryptionKeyId().setNillable(true);
        authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().setNillable(true);
        authorizeTransaction.getTransaction().getTenderData().getCardData().setCardType(req.getParameter("cardtype"));
        authorizeTransaction.getTransaction().getTenderData().getCardData().setPan(req.getParameter("panNumber"));
        authorizeTransaction.getTransaction().getTenderData().getCardData().setExpiryDate(req.getParameter("month") + req.getParameter("year"));
        authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().setNillable(true);
        authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);
        authorizeTransaction.getTransaction().getTransactionData().setAmount(req.getParameter("amount"));
        authorizeTransaction.getTransaction().getTransactionData().setCurrencyCode(req.getParameter("currencyCode"));
        authorizeTransaction.getTransaction().getTransactionData().setTransactionDateTime(req.getParameter("transactionDateTime"));
        authorizeTransaction.getTransaction().getTransactionData().getCampaignId().setNillable(true);
        authorizeTransaction.getTransaction().getTransactionData().setReference(req.getParameter("reference"));
        authorizeTransaction.getTransaction().getTransactionData().setAccountType(req.getParameter("accountType"));
        authorizeTransaction.getTransaction().getTransactionData().getApprovalCode().setNillable(true);
        authorizeTransaction.getTransaction().getTransactionData().setCashBackAmount("0.0");
        authorizeTransaction.getTransaction().getTransactionData().setCustomerPresent("Present");
        authorizeTransaction.getTransaction().getTransactionData().setEmployeeId(req.getParameter("empID"));
        authorizeTransaction.getTransaction().getTransactionData().setEntryMode(req.getParameter("entryMode"));
        authorizeTransaction.getTransaction().getTransactionData().setGoodsType(req.getParameter("goodsType"));
        authorizeTransaction.getTransaction().getTransactionData().setIndustryType(req.getParameter("industryType"));
        authorizeTransaction.getTransaction().getTransactionData().getInternetTransactionData().setNillable(true);
        authorizeTransaction.getTransaction().getTransactionData().setInvoiceNumber("");
        authorizeTransaction.getTransaction().getTransactionData().setOrderNumber("629203");
        authorizeTransaction.getTransaction().getTransactionData().setPartialShipment(false);
        authorizeTransaction.getTransaction().getTransactionData().setSignatureCaptured(false);
        authorizeTransaction.getTransaction().getTransactionData().setFeeAmount("1000.05");
        authorizeTransaction.getTransaction().getTransactionData().getTerminalId().setNillable(true);
        authorizeTransaction.getTransaction().getTransactionData().getLaneId().setNillable(true);
        authorizeTransaction.getTransaction().getTransactionData().setTipAmount(req.getParameter("tipAmount"));
        authorizeTransaction.getTransaction().getTransactionData().getBatchAssignment().setNillable(true);
        authorizeTransaction.getTransaction().getTransactionData().setPartialApprovalCapable("NotSet");
        authorizeTransaction.getTransaction().getTransactionData().getScoreThreshold().setNillable(true);
        authorizeTransaction.getTransaction().getTransactionData().setQuasiCash(false);
        return authorizeTransaction;
    }
    /**
     * Generating the Authorize input AuthorizeTransaction instance from the User inputs.
     * 
     * @author Vimal Kumar
     * @param req - of the type HttpServletRequest
     * @return - of the type AuthorizeAndCaptureTransaction
     */
    AuthorizeAndCaptureTransaction getAuthorizeAndCaptureTransactionInstance(HttpServletRequest req)
    {
        HttpSession session = req.getSession(false);
        /* Creating the object for AuthorizeAndCaptureTransaction */
        AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction = new AuthorizeAndCaptureTransaction();
        /* Setting the values for AuthorizeAndCapture XML */
        authorizeAndCaptureTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getName().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStreet1(req.getParameter("street"));
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCity(req.getParameter("city"));
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStateProvince(req.getParameter("state"));
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setPostalCode(req.getParameter("zip"));
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCountryCode(req.getParameter("country"));
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().setBusinessName(req.getParameter("businessName"));
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getPhone().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getFax().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getEmail().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().setCustomerId(req.getParameter("empID"));
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getCustomerTaxId().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getShippingData().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getReportingData().setComment(req.getParameter("comment"));
        authorizeAndCaptureTransaction.getTransaction().getReportingData().setDescription(req.getParameter("description"));
        authorizeAndCaptureTransaction.getTransaction().getReportingData().setReference(req.getParameter("reference"));
        if(session.getAttribute("paymentAccountDataToken") != null)
        {
            LOG.debug("getAuthorizeAndCaptureTransactionInstance paymentAccountDataToken == " + (String) session.getAttribute("paymentAccountDataToken"));
            authorizeAndCaptureTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setValue(((String) session.getAttribute("paymentAccountDataToken")));
        }
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getEncryptionKeyId().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getSwipeStatus().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().setCardType(req.getParameter("cardtype"));
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().setPan(req.getParameter("panNumber"));
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardData().setExpiryDate(req.getParameter("month") + req.getParameter("year"));
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setAmount(req.getParameter("amount"));
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setCurrencyCode(req.getParameter("currencyCode"));
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setTransactionDateTime(req.getParameter("transactionDateTime"));
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().getCampaignId().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setReference("xyt");
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setAccountType(req.getParameter("accountType"));
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().getApprovalCode().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setCashBackAmount("0.0");
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setCustomerPresent("Present");
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setEmployeeId(req.getParameter("empID"));
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setEntryMode(req.getParameter("entryMode"));
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setGoodsType(req.getParameter("goodsType"));
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setIndustryType(req.getParameter("industryType"));
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().getInternetTransactionData().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setInvoiceNumber("");
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setOrderNumber("629203");
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setPartialShipment(false);
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setSignatureCaptured(false);
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setFeeAmount("0.0");
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().getTerminalId().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().getLaneId().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setTipAmount(req.getParameter("tipAmount"));
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().getBatchAssignment().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setPartialApprovalCapable("NotSet");
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().getScoreThreshold().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setQuasiCash(false);
        return authorizeAndCaptureTransaction;
    }
    /**
     * Generating the Capture instance from the User inputs.
     * 
     * @author Vimal Kumar
     * @param req - of the type HttpServletRequest
     * @return - of the type ChangeTransaction
     */
    ChangeTransaction getCaptureTransactionInstance(HttpServletRequest req)
    {
        /* Creating object for Capture Transaction */
        ChangeTransaction captureTransaction = new ChangeTransaction();
        /* Setting the values for Capture XML */
        captureTransaction.getDifferenceData().setType(VelocityEnums.BankcardCapture);
        captureTransaction.getDifferenceData().setAmount(req.getParameter("amount"));
        captureTransaction.getDifferenceData().setTipAmount(req.getParameter("tipAmount"));
        return captureTransaction;
    }
    /**
     * Generating the Void(Undo) instance from the User inputs.
     * 
     * @author Vimal Kumar
     * @param req - of the type HttpServletRequest
     * @return - of the type Undo
     */
    Undo getUndoTransactionInstance(HttpServletRequest req) {
        /* Creating object for UndoTransaction */
        Undo undoTransaction = new Undo();
        /* Setting the values for Undo XML */
        undoTransaction.setType(VelocityEnums.Undo);
        undoTransaction.getBatchIds().setNillable(true);
        undoTransaction.getDifferenceData().setNillable(true);
        return undoTransaction;
    }
    /**
     * Generating the Adjust instance from the User inputs.
     * 
     * @author Vimal Kumar
     * @param req - of the type HttpServletRequest
     * @return - of the type Adjust
     */
    Adjust getAdjustTransactionInstance(HttpServletRequest req) {
        /* Creating object for AdjustTransaction */
        Adjust adjustTransaction = new Adjust();
        /* Setting the values for Adjust XML */
        adjustTransaction.setType(VelocityEnums.Adjust);
        adjustTransaction.getBatchIds().setNillable(true);
        adjustTransaction.getDifferenceData().setAmount(req.getParameter("amount"));
        return adjustTransaction;
    }
    /**
     * Generating the Adjust instance from the User inputs.
     * 
     * @author Vimal Kumar
     * @param req - of the type HttpServletRequest
     * @return - of the type ReturnById
     */
    ReturnById getReturnByIdTransactionInstance(HttpServletRequest req) {
        ReturnById returnByIdTransaction = new ReturnById();
        /* Setting the values for ReturnById XML */
        returnByIdTransaction.setType(VelocityEnums.ReturnById);
        returnByIdTransaction.getBatchIds().setNillable(true);
        returnByIdTransaction.getDifferenceData().setAmount(req.getParameter("amount"));
        return returnByIdTransaction;
    }
    /**
     * Generating the ReturnUnlinked instance from the User inputs.
     * 
     * @author Vimal Kumar
     * @param req - of the type HttpServletRequest
     * @return - of the type ReturnTransaction
     */
    ReturnTransaction getReturnTransactionInstance(HttpServletRequest req)
    {
        HttpSession session = req.getSession(false);
        /* Creating object for ReturnUnlinkedTransaction */
        ReturnTransaction returnUnlinkedTransaction = new ReturnTransaction();
        /* Setting the values for ReturnUnlinked XML */
        returnUnlinkedTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
        returnUnlinkedTransaction.getBatchIds().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getName().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStreet1(req.getParameter("street"));
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCity(req.getParameter("city"));
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStateProvince(req.getParameter("state"));
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setPostalCode(req.getParameter("zip"));
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCountryCode(req.getParameter("country"));
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().setBusinessName(req.getParameter("businessName"));
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getPhone().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getFax().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getEmail().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getCustomerData().setCustomerId(req.getParameter("empID"));
        returnUnlinkedTransaction.getTransaction().getCustomerData().getCustomerTaxId().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getCustomerData().getShippingData().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getReportingData().setComment("a test comment");
        returnUnlinkedTransaction.getTransaction().getReportingData().setDescription(req.getParameter("description"));
        returnUnlinkedTransaction.getTransaction().getReportingData().setReference(req.getParameter("reference"));
        if(session.getAttribute("paymentAccountDataToken" + "") != null)
        {
            LOG.debug("getReturnTransactionInstance paymentAccountDataToken == " + (String) session.getAttribute("paymentAccountDataToken"));
            returnUnlinkedTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setValue(((String) session.getAttribute("paymentAccountDataToken")));
        }
        returnUnlinkedTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTenderData().getEncryptionKeyId().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTenderData().getSwipeStatus().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setCardType(req.getParameter("cardtype"));
        returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setPan(req.getParameter("panNumber"));
        returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setExpiryDate(req.getParameter("month") + req.getParameter("year"));
        returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTransactionData().setAmount(req.getParameter("amount"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().setCurrencyCode(req.getParameter("currencyCode"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().setTransactionDateTime(req.getParameter("transactionDateTime"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().getCampaignId().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTransactionData().setReference("xyt");
        returnUnlinkedTransaction.getTransaction().getTransactionData().setAccountType("NotSet");
        returnUnlinkedTransaction.getTransaction().getTransactionData().getApprovalCode().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTransactionData().setCashBackAmount("0.0");
        returnUnlinkedTransaction.getTransaction().getTransactionData().setCustomerPresent("Present");
        returnUnlinkedTransaction.getTransaction().getTransactionData().setEmployeeId(req.getParameter("empID"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().setEntryMode(req.getParameter("entryMode"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().setGoodsType(req.getParameter("goodsType"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().setIndustryType(req.getParameter("industryType"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().getInternetTransactionData().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTransactionData().setInvoiceNumber("802");
        returnUnlinkedTransaction.getTransaction().getTransactionData().setOrderNumber("629203");
        returnUnlinkedTransaction.getTransaction().getTransactionData().setPartialShipment(false);
        returnUnlinkedTransaction.getTransaction().getTransactionData().setSignatureCaptured(false);
        returnUnlinkedTransaction.getTransaction().getTransactionData().setFeeAmount("0.0");
        returnUnlinkedTransaction.getTransaction().getTransactionData().getTerminalId().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTransactionData().getLaneId().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTransactionData().setTipAmount(req.getParameter("tipAmount"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().getBatchAssignment().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTransactionData().setPartialApprovalCapable("NotSet");
        returnUnlinkedTransaction.getTransaction().getTransactionData().getScoreThreshold().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTransactionData().setQuasiCash(false);
        return returnUnlinkedTransaction;
    }
    /**
     * Generating the QueryTransactionsDetail instance from the User inputs.
     * 
     * @author Vimal Kumar
     * @param req - of the type HttpServletRequest
     * @return - instance of the type QueryTransactionsDetail
     */
    QueryTransactionsDetail getQueryTransactionsDetailInstance(HttpServletRequest req)
    {
        HttpSession session = req.getSession(false);
        /* Creating object for QueryTransactionsDetail */
        QueryTransactionsDetail queryTransactionsDetail = new QueryTransactionsDetail();
        queryTransactionsDetail.setTransactionDetailFormat(com.velocity.enums.TransactionDetailFormat.CWSTransaction);
        queryTransactionsDetail.getPagingParameters().setPage(0);
        queryTransactionsDetail.getPagingParameters().setPageSize(3);
        return queryTransactionsDetail;
    }
    
    AuthorizeTransaction getP2PEAuthorizeRequestAuthorizeTransactionInstance(HttpServletRequest req)
    {
        AuthorizeTransaction authorizeTransaction = new AuthorizeTransaction();
        // Setting the values for Authorize XML
        authorizeTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStreet1(req.getParameter("street1"));
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCity(req.getParameter("city"));
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStateProvince(req.getParameter("stateProvince"));
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setPostalCode(req.getParameter("zip"));
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCountryCode(req.getParameter("country"));
        authorizeTransaction.getTransaction().getCustomerData().getBillingData().setBusinessName(req.getParameter("businessName"));
        authorizeTransaction.getTransaction().getCustomerData().setCustomerId(req.getParameter("empID"));
        authorizeTransaction.getTransaction().getReportingData().setComment(req.getParameter("comment"));
        authorizeTransaction.getTransaction().getReportingData().setDescription(req.getParameter("description"));
        authorizeTransaction.getTransaction().getReportingData().setReference(req.getParameter("reference"));
        authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setNillable(true);
        authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setValue(req.getParameter("securePaymentAccountData"));
        authorizeTransaction.getTransaction().getTenderData().getEncryptionKeyId().setValue(req.getParameter("encryptionKeyId"));
        authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().setValue(req.getParameter("swipeStatus"));
        authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().setValue((req.getParameter("identificationInformation")));
        authorizeTransaction.getTransaction().getTransactionData().setAmount(req.getParameter("amount"));
        authorizeTransaction.getTransaction().getTransactionData().setCurrencyCode(req.getParameter("currencyCode"));
        authorizeTransaction.getTransaction().getTransactionData().setTransactionDateTime(req.getParameter("transactionDateTime"));
        authorizeTransaction.getTransaction().getTransactionData().setReference("xyt");
        authorizeTransaction.getTransaction().getTransactionData().setEmployeeId(req.getParameter("empID"));
        authorizeTransaction.getTransaction().getTransactionData().setEntryMode(req.getParameter("entryMode"));
        authorizeTransaction.getTransaction().getTransactionData().setGoodsType(req.getParameter("goodsType"));
        authorizeTransaction.getTransaction().getTransactionData().setIndustryType(req.getParameter("industryType"));
        authorizeTransaction.getTransaction().getTransactionData().setInvoiceNumber("");
        return authorizeTransaction;
    }
    /**
     * This method prepares the CaptureAll instance for CaptureAllTransaction
     * 
     * @author Vimal Kumar The method sets the values for CaptureAll XML elements.
     * @return of the type captureTransaction
     */
    CaptureAll getCaptureAllInstance(HttpServletRequest req) {
        CaptureAll captureAllTransaction = new CaptureAll();
        captureAllTransaction.getBatchIds().setNillable(true);
        captureAllTransaction.getDifferenceData().setNillable(true);
        captureAllTransaction.getDifferenceData().setNillable(true);
        return captureAllTransaction;
    }
    AuthorizeAndCaptureTransaction getP2PEAuthorizeAndCaptureRequestAuthorizeTransactionInstance(HttpServletRequest req)
    {
        AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction = new AuthorizeAndCaptureTransaction();
        // Setting the values for AuthorizeAndCapture XML
        authorizeAndCaptureTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getName().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStreet1(req.getParameter("street1"));
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCity(req.getParameter("city"));
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStateProvince(req.getParameter("stateProvince"));
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setPostalCode(req.getParameter("zip"));
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCountryCode(req.getParameter("country"));
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().setBusinessName(req.getParameter("businessName"));
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getPhone().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getFax().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getBillingData().getEmail().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().setCustomerId(req.getParameter("empID"));
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getCustomerTaxId().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getCustomerData().getShippingData().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getReportingData().setComment(req.getParameter("comment"));
        authorizeAndCaptureTransaction.getTransaction().getReportingData().setDescription(req.getParameter("description"));
        authorizeAndCaptureTransaction.getTransaction().getReportingData().setReference(req.getParameter("reference"));
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setValue(req.getParameter("securePaymentAccountData"));
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getEncryptionKeyId().setValue(req.getParameter("encryptionKeyId"));
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getSwipeStatus().setValue(req.getParameter("swipeStatus"));
        authorizeAndCaptureTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().setValue((req.getParameter("identificationInformation")));
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setAmount(req.getParameter("amount"));
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setCurrencyCode(req.getParameter("currencyCode"));
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setTransactionDateTime(req.getParameter("transactionDateTime"));
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().getCampaignId().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setReference("xyt");
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setAccountType("NotSet");
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().getApprovalCode().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setCashBackAmount("0.0");
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setCustomerPresent("Present");
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setEmployeeId(req.getParameter("empID"));
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setEntryMode(req.getParameter("entryMode"));
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setGoodsType(req.getParameter("goodsType"));
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setIndustryType(req.getParameter("industryType"));
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().getInternetTransactionData().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setInvoiceNumber("802");
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setOrderNumber("629203");
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setPartialShipment(false);
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setSignatureCaptured(false);
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setFeeAmount("0.0");
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().getTerminalId().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().getLaneId().setNillable(true);
        authorizeAndCaptureTransaction.getTransaction().getTransactionData().setTipAmount(req.getParameter("tipAmount"));
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
    ReturnTransaction getP2PEReturnTransactionInstance(HttpServletRequest req)
    {
        ReturnTransaction returnUnlinkedTransaction = new ReturnTransaction();
        /* Setting the values for ReturnUnlinked XML */
        returnUnlinkedTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
        returnUnlinkedTransaction.getBatchIds().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getName().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStreet1(req.getParameter("street1"));
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCity(req.getParameter("city"));
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStateProvince(req.getParameter("stateProvince"));
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setPostalCode(req.getParameter("zip"));
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCountryCode(req.getParameter("country"));
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().setBusinessName(req.getParameter("businessName"));
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getPhone().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getFax().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getEmail().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getCustomerData().setCustomerId(req.getParameter("empID"));
        returnUnlinkedTransaction.getTransaction().getCustomerData().getCustomerTaxId().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getCustomerData().getShippingData().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getReportingData().setComment(req.getParameter("comment"));
        returnUnlinkedTransaction.getTransaction().getReportingData().setDescription(req.getParameter("description"));
        returnUnlinkedTransaction.getTransaction().getReportingData().setReference(req.getParameter("reference"));
        returnUnlinkedTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setValue(req.getParameter("securePaymentAccountData"));
        returnUnlinkedTransaction.getTransaction().getTenderData().getEncryptionKeyId().setValue(req.getParameter("encryptionKeyId"));
        returnUnlinkedTransaction.getTransaction().getTenderData().getSwipeStatus().setValue(req.getParameter("swipeStatus"));
        returnUnlinkedTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().setValue((req.getParameter("identificationInformation")));
        returnUnlinkedTransaction.getTransaction().getTransactionData().setAmount(req.getParameter("amount"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().setCurrencyCode(req.getParameter("currencyCode"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().setTransactionDateTime(req.getParameter("transactionDateTime"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().getCampaignId().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTransactionData().setReference(req.getParameter("reference"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().setAccountType(req.getParameter("accountType"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().getApprovalCode().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTransactionData().setCashBackAmount("0.0");
        returnUnlinkedTransaction.getTransaction().getTransactionData().setCustomerPresent("Present");
        returnUnlinkedTransaction.getTransaction().getTransactionData().setEmployeeId(req.getParameter("empID"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().setEntryMode(req.getParameter("entryMode"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().setGoodsType(req.getParameter("goodsType"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().setIndustryType(req.getParameter("industryType"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().getInternetTransactionData().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTransactionData().setInvoiceNumber("802");
        returnUnlinkedTransaction.getTransaction().getTransactionData().setOrderNumber("629203");
        returnUnlinkedTransaction.getTransaction().getTransactionData().setPartialShipment(false);
        returnUnlinkedTransaction.getTransaction().getTransactionData().setSignatureCaptured(false);
        returnUnlinkedTransaction.getTransaction().getTransactionData().setFeeAmount("0.0");
        returnUnlinkedTransaction.getTransaction().getTransactionData().getTerminalId().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTransactionData().getLaneId().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTransactionData().setTipAmount(req.getParameter("tipAmount"));
        returnUnlinkedTransaction.getTransaction().getTransactionData().getBatchAssignment().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTransactionData().setPartialApprovalCapable("NotSet");
        returnUnlinkedTransaction.getTransaction().getTransactionData().getScoreThreshold().setNillable(true);
        returnUnlinkedTransaction.getTransaction().getTransactionData().setQuasiCash(false);
        return returnUnlinkedTransaction;
    }
    
}
