package pt.utl.ist.elab.driver.rollpaper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValFactory;

/**
 * Makes all the necessary calculations to generate one single sample. It acts like if it were the hardware from which the data source is receiving data.
 * 
 * @author Bruno Catarino - Linkare TI
 */
public class RollpaperHardwareSimulator {

    private final int numChannels;
    private final double baseHeight1;
    private final double baseHeight2;
    private final double gravityAcceleration;
    private final double totalRadius;
    private final double innerRadius;
    private final HardwareAcquisitionConfig hardwareConfig;

    private double rollAcceleration;

    public RollpaperHardwareSimulator(int numChannels, double baseHeight1, double baseHeight2, double g, double totalRadius, double innerRadius,
	    HardwareAcquisitionConfig hardwareConfig) {

	this.numChannels = numChannels;
	this.baseHeight1 = baseHeight1;
	this.baseHeight2 = baseHeight2;
	this.gravityAcceleration = g;
	this.totalRadius = totalRadius;
	this.innerRadius = innerRadius;
	this.hardwareConfig = hardwareConfig;

	this.rollAcceleration = getRollAcceleration().doubleValue();
    }

    /**
     * Creates a new sample with all {@link PhysicsValue} that represent a sample.
     * 
     * @param time
     *            The time that passed since the beginning of the experiment. The sample will be calculated according to that time.
     * @return
     */
    public PhysicsValue[] getNewSample(double time) {

	PhysicsValue[] value = null;

	double heightHeld = getCurrentHeight(baseHeight1, time, rollAcceleration).doubleValue();
	double heightFreefall = getCurrentHeight(baseHeight2, time, gravityAcceleration).doubleValue();
	byte[] snapshotHeld = getSnapshot();
	byte[] snapshotFreeFall = getSnapshot();

	if (snapshotHeld == null && snapshotFreeFall == null) {
	    value = new PhysicsValue[numChannels - 2];
	} else if (snapshotFreeFall == null) {
	    value = new PhysicsValue[numChannels - 1];
	} else {
	    value = new PhysicsValue[numChannels];
	}

	value[0] = new PhysicsValue(PhysicsValFactory.fromDouble(heightHeld), hardwareConfig.getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
				    hardwareConfig.getChannelsConfig(0).getSelectedScale().getMultiplier());
	value[1] = new PhysicsValue(PhysicsValFactory.fromDouble(heightFreefall),
				    hardwareConfig.getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(), hardwareConfig.getChannelsConfig(1)
																 .getSelectedScale()
																 .getMultiplier());

	if (value.length > 1) {
	    value[2] = snapshotHeld != null ? new PhysicsValue(PhysicsValFactory.fromByteArray(snapshotHeld, "image/gif"),
							       hardwareConfig.getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(),
							       hardwareConfig.getChannelsConfig(2).getSelectedScale().getMultiplier()) : null;
	}

	if (value.length > 2) {
	    value[3] = new PhysicsValue(PhysicsValFactory.fromByteArray(snapshotFreeFall, "image/gif"), hardwareConfig.getChannelsConfig(3).getSelectedScale()
														      .getDefaultErrorValue(),
					hardwareConfig.getChannelsConfig(3).getSelectedScale().getMultiplier());
	}

	return value;
    }

    /**
     * Gets a snapshot of the falling paper rolls for a given moment. It's only a simulation.
     * 
     * @return
     */
    private byte[] getSnapshot() {

	if (Math.random() < 0.3) {
	    return null;
	}

	try {
	    InputStream is = this.getClass().getClassLoader().getResourceAsStream("pt/utl/ist/elab/driver/rollpaper/resources/test.gif");
	    byte[] buffer = new byte[1024];
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    while (is.read(buffer) > 0) {
		baos.write(buffer);
	    }
	    return baos.toByteArray();
	} catch (IOException e) {
	    return null;
	}
    }

    /**
     * Returns the current height, based on the base height and the distance traveled since the time 0.
     * 
     * @param baseHeight
     * @param time
     * @param acceleration
     * @return
     */
    private BigDecimal getCurrentHeight(double baseHeight, double time, double acceleration) {
	return new BigDecimal(baseHeight).subtract(getDistance(time, acceleration));
    }

    /**
     * Determines the distance traveled by the paper roll after <code>time</code> units of time, also depending of the given acceleration.
     * 
     * @param acceleration
     * @param time
     * @return
     */
    private BigDecimal getDistance(double time, double acceleration) {
	return new BigDecimal(acceleration).multiply(new BigDecimal(time).pow(2)).divide(new BigDecimal(2));
    }

    /**
     * Determines the acceleration for the paper roll that's being held by the tip, according to
     * <code>a = 2 * {@link #gravityAcceleration} * Math.pow({@link #totalRadius}, 2) / (3 * Math.pow({@link #totalRadius}, 2) + Math.pow({@link #innerRadius}, 2)</code>
     * 
     * @return
     */
    private BigDecimal getRollAcceleration() {
	BigDecimal numerator = new BigDecimal(2).multiply(new BigDecimal(gravityAcceleration)).multiply(new BigDecimal(totalRadius).pow(2));
	BigDecimal denominator = new BigDecimal(3).multiply(new BigDecimal(totalRadius).pow(2)).add(new BigDecimal(innerRadius).pow(2));
	return numerator.divide(denominator);
    }
}