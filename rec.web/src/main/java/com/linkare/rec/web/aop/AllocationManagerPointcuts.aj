package com.linkare.rec.web.aop;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public aspect AllocationManagerPointcuts {

    public pointcut inDomainLayer() :
	within(com.linkare.rec.web.model..*);
    
    public pointcut inServiceLayer() :
	within(com.linkare.rec.web.service..*);
    
    public pointcut inWebLayer() :
	within(com.linkare.rec.web.web..*);
}