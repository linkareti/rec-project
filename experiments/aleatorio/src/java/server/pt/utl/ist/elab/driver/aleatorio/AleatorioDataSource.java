/*
 * AleatorioDataSource.java
 *
 * Created on 12 de Junho de 2003, 14:43
 */

package pt.utl.ist.elab.driver.aleatorio;

import java.awt.Graphics2D;
import java.awt.Image;

import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValFactory;
import com.linkare.rec.impl.driver.BaseDataSource;

/**
 * 
 * @author Pedro Carvalho - LEFT - IST
 */
public class AleatorioDataSource extends BaseDataSource {

	/** REC */
	private PhysicsValue[] values = null;
	private boolean firstImage = true;

	AleatorioDriver aleatorioDriver = null;
	public pt.utl.ist.elab.driver.aleatorio.Utils.FileOps file = null;
	public int[] statisticX = null;
	public int[] statisticY = null;

	/**
	 * channel 0 ==> image; channel 1 ==> [minX, maxX]; channel 2 ==> [y1....yN]
	 * channel 3 ==> movie; channel 4 ==> [centersX][centersY]; channel 5 ==>
	 * [configuration values]
	 */
	private static final int NUM_CHANNELS = 6;

	/**
	 * Creates a new instance of AleatorioDataSource
	 * 
	 * @param aleatorioDriver
	 */
	public AleatorioDataSource(final AleatorioDriver aleatorioDriver) {
		this.aleatorioDriver = aleatorioDriver;
	}

	/*
	 * public void dataSource() { values = new PhysicsValue[NUM_CHANNELS]; //the
	 * image to be sent thru the web values[0] = new
	 * PhysicsValue(PhysicsValFactory
	 * .fromByteArray(image2ByteArray(this.aleatorioDriver.webcam.getImage()),
	 * "image/jpeg"), //mime-Type null, //Error
	 * com.linkare.rec.data.Multiplier.none); //multiplier
	 * 
	 * int[] xxFirstLast = new int[2]; int numDice = 0; int minValueOfDie = 0;
	 * int maxValueOfDie = 0; try{numDice = new
	 * Integer(aleatorioDriver.getProps(
	 * ).getProperty("numberOfDice")).intValue();} catch(NumberFormatException
	 * nfe){} try{minValueOfDie = new
	 * Integer(aleatorioDriver.getProps().getProperty
	 * ("minValueOfDie")).intValue();} catch(NumberFormatException nfe){}
	 * try{maxValueOfDie = new
	 * Integer(aleatorioDriver.getProps().getProperty("maxValueOfDie"
	 * )).intValue();} catch(NumberFormatException nfe){}
	 * 
	 * int[] newFileProperties = new int[3]; newFileProperties[0] = numDice;
	 * newFileProperties[1] = minValueOfDie; newFileProperties[2] =
	 * maxValueOfDie; file = new
	 * pt.utl.ist.elab.driver.aleatorio.Utils.FileOps("utils/AcuStat" + numDice
	 * + ".dat", newFileProperties);
	 * 
	 * statisticX = file.getIDataX(); statisticY = file.getIDataY();
	 * 
	 * xxFirstLast[0] = statisticX[0]; xxFirstLast[1] =
	 * statisticX[statisticX.length-1]; //the xx values for the statistical
	 * graph values[1] = new
	 * PhysicsValue(PhysicsValFactory.fromByteArray(int2ByteArray(xxFirstLast),
	 * "array/int"), null, com.linkare.rec.data.Multiplier.none); //the yy
	 * values for the statistical graph values[2] = new
	 * PhysicsValue(PhysicsValFactory.fromByteArray(int2ByteArray(statisticY),
	 * "array/int"), null, com.linkare.rec.data.Multiplier.none); //isto n�o tem
	 * nada a ver, mas olhei agora para isto e parece-me extremamente idiota...
	 * //the movie file //values[3]=.....
	 * 
	 * //the centers coordinates values[4] = new
	 * PhysicsValue(PhysicsValFactory.fromByteArray
	 * (int2ByteArray(this.aleatorioDriver.getCenters()), "array/int2d"), null,
	 * com.linkare.rec.data.Multiplier.none);
	 * 
	 * 
	 * //the configuration information of the experiment //BWThreshold, radius,
	 * houghThreshold1, houghThreshold2, convolutionThreshold int[]
	 * configurationIntArray = new int[6]; configurationIntArray[0] =
	 * Integer.valueOf
	 * (aleatorioDriver.getProps().getProperty("BWThreshold")).intValue();
	 * configurationIntArray[1] =
	 * Integer.valueOf(aleatorioDriver.getProps().getProperty
	 * ("radiusOfSpot")).intValue(); configurationIntArray[2] =
	 * Integer.valueOf(aleatorioDriver
	 * .getProps().getProperty("houghThreshold1")).intValue();
	 * configurationIntArray[3] =
	 * Integer.valueOf(aleatorioDriver.getProps().getProperty
	 * ("houghThreshold2")).intValue(); configurationIntArray[4] =
	 * Integer.valueOf
	 * (aleatorioDriver.getProps().getProperty("convThreshold")).intValue();
	 * configurationIntArray[5] =
	 * Integer.valueOf(aleatorioDriver.getProps().getProperty
	 * ("maxWidthOfDie")).intValue(); //configurationIntArray[6] =
	 * Integer.valueOf
	 * (aleatorioDriver.getProps().getProperty("minValueOfDie")).intValue();
	 * //configurationIntArray[7] =
	 * Integer.valueOf(aleatorioDriver.getProps().getProperty
	 * ("maxValueOfDie")).intValue(); //configurationIntArray[8] =
	 * Integer.valueOf
	 * (aleatorioDriver.getProps().getProperty("numberOfDice")).intValue();
	 * values[5] = new
	 * PhysicsValue(PhysicsValFactory.fromByteArray(int2ByteArray
	 * (configurationIntArray), "array/int"), null,
	 * com.linkare.rec.data.Multiplier.none);
	 * 
	 * super.addDataRow(values); }
	 */

	public void sendImage(final java.awt.Image image, final int centerCounter) {
		// the image to be sent thru the web
		values = new PhysicsValue[AleatorioDataSource.NUM_CHANNELS];
		if (firstImage) {
			fillStatistics();
			fillConfig();
			fillCenterCounter(centerCounter);
			// sendStatistics();
			// sendConfig();
		}// if

		final byte[] baImage = image2ByteArray(image);
		values[0] = new PhysicsValue(PhysicsValFactory.fromByteArray(baImage, "image/jpeg"), // mime-Type
				null, // Error
				com.linkare.rec.data.Multiplier.none); // multiplier
		super.addDataRow(values);

		if (firstImage) {
			firstImage = false;
		}
	}

	public void fillStatistics()// sendStatistics()
	{
		// values = new PhysicsValue[NUM_CHANNELS];
		final int[] xxFirstLast = new int[2];
		int numDice = 0;
		int minValueOfDie = 0;
		int maxValueOfDie = 0;

		// System.out.println(">> getting props!");

		try {
			numDice = Integer.valueOf(aleatorioDriver.getProps().getProperty("numberOfDice")).intValue();
		} catch (final NumberFormatException nfe) {
		}
		try {
			minValueOfDie = Integer.valueOf(aleatorioDriver.getProps().getProperty("minValueOfDie")).intValue();
		} catch (final NumberFormatException nfe) {
		}
		try {
			maxValueOfDie = Integer.valueOf(aleatorioDriver.getProps().getProperty("maxValueOfDie")).intValue();
		} catch (final NumberFormatException nfe) {
		}

		final int[] newFileProperties = new int[3];
		newFileProperties[0] = numDice;
		newFileProperties[1] = minValueOfDie;
		newFileProperties[2] = maxValueOfDie;
		// System.out.println(">> opening file!");
		file = new pt.utl.ist.elab.driver.aleatorio.Utils.FileOps("AcuStat" + numDice + ".dat", newFileProperties);

		statisticX = file.getIDataX();
		statisticY = file.getIDataY();

		xxFirstLast[0] = statisticX[0];
		xxFirstLast[1] = statisticX[statisticX.length - 1];
		// System.out.println(">>Adding values");
		// the xx values for the statistical graph
		// System.out.println(">>Adding physicsValue with:"+xxFirstLast);
		// System.out.println(">>Byte Array => "+int2ByteArray(xxFirstLast));

		values[1] = new PhysicsValue(PhysicsValFactory.fromByteArray(int2ByteArray(xxFirstLast), "array/int"), null,
				com.linkare.rec.data.Multiplier.none);
		// System.out.println(">>Resulting PhysicsValue:" +
		// values[1].getValue().getByteArrayValue());
		// the yy values for the statistical graph
		// System.out.println(">>Adding physicsValue with:"+statisticY);
		// System.out.println(">>Byte Array => "+int2ByteArray(statisticY));
		values[2] = new PhysicsValue(PhysicsValFactory.fromByteArray(int2ByteArray(statisticY), "array/int"), null,
				com.linkare.rec.data.Multiplier.none);
		// System.out.println(">>Resulting PhysicsValue:" +
		// values[2].getValue().getByteArrayValue());
		// System.out.println(">>Adding values to dataRow");

		// super.addDataRow(values);
		// System.out.println(">>Values added to dataRow");
	}

	public void sendMovieFrame(final java.awt.Image image) {
		if (image != null) {
			values = new PhysicsValue[AleatorioDataSource.NUM_CHANNELS];
			final byte[] dataImage = image2ByteArray(image);
			final PhysicsVal val = PhysicsValFactory.fromByteArray(dataImage, "image/jpeg");
			values[3] = new PhysicsValue(val, null, com.linkare.rec.data.Multiplier.none);

			super.addDataRow(values);
		}
	}// movieDataSource(javax.imageio.stream.ImageOutputStream ios)

	/*
	 * public void sendCenters(int[][] centers) { values = new
	 * PhysicsValue[NUM_CHANNELS]; //the centers coordinates values[4] = new
	 * PhysicsValue
	 * (PhysicsValFactory.fromByteArray(int2ByteArray(centers),"array/int2d"),
	 * null, com.linkare.rec.data.Multiplier.none); super.addDataRow(values); }
	 */

	public void fillConfig()// sendConfig()
	{
		// estes deviam estar metidos na HardwareCOnfig... como name=value
		// parameters.. at� � mais f�cil de fazer...
		// values = new PhysicsValue[NUM_CHANNELS];
		// the configuration information of the experiment
		// BWThreshold, radius, houghThreshold1, houghThreshold2,
		// convolutionThreshold
		final int[] configurationIntArray = new int[8];
		// isto tamb�m � idiota... podia import como props da HardwareConfig -
		// HardwareParameters (alias para ChannelParameters...)
		configurationIntArray[0] = Integer.valueOf(aleatorioDriver.getProps().getProperty("BWThreshold")).intValue();
		configurationIntArray[1] = Integer.valueOf(aleatorioDriver.getProps().getProperty("radiusOfSpot")).intValue();
		configurationIntArray[2] = Integer.valueOf(aleatorioDriver.getProps().getProperty("houghThreshold1"))
				.intValue();
		configurationIntArray[3] = Integer.valueOf(aleatorioDriver.getProps().getProperty("houghThreshold2"))
				.intValue();
		configurationIntArray[4] = Integer.valueOf(aleatorioDriver.getProps().getProperty("houghThreshold3"))
				.intValue();
		configurationIntArray[5] = Integer.valueOf(aleatorioDriver.getProps().getProperty("convThreshold")).intValue();
		configurationIntArray[6] = Integer.valueOf(aleatorioDriver.getProps().getProperty("maxWidthOfDie")).intValue();
		configurationIntArray[7] = Integer.valueOf(aleatorioDriver.getProps().getProperty("numberOfDice")).intValue();
		// configurationIntArray[6] =
		// Integer.valueOf(aleatorioDriver.getProps().getProperty("minValueOfDie")).intValue();
		// configurationIntArray[7] =
		// Integer.valueOf(aleatorioDriver.getProps().getProperty("maxValueOfDie")).intValue();
		// configurationIntArray[8] =
		// Integer.valueOf(aleatorioDriver.getProps().getProperty("numberOfDice")).intValue();
		values[5] = new PhysicsValue(
				PhysicsValFactory.fromByteArray(int2ByteArray(configurationIntArray), "array/int"), null,
				com.linkare.rec.data.Multiplier.none);
		// super.addDataRow(values);
	}

	public void fillCenterCounter(final int centerCounter)// sendCenterCounter(int
	// centerCounter)
	{
		// values = new PhysicsValue[NUM_CHANNELS];
		values[4] = new PhysicsValue(PhysicsValFactory.fromInt(centerCounter), null,
				com.linkare.rec.data.Multiplier.none);
		// super.addDataRow(values);
	}// sendCenterCounter

	/*
	 * public void sendImageCenters(java.awt.Image image, int[][] centers) {
	 * values = new PhysicsValue[NUM_CHANNELS]; //the image to be sent thru the
	 * web values[0] = new
	 * PhysicsValue(PhysicsValFactory.fromByteArray(image2ByteArray(image),
	 * "image/jpeg"), //mime-Type null, //Error
	 * com.linkare.rec.data.Multiplier.none); //multiplier //the centers
	 * coordinates values[4] = new
	 * PhysicsValue(PhysicsValFactory.fromByteArray(int2ByteArray
	 * (centers),"array/int2d"), null, com.linkare.rec.data.Multiplier.none);
	 * 
	 * super.addDataRow(values); }
	 */
	// sendImageCenters

	private byte[] image2ByteArray(final Image image) {
		final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
		final javax.imageio.stream.MemoryCacheImageOutputStream mcios = new javax.imageio.stream.MemoryCacheImageOutputStream(
				baos);
		if (image == null) {
			return null;
		}
		// convert Image to BufferedImage
		// System.out.println("converting to JPEG!");
		// System.out.println("Image is: "+image);
		// System.out.println("Image.width:"+image.getWidth(null)+
		// ", image.height:"+image.getHeight(null));
		final int imageWidth = image.getWidth(null), imageHeight = image.getHeight(null);
		final java.awt.image.BufferedImage bImage = new java.awt.image.BufferedImage(imageWidth, imageHeight,
				java.awt.image.BufferedImage.TYPE_INT_RGB);
		final Graphics2D g2d = bImage.createGraphics();
		// javax.swing.JPanel painel = new javax.swing.JPanel();
		while (!g2d.drawImage(image, 0, 0, imageWidth, imageHeight, null)) {
		}// wait for the full image to be available
		/*
		 * javax.swing.JFrame jf = new javax.swing.JFrame("Test");
		 * jf.getContentPane().add(painel, java.awt.BorderLayout.CENTER);
		 * jf.pack(); jf.show();
		 */
		// write BufferedImage to ByteArrayOutputStream in jpg!
		try {
			javax.imageio.ImageIO.write(bImage, "jpg", mcios);
		} catch (final java.io.IOException e) {
		}
		/*
		 * try {javax.imageio.ImageIO.write(bImage, "jpg", new
		 * java.io.File("c:\\teste01.jpg"));} catch(java.io.IOException e){}
		 * catch(com.sun.image.codec.jpeg.ImageFormatException e){}
		 */

		// create the ByteArrayInputStream and get the Byte[]
		final java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(baos.toByteArray());
		final byte[] byteArray = new byte[bais.available()];

		try {
			bais.read(byteArray);
		} catch (final java.io.IOException e) {
		}

		return byteArray;
	}// image2ByteArray(Image image)

	private byte[] int2ByteArray(final int[] intArray) {
		// each int takes 3 bytes
		final byte[] byteArray = new byte[intArray.length * 4];

		for (int index = 0; index < intArray.length; index++) {
			byteArray[4 * index] = (byte) ((intArray[index] & 0xff000000) >> 24);
			byteArray[4 * index + 1] = (byte) ((intArray[index] & 0x00ff0000) >> 16);
			byteArray[4 * index + 2] = (byte) ((intArray[index] & 0x0000ff00) >> 8);
			byteArray[4 * index + 3] = (byte) (intArray[index] & 0x000000ff);
		}
		return byteArray;
	}// int2ByteArray(int[] intArray)

	public void updateStatisticsFile(final int pos, final int value) {
		file.updateFile(pos, value);

	}// updateStatisticsFile(int pos, int value)

	public void sessionStatisticsFile(final int[] centers, final int numberOfDice) {
		final java.util.Date today = new java.util.Date();
		final java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy_MM_dd-HH'h'mm'm'ss");
		final String pathName = "data/ReC_Aleatorio_" + formatter.format(today) + ".dat";

		final java.io.File newFile = new java.io.File(pathName);
		// try{newFile.createNewFile();}
		// catch(java.io.IOException e){e.printStackTrace();}
		java.io.FileOutputStream fo = null;
		try {
			fo = new java.io.FileOutputStream(newFile, false);
			fo.write(getAcquisitionHeader().toString().getBytes());
		}// try
		catch (final java.io.FileNotFoundException e) {
			e.printStackTrace();
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		}
		byte[] bytesToWrite = "Counts\n".getBytes();

		try {
			fo.write(bytesToWrite);
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		}

		for (final int center : centers) {
			bytesToWrite = (String.valueOf(center) + "\n").getBytes();
			try {
				fo.write(bytesToWrite);
			} catch (final java.io.IOException e) {
				e.printStackTrace();
			}
		}// for_I
		try {
			fo.close();
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		}

	}// sessionStatisticsFile

	@Override
	public void setAcquisitionHeader(final HardwareAcquisitionConfig config) {
		super.setAcquisitionHeader(config);

	}// setAcquisitionHeader

	public void endAcquisition() {
		setDataSourceEnded();
		// fireIDataSourceListenerAcquisitionEnded();
	}

	@Override
	public void stopNow() {
		setDataSourceStoped();
	}

}
