package com.linkare.rec.jmf.media.protocol.function.ui;

import java.awt.BorderLayout;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.event.ListDataListener;

import com.linkare.rec.jmf.media.protocol.function.FunctorType;
import com.linkare.rec.jmf.media.protocol.function.FunctorTypeControl;

public class FunctorTypeControlComponent extends JPanel {

	private FunctorTypeControl functorTypeControl;

	public FunctorTypeControlComponent(FunctorTypeControl functorTypeControl) {
		this.functorTypeControl = functorTypeControl;
		this.setLayout(new BorderLayout());

		JComboBox comboType = new JComboBox();

		comboType.setModel(new ComboBoxModel() {

			@Override
			public void removeListDataListener(ListDataListener l) {

			}

			@Override
			public int getSize() {
				return FunctorType.values().length;
			}

			@Override
			public Object getElementAt(int index) {
				return FunctorType.values()[index];
			}

			@Override
			public void addListDataListener(ListDataListener l) {

			}

			@Override
			public void setSelectedItem(Object anItem) {
				FunctorTypeControlComponent.this.functorTypeControl.setFunctorType((FunctorType) anItem);
				refreshFunctorControlComponent();
			}

			@Override
			public Object getSelectedItem() {
				return FunctorTypeControlComponent.this.functorTypeControl.getFunctorType();
			}
		});

		this.add(comboType, BorderLayout.NORTH);

		if (this.functorTypeControl.getFunctorControl() != null) {
			this.add(this.functorTypeControl.getFunctorControl().getControlComponent(), BorderLayout.CENTER);
		}

	}

	private void refreshFunctorControlComponent() {
		if (this.getComponentCount() > 1) {
			this.remove(1);
		}
		if (this.functorTypeControl != null && this.functorTypeControl.getFunctorControl() != null
				&& this.functorTypeControl.getFunctorControl().getControlComponent() != null) {
			this.add(this.functorTypeControl.getFunctorControl().getControlComponent(), BorderLayout.CENTER);
		}
		repaint();
	}

	/**
     * 
     */
	private static final long serialVersionUID = -7721689066215601186L;

}
