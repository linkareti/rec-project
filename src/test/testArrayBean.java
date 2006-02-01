/*
 * testArrayBean.java
 *
 * Created on 11 de Dezembro de 2003, 18:53
 */

package test;

/**
 *
 * @author  Administrator
 */
public class testArrayBean
{
    
    private int[] intArray = null;
    
    /** Holds value of property colores. */
    private java.awt.Color[] colores;
    
    /** Holds value of property colore. */
    private java.awt.Color colore;
    
    /** Holds value of property matrixColors. */
    private java.awt.Color[][] matrixColors;
    
    /** Creates a new instance of testArrayBean */
    public testArrayBean()
    {
    }
    
    /** Getter for property intArray.
     * @return Value of property intArray.
     *
     */
    public int[] getIntArray()
    {
	return this.intArray;
    }
    
    /** Setter for property intArray.
     * @param intArray New value of property intArray.
     *
     */
    public void setIntArray(int[] intArray)
    {
	this.intArray = intArray;
    }
    
    
    public int getIntArray(int index)
    {
	return intArray[index];
    }
    
    public void setIntArray(int index, int value)
    {
	intArray[index]=value;
    }
    
    /** Indexed getter for property colores.
     * @param index Index of the property.
     * @return Value of the property at <CODE>index</CODE>.
     *
     */
    public java.awt.Color getColores(int index)
    {
	return this.colores[index];
    }
    
    /** Getter for property colores.
     * @return Value of property colores.
     *
     */
    public java.awt.Color[] getColores()
    {
	return this.colores;
    }
    
    /** Indexed setter for property colores.
     * @param index Index of the property.
     * @param colores New value of the property at <CODE>index</CODE>.
     *
     */
    public void setColores(int index, java.awt.Color colores)
    {
	this.colores[index] = colores;
    }
    
    /** Setter for property colores.
     * @param colores New value of property colores.
     *
     */
    public void setColores(java.awt.Color[] colores)
    {
	this.colores = colores;
    }
    
    /** Getter for property colore.
     * @return Value of property colore.
     *
     */
    public java.awt.Color getColore()
    {
	return this.colore;
    }
    
    /** Setter for property colore.
     * @param colore New value of property colore.
     *
     */
    public void setColore(java.awt.Color colore)
    {
	this.colore = colore;
    }
    
    /** Getter for property matrixColors.
     * @return Value of property matrixColors.
     *
     */
    public java.awt.Color[][] getMatrixColors()
    {
	return this.matrixColors;
    }
    
    /** Setter for property matrixColors.
     * @param matrixColors New value of property matrixColors.
     *
     */
    public void setMatrixColors(java.awt.Color[][] matrixColors)
    {
	this.matrixColors = matrixColors;
    }
    
}
