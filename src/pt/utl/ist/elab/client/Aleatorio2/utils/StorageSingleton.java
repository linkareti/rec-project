/*
 * StorageSingleton.java
 *
 * Created on 24 de Junho de 2003, 13:04
 */

package pt.utl.ist.elab.client.Aleatorio2.utils;

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
    
    /** Creates a new instance of storageSingleton 
     * Note that it's private so only one of these is around
     */
    private StorageSingleton() {
    }
    
    public static StorageSingleton getSingleton()
    {
        if (storageSingleton == null)
            storageSingleton = new StorageSingleton();
        return storageSingleton;
    }
    
    public void setStoredInt(int intToStore)
    {
        this.storedInt = intToStore;
        this.newValueAvailableAccu = true;
        this.newValueAvailableSess = true;
    }//setStoredInt(int intToStore)
    
    public int accuGetStoredInt()
    {
        this.newValueAvailableAccu = false;
        return storedInt;
    }//accuGetStoredInt()
    
    public int sessGetStoredInt()
    {
        this.newValueAvailableSess = false;
        return storedInt;
    }//SessGetStoredInt()
    
    public boolean checkNewValueAvailableAccu()
    {
        return newValueAvailableAccu;
    }//checkNewValueAccu()
    
    public boolean checkNewValueAvailableSess()
    {
        return newValueAvailableSess;
    }//checkNewValueSess()
    
    public void setImage(java.awt.Image image)
    {
        this.image = image;
    }//setImage(java.awt.Image image)
    
    public java.awt.Image getImage()
    {
        return image;
    }
}
