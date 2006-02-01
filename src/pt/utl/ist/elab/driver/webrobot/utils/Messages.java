/*
 * Messages.java
 *
 * Created on 14 de Março de 2003, 17:18
 */

package pt.utl.ist.elab.driver.webrobot.utils;

/**
 *
 * @author  André
 */


public class Messages {
    
    private static boolean errorOccurred;
    
    /** Creates a new instance of Error */
    public Messages() 
    {
        errorOccurred=false;
    }
    
    
    public static void displayErrorMessage(String errorMessage)
    {
        System.out.println(errorMessage);
        javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(),errorMessage,"Erro",javax.swing.JOptionPane.ERROR_MESSAGE);
        setErrorOccurred(true);
    }    
    
    public static boolean isErrorOccurred()
    {
        return errorOccurred;
    }
    
    public static void setErrorOccurred(boolean errorState)
    {
        errorOccurred=errorState;
    }
}
