package pt.utl.ist.elab.driver.serial.serialportgeneric.rs232;

public class InnerFunction {

	private FunctionKind kind = null;
	private Double a = null;
	private Double b = null;
	private Double c = null;

	public InnerFunction(FunctionKind kind, Double a, Double b, Double c) throws Exception {
		if (kind == null || (a == null || b == null) || (kind != FunctionKind.LINEAR && c == null))
			throw new Exception("invalid.arguments.for.transfer.function");
		this.kind = kind;
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public Double applyOver(Double x) {
		if (kind == FunctionKind.LINEAR) {
			return a * x - b;
		} else if (kind == FunctionKind.POWER) {
			return a * Math.pow((x - b), c);
		} else if (kind == FunctionKind.EXP) {
			return a * Math.exp(c * (x - b));
		} else if (kind == FunctionKind.LOG) {
			return a * Math.log(c * (x - b));
		} else if (kind == FunctionKind.SIN) {
			return a * Math.sin(c * x - b);
		} else if (kind == FunctionKind.TG) {
			return a * Math.tan(c * x - b);
		}
		return 0D; // i pay to see you arriving here :D
	}

}
