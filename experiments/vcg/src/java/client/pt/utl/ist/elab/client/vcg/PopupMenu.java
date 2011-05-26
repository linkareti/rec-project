package pt.utl.ist.elab.client.vcg;

/*
 * PopupMenu.java
 * v0.1
 *
 * Antonio Jose Rodrigues Figueiredo
 *
 * Created on 27 de Outubro de 2004, 5:18
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 * 
 * @author nomead
 * 
 */
public class PopupMenu extends JPopupMenu implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7671998174317494354L;
	private ActionListener listener;

	/** Creates a new instance of PopupMenu */
	public PopupMenu() {
		setEnabled(true);
	}

	public PopupMenu(final ActionListener listener) {
		setEnabled(true);
		this.listener = listener;
	}

	public void addItem(final String name, final String toolTip) {
		final JMenuItem item = new JMenuItem(name);
		item.setToolTipText(toolTip);

		if (listener == null) {
			item.addActionListener(this);
		} else {
			item.addActionListener(listener);
		}

		add(item);
	}

	public void addCheckBoxItem(final String name, final String toolTip) {
		final JCheckBox item = new JCheckBox(name);
		item.setToolTipText(toolTip);

		if (listener == null) {
			item.addActionListener(this);
		} else {
			item.addActionListener(listener);
		}

		add(item);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
	}

	/*
	 * 
	 * sliderConfig : 0 -> majorTickSpacing 1 -> Maximum 2 -> Minimum 3 ->
	 * MinorTickSpacing
	 */
	public static int dialog(final String title, final String toolTip, final String okToolTip, final int value,
			final int[] sliderConfig) {
		java.awt.GridBagConstraints gridBagConstraints;

		final JDialog tempDialog = new JDialog(new JFrame(), title, true);

		final JPanel dialogPanel = new JPanel();
		final JTextField dialogTextField = new JTextField();

		final JSlider dialogSlider = new JSlider();
		final JButton okButton = new JButton();

		tempDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		dialogPanel.setLayout(new java.awt.GridBagLayout());

		dialogSlider.setMinimumSize(new java.awt.Dimension(300, 47));
		dialogSlider.setPreferredSize(new java.awt.Dimension(300, 47));

		dialogSlider.setMajorTickSpacing(sliderConfig[0]);
		dialogSlider.setMaximum(sliderConfig[1]);
		dialogSlider.setMinimum(sliderConfig[2]);
		dialogSlider.setMinorTickSpacing(sliderConfig[3]);
		dialogSlider.setValue(value);

		dialogSlider.setPaintLabels(true);
		dialogSlider.setPaintTicks(true);

		dialogSlider.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				((JTextField) ((JSlider) evt.getSource()).getParent().getComponents()[1]).setText(""
						+ ((JSlider) evt.getSource()).getValue());
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		dialogPanel.add(dialogSlider, gridBagConstraints);

		dialogPanel.setBorder(new javax.swing.border.TitledBorder(title));
		dialogPanel.setToolTipText(toolTip);
		dialogTextField.setText("" + dialogSlider.getValue());
		dialogTextField.setMinimumSize(new java.awt.Dimension(62, 20));
		dialogTextField.setPreferredSize(new java.awt.Dimension(62, 20));
		dialogTextField.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				CGCustomizer.validateInput((JTextField) evt.getSource(), ((JSlider) ((JTextField) evt.getSource())
						.getParent().getComponents()[0]), 1);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		dialogPanel.add(dialogTextField, gridBagConstraints);

		okButton.setText("Ok");
		okButton.setToolTipText(okToolTip);
		okButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				((JDialog) ((JButton) evt.getSource()).getParent().getParent().getParent().getParent().getParent())
						.dispose();
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		dialogPanel.add(okButton, gridBagConstraints);

		tempDialog.getContentPane().add(dialogPanel, java.awt.BorderLayout.CENTER);

		tempDialog.pack();

		final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		final java.awt.Dimension tempSize = tempDialog.getSize();
		tempDialog.setLocation((int) ((screenSize.width - tempSize.getWidth()) / 2d),
				(int) ((screenSize.height - tempSize.getHeight()) / 2d));

		tempDialog.show();

		return dialogSlider.getValue();
	}

}
