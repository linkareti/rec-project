package pt.utl.ist.elab.client.statsound;

/**
 * This represents the possible different protocols that one may execute in the
 * statsound experiment. Notice that the <code>value</code> must map with the
 * name of the experiment that comes from the GUI, in the customizer.
 * 
 * @see pt.utl.ist.elab.client.statsound.StatSoundCustomizer
 * 
 * @author Paulo Zenida - Linkare TI
 */
public enum TypeOfExperiment {

	STATSOUND_VARY_PISTON("Vary Piston"),

	STATSOUND_VARY_FREQUENCY("Vary Freq"),

	SOUND_VELOCITY("Sound Vel");

	private final String value;

	private TypeOfExperiment(final String name) {
		this.value = name;
	}

	/**
	 * @return the name
	 */
	public String getValue() {
		return value;
	}

	public static TypeOfExperiment from(final String value) {
		if (STATSOUND_VARY_PISTON.getValue().equalsIgnoreCase(value)) {
			return STATSOUND_VARY_PISTON;
		} else if (STATSOUND_VARY_FREQUENCY.getValue().equalsIgnoreCase(value)) {
			return STATSOUND_VARY_FREQUENCY;
		} else if (SOUND_VELOCITY.getValue().equalsIgnoreCase(value)) {
			return SOUND_VELOCITY;
		}
		throw new IllegalArgumentException("Could not find type of experiment for " + value);
	}
}