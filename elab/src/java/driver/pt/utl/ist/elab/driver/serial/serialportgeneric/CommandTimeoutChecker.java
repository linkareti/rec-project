/* 
 * CommandTimeoutChecker.java created on 27 Jan 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.driver.serial.serialportgeneric;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommand;
import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommandList;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.TimeoutNode;

import com.linkare.rec.impl.logging.LoggerUtil;

/**
 * 
 * @author npadriano
 */
public class CommandTimeoutChecker {

	protected static String LOGGER = "CommandTimeoutChecker.Logger";

	static {
		Logger l = LogManager.getLogManager().getLogger(LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(LOGGER));
		}
	}

	private int cfg;
	private int cur;
	private int str;
	private int datBin;
	private int noData;
	private int stp;
	private int rst;

	private CommandTimeoutCheckerThread checkerThread = new CommandTimeoutCheckerThread();
	private Object synch = null;
	private ICommandTimeoutListener listenerICommandTimeoutListener = null;

	private SerialPortCommand command = null;

	/**
	 * Creates the <code>CommandTimeoutChecker</code>.
	 * 
	 * @param listener
	 * @param cfg
	 * @param cur
	 * @param str
	 * @param datBin
	 * @param noData
	 * @param binNoData
	 * @param stp
	 * @param rst
	 */
	public CommandTimeoutChecker(ICommandTimeoutListener listener, int cfg, int cur, int str, int datBin, int noData,
			int stp, int rst) {
		this.listenerICommandTimeoutListener = listener;
		this.cfg = cfg;
		this.cur = cur;
		this.str = str;
		this.datBin = datBin;
		this.noData = noData;
		this.stp = stp;
		this.rst = rst;

		this.synch = this;
		this.checkerThread.start();
	}

	/**
	 * Creates the <code>CommandTimeoutChecker</code>.
	 * 
	 * @param listener
	 * @param node
	 */
	public CommandTimeoutChecker(ICommandTimeoutListener listener, TimeoutNode node) {
		this(listener, node.getCfg().getTimeInt(), node.getCur().getTimeInt(), node.getStr().getTimeInt(), node
				.getDatBin().getTimeInt(), node.getNoData().getTimeInt(), node.getStp().getTimeInt(), node.getRst()
				.getTimeInt());
	}

	/**
	 * Notifies the registered listener about the event.
	 */
	private void fireICommandTimeoutListenerCommandTimeout() {
		if (listenerICommandTimeoutListener == null) {
			return;
		}
		listenerICommandTimeoutListener.commandTimeout(command);
	}

	private void fireTimeout() {
		if (command == null) {
			listenerICommandTimeoutListener.commandTimeoutNoData();
		} else {
			fireICommandTimeoutListenerCommandTimeout();
		}
	}

	public synchronized void shutdown() {
		checkerThread.shutdown();
	}

	public synchronized void reset() {
		checkerThread.reset();
	}

	public synchronized void checkCommand(SerialPortCommand command) {
		Logger.getLogger(LOGGER).log(Level.FINEST, "Check for the command [" + command + "]");

		if (command == null) {
			return;
		}
		long time = 0;
		if (command.getCommandIdentifier().equalsIgnoreCase(SerialPortCommandList.CFG.toString())) {
			time = cfg;
		} else if (command.getCommandIdentifier().equalsIgnoreCase(SerialPortCommandList.CUR.toString())) {
			time = cur;
		} else if (command.getCommandIdentifier().equalsIgnoreCase(SerialPortCommandList.STR.toString())) {
			time = str;
		} else if (command.getCommandIdentifier().equalsIgnoreCase(SerialPortCommandList.DAT.toString())
				|| command.getCommandIdentifier().equalsIgnoreCase(SerialPortCommandList.BIN.toString())) {
			time = datBin;
		} else if (command.getCommandIdentifier().equalsIgnoreCase(SerialPortCommandList.STP.toString())) {
			time = stp;
		} else if (command.getCommandIdentifier().equalsIgnoreCase(SerialPortCommandList.RST.toString())) {
			time = rst;
		} else {
			return;
		}
		this.command = command;
		checkerThread.waitTime(time);
	}

	public synchronized void checkNoData() {
		Logger.getLogger(LOGGER).log(Level.FINEST, "Check for no data");

		command = null;
		checkerThread.waitTime(noData);
	}

	private class CommandTimeoutCheckerThread extends Thread {

		private boolean running = true;
		private boolean waiting = false;
		private long waitRequestTimestamp;
		private long waitTime;

		/**
		 * Creates the <code>CommandTimeoutChecker.Checker</code>.
		 */
		public CommandTimeoutCheckerThread() {
			super();
			setName(getName() + " - CommandTimeoutChecker.Checker");
		}

		public void reset() {
			synchronized (synch) {
				if (waiting) {
					Logger.getLogger(LOGGER).log(Level.FINEST,
							"Reset the command timeout checker for the command [" + command + "]");
					waiting = false;
					synch.notifyAll();
				}
			}
		}

		public void shutdown() {
			synchronized (synch) {
				Logger.getLogger(LOGGER).log(Level.INFO, "Shuting down the command timeout checker thread - " + getName());
				running = false;
				synch.notifyAll();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			Logger.getLogger(LOGGER).log(Level.INFO, "Starting the command timeout checker thread - " + getName());

			while (running) {
				try {
					synchronized (synch) {
						// waiting for the next check
						synch.wait();
						
						while (running && waiting && waitTime > 0) {
							Logger.getLogger(LOGGER).log(Level.INFO, "Going to wait for [" + waitTime + "] ms");
							// waiting for the specified cmd time
							synch.wait(waitTime);

							if (running && waiting) {
								long diffTime = System.currentTimeMillis() - waitRequestTimestamp;
								if ((diffTime >= waitTime)) {
									// notify that the time has passed
									Logger.getLogger(LOGGER).log(
											Level.INFO,
											"Firing timeout checker for the command [" + command
													+ "] and diff time of [" + diffTime + "] ms");
									fireTimeout();

									// done waiting
									waiting = false;
									command = null;
								} else {
									Logger.getLogger(LOGGER)
											.log(
													Level.FINE,
													"Wait time must have been update. Diff time is [" + diffTime
															+ "] ms. Wait again ["
															+ (running && waiting && waitTime > 0) + "]");

									waitTime = diffTime;
								}
							}
						}
						// paranoia check
						waiting = false;
						command = null;

						// TODO remove old version that migh have problem with
						// sequential reset and wait invocations.
						// if (running) {
						// // waiting for the specified cmd time
						// synch.wait(waitTime);
						// }
						//						
						// if (running && waiting) {
						// long diffTime = System.currentTimeMillis() -
						// waitRequestTimestamp;
						// if ((diffTime >= waitTime)) {
						// // notify that the time has passed
						// Logger.getLogger(LOGGER).log(
						// Level.INFO,
						// "Firing timeout checker for the command [" + command
						// + "] and diff time of ["
						// + diffTime + "] ms");
						// fireTimeout();
						// } else {
						// // something is wrong because the synch was
						// // notified while waiting
						// Logger.getLogger(LOGGER).log(
						// Level.SEVERE,
						// "Something is wrong because the synch was notified while waiting with the diff time of ["
						// + diffTime + "] ms");
						// }
						// }
						// // done waiting
						// waiting = false;
						// command = null;
					}

				} catch (Throwable e) {
					// done waiting
					waiting = false;
					command = null;
					LoggerUtil.logThrowable("Exception occurred during run.", e, Logger.getLogger(LOGGER));
				}
			}

			Logger.getLogger(LOGGER).log(
					Level.WARNING,
					"The command timeout checkeris terminating with the values of running [" + running
							+ "] and waiting [" + waiting + "]");
		}

		/**
		 * @param waitTime the waitTime to set
		 */
		public void waitTime(long waitTime) {
			synchronized (synch) {
				Logger.getLogger(LOGGER).log(Level.FINEST, "Setting wait time [" + waitTime + "] ms");
				this.waitTime = waitTime;
				waiting = true;
				waitRequestTimestamp = System.currentTimeMillis();
				synch.notifyAll();
			}
		}
	}

}
