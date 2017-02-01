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
public class ModelIVPWM extends pt.utl.ist.elab.client.webrobot.customizer.Models.ModelBlock implements
		pt.utl.ist.elab.client.webrobot.customizer.Interfaces.Model {

	/** Holds value of property d1. */
	private String d1 = " ";

	/** Holds value of property d2. */
	private String d2 = " ";

	/** Holds value of property d3. */
	private String d3 = " ";

	/** Holds value of property valor. */
	private int valor = 0;

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

	/** Holds value of property iValues. */
	private Object[][] iValues = { { "00000000", new Integer(155), new Integer(155) },
			{ "00000100", new Integer(136), new Integer(155) }, { "00001000", new Integer(136), new Integer(155) },
			{ "00001100", new Integer(136), new Integer(155) } };

	/** Holds value of property i0Value. */
	private int i0Value = 123;

	/** Holds value of property i1Value. */
	private int i1Value = 234;

	/** Holds value of property i2Value. */
	private int i2Value = 92;

	/** Holds value of property i3Value. */
	private int i3Value = 92;

	/** Holds value of property i4Value. */
	private int i4Value = 93;

	/** Holds value of property i5Value. */
	private int i5Value = 98;

	/** Holds value of property i6Value. */
	private int i6Value = 222;

	/** Holds value of property i0Value. */
	private int i7Value = 150;

	/** Holds value of property i0State. */
	private int i0State = 1;

	/** Holds value of property i1State. */
	private int i1State = 1;

	/** Holds value of property i2State. */
	private int i2State = 0;

	/** Holds value of property i3State. */
	private int i3State = 0;

	/** Holds value of property i4State. */
	private int i4State = 0;

	/** Holds value of property i5State. */
	private int i5State = 0;

	/** Holds value of property i6State. */
	private int i6State = 1;

	/** Holds value of property i7State. */
	private int i7State = 1;

	/** Creates a new instance of ModelCompInt */

	public ModelIVPWM() {
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

	/**
	 * Getter for property iValues.
	 * 
	 * @return Value of property iValues.
	 */
	public Object[][] getIValues() {
		return iValues;
	}

	/**
	 * Setter for property iValues.
	 * 
	 * @param iValues New value of property iValues.
	 */
	public void setIValues(final Object[][] iValues) {
		this.iValues = iValues;
	}

	/**
	 * Getter for property i0Value.
	 * 
	 * @return Value of property i0Value.
	 */
	public int getI0Value() {
		return i0Value;
	}

	/**
	 * Setter for property i0Value.
	 * 
	 * @param i0Value New value of property i0Value.
	 */
	public void setI0Value(final int i0Value) {
		this.i0Value = i0Value;
	}

	/**
	 * Getter for property i1Value.
	 * 
	 * @return Value of property i1Value.
	 */
	public int getI1Value() {
		return i1Value;
	}

	/**
	 * Setter for property i1Value.
	 * 
	 * @param i1Value New value of property i1Value.
	 */
	public void setI1Value(final int i1Value) {
		this.i1Value = i1Value;
	}

	/**
	 * Getter for property i2Value.
	 * 
	 * @return Value of property i2Value.
	 */
	public int getI2Value() {
		return i2Value;
	}

	/**
	 * Setter for property i2Value.
	 * 
	 * @param i2Value New value of property i2Value.
	 */
	public void setI2Value(final int i2Value) {
		this.i2Value = i2Value;
	}

	/**
	 * Getter for property i3Value.
	 * 
	 * @return Value of property i3Value.
	 */
	public int getI3Value() {
		return i3Value;
	}

	/**
	 * Setter for property i3Value.
	 * 
	 * @param i3Value New value of property i3Value.
	 */
	public void setI3Value(final int i3Value) {
		this.i3Value = i3Value;
	}

	/**
	 * Getter for property i4Value.
	 * 
	 * @return Value of property i4Value.
	 */
	public int getI4Value() {
		return i4Value;
	}

	/**
	 * Setter for property i4Value.
	 * 
	 * @param i4Value New value of property i4Value.
	 */
	public void setI4Value(final int i4Value) {
		this.i4Value = i4Value;
	}

	/**
	 * Getter for property i5Value.
	 * 
	 * @return Value of property i5Value.
	 */
	public int getI5Value() {
		return i5Value;
	}

	/**
	 * Setter for property i5Value.
	 * 
	 * @param i5Value New value of property i5Value.
	 */
	public void setI5Value(final int i5Value) {
		this.i5Value = i5Value;
	}

	/**
	 * Getter for property i6Value.
	 * 
	 * @return Value of property i6Value.
	 */
	public int getI6Value() {
		return i6Value;
	}

	/**
	 * Setter for property i6Value.
	 * 
	 * @param i6Value New value of property i6Value.
	 */
	public void setI6Value(final int i6Value) {
		this.i6Value = i6Value;
	}

	/**
	 * Getter for property i7Value.
	 * 
	 * @return Value of property i7Value.
	 */
	public int getI7Value() {
		return i7Value;
	}

	/**
	 * Setter for property i7Value.
	 * 
	 * @param i7Value New value of property i7Value.
	 */
	public void setI7Value(final int i7Value) {
		this.i7Value = i7Value;
	}

	/**
	 * Getter for property i0State.
	 * 
	 * @return Value of property i0State.
	 */
	public int getI0State() {
		return i0State;
	}

	/**
	 * Setter for property i0State.
	 * 
	 * @param i0State New value of property i0State.
	 */
	public void setI0State(final int i0State) {
		this.i0State = i0State;
	}

	/**
	 * Getter for property i1State.
	 * 
	 * @return Value of property i1State.
	 */
	public int getI1State() {
		return i1State;
	}

	/**
	 * Setter for property i1State.
	 * 
	 * @param i1State New value of property i1State.
	 */
	public void setI1State(final int i1State) {
		this.i1State = i1State;
	}

	/**
	 * Getter for property i2State.
	 * 
	 * @return Value of property i2State.
	 */
	public int getI2State() {
		return i2State;
	}

	/**
	 * Setter for property i2State.
	 * 
	 * @param i2State New value of property i2State.
	 */
	public void setI2State(final int i2State) {
		this.i2State = i2State;
	}

	/**
	 * Getter for property i3State.
	 * 
	 * @return Value of property i3State.
	 */
	public int getI3State() {
		return i3State;
	}

	/**
	 * Setter for property i3State.
	 * 
	 * @param i3State New value of property i3State.
	 */
	public void setI3State(final int i3State) {
		this.i3State = i3State;
	}

	/**
	 * Getter for property i4State.
	 * 
	 * @return Value of property i4State.
	 */
	public int getI4State() {
		return i4State;
	}

	/**
	 * Setter for property i4State.
	 * 
	 * @param i4State New value of property i4State.
	 */
	public void setI4State(final int i4State) {
		this.i4State = i4State;
	}

	/**
	 * Getter for property i5State.
	 * 
	 * @return Value of property i5State.
	 */
	public int getI5State() {
		return i5State;
	}

	/**
	 * Setter for property i5State.
	 * 
	 * @param i5State New value of property i5State.
	 */
	public void setI5State(final int i5State) {
		this.i5State = i5State;
	}

	/**
	 * Getter for property i6State.
	 * 
	 * @return Value of property i6State.
	 */
	public int getI6State() {
		return i6State;
	}

	/**
	 * Setter for property i6State.
	 * 
	 * @param i6State New value of property i6State.
	 */
	public void setI6State(final int i6State) {
		this.i6State = i6State;
	}

	/**
	 * Getter for property i7State.
	 * 
	 * @return Value of property i7State.
	 */
	public int getI7State() {
		return i7State;
	}

	/**
	 * Setter for property i7State.
	 * 
	 * @param i7State New value of property i7State.
	 */
	public void setI7State(final int i7State) {
		this.i7State = i7State;
	}
}
