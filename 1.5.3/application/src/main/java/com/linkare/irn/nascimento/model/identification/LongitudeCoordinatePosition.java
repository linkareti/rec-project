package com.linkare.irn.nascimento.model.identification;

public enum LongitudeCoordinatePosition {
    
    EAST,
    
    WEST;
    
    public String getKey() {
	return String.format("%s.%s", getClass().getSimpleName(), this.name());
    }
}
