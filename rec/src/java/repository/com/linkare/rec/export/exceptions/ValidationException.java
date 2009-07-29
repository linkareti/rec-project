/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.export.exceptions;

/**
 * this exception will be throw when vo is invalid
 * 
 * @author artur
 */
public class ValidationException extends Exception {

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException() {
    }


    

}
