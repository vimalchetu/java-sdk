package com.velocity.exceptions;

/**
 * This class defines the NotFound exception for Velocity payment gateway implementation.
 * @author anitk
 * @date 13-January-2015
 */
public class VelocityNotFoundException extends Exception {

    private static final long serialVersionUID = -8437943283405002162L;

    /**
     * Default constructor
     */
    public VelocityNotFoundException() {
        super();
    }

    /**
     * Constructor
     * @param msg - the message to include in the exception
     */
    public VelocityNotFoundException(String msg) {
        super(msg);

    }

    /**
     * Constructor
     * @param t  - creates the exception from another exception
     */
    public VelocityNotFoundException(Throwable t) {
        super(t);

    }

    /**
     * Message + throwable constructor
     * @param msg - message to include in the exception
     * @param t - the causing exception
     */
    public VelocityNotFoundException(String msg, Throwable t) {
        super(msg, t);

    }

}
