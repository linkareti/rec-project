package com.linkare.irn.nascimento.model.identification;

public enum LatitudeCoordinatePosition {
    
    NORTH,
    
    SOUTH;
    
    public String getKey() {
	return String.format("%s.%s", getClass().getSimpleName(), this.name());
    }
}
