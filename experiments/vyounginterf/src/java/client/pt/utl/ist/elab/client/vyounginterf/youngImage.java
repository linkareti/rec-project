/*
 * youngImage.java
 *
 * Created on 25 de Mar�o de 2005, 18:40
 */

/**
 *
 * @author  Ean
 */

package pt.utl.ist.elab.client.vyounginterf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.linkare.rec.impl.i18n.ReCResourceBundle;

public class youngImage extends JComponent {

	static final int border = 20; // additional border
	static double largura = 1080; // number of lines to be painted, equal to the
	// width of the spectrum
	static double altura = 300;
	static double wl = 640; // comprimento de onda
	static double abertura = 0.15; // distancia entre fendas em mm
	static double larguraReal = 30; // em mm
	static double distanciaPlano = 0.0001; // em m

	static BufferedImage imagemBuffer = new BufferedImage((int) (largura + 2 * border), (int) (altura),
			BufferedImage.TYPE_INT_RGB);

	public static void main(String[] args) {

		JFrame frame = new JFrame("Young's Interferences Test");
		youngImage panel = new youngImage();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.BLACK);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(panel);
		panel.setPreferredSize(new Dimension(1200, 400));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.show();

		ReCResourceBundle.loadResourceBundle("ReCExpYoungInterf",
				"recresource:///pt/utl/ist/elab/virtual/client/younginterf/resources/ReCExpYoungInterf");

		JFrame frame2 = new JFrame("Young's Interferences ImageIcon Test");
		JPanel pane = new JPanel(true);

		pane.setLayout(new BorderLayout());
		pane.setBackground(Color.BLACK);
		frame2.getContentPane().setLayout(new BorderLayout());
		frame2.getContentPane().add(pane);
		pane.setPreferredSize(new Dimension(1200, 400));
		frame2.pack();
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame2.show();
		javax.swing.ImageIcon icon_ = createIcon();
		JLabel label = new JLabel(icon_);

		pane.add(label);

	}// end main

	public static javax.swing.ImageIcon createIcon() {

		javax.swing.ImageIcon icon = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().createImage(
				new java.awt.image.MemoryImageSource(imagemBuffer.getWidth(), imagemBuffer.getHeight(), Desenhar(), 0,
						imagemBuffer.getWidth())));
		return icon;
	}

	public void paint(Graphics g) {
		imagemBuffer.getGraphics().drawImage(
				java.awt.Toolkit.getDefaultToolkit().createImage(
						new java.awt.image.MemoryImageSource(imagemBuffer.getWidth(), imagemBuffer.getHeight(),
								Desenhar(), 0, imagemBuffer.getWidth())), 0, 0, null);

		g.drawImage(imagemBuffer, 0, 0, this);
	}

	public static int[] Desenhar() {

		BufferedImage imgBuf = new BufferedImage((int) (largura + 2 * border), (int) (altura),
				BufferedImage.TYPE_INT_RGB);

		Graphics g = imgBuf.getGraphics();

		// para texto
		Font little = new Font("Dialog", Font.PLAIN, 10);
		// FontMetrics littlefm = FontMetrics(little);
		Font littleBold = new Font("Dialog", Font.BOLD, 11);
		// FontMetrics littleBfm = getFontMetrics(littleBold);
		Font big = new Font("Dialog", Font.BOLD, 14);
		// FontMetrics bigfm = getFontMetrics(big);

		double derivadaI = 0;
		double gama = 0;
		double gamA = 0;
		double xReal = -larguraReal / 2;
		double dx = larguraReal / largura;
		// int xVirtual = (int)( largura*xReal/larguraReal + largura/2 );
		int xVirtual = roundToInt(largura * xReal / larguraReal + largura / 2);
		int xVirtualAnterior = xVirtual + 1;
		double gamaAnterior = gama;

		// System.out.println("o valor de dx e': "+dx);
		double preCalc = (wl * 10e-9 * distanciaPlano / (abertura * 10e-3)) * 1000;
		double deltaX = 0;
		int tag = 0;
		int tag_ = 0;
		boolean passa;
		String s1 = ReCResourceBundle.findStringOrDefault("ReCExpYoungInterf$rec.exp.display.younginterf.string",
				"Distance between maximums (minimums) (mm)");
		do {// start do cicle
			passa = true;
			gamaAnterior = gama;
			gama = CalculateGama(xReal, wl, abertura, distanciaPlano);

			derivadaI = (gama - gamaAnterior) / dx;

			while (xReal >= 0 && passa) {
				if (xReal >= -dx && xReal <= dx) {
					tag = 1;
					// System.out.println("1 X Real � : "+ xReal);
				}
				if (tag == 1 && tag_ == 1 && (gamaAnterior > gama)) {
					deltaX = xReal - (dx / 2);
					tag = 0;
					// System.out.println("2 X Real � : "+ xReal);
					// System.out.println( s1 +"  " +deltaX + " \u00b1 "+ dx
					// +" no entanto temos este como pre-calculado: " + preCalc
					// );
				}
				passa = false;
			}
			if (xReal > 2 * dx) {
				if (gamaAnterior > gama) {
					tag_ = 0;
				} else {
					tag_ = 1;
				}
			}
			// System.out.println("A derivada em "+xReal+" e' "+derivadaI);

			// if( xVirtual != xVirtualAnterior + 1 ){
			// gama = (gama + gamaAnterior)/2;
			// xVirtual = xVirtualAnterior+1;
			// System.out.println("AI QUE FIZ MERDA NO PONTO "+xVirtual+ "\n");}

			// System.out.println("gama em "+xReal+ " e' "+ gama );
			// System.out.println("xVirtual e' "+xVirtual );
			// System.out.println("xReal e' "+xReal + "\n");

			Color shade = Wavelength.wvColor((float) wl, (float) gama);

			g.setColor(shade);
			g.drawLine(xVirtual + border, 110 + 60, xVirtual + border, 190 + 60);
			g.setColor(Wavelength.wvColor((float) wl, 1));
			// g.drawLine(xVirtual+border,100- (int)(gama*100), (int)(
			// largura*(xReal+dx)/larguraReal + largura/2 )+border,100-
			// (int)((CalculateGama((xReal+dx), wl, abertura,
			// distanciaPlano))*100) );
			g.drawLine(xVirtual + border, 149 - roundToInt(gama * 100), roundToInt(largura * (xReal + dx) / larguraReal
					+ largura / 2)
					+ border, 149 - roundToInt((CalculateGama((xReal + dx), wl, abertura, distanciaPlano)) * 100));

			// xVirtualAnterior = xVirtual;
			xReal = xReal + dx;
			// xVirtual = (int)( largura*xReal/larguraReal + largura/2 );
			xVirtual = roundToInt(largura * xReal / larguraReal + largura / 2);

		} while (xReal <= (larguraReal + dx) / 2); // end do cicle

		// System.out.println("o valor de dx e': "+dx);

		g.setColor(Color.WHITE);

		g.setFont(big);
		// String title = "\u03BB = "+wl+" nm                       " + s1
		// +" =  " +(float)deltaX + " \u00b1 "+ (float)dx
		// +" mm"+"  pre-calculado: " + preCalc ;
		String title = "\u03BB = "
				+ wl
				+ " nm                                                                                                                "
				+ s1 + " =  " + (float) deltaX + " \u00b1 " + (float) dx + " mm";
		// g.drawString(title, 2+border+bigfm.stringWidth(title), 20);
		g.drawString(title, 2 + border, 20);
		// g.setFont(little);
		// g.drawString(title, 2+border+littlefm.stringWidth(title), 30);
		g.setFont(littleBold);

		int i = 0;
		// desenhar os eixos
		g.drawLine(0 + border, 101 + 50, (int) largura + border, 101 + 50);
		g.drawLine(roundToInt(largura / 2) + border, 0 + 50, roundToInt(largura / 2) + border, 100 + 50);
		g.drawLine(roundToInt(largura / 2) + border - 4, 0 + 48, roundToInt(largura / 2) + 4 + border, 0 + 48);
		g.drawString("I Max ", roundToInt(largura / 2) + border - 4, 0 + 40);
		g.setFont(little);
		// desenhar os pontos dos eixos e legenda dos eixos
		for (int conta = 0; conta <= (int) largura / 2; conta = conta + roundToInt(largura / larguraReal)) {
			String s = "" + i + "mm";
			String s_ = "" + (-i) + "mm";
			g.drawLine(border + (int) (largura / 2) + conta, 90 + 50, border + (int) (largura / 2) + conta, 100 + 50);
			g.drawLine(border + (int) (largura / 2) - conta, 90 + 50, border + (int) (largura / 2) - conta, 100 + 50);
			// g.drawString(s,border + (int)(largura/2) +1+
			// conta-(littlefm.stringWidth(s)/2) ,90+74);

			g.drawString(s, border + (int) (largura / 2) + 1 + conta - (24 / 2), 90 + 74);
			// if(i!=0)g.drawString(s_,border + (int)(largura/2) -1-
			// conta-(littlefm.stringWidth(s)/2) ,90+74);
			if (i != 0)
				g.drawString(s_, border + (int) (largura / 2) - 1 - conta - (24 / 2), 90 + 74);
			g.drawLine(border + (int) (largura / 2) + conta + roundToInt(1 * largura / (larguraReal * 2)), 100 + 50,
					border + (int) (largura / 2) + conta + roundToInt(1 * largura / (larguraReal * 2)), 95 + 50);
			g.drawLine(border + (int) (largura / 2) - conta - roundToInt(1 * largura / (larguraReal * 2)), 100 + 50,
					border + (int) (largura / 2) - conta - roundToInt(1 * largura / (larguraReal * 2)), 95 + 50);
			i++;
		}

		g.dispose();

		PixelGrabber pg = new PixelGrabber(imgBuf, 0, 0, (int) (largura + 2 * border), (int) (altura), false);

		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
		}

		int[] array = (int[]) pg.getPixels();
		// return (int[]) pg.getPixels();
		System.out.println("O tamanho de int[] e' " + array.length + "");

		return array;

	} // end paint

	/**
	 *Arredonda um double para int de acordo com as casas decimais
	 */
	public static int roundToInt(double number) {
		int rounded = 0;
		if (number % (int) number < 0.5) {
			rounded = (int) number;
		} else if (number % (int) number >= 0.5) {
			rounded = (int) number + 1;
		}
		return rounded;
	}

	/**
	 * calcula a intensidade normalizada (0-1) no ponto xReal, segundo as
	 * coordenadas do eixo contido no plano de projec�ao e com centro no maximo
	 * principal
	 */
	public static double CalculateGama(double xReal_, double wl_, double abertura_, double distanciaPlano_) { // start
		// CalculateGama
		// method
		return Math.pow(Math.cos(Math.PI * abertura_ / (1000d * wl_ * Math.pow(10, -9))
				* Math.sin(Math.atan(xReal_ / (distanciaPlano_ * 1000d)))), 2);
	} // end CalculateGama method
}// end youngImage