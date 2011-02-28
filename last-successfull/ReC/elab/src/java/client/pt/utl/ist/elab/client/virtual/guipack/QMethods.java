package pt.utl.ist.elab.client.virtual.guipack;

//
//  QMethods.java
//  
//  Created by Pedro Queiro' on Sat Nov 8 2003.
//  Copyright (c) 2003 Pedro Queiro'. All rights reserved.
//
//  Coleccao de metodos para varias tarefas
//

/**
 * A classe QMethods disponibiliza uma serie de metodos para facilitar a
 * utilizacao do Java.
 * 
 * @author The Godfather
 * @version Alpha
 */

public class QMethods {

	// Metodos disponiveis

	/**
	 * Arredonda um dado numero com as casas decimais desejadas. O numero e'
	 * arredondado 'a x-esima casa.
	 * 
	 * <pre>
	 * QMethods.arredondar(double a, int x);
	 * </pre>
	 * 
	 * Devolve um double que e' o double <i>a</i> fornecido arredondado a
	 * <i>x</i> casas.
	 * 
	 * @param a numero a arredondar.
	 * @param x numero de casas desejadas.
	 * @return O numero a arredondado as casas desejadas.
	 */

	public static double arredondar(double a, int x) {

		double b, c, d;

		d = Math.pow(10, x);

		b = (a * d);
		c = (Math.rint(b)) / d;

		return c;
	}

	/**
	 * Arredonda um dado numero com as casas decimais desejadas. O numero e'
	 * arredondado 'a x-esima casa.
	 * 
	 * <pre>
	 * QMethods.arredondar(float a, int x);
	 * </pre>
	 * 
	 * Devolve um float que e' o float <i>a</i> fornecido arredondado a <i>x</i>
	 * casas.
	 * 
	 * @param a numero a arredondar.
	 * @param x numero de casas desejadas.
	 * @return O numero a arredondado as casas desejadas.
	 */

	public static float arredondar(float a, int x) {

		float b, c, d;

		d = (float) Math.pow(10, x);

		b = (float) (a * d);

		c = (float) (Math.rint(b)) / d;

		return c;
	}

	/**
	 * Arredonda um dado numero a 0 casas decimais, ou seja, ao inteiro mais
	 * proximo. O numero e' arredondado ao inteiro mais proximo.
	 * 
	 * <pre>
	 * QMethods.arredondarInt(double a);
	 * </pre>
	 * 
	 * Devolve um int que e' o double <i>a</i> fornecido arredondado 'as 0 casas
	 * decimais, ou seja, ao inteiro mais proximo.
	 * 
	 * @param a numero a arredondar.
	 * @return O numero a arredondado a 0 casas decimais, ou seja, um inteiro.
	 */

	public static int arredondarInt(double a) {

		int c;

		c = (int) (Math.rint(a));

		return c;
	}

	/**
	 * Arredonda um dado numero a 0 casas decimais, ou seja, ao inteiro mais
	 * proximo. O numero e' arredondado ao inteiro mais proximo.
	 * 
	 * <pre>
	 * QMethods.arredondarInt(double a);
	 * </pre>
	 * 
	 * Devolve um int que e' o double <i>a</i> fornecido arredondado 'as 0 casas
	 * decimais, ou seja, ao inteiro mais proximo.
	 * 
	 * @param a numero a arredondar.
	 * @return O numero a arredondado a 0 casas decimais, ou seja, um inteiro.
	 */

	public static int arredondarInt(float a) {

		int c;

		c = (int) (Math.rint(a));

		return c;
	}

}