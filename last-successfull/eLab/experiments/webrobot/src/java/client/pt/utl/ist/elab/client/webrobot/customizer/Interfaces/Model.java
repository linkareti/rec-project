/*
 * Model.java
 *
 * Created on 19 de Fevereiro de 2003, 11:11
 */

package pt.utl.ist.elab.client.webrobot.customizer.Interfaces;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public interface Model {

	String d1 = "A1";
	String d2 = "=";
	String d3 = "A1";
	int flag = 0;
	int valor = 0;
	int valor2 = 0;
	int coluna = 0;
	int nivel = 0;
	char baixo = 'b';
	char esquerda = 'b';

	public String getD1();

	public void setD1(String d1);

	public String getD2();

	public void setD2(String d2);

	public String getD3();

	public void setD3(String d3);

	public int getFlag();

	public void setFlag(int flag);

	public int getValor();

	public void setValor(int valor);

	public int getValor2();

	public void setValor2(int valor2);

	public int getColuna();

	public void setColuna(int coluna);

	public int getNivel();

	public void setNivel(int Nivel);

	public char getBaixo();

	public void setBaixo(char baixo);

	public char getEsquerda();

	public void setEsquerda(char esquerda);
}
