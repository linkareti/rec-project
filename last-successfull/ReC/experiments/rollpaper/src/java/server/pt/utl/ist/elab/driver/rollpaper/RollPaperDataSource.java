/*
 * RollPaperDataProducer.java
 *
 * Created on 27 de Fevereiro de 2005, 12:13 PM
 */

package pt.utl.ist.elab.driver.rollpaper;

import java.util.logging.Logger;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.driver.BaseDataSource;
import com.linkare.rec.impl.logging.LoggerUtil;

/**
 * 
 * @author Bruno Catarino - Linkare TI
 */
public class RollPaperDataSource extends BaseDataSource {

    private final int NUM_CHANNELS = 4;

    private int tbs;
    private final int nSamples;

    private RollPaperDriver driver = null;
    private RollpaperHardwareSimulator samplesGenerator;
    private boolean stopped = false;

    /**
     * Creates the <code>RollPaperDataProducer</code>.
     * 
     * @param rollPaperDriver
     *            A reference to the driver connected to this DataProducer.
     * @param baseHeight1
     *            The initial height of the first paper roll (grabed by tip).
     * @param baseHeight2
     *            The initial height of the second paper roll (freefall).
     * @param g
     *            The gravity acceleration.
     * @param totalRadius
     *            The radius of the paper roll.
     * @param innerRadius
     *            The radius of the paper roll's hole.
     * @param tbs
     * @param nSamples
     */
    public RollPaperDataSource(RollPaperDriver rollPaperDriver, float baseHeight1, float baseHeight2, float g, float totalRadius, float innerRadius, int tbs,
	    int nSamples) {
	this.driver = rollPaperDriver;
	this.nSamples = nSamples;
	this.tbs = tbs;

	samplesGenerator = new RollpaperHardwareSimulator(NUM_CHANNELS, baseHeight1, baseHeight2, g, totalRadius, innerRadius, getAcquisitionHeader());
    }

    public void startProduction() {
	stopped = false;
	new ProducerThread().start();
    }

    private class ProducerThread extends Thread {
	private int currentSample = 0;

	@Override
	public void run() {
	    try {
		Thread.sleep(1000);

		PhysicsValue[] value;
		int counter = 0;

		while (currentSample < nSamples && !stopped) {

		    if (counter % tbs == 0) {
			value = samplesGenerator.getNewSample(1 / tbs * currentSample);
			addDataRow(value);
			Thread.sleep(tbs);
			currentSample++;
		    }
		    counter++;
		}

		join(100);
		endProduction();

		driver.stopVirtualHardware();
	    } catch (final InterruptedException ie) {
		LoggerUtil.logThrowable("Error in Thread: ", ie, Logger.getLogger(RollPaperDriver.ROLLPAPER_DRIVER_LOGGER));
	    }
	}
    }

    @Override
    public void stopNow() {
	stopped = true;
	setDataSourceStoped();
    }

    private void endProduction() {
	stopped = true;
	setDataSourceEnded();
    }
}
