package com.velocity.exceptions;

/**
 * This class defines the Generic exception for Velocity payment gateway implementation.
 * @author anitk
 * @date 30-December-2014
 */
public class VelocityException extends Exception {

    private static final long serialVersionUID = 7425191749948021761L;

    /**
     * Default constructor
     */
    public VelocityException() {
        super();
    }

    /**
     * Constructor
     * @param msg - the message to include in the exception
     */
    public VelocityException(String msg) {
        super(msg);

    }

    /**
     * Constructor
     * @param t  - creates the exception from another exception
     */
    public VelocityException(Throwable t) {
        super(t);

    }

    /**
     * Message + throwable constructor
     * @param msg - message to include in the exception
     * @param t - the causing exception
     */
    public VelocityException(String msg, Throwable t) {
        super(msg, t);

    }

}
