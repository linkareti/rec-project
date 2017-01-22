/*
 * AccumulatedGraphDisplay.java
 *
 * Created on 25 de Junho de 2003, 17:56
 */

package pt.utl.ist.elab.client.aleatorio.displays;

import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
import javax.swing.SwingConstants;

/**
 * 
 * @author Pedro Carvalho - LEFT - IST
 */
public class AccumulatedGraphDisplay extends javax.swing.JPanel implements
		com.linkare.rec.impl.client.experiment.ExpDataDisplay,
		com.linkare.rec.impl.client.experiment.ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3869131619333980671L;

	/** Creates new form AccumulatedGraphDisplay */
	public AccumulatedGraphDisplay() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {//GEN-BEGIN:initComponents
		java.awt.GridBagConstraints gridBagConstraints;

		accuStatValuesPanel = new javax.swing.JPanel();
		muLabel = new javax.swing.JLabel();
		muText = new javax.swing.JTextField();
		sigmaLabel = new javax.swing.JLabel();
		sigmaText = new javax.swing.JTextField();
		y0Label = new javax.swing.JLabel();
		y0Text = new javax.swing.JTextField();
		ampLabel = new javax.swing.JLabel();
		ampText = new javax.swing.JTextField();
		chiSqLabel = new javax.swing.JLabel();
		chiSqText = new javax.swing.JTextField();
		countsLabel = new javax.swing.JLabel();
		countsText = new javax.swing.JTextField();
		accuStatScrollPane = new javax.swing.JScrollPane();

		setLayout(new java.awt.GridBagLayout());

		accuStatValuesPanel.setLayout(new java.awt.GridLayout(3, 4, 5, 5));

		accuStatValuesPanel.setBorder(new javax.swing.border.TitledBorder("Session Parameters"));
		accuStatValuesPanel.setMinimumSize(new java.awt.Dimension(400, 50));
		accuStatValuesPanel.setName("AccuStatValuesPanel");
		accuStatValuesPanel.setPreferredSize(new java.awt.Dimension(400, 80));
		muLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		muLabel.setText(" Mean Value =");
		muLabel.setName("muLabel");
		accuStatValuesPanel.add(muLabel);

		muText.setBackground(new java.awt.Color(204, 204, 204));
		muText.setEditable(false);
		muText.setHorizontalAlignment(SwingConstants.LEFT);
		muText.setMinimumSize(new java.awt.Dimension(40, 20));
		muText.setName("muText");
		accuStatValuesPanel.add(muText);

		sigmaLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		sigmaLabel.setText("Sigma =");
		sigmaLabel.setName("sigmaLabel");
		accuStatValuesPanel.add(sigmaLabel);

		sigmaText.setBackground(new java.awt.Color(204, 204, 204));
		sigmaText.setEditable(false);
		sigmaText.setHorizontalAlignment(SwingConstants.LEFT);
		sigmaText.setMinimumSize(new java.awt.Dimension(40, 20));
		sigmaText.setName("sigmaText");
		accuStatValuesPanel.add(sigmaText);

		y0Label.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		y0Label.setText("Y_0 =");
		y0Label.setName("y0Label");
		accuStatValuesPanel.add(y0Label);

		y0Text.setBackground(new java.awt.Color(204, 204, 204));
		y0Text.setEditable(false);
		y0Text.setHorizontalAlignment(SwingConstants.LEFT);
		y0Text.setMinimumSize(new java.awt.Dimension(40, 20));
		y0Text.setName("y0Text");
		accuStatValuesPanel.add(y0Text);

		ampLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		ampLabel.setText("Amplitude =");
		ampLabel.setName("ampLabel");
		accuStatValuesPanel.add(ampLabel);

		ampText.setBackground(new java.awt.Color(204, 204, 204));
		ampText.setEditable(false);
		ampText.setHorizontalAlignment(SwingConstants.LEFT);
		ampText.setName("ampText");
		accuStatValuesPanel.add(ampText);

		chiSqLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		chiSqLabel.setText("\u03c7\u00b2 =");
		accuStatValuesPanel.add(chiSqLabel);

		chiSqText.setEditable(false);
		accuStatValuesPanel.add(chiSqText);

		countsLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		countsLabel.setText("Counts =");
		accuStatValuesPanel.add(countsLabel);

		countsText.setEditable(false);
		accuStatValuesPanel.add(countsText);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		add(accuStatValuesPanel, gridBagConstraints);

		accuStatScrollPane.setBorder(new javax.swing.border.TitledBorder("Accumulated Statistics"));
		add(accuStatScrollPane, new java.awt.GridBagConstraints());

	}//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JScrollPane accuStatScrollPane;
	private javax.swing.JTextField y0Text;
	private javax.swing.JTextField ampText;
	private javax.swing.JTextField sigmaText;
	private javax.swing.JLabel ampLabel;
	private javax.swing.JLabel muLabel;
	private javax.swing.JTextField muText;
	private javax.swing.JTextField countsText;
	private javax.swing.JTextField chiSqText;
	private javax.swing.JLabel y0Label;
	private javax.swing.JLabel chiSqLabel;
	private javax.swing.JLabel countsLabel;
	private javax.swing.JPanel accuStatValuesPanel;
	private javax.swing.JLabel sigmaLabel;
	// End of variables declaration//GEN-END:variables

	private int counts = 0;
	private GraphPanel graphPanel;
	private final ImageStorePanel imageStorePanel = new ImageStorePanel();
	private double[] accuX = null, accuY = null;
	private int[] xx;
	private double mu, sigma, y0, Amp;
	private com.linkare.rec.impl.client.experiment.ExpDataModel model;
	private final String name = "Accumulated Statistics";

	private final javax.swing.Icon icon = new javax.swing.ImageIcon(getClass().getResource(
			"/pt/utl/ist/elab/client/aleatorio/resources/AleatorioIcon.gif"));

	/**
	 * ExpDataDisplay Implementation
	 */
	@Override
	public void setExpDataModel(final com.linkare.rec.impl.client.experiment.ExpDataModel model) {
		if (this.model != null) {
			model.removeExpDataModelListener(this);
		}

		this.model = model;

		if (this.model != null) {
			this.model.addExpDataModelListener(this);
		}

	}// setExpDataModel(ExpDataModel model)

	@Override
	public javax.swing.JComponent getDisplay() {
		return this;
	}// getDisplay()

	@Override
	public String getName() {
		return name;
	}// getName()

	@Override
	public javax.swing.Icon getIcon() {
		return icon;
	}// getIcon()

	@Override
	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}// getMenuBar()

	@Override
	public javax.swing.JToolBar getToolBar() {
		return null;
	}// getToolBar()

	/**
	 * ExpDataModelListener implementation
	 */

	@Override
	public void newSamples(final com.linkare.rec.impl.client.experiment.NewExpDataEvent evt) {
		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
			byte[] accuYByteArray = null;
			if (model.getValueAt(i, model.getChannelIndex("YY")) != null) {
				accuYByteArray = model.getValueAt(i, model.getChannelIndex("YY")).getValue().getByteArrayValue()
						.getData();
			}// if
			if (model.getValueAt(i, model.getChannelIndex("XX")) != null) {
				final byte[] xxByteArray = model.getValueAt(i, model.getChannelIndex("XX")).getValue()
						.getByteArrayValue().getData();
				xx = byteArray2IntArray(xxByteArray); // xx[0] is the first
				// element of sessionX;
				// xx[1] is the last.
				accuX = new double[xx[1] - xx[0] + 1];
				if (!accuYByteArray.equals(null)) {
					accuY = new double[accuX.length];
					int[] accuYInt = new int[accuX.length];
					accuYInt = byteArray2IntArray(accuYByteArray);
					for (int index = 0; index < accuX.length; index++) {
						accuX[index] = (xx[0] + index);
						accuY[index] = accuYInt[index];
					}// for_index
						// these are just initial values for the fitting
						// algorithm
					mu = accuX[accuX.length / 2];
					sigma = mu / 2.;
					y0 = 0.;
					Amp = getMax(accuY);
					counts = getCounts(accuY);
					graphPanel = new GraphPanel(accuX, accuY, mu, sigma, y0, Amp);
					imageStorePanel.setImage(graphPanel.getImage());
					imageStorePanel.setPreferredSize(new java.awt.Dimension(graphPanel.imageSize()[0], graphPanel
							.imageSize()[1]));
					accuStatScrollPane.setViewportView(imageStorePanel);

					// graphPanel.setPreferredSize(new
					// java.awt.Dimension(graphPanel.imageSize()[0],graphPanel.imageSize()[1]));
					// accuStatScrollPane.setViewportView(graphPanel);
					// graphPanel.repaint();

					// while
					// (accuGraphDisplaySingleton.checkNewValueAvailableAccu());
					// //This might result in something getting stuck!
					// If it does, we'll have to use some eventListener

					// for (int j = 0; j< accuY.length; j++)
					// {
					// System.out.println("accuY["+j+"]="+accuY[j]);
					// }//for_j
				}// if
			}// if
				// TODO FIXME out of bounds -14 again?!?!?
				// if (model.getValueAt(i, model.getChannelIndex("CenterCount"))
				// != null && xx != null) {
			// // sessionY[sessionGraphDisplaySingleton.sessGetStoredInt() -
			// // xx[0]]++;
			// accuY[model.getValueAt(i,
			// model.getChannelIndex("CenterCount")).getValue().getIntValue() -
			// xx[0]]++;
			//
			// double lambda = 0.01; // 0.001
			// double termEpsilon = 0.01;
			// int maxIter = 100;
			// double[] params =
			// pt.utl.ist.elab.client.aleatorio.utils.LMFit.solve(accuX, accuY,
			// mu, sigma, y0, Amp,
			// lambda, termEpsilon, maxIter);
			// graphPanel.updateGraph(accuX, accuY, params[0], params[1],
			// params[2], params[3]);
			// imageStorePanel.setImage(graphPanel.getImage());
			// imageStorePanel.repaint();
			//
			// counts++;
			// muText.setText(String.valueOf(params[0]));
			// sigmaText.setText(String.valueOf(params[1]));
			// y0Text.setText(String.valueOf(params[2]));
			// ampText.setText(String.valueOf(params[3]));
			// chiSqText.setText(String.valueOf(params[4]));
			// countsText.setText(String.valueOf(counts));
			// }// if
		}// for_i
			// System.out.println("Processed new samples in Accu!");
	}// newSamples(NewExpDataEvent evt)

	@Override
	public void dataModelStoped() {
	}// dataModelStoped()

	public void dataModelRunning() {
	}// dataModelRunning()

	public void headerAvailable(final com.linkare.rec.data.config.HardwareAcquisitionConfig header) {
	}// headerAvailable(HardwareAcquisitionConfig header)

	/**
	 * Utilities
	 */

	private int[] byteArray2IntArray(final byte[] byteArray) {
		final int[] temp = new int[byteArray.length / 4];

		for (int index = 0; index < temp.length; index++) {
			temp[index] = (byteArray[4 * index] & 0xff) << 24;
			temp[index] = (byteArray[4 * index + 1] & 0xff) << 16;
			temp[index] += (byteArray[4 * index + 2] & 0xff) << 8;
			temp[index] += (byteArray[4 * index + 3] & 0xff);
		}
		return temp;
	}// byteArray2IntArray(byte[] byteArray)

//	private int getIndexOfMax(final double[] array) {
//		double value = Double.MIN_VALUE;
//		int valueIndex = -1;
//		for (int index = 0; index < array.length; index++) {
//			if (array[index] > value) {
//				valueIndex = index;
//				value = array[index];
//			}
//		}
//		return valueIndex;
//	}// getIndexOfMax(double[] array)

	private double getMax(final double[] array) {
		if (array == null) {
			return 0.;
		}

		double value = array[0];
		for (int index = 1; index < array.length; index++) {
			value = (array[index] > value) ? array[index] : value;
		}

		return value;
	}// getMax(double[] array)

	private int getCounts(final double[] array) {
		counts = 0;
		for (final double element : array) {
			counts += (int) element;
		}// for_i
		return counts;
	}

	@Override
	public void dataModelWaiting() {
	}

	@Override
	public void dataModelStarted() {
	}

	@Override
	public void dataModelStartedNoData() {
	}

	@Override
	public void dataModelEnded() {
	}

	@Override
	public void dataModelError() {
	}

	// getCounts
    @Override
    public DataDisplayEnum getDisplayType() {
        return DataDisplayEnum.CHART;
    }
}