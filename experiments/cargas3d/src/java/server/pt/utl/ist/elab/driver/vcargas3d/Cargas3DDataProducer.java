/*
 * Cargas3DDataProducer.java
 *
 * Created on 22 de Mar�o de 2005, 14:37
 */

package pt.utl.ist.elab.driver.vcargas3d;

/**
 *
 * @author  n0dP2
 */

import org.opensourcephysics.displayejs.InteractiveCharge;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;
import pt.utl.ist.elab.driver.virtual.utils.ByteUtil;

import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

public class Cargas3DDataProducer extends VirtualBaseDataSource {
	// public DrawingPanel3D panel = new DrawingPanel3D();

	private final float k = 8.9875E9f;
	// O numero de canais(de dados) que existem!
	private int NUM_CHANNELS = 2;

	private VirtualBaseDriver driver = null;
	private java.util.ArrayList sistema;

	// Aproveitamos para inicializar todas as vari�veis logo no construtor...
	public Cargas3DDataProducer(VirtualBaseDriver driver, java.util.ArrayList sistema) {
		this.driver = driver;
		this.sistema = sistema;
	}

	public Cargas3DDataProducer(java.util.ArrayList sistema) {
		// panel.setPreferredMinMax(0,10,0,10, 0,10);
		this.sistema = sistema;
	}

	// Este � o processo que nos vai simular e criar as amostras para enviar ao
	// cliente!
	private class ProducerThread extends Thread {

		private java.util.ArrayList linhas = new java.util.ArrayList();
		private java.util.ArrayList[][] superficies = new java.util.ArrayList[3][20];

		private float x, y, z;
		private float rx, ry, rz, r;
		private float Ex = 0, Ey = 0, Ez = 0;
		private float EF;
		private float ExF = 0, EyF = 0, EzF = 0, E = 0;

		public java.util.ArrayList calculaLinhas() {
			for (int i = 0; i < sistema.size(); i++) {
				pontos(3, i);
			}
			return linhas;
		}

		public java.util.ArrayList[][] calculaSuperficies() {
			calculaSuperficiesMaxMin();
			calculaSuperficiesFinal();
			return superficies;
		}

		// porque 20?
		// do espaco dividido em 50, apenas sao mais relevantes
		// para estudo os primeiros 20
		java.util.ArrayList listax[] = new java.util.ArrayList[20];
		java.util.ArrayList listay[] = new java.util.ArrayList[20];
		java.util.ArrayList listaz[] = new java.util.ArrayList[20];
		double EFmin, EFmax;
		// EFmin e EFmax arredondados
		int EFFmin, EFFmax;

		private void calculaSuperficiesMaxMin() {
			// campo minimo,seja numero muito grande
			// para obter-mos o minimo real
			EFmin = 1E100;
			EFmax = 0;

			for (x = 0f; x < 10; x = x + 0.1f) {
				for (y = 0f; y < 10; y = y + 0.1f) {
					for (z = 0f; z < 10; z = z + 0.1f) {

						calculaCampo();
						int EFF = pt.utl.ist.elab.client.virtual.guipack.QMethods.arredondarInt(EF);

						// considere-se apenas o campo dentro de certos
						// limites na caixa
						boolean minmax = true;
						for (int i = 0; i < sistema.size() && minmax; i++) { // se
							// for
							// uma
							// fez
							// falso,
							// seja
							// para
							// sempre
							InteractiveCharge carga = ((InteractiveCharge) sistema.get(i));
							if ((x < carga.getX() + 0.5 && x > carga.getX() - 0.5)
									|| (y < carga.getY() + 0.5 && y > carga.getY() - 0.5)
									|| (z < carga.getZ() + 0.5 && z > carga.getZ() - 0.5) || z > 8 || z < 3 || y > 8
									|| y < 3 || x > 8 || x < 3)
								minmax = false;
							else
								minmax = true;
						}
						if (minmax == true) {
							EFmax = Math.max(EF, EFmax);
							EFmin = Math.min(EF, EFmin);
						}
					}
				}
			}
			EFFmin = pt.utl.ist.elab.client.virtual.guipack.QMethods.arredondarInt(EFmin);
			EFFmax = pt.utl.ist.elab.client.virtual.guipack.QMethods.arredondarInt(EFmax);
			// calculou-se o minimo e maximo
			// volte-se a calcular para selecionar os valores
			// tendo em consideracao o max e o min
		}

		private void calculaSuperficiesFinal() {
			// considere-se o espaco dividido em 50

			// divisao do espaco a considerar para
			// o modulo do campo
			int razao = pt.utl.ist.elab.client.virtual.guipack.QMethods.arredondarInt((EFmax - EFmin) / 50);

			// inicializem-se as listas
			for (int i = 0; i < 20; i++) {
				listax[i] = new java.util.ArrayList();
				listay[i] = new java.util.ArrayList();
				listaz[i] = new java.util.ArrayList();
			}

			for (x = 0f; x < 10; x = x + 0.1f) {
				for (y = 0f; y < 10; y = y + 0.1f) {
					for (z = 0f; z < 10; z = z + 0.1f) {

						calculaCampo();
						int EFF = pt.utl.ist.elab.client.virtual.guipack.QMethods.arredondarInt(EF);
						// o meio e' a tolerancia em guardar os pontos
						int meio = (int) (EFF * 0.02);
						for (int i = 1; i <= 20; i++) {

							int modulo = i * razao + EFFmin;
							// adicione-se a lista
							if (modulo > EFF - meio && modulo < EFF + meio && EFF < EFFmax) {
								listax[i - 1].add(new Float(x));
								listay[i - 1].add(new Float(y));
								listaz[i - 1].add(new Float(z));
							}
						}
					}
				}
			}
			superficies[0] = listax;
			superficies[1] = listay;
			superficies[2] = listaz;
			// setArray();
		}

		private void calculaCampo() {
			Ex = 0;
			Ey = 0;
			Ez = 0;
			ExF = 0;
			EyF = 0;
			EzF = 0;
			E = 0;

			for (int i = 0; i < sistema.size(); i++) {
				InteractiveCharge carga = ((InteractiveCharge) sistema.get(i));
				rx = x - (float) (carga).getX();
				ry = y - (float) (carga).getY();
				rz = z - (float) (carga).getZ();
				r = (float) Math.sqrt(rx * rx + ry * ry + rz * rz);
				float q = carga.getCharge();
				E = (float) (k * q * 1E-6 / (r * r));

				Ex = E * rx / r;
				Ey = E * ry / r;
				Ez = E * rz / r;

				ExF = ExF + Ex;
				EyF = EyF + Ey;
				EzF = EzF + Ez;
			}

			EF = (float) Math.sqrt(ExF * ExF + EzF * EzF + EyF * EyF);
		}

		private void pontos(int densidade, int nparticula) {
			InteractiveCharge carga = ((InteractiveCharge) sistema.get(nparticula));
			double r = 0.15;
			double x = 0, y = 0, z = 0;
			double x1, y1, z1;

			x1 = -r / 2;
			y1 = -r / 2;
			z1 = -r / 2;

			for (double k = 0; k <= r; k += r / densidade) {
				for (double j = 0; j <= r; j += r / densidade) {
					for (double i = 0; i <= r; i += r / densidade) {

						double norma = Math.sqrt((i + x1) * (i + x1) + (j + y1) * (j + y1) + (k + z1) * (k + z1));
						x = (x1 + i) / (norma) * r;
						y = (y1 + j) / (norma) * r;
						z = (z1 + k) / (norma) * r;
						// calcula para cada ponto da superficies esferica, para
						// cada esfera
						linhas
								.add(calculaLinha(carga.getX() + x, carga.getY() + y, carga.getZ() + z, carga
										.getCharge()));
					}
				}
			}
		}

		private java.util.ArrayList calculaLinha(double x0, double y0, double z0, double Q) {
			java.util.ArrayList linha = new java.util.ArrayList();
			if (Q < 0) {
				linha.add("neg");
			}
			if (Q > 0) {
				linha.add("pos");
			}
			if (Q == 0) {
				linha.add("nul");
			}
			final float passo = 0.05f;
			x = (float) x0;
			y = (float) y0;
			z = (float) z0;

			boolean controla = true;
			int controlador = 0;
			while (x < 10 && x > 0 && y < 10 && y > 0 && z < 10 && z > 0 && controla) {
				calculaCampo();
				// inverta-se o sentido das linhas de campo
				// caso a carga seja negativa
				if (Q < 0) {
					ExF = -ExF;
					EyF = -EyF;
					EzF = -EzF;
				}

				x = x + (ExF / EF) * passo;
				y = y + (EyF / EF) * passo;
				z = z + (EzF / EF) * passo;

				controlador++;
				// seguranca apenas
				// previne OutOfMemoryError
				if (controlador > 1500) {
					controla = false;
				}

				// adicione-se ponto ao traco
				Float[] linhaXYZ = new Float[3];
				linhaXYZ[0] = new Float(x);
				linhaXYZ[1] = new Float(y);
				linhaXYZ[2] = new Float(z);
				linha.add(linhaXYZ);

				// se a linha for de encontro a uma carga, pare-se o calculo
				for (int i = 0; i < sistema.size(); i++) {
					InteractiveCharge carga = ((InteractiveCharge) sistema.get(i));
					if ((x < carga.getX() + 0.15 && x > carga.getX() - 0.15)
							&& (y < carga.getY() + 0.15 && y > carga.getY() - 0.15)
							&& (z < carga.getZ() + 0.15 && z > carga.getZ() - 0.15))
						controla = false;
				}
			}

			return linha;
		}

		public void run() {
			// java.util.ArrayList sup20=new java.util.ArrayList();
			// toPanelSuperficies(calculaSuperficies(),sup20);
			// panel.addDrawable((org.opensourcephysics.displayejs.InteractivePoints)(sup20.get(10)));
			// panel.repaint();
			PhysicsValue[] value = new PhysicsValue[NUM_CHANNELS];

			// envia no canal CORRESPONDENTE!!! o valor
			PhysicsVal val = PhysicsValFactory
					.fromByteArray(ByteUtil.getObjectAsByteArray(calculaLinhas()), "data/raw");
			value[0] = new PhysicsValue(val, null, com.linkare.rec.data.Multiplier.none);
			val = PhysicsValFactory.fromByteArray(ByteUtil.getObjectAsByteArray(calculaSuperficies()), "data/raw");
			value[1] = new PhysicsValue(val, null, com.linkare.rec.data.Multiplier.none);

			addDataRow(value);
			try {
				join(100);
			} catch (InterruptedException ie) {
			}
			endProduction();

			driver.stopVirtualHardware();

		}
	}

	// public static void main(String args[]){
	// pt.utl.ist.elab.virtual.client.cargas3d.Sistema.novaCarga(5f, 8f, 5f,
	// -25f);
	// pt.utl.ist.elab.virtual.client.cargas3d.Sistema.novaCarga(5f, 2f, 5f,
	// 25f);
	// pt.utl.ist.elab.virtual.client.cargas3d.Sistema.novaCarga(1f, 2f, 3f,
	// 10f);
	// Cargas3DDataProducer hips = new Cargas3DDataProducer(
	// pt.utl.ist.elab.virtual.client.cargas3d.Sistema.sistema);
	// hips.startProduction();
	// javax.swing.JFrame frame = new javax.swing.JFrame();
	// frame.getContentPane().add(hips.panel);
	// frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	// frame.pack();
	// frame.show();
	// }

	public void startProduction() {
		new ProducerThread().start();
	}

	public void endProduction() {
		setDataSourceEnded();
	}

	public void stopNow() {
		setDataSourceStoped();
	}

}