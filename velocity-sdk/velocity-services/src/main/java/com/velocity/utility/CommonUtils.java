package com.velocity.utility;

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;

import com.velocity.exceptions.VelocityException;
import com.velocity.exceptions.VelocityIllegalArgumentException;
import com.velocity.model.response.ArrayOfResponse;
import com.velocity.model.response.BankcardCaptureResponse;
import com.velocity.model.response.BankcardTransactionResponsePro;
import com.velocity.model.response.ErrorResponse;

/**
 * This class is a Utility class for Velocity processor.
 * 
 * @author anitk
 * @date 09-January-2015
 */
public class CommonUtils {
    private static final Logger LOG = Logger.getLogger(CommonUtils.class);

    /**
     * This method generates the BankcardTransaction Response
     * 
     * @author vimalk2
     * @param result
     *        - holds result for the BankcardTransaction Response
     * @return BankcardTransactionResponsePro - Instance of the type
     *         BankcardTransactionResponsePro
     * @throws VelocityIllegalArgumentException
     *         - Thrown When Illegal argument supplied.
     * @throws VelocityException
     *         - thrown when any exception occurs at generating the
     *         BankcardTransactionResponse
     */
    public static BankcardTransactionResponsePro generateBankcardTransactionResponsePro(String result) throws VelocityIllegalArgumentException, VelocityException {
        if(result == null || result.isEmpty()){
            throw new VelocityIllegalArgumentException("Input parameter can not be null or empty.");
        }
        try{
            /* Generating the Bankcard Transaction Response */
            JAXBContext jc = JAXBContext.newInstance(BankcardTransactionResponsePro.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            BankcardTransactionResponsePro respBankcardTransactionResponsePro = (BankcardTransactionResponsePro) unmarshaller.unmarshal(new ByteArrayInputStream(result.getBytes()));
            return respBankcardTransactionResponsePro;
        }catch (JAXBException ex){
            LOG.error("Error occured in creating BankcardTransactionResponsePro instance at CommonUtils:generateBankcardTransactionResponsePro() ", ex);
            throw new VelocityException("Exception occured in creating BankcardTransactionResponsePro instance.", ex);
        }
    }

    /**
     * This method generates the BankcardCaptureResponse
     * 
     * @author vimalk2
     * @param result
     *        - holds the result for the BankcardCaptureResponse
     * @return BankcardCaptureResponse - Instance of the type
     *         BankcardCaptureResponse
     * @throws VelocityIllegalArgumentException
     *         - Thrown When Illegal argument supplied.
     * @throws VelocityException
     *         - thrown when any exception occurs at generating the
     *         BankcardCaptureResponse
     */
    public static BankcardCaptureResponse generateBankcardCaptureResponse(String result) throws VelocityIllegalArgumentException, VelocityException {
        if(result == null || result.isEmpty()){
            throw new VelocityIllegalArgumentException("Input parameter can not be null or empty.");
        }
        try{
            /* Generating the BankcardCapture Transaction Response */
            JAXBContext jc = JAXBContext.newInstance(BankcardCaptureResponse.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            BankcardCaptureResponse respBankcardCaptureResponse = (BankcardCaptureResponse) unmarshaller.unmarshal(new ByteArrayInputStream(result.getBytes()));
            return respBankcardCaptureResponse;
        }catch (JAXBException ex){
            LOG.error("Exception occured in creating generateBankcardCaptureResponse instance at CommonUtils:generateBankcardCaptureResponse() ", ex);
            throw new VelocityException("Exception occured in creating generateBankcardCaptureResponse instance.", ex);
        }
    }

    /**
     * This method generates the ErrorResponse
     * 
     * @author vimalk2
     * @param result
     *        - holds the result for the ErrorResponse
     * @return ErrorResponse - Instance of the type ErrorResponse
     * @throws VelocityIllegalArgumentException
     *         - Thrown When Illegal argument supplied.
     * @throws VelocityException
     *         - thrown when any exception occurs in generating the
     *         ErrorResponse
     */
    public static ErrorResponse generateErrorResponse(String result) throws VelocityIllegalArgumentException, VelocityException {
        if(result == null || result.isEmpty()){
            throw new VelocityIllegalArgumentException("Input parameter can not be null or empty.");
        }
        try{
            /* Generating the Error Response for the Transaction performed. */
            JAXBContext jc = JAXBContext.newInstance(ErrorResponse.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            ErrorResponse errorResponse = (ErrorResponse) unmarshaller.unmarshal(new ByteArrayInputStream(result.getBytes()));
            return errorResponse;
        }catch (JAXBException ex){
            LOG.error("Exception occured in creating ErrorResponse instance at CommonUtils:generateErrorResponse() ", ex);
            throw new VelocityException("Exception occured in creating ErrorResponse instance.", ex);
        }
    }

    /* remove Invalid char */
    public static String removeInvalidChar(String str) {
        if(str == null || StringUtils.isEmpty(str)){
            return null;
        }
        return str.replace("\"", "");
    }

    public static ArrayOfResponse generateArrayOfResponse(String result) throws VelocityIllegalArgumentException, VelocityException {
        if(result == null || result.isEmpty()){
            throw new VelocityIllegalArgumentException("Input parameter can not be null or empty.");
        }
        try{
            /* Generating the Bankcard Transaction Response */
            JAXBContext jc = JAXBContext.newInstance(ArrayOfResponse.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            ArrayOfResponse respArrayOfResponse = (ArrayOfResponse) unmarshaller.unmarshal(new ByteArrayInputStream(result.getBytes()));
            return respArrayOfResponse;
        }catch (JAXBException ex){
            throw new VelocityException("Exception occured in creating ArrayOfResponse instance.", ex);
        }
    }

    /**
     * Instead of writing same code with each transaction where we verify
     * provided parameter to perform null pointer and empty checks we write this
     * method and call wherever needed.
     * 
     * @param str
     * @throws VelocityIllegalArgumentException
     */
    public static void checkNullStr(String str) throws VelocityIllegalArgumentException {
        if(str == null || str.isEmpty()){
            throw new VelocityIllegalArgumentException("request param cannot be null or empty.");
        }
    }

    /**
     * Method to check Null pointer exception
     * 
     * @param obj
     * @throws VelocityIllegalArgumentException
     */
    public static void checkNullObj(Object obj) throws VelocityIllegalArgumentException {
        if(obj == null){
            throw new VelocityIllegalArgumentException("Transaction information can not be null or empty.");
        }
    }
}
