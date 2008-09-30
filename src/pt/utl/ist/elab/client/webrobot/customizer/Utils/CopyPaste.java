/*
 * CopyPaste.java
 *
 * Created on 19 de Fevereiro de 2003, 11:19
 */

package pt.utl.ist.elab.client.webrobot.customizer.Utils;

/**
 *
 * @author André Neto - LEFT - IST
 */
public class CopyPaste {
    
    pt.utl.ist.elab.client.webrobot.customizer.Comps.Block[][] matrixToCopy=new pt.utl.ist.elab.client.webrobot.customizer.Comps.Block[3][3];//Security initialization
    String[][] matrixWiringToCopy=new String[3][3];//Security initialization
    
    /** Creates a new instance of CopyPaste */
    public CopyPaste() {
    }
    
    public void addData(pt.utl.ist.elab.client.webrobot.customizer.Comps.Block[][] matrixToCopy)
    {
        this.matrixToCopy=matrixToCopy;
    }
    
    public void addData(String[][] matrixWiringToCopy)
    {
        this.matrixWiringToCopy=matrixWiringToCopy;
    }

    public pt.utl.ist.elab.client.webrobot.customizer.Comps.Block[][] getMatrixData()
    {
        return matrixToCopy;
    }
    
    public String[][] getMatrixWiringData()
    {
        return matrixWiringToCopy;
    }
}
