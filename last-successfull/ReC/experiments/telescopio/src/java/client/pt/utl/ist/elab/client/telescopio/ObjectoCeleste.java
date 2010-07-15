/*
 * Objetos_celestes.java vers�o2.0
 *
 * Created on 23 de Outubro de 2004, 22:41
 */
package pt.utl.ist.elab.client.telescopio;

import java.util.GregorianCalendar;

/**
 * 
 * @author Myriam
 */

public class ObjectoCeleste {

	String nome, comentario, nomeObjecto;
	double ar_m, dec_m;
	boolean visivel;
	int ar_h, dec_g;
	double dt;

	public ObjectoCeleste(String _nomeObjecto, String _nome, String _comentario, int _ar_h, double _ar_m, int _dec_g,
			double _dec_m, double _dt) {
		nomeObjecto = _nomeObjecto;
		nome = _nome;
		comentario = _comentario;
		ar_h = _ar_h;
		ar_m = _ar_m;
		dec_g = _dec_g;
		dec_m = _dec_m;
		dt = _dt;
		visivel = visivel(ar_h, ar_m, dec_g, dec_m);

	}

	public String getNome() {
		return nome;
	}

	public String getNomeObjecto() {
		return nomeObjecto;
	}

	public String getAr_h() {
		return String.valueOf(ar_h);
	}

	public String getAr_m() {
		return String.valueOf(ar_m);
	}

	public String getDec_g() {
		return String.valueOf(dec_g);
	}

	public String getDec_m() {
		return String.valueOf(dec_m);
	}

	public String getTempo() {
		return String.valueOf(dt);
	}

	public String getComment() {
		return comentario;
	}

	public static double degToRad(double arg) {
		return arg * Math.PI / 180;
	}

	public static double radToDeg(double arg) {
		return arg * 180 / Math.PI;
	}

	/* calcula os objetos visiveis, devolve true se for visivel */
	public static boolean visivel(int ar_h, double ar_m, int dec_g, double dec_m) {

		/*
		 * Local de observa��o Lisboa PORTUGAL Latitude: 38.7� Longitude -9.2�
		 */
		double lat = 0.675442421, longi = 0.160570291;
		double ar, dec;
		int ano, mes, dia, hora, minutos, hora_h;
		double a, b, jd;

		/* Transformar ascen��o recta de HH:MM em radianos */
		ar = degToRad(15 * (ar_h + ar_m / 60));

		/* Transformar declina��o de DD:MM em radianos */
		if (dec_g < 0)
			dec = degToRad(-(Math.abs(dec_g) + dec_m / 60));
		else
			dec = degToRad(dec_g + dec_m / 60);

		/* Criar calendario */
		GregorianCalendar calendario = new GregorianCalendar();
		ano = calendario.get(GregorianCalendar.YEAR);
		mes = calendario.get(GregorianCalendar.MONTH);
		dia = calendario.get(GregorianCalendar.DAY_OF_MONTH);
		hora = calendario.get(GregorianCalendar.HOUR_OF_DAY);
		minutos = calendario.get(GregorianCalendar.MINUTE);
		hora_h = hora + minutos / 60; // Convers�o HH:MM em HH decimal

		/* Calcular o dia Juliano jd */
		if (mes < 2) {
			mes = mes + 12;
			ano = ano - 1;
		}

		a = Math.round(ano / 100);
		b = 2 - a + Math.round(ano / 400);
		jd = Math.round(1461 * (dia + 4716) / 4) + Math.round(153 * (mes + 1) / 5) + dia + b - 1524.5;

		/* Calculo de dias a partir de 1/01/2000 , t_2000 */
		double t_2000;
		t_2000 = jd - 2451544.5;

		/* Calculo da Hora Local Sideral LST */
		double lst;
		lst = 100.46 + 0.985647 * t_2000 + longi + 15 * hora_h;
		int k = 1;
		/*
		 * while(lst<0 ||lst>360) { if(lst<0){lst= lst+k*360; k++;}
		 * if(lst>360){lst= lst -k*360;k++;} }
		 */
		lst = degToRad(lst);

		/* Calculo do angulo horario HA */
		double ha = lst - ar;
		k = 1;
		/*
		 * while(ha<0){ ha=ha+2*k*Math.PI; k++; }
		 */

		/* Calculo da altitude e azimut do objeto */

		double sin_alt, alt, cos_a, a_, azim;
		sin_alt = Math.sin(dec) * Math.sin(lat) + Math.cos(dec) * Math.cos(lat) * Math.cos(ha);
		alt = Math.asin(sin_alt);

		cos_a = (Math.sin(dec) - Math.sin(alt) * Math.sin(lat)) / (Math.cos(alt) * Math.cos(lat));
		a = Math.acos(cos_a);

		if (Math.sin(ha) < 0)
			azim = a;
		else
			azim = 2 * Math.PI - a;

		/* Excluir os objetos a baixo de 10� e perto do meridiano local (5�) */
		if ((alt < 10) || (Math.abs(azim) < 0.1))
			return true;
		else
			return true;
	}
}