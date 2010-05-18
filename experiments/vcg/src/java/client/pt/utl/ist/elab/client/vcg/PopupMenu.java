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

	private ActionListener listener;

	/** Creates a new instance of PopupMenu */
	public PopupMenu() {
		setEnabled(true);
	}

	public PopupMenu(ActionListener listener) {
		setEnabled(true);
		this.listener = listener;
	}

	public void addItem(String name, String toolTip) {
		JMenuItem item = new JMenuItem(name);
		item.setToolTipText(toolTip);

		if (listener == null)
			item.addActionListener(this);
		else
			item.addActionListener(listener);

		add(item);
	}

	public void addCheckBoxItem(String name, String toolTip) {
		JCheckBox item = new JCheckBox(name);
		item.setToolTipText(toolTip);

		if (listener == null)
			item.addActionListener(this);
		else
			item.addActionListener(listener);

		add(item);
	}

	public void actionPerformed(ActionEvent e) {
	}

	/*
	 * 
	 * sliderConfig : 0 -> majorTickSpacing 1 -> Maximum 2 -> Minimum 3 ->
	 * MinorTickSpacing
	 */
	public static int dialog(String title, String toolTip, String okToolTip, int value, int[] sliderConfig) {
		java.awt.GridBagConstraints gridBagConstraints;

		JDialog tempDialog = new JDialog(new JFrame(), title, true);

		JPanel dialogPanel = new JPanel();
		JTextField dialogTextField = new JTextField();

		JSlider dialogSlider = new JSlider();
		JButton okButton = new JButton();

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
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
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
			public void focusLost(java.awt.event.FocusEvent evt) {
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
			public void actionPerformed(java.awt.event.ActionEvent evt) {
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

		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Dimension tempSize = tempDialog.getSize();
		tempDialog.setLocation((int) ((screenSize.width - tempSize.getWidth()) / 2d),
				(int) ((screenSize.height - tempSize.getHeight()) / 2d));

		tempDialog.show();

		return dialogSlider.getValue();
	}

}
