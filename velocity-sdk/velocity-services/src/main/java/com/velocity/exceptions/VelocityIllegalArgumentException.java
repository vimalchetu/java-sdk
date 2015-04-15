package com.velocity.exceptions;

/**
 * This class defines the IllegalArgument exception for Velocity payment gateway implementation.
 * 
 * @author anitk
 * @date 13-January-2015
 */
public class VelocityIllegalArgumentException extends Exception {

    private static final long serialVersionUID = 2372214558943201219L;

    /**
     * Default constructor
     */
    public VelocityIllegalArgumentException() {
        super();
    }
    /**
     * Constructor
     * 
     * @param msg - the message to include in the exception
     */
    public VelocityIllegalArgumentException(String msg) {
        super(msg);
    }
    /**
     * Constructor
     * 
     * @param t - creates the exception from another exception
     */
    public VelocityIllegalArgumentException(Throwable t) {
        super(t);
    }
    /**
     * Message + throwable constructor
     * 
     * @param msg - message to include in the exception
     * @param t - the causing exception
     */
    public VelocityIllegalArgumentException(String msg, Throwable t) {
        super(msg, t);
    }
}
