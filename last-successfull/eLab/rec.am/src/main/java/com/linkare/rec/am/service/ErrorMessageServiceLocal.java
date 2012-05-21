package com.linkare.rec.am.service;

import javax.ejb.Local;

import com.linkare.rec.am.model.ErrorMessage;

/**
 * 
 * @author Bruno Catarino - Linkare TI
 * 
 */
@Local
public interface ErrorMessageServiceLocal extends BusinessService<ErrorMessage, Long> {
}