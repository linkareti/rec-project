package com.linkare.rec.am.aop;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public aspect AllocationManagerPointcuts {

    public pointcut inDomainLayer() :
	within(com.linkare.rec.am.model..*);
    
    public pointcut inServiceLayer() :
	within(com.linkare.rec.am.service..*);
    
    public pointcut inWebLayer() :
	within(com.linkare.rec.am.web..*);
}