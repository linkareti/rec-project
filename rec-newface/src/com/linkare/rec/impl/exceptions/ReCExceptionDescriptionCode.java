/* 
 * ReCExceptionDescriptionCode.java created on Feb 6, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.exceptions;

/**
 * Interface for ReC Exceptions consolidation. Defines Exception code and description.
 * 
 * @author Henrique Fernandes
 */
public interface ReCExceptionDescriptionCode {

    /**
     * @return The Exception code identifier.
     */
    ExceptionCode getCode();

}
