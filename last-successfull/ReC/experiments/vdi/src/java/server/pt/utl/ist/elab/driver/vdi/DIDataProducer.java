/*
 * DIDataProducer.java
 *
 * Created on April 3, 2005, 10:13 PM
 */

package pt.utl.ist.elab.driver.vdi;

/**
 *
 * @author  Queiro'
 */

import org.opensourcephysics.numerics.ODESolver;
import org.opensourcephysics.numerics.RK4;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

public class DIDataProducer extends VirtualBaseDataSource {
	// O numero de canais(de dados) que existem!
	private final int NUM_CHANNELS = 11;

	private double tempo = 0;

	private final Disco disco1, disco2;
	private final ODESolver odeSolver1, odeSolver2;

	private int tbs = 30;
	private int nSamples = 200;

	private boolean stopped = false;
	private VirtualBaseDriver driver = null;

	// Aproveitamos para inicializar todas as variaveis logo no construtor...
	public DIDataProducer(final VirtualBaseDriver driver, final double r1i, final double r1e, final double r2i,
			final double r2e, final double m1, final double m2, final double inc, final int tbs, final int nSamples) {
		this.driver = driver;
		this.tbs = tbs;
		this.nSamples = nSamples;

		disco1 = new Disco(r1i, r1e, Math.toRadians(inc));
		disco2 = new Disco(r2i, r2e, Math.toRadians(inc));

		odeSolver1 = new RK4(disco1);
		odeSolver1.initialize(tbs / 1000D);

		odeSolver2 = new RK4(disco2);
		odeSolver2.initialize(tbs / 1000D);
	}

	// Este e' o processo que nos vai simular e criar as amostras para enviar ao
	// cliente!
	private class ProducerThread extends Thread {
		private int currentSample = 0;

		@Override
		public void run() {
			try {
				Thread.sleep(1000);

				// Enquanto a amostra actual for menor do que o numero de
				// amostras pedido pelo cliente E ninguem tiver parado a
				// exp...produz dados
				while (currentSample < nSamples && !stopped) {
					// envia as amostra calculadas!
					// 1- cria um array com o numero de canais existentes!
					final PhysicsValue[] value = new PhysicsValue[NUM_CHANNELS];

					// envia no canal CORRESPONDENTE!!! o valor
					value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) tempo), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getMultiplier());
					value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) disco1.getState()[0]),
							getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier());
					value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float) disco2.getState()[0]),
							getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getMultiplier());
					value[3] = new PhysicsValue(PhysicsValFactory.fromFloat((float) disco1.getState()[2]),
							getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getMultiplier());
					value[4] = new PhysicsValue(PhysicsValFactory.fromFloat((float) disco2.getState()[2]),
							getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getMultiplier());
					value[5] = new PhysicsValue(PhysicsValFactory.fromFloat((float) disco1.getModuloVelocidade()),
							getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getMultiplier());
					value[6] = new PhysicsValue(PhysicsValFactory.fromFloat((float) disco2.getModuloVelocidade()),
							getAcquisitionHeader().getChannelsConfig(6).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(6).getSelectedScale().getMultiplier());
					value[7] = new PhysicsValue(PhysicsValFactory.fromFloat((float) disco1.getState()[5]),
							getAcquisitionHeader().getChannelsConfig(7).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(7).getSelectedScale().getMultiplier());
					value[8] = new PhysicsValue(PhysicsValFactory.fromFloat((float) disco2.getState()[5]),
							getAcquisitionHeader().getChannelsConfig(8).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(8).getSelectedScale().getMultiplier());
					value[9] = new PhysicsValue(PhysicsValFactory.fromFloat((float) disco1.getEspacoPercorrido()),
							getAcquisitionHeader().getChannelsConfig(9).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(9).getSelectedScale().getMultiplier());
					value[10] = new PhysicsValue(PhysicsValFactory.fromFloat((float) disco2.getEspacoPercorrido()),
							getAcquisitionHeader().getChannelsConfig(10).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(10).getSelectedScale().getMultiplier());

					addDataRow(value);

					// incrementa o tempo
					tempo += tbs / 1000F;
					odeSolver1.step();
					odeSolver2.step();
					// dorme o tbs que o utilizador pediu...
					Thread.sleep(tbs);

					currentSample++;
				}
				join(100);
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

	public static void main(final String args[]) {
		/*
		 * DPendulumDataProducer dp = new DPendulumDataProducer(null,90, 90, 10,
		 * 0, 0.5f, 1.5f, 0.3f, 0.5f, 10, 10000); dp.startProduction();
		 */
	}

	@Override
	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}
}