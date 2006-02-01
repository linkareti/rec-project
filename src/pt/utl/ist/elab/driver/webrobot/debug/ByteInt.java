/*
 * ByteInt.java
 *
 * Created on 28 de Abril de 2003, 23:55
 */

package pt.utl.ist.elab.driver.webrobot.debug;

/**
 *
 * @author  André
 */
public class ByteInt {
    
    /** Creates a new instance of ByteInt */
    public ByteInt() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        for(int i=0;i<256;i++)
        {
            System.out.println("i="+i+" "+(byte)i);
        }
    }
    
}
