package com.velocity.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.velocity.exceptions.VelocityException;

/**
 * This class is defined to retrieve values from property file for all Velocity related transactions
 * 
 * @author Vimal Kumar
 * @date 22-December-2014
 */
public final class VelocityConfigManager {

    private static final Logger LOG = Logger.getLogger(VelocityConfigManager.class);
    private static final String VELOCITY_PROP_FILE_NAME = "velocity.properties";
    private static Properties properties;

    public VelocityConfigManager() throws VelocityException, IOException
    {
        initProperties();
    }
    /**
     * Load Velocity custom property file that contains all Velocity related information
     */
    private static void loadSDKProperties(final String filename) throws VelocityException, IOException
    {
        try{
            final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            properties = new Properties();
            properties.load(inputStream);
        }catch (Exception ex){
            LOG.error("Error Occurred reading poperty at VelocityConfigManager:loadSDKProperties() ", ex);
            throw new VelocityException("Configuration file " + VelocityConfigManager.VELOCITY_PROP_FILE_NAME + " is not found.");
        }
    }
    /**
     * This method provides the corresponding configurable value for a key from the configuration
     */
    public String getProperty(final String key)
    {
        return properties.getProperty(key);
    }
    public static void initProperties() throws VelocityException, IOException
    {
        LOG.info("VelocityConfigManager.initProperties");
        loadSDKProperties(VelocityConfigManager.VELOCITY_PROP_FILE_NAME);
    }
}
