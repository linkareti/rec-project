/*
 * CypressFinder.java
 *
 * Created on 15 de Maio de 2003, 11:51
 *
 *
 * ->Changed by André on 26/07/04:
 *    Added suport to Basic Atom. Now we can control RTS, DTR and echo
 */

package pt.utl.ist.elab.driver.usb.cypress;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.usb.UsbDevice;
import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbHub;
import javax.usb.UsbServices;

import com.linkare.rec.impl.logging.LoggerUtil;

/**
 * 
 * @author André
 */
public class CypressFinder {
	private static String CYPRESS_FINDER_LOGGER = "CypressFinder.Logger";

	static {
		Logger l = LogManager.getLogManager().getLogger(CYPRESS_FINDER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(CYPRESS_FINDER_LOGGER));
		}
	}

	/** Utility field used by event firing mechanism. */
	private javax.swing.event.EventListenerList listenerList = null;

	/** Holds value of property waitForEcho. */
	private boolean waitForEcho = true;
	private boolean usbFound = false;

	private CypressFinderRunner runner = null;

	private String cypressIdentifier = "ELAB_CYPRESS_V0.0";
	private short vendorID = (short) 0x0547;
	private short productID = (short) 0x2131;
	private short interfaceNumber = (short) 0;
	private short alternateSetting = (short) 1;
	private byte inputChannelNumber = (byte) 2;
	private byte outputChannelNumber = (byte) 3;

	/** Creates a new instance of CypressFinder */
	public CypressFinder() {

	}

	public byte getInputChannelNumber() {
		return inputChannelNumber;
	}

	public void setInputChannelNumber(byte inputChannelNumber) {
		this.inputChannelNumber = inputChannelNumber;
	}

	public byte getOutputChannelNumber() {
		return outputChannelNumber;
	}

	public void setOutputChannelNumber(byte outputChannelNumber) {
		this.outputChannelNumber = outputChannelNumber;
	}

	public short getVendorID() {
		return vendorID;
	}

	public void setVendorID(short vendorID) {
		this.vendorID = vendorID;
	}

	public short getProductID() {
		return productID;
	}

	public void setProductID(short productID) {
		this.productID = productID;
	}

	public short getInterfaceNumber() {
		return interfaceNumber;
	}

	public void setInterfaceNumber(short interfaceNumber) {
		this.interfaceNumber = interfaceNumber;
	}

	public short getAlternateSetting() {
		return alternateSetting;
	}

	public void setAlternateSetting(short alternateSetting) {
		this.alternateSetting = alternateSetting;
	}

	/**
	 * Getter for property CypressIdentifier.
	 * 
	 * @return Value of property CypressIdentifier.
	 */
	public String getCypressIdentifier() {
		return this.cypressIdentifier;
	}

	/**
	 * Setter for property CypressIdentifier.
	 * 
	 * @param CypressIdentifier New value of property CypressIdentifier.
	 */
	public void setCypressIdentifier(String CypressIdentifier) {
		this.cypressIdentifier = cypressIdentifier;
	}

	/**
	 * Getter for property waitForEcho.
	 * 
	 * @return Value of property waitForEcho.
	 * 
	 */
	public boolean isWaitForEcho() {
		return this.waitForEcho;
	}

	/**
	 * Setter for property waitForEcho.
	 * 
	 * @param waitForEcho New value of property waitForEcho.
	 * 
	 */
	public void setWaitForEcho(boolean waitForEcho) {
		this.waitForEcho = waitForEcho;
	}

	public void stopSearch() {
		if (runner != null)
			runner.stopNow();

		runner = null;
	}

	public void startSearch() {
		runner = new CypressFinderRunner();
		runner.setPriority(Thread.NORM_PRIORITY + 2);
		runner.start();
	}

	/**
	 * Registers CypressFinderListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	public synchronized void addCypressFinderListener(pt.utl.ist.elab.driver.usb.cypress.CypressFinderListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(pt.utl.ist.elab.driver.usb.cypress.CypressFinderListener.class, listener);
	}

	/**
	 * Removes CypressFinderListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	public synchronized void removeCypressFinderListener(
			pt.utl.ist.elab.driver.usb.cypress.CypressFinderListener listener) {
		listenerList.remove(pt.utl.ist.elab.driver.usb.cypress.CypressFinderListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 */
	private void fireCypressFinderListenerCypressFound(UsbDevice event) {
		if (listenerList == null)
			return;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == pt.utl.ist.elab.driver.usb.cypress.CypressFinderListener.class) {
				((pt.utl.ist.elab.driver.usb.cypress.CypressFinderListener) listeners[i + 1]).cypressFound(event);
			}
		}
	}

	/**
	 * Get the virtual root UsbHub.
	 * 
	 * @return The virtual root UsbHub.
	 */
	private UsbHub getVirtualRootUsbHub() {
		UsbServices services = null;
		UsbHub virtualRootUsbHub = null;

		/*
		 * First we need to get the UsbServices. This might throw either an
		 * UsbException or SecurityException. A SecurityException means we're
		 * not allowed to access the USB bus, while a UsbException indicates
		 * there is a problem either in the javax.usb implementation or the OS
		 * USB support.
		 */

		try {
			java.util.Properties props = UsbHostManager.getProperties();
			java.util.Enumeration enumer = props.elements();
			while (enumer.hasMoreElements()) {
				System.out.println(enumer.nextElement().toString());
			}

			services = UsbHostManager.getUsbServices();
		} catch (UsbException uE) {
			throw new RuntimeException("Error : " + uE.getMessage());
		} catch (SecurityException sE) {
			throw new RuntimeException("Error : " + sE.getMessage());
		}

		/*
		 * Now we need to get the virtual root UsbHub, everything is connected
		 * to it. The Virtual Root UsbHub doesn't actually correspond to any
		 * physical device, it's strictly virtual. Each of the devices connected
		 * to one of its ports corresponds to a physical host controller located
		 * in the system. Those host controllers are (usually) located inside
		 * the computer, e.g. as a PCI board, or a chip on the mainboard, or a
		 * PCMCIA card. The virtual root UsbHub aggregates all these host
		 * controllers.
		 * 
		 * This also may throw an UsbException or SecurityException.
		 */
		try {
			virtualRootUsbHub = services.getRootUsbHub();
			System.out.println("->" + virtualRootUsbHub.getAttachedUsbDevices().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return virtualRootUsbHub;
	}

	/**
	 * Get a List of all devices that match the specified vendor and product id.
	 * 
	 * @param usbDevice The UsbDevice to check.
	 * @param vendorId The vendor id to match.
	 * @param productId The product id to match.
	 * @param A List of any matching UsbDevice(s).
	 */
	private List getUsbDevicesWithId(UsbDevice usbDevice, short vendorId, short productId) {
		List list = new ArrayList();

		/*
		 * A device's descriptor is always available. All descriptor field names
		 * and types match exactly what is in the USB specification. Note that
		 * Java does not have unsigned numbers, so if you are comparing 'magic'
		 * numbers to the fields, you need to handle it correctly. For example
		 * if you were checking for Intel (vendor id 0x8086) devices, if (0x8086
		 * == descriptor.idVendor()) will NOT work. The 'magic' number 0x8086 is
		 * a positive integer, while the _short_ vendor id 0x8086 is a negative
		 * number! So you need to do either if ((short)0x8086 ==
		 * descriptor.idVendor()) or if (0x8086 ==
		 * UsbUtil.unsignedInt(descriptor.idVendor())) or short intelVendorId =
		 * (short)0x8086; if (intelVendorId == descriptor.idVendor()) Note the
		 * last one, if you don't cast 0x8086 into a short, the compiler will
		 * fail because there is a loss of precision; you can't represent
		 * positive 0x8086 as a short; the max value of a signed short is 0x7fff
		 * (see Short.MAX_VALUE).
		 * 
		 * See javax.usb.util.UsbUtil.unsignedInt() for some more information.
		 */
		if (vendorId == usbDevice.getUsbDeviceDescriptor().idVendor()
				&& productId == usbDevice.getUsbDeviceDescriptor().idProduct())
			list.add(usbDevice);

		/* this is just normal recursion. Nothing special. */
		if (usbDevice.isUsbHub()) {
			List devices = ((UsbHub) usbDevice).getAttachedUsbDevices();
			for (int i = 0; i < devices.size(); i++)
				list.addAll(getUsbDevicesWithId((UsbDevice) devices.get(i), vendorId, productId));
		}

		return list;
	}

	private class CypressFinderRunner extends Thread {

		private boolean exit = false;
		private Thread current = null;
		private int currentPort = 0;
		private BaseCypressIO CypressIO = null;

		public void stopNow() {
			exit = true;
			if (current != null) {
				try {
					current.join();
				} catch (InterruptedException e) {
					LoggerUtil.logThrowable(null, e, Logger.getLogger(CYPRESS_FINDER_LOGGER));
				}
			}
		}

		public void run() {
			while (!exit) {
				Logger.getLogger(CYPRESS_FINDER_LOGGER).log(Level.INFO, "Cycling port...");
				cycleUSBPorts();

				try {
					sleep(5000);
				} catch (Exception ignored) {
				}
			}
		}

		public void cycleUSBPorts() {
			Logger.getLogger(CYPRESS_FINDER_LOGGER).log(Level.INFO, "Searching for a USBDevice with:");
			Logger.getLogger(CYPRESS_FINDER_LOGGER).log(Level.INFO,
					"staticVendorId = " + vendorID + " and staticProductId = " + productID);

			UsbHub virtualRootUsbHub = getVirtualRootUsbHub();

			List usbDevices = null;

			usbDevices = getUsbDevicesWithId(virtualRootUsbHub, vendorID, productID);

			if (usbDevices != null && usbDevices.size() > 0)
				Logger.getLogger(CYPRESS_FINDER_LOGGER).log(Level.INFO,
						"Found " + usbDevices.size() + " device with the requested parameters!");
			else {
				System.out.println("Couln't found any device with the requested parameters... returning");
				return;
			}

			try {
				UsbDevice device = (UsbDevice) usbDevices.get(0);

				usbFound = true;
				exit = true;

				fireCypressFinderListenerCypressFound(device);
			} catch (Exception e) {
				Logger.getLogger(CYPRESS_FINDER_LOGGER).log(Level.INFO, e.getMessage());
				usbFound = false;
			}
		}
	}
}
