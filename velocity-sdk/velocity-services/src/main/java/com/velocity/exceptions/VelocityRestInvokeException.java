package com.velocity.exceptions;

/**
 * This class defines the RestInvokeException exception for Velocity payment gateway implementation.
 * @author anitk
 * @date 13-January-2015
 */
public class VelocityRestInvokeException extends Exception {

    private static final long serialVersionUID = 2407112332528162904L;

    /**
     * Default constructor
     */
    public VelocityRestInvokeException() {
        super();
    }

    /**
     * Constructor
     * @param msg - the message to include in the exception
     */
    public VelocityRestInvokeException(String msg) {
        super(msg);

    }

    /**
     * Constructor
     * @param t  - creates the exception from another exception
     */
    public VelocityRestInvokeException(Throwable t) {
        super(t);

    }

    /**
     * Message + throwable constructor
     * @param msg - message to include in the exception
     * @param t - the causing exception
     */
    public VelocityRestInvokeException(String msg, Throwable t) {
        super(msg, t);

    }

}
