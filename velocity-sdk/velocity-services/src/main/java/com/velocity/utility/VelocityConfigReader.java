/**
 * 
 */
package com.velocity.utility;

import java.io.InputStream;
import java.util.Properties;

import com.velocity.exceptions.VelocityIllegalArgument;
import com.velocity.exceptions.VelocityNotFound;

/**
 * This class is used to read the application Configurable values from a Configuration file.
 * @author anitk
 * @date 22-December-2014
 */
public final class VelocityConfigReader {
	
	/* Reference for application configuration reader. */
	private static Properties propertyReader;
	
	
	
	/**
	 * This method provides the corresponding configurable value for a key from the configuration file.
	 * @param key - key to which value read from configuration file.
	 * @return String - Corresponding value of key from Property file.
	 * @throws VelocityIllegalArgument - thrown when Illegal argument is supplied.
	 * @throws VelocityNotFound - thrown when a resource is not found.
	 */
	public static String getProperty(String key) throws VelocityIllegalArgument, VelocityNotFound
	{
		if(key == null || key.isEmpty())
		{
			throw new VelocityIllegalArgument("Input key cannot be null or empty.");
		}
		
		if(propertyReader == null)
		{
			propertyReader = new Properties();
			try {
				InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(VelocityConstants.CONFIG_FILE_LOCATION);
				AppLogger.logDebug(VelocityConfigReader.class, "getProperty", "Config file InputStream == "+inputStream);
				propertyReader.load(inputStream);
			} catch(Exception ex)
			{
				ex.printStackTrace();
				throw new VelocityNotFound("Configuration file " + VelocityConstants.CONFIG_FILE_LOCATION + " is not found.", ex);
			}
		}
		
		return propertyReader.getProperty(key);
	}

}
