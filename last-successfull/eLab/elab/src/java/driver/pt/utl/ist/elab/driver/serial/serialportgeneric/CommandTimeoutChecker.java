package pt.utl.ist.elab.driver.serial.serialportgeneric;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommand;
import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommandList;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.TimeoutNode;

/**
 * 
 * @author npadriano
 */
public class CommandTimeoutChecker {

	private static final Logger LOGGER = Logger.getLogger(CommandTimeoutChecker.class.getName());

	private final int cfg;
	private final int cur;
	private final int str;
	private final int datBin;
	private final int noData;
	private final int end;
	private final int stp;
	private final int rst;

	private final CommandTimeoutCheckerThread checkerThread = new CommandTimeoutCheckerThread();
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
	 * @param end
	 * @param binNoData
	 * @param stp
	 * @param rst
	 */
	public CommandTimeoutChecker(final ICommandTimeoutListener listener, final int cfg, final int cur, final int str,
			final int datBin, final int noData, final int end, final int stp, final int rst) {
		listenerICommandTimeoutListener = listener;
		this.cfg = cfg;
		this.cur = cur;
		this.str = str;
		this.datBin = datBin;
		this.noData = noData;
		this.end = end;
		this.stp = stp;
		this.rst = rst;

		synch = this;
		checkerThread.start();
	}

	/**
	 * Creates the <code>CommandTimeoutChecker</code>.
	 * 
	 * @param listener
	 * @param node
	 */
	public CommandTimeoutChecker(final ICommandTimeoutListener listener, final TimeoutNode node) {
		this(listener, node.getCfg().getTimeInt(), node.getCur().getTimeInt(), node.getStr().getTimeInt(), node
				.getDatBin().getTimeInt(), node.getNoData().getTimeInt(), node.getEnd().getTimeInt(), node.getStp()
				.getTimeInt(), node.getRst().getTimeInt());
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

	public synchronized void checkCommand(final SerialPortCommand command) {
		LOGGER.log(Level.FINEST, "Check for the command [" + command + "]");

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
		} else if (command.getCommandIdentifier().equalsIgnoreCase(SerialPortCommandList.END.toString())) {
			time = end;
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
		LOGGER.log(Level.FINEST, "Check for no data");

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
					LOGGER.log(Level.FINEST, "Reset the command timeout checker for the command [" + command + "]");
					waiting = false;
					synch.notifyAll();
				}
			}
		}

		public void shutdown() {
			synchronized (synch) {
				LOGGER.log(Level.INFO, "Shuting down the command timeout checker thread - " + getName());
				running = false;
				synch.notifyAll();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			LOGGER.log(Level.INFO, "Starting the command timeout checker thread - " + getName());

			while (running) {
				try {
					synchronized (synch) {
						// waiting for the next check
						synch.wait();

						while (running && waiting && waitTime > 0) {
							LOGGER.log(Level.INFO, "Going to wait for [" + waitTime + "] ms");
							// waiting for the specified cmd time
							synch.wait(waitTime);

							if (running && waiting) {
								final long diffTime = System.currentTimeMillis() - waitRequestTimestamp;
								if ((diffTime >= waitTime)) {
									// notify that the time has passed
									LOGGER.log(Level.INFO, "Firing timeout checker for the command [" + command
											+ "] and diff time of [" + diffTime + "] ms");
									fireTimeout();

									// done waiting
									waiting = false;
									command = null;
								} else {
									waitTime -= diffTime;
									LOGGER.log(Level.FINE, "Wait time must have been update. Diff [" + diffTime
											+ "] ms Wait [" + waitTime + "] ms. Wait again ["
											+ (running && waiting && waitTime > 0) + "]");
								}
							}
						}
						// paranoia check
						waiting = false;
						command = null;
					}

				} catch (final Throwable e) {
					// done waiting
					waiting = false;
					command = null;
					LOGGER.log(Level.SEVERE, "Exception occurred during run.", e);
				}
			}

			LOGGER.log(Level.WARNING, "The command timeout checkeris terminating with the values of running ["
					+ running + "] and waiting [" + waiting + "]");
		}

		/**
		 * @param waitTime the waitTime to set
		 */
		public void waitTime(final long waitTime) {
			synchronized (synch) {
				LOGGER.log(Level.FINEST, "Setting wait time [" + waitTime + "] ms");
				this.waitTime = waitTime;
				waiting = true;
				waitRequestTimestamp = System.currentTimeMillis();
				synch.notifyAll();
			}
		}
	}

}
