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

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import com.linkare.net.protocols.tex.TexUtils;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.web.config.Apparatus;
import com.linkare.rec.web.config.WebResource;

/**
 * 
 * @author Henrique Fernandes
 */
public class ApparatusDescriptionPane extends AbstractContentPane {

	/**
	 * 
	 */
	private static final String HTML_BODY_END_TAG = "</body>";
	/**
     * 
     */
	private static final long serialVersionUID = 405323800381364687L;
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ApparatusDescriptionPane.class.getName());
	private Color defaultBackgroundColor = null;
	private boolean defaultOpaque = false;

	/** Creates new form ApparatusPane */
	public ApparatusDescriptionPane() {
		initComponents();
		this.defaultBackgroundColor = this.getBackground();
		this.defaultOpaque = this.isOpaque();
		this.scrollApparatusDescription.getViewport().setOpaque(false);

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
			lblApparatusName.setText("");
			lblApparatusImg.setIcon(null);
			txtApparatusDescription.setText("");
			lblIcon.setIcon(null);
			this.setBackground(defaultBackgroundColor);
			txtApparatusDescription.setBackground(defaultBackgroundColor);
			this.setOpaque(this.defaultOpaque);
			return;
		} else {
			String desktopLocationBundleKey = null;
			String displayStringBundleKey = null;
			String descriptionStringBundleKey = null;
			String iconLocationBundleKey = null;

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
			if (apparatusConfig.getIconLocationBundleKey() != null
					&& apparatusConfig.getIconLocationBundleKey().trim().length() > 0) {
				iconLocationBundleKey = apparatusConfig.getIconLocationBundleKey();
			}

			String description = ReCResourceBundle.findStringOrDefault(descriptionStringBundleKey,
					"<html><body></body></html>");
			if (!description.contains(HTML_BODY_END_TAG)) {
				description = "<html><body>" + description + "</body></html>";
			}

			final String descriptionPre = description.substring(0, description.indexOf(HTML_BODY_END_TAG));
			final String descriptionPost = description.substring(description.indexOf(HTML_BODY_END_TAG)
					+ HTML_BODY_END_TAG.length());

			final StringBuilder webResourceLinks = new StringBuilder(descriptionPre);
			if (apparatusConfig.getWebResource() != null && apparatusConfig.getWebResource().size() > 0) {

				String webResourceLocationBundleKey = null;
				try {
					final WebResource webResource = apparatusConfig.getWebResource().get(0);
					webResourceLocationBundleKey = webResource.getLocationBundleKey();
					if (webResourceLocationBundleKey != null && webResourceLocationBundleKey.trim().length() != 0) {
						final String webResourceLocation = ReCResourceBundle.findStringOrDefault(
								webResourceLocationBundleKey, null);
						try {
							// Just to check it is a valid URL
							new URL(webResourceLocation);
							final String iconBundleKey = webResource.getIconLocationBundleKey();
							final String displayBundleKey = webResource.getDisplayStringBundleKey();
							final String iconUrl = ReCResourceBundle.findStringOrDefault(iconBundleKey, null);
							final String displayString = ReCResourceBundle.findStringOrDefault(displayBundleKey,
									webResourceLocation);

							webResourceLinks.append("<div>");
							if (iconUrl != null) {
								webResourceLinks.append("<img src=\"").append(iconUrl).append("\" />");
							}
							webResourceLinks.append("<a href=\"").append(webResourceLocation).append("\">")
									.append(displayString != null ? displayString : webResourceLocation).append("</a>");

							webResourceLinks.append("</div>");
						} catch (Exception e) {
							log.finest("url '" + webResourceLocation + "' does not represent a valid URL!");
						}
					}
				} catch (final MissingResourceException e) {
					ApparatusDescriptionPane.log.warning("Could not find the resource " + webResourceLocationBundleKey
							+ " on the experiment bundle");
				}
			}
			webResourceLinks.append(descriptionPost);
			description = webResourceLinks.toString();

			lblApparatusName.setText(ReCResourceBundle.findStringOrDefault(displayStringBundleKey,
					displayStringBundleKey));

			if (desktopLocationBundleKey != null && desktopLocationBundleKey.trim().length() != 0) {
				lblApparatusImg.setIcon(ReCResourceBundle.findImageIconOrDefault(desktopLocationBundleKey,
						new ImageIcon()));
			}

			if (descriptionStringBundleKey != null) {
				String translatedStr = TexUtils.getDescriptionWithTeXExpressions(description);
				txtApparatusDescription.setText(translatedStr);
			}

			if (iconLocationBundleKey != null) {
				try {
					lblIcon.setIcon(ReCResourceBundle.findImageIcon(iconLocationBundleKey));
				} catch (MissingResourceException e) {
					ApparatusDescriptionPane.log.warning("Could not find the resource " + iconLocationBundleKey
							+ " on the experiment bundle");
				} catch (MalformedURLException e) {
					ApparatusDescriptionPane.log.warning("The resource " + iconLocationBundleKey
							+ " does not define a valid URL");
				}
			}

			if (apparatusConfig.isVirtual()) {
				this.setOpaque(true);
				Color bg = getResourceMap().getColor("virtualExperiments.background");
				this.setBackground(bg);
				// txtApparatusDescription.setBackground(bg);
				// scrollApparatusDescription.setBackground(bg);
			}

		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		lblApparatusName = new javax.swing.JLabel();
		lblApparatusImg = new javax.swing.JLabel();
		scrollApparatusDescription = new javax.swing.JScrollPane();
		txtApparatusDescription = new javax.swing.JTextPane();
		lblIcon = new javax.swing.JLabel();

		setMaximumSize(new java.awt.Dimension(32767, 500));
		setMinimumSize(new java.awt.Dimension(388, 398));
		setName("Form"); // NOI18N

		lblApparatusName.setFont(lblApparatusName.getFont().deriveFont(
				lblApparatusName.getFont().getStyle() | java.awt.Font.BOLD, 16));
		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
				.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext()
				.getResourceMap(ApparatusDescriptionPane.class);
		lblApparatusName.setText(resourceMap.getString("lblApparatusName.text")); // NOI18N
		lblApparatusName.setName("lblApparatusName"); // NOI18N

		lblApparatusImg.setIcon(resourceMap.getIcon("lblApparatusImg.icon")); // NOI18N
		lblApparatusImg.setName("lblApparatusImg"); // NOI18N

		scrollApparatusDescription.setName("scrollApparatusDescription"); // NOI18N
		scrollApparatusDescription.setOpaque(false);

		txtApparatusDescription.setContentType(resourceMap.getString("txtApparatusDescription.contentType")); // NOI18N
		txtApparatusDescription.setEditable(false);
		txtApparatusDescription.setText(resourceMap.getString("txtApparatusDescription.text")); // NOI18N
		txtApparatusDescription.setToolTipText(resourceMap.getString("txtApparatusDescription.toolTipText")); // NOI18N
		txtApparatusDescription.setName("txtApparatusDescription"); // NOI18N
		txtApparatusDescription.setOpaque(false);
		txtApparatusDescription.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
			public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {
				txtApparatusDescriptionHyperlinkUpdate(evt);
			}
		});
		scrollApparatusDescription.setViewportView(txtApparatusDescription);

		lblIcon.setIcon(resourceMap.getIcon("lblIcon.icon")); // NOI18N
		lblIcon.setText(resourceMap.getString("lblIcon.text")); // NOI18N
		lblIcon.setName("lblIcon"); // NOI18N

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(scrollApparatusDescription, javax.swing.GroupLayout.DEFAULT_SIZE,
												368, Short.MAX_VALUE)
										.addGroup(
												layout.createSequentialGroup()
														.addComponent(lblApparatusName)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																265, Short.MAX_VALUE).addComponent(lblIcon))
										.addComponent(lblApparatusImg, javax.swing.GroupLayout.DEFAULT_SIZE, 368,
												Short.MAX_VALUE)).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(lblApparatusName).addComponent(lblIcon))
						.addGap(18, 18, 18)
						.addComponent(lblApparatusImg, javax.swing.GroupLayout.PREFERRED_SIZE, 132,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(scrollApparatusDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 215,
								Short.MAX_VALUE).addContainerGap()));

		lblApparatusName.getAccessibleContext().setAccessibleName(
				resourceMap.getString("lblApparatusName.AccessibleContext.accessibleName")); // NOI18N
	}// </editor-fold>//GEN-END:initComponents

	private void txtApparatusDescriptionHyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {// GEN-FIRST:event_txtApparatusDescriptionHyperlinkUpdate
		final URL urlRemote = evt.getURL();
		if (urlRemote != null && Desktop.isDesktopSupported()) {
			final Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					ApparatusDescriptionPane.log.fine("Browsing " + urlRemote);
					desktop.browse(urlRemote.toURI());
				} catch (final URISyntaxException e) {
					ApparatusDescriptionPane.log.log(Level.SEVERE, "Invalid URL.", e);
				} catch (final IOException e) {
					ApparatusDescriptionPane.log.log(Level.SEVERE, "User browser not found.", e);
				}

			}
		}
	}// GEN-LAST:event_txtApparatusDescriptionHyperlinkUpdate

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel lblApparatusImg;
	private javax.swing.JLabel lblApparatusName;
	private javax.swing.JLabel lblIcon;
	private javax.swing.JScrollPane scrollApparatusDescription;
	private javax.swing.JTextPane txtApparatusDescription;
	// End of variables declaration//GEN-END:variables
}
