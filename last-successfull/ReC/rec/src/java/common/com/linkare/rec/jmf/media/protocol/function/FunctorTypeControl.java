package com.linkare.rec.jmf.media.protocol.function;

import java.awt.Component;

import javax.media.Control;

import com.linkare.rec.jmf.media.protocol.function.ui.FunctorTypeControlComponent;

public class FunctorTypeControl implements Control {

    private DataSource dataSource;

    FunctorTypeControl(DataSource dataSource) {
	this.dataSource=dataSource;
    }
    
    @Override
    public Component getControlComponent() {
	return new FunctorTypeControlComponent(this);
    }

    public void setFunctorType(FunctorType type)
    {
	this.dataSource.setFunctorType(type);
    }
    
    public FunctorType getFunctorType()
    {
	return this.dataSource.getFunctorType();
    }

    public FunctorControl getFunctorControl() {
	return getFunctorType().getFunctorControl();
    }
    
}
