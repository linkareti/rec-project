package pt.utl.ist.elab.driver.serial.serialportgeneric.rs232;

import java.text.DecimalFormat;

import pt.utl.ist.elab.driver.serial.serialportgeneric.rs232.TransferFunction.TransferFunctionType;

public class SerialPortRs232Parameter {

	private DecimalFormat inputFormat = null;
	private DecimalFormat outputFormat = null;
	private Double maxValue = null;
	private Double minValue = null;
	private Integer order = null;
	private TransferFunction inputTransferFunction = null;
	private TransferFunction outputTransferFunction = null;

	private String name = null;
	private SerialPortRs232ParameterType type = SerialPortRs232ParameterType.UNDEFINED;
	private Double defaultContinuousValue = null;
	private Integer defaultSelection = null;
	private Boolean defaultOnOff = null;

	public SerialPortRs232Parameter(String inputFormat, String outputFormat, Double maxValue, Double minValue,
			Integer order) throws Exception {
		if (inputFormat == null || outputFormat == null || order == null
				|| (maxValue != null && minValue != null && minValue > maxValue))
			throw new Exception("invalid.rs232.parameter");

		try {
			if (!inputFormat.equals("")) {
				this.inputFormat = new DecimalFormat();
				this.inputFormat.applyPattern(inputFormat.replace("#", "0"));
			}
		} catch (IllegalArgumentException e) {
			throw new Exception("parameter.input.format.not.valid");
		}
		try {
			if (!outputFormat.equals("")) {
				this.outputFormat = new DecimalFormat();
				this.outputFormat.applyPattern(outputFormat.replace("#", "0"));
			}
		} catch (IllegalArgumentException e) {
			throw new Exception("parameter.output.format.not.valid");
		}

		this.maxValue = maxValue;
		this.minValue = minValue;
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
				throw new Exception("input.transference.function.already.exists.for.current.parameter");
		} else if (type == TransferFunctionType.OUTPUT) {
			if (outputTransferFunction == null) {
				outputTransferFunction = new TransferFunction(TransferFunctionType.INPUT);
				transferFunction = outputTransferFunction;
			} else
				throw new Exception("output.transference.function.already.exists.for.current.parameter");
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

	public Double getMaxValue() {
		return maxValue;
	}

	public Double getMinValue() {
		return minValue;
	}

	public String applyInputFormat(Double value) {
		if (inputFormat == null)
			return value.toString();
		return inputFormat.format(value).toString();
	}

	public String applyOutputFormat(Double value) {
		if (outputFormat == null)
			return value.toString();
		return outputFormat.format(value).toString();
	}

	public Double applyInputTransferFunction(Double value) {
		if (inputTransferFunction == null)
			return value;
		return inputTransferFunction.applyOver(value);
	}

	public Double applyOutputTransferFunction(Double value) {
		if (outputTransferFunction == null)
			return value;
		return outputTransferFunction.applyOver(value);
	}

}
