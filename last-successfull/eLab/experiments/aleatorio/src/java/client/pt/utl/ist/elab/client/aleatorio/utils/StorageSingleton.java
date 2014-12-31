/*
 * StorageSingleton.java
 *
 * Created on 24 de Junho de 2003, 13:04
 */

package pt.utl.ist.elab.client.aleatorio.utils;

/**
 * 
 * @author Pedro Carvalho - LEFT - IST
 */
public class StorageSingleton {

	private static StorageSingleton storageSingleton = null;

	private int storedInt;
	private boolean newValueAvailableAccu = false;
	private boolean newValueAvailableSess = false;
	private java.awt.Image image = null;

	/**
	 * Creates a new instance of storageSingleton Note that it's private so only
	 * one of these is around
	 */
	private StorageSingleton() {
	}

	public static StorageSingleton getSingleton() {
		if (StorageSingleton.storageSingleton == null) {
			StorageSingleton.storageSingleton = new StorageSingleton();
		}
		return StorageSingleton.storageSingleton;
	}

	public void setStoredInt(final int intToStore) {
		storedInt = intToStore;
		newValueAvailableAccu = true;
		newValueAvailableSess = true;
	}// setStoredInt(int intToStore)

	public int accuGetStoredInt() {
		newValueAvailableAccu = false;
		return storedInt;
	}// accuGetStoredInt()

	public int sessGetStoredInt() {
		newValueAvailableSess = false;
		return storedInt;
	}// SessGetStoredInt()

	public boolean checkNewValueAvailableAccu() {
		return newValueAvailableAccu;
	}// checkNewValueAccu()

	public boolean checkNewValueAvailableSess() {
		return newValueAvailableSess;
	}// checkNewValueSess()

	public void setImage(final java.awt.Image image) {
		this.image = image;
	}// setImage(java.awt.Image image)

	public java.awt.Image getImage() {
		return image;
	}
}
