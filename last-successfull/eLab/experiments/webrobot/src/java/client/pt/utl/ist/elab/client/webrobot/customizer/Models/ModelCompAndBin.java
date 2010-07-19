/*
 * ModelCompInt.java
 *
 * Created on 28 de Janeiro de 2003, 20:03
 */

package pt.utl.ist.elab.client.webrobot.customizer.Models;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class ModelCompAndBin extends pt.utl.ist.elab.client.webrobot.customizer.Models.ModelBlock implements
		pt.utl.ist.elab.client.webrobot.customizer.Interfaces.Model {

	/** Holds value of property d1. */
	private String d1 = "b0";

	/** Holds value of property d2. */
	private String d2 = " ";

	/** Holds value of property d3. */
	private String d3 = " ";

	/** Holds value of property flag. */
	private int flag = 0;

	/** Holds value of property valor. */
	private int valor = 0;

	/** Holds value of property valor2. */
	private int valor2 = 0;

	/** Holds value of property coluna. */
	private int coluna = 0;

	/** Holds value of property nivel. */
	private int nivel = 0;

	/** Holds value of property baixo. */
	private char baixo = 'b';

	/** Holds value of property esquerda. */
	private char esquerda = 'b';

	/** Creates a new instance of ModelCompInt */
	public ModelCompAndBin() {
	}

	/**
	 * Getter for property d1.
	 * 
	 * @return Value of property d1.
	 */
	public String getD1() {
		return this.d1;
	}

	/**
	 * Setter for property d1.
	 * 
	 * @param d1 New value of property d1.
	 */
	public void setD1(String d1) {
		this.d1 = d1;
	}

	/**
	 * Getter for property d2.
	 * 
	 * @return Value of property d2.
	 */
	public String getD2() {
		return this.d2;
	}

	/**
	 * Setter for property d2.
	 * 
	 * @param d2 New value of property d2.
	 */
	public void setD2(String d2) {
		this.d2 = d2;
	}

	/**
	 * Getter for property d3.
	 * 
	 * @return Value of property d3.
	 */
	public String getD3() {
		return this.d3;
	}

	/**
	 * Setter for property d3.
	 * 
	 * @param d3 New value of property d3.
	 */
	public void setD3(String d3) {
		this.d3 = d3;
	}

	/**
	 * Getter for property flag.
	 * 
	 * @return Value of property flag.
	 */
	public int getFlag() {
		return this.flag;
	}

	/**
	 * Setter for property flag.
	 * 
	 * @param flag New value of property flag.
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}

	/**
	 * Getter for property valor.
	 * 
	 * @return Value of property valor.
	 */
	public int getValor() {
		return this.valor;
	}

	/**
	 * Setter for property valor.
	 * 
	 * @param valor New value of property valor.
	 */
	public void setValor(int valor) {
		this.valor = valor;
	}

	/**
	 * Getter for property valor2.
	 * 
	 * @return Value of property valor2.
	 */
	public int getValor2() {
		return this.valor2;
	}

	/**
	 * Setter for property valor2.
	 * 
	 * @param valor2 New value of property valor2.
	 */
	public void setValor2(int valor2) {
		this.valor2 = valor2;
	}

	/**
	 * Getter for property coluna.
	 * 
	 * @return Value of property coluna.
	 */
	public int getColuna() {
		return this.coluna;
	}

	/**
	 * Setter for property coluna.
	 * 
	 * @param coluna New value of property coluna.
	 */
	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	/**
	 * Getter for property nivel.
	 * 
	 * @return Value of property nivel.
	 */
	public int getNivel() {
		return this.nivel;
	}

	/**
	 * Setter for property nivel.
	 * 
	 * @param coluna New value of property nivel.
	 */
	public void setNivel(int Nivel) {
		this.nivel = nivel;
	}

	/**
	 * Getter for property baixo.
	 * 
	 * @return Value of property baixo.
	 */
	public char getBaixo() {
		return this.baixo;
	}

	/**
	 * Setter for property baixo.
	 * 
	 * @param baixo New value of property baixo.
	 */
	public void setBaixo(char baixo) {
		this.baixo = baixo;
	}

	/**
	 * Getter for property esquerda.
	 * 
	 * @return Value of property esquerda.
	 */
	public char getEsquerda() {
		return this.esquerda;
	}

	/**
	 * Setter for property esquerda.
	 * 
	 * @param esquerda New value of property esquerda.
	 */
	public void setEsquerda(char esquerda) {
		this.esquerda = esquerda;
	}

}
