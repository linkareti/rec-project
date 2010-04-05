/*
 * DataProducer.java
 *
 * Created on February 13, 2004, 12:13 PM
 */

package pt.utl.ist.elab.virtual.driver.poisson;

/**
 *
 * @author  andre
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;
import pt.utl.ist.elab.driver.virtual.utils.ByteUtil;

import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

public class PoissonDataProducer extends VirtualBaseDataSource {
	private int NUM_CHANNELS = 1;

	private String nx = null;
	private String ny = null;
	private String nz = null;
	private String fnFace1 = null;
	private String fnFace2 = null;
	private String fnFace3 = null;
	private String fnFace4 = null;
	private String fnFace5 = null;
	private String fnFace6 = null;
	private String fnRho = null;

	private String server = "orionte.cfn.ist.utl.pt";
	private int port = 4444;

	private boolean stopped = false;
	private VirtualBaseDriver driver = null;

	// Aproveitamos para inicializar todas as variáveis logo no construtor...
	public PoissonDataProducer(VirtualBaseDriver driver, String nx, String ny, String nz, String fnFace1,
			String fnFace2, String fnFace3, String fnFace4, String fnFace5, String fnFace6, String fnRho) {
		this.driver = driver;
		this.nx = nx;
		this.ny = ny;
		this.nz = nz;
		this.fnFace1 = fnFace1;
		this.fnFace2 = fnFace2;
		this.fnFace3 = fnFace3;
		this.fnFace4 = fnFace4;
		this.fnFace5 = fnFace5;
		this.fnFace6 = fnFace6;
		this.fnRho = fnRho;
	}

	// Este é o processo que nos vai simular e criar as amostras para enviar ao
	// cliente!
	private class ProducerThread extends Thread {
		private int currentSample = 0;
		private float time = 0;

		public void run() {
			try {
				sleep(1000);

				Socket kkSocket = null;
				OutputStream out = null;
				InputStream in = null;
				ObjectInputStream ois = null;
				String LS = System.getProperty("line.separator");

				try {
					kkSocket = new Socket(server, port);
					out = kkSocket.getOutputStream();
					System.out.println("1");
					System.out.println("2");
					out.write((fnFace1 + LS).getBytes());
					out.write((fnFace2 + LS).getBytes());
					out.write((fnFace3 + LS).getBytes());
					out.write((fnFace4 + LS).getBytes());
					out.write((fnFace5 + LS).getBytes());
					out.write((fnFace6 + LS).getBytes());
					out.write((fnRho + LS).getBytes());
					out.write((nx + LS).getBytes());
					out.write((ny + LS).getBytes());
					out.write((nz + LS).getBytes());

					ois = new ObjectInputStream(kkSocket.getInputStream());
					double[][][] result = (double[][][]) ois.readObject();

					out.close();
					ois.close();
					kkSocket.close();

					PhysicsValue[] value = new PhysicsValue[NUM_CHANNELS];
					PhysicsVal val = PhysicsValFactory.fromByteArray(ByteUtil.getObjectAsByteArray(result), "data/raw");
					value[0] = new PhysicsValue(val, null, com.linkare.rec.data.Multiplier.none);
					addDataRow(value);
				} catch (UnknownHostException e) {
					System.err.println("Don't know about host: " + server);
					System.exit(1);
				} catch (IOException e) {
					System.err.println("Couldn't get I/O for the connection to: " + server);
					System.exit(1);
				} catch (ClassNotFoundException nfe) {
					nfe.printStackTrace();
				}

				join(100);
				endProduction();

				driver.stopVirtualHardware();

			} catch (InterruptedException ie) {
			}

		}
	}

	public void startProduction() {
		stopped = false;
		new ProducerThread().start();
	}

	public void endProduction() {
		stopped = true;
		setDataSourceEnded();
	}

	public static void main(String args[]) {
		/*
		 * DPendulumDataProducer dp = new DPendulumDataProducer(null,90, 90, 10,
		 * 0, 0.5f, 1.5f, 0.3f, 0.5f, 10, 10000); dp.startProduction();
		 */
	}

	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}
}
