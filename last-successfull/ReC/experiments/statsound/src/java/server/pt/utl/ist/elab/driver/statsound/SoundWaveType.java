package pt.utl.ist.elab.driver.statsound;

import com.linkare.rec.jmf.media.protocol.function.FunctorType;

/**
 * This enumeration defines the possible values for playing a wave.
 * 
 * @author Paulo Zenida - Linkare TI
 */
public enum SoundWaveType {

	SIN(FunctorType.SIN_WAVE),

	TRIANGULAR(FunctorType.TRIANGULAR_WAVE),

	WHITE_NOISE(FunctorType.WHITE_NOISE),

	PINK_NOISE(FunctorType.PINK_NOISE),

	PULSE(FunctorType.PULSE);

	
	
	private final FunctorType functorType;
	

	/**
	 * Creates the <code>SoundWaveType</code>.
	 */
	private SoundWaveType(final FunctorType functorType) {
		this.functorType=functorType;
	}
	
	
	/**
	 * @return the functorType
	 */
	public final FunctorType getFunctorType() {
		return functorType;
	}

	/**
	 * This method simply delegates to the
	 * <code>SoundWaveType.valueOf(String)</code> at the moment because the
	 * value is expected to be exactly the name of the enumeration type.
	 * However, if this needs to be changed later on, the client is already
	 * using a method containing a specific logic that may be customized.
	 * 
	 * @param value the value to match
	 * @return the SoundWaveType matching the passed in <code>value</code>.
	 * @see SoundWaveType#valueOf(String)
	 * @throws IllegalArgumentException when the <code>value</code> does not
	 *             find a match.
	 */
	public static SoundWaveType from(final String value) {
		final SoundWaveType type = SoundWaveType.valueOf(value);
		if (type != null) {
			return type;
		}
		throw new IllegalArgumentException("Could not find sound wave type for " + value);
	}
}