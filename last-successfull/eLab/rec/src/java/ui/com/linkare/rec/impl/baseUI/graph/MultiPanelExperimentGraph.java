/*
 * MultiPanelExperimentGraph.java
 *
 * Created on June 11, 2004, 1:02 PM
 */

package com.linkare.rec.impl.baseUI.graph;

import javax.swing.Icon;

import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class MultiPanelExperimentGraph extends javax.swing.JPanel implements ExpDataDisplay, ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3959924377110995179L;
	private Object[] expDataList = null;
	private final javax.swing.JPanel jPanelContainer;
	private final javax.swing.JScrollPane jScrollPane1;

	public MultiPanelExperimentGraph(final Object[] expDataList) {
		this.expDataList = expDataList;
		setLayout(new java.awt.BorderLayout());

		jScrollPane1 = new javax.swing.JScrollPane();
		jPanelContainer = new javax.swing.JPanel();

		jPanelContainer.setLayout(new java.awt.GridLayout(expDataList.length, 1));
		for (final Object element : expDataList) {
			jPanelContainer.add((java.awt.Component) element);
		}

		jScrollPane1.setViewportView(jPanelContainer);

		add(jScrollPane1, java.awt.BorderLayout.CENTER);
	}

	@Override
	public void dataModelStoped() {
		for (final Object element : expDataList) {
			((ExpDataModelListener) (element)).dataModelStoped();
		}
	}

	@Override
	public javax.swing.JComponent getDisplay() {
		return this;
	}

	@Override
	public Icon getIcon() {
		return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/chart16.gif"));
	}

	@Override
	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	@Override
	public javax.swing.JToolBar getToolBar() {
		return null;
	}

	@Override
	public void newSamples(final NewExpDataEvent evt) {
		for (final Object element : expDataList) {
			((ExpDataModelListener) (element)).newSamples(evt);
		}
	}

	@Override
	public void setExpDataModel(final ExpDataModel model) {
		for (final Object element : expDataList) {
			((ExpDataDisplay) (element)).setExpDataModel(model);
		}
	}

	@Override
	public void dataModelEnded() {
		for (final Object element : expDataList) {
			((ExpDataModelListener) (element)).dataModelEnded();
		}
	}

	@Override
	public void dataModelError() {
		for (final Object element : expDataList) {
			((ExpDataModelListener) (element)).dataModelError();
		}
	}

	@Override
	public void dataModelStarted() {
		for (final Object element : expDataList) {
			((ExpDataModelListener) (element)).dataModelStarted();
		}
	}

	@Override
	public void dataModelStartedNoData() {
		for (final Object element : expDataList) {
			((ExpDataModelListener) (element)).dataModelStartedNoData();
		}
	}

	@Override
	public void dataModelWaiting() {
		for (final Object element : expDataList) {
			((ExpDataModelListener) (element)).dataModelWaiting();
		}
	}

}
