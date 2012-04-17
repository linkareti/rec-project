/*
 * YoungInterfDataProducer.java
 *
 * Created on 27 de Dezembro de 2004, 16:31
 */

package pt.utl.ist.elab.driver.vyounginterf;

/**
 *
 * @author  Emanuel Antunes
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;
import pt.utl.ist.elab.driver.virtual.utils.ByteUtil;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

public class YoungInterfDataProducer extends VirtualBaseDataSource {

	// O numero de canais(de dados) que existem!
	private final int NUM_CHANNELS = 3; // ATENCAO QUE SERAM POSICAO,
										// INTENSIDADE,
	// DADOS DA IMAGEM

	private double dfendas = 1; // distancia entre fendas em milimetros
	private double dplanos = 6.0; // distancia entre planos em metros
	private double lambda = 580; // comprimento de onda em nanometros
	private double lpadrao = 6;

	private int tbs = 100;
	private int nSamples = 10;

	private boolean stopped = false;
	private VirtualBaseDriver driver = null;

	// private ODESolver odeSolver = null;

	/** Creates a new instance of YoungInterfDataProducer 
	 * @param driver 
	 * @param dfendas 
	 * @param dplanos 
	 * @param lambda 
	 * @param lpadrao 
	 * @param tbs 
	 * @param nSamples */
	public YoungInterfDataProducer(final VirtualBaseDriver driver, final float dfendas, final float dplanos,
			final float lambda, final float lpadrao, final int tbs, final int nSamples) {
		this.driver = driver;
		this.dfendas = dfendas;
		this.dplanos = dplanos;
		this.lambda = lambda;
		this.lpadrao = lpadrao;
		this.tbs = tbs;
		this.nSamples = nSamples;

	}

	/**
	 * Arredonda um double para int de acordo com as casas decimais
	 * @param number 
	 * @return 
	 */
	public static int roundToInt(final double number) {
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
	 * @param xReal_ 
	 * @param wl_ 
	 * @param abertura_ 
	 * @param distanciaPlano_ 
	 * @return 
	 */
	public static double CalculateGama(final double xReal_, final double wl_, final double abertura_,
			final double distanciaPlano_) { // start
		// CalculateGama
		// method
		return Math.pow(
				Math.cos(Math.PI * abertura_ / (1000d * wl_ * Math.pow(10, -9))
						* Math.sin(Math.atan(xReal_ / (distanciaPlano_ * 1000d)))), 2);
	} // end CalculateGama method

	// Este e' o processo que nos vai simular e criar as amostras para enviar ao
	// cliente!
	private class ProducerThread extends Thread {
		private final int currentSample = 0;
		private final float time = 0;

		final int border = 20; // borda adicional da imagem
		double largura = 1080; // largura da imagem, corresponde ao numero de
		// tiras a calcular e pintar do padrao da imagem
		// do interferencia
		double altura = 300; // altura da imagem
		double wl = lambda; // comprimento de onda
		double abertura = dfendas; // distancia entre fendas em mm
		double larguraReal = lpadrao; // em mm
		double distanciaPlano = dplanos; // em m

		@Override
		public void run() {
			try {
				Thread.sleep(1000);

				final BufferedImage imagemBuffer = new BufferedImage((int) (largura + 2 * border), (int) (altura),
						BufferedImage.TYPE_INT_RGB);

				final Graphics g = imagemBuffer.getGraphics();

				// para texto
				final Font little = new Font("Dialog", Font.PLAIN, 10);
				final Font littleBold = new Font("Dialog", Font.BOLD, 11);
				final Font big = new Font("Dialog", Font.BOLD, 14);

				double derivadaI = 0;
				double intensidade = 0;
				double gama = 0;
				double xReal = -larguraReal / 2;
				final double dx = larguraReal / largura;
				int xVirtual = YoungInterfDataProducer.roundToInt(largura * xReal / larguraReal + largura / 2);
				final int xVirtualAnterior = xVirtual + 1;
				double gamaAnterior = gama;

				// System.out.println("o valor de dx e': "+dx);

				final double preCalc = (wl * 10e-9 * distanciaPlano / (abertura * 10e-3)) * 1000;
				double deltaX = 0;
				int tag = 0;
				int tag_ = 0;
				boolean passa;
				// String s1 = ReCResourceBundle.findStringOrDefault(
				// "younginterf$rec.exp.display.younginterf.string",
				// "Distance between maximums (minimums) (mm)");

				// envia as amostra calculadas!
				// 1- cria um array com o numero de canais existentes!
				PhysicsValue[] value = new PhysicsValue[NUM_CHANNELS];

				// Enquanto a amostra actual (n*dx )for menor do que o numero de
				// amostras (largura) pedido pelo cliente E ninguém tiver parado
				// a exp...produz dados
				do {// start do cicle

					passa = true;
					gamaAnterior = gama;
					gama = YoungInterfDataProducer.CalculateGama(xReal, wl, abertura, distanciaPlano);
					derivadaI = (gama - gamaAnterior) / dx;
					intensidade = gama * 100;

					// rotina ond ese determina a distancia do maximo central ao
					// maximo de segunda ordem
					while (xReal >= 0 && passa) {
						if (xReal >= -dx && xReal <= dx) {
							tag = 1;
							System.out.println("1 X Real � : " + xReal);
						}
						if (tag == 1 && tag_ == 1 && (gamaAnterior > gama)) {
							deltaX = xReal - (dx / 2);
							tag = 0;
							// System.out.println("2 X Real � : "+ xReal);
							// System.out.println( s1 +"  " +deltaX +
							// " \u00b1 "+ dx
							// +" no entanto temos este como pre-calculado: " +
							// preCalc );
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

					final Color shade = Wavelength.wvColor((float) wl, (float) gama);

					g.setColor(shade);
					g.drawLine(xVirtual + border, 110 + 60, xVirtual + border, 190 + 60);
					g.setColor(Wavelength.wvColor((float) wl, 1));
					g.drawLine(xVirtual + border, 149 - YoungInterfDataProducer.roundToInt(gama * 100),
							YoungInterfDataProducer.roundToInt(largura * (xReal + dx) / larguraReal + largura / 2)
									+ border, 149 - YoungInterfDataProducer.roundToInt((YoungInterfDataProducer
									.CalculateGama((xReal + dx), wl, abertura, distanciaPlano)) * 100));

					// envia as amostra calculadas!
					// 1- cria um array com o numero de canais existentes!
					value = new PhysicsValue[NUM_CHANNELS];

					// envia no canal CORRESPONDENTE!!! o valor
					value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) xReal), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getMultiplier());
					value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) intensidade),
							getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier());
					/*
					 * value[2] = new
					 * PhysicsValue(PhysicsValFactory.fromFloat((float
					 * )state[1]),
					 * getAcquisitionHeader().getChannelsConfig(2).getSelectedScale
					 * ().getDefaultErrorValue(),
					 * getAcquisitionHeader().getChannelsConfig
					 * (2).getSelectedScale().getMultiplier() );
					 */
					addDataRow(value);

					xReal = xReal + dx;
					xVirtual = YoungInterfDataProducer.roundToInt(largura * xReal / larguraReal + largura / 2);

				} while (xReal <= (larguraReal + dx) / 2 && !stopped); // end do
				// cicle

				// apresentar alguns valores importantes da Imagem
				System.out.println("o valor de dx e': " + dx);
				g.setColor(Color.WHITE);
				g.setFont(big);
				final String title = "\u03BB = "
						+ wl
						+ " nm                                                                                                                "
						+ "Distance between maximums (minimums) (mm)" + " =  " + (float) deltaX + " \u00b1 "
						+ (float) dx + " mm";
				g.drawString(title, 2 + border, 20);
				g.setFont(littleBold);

				int i = 0;

				// desenhar os eixos
				g.drawLine(0 + border, 101 + 50, (int) largura + border, 101 + 50);
				g.drawLine(YoungInterfDataProducer.roundToInt(largura / 2) + border, 0 + 50,
						YoungInterfDataProducer.roundToInt(largura / 2) + border, 100 + 50);
				g.drawLine(YoungInterfDataProducer.roundToInt(largura / 2) + border - 4, 0 + 48,
						YoungInterfDataProducer.roundToInt(largura / 2) + 4 + border, 0 + 48);
				g.drawString("I Max ", YoungInterfDataProducer.roundToInt(largura / 2) + border - 4, 0 + 40);
				g.setFont(little);

				// desenhar os pontos dos eixos e legenda dos eixos
				for (int conta = 0; conta <= (int) largura / 2; conta = conta
						+ YoungInterfDataProducer.roundToInt(largura / larguraReal)) {
					final String s = "" + i + "mm";
					final String s_ = "" + (-i) + "mm";
					g.drawLine(border + (int) (largura / 2) + conta, 90 + 50, border + (int) (largura / 2) + conta,
							100 + 50);
					g.drawLine(border + (int) (largura / 2) - conta, 90 + 50, border + (int) (largura / 2) - conta,
							100 + 50);

					g.drawString(s, border + (int) (largura / 2) + 1 + conta - (24 / 2), 90 + 74);
					if (i != 0) {
						g.drawString(s_, border + (int) (largura / 2) - 1 - conta - (24 / 2), 90 + 74);
					}
					g.drawLine(
							border + (int) (largura / 2) + conta
									+ YoungInterfDataProducer.roundToInt(1 * largura / (larguraReal * 2)),
							100 + 50,
							border + (int) (largura / 2) + conta
									+ YoungInterfDataProducer.roundToInt(1 * largura / (larguraReal * 2)), 95 + 50);
					g.drawLine(
							border + (int) (largura / 2) - conta
									- YoungInterfDataProducer.roundToInt(1 * largura / (larguraReal * 2)),
							100 + 50,
							border + (int) (largura / 2) - conta
									- YoungInterfDataProducer.roundToInt(1 * largura / (larguraReal * 2)), 95 + 50);
					i++;
				}

				g.dispose();

				/*
				 * PixelGrabber pg = new PixelGrabber(imagemBuffer, 0, 0,
				 * (int)(largura + 2*border), (int)(altura), false); try {
				 * pg.grabPixels(); } catch (InterruptedException e){}
				 * 
				 * int[] array = (int[])pg.getPixels(); return (int[])
				 * pg.getPixels(); System.out.println("O tamanho de int[] e' "+
				 * array.length +""); javax.swing.ImageIcon icon = new
				 * ImageIcon(
				 * java.awt.Toolkit.getDefaultToolkit().createImage(new
				 * java.awt.image.MemoryImageSource(imagemBuffer.getWidth(),
				 * imagemBuffer.getHeight(), array, 0,
				 * imagemBuffer.getWidth())));
				 */
				final javax.swing.ImageIcon icon = new ImageIcon(imagemBuffer);

				try {
					value = new PhysicsValue[NUM_CHANNELS];
					value[2] = new PhysicsValue(PhysicsValFactory.fromByteArray(ByteUtil.getObjectAsByteArray(icon),
							"data/raw"), null, com.linkare.rec.data.Multiplier.none);
					addDataRow(value);
					System.out.println("Byte Array produced!");
				} catch (final Throwable t) {
					t.printStackTrace();
				}

				Thread.sleep(1000);
				endProduction();

				driver.stopVirtualHardware();

			} catch (final InterruptedException ie) {
			}
		}
	}

	@Override
	public void startProduction() {
		stopped = false;
		new ProducerThread().start();
	}

	public void endProduction() {
		stopped = true;
		setDataSourceEnded();
	}

	@Override
	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}

	public static void main(final String args[]) {

	}
}