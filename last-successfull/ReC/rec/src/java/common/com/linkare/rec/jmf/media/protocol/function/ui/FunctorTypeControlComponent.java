package com.linkare.rec.jmf.media.protocol.function.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Window;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.event.ListDataListener;

import com.linkare.rec.jmf.media.protocol.function.FunctorType;
import com.linkare.rec.jmf.media.protocol.function.FunctorTypeControl;

public class FunctorTypeControlComponent extends JPanel {

	private FunctorTypeControl functorTypeControl;

	public FunctorTypeControlComponent(FunctorTypeControl functorTypeControl2) {
		this.functorTypeControl = functorTypeControl2;
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
				final FunctorType oldFunctor = functorTypeControl.getFunctorType();
				FunctorType newFunctor = (FunctorType) anItem;
				newFunctor.getFunctorControl().setFrequency(oldFunctor.getFunctorControl().getFrequency());
				functorTypeControl.setFunctorType(newFunctor);
				refreshFunctorControlComponent(oldFunctor);
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

	private void refreshFunctorControlComponent(FunctorType oldFunctor) {

		if (oldFunctor != null && oldFunctor.getFunctorControl() != null
				&& oldFunctor.getFunctorControl().getControlComponent() != null) {
			// removing old component
			this.remove(1);
		}
		if (this.functorTypeControl != null && this.functorTypeControl.getFunctorControl() != null
				&& this.functorTypeControl.getFunctorControl().getControlComponent() != null) {
			this.add(this.functorTypeControl.getFunctorControl().getControlComponent(), BorderLayout.CENTER);
		}
		Container parent = this.getParent();
		while (!(parent instanceof Window) && parent != null) {
			parent = parent.getParent();
		}
		if (parent != null) {
			((Window) parent).pack();
		}
	}

	/**
     * 
     */
	private static final long serialVersionUID = -7721689066215601186L;

}
