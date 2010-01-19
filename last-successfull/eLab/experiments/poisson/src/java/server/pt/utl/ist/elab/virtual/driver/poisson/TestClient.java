/*
 * TestClient.java
 *
 * Created on June 28, 2005, 5:23 PM
 */

package pt.utl.ist.elab.virtual.driver.poisson;

/**
 *
 * @author  andre
 */

import java.io.*;
import java.net.*;
import org.opensourcephysics.frames.*;

public class TestClient {
	private int Nx = 60;
	private int Ny = 60;
	private int Nz = 60;

	/** Creates a new instance of TestClient */
	public TestClient() {
		Socket kkSocket = null;
		OutputStream out = null;
		InputStream in = null;
		ObjectInputStream ois = null;
		String LS = System.getProperty("line.separator");

		try {
			kkSocket = new Socket("orionte.cfn.ist.utl.pt", 4444);
			out = kkSocket.getOutputStream();
			System.out.println("1");
			System.out.println("2");
			out.write(("1" + LS).getBytes());
			out.write(("1" + LS).getBytes());
			out.write(("1" + LS).getBytes());
			out.write(("1" + LS).getBytes());
			out.write(("1" + LS).getBytes());
			out.write(("1" + LS).getBytes());
			out.write(("0" + LS).getBytes());
			out.write((Nx + LS).getBytes());
			out.write((Ny + LS).getBytes());
			out.write((Nz + LS).getBytes());

			ois = new ObjectInputStream(kkSocket.getInputStream());
			double[][][] result = (double[][][]) ois.readObject();

			myScalar2DFrame(result[10], "", "", "");
			myScalar2DFrame(result[20], "", "", "");
			myScalar2DFrame(result[30], "", "", "");
			myScalar2DFrame(result[40], "", "", "");

			out.close();
			ois.close();
			kkSocket.close();
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: orionte.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: orionte.");
			System.exit(1);
		} catch (ClassNotFoundException nfe) {
			nfe.printStackTrace();
		}
	}

	// constructs and shows a Scalar2DFrame(OSP) with color legend
	private void myScalar2DFrame(double[][] data, String label1, String label2, String title) {
		Scalar2DFrame frame = new Scalar2DFrame(label1, label2, title);
		frame.setPreferredMinMax(0.0, 1.0, 0.0, 1.0);
		frame.setAutoscaleX(true);
		frame.setAutoscaleY(true);
		frame.resizeGrid(Ny + 2, Nz + 2);
		frame.setAll(data, 0, 1, 0, 1);

		frame.show();

		int w = frame.getDrawingPanel().getWidth();
		int h = frame.getDrawingPanel().getHeight();

		javax.swing.JPanel panel = new javax.swing.JPanel();
		panel.setBackground(java.awt.Color.BLUE);
		panel.setMaximumSize(new java.awt.Dimension(w, h + 50));
		panel.setMinimumSize(new java.awt.Dimension(w, h + 50));
		panel.setPreferredSize(new java.awt.Dimension(w, h + 50));
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		frame.setSize(w, frame.getHeight() + 50);
		javax.swing.JPanel legend = new javax.swing.JPanel();
		legend.setBackground(java.awt.Color.WHITE);
		legend.setMaximumSize(new java.awt.Dimension(w, 50));
		legend.setMinimumSize(new java.awt.Dimension(w, 50));
		legend.setPreferredSize(new java.awt.Dimension(w, 50));
		legend.setLayout(null);

		panel.add(legend);

		legend.setBounds(0, h, w, 50);

		javax.swing.JPanel minColor = new javax.swing.JPanel();
		minColor.setBackground(java.awt.Color.MAGENTA);
		minColor.setMaximumSize(new java.awt.Dimension(w, 50));
		minColor.setMinimumSize(new java.awt.Dimension(w, 50));
		minColor.setPreferredSize(new java.awt.Dimension(w, 50));
		minColor.setLayout(null);

		javax.swing.JPanel maxColor = new javax.swing.JPanel();
		maxColor.setBackground(java.awt.Color.RED);
		maxColor.setMaximumSize(new java.awt.Dimension(w, 50));
		maxColor.setMinimumSize(new java.awt.Dimension(w, 50));
		maxColor.setPreferredSize(new java.awt.Dimension(w, 50));
		maxColor.setLayout(null);

		javax.swing.JLabel minLabel = new javax.swing.JLabel();
		minLabel.setText("" + getMatrixMin(data));

		javax.swing.JLabel maxLabel = new javax.swing.JLabel();
		maxLabel.setText("" + getMatrixMax(data));

		legend.add(minColor);
		legend.add(maxColor);
		legend.add(minLabel);
		legend.add(maxLabel);

		minColor.setBounds(10, 20, 20, 10);
		maxColor.setBounds((int) Math.floor(w / 2) + 10, 20, 20, 10);
		minLabel.setBounds(40, 20, 100, 30);
		maxLabel.setBounds((int) Math.floor(w / 2) + 40, 20, 100, 30);

		frame.pack();
		frame.show();
	}

	// returns the minimum value of the 2d matrix m
	private double getMatrixMin(double[][] m) {
		double min = m[0][0];

		for (int x = 0; x < m.length; x++)
			for (int y = 0; y < m[0].length; y++)
				if (m[x][y] < min)
					min = m[x][y];

		return min;
	}

	// returns the maximum value of the 2d matrix m
	private double getMatrixMax(double[][] m) {
		double max = m[0][0];

		for (int x = 0; x < m.length; x++)
			for (int y = 0; y < m[0].length; y++)
				if (m[x][y] > max)
					max = m[x][y];

		return max;
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		new TestClient();
	}

}
