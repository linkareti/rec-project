package com.linkare.rec.am.util;


public class MbeanProxy<T, K> {

    private final K entity;

    private final T mbeanInterface;

    public MbeanProxy(K entity, T mbeanInterface) {
	super();
	this.entity = entity;
	this.mbeanInterface = mbeanInterface;
    }

    public K getEntity() {
	return entity;
    }

    public T getMbeanInterface() {
	return mbeanInterface;
    }

}
