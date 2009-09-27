package pt.utl.ist.elab.driver.serial.serialportgeneric.rs232;

import java.util.HashMap;

public class TransferFunction {

	private TransferFunctionType type = null;
	private HashMap<Integer, InnerFunction> functions = new HashMap<Integer, InnerFunction>();

	public TransferFunction(TransferFunctionType type) {
		this.type = type;
	}

	public void addLinearFunction(Double weight, Double center) throws Exception {
		InnerFunction linear = new InnerFunction(FunctionKind.LINEAR, weight, center, null);
		functions.put(functions.size() + 1, linear);
	}

	public void addPowerFunction(Double weight, Double center, Double power) throws Exception {
		InnerFunction linear = new InnerFunction(FunctionKind.POWER, weight, center, power);
		functions.put(functions.size() + 1, linear);
	}

	public void addExpFunction(Double weight, Double center, Double coeficient) throws Exception {
		InnerFunction linear = new InnerFunction(FunctionKind.EXP, weight, center, coeficient);
		functions.put(functions.size() + 1, linear);
	}

	public void addLogFunction(Double weight, Double center, Double coeficient) throws Exception {
		InnerFunction linear = new InnerFunction(FunctionKind.LOG, weight, center, coeficient);
		functions.put(functions.size() + 1, linear);
	}

	public void addSinFunction(Double weight, Double delta, Double coeficient) throws Exception {
		InnerFunction linear = new InnerFunction(FunctionKind.SIN, weight, delta, coeficient);
		functions.put(functions.size() + 1, linear);
	}

	public void addTgFunction(Double weight, Double delta, Double coeficient) throws Exception {
		InnerFunction linear = new InnerFunction(FunctionKind.TG, weight, delta, coeficient);
		functions.put(functions.size() + 1, linear);
	}

	public Double applyOver(Double value) {
		Double total = 0D;
		if (functions.size() == 0)
			return value;
		for (InnerFunction innerFunction : functions.values()) {
			total = total + innerFunction.applyOver(value);
		}
		return total;
	}

	public TransferFunctionType getTransferFunctionType() {
		return this.type;
	}

	public static enum TransferFunctionType {
		INPUT, OUTPUT
	}

}
