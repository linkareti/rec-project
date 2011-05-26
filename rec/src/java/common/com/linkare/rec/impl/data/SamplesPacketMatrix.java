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
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
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
 * @author José Pedro Pereira - Linkare TI
 * 
 */
public class SamplesPacketMatrix implements SamplesPacketSource, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8817657464936461384L;

	/**
	 * 
	 */
	private static final String TRYED_TO_SERIALIZE_PACKETS_BUT_ERROR_OCCURRED_LOGMSG = "Tryed to serialize packets but error occurred";

	// TODO - define this property in jnlp, build properties and startup
	// scripts...
	public static final String SYSPROP_FREE_THRESHOLD_NAME = "ReC.PercentFreeMemoryThreshold2Serialization";

	public static final String FREE_THRESHOLD_NAME = com.linkare.rec.impl.utils.Defaults.defaultIfEmpty(
			System.getProperty(SamplesPacketMatrix.SYSPROP_FREE_THRESHOLD_NAME), "10");

	private static final int PREDICTION_SIZE_READ = 10;

	private transient ArrayList<SamplesPacket> samples = null;

	private transient SamplesPacketMatrixIO ioDelegate;

	private boolean serialized = false;

	/** Utility field used by event firing mechanism. */

	private transient EventListenerList listenerList = null;

	private double percentFreeThreshold = 10.;

	private SamplesPacketPredictiveBuffer predictiveReadingBuffer = null;

	private class SamplesPacketPredictiveBuffer {

		private final SamplesPacket[] predictedReadPackets;
		private final int packetStart;
		private final int packetEnd;

		@SuppressWarnings("PMD.ArrayIsStoredDirectly")
		public SamplesPacketPredictiveBuffer(final SamplesPacket[] predictedReadPackets, final int packetStart) {
			this.predictedReadPackets = predictedReadPackets;
			this.packetStart = packetStart;
			packetEnd = packetStart + (predictedReadPackets == null ? 0 : predictedReadPackets.length);
		}

		public SamplesPacket getSamplesPacketIfAvailable(final int packetNumber) {
			if (packetNumber >= packetStart && packetNumber < packetEnd) {
				return predictedReadPackets[packetNumber - packetStart];
			}
			return null;
		}
	}

	/** Creates a new instance of SamplesPacketMatrix */

	public SamplesPacketMatrix()

	{
		samples = new ArrayList<SamplesPacket>(1000);
		try {
			percentFreeThreshold = Double.parseDouble(SamplesPacketMatrix.FREE_THRESHOLD_NAME);
		} catch (final NumberFormatException nfe) {
			percentFreeThreshold = 10.;
		}
	}

	public SamplesPacketMatrix(final SamplesPacket[] samples_packets) {
		samples = new ArrayList<SamplesPacket>(samples_packets.length);
		addSamplesPackets(samples_packets);
		try {
			percentFreeThreshold = Double.parseDouble(SamplesPacketMatrix.FREE_THRESHOLD_NAME);
		} catch (final NumberFormatException nfe) {
			percentFreeThreshold = 10.;
		}
	}

	/**
	 * Indexed getter for property samples_packet.
	 * 
	 * @param packet_num
	 * 
	 * @param index Index of the property.
	 * 
	 * @return Value of the property at <CODE>index</CODE>.
	 * @throws NotAnAvailableSamplesPacketException
	 * 
	 */
	public SamplesPacket getSamplesPacket(final int packet_num) throws NotAnAvailableSamplesPacketException {
		if (serialized) {
			if (predictiveReadingBuffer != null) {
				final SamplesPacket retPacket = predictiveReadingBuffer.getSamplesPacketIfAvailable(packet_num);
				if (retPacket != null) {
					return retPacket;
				}
			}

			try {
				predictiveReadingBuffer = new SamplesPacketPredictiveBuffer(ioDelegate.getSamplesPackets(packet_num,
						packet_num + SamplesPacketMatrix.PREDICTION_SIZE_READ), packet_num);
				return predictiveReadingBuffer.getSamplesPacketIfAvailable(packet_num);
			} catch (final IOException e) {
				throw new NotAnAvailableSamplesPacketException(
						NotAnAvailableSamplesPacketExceptionConstants.PACKET_NOT_FOUND_IN_CACHE, packet_num);
			}
		}
		SamplesPacket p = null;
		try {
			p = samples.get(packet_num);
		} catch (final IndexOutOfBoundsException e) {
			throw new NotAnAvailableSamplesPacketException(
					NotAnAvailableSamplesPacketExceptionConstants.PACKET_NOT_FOUND_IN_CACHE, packet_num);
		}
		if (p == null) {
			throw new NotAnAvailableSamplesPacketException(
					NotAnAvailableSamplesPacketExceptionConstants.PACKET_NOT_FOUND_IN_CACHE, packet_num);
		}
		return p;
	}

	/**
	 * Getter for property samples_packet.
	 * 
	 * @return Value of property samples_packet.
	 * @throws SamplesPacketReadException
	 * 
	 */
	public SamplesPacket[] getSamplesPackets() throws SamplesPacketReadException {
		return getSamplesPackets(0, getLargestNumPacket());
	}

	public int size() {
		if (serialized) {
			return ioDelegate.size();
		}

		return samples.size();
	}

	@Override
	public int getLargestNumPacket() {
		return size() - 1;
	}

	/**
	 * Getter for property samples_packet.
	 * 
	 * @return Value of property samples_packet.
	 * 
	 */

	@Override
	public SamplesPacket[] getSamplesPackets(final int packetStart, final int packetEnd)
			throws SamplesPacketReadException {
		if (serialized) {
			return ioDelegate.getSamplesPackets(packetStart, packetEnd);
		}
		try {
			if (packetStart > packetEnd) {
				throw new SamplesPacketReadException("Passed me a packetStart>packetEnd", packetEnd);
			}

			if (samples.size() <= packetEnd) {
				throw new SamplesPacketReadException("We still don't have all the packets required", samples.size() - 1);
			}

			final SamplesPacket[] packets = new SamplesPacket[packetEnd - packetStart + 1];
			System.arraycopy(samples.toArray(new SamplesPacket[0]), packetStart, packets, 0, packets.length);
			return packets;
		} catch (final Exception e) {
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
	public void addSamplesPackets(final SamplesPacket[] samples_packet) {
		if (samples_packet == null) {
			return;
		}

		if (serialized) {
			try {
				ioDelegate.write(samples_packet);
			} catch (final IOException e) {
				LoggerUtil.logThrowable(SamplesPacketMatrix.TRYED_TO_SERIALIZE_PACKETS_BUT_ERROR_OCCURRED_LOGMSG, e,
						Logger.getLogger("SamplesPacketIO"));

				Logger.getLogger("SamplesPacketIO").log(Level.WARNING,
						"Couldn't serialize SamplePackets to file... Defaulting to in memory ArrayList!");

			}
		} else {
			samples.addAll(Arrays.asList(samples_packet));
		}

		if (shouldSerialize() && !serialized) {
			try {
				ioDelegate = new SamplesPacketMatrixIO();
				ioDelegate.write(getSamplesPackets());
				serialized = true;
				samples.clear();
			} catch (final IOException e) {
				LoggerUtil.logThrowable(SamplesPacketMatrix.TRYED_TO_SERIALIZE_PACKETS_BUT_ERROR_OCCURRED_LOGMSG, e,
						Logger.getLogger("SamplesPacketIO"));

				Logger.getLogger("SamplesPacketIO").log(Level.WARNING,
						"Couldn't serialize SamplePackets to file... Defaulting to in memory ArrayList!");
			}
		}
		fireNewSamplesPackets(getLargestNumPacket());
	}

	public void addSamplesPacket(final SamplesPacket samples_packet) {
		addSamplesPackets(new SamplesPacket[] { samples_packet });
	}

	// Just for the sake of xml serialization
	public void setSamplesPackets(final SamplesPacket[] samples_packet) {
		addSamplesPackets(samples_packet);
	}

	private boolean shouldSerialize() {
		// return ((double) Runtime.getRuntime().freeMemory() <=
		// percentFreeThreshold / 100.
		// * (double) Runtime.getRuntime().maxMemory());
		final MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
		final double maxMem = heapMemoryUsage.getMax();
		final double availableMem = heapMemoryUsage.getMax() - heapMemoryUsage.getUsed();
		final double currentPercentFreeMem = 100. * availableMem / maxMem;
		return currentPercentFreeMem <= percentFreeThreshold;
	}

	/**
	 * Registers SamplesPacketSourceEventListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	@Override
	public synchronized void addSamplesPacketSourceEventListener(
			final com.linkare.rec.impl.data.SamplesPacketSourceEventListener listener) {
		if (listenerList == null) {
			listenerList = new EventListenerList();
		}
		listenerList.add(com.linkare.rec.impl.data.SamplesPacketSourceEventListener.class, listener);
	}

	/**
	 * Removes SamplesPacketSourceEventListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	@Override
	public synchronized void removeSamplesPacketSourceEventListener(
			final com.linkare.rec.impl.data.SamplesPacketSourceEventListener listener) {
		listenerList.remove(com.linkare.rec.impl.data.SamplesPacketSourceEventListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * 
	 * 
	 * @param event The event to be fired
	 */
	private void fireNewSamplesPackets(final int packetLargestIndex) {
		final com.linkare.rec.impl.data.SamplesPacketSourceEvent event = new com.linkare.rec.impl.data.SamplesPacketSourceEvent(
				this, packetLargestIndex);

		if (listenerList == null) {
			return;
		}

		final Object[] listeners = listenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == com.linkare.rec.impl.data.SamplesPacketSourceEventListener.class) {
				// System.out.println("Firing newSamplesPackets at SamplesPacketMatrix to registered only "
				// + packetLargestIndex);
				((com.linkare.rec.impl.data.SamplesPacketSourceEventListener) listeners[i + 1])
						.newSamplesPackets(event);
			}
		}
	}

	private void writeObject(final ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		if (stream instanceof FileObjectOutputStream) {
			// System.out.println("Writing to a FileObjectOutputStream...");
			final FileObjectOutputStream fos = (FileObjectOutputStream) stream;
			String samplesFileName = fos.getFile().getAbsolutePath();
			samplesFileName = samplesFileName.substring(0, samplesFileName.lastIndexOf('.')) + "_Samples.ser";
			final File outputSamplesFile = new File(samplesFileName);
			if (!serialized) {
				try {

					final ObjectOutputStream samplesStream = new ObjectOutputStream(new FileOutputStream(
							outputSamplesFile));

					samplesStream.writeObject(samples);

					samplesStream.flush();
					samplesStream.close();

					stream.writeObject(outputSamplesFile);

				} catch (final IOException e) {
					LoggerUtil.logThrowable(SamplesPacketMatrix.TRYED_TO_SERIALIZE_PACKETS_BUT_ERROR_OCCURRED_LOGMSG,
							e, Logger.getLogger("SamplesPacketIO"));

					Logger.getLogger("SamplesPacketIO").log(Level.WARNING,
							"Couldn't serialize SamplePackets to file...");
				}
				samples.clear();
				return;
			}
			try {
				ioDelegate.setFile(outputSamplesFile);
				stream.writeObject(ioDelegate);
			} catch (final IOException e) {
				LoggerUtil.logThrowable(SamplesPacketMatrix.TRYED_TO_SERIALIZE_PACKETS_BUT_ERROR_OCCURRED_LOGMSG, e,
						Logger.getLogger("SamplesPacketIO"));

				Logger.getLogger("SamplesPacketIO").log(Level.WARNING, "Couldn't serialize SamplePackets to file...");
			}
		} else {
			if (serialized) {
				final SamplesPacket[] packets = ioDelegate.getSamplesPackets(0, ioDelegate.size() - 1);
				samples.addAll(Arrays.asList(packets));
				stream.writeObject(samples);
			}
		}
	}

	@SuppressWarnings("unchecked")
	// readObject warnings...
	private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
		try {
			stream.defaultReadObject();
			if (serialized) {
				ioDelegate = (SamplesPacketMatrixIO) stream.readObject();
			} else {
				final Object read = stream.readObject();

				if (read instanceof File) {
					final File f = (File) read;
					if (f.exists()) {
						final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
						samples = (ArrayList<SamplesPacket>) ois.readObject();
						ois.close();
					} else {
						System.out.println("The file " + f + " doesn't exist...");
					}
				} else {
					samples = (ArrayList<SamplesPacket>) read;
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
