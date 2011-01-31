package pt.utl.ist.elab.driver.serial.serialportgeneric;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommand;
import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommandList;

/**
 * 
 * <p>
 * This enum lists all the driver states and implements two methods to control
 * the flow of the states while the driver receives commands from the hardware.
 * The method <b>nextState</b> returns a state as response to a command. Most of
 * the responses are <i>UNKWNON</i> as the driver is not expecting all the
 * messages while on a state. The method <b>processeMe</b> is a auxiliary to
 * process the commands. It tells if the command is expected so the data should
 * be processed or ignored.
 * </p>
 * 
 * <p>
 * The switch/case lists can make the code a little homogeneous and difficult to
 * read, I know, but I like exhaustive lists like a matrix and there's nothing I
 * can do about it :D, but you do..., so if you don't like it just delete the
 * lines that returns the same value as the default case.
 * </p>
 * 
 * <p>
 * The purpose of this enum is to simplify the reading and understanding of all
 * responses to the driver commands, so its management and maintenance will be
 * better and faster.
 * </p>
 * 
 * <p>
 * Thank you for your time. Have a nice day here on eLab code.
 * </p>
 * 
 * @author fdias
 * @version Oct 2009
 * 
 */

public enum DriverState {

	ERROR {
		public DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd) {
			// Log this event
			Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(
					Level.INFO,
					"Processing next state... Actual state: " + this.toString() + ", Actual command: "
							+ command.toString());
			switch (command) {
			case IDS:
				return logAndReturn(DriverState.UNKNOWN);
			default:
				return logAndReturn(this);
			}
		}

		public boolean processeMe(SerialPortCommandList command) {
			// Log this event
			switch (command) {
			case IDS:
				return true;
			case ERR:
				return true;
			default:
				return false;
			}
		}

		public boolean acceptHardwareStatus(HardwareStatus status) {
			switch (status) {
			case UNKNOWN:
				return true;
			case ERROR:
				return true;
			default:
				return false;
			}
		}
	},

	UNKNOWN {
		public DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd) {
			// Log this event
			Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(
					Level.INFO,
					"Processing next state... Actual state: " + this.toString() + ", Actual command: "
							+ command.toString());
			switch (command) {
			case IDS:
				if (HardwareStatus.valueOf(cmd.getDataHashMap().get(1)) == HardwareStatus.STOPED) {
					return logAndReturn(STOPPED);
				}
				return logAndReturn(RESETED);
			case CFG:
			case CFGOK:
			case CUR:
			case STR:
			case STROK:
			case DAT:
			case BIN:
			case END:
			case STP:
			case STPOK:
			case RST:
			case RSTOK:
				return logAndReturn(this);
			case ERR:
				return logAndReturn(DriverState.ERROR);
			default:
				return logAndReturn(DriverState.UNKNOWN);
			}
		}

		public boolean processeMe(SerialPortCommandList command) {
			// Log this event
			switch (command) {
			case IDS:
				return true;
			case ERR:
				return true;
			default:
				return false;
			}
		}

		public boolean acceptHardwareStatus(HardwareStatus status) {
			switch (status) {
			case UNKNOWN:
			case RESETED:
			case STOPED:
				return true;
			case ERROR:
				return true;
			default:
				return false;
			}
		}
	},

	STOPING {
		public DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd) {
			// Log this event
			Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(
					Level.INFO,
					"Processing next state... Actual state: " + this.toString() + ", Actual command: "
							+ command.toString());
			switch (command) {
			case IDS:
			case STP:
				return logAndReturn(this);
			case STPOK:
				return logAndReturn(DriverState.STOPPED);
			case ERR:
				return logAndReturn(DriverState.ERROR);
			default:
				return logAndReturn(DriverState.UNKNOWN);
			}
		}

		public boolean processeMe(SerialPortCommandList command) {
			// Log this event
			switch (command) {
			case IDS:
				return true;
			case STP:
				return true;
			case ERR:
				return true;
			default:
				return false;
			}
		}

		public boolean acceptHardwareStatus(HardwareStatus status) {
			switch (status) {
			case STARTED:
				return true;
			default:
				return false;
			}
		}
	},

	STOPWAIT {
		public DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd) {
			// Log this event
			Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(
					Level.INFO,
					"Processing next state... Actual state: " + this.toString() + ", Actual command: "
							+ command.toString());
			switch (command) {
			case IDS:
				return logAndReturn(this);
			case STPOK:
				return logAndReturn(DriverState.STOPPED);
			case ERR:
				return logAndReturn(DriverState.ERROR);
			default:
				return logAndReturn(DriverState.UNKNOWN);
			}
		}

		public boolean processeMe(SerialPortCommandList command) {
			// Log this event
			switch (command) {
			case IDS:
				return true;
			case STPOK:
				return true;
			case ERR:
				return true;
			default:
				return false;
			}
		}

		public boolean acceptHardwareStatus(HardwareStatus status) {
			switch (status) {
			case STARTED:
				return true;
			default:
				return false;
			}
		}
	},

	STOPPED {
		public DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd) {
			// Log this event
			Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(
					Level.INFO,
					"Processing next state... Actual state: " + this.toString() + ", Actual command: "
							+ command.toString());
			switch (command) {
			case IDS:
				return logAndReturn(this);
			case CFG:
				return logAndReturn(DriverState.UNKNOWN);
			case CFGOK:
				return logAndReturn(DriverState.UNKNOWN);
			case CUR:
				return logAndReturn(DriverState.UNKNOWN);
			case STR:
				return logAndReturn(DriverState.UNKNOWN);
			case STROK:
				return logAndReturn(DriverState.UNKNOWN);
			case DAT:
				return logAndReturn(DriverState.UNKNOWN);
			case BIN:
				return logAndReturn(DriverState.UNKNOWN);
			case END:
				return logAndReturn(DriverState.UNKNOWN);
			case STP:
				return logAndReturn(DriverState.UNKNOWN);
			case STPOK:
				return logAndReturn(DriverState.UNKNOWN);
			case RST:
				return logAndReturn(DriverState.UNKNOWN);
			case RSTOK:
				return logAndReturn(DriverState.UNKNOWN);
			case ERR:
				return logAndReturn(DriverState.ERROR);
			default:
				return logAndReturn(DriverState.UNKNOWN);
			}
		}

		public boolean processeMe(SerialPortCommandList command) {
			// Log this event
			switch (command) {
			case IDS:
				return true;
			case CFG:
				return false;
			case CFGOK:
				return false;
			case CUR:
				return false;
			case STR:
				return false;
			case STROK:
				return false;
			case DAT:
				return false;
			case BIN:
				return false;
			case END:
				return false;
			case STP:
				return false;
			case STPOK:
				return false;
			case RST:
				return false;
			case RSTOK:
				return false;
			case ERR:
				return true;
			default:
				return false;
			}
		}

		public boolean acceptHardwareStatus(HardwareStatus status) {
			switch (status) {
			case STOPED:
				return true;
			default:
				return false;
			}
		}
	},

	CONFIGURING {
		public DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd) {
			// Log this event
			Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(
					Level.INFO,
					"Processing next state... Actual state: " + this.toString() + ", Actual command: "
							+ command.toString());
			switch (command) {
			case IDS:
				return logAndReturn(this);
			case CFG:
				return logAndReturn(DriverState.CONFIGUREWAIT);
			case CFGOK:
				return logAndReturn(DriverState.CONFIGURED);
			case CUR:
				return logAndReturn(DriverState.UNKNOWN);
			case STR:
				return logAndReturn(DriverState.UNKNOWN);
			case STROK:
				return logAndReturn(DriverState.UNKNOWN);
			case DAT:
				return logAndReturn(DriverState.UNKNOWN);
			case BIN:
				return logAndReturn(DriverState.UNKNOWN);
			case END:
				return logAndReturn(DriverState.UNKNOWN);
			case STP:
				return logAndReturn(DriverState.UNKNOWN);
			case STPOK:
				return logAndReturn(DriverState.UNKNOWN);
			case RST:
				return logAndReturn(DriverState.UNKNOWN);
			case RSTOK:
				return logAndReturn(DriverState.UNKNOWN);
			case ERR:
				return logAndReturn(DriverState.ERROR);
			default:
				return logAndReturn(DriverState.UNKNOWN);
			}
		}

		public boolean processeMe(SerialPortCommandList command) {
			// Log this event
			switch (command) {
			case IDS:
				return true;
			case CFG:
				return true;
			case CFGOK:
				return true;
			case CUR:
				return false;
			case STR:
				return false;
			case STROK:
				return false;
			case DAT:
				return false;
			case BIN:
				return false;
			case END:
				return false;
			case STP:
				return false;
			case STPOK:
				return false;
			case RST:
				return false;
			case RSTOK:
				return false;
			case ERR:
				return true;
			default:
				return false;
			}
		}

		public boolean acceptHardwareStatus(HardwareStatus status) {
			switch (status) {
			case STOPED:
				return true;
			case UNKNOWN:
				return true;
			case RESETED:
				return true;
			default:
				return false;
			}
		}
	},

	CONFIGUREWAIT {
		public DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd) {
			// Log this event
			Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(
					Level.INFO,
					"Processing next state... Actual state: " + this.toString() + ", Actual command: "
							+ command.toString());
			switch (command) {
			case IDS:
				return logAndReturn(this);
			case CFG:
				return logAndReturn(DriverState.UNKNOWN);
			case CFGOK:
				return logAndReturn(DriverState.CONFIGURED);
			case CUR:
				return logAndReturn(DriverState.UNKNOWN);
			case STR:
				return logAndReturn(DriverState.UNKNOWN);
			case STROK:
				return logAndReturn(DriverState.UNKNOWN);
			case DAT:
				return logAndReturn(DriverState.UNKNOWN);
			case BIN:
				return logAndReturn(DriverState.UNKNOWN);
			case END:
				return logAndReturn(DriverState.UNKNOWN);
			case STP:
				return logAndReturn(DriverState.UNKNOWN);
			case STPOK:
				return logAndReturn(DriverState.UNKNOWN);
			case RST:
				return logAndReturn(DriverState.UNKNOWN);
			case RSTOK:
				return logAndReturn(DriverState.UNKNOWN);
			case ERR:
				return logAndReturn(DriverState.ERROR);
			default:
				return logAndReturn(DriverState.UNKNOWN);
			}
		}

		public boolean processeMe(SerialPortCommandList command) {
			// Log this event
			switch (command) {
			case IDS:
				return true;
			case CFG:
				return false;
			case CFGOK:
				return true;
			case CUR:
				return false;
			case STR:
				return false;
			case STROK:
				return false;
			case DAT:
				return false;
			case BIN:
				return false;
			case END:
				return false;
			case STP:
				return false;
			case STPOK:
				return false;
			case RST:
				return false;
			case RSTOK:
				return false;
			case ERR:
				return true;
			default:
				return false;
			}
		}

		public boolean acceptHardwareStatus(HardwareStatus status) {
			switch (status) {
			case STOPED:
				return true;
			case UNKNOWN:
				return true;
			case RESETED:
				return true;
			default:
				return false;
			}
		}
	},

	CONFIGURED {
		public DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd) {
			// Log this event
			Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(
					Level.INFO,
					"Processing next state... Actual state: " + this.toString() + ", Actual command: "
							+ command.toString());
			switch (command) {
			case IDS:
				return logAndReturn(this);
			case CFG:
				return logAndReturn(DriverState.UNKNOWN);
			case CFGOK:
				return logAndReturn(DriverState.CONFIGURED);
			case CUR:
				return logAndReturn(DriverState.UNKNOWN);
			case STR:
				return logAndReturn(DriverState.UNKNOWN);
			case STROK:
				return logAndReturn(DriverState.UNKNOWN);
			case DAT:
				return logAndReturn(DriverState.UNKNOWN);
			case BIN:
				return logAndReturn(DriverState.UNKNOWN);
			case END:
				return logAndReturn(DriverState.UNKNOWN);
			case STP:
				return logAndReturn(DriverState.UNKNOWN);
			case STPOK:
				return logAndReturn(DriverState.UNKNOWN);
			case RST:
				return logAndReturn(DriverState.UNKNOWN);
			case RSTOK:
				return logAndReturn(DriverState.UNKNOWN);
			case ERR:
				return logAndReturn(DriverState.ERROR);
			default:
				return logAndReturn(DriverState.UNKNOWN);
			}
		}

		public boolean processeMe(SerialPortCommandList command) {
			// Log this event
			switch (command) {
			case IDS:
				return true;
			case CFG:
				return false;
			case CFGOK:
				return false;
			case CUR:
				return false;
			case STR:
				return false;
			case STROK:
				return false;
			case DAT:
				return false;
			case BIN:
				return false;
			case END:
				return false;
			case STP:
				return false;
			case STPOK:
				return false;
			case RST:
				return false;
			case RSTOK:
				return false;
			case ERR:
				return true;
			default:
				return false;
			}
		}

		public boolean acceptHardwareStatus(HardwareStatus status) {
			switch (status) {
			case CONFIGURED:
				return true;
			default:
				return false;
			}
		}
	},

	STARTING {
		public DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd) {
			// Log this event
			Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(
					Level.INFO,
					"Processing next state... Actual state: " + this.toString() + ", Actual command: "
							+ command.toString());
			switch (command) {
			case IDS:
				return logAndReturn(this);
			case CFG:
				return logAndReturn(DriverState.UNKNOWN);
			case CFGOK:
				return logAndReturn(DriverState.UNKNOWN);
			case CUR:
				return logAndReturn(DriverState.UNKNOWN);
			case STR:
				return logAndReturn(DriverState.STARTWAIT);
			case STROK:
				return logAndReturn(DriverState.STARTED);
			case DAT:
				return logAndReturn(DriverState.UNKNOWN);
			case BIN:
				return logAndReturn(DriverState.UNKNOWN);
			case END:
				return logAndReturn(DriverState.UNKNOWN);
			case STP:
				return logAndReturn(DriverState.UNKNOWN);
			case STPOK:
				return logAndReturn(DriverState.UNKNOWN);
			case RST:
				return logAndReturn(DriverState.UNKNOWN);
			case RSTOK:
				return logAndReturn(DriverState.UNKNOWN);
			case ERR:
				return logAndReturn(DriverState.ERROR);
			default:
				return logAndReturn(DriverState.UNKNOWN);
			}
		}

		public boolean processeMe(SerialPortCommandList command) {
			// Log this event
			switch (command) {
			case IDS:
				return true;
			case CFG:
				return false;
			case CFGOK:
				return false;
			case CUR:
				return false;
			case STR:
				return true;
			case STROK:
				return true;
			case DAT:
				return false;
			case BIN:
				return false;
			case END:
				return false;
			case STP:
				return false;
			case STPOK:
				return false;
			case RST:
				return false;
			case RSTOK:
				return false;
			case ERR:
				return true;
			default:
				return false;
			}
		}

		public boolean acceptHardwareStatus(HardwareStatus status) {
			switch (status) {
			case CONFIGURED:
				return true;
			case STOPED:
				return true;
			default:
				return false;
			}
		}
	},

	STARTWAIT {
		public DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd) {
			// Log this event
			Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(
					Level.INFO,
					"Processing next state... Actual state: " + this.toString() + ", Actual command: "
							+ command.toString());
			switch (command) {
			case IDS:
				return logAndReturn(this);
			case CFG:
				return logAndReturn(DriverState.UNKNOWN);
			case CFGOK:
				return logAndReturn(DriverState.UNKNOWN);
			case CUR:
				return logAndReturn(DriverState.UNKNOWN);
			case STR:
				return logAndReturn(DriverState.UNKNOWN);
			case STROK:
				return logAndReturn(DriverState.STARTED);
			case DAT:
				return logAndReturn(DriverState.UNKNOWN);
			case BIN:
				return logAndReturn(DriverState.UNKNOWN);
			case END:
				return logAndReturn(DriverState.UNKNOWN);
			case STP:
				return logAndReturn(DriverState.UNKNOWN);
			case STPOK:
				return logAndReturn(DriverState.UNKNOWN);
			case RST:
				return logAndReturn(DriverState.UNKNOWN);
			case RSTOK:
				return logAndReturn(DriverState.UNKNOWN);
			case ERR:
				return logAndReturn(DriverState.ERROR);
			default:
				return logAndReturn(DriverState.UNKNOWN);
			}
		}

		public boolean processeMe(SerialPortCommandList command) {
			// Log this event
			switch (command) {
			case IDS:
				return true;
			case CFG:
				return false;
			case CFGOK:
				return false;
			case CUR:
				return false;
			case STR:
				return false;
			case STROK:
				return true;
			case DAT:
				return false;
			case BIN:
				return false;
			case END:
				return false;
			case STP:
				return false;
			case STPOK:
				return false;
			case RST:
				return false;
			case RSTOK:
				return false;
			case ERR:
				return true;
			default:
				return false;
			}
		}

		public boolean acceptHardwareStatus(HardwareStatus status) {
			switch (status) {
			case CONFIGURED:
				return true;
			case STOPED:
				return true;
			default:
				return false;
			}
		}
	},

	STARTED {
		public DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd) {
			// Log this event
			Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(
					Level.INFO,
					"Processing next state... Actual state: " + this.toString() + ", Actual command: "
							+ command.toString());
			switch (command) {
			case IDS:
				return logAndReturn(this);
			case CFG:
				return logAndReturn(DriverState.UNKNOWN);
			case CFGOK:
				return logAndReturn(DriverState.UNKNOWN);
			case CUR:
				return logAndReturn(DriverState.UNKNOWN);
			case STR:
				return logAndReturn(DriverState.UNKNOWN);
			case STROK:
				return logAndReturn(DriverState.UNKNOWN);
			case DAT:
				return logAndReturn(DriverState.RECEIVINGDATA);
			case BIN:
				return logAndReturn(DriverState.RECEIVINGBIN);
			case END:
				return logAndReturn(DriverState.UNKNOWN);
			case STP:
				return logAndReturn(DriverState.UNKNOWN);
			case STPOK:
				return logAndReturn(DriverState.STOPPED);
			case RST:
				return logAndReturn(DriverState.UNKNOWN);
			case RSTOK:
				return logAndReturn(DriverState.UNKNOWN);
			case ERR:
				return logAndReturn(DriverState.ERROR);
			default:
				return logAndReturn(DriverState.UNKNOWN);
			}
		}

		public boolean processeMe(SerialPortCommandList command) {
			// Log this event
			switch (command) {
			case IDS:
				return true;
			case CFG:
				return false;
			case CFGOK:
				return false;
			case CUR:
				return false;
			case STR:
				return false;
			case STROK:
				return false;
			case DAT:
				return true;
			case BIN:
				return true;
			case END:
				return false;
			case STP:
				return false;
			case STPOK:
				return false;
			case RST:
				return false;
			case RSTOK:
				return false;
			case ERR:
				return true;
			default:
				return false;
			}
		}

		public boolean acceptHardwareStatus(HardwareStatus status) {
			switch (status) {
			case STARTED:
				return true;
			default:
				return false;
			}
		}
	},

	RECEIVINGDATA {
		public DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd) {
			// Log this event
			Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(
					Level.INFO,
					"Processing next state... Actual state: " + this.toString() + ", Actual command: "
							+ command.toString());
			switch (command) {
			case IDS:
				return logAndReturn(this);
			case END:
				return logAndReturn(DriverState.STARTED);
			case ERR:
				return logAndReturn(DriverState.ERROR);
			default:
				return logAndReturn(this);
			}
		}

		public boolean processeMe(SerialPortCommandList command) {
			// Log this event
			switch (command) {
			case IDS:
				return true;
			case END:
				return true;
			case ERR:
				return true;
			default:
				return false;
			}
		}

		public boolean acceptHardwareStatus(HardwareStatus status) {
			switch (status) {
			case STARTED:
				return true;
			default:
				return false;
			}
		}
	},

	RECEIVINGBIN {
		@Deprecated
		public DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd) {
			// Log this event
			Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(
					Level.INFO,
					"Processing next state... Actual state: " + this.toString() + ", Actual command: "
							+ command.toString());
			switch (command) {
			// should never achieve this point of code ;)
			default:
				return logAndReturn(this);
			}
		}

		@Deprecated
		public boolean processeMe(SerialPortCommandList command) {
			// Log this event
			switch (command) {
			// should never achieve this point of code ;)
			default:
				return false;
			}
		}

		@Deprecated
		public boolean acceptHardwareStatus(HardwareStatus status) {
			switch (status) {
			case STARTED:
				return true;
			default:
				return false;
			}
		}
	},

	RECEIVINGCONFIG {
		public DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd) {
			// Log this event
			Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(
					Level.INFO,
					"Processing next state... Actual state: " + this.toString() + ", Actual command: "
							+ command.toString());
			switch (command) {
			case IDS:
				return logAndReturn(this);
			case CFG:
				return logAndReturn(DriverState.UNKNOWN);
			case CFGOK:
				return logAndReturn(DriverState.UNKNOWN);
				// Okay, I'm expecting this state, but I need to return to the
				// state before RECEIVINGCONFIG
			case CUR:
				return logAndReturn(lastState);
			case STR:
				return logAndReturn(DriverState.UNKNOWN);
			case STROK:
				return logAndReturn(DriverState.UNKNOWN);
			case DAT:
				return logAndReturn(DriverState.UNKNOWN);
			case BIN:
				return logAndReturn(DriverState.UNKNOWN);
			case END:
				return logAndReturn(DriverState.UNKNOWN);
			case STP:
				return logAndReturn(DriverState.UNKNOWN);
			case STPOK:
				return logAndReturn(DriverState.UNKNOWN);
			case RST:
				return logAndReturn(DriverState.UNKNOWN);
			case RSTOK:
				return logAndReturn(DriverState.UNKNOWN);
			case ERR:
				return logAndReturn(DriverState.ERROR);
			default:
				return logAndReturn(DriverState.UNKNOWN);
			}
		}

		public boolean processeMe(SerialPortCommandList command) {
			// Log this event
			switch (command) {
			case IDS:
				return true;
			case CFG:
				return false;
			case CFGOK:
				return false;
			case CUR:
				return true;
			case STR:
				return false;
			case STROK:
				return false;
			case DAT:
				return false;
			case BIN:
				return false;
			case END:
				return false;
			case STP:
				return false;
			case STPOK:
				return false;
			case RST:
				return false;
			case RSTOK:
				return false;
			case ERR:
				return true;
			default:
				return false;
			}
		}

		public boolean acceptHardwareStatus(HardwareStatus status) {
			switch (status) {
			case UNKNOWN:
				return true;
			case STOPED:
				return true;
			case CONFIGURED:
				return true;
			case RESETED:
				return true;
			default:
				return false;
			}
		}
	},

	RESETING {
		public DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd) {
			// Log this event
			Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(
					Level.INFO,
					"Processing next state... Actual state: " + this.toString() + ", Actual command: "
							+ command.toString());
			switch (command) {
			case IDS:
				return logAndReturn(this);
			case CFG:
				return logAndReturn(DriverState.UNKNOWN);
			case CFGOK:
				return logAndReturn(DriverState.UNKNOWN);
			case CUR:
				return logAndReturn(DriverState.UNKNOWN);
			case STR:
				return logAndReturn(DriverState.UNKNOWN);
			case STROK:
				return logAndReturn(DriverState.UNKNOWN);
			case DAT:
				return logAndReturn(DriverState.UNKNOWN);
			case BIN:
				return logAndReturn(DriverState.UNKNOWN);
			case END:
				return logAndReturn(DriverState.UNKNOWN);
			case STP:
				return logAndReturn(DriverState.UNKNOWN);
			case STPOK:
				return logAndReturn(DriverState.UNKNOWN);
			case RST:
				return logAndReturn(DriverState.RESETWAIT);
			case RSTOK:
				return logAndReturn(DriverState.UNKNOWN);
			case ERR:
				return logAndReturn(DriverState.ERROR);
			default:
				return logAndReturn(DriverState.UNKNOWN);
			}
		}

		public boolean processeMe(SerialPortCommandList command) {
			// Log this event
			switch (command) {
			case IDS:
				return true;
			case CFG:
				return false;
			case CFGOK:
				return false;
			case CUR:
				return false;
			case STR:
				return false;
			case STROK:
				return false;
			case DAT:
				return false;
			case BIN:
				return false;
			case END:
				return false;
			case STP:
				return false;
			case STPOK:
				return false;
			case RST:
				return true;
			case RSTOK:
				return false;
			case ERR:
				return true;
			default:
				return false;
			}
		}

		public boolean acceptHardwareStatus(HardwareStatus status) {
			switch (status) {
			default:
				return true;
			}
		}
	},

	RESETWAIT {
		public DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd) {
			// Log this event
			Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(
					Level.INFO,
					"Processing next state... Actual state: " + this.toString() + ", Actual command: "
							+ command.toString());
			switch (command) {
			case IDS:
				return logAndReturn(this);
			case CFG:
				return logAndReturn(DriverState.UNKNOWN);
			case CFGOK:
				return logAndReturn(DriverState.UNKNOWN);
			case CUR:
				return logAndReturn(DriverState.UNKNOWN);
			case STR:
				return logAndReturn(DriverState.UNKNOWN);
			case STROK:
				return logAndReturn(DriverState.UNKNOWN);
			case DAT:
				return logAndReturn(DriverState.UNKNOWN);
			case BIN:
				return logAndReturn(DriverState.UNKNOWN);
			case END:
				return logAndReturn(DriverState.UNKNOWN);
			case STP:
				return logAndReturn(DriverState.UNKNOWN);
			case STPOK:
				return logAndReturn(DriverState.UNKNOWN);
			case RST:
				return logAndReturn(DriverState.UNKNOWN);
			case RSTOK:
				return logAndReturn(DriverState.RESETED);
			case ERR:
				return logAndReturn(DriverState.ERROR);
			default:
				return logAndReturn(DriverState.UNKNOWN);
			}
		}

		public boolean processeMe(SerialPortCommandList command) {
			// Log this event
			switch (command) {
			case IDS:
				return true;
			case CFG:
				return false;
			case CFGOK:
				return false;
			case CUR:
				return false;
			case STR:
				return false;
			case STROK:
				return false;
			case DAT:
				return false;
			case BIN:
				return false;
			case END:
				return false;
			case STP:
				return false;
			case STPOK:
				return false;
			case RST:
				return false;
			case RSTOK:
				return true;
			case ERR:
				return true;
			default:
				return false;
			}
		}

		public boolean acceptHardwareStatus(HardwareStatus status) {
			switch (status) {
			default:
				return true;
			}
		}
	},

	RESETED {
		public DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd) {
			// Log this event
			Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(
					Level.INFO,
					"Processing next state... Actual state: " + this.toString() + ", Actual command: "
							+ command.toString());
			switch (command) {
			case IDS:
				return logAndReturn(this);
			case CFG:
				return logAndReturn(DriverState.UNKNOWN);
			case CFGOK:
				return logAndReturn(DriverState.UNKNOWN);
			case CUR:
				return logAndReturn(DriverState.UNKNOWN);
			case STR:
				return logAndReturn(DriverState.UNKNOWN);
			case STROK:
				return logAndReturn(DriverState.UNKNOWN);
			case DAT:
				return logAndReturn(DriverState.UNKNOWN);
			case BIN:
				return logAndReturn(DriverState.UNKNOWN);
			case END:
				return logAndReturn(DriverState.UNKNOWN);
			case STP:
				return logAndReturn(DriverState.UNKNOWN);
			case STPOK:
				return logAndReturn(DriverState.UNKNOWN);
			case RST:
				return logAndReturn(DriverState.UNKNOWN);
			case RSTOK:
				return logAndReturn(DriverState.UNKNOWN);
			case ERR:
				return logAndReturn(DriverState.ERROR);
			default:
				return logAndReturn(DriverState.UNKNOWN);
			}
		}

		public boolean processeMe(SerialPortCommandList command) {
			// Log this event
			switch (command) {
			case IDS:
				return true;
			case CFG:
				return false;
			case CFGOK:
				return false;
			case CUR:
				return false;
			case STR:
				return false;
			case STROK:
				return false;
			case DAT:
				return false;
			case BIN:
				return false;
			case END:
				return false;
			case STP:
				return false;
			case STPOK:
				return false;
			case RST:
				return false;
			case RSTOK:
				return false;
			case ERR:
				return true;
			default:
				return false;
			}
		}

		public boolean acceptHardwareStatus(HardwareStatus status) {
			switch (status) {
			case STOPED:
			case RESETED:
				return true;
			default:
				return false;
			}
		}
	};

	private static DriverState lastState = null;

	public void setLastState(DriverState lastState) {
		DriverState.lastState = lastState;
	}

	private static DriverState logAndReturn(DriverState driverState) {
		Logger.getLogger(AbstractSerialPortDriver.SERIAL_PORT_LOGGER).log(Level.FINE,
				"Returning next state... New state: " + driverState.toString());
		return driverState;
	}

	/**
	 * 
	 * In response to the current command, the current DriverState must change
	 * to another DriverState. This decision is made by this method.
	 * 
	 * @param command
	 * @return {@link DriverState}
	 * @author fdias
	 * @param cmd 
	 */
	public abstract DriverState nextState(SerialPortCommandList command, SerialPortCommand cmd);

	/**
	 * 
	 * This command have data that deserves to be processed?
	 * 
	 * @param command
	 * @return boolean
	 * @author fdias
	 */
	public abstract boolean processeMe(SerialPortCommandList command);

	/**
	 * 
	 * Checks if the HardwareStatus from the hardware, that comes on IDS
	 * messages, is expected on the current DriverState.
	 * 
	 * @param status
	 * @return boolean
	 * @author fdias
	 */
	public abstract boolean acceptHardwareStatus(HardwareStatus status);

}