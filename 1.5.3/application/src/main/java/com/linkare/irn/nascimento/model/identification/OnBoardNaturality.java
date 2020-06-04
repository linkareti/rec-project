package com.linkare.irn.nascimento.model.identification;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Entity
@DiscriminatorValue("OnBoardNaturality")
public class OnBoardNaturality extends Naturality {

    private static final long serialVersionUID = 1L;
    @Column(name = "location", length = 150)
    @Size(max = 150)
    @NotNull
    private String location;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "longitude")
    private float longitude;

    /**
     * @return the location
     */
    public String getLocation() {
	return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location) {
	this.location = location;
    }

    /**
     * @return the latitude
     */
    public float getLatitude() {
	return latitude;
    }

    /**
     * @param latitude
     *            the latitude to set
     */
    public void setLatitude(float latitude) {
	this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public float getLongitude() {
	return longitude;
    }

    /**
     * @param longitude
     *            the longitude to set
     */
    public void setLongitude(float longitude) {
	this.longitude = longitude;
    }

    /*************************/
    public float getLatitudeValue() {
	return Math.abs(latitude);
    }

    public void setLatitudeValue(final float latitudeValue) {
	changeLatitude(getLatitudeCoordinatePosition(), latitudeValue);
    }

    public LatitudeCoordinatePosition getLatitudeCoordinatePosition() {
	return latitude < 0.0F ? LatitudeCoordinatePosition.SOUTH : LatitudeCoordinatePosition.NORTH;
    }

    public void setLatitudeCoordinatePosition(final LatitudeCoordinatePosition latitudeCoordinatePosition) {
	changeLatitude(latitudeCoordinatePosition, latitude);
    }

    public float getLongitudeValue() {
	return Math.abs(longitude);
    }

    public void setLongitudeValue(final float longitudeValue) {
	changeLongitude(getLongitudeCoordinatePosition(), longitudeValue);
    }

    public LongitudeCoordinatePosition getLongitudeCoordinatePosition() {
	return longitude < 0.0F ? LongitudeCoordinatePosition.WEST : LongitudeCoordinatePosition.EAST;
    }

    public void setLongitudeCoordinatePosition(final LongitudeCoordinatePosition longitudeCoordinatePosition) {
	changeLongitude(longitudeCoordinatePosition, longitude);
    }

    private void changeLatitude(final LatitudeCoordinatePosition latitudeCoordinatePosition, final float latitudeValue) {
	if (LatitudeCoordinatePosition.SOUTH.equals(latitudeCoordinatePosition)) {
	    this.latitude = -Math.abs(latitudeValue);
	} else {
	    this.latitude = Math.abs(latitudeValue);
	}
    }

    private void changeLongitude(final LongitudeCoordinatePosition longitudeCoordinatePosition, final float longitudeValue) {
	if (LongitudeCoordinatePosition.WEST.equals(longitudeCoordinatePosition)) {
	    this.longitude = -Math.abs(longitudeValue);
	} else {
	    this.longitude = Math.abs(longitudeValue);
	}
    }

    @Override
    public NaturalityType getNaturalityType() {
	return NaturalityType.ON_BOARD;
    }
}