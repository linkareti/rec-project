/*
 * CustomizerUtil.java
 *
 * Created on 8 de Maio de 2003, 15:05
 */
package com.linkare.rec.impl.client.customizer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.utils.ClassUtils;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class CustomizerUIUtil {

	private static final Logger LOGGER = Logger.getLogger(CustomizerUIUtil.class.getName());

	/** Creates a new instance of CustomizerUtil */
	public CustomizerUIUtil() {
	}

	public static ICustomizer loadCustomizer(final String url) {
		try {
			final String className = url;
                        final Class<?> c = ClassUtils.findClass(className, ClassLoader.getSystemClassLoader());

			final Object o = c.newInstance();

			if (o instanceof ICustomizer) {
				return (ICustomizer) o;
			} else {
				return null;
			}

		} catch (final Exception e2) {
			LOGGER.log(Level.SEVERE, "Couldn't load Customizer from URL :" + url, e2);
			return null;
		}
	}

	public static boolean customizerExists(final String url) {
		try {
			final String className = url;                        
			final Class<?> c = ClassUtils.findClass(className, ClassLoader.getSystemClassLoader());
			return (c != null);

		} catch (final Exception e2) {
			LOGGER.log(Level.SEVERE, "Couldn't load Customizer from URL :" + url, e2);
			return false;
		}
	}

	public static ICustomizer InitCustomizer(final ICustomizer customizer, final HardwareInfo hardwareInfo,
			final HardwareAcquisitionConfig acqConfig, final CustomizerInfo info) {
		try {
			if (customizer == null) {
				return null;
			}
			customizer.setHardwareInfo(hardwareInfo);
			customizer.setHardwareAcquisitionConfig(acqConfig);
			final JComponent display = customizer.getCustomizerComponent();
			if (display == null) {
				return null;
			}

			final JFrame customizerFrame = new JFrame(info.getCustomizerTitle());
			if (info.getCustomizerIcon() != null) {
				customizerFrame.setIconImage(info.getCustomizerIcon().getImage());
			}
			customizerFrame.getContentPane().setLayout(new BorderLayout());
			customizerFrame.getContentPane().add(display, BorderLayout.CENTER);
			final JMenuBar menuBar = customizer.getMenuBar();
			if (menuBar != null) {
				customizerFrame.setJMenuBar(menuBar);
			}

			customizer.addICustomizerListener(new ICustomizerListener() {

				@Override
				public void canceled() {
					customizerFrame.dispose();
				}

				@Override
				public void done() {
					customizerFrame.dispose();
				}
			});

			customizerFrame.setVisible(true);
			customizerFrame.pack();
			final int width = customizerFrame.getWidth();
			final int height = customizerFrame.getHeight();
			final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			customizerFrame.setLocation((d.width - width) / 2, (d.height - height) / 2);
			return customizer;

		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Couldn't init Customizer for Experiment " + hardwareInfo.getFamiliarName(), e);
			return null;
		}
	}
}
