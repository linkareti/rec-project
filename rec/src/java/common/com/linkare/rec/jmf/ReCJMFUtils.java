/* 
 * ReCJMFUtils.java created on 24 Aug 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.jmf;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Logger;

import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.ConfigureCompleteEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.DataSink;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSinkException;
import javax.media.NoDataSourceException;
import javax.media.NoPlayerException;
import javax.media.PackageManager;
import javax.media.Player;
import javax.media.PrefetchCompleteEvent;
import javax.media.RealizeCompleteEvent;
import javax.media.StartEvent;
import javax.media.StopEvent;
import javax.media.TransitionEvent;
import javax.media.control.BufferControl;
import javax.media.format.AudioFormat;
import javax.media.protocol.DataSource;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import com.linkare.rec.jmf.media.datasink.capture.Handler;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ReCJMFUtils {

	public static final Logger LOGGER = Logger.getLogger(ReCJMFUtils.class.getName());

	private static final String JMF_PACKAGE = ReCJMFUtils.class.getPackage().getName();

	private static final String CAPTURE_DEVICE_URL_SYSPROP_KEY = "capture.device.url";

	public static final void initReCJMFPackages() {
		@SuppressWarnings("unchecked")
		Vector<String> protocolPrefixList = PackageManager.getProtocolPrefixList();

		if (!protocolPrefixList.contains(JMF_PACKAGE)) {
			protocolPrefixList.add(JMF_PACKAGE);
			PackageManager.setProtocolPrefixList(protocolPrefixList);
			PackageManager.commitProtocolPrefixList();
		}

		@SuppressWarnings("unchecked")
		Vector<String> contentPrefixList = PackageManager.getContentPrefixList();
		if (!contentPrefixList.contains(JMF_PACKAGE)) {
			contentPrefixList.add(JMF_PACKAGE);
			PackageManager.setContentPrefixList(protocolPrefixList);
			PackageManager.commitContentPrefixList();
		}
	}

	public static final void autoDetectJavaSoundDevices() {
		try {
			// It's there, start to register JavaSound with CaptureDeviceManager
			@SuppressWarnings("unchecked")
			Vector<CaptureDeviceInfo> devices = (Vector<CaptureDeviceInfo>) CaptureDeviceManager.getDeviceList(null)
					.clone();

			// detect if javasound capturers are already defined!
			boolean foundJavaSoundDevice = false;
			String name;
			final Enumeration<CaptureDeviceInfo> enumDevices = devices.elements();
			while (enumDevices.hasMoreElements()) {
				CaptureDeviceInfo cdi = enumDevices.nextElement();
				name = cdi.getName();
				if (name.startsWith("JavaSound")) {
					foundJavaSoundDevice = true;
					break;
				}
			}

			if (!foundJavaSoundDevice) {
				DataLine.Info lineInfo = new DataLine.Info(TargetDataLine.class, null, AudioSystem.NOT_SPECIFIED);

				if (!AudioSystem.isLineSupported(lineInfo)) {
					throw new Exception("AudioSystem.isLineSupported said 'false'");
				}

				// collect javasound capture device info from
				// JavaSoundSourceStream
				// and register them with CaptureDeviceManager
				CaptureDeviceInfo[] cdi = com.sun.media.protocol.javasound.JavaSoundSourceStream
						.listCaptureDeviceInfo();
				if (cdi != null) {
					for (int i = 0; i < cdi.length; i++) {
						CaptureDeviceManager.addDevice(cdi[i]);
					}
					CaptureDeviceManager.commit();
					LOGGER.fine("JavaSoundAuto: Committed ok");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Unable to detect java sound capture device... No sound board???", e);
		}
	}

	public static final String locateCaptureDeviceForParameters(double sampleRate, int bitsPerChannel, int numChannels,
			int endian, int signed) {

		String deviceLocation = System.getProperty(CAPTURE_DEVICE_URL_SYSPROP_KEY);

		AudioFormat audioFormat = new AudioFormat(AudioFormat.LINEAR, sampleRate, bitsPerChannel, numChannels, endian,
				signed);

		if (deviceLocation == null) {
			@SuppressWarnings("unchecked")
			Vector<CaptureDeviceInfo> deviceList = CaptureDeviceManager.getDeviceList(audioFormat);
			if (deviceList != null && deviceList.size() > 0) {
				for (CaptureDeviceInfo captureDeviceInfo : deviceList) {
					LOGGER.info("Found device at " + captureDeviceInfo.getLocator().toExternalForm());
				}
				if (deviceList.size() > 1) {
					LOGGER.severe("Please specify the correct device by setting the system property "
							+ CAPTURE_DEVICE_URL_SYSPROP_KEY);
				} else {
					deviceLocation = deviceList.get(0).getLocator().toExternalForm();
				}
			} else {
				LOGGER.severe("Did not find any device matching the characteristics...");
				LOGGER.severe("Please use the auto-detect funcions of JMFRegistry (will be opened now if possible) and commit the changes...");
				throw new RuntimeException(
						"It was not possible to find any device matching the desired characteristics");
			}
		}
		return deviceLocation;
	}

	public static final String CAPTURE_LOCATOR_PREFIX = "capture://";

	public static final DataSource locateDataSource(String deviceLocation, long desiredBufferLength)
			throws NoDataSourceException, IOException {
		DataSource dataSource = null;
		if (deviceLocation != null) {
			MediaLocator locator = new MediaLocator(deviceLocation);
			LOGGER.fine("Capturing from " + locator);
			dataSource = Manager.createDataSource(locator);

			Object[] controls = dataSource.getControls();
			for (Object control : controls) {
				LOGGER.fine("There is a control object : " + control.getClass().getName());
				if (control instanceof BufferControl) {
					BufferControl bufferControl = (BufferControl) control;
					LOGGER.fine("Length of Buffer was " + bufferControl.getBufferLength());
					bufferControl.setBufferLength(desiredBufferLength);
					LOGGER.fine("Length of Buffer is now " + bufferControl.getBufferLength());
				}
			}

		}
		return dataSource;
	}

	public static final Handler createCaptureDeviceFor(DataSource dataSource, int sampleRate, int bitsPerSample,
			int numChannels) throws NoDataSinkException, SecurityException, IOException {
		String destinationLocator = CAPTURE_LOCATOR_PREFIX + sampleRate + "/" + bitsPerSample + "/" + numChannels;
		LOGGER.fine("Sinking data to " + destinationLocator);
		DataSink sink = Manager.createDataSink(dataSource, new MediaLocator(destinationLocator));
		sink.open();
		dataSource.connect();
		Handler soundCaptureDevice = (Handler) sink;
		return soundCaptureDevice;
	}

	public static final void startCapturing(DataSource dataSource, Handler sink) throws IOException {
		sink.start();
		dataSource.start();
	}

	public static final Player createAndStartPlayer(String locatorString) throws NoPlayerException, IOException {
		LOGGER.fine("Creating player for " + locatorString);
		final Player player = Manager.createPlayer(new MediaLocator(locatorString));

		class StateControllerListener implements ControllerListener {
			private final int[] waitSync = new int[0];
			private volatile boolean stateTransitionOK = true;

			@Override
			public void controllerUpdate(ControllerEvent evt) {

				boolean currentTransitionOK = false;

				LOGGER.fine("Controller event: " + evt.toString());

				if (evt instanceof ConfigureCompleteEvent || evt instanceof RealizeCompleteEvent
						|| evt instanceof PrefetchCompleteEvent || evt instanceof StartEvent) {
					currentTransitionOK = true;
				} else if (evt instanceof StopEvent) {
					currentTransitionOK = false;
				} else if (evt instanceof TransitionEvent) {
					currentTransitionOK = true;
				}

				synchronized (waitSync) {
					stateTransitionOK = currentTransitionOK;
					waitSync.notifyAll();
				}

			}

			public boolean waitForState(int state) {
				synchronized (waitSync) {
					try {
						while (player.getState() < state && stateTransitionOK) {
							LOGGER.fine("Waiting for state " + state + " and player state is now "
									+ player.getState());
							waitSync.wait();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return stateTransitionOK;
			}
		}

		StateControllerListener stateController = new StateControllerListener();

		player.addControllerListener(stateController);

		LOGGER.fine("Realizing player...");
		player.realize();
		if (!stateController.waitForState(Player.Realized)) {
			// cleanup the player
			player.stop();
			player.deallocate();
			player.close();
			throw new RuntimeException("Failed to realize the player");
		}

		LOGGER.fine("Prefetching player...");
		player.prefetch();
		if (!stateController.waitForState(Player.Prefetched)) {
			// cleanup the player
			player.stop();
			player.deallocate();
			player.close();
			throw new RuntimeException("Failed to prefecth the player");
		}

		LOGGER.fine("Starting player...");
		player.start();
		if (!stateController.waitForState(Player.Started)) {
			// cleanup the player
			player.stop();
			player.deallocate();
			player.close();
			throw new RuntimeException("Failed to start the player");
		}

		return player;
	}
}
