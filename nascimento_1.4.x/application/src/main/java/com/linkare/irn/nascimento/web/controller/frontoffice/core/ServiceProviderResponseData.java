package com.linkare.irn.nascimento.web.controller.frontoffice.core;

import java.util.Map;

import com.linkare.irn.nascimento.web.fasp.FARequestedAttribute;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class ServiceProviderResponseData {

    private String civilIdentificationNumber;

    private String givenName;

    private String familyName;

    private String dateOfBirth;

    private String nationality;

    // parents
    private String fatherGivenName;

    private String fatherFamilyName;

    private String motherGivenName;

    private String motherFamilyName;

    // Address
    private String streetType;

    private String streetName;

    private String buildingType;

    private String doorNumber;

    private String floor;

    private String side;

    private String zipCode4;

    private String zipCode3;

    private String location;

    private String parish;

    public ServiceProviderResponseData(final Map<String, String> requestedAttributesMap) {
	this.givenName = requestedAttributesMap.get(FARequestedAttribute.NOME_PROPRIO);
	this.familyName = requestedAttributesMap.get(FARequestedAttribute.NOME_APELIDO);
	this.civilIdentificationNumber = requestedAttributesMap.get(FARequestedAttribute.NIC);
	this.dateOfBirth = requestedAttributesMap.get(FARequestedAttribute.DATA_NASCIMENTO);
	this.nationality = requestedAttributesMap.get(FARequestedAttribute.NACIONALIDADE);

	// Parents
	this.fatherGivenName = requestedAttributesMap.get(FARequestedAttribute.NOME_PROPRIO_PAI);
	this.fatherFamilyName = requestedAttributesMap.get(FARequestedAttribute.NOME_APELIDO_PAI);
	this.motherGivenName = requestedAttributesMap.get(FARequestedAttribute.NOME_PROPRIO_MAE);
	this.motherFamilyName = requestedAttributesMap.get(FARequestedAttribute.NOME_APELIDO_MAE);

	// Address
	this.streetType = requestedAttributesMap.get(FARequestedAttribute.TIPO_DE_VIA);
	this.streetName = requestedAttributesMap.get(FARequestedAttribute.DESIGNACAO_DA_VIA);
	this.buildingType = requestedAttributesMap.get(FARequestedAttribute.TIPO_EDIFICIO);
	this.doorNumber = requestedAttributesMap.get(FARequestedAttribute.NUMERO_PORTA);
	this.side = requestedAttributesMap.get(FARequestedAttribute.LADO);
	this.zipCode4 = requestedAttributesMap.get(FARequestedAttribute.CODIGO_POSTAL4);
	this.zipCode3 = requestedAttributesMap.get(FARequestedAttribute.CODIGO_POSTAL3);
	this.location = requestedAttributesMap.get(FARequestedAttribute.LOCALIDADE);
	this.parish = requestedAttributesMap.get(FARequestedAttribute.FREGUESIA);
    }

    /**
     * @return the civilIdentificationNumber
     */
    public String getCivilIdentificationNumber() {
	return civilIdentificationNumber;
    }

    /**
     * @param civilIdentificationNumber
     *            the civilIdentificationNumber to set
     */
    public void setCivilIdentificationNumber(String civilIdentificationNumber) {
	this.civilIdentificationNumber = civilIdentificationNumber;
    }

    /**
     * @return the givenName
     */
    public String getGivenName() {
	return givenName;
    }

    /**
     * @param givenName
     *            the givenName to set
     */
    public void setGivenName(String givenName) {
	this.givenName = givenName;
    }

    /**
     * @return the familyName
     */
    public String getFamilyName() {
	return familyName;
    }

    /**
     * @param familyName
     *            the familyName to set
     */
    public void setFamilyName(String familyName) {
	this.familyName = familyName;
    }

    /**
     * @return the dateOfBirth
     */
    public String getDateOfBirth() {
	return dateOfBirth;
    }

    /**
     * @param dateOfBirth
     *            the dateOfBirth to set
     */
    public void setDateOfBirth(String dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return the nationality
     */
    public String getNationality() {
	return nationality;
    }

    /**
     * @param nationality
     *            the nationality to set
     */
    public void setNationality(String nationality) {
	this.nationality = nationality;
    }

    /**
     * @return the fatherGivenName
     */
    public String getFatherGivenName() {
	return fatherGivenName;
    }

    /**
     * @param fatherGivenName
     *            the fatherGivenName to set
     */
    public void setFatherGivenName(String fatherGivenName) {
	this.fatherGivenName = fatherGivenName;
    }

    /**
     * @return the fatherFamilyName
     */
    public String getFatherFamilyName() {
	return fatherFamilyName;
    }

    /**
     * @param fatherFamilyName
     *            the fatherFamilyName to set
     */
    public void setFatherFamilyName(String fatherFamilyName) {
	this.fatherFamilyName = fatherFamilyName;
    }

    /**
     * @return the motherGivenName
     */
    public String getMotherGivenName() {
	return motherGivenName;
    }

    /**
     * @param motherGivenName
     *            the motherGivenName to set
     */
    public void setMotherGivenName(String motherGivenName) {
	this.motherGivenName = motherGivenName;
    }

    /**
     * @return the motherFamilyName
     */
    public String getMotherFamilyName() {
	return motherFamilyName;
    }

    /**
     * @param motherFamilyName
     *            the motherFamilyName to set
     */
    public void setMotherFamilyName(String motherFamilyName) {
	this.motherFamilyName = motherFamilyName;
    }

    /**
     * @return the streetType
     */
    public String getStreetType() {
	return streetType;
    }

    /**
     * @param streetType
     *            the streetType to set
     */
    public void setStreetType(String streetType) {
	this.streetType = streetType;
    }

    /**
     * @return the streetName
     */
    public String getStreetName() {
	return streetName;
    }

    /**
     * @param streetName
     *            the streetName to set
     */
    public void setStreetName(String streetName) {
	this.streetName = streetName;
    }

    /**
     * @return the buildingType
     */
    public String getBuildingType() {
	return buildingType;
    }

    /**
     * @param buildingType
     *            the buildingType to set
     */
    public void setBuildingType(String buildingType) {
	this.buildingType = buildingType;
    }

    /**
     * @return the doorNumber
     */
    public String getDoorNumber() {
	return doorNumber;
    }

    /**
     * @param doorNumber
     *            the doorNumber to set
     */
    public void setDoorNumber(String doorNumber) {
	this.doorNumber = doorNumber;
    }

    /**
     * @return the floor
     */
    public String getFloor() {
	return floor;
    }

    /**
     * @param floor
     *            the floor to set
     */
    public void setFloor(String floor) {
	this.floor = floor;
    }

    /**
     * @return the side
     */
    public String getSide() {
	return side;
    }

    /**
     * @param side
     *            the side to set
     */
    public void setSide(String side) {
	this.side = side;
    }

    /**
     * @return the zipCode4
     */
    public String getZipCode4() {
	return zipCode4;
    }

    /**
     * @param zipCode4
     *            the zipCode4 to set
     */
    public void setZipCode4(String zipCode4) {
	this.zipCode4 = zipCode4;
    }

    /**
     * @return the zipCode3
     */
    public String getZipCode3() {
	return zipCode3;
    }

    /**
     * @param zipCode3
     *            the zipCode3 to set
     */
    public void setZipCode3(String zipCode3) {
	this.zipCode3 = zipCode3;
    }

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
     * @return the parish
     */
    public String getParish() {
	return parish;
    }

    /**
     * @param parish
     *            the parish to set
     */
    public void setParish(String parish) {
	this.parish = parish;
    }
}