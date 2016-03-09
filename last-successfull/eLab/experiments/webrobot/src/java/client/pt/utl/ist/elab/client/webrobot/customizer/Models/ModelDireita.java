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
public class ModelDireita extends pt.utl.ist.elab.client.webrobot.customizer.Models.ModelBlock implements
		pt.utl.ist.elab.client.webrobot.customizer.Interfaces.Model {

	/** Holds value of property d1. */
	private String d1 = "b0";

	/** Holds value of property d2. */
	private String d2 = " ";

	/** Holds value of property d3. */
	private String d3 = " ";

	/** Holds value of property valor. */
	private int valor = 1;

	/** Holds value of property valor2. */
	private int valor2 = 0;

	/** Holds value of property coluna. */
	private int coluna = 0;

	/** Holds value of property nivel. */
	private int nivel = 0;

	/** Holds value of property flag. */
	private int flag = 0;

	/** Holds value of property baixo. */
	private char baixo = 'b';

	/** Holds value of property esquerda. */
	private char esquerda = 'b';

	/** Creates a new instance of ModelCompInt */
	public ModelDireita() {
	}

	/**
	 * Getter for property d1.
	 * 
	 * @return Value of property d1.
	 */
	@Override
	public String getD1() {
		return d1;
	}

	/**
	 * Setter for property d1.
	 * 
	 * @param d1 New value of property d1.
	 */
	@Override
	public void setD1(final String d1) {
		this.d1 = d1;
	}

	/**
	 * Getter for property d2.
	 * 
	 * @return Value of property d2.
	 */
	@Override
	public String getD2() {
		return d2;
	}

	/**
	 * Setter for property d2.
	 * 
	 * @param d2 New value of property d2.
	 */
	@Override
	public void setD2(final String d2) {
		this.d2 = d2;
	}

	/**
	 * Getter for property d3.
	 * 
	 * @return Value of property d3.
	 */
	@Override
	public String getD3() {
		return d3;
	}

	/**
	 * Setter for property d3.
	 * 
	 * @param d3 New value of property d3.
	 */
	@Override
	public void setD3(final String d3) {
		this.d3 = d3;
	}

	/**
	 * Getter for property valor.
	 * 
	 * @return Value of property valor.
	 */
	@Override
	public int getValor() {
		return valor;
	}

	/**
	 * Setter for property valor.
	 * 
	 * @param valor New value of property valor.
	 */
	@Override
	public void setValor(final int valor) {
		this.valor = valor;
	}

	/**
	 * Getter for property valor2.
	 * 
	 * @return Value of property valor2.
	 */
	@Override
	public int getValor2() {
		return valor2;
	}

	/**
	 * Setter for property valor2.
	 * 
	 * @param valor2 New value of property valor2.
	 */
	@Override
	public void setValor2(final int valor2) {
		this.valor2 = valor2;
	}

	/**
	 * Getter for property coluna.
	 * 
	 * @return Value of property coluna.
	 */
	@Override
	public int getColuna() {
		return coluna;
	}

	/**
	 * Setter for property coluna.
	 * 
	 * @param coluna New value of property coluna.
	 */
	@Override
	public void setColuna(final int coluna) {
		this.coluna = coluna;
	}

	/**
	 * Getter for property nivel.
	 * 
	 * @return Value of property nivel.
	 */
	@Override
	public int getNivel() {
		return nivel;
	}

	/**
	 * Setter for property nivel.
	 * 
	 * @param coluna New value of property nivel.
	 */
	@Override
	public void setNivel(final int Nivel) {
		nivel = nivel;
	}

	/**
	 * Getter for property flag.
	 * 
	 * @return Value of property flag.
	 */
	@Override
	public int getFlag() {
		return flag;
	}

	/**
	 * Setter for property flag.
	 * 
	 * @param flag New value of property flag.
	 */
	@Override
	public void setFlag(final int flag) {
		this.flag = flag;
	}

	/**
	 * Getter for property baixo.
	 * 
	 * @return Value of property baixo.
	 */
	@Override
	public char getBaixo() {
		return baixo;
	}

	/**
	 * Setter for property baixo.
	 * 
	 * @param baixo New value of property baixo.
	 */
	@Override
	public void setBaixo(final char baixo) {
		this.baixo = baixo;
	}

	/**
	 * Getter for property esquerda.
	 * 
	 * @return Value of property esquerda.
	 */
	@Override
	public char getEsquerda() {
		return esquerda;
	}

	/**
	 * Setter for property esquerda.
	 * 
	 * @param esquerda New value of property esquerda.
	 */
	@Override
	public void setEsquerda(final char esquerda) {
		this.esquerda = esquerda;
	}

}
