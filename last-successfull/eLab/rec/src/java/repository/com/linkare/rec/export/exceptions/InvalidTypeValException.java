/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.export.exceptions;

/**
 *
 * @author artur
 */
public class InvalidTypeValException extends Exception {

    public InvalidTypeValException(Throwable cause) {
        super(cause);
    }

    public InvalidTypeValException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTypeValException(String message) {
        super(message);
    }

    public InvalidTypeValException() {
    }
    
}
