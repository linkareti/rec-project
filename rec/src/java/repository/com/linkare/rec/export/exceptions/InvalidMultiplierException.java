/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.export.exceptions;

/**
 *
 * @author artur
 */
public class InvalidMultiplierException extends Exception {


    public InvalidMultiplierException(Throwable cause) {
        super(cause);
    }

    public InvalidMultiplierException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMultiplierException(String message) {
        super(message);
    }

    public InvalidMultiplierException() {
    }

    
}
