/**
 * 
 */
package com.velocity.utility;

import org.apache.log4j.Level; 
import org.apache.log4j.Logger;


/**
 * Description: This Class is responsible for generation of log file of the
 * codes being executed. This helps in easy debugging of code and
 * understandability the flow of control of application.
 * @Type AppLogger
 * @author anitk
 * @date 22-December-2014
 */
public class AppLogger extends Logger {
	/**
	 * Description: Constructor of the class.
	 * @param name Type of String
	 */
	protected AppLogger(String name) {
		super(name);
	}

	/**
	 * Description: This method is responsible for logging Error messages
	 * @author anitk
	 * @param className of Class type
	 * @param methodName of String type
	 * @param objObject of Object type         
	 */
	public static void logError(Class className, String methodName,Object objObject) {
			
		/* Creates an Instance of Logger */
		Logger objLogger = Logger.getLogger(className.getName());

		/* Checks whether Error logger is enabled or not */
		if (objLogger.isEnabledFor(Level.ERROR)) {
			
			objLogger.error(" :: " + methodName + " :: ", (Throwable) objObject);
					
		}
	}

	public static void logError(String className, String methodName,Object objObject) {
			
		/* Creates an Instance of Logger */
		Logger objLogger = Logger.getLogger(className);

		/* Checks whether Error logger is enabled or not */
		if (objLogger.isEnabledFor(Level.ERROR)) {
			
			objLogger.error(" :: " + methodName + " :: ", (Throwable) objObject);
		}
	}

	/**
	 * Description: This method is responsible for logging Informational
	 * messages
	 * @author anitk
	 * @param className of Class type
	 * @param methodName of String type          
	 * @param message of String type           
	 */
	public static void logInfo(Class className, String methodName,
			String message) {
		/* Creates an Instance of Logger */
		Logger objLogger = Logger.getLogger(className.getName());

		/* Checks whether Info logger is enabled or not */
		if (objLogger.isInfoEnabled()) {
			objLogger.info(className.getName() + " :: " + methodName + " :: "+ message);
					
		}
	}

	/**
	 * Description: This method is responsible for logging Debug Informational
	 * messages
	 * @param className of Class type
	 * @param methodName of String type
	 * @param message of String type
	 */
	public static void logDebug(Class className, String methodName,String message) {

		/* Creates an Instance of Logger */
		Logger objLogger = Logger.getLogger(className.getName());

		/* Checks whether Info logger is enabled or not */
		if (objLogger.isDebugEnabled()) {
			objLogger.debug(className.getName() + " :: " + methodName + " :: "+ message);
		}
	}
}
