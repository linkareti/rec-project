package pt.utl.ist.elab.driver.serial.serialportgeneric.rs232;

import java.text.DecimalFormat;

import pt.utl.ist.elab.driver.serial.serialportgeneric.rs232.TransferFunction.TransferFunctionType;

public class SerialPortRs232Channel {

	private DecimalFormat format = null;
	private Integer order = null;
	private TransferFunction inputTransferFunction = null;
	private TransferFunction outputTransferFunction = null;

	public SerialPortRs232Channel(String format, Integer order) throws Exception {
		if (format == null || order == null)
			throw new Exception("invalid.rs232.channel");

		try {
			if (!format.equals("")) {
				this.format = new DecimalFormat();
				this.format.applyPattern(format.replace("#", "0"));
			}
		} catch (IllegalArgumentException e) {
			throw new Exception("parameter.input.format.not.valid");
		}

		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	public void addInputTransferFunction(FunctionKind kind, Double a, Double b, Double c) throws Exception {
		addTransferFunction(TransferFunctionType.INPUT, kind, a, b, c);
	}

	public void addOutputTransferFunction(FunctionKind kind, Double a, Double b, Double c) throws Exception {
		addTransferFunction(TransferFunctionType.OUTPUT, kind, a, b, c);
	}

	private void addTransferFunction(TransferFunctionType type, FunctionKind kind, Double a, Double b, Double c)
			throws Exception {
		TransferFunction transferFunction = null;
		if (type == TransferFunctionType.INPUT) {
			if (inputTransferFunction == null) {
				inputTransferFunction = new TransferFunction(TransferFunctionType.INPUT);
				transferFunction = inputTransferFunction;
			} else
				throw new Exception("input.transference.function.already.exists.for.current.channel");
		} else if (type == TransferFunctionType.OUTPUT) {
			if (outputTransferFunction == null) {
				outputTransferFunction = new TransferFunction(TransferFunctionType.INPUT);
				transferFunction = outputTransferFunction;
			} else
				throw new Exception("output.transference.function.already.exists.for.current.channel");
		}
		if (kind == FunctionKind.LINEAR) {
			transferFunction.addLinearFunction(a, b);
		} else if (kind == FunctionKind.POWER) {
			transferFunction.addPowerFunction(a, b, c);
		} else if (kind == FunctionKind.EXP) {
			transferFunction.addExpFunction(a, b, c);
		} else if (kind == FunctionKind.LOG) {
			transferFunction.addLogFunction(a, b, c);
		} else if (kind == FunctionKind.SIN) {
			transferFunction.addSinFunction(a, b, c);
		} else if (kind == FunctionKind.TG) {
			transferFunction.addTgFunction(a, b, c);
		}
	}

	public Double applyOutputTransferFunction(Double value) {
		if (outputTransferFunction == null)
			return value;
		return outputTransferFunction.applyOver(value);
	}

}
