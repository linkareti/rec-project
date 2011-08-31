/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ApparatusPane.java
 *
 * Created on 20/Abr/2009, 14:00:55
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Component;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.MissingResourceException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.newface.config.Apparatus;
import com.linkare.rec.impl.newface.config.WebResource;

/**
 * 
 * @author Henrique Fernandes
 */
public class ApparatusDescriptionPane extends AbstractContentPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 405323800381364687L;
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ApparatusDescriptionPane.class.getName());

	/** Creates new form ApparatusPane */
	public ApparatusDescriptionPane() {
		initComponents();
	}

	public void setFieldsVisible(final boolean visible) {
		for (final Component component : getComponents()) {
			component.setVisible(visible);
		}
	}

	/**
	 * Updates the description pane with the given <code>Apparatus</code>
	 * configuration.
	 * 
	 * @param apparatusConfig
	 */
	public void setApparatusConfig(final Apparatus apparatusConfig) {

		if (apparatusConfig == null) {
			// TODO clear selection
			return;

		} else {
			String desktopLocationBundleKey = null;
			String displayStringBundleKey = null;
			String descriptionStringBundleKey = null;
			String webResourceLocation = null;

			if (apparatusConfig.getDesktopLocationBundleKey() != null
					&& apparatusConfig.getDesktopLocationBundleKey().trim().length() > 0) {
				desktopLocationBundleKey = apparatusConfig.getDesktopLocationBundleKey();
			}
			if (apparatusConfig.getDisplayStringBundleKey() != null
					&& apparatusConfig.getDisplayStringBundleKey().trim().length() > 0) {
				displayStringBundleKey = apparatusConfig.getDisplayStringBundleKey();
			}
			if (apparatusConfig.getDescriptionStringBundleKey() != null
					&& apparatusConfig.getDescriptionStringBundleKey().trim().length() > 0) {
				descriptionStringBundleKey = apparatusConfig.getDescriptionStringBundleKey();
			}
			if (apparatusConfig.getWebResource() != null && apparatusConfig.getWebResource().size() > 0) {
				String webResourceLocationBundleKey = null;
				try {
					// FIXME HFernandes - You should support more than one
					// webresource... That is not uncommon... So, please, add
					// links for every resource
					// And then again, why are the links handled as buttons and
					// not as hyperlinks inside some html... Please see the way
					// we do it in Chat
					// Get the first web resource for the link
					final WebResource webResource = apparatusConfig.getWebResource().get(0);
					webResourceLocationBundleKey = webResource.getLocationBundleKey();
					webResourceLocation = ReCResourceBundle.findStringOrDefault(webResourceLocationBundleKey, null);
				} catch (final MissingResourceException e) {
					ApparatusDescriptionPane.log.warning("Could not find the resource " + webResourceLocationBundleKey
							+ " on the experiment bundle");
				}
			}

			lblApparatusName.setText(ReCResourceBundle.findString(displayStringBundleKey));
			// FIXME Set default icon
			lblApparatusImg
					.setIcon(ReCResourceBundle.findImageIconOrDefault(desktopLocationBundleKey, new ImageIcon()));
			txtApparatusDescription.setText(ReCResourceBundle.findStringOrDefault(descriptionStringBundleKey,
					"Apparatus description was not found."));
			btnLink.setVisible(webResourceLocation != null);
			btnLink.setActionCommand(webResourceLocation);
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		lblApparatusName = new javax.swing.JLabel();
		lblApparatusImg = new javax.swing.JLabel();
		btnLink = new javax.swing.JButton();
		scrollApparatusDescription = new javax.swing.JScrollPane();
		txtApparatusDescription = new javax.swing.JTextPane();

		setMaximumSize(new java.awt.Dimension(32767, 500));
		setMinimumSize(new java.awt.Dimension(388, 398));
		setName("Form"); // NOI18N

		lblApparatusName.setFont(lblApparatusName.getFont().deriveFont(
				lblApparatusName.getFont().getStyle() | java.awt.Font.BOLD, 16));
		final org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
				.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext()
				.getResourceMap(ApparatusDescriptionPane.class);
		lblApparatusName.setText(resourceMap.getString("lblApparatusName.text")); // NOI18N
		lblApparatusName.setName("lblApparatusName"); // NOI18N

		lblApparatusImg.setIcon(resourceMap.getIcon("lblApparatusImg.icon")); // NOI18N
		lblApparatusImg.setName("lblApparatusImg"); // NOI18N

		btnLink.setBackground(resourceMap.getColor("btnLink.background")); // NOI18N
		btnLink.setFont(btnLink.getFont().deriveFont((float) 11));
		btnLink.setForeground(resourceMap.getColor("btnLink.foreground")); // NOI18N
		btnLink.setText(resourceMap.getString("btnLink.text")); // NOI18N
		btnLink.setBorderPainted(false);
		btnLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnLink.setName("btnLink"); // NOI18N
		btnLink.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				onInfoButtonAction(evt);
			}
		});

		scrollApparatusDescription.setName("scrollApparatusDescription"); // NOI18N
		txtApparatusDescription.setBackground(resourceMap.getColor("txtApparatusDescription.background"));
		txtApparatusDescription.setContentType(resourceMap.getString("txtApparatusDescription.contentType")); // NOI18N
		txtApparatusDescription.setText(resourceMap.getString("txtApparatusDescription.text")); // NOI18N
		txtApparatusDescription.setToolTipText(resourceMap.getString("txtApparatusDescription.toolTipText")); // NOI18N
		txtApparatusDescription.setName("txtApparatusDescription"); // NOI18N
		scrollApparatusDescription.setViewportView(txtApparatusDescription);

		final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(scrollApparatusDescription, javax.swing.GroupLayout.DEFAULT_SIZE,
												368, Short.MAX_VALUE)
										.addComponent(lblApparatusName)
										.addComponent(btnLink, javax.swing.GroupLayout.PREFERRED_SIZE, 206,
												javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(lblApparatusImg))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblApparatusName)
						.addGap(18, 18, 18)
						.addComponent(lblApparatusImg, javax.swing.GroupLayout.PREFERRED_SIZE, 132,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(scrollApparatusDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 179,
								Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(btnLink, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		lblApparatusName.getAccessibleContext().setAccessibleName(
				resourceMap.getString("lblApparatusName.AccessibleContext.accessibleName")); // NOI18N
	}// </editor-fold>//GEN-END:initComponents

	private void onInfoButtonAction(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_onInfoButtonAction
		final String actionCommand = btnLink.getActionCommand();
		if (actionCommand != null && Desktop.isDesktopSupported()) {
			final Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.BROWSE)) {
				URI uri;
				try {
					ApparatusDescriptionPane.log.fine("Browsing " + actionCommand);
					uri = new URI(actionCommand);
					desktop.browse(uri);
				} catch (final URISyntaxException e) {
					ApparatusDescriptionPane.log.log(Level.SEVERE, "Invalid URL.", e);
				} catch (final IOException e) {
					ApparatusDescriptionPane.log.log(Level.SEVERE, "User browser not found.", e);
				}

			}
		}
	}// GEN-LAST:event_onInfoButtonAction

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnLink;
	private javax.swing.JLabel lblApparatusImg;
	private javax.swing.JLabel lblApparatusName;
	private javax.swing.JScrollPane scrollApparatusDescription;
	private javax.swing.JTextPane txtApparatusDescription;
	// End of variables declaration//GEN-END:variables

}
