/**
 * 
 */
package com.velocity.utility;

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.velocity.exceptions.VelocityGenericException;
import com.velocity.exceptions.VelocityIllegalArgument;
import com.velocity.models.response.BankcardCaptureResponse;
import com.velocity.models.response.BankcardTransactionResponsePro;
import com.velocity.models.response.ErrorResponse;

/**
 * This class is a Utility class for Velocity processor.
 * 
 * @author anitk
 * @date 09-January-2015
 */

public class CommonUtils {

	/**
	 * This method generates the BankcardTransaction Response
	 * @author vimalk2
	 * @param result - holds result for the BankcardTransaction Response
	 * @return BankcardTransactionResponsePro - Instance of the type BankcardTransactionResponsePro
	 * @throws VelocityIllegalArgument - Thrown When Illegal argument supplied.
	 * @throws VelocityGenericException - thrown when any exception occurs at generating the BankcardTransactionResponse
	 */
	public static BankcardTransactionResponsePro generateBankcardTransactionResponsePro(String result) throws VelocityIllegalArgument, VelocityGenericException
	{
		AppLogger.logDebug(CommonUtils.class, "generateBankcardTransactionResponsePro", "Entering...");
		
		if(result == null || result.isEmpty())
		{
			throw new VelocityIllegalArgument("Input parameter can not be null or empty.");
		}
		
		try {
			/* Generating the Bankcard Transaction Response*/
		JAXBContext jc = JAXBContext.newInstance(BankcardTransactionResponsePro.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller(); 
		BankcardTransactionResponsePro respBankcardTransactionResponsePro = (BankcardTransactionResponsePro) unmarshaller.unmarshal(new ByteArrayInputStream(result.getBytes()));
		AppLogger.logDebug(CommonUtils.class, "generateBankcardTransactionResponsePro", "Exiting..."+respBankcardTransactionResponsePro);
		return respBankcardTransactionResponsePro;
		} catch(JAXBException ex)
		{
			AppLogger.logError(CommonUtils.class, "generateBankcardTransactionResponsePro", ex);
			throw new VelocityGenericException("Exception occured in creating BankcardTransactionResponsePro instance.", ex);
		}
	}
	
	/**
	 * This method generates the BankcardCaptureResponse
	 * @author vimalk2
	 * @param  result- holds the result for the BankcardCaptureResponse
	 * @return BankcardCaptureResponse - Instance of the type BankcardCaptureResponse
	 * @throws VelocityIllegalArgument - Thrown When Illegal argument supplied.
	 * @throws VelocityGenericException - thrown when any exception occurs at generating the BankcardCaptureResponse
	 */
	public static BankcardCaptureResponse generateBankcardCaptureResponse(String result) throws VelocityIllegalArgument, VelocityGenericException
	{
		AppLogger.logDebug(CommonUtils.class, "generateBankcardCaptureResponse", "Entering...");
		
		if(result == null || result.isEmpty())
		{
			throw new VelocityIllegalArgument("Input parameter can not be null or empty.");
		}
		
		try {
			/* Generating the BankcardCapture Transaction Response*/
		JAXBContext jc = JAXBContext.newInstance(BankcardCaptureResponse.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller(); 
		BankcardCaptureResponse respBankcardCaptureResponse = (BankcardCaptureResponse) unmarshaller.unmarshal(new ByteArrayInputStream(result.getBytes()));
		AppLogger.logDebug(CommonUtils.class, "generateBankcardCaptureResponse", "Exiting..."+respBankcardCaptureResponse);
		return respBankcardCaptureResponse;
		} catch(JAXBException ex)
		{
			AppLogger.logError(CommonUtils.class, "generateBankcardCaptureResponse", ex);
			throw new VelocityGenericException("Exception occured in creating generateBankcardCaptureResponse instance.", ex);
		}
	}
	
	
	/**
	 * This method generates the ErrorResponse
	 * @author vimalk2
	 * @param  result- holds the result for the ErrorResponse
	 * @return ErrorResponse - Instance of the type ErrorResponse
	 * @throws VelocityIllegalArgument - Thrown When Illegal argument supplied.
	 * @throws VelocityGenericException - thrown when any exception occurs in generating the ErrorResponse
	 */
	public static ErrorResponse generateErrorResponse(String result) throws VelocityIllegalArgument, VelocityGenericException
	{
		AppLogger.logDebug(CommonUtils.class, "generateErrorResponse", "Entering...");

		if(result == null || result.isEmpty())
		{
			throw new VelocityIllegalArgument("Input parameter can not be null or empty.");
		}
		
		try {
			/* Generating the Error Response for the Transaction performed.*/
			JAXBContext jc = JAXBContext.newInstance(ErrorResponse.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller(); 
			ErrorResponse errorResponse = (ErrorResponse) unmarshaller.unmarshal(new ByteArrayInputStream(result.getBytes()));
			AppLogger.logDebug(CommonUtils.class, "generateErrorResponse", "Exiting..."+errorResponse);
			return errorResponse;
		} catch(JAXBException ex)
		{
			AppLogger.logError(CommonUtils.class, "generateErrorResponse", ex);
			throw new VelocityGenericException("Exception occured in creating ErrorResponse instance.", ex);
		}
	}


}
