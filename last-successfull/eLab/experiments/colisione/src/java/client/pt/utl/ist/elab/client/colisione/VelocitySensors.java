/* 
 * VelocitySensors.java created on 16 de Out de 2013
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.client.colisione;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;

import org.jfree.chart.JFreeChart;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
/**
 * 
 * @author joao - Linkare TI
 */

public class VelocitySensors extends JPanel implements com.linkare.rec.impl.client.experiment.ExpDataDisplay,
com.linkare.rec.impl.client.experiment.ExpDataModelListener {
	
	private static final long serialVersionUID = 2976338158081776558L;
	//private final DialDoubleNeedle dial1 = new DialDoubleNeedle(ReCResourceBundle.findStringOrDefault("colisione$rec.exp.colisione.dial.title1", "Velocity of Car 1"));
	//private final DialDoubleNeedle dial2 = new DialDoubleNeedle(ReCResourceBundle.findStringOrDefault("colisione$rec.exp.colisione.dial.title2", "Velocity of Car 2"));
	private final DialDoubleNeedle dial1 = new DialDoubleNeedle();
	private final DialDoubleNeedle dial2 = new DialDoubleNeedle();
	
	private javax.swing.JScrollPane scrollPane;
	private javax.swing.JLabel labelWaitData;
	
	public VelocitySensors(){
		initComponents();
	}
	

	public void initComponents(){
		setLayout(new java.awt.BorderLayout());
		setBackground(new java.awt.Color(0, 0, 0));
		setForeground(new java.awt.Color(51, 51, 51));
		
		scrollPane = new javax.swing.JScrollPane();
		labelWaitData = new javax.swing.JLabel();
		
		setPreferredSize(new Dimension(dial1.getWidth() * 2 + 3 * 10, dial1.getHeight() + 2 * 10));
		setMinimumSize(getPreferredSize());
		setMaximumSize(getPreferredSize());
        
		labelWaitData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		labelWaitData.setText("waiting for data...");
		scrollPane.setViewportView(labelWaitData);
		
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
		add(scrollPane, java.awt.BorderLayout.CENTER);
		
	}
	
	
	public void headerAvailable(final HardwareAcquisitionConfig header) {
		if (header != null) {
			acqHeaderInited = true;
			this.header = header;
		}
		
		dial1.setTitle(ReCResourceBundle.findStringOrDefault("colisione$rec.exp.colisione.dial.title1", "Velocity of Car 1"));
		dial2.setTitle(ReCResourceBundle.findStringOrDefault("colisione$rec.exp.colisione.dial.title2", "Velocity of Car 2"));
		
		java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		
        JPanel panel = new JPanel();

        panel.add(dial1,gridBagConstraints);
        panel.add(dial2,gridBagConstraints);
        panel.setPreferredSize(new java.awt.Dimension(350, 300));
        
		scrollPane.remove(labelWaitData);
		scrollPane.setViewportView(panel);
	}

	private HardwareAcquisitionConfig header = null;
	private boolean acqHeaderInited = false;
	
	
	private double vel1 = 0;
	private double velmax1 = 0;
	private double vel2 = 0;
	private double velmax2 = 0;

	public void setVelocity(final PhysicsValue time1, final PhysicsValue time2) {
		if(time1.getValue().toDouble() > 0){
			double temp_vel1= 0.01/(time1.getValue().toDouble()/1000);
			if(temp_vel1 >= velmax1){
				this.velmax1 = temp_vel1;
			}
			this.vel1 = temp_vel1;
		}	
		
		if(time2.getValue().toDouble() > 0){
			double temp_vel2= 0.01/(time2.getValue().toDouble()/1000);
			if(temp_vel2 >= velmax2){
				this.velmax2 = temp_vel2;
			}
			this.vel2 = temp_vel2;
		}
		
		dial1.getDataset1().setValue(this.vel1);
		dial1.getDataset2().setValue(this.velmax1);

		dial2.getDataset1().setValue(this.vel2);
		dial2.getDataset2().setValue(this.velmax2);
		
		scrollPane.getHorizontalScrollBar().repaint();
		scrollPane.getVerticalScrollBar().repaint();
		
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newSamples(final NewExpDataEvent evt) {
		
		if (header == null && model != null) {
			headerAvailable(model.getAcquisitionConfig());
		}
		
		/*
		if (!acqHeaderInited) {
			headerAvailable(model.getAcquisitionConfig());
		}*/

		//final int lastsample = evt.getSamplesEndIndex();
		//setVelocity(model.getValueAt(lastsample, 1), model.getValueAt(lastsample, 2));
		
		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
				setVelocity(model.getValueAt(i, 1), model.getValueAt(i, 2));
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dataModelWaiting() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dataModelStartedNoData() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dataModelStarted() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dataModelEnded() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dataModelStoped() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dataModelError() {
	}

	/**
	 * {@inheritDoc}
	 */
	
	private final Icon icon = new javax.swing.ImageIcon(getClass().getResource(
			"/com/linkare/rec/impl/newface/resources/legacy/sensor16.gif"));
	
	@Override
	public Icon getIcon() {
		return icon;
	}

	/**
	 * {@inheritDoc}
	 */
	
	private ExpDataModel model = null;
	
	@Override
	public void setExpDataModel(ExpDataModel model) {
		
		if (this.model != null) {
			this.model.removeExpDataModelListener(this);
		}
		this.model = model;
		if (this.model != null) {
			this.model.addExpDataModelListener(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JComponent getDisplay() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JMenuBar getMenuBar() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JToolBar getToolBar() {
		return null;
	}
	
	@Override
	public String getName() {
		return "Velocity Dials";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataDisplayEnum getDisplayType() {
		return DataDisplayEnum.SENSOR;
		
	}

}
