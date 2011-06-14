package com.linkare.jmf.media.protocol.function;

import java.util.StringTokenizer;

import javax.media.Control;

public enum FunctorType {
	SILENCE(new SilenceFunctor(), "silence") {
		@Override
		public void parseOptions(String optionsStr) {
		    System.out.println(optionsStr);
			if (optionsStr != null && optionsStr.trim().length() > 0)
				throw new RuntimeException(this.getFunctionName()
						+ " does not support any options (" + optionsStr + ")");
		}

		@Override
		public Control getFunctorControl() {
		    return null;
		}
	},
	SIN_WAVE(new SineFunctor(), "sin") {
		@Override
		public void parseOptions(String optionsStr) {
			StringTokenizer tokens = new StringTokenizer(optionsStr, "/", false);
			if (tokens.countTokens() != 2)
				throw new RuntimeException(this.getFunctionName()
						+ " only supports options /<frequency>/<phase> ("
						+ optionsStr + ")");
			String frequencyStr = tokens.nextToken();
			String phaseStr = tokens.nextToken();

			double frequency = 0;
			double phase = 0;

			try {
				frequency = Double.parseDouble(frequencyStr);
			} catch (NumberFormatException e) {
				throw new RuntimeException(this.getFunctionName()
						+ " frequency option must be a valid number ("
						+ frequencyStr + ")");
			}
			try {
				phase = Double.parseDouble(phaseStr);
			} catch (NumberFormatException e) {
				throw new RuntimeException(this.getFunctionName()
						+ " phase option must be a valid number (" + phaseStr
						+ ")");
			}
			SineFunctor sineFunctor = (SineFunctor) this.getFunctor();
			sineFunctor.setFrequency(frequency);
			System.out.println("Frequency is "+frequency);
			sineFunctor.setPhase(phase);
		}

		@Override
		public Control getFunctorControl() {
		    return new SineFunctorControl(this);
		}
	},
	TRIANGULAR_WAVE(new TriangularFunctor(), "triangle") {
		@Override
		public void parseOptions(String optionsStr) {
			StringTokenizer tokens = new StringTokenizer(optionsStr, "/", false);
			if (tokens.countTokens() != 1)
				throw new RuntimeException(this.getFunctionName()
						+ " only supports options /<frequency> (" + optionsStr
						+ ")");
			String frequencyStr = tokens.nextToken();

			double frequency = 0;

			try {
				frequency = Double.parseDouble(frequencyStr);
			} catch (NumberFormatException e) {
				throw new RuntimeException(this.getFunctionName()
						+ " frequency option must be a valid number ("
						+ frequencyStr + ")");
			}
			TriangularFunctor triangularFunctor = (TriangularFunctor) this
					.getFunctor();
			triangularFunctor.setFrequency(frequency);
		}
		@Override
		public Control getFunctorControl() {
		    return new TriangularFunctorControl(this);
		}
	},
	PULSE(new PulseFunctor(), "pulse") {
		@Override
		public void parseOptions(String optionsStr) {
			StringTokenizer tokens = new StringTokenizer(optionsStr, "/", false);
			if (tokens.countTokens() != 2)
				throw new RuntimeException(
						this.getFunctionName()
								+ " only supports options /<frequency>/<pulseLengthPercent> ("
								+ optionsStr + ")");
			String frequencyStr = tokens.nextToken();
			String pulseLengthPercentStr = tokens.nextToken();

			double frequency = 0;
			double pulseLengthPercent = 0;
			try {
				frequency = Double.parseDouble(frequencyStr);
			} catch (NumberFormatException e) {
				throw new RuntimeException(this.getFunctionName()
						+ " frequency option must be a valid number ("
						+ frequencyStr + ")");
			}
			try {
				pulseLengthPercent = Double.parseDouble(pulseLengthPercentStr);
				if (pulseLengthPercent < 0. || pulseLengthPercent > 1.)
					throw new NumberFormatException();

			} catch (NumberFormatException e) {
				throw new RuntimeException(
						this.getFunctionName()
								+ " pulseLength option must be a valid number between 0 and 1 ("
								+ pulseLengthPercentStr + ")");
			}

			PulseFunctor pulseFunctor = (PulseFunctor) this.getFunctor();
			pulseFunctor.setFrequency(frequency);
			pulseFunctor.setPulseLengthPercent(pulseLengthPercent);

		}
		@Override
		public Control getFunctorControl() {
		    return new PulseFunctorControl(this);
		}

	},
	WHITE_NOISE(new WhiteNoiseFunctor(), "whitenoise") {
		@Override
		public void parseOptions(String optionsStr) {
			if (optionsStr != null && optionsStr.trim().length() > 0)
				throw new RuntimeException(this.getFunctionName()
						+ " does not support any options (" + optionsStr + ")");
		}
		@Override
		public Control getFunctorControl() {
		    return null;
		}

	},
	PINK_NOISE(new PinkNoiseFunctor(), "pinknoise") {
		@Override
		public void parseOptions(String optionsStr) {
			if (optionsStr != null && optionsStr.trim().length() > 0)
				throw new RuntimeException(this.getFunctionName()
						+ " does not support any options (" + optionsStr + ")");
		}
		@Override
		public Control getFunctorControl() {
		    return null;
		}

	};

	private Functor functor;
	private String functionName;

	public String getFunctionName() {
		return functionName;
	}

	private FunctorType(Functor functor, String functionName) {
		this.functor = functor;
		this.functionName = functionName;
	}

	public Functor getFunctor() {
		return functor;
	}

	public static FunctorType getByFunctionName(String functorTypeStr) {
		for (FunctorType functorType : values()) {
			if (functorType.functionName.equalsIgnoreCase(functorTypeStr)) {
				return functorType;
			}
		}
		return null;
	}

	public abstract void parseOptions(String optionsStr);

	public abstract Control getFunctorControl();
	
}
