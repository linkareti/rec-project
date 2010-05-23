/*
 
 * SamplesPacketMatrix.java
 
 *
 
 * Created on 26 de Junho de 2002, 12:13
 
 */

package com.linkare.rec.impl.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.EventListenerList;

import com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.impl.exceptions.NotAnAvailableSamplesPacketExceptionConstants;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.FileObjectOutputStream;

/**
 * 
 * 
 * 
 * @author José Pedro Pereira - Linkare TI
 * 
 */

public class SamplesPacketMatrix implements SamplesPacketSource, Serializable

{

	// Added by André... the free mem should be defined by in the init
	// scripts...

	public static final String SYSPROP_FREE_THRESHOLD_NAME = "ReC.PercentFreeMemoryThreshold2Serialization";

	public static final String FREE_THRESHOLD_NAME = com.linkare.rec.impl.utils.Defaults.defaultIfEmpty(System
			.getProperty(SYSPROP_FREE_THRESHOLD_NAME), "10");

	private transient ArrayList<SamplesPacket> samples = null;

	private transient SamplesPacketMatrixIO ioDelegate;

	private boolean serialized = false;

	/** Utility field used by event firing mechanism. */

	private transient EventListenerList listenerList = null;

	private double percentFreeThreshold = 10.;

	/** Creates a new instance of SamplesPacketMatrix */

	public SamplesPacketMatrix()

	{

		samples = new ArrayList<SamplesPacket>(1000);

		try

		{

			percentFreeThreshold = Double.parseDouble(FREE_THRESHOLD_NAME);

		}

		catch (NumberFormatException nfe)

		{

			percentFreeThreshold = 10.;

		}

	}

	public SamplesPacketMatrix(SamplesPacket[] samples_packets)

	{

		samples = new ArrayList<SamplesPacket>(samples_packets.length);

		addSamplesPackets(samples_packets);

		try

		{

			percentFreeThreshold = Double.parseDouble(FREE_THRESHOLD_NAME);

		}

		catch (NumberFormatException nfe)

		{

			percentFreeThreshold = 10.;

		}

	}

	/**
	 * Indexed getter for property samples_packet.
	 * 
	 * @param index Index of the property.
	 * 
	 * @return Value of the property at <CODE>index</CODE>.
	 * 
	 */

	public SamplesPacket getSamplesPacket(int packet_num) throws NotAnAvailableSamplesPacketException

	{

		if (serialized)

		{

			try

			{

				return ioDelegate.getSamplesPackets(packet_num, packet_num)[0];

			} catch (IOException e)

			{

				throw new NotAnAvailableSamplesPacketException(
						NotAnAvailableSamplesPacketExceptionConstants.PACKET_NOT_FOUND_IN_CACHE, packet_num);

			}

		}

		SamplesPacket p = null;

		try

		{

			p = (SamplesPacket) samples.get(packet_num);

		} catch (IndexOutOfBoundsException e)

		{

			throw new NotAnAvailableSamplesPacketException(
					NotAnAvailableSamplesPacketExceptionConstants.PACKET_NOT_FOUND_IN_CACHE, packet_num);

		}

		if (p == null)

			throw new NotAnAvailableSamplesPacketException(
					NotAnAvailableSamplesPacketExceptionConstants.PACKET_NOT_FOUND_IN_CACHE, packet_num);

		return p;

	}

	/**
	 * Getter for property samples_packet.
	 * 
	 * @return Value of property samples_packet.
	 * 
	 */

	public SamplesPacket[] getSamplesPackets() throws SamplesPacketReadException

	{

		return getSamplesPackets(0, getLargestNumPacket());

	}

	public int size()

	{

		if (serialized)

			return ioDelegate.size();

		return samples.size();

	}

	public int getLargestNumPacket()

	{

		return size() - 1;

	}

	/**
	 * Getter for property samples_packet.
	 * 
	 * @return Value of property samples_packet.
	 * 
	 */

	public SamplesPacket[] getSamplesPackets(int packetStart, int packetEnd) throws SamplesPacketReadException

	{

		if (serialized)

		{

			return ioDelegate.getSamplesPackets(packetStart, packetEnd);

		}

		try

		{

			if (packetStart > packetEnd)

				throw new SamplesPacketReadException("Passed me a packetStart>packetEnd", packetEnd);

			if (samples.size() <= packetEnd)

				throw new SamplesPacketReadException("We still don't have all the packets required", samples.size() - 1);

			SamplesPacket[] packets = new SamplesPacket[packetEnd - packetStart + 1];

			System.arraycopy(samples.toArray(new SamplesPacket[0]), packetStart, packets, 0, packets.length);

			return packets;

		} catch (Exception e)

		{

			e.printStackTrace();

			throw new SamplesPacketReadException(e.getMessage(), packetStart);

		}

	}

	/**
	 * Setter for property samples_packet.
	 * 
	 * @param samples_packet New value of property samples_packet.
	 * 
	 */

	public void addSamplesPackets(SamplesPacket[] samples_packet)

	{

		if (serialized)

		{

			try

			{

				ioDelegate.write(samples_packet);

			}

			catch (IOException e)

			{

				LoggerUtil.logThrowable("Tryed to serialize packets but error occurred", e, Logger
						.getLogger("SamplesPacketIO"));

				Logger.getLogger("SamplesPacketIO").log(Level.WARNING,
						"Couldn't serialize SamplePackets to file... Defaulting to in memory ArrayList!");

			}

		}

		else

			samples.addAll(Arrays.asList(samples_packet));

		if (shouldSerialize() && !serialized)

		{

			try

			{

				ioDelegate = new SamplesPacketMatrixIO();

				ioDelegate.write(getSamplesPackets());

				serialized = true;

				samples.clear();

				System.gc();

			}

			catch (IOException e)

			{

				LoggerUtil.logThrowable("Tryed to serialize packets but error occurred", e, Logger
						.getLogger("SamplesPacketIO"));

				Logger.getLogger("SamplesPacketIO").log(Level.WARNING,
						"Couldn't serialize SamplePackets to file... Defaulting to in memory ArrayList!");

			}

		}

		fireNewSamplesPackets(getLargestNumPacket());

	}

	public void addSamplesPacket(SamplesPacket samples_packet)

	{

		addSamplesPackets(new SamplesPacket[]

		{ samples_packet });

	}

	// Just for the sake of xml serialization

	public void setSamplesPackets(SamplesPacket[] samples_packet)

	{

		addSamplesPackets(samples_packet);

	}

	private boolean shouldSerialize()

	{

		return ((double) Runtime.getRuntime().freeMemory() <= percentFreeThreshold / 100.
				* (double) Runtime.getRuntime().maxMemory());

	}

	/**
	 * Registers SamplesPacketSourceEventListener to receive events.
	 * 
	 * @param listener The listener to register.
	 * 
	 * 
	 * 
	 */

	public synchronized void addSamplesPacketSourceEventListener(
			com.linkare.rec.impl.data.SamplesPacketSourceEventListener listener)

	{

		if (listenerList == null)

		{

			listenerList = new EventListenerList();

		}

		listenerList.add(com.linkare.rec.impl.data.SamplesPacketSourceEventListener.class, listener);

	}

	/**
	 * Removes SamplesPacketSourceEventListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 * 
	 * 
	 * 
	 */

	public synchronized void removeSamplesPacketSourceEventListener(
			com.linkare.rec.impl.data.SamplesPacketSourceEventListener listener)

	{

		listenerList.remove(com.linkare.rec.impl.data.SamplesPacketSourceEventListener.class, listener);

	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * 
	 * 
	 * @param event The event to be fired
	 * 
	 * 
	 * 
	 */

	private void fireNewSamplesPackets(int packetLargestIndex)

	{

		com.linkare.rec.impl.data.SamplesPacketSourceEvent event = new com.linkare.rec.impl.data.SamplesPacketSourceEvent(
				this, packetLargestIndex);

		if (listenerList == null)
			return;

		Object[] listeners = listenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2)

		{

			if (listeners[i] == com.linkare.rec.impl.data.SamplesPacketSourceEventListener.class)

			{

				// System.out.println("Firing newSamplesPackets at SamplesPacketMatrix to registered only "
				// + packetLargestIndex);

				((com.linkare.rec.impl.data.SamplesPacketSourceEventListener) listeners[i + 1])
						.newSamplesPackets(event);

			}

		}

	}

	private void writeObject(ObjectOutputStream stream) throws IOException

	{

		stream.defaultWriteObject();

		if (stream instanceof FileObjectOutputStream)

		{

			// System.out.println("Writing to a FileObjectOutputStream...");

			FileObjectOutputStream fos = (FileObjectOutputStream) stream;

			String samplesFileName = fos.getFile().getAbsolutePath();

			samplesFileName = samplesFileName.substring(0, samplesFileName.lastIndexOf('.')) + "_Samples.ser";

			File outputSamplesFile = new File(samplesFileName);

			if (!serialized) {
				try {

					ObjectOutputStream samplesStream = new ObjectOutputStream(new FileOutputStream(outputSamplesFile));

					samplesStream.writeObject(samples);

					samplesStream.flush();
					samplesStream.close();

					stream.writeObject(outputSamplesFile);

				} catch (IOException e) {

					LoggerUtil.logThrowable("Tryed to serialize packets but error occurred", e, Logger
							.getLogger("SamplesPacketIO"));

					Logger.getLogger("SamplesPacketIO").log(Level.WARNING,
							"Couldn't serialize SamplePackets to file...");

				}

				samples.clear();

				System.gc();

				return;
			}

			try {
				ioDelegate.setFile(outputSamplesFile);

				stream.writeObject(ioDelegate);

				System.gc();
			}

			catch (IOException e)

			{

				LoggerUtil.logThrowable("Tryed to serialize packets but error occurred", e, Logger
						.getLogger("SamplesPacketIO"));

				Logger.getLogger("SamplesPacketIO").log(Level.WARNING, "Couldn't serialize SamplePackets to file...");

			}

		} else {

			if (serialized)

			{

				SamplesPacket[] packets = ioDelegate.getSamplesPackets(0, ioDelegate.size() - 1);

				samples.addAll(Arrays.asList(packets));

				stream.writeObject(samples);

			}

		}

	}

	@SuppressWarnings("unchecked")
	// readObject warnings...
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException

	{
		try {
			stream.defaultReadObject();

			if (serialized)

				ioDelegate = (SamplesPacketMatrixIO) stream.readObject();

			else {
				Object read = stream.readObject();

				if (read instanceof File) {
					File f = (File) read;
					if (f.exists()) {
						ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
						samples = (ArrayList<SamplesPacket>) ois.readObject();
						ois.close();
					} else
						System.out.println("The file " + f + " doesn't exist...");
				} else
					samples = (ArrayList<SamplesPacket>) read;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
