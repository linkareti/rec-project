/*
 * testArrays.java
 *
 * Created on 11 de Dezembro de 2003, 22:20
 */

package test;

/**
 *
 * @author  jp
 */
public class testArrays
{
    
    /** Holds value of property listaInteiros. */
    private int[] listaInteiros;
    private java.awt.Color[] cores = null;
    private java.awt.Color[][] coresMatrix = null;
    
    /** Creates a new instance of testArrays */
    public testArrays()
    {
    }
    
    /** Indexed getter for property listaInteiros.
     * @param index Index of the property.
     * @return Value of the property at <CODE>index</CODE>.
     *
     */
    public int getListaInteiros(int index)
    {
	return this.listaInteiros[index];
    }
    
    /** Getter for property listaInteiros.
     * @return Value of property listaInteiros.
     *
     */
    public int[] getListaInteiros()
    {
	return this.listaInteiros;
    }
    
    /** Indexed setter for property listaInteiros.
     * @param index Index of the property.
     * @param listaInteiros New value of the property at <CODE>index</CODE>.
     *
     */
    public void setListaInteiros(int index, int listaInteiros)
    {
	this.listaInteiros[index] = listaInteiros;
    }
    
    /** Setter for property listaInteiros.
     * @param listaInteiros New value of property listaInteiros.
     *
     */
    public void setListaInteiros(int[] listaInteiros)
    {
	this.listaInteiros = listaInteiros;
    }
    
    /** Getter for property cores.
     * @return Value of property cores.
     *
     */
    public java.awt.Color[] getCores() {
        return this.cores;
    }
    
    /** Setter for property cores.
     * @param cores New value of property cores.
     *
     */
    public void setCores(java.awt.Color[] cores) {
        this.cores = cores;
    }
    
    /** Getter for property coresMatrix.
     * @return Value of property coresMatrix.
     *
     */
    public java.awt.Color[][] getCoresMatrix() {
        return this.coresMatrix;
    }
    
    /** Setter for property coresMatrix.
     * @param coresMatrix New value of property coresMatrix.
     *
     */
    public void setCoresMatrix(java.awt.Color[][] coresMatrix) {
        this.coresMatrix = coresMatrix;
    }
    
}

