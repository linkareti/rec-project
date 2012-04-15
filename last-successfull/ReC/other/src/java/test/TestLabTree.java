/*
 * TestLabTree.java
 *
 * Created on July 20, 2004, 11:38 AM
 */

package test;

/**
 *
 * @author André Neto - LEFT - IST
 */
public class TestLabTree {
    
    /** Creates a new instance of TestLabTree */
    public TestLabTree() 
    {
        javax.swing.JFrame dummy = new javax.swing.JFrame();
        dummy.getContentPane().add(new com.linkare.rec.impl.baseUI.labsTree.LaboratoryTree());
        dummy.pack();
        dummy.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        new TestLabTree();
    }
    
}
