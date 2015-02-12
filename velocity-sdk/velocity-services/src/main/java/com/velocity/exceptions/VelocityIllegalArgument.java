/**
 * 
 */
package com.velocity.exceptions;

/**
 * This class defines the IllegalArgument exception for Velocity payment gateway implementation.
 * @author anitk
 * @date 13-January-2015
 */
public class VelocityIllegalArgument extends Exception {

    /**
     * Default constructor
     */
    public VelocityIllegalArgument() {
        super();
    }

    /**
     * Constructor
     * @param msg - the message to include in the exception
     */
    public VelocityIllegalArgument(String msg) {
        super(msg);

    }

    /**
     * Constructor
     * @param t  - creates the exception from another exception
     */
    public VelocityIllegalArgument(Throwable t) {
        super(t);

    }

    /**
     * Message + throwable constructor
     * @param msg - message to include in the exception
     * @param t - the causing exception
     */
    public VelocityIllegalArgument(String msg, Throwable t) {
        super(msg, t);

    }

}
