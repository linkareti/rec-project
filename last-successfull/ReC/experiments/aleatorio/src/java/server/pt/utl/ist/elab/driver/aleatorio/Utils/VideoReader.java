/*
 * videoReader.java
 *
 * Created on 22 October 2003, 12:23
 */

package pt.utl.ist.elab.driver.aleatorio.Utils;

import javax.media.Buffer;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;

import pt.utl.ist.elab.driver.aleatorio.AleatorioDataSource;
import pt.utl.ist.elab.driver.aleatorio.Hardware.PrioritazibleBuffer;
import pt.utl.ist.elab.driver.aleatorio.Hardware.WebCamThread;

/**
 * 
 * @author Pedro Carvalho - LEFT - IST
 */
public class VideoReader extends Thread {

	private final com.linkare.rec.impl.utils.EventQueue queue = new com.linkare.rec.impl.utils.EventQueue(
			new VideoBufferDispatcher(), this.getClass().getSimpleName());
	private final int totalFrames;
	private int frameCounter, bufferCounter;
	private WebCamThread webcam = null;
	private AleatorioDataSource aDS = null;
	public boolean startedFilming = false;
	public java.awt.Image lastFrame = null;
	private int frameWait = 0;

	/**
	 * Creates a new instance of VideoReader
	 * 
	 * @param aDS
	 * @param webcam
	 * @param totalFrames
	 * @param frameRate
	 */
	public VideoReader(final AleatorioDataSource aDS, final WebCamThread webcam, final int totalFrames,
			final int frameRate) {
		this.webcam = webcam;
		this.aDS = aDS;
		this.totalFrames = totalFrames;
		frameCounter = 0;
		bufferCounter = 0;
		frameWait = 1000 / frameRate;
	}

	@Override
	public void run() {
		startedFilming = true;
		// System.out.println("Starting the recording process!");
		Buffer buffer;
		for (frameCounter = 0; frameCounter < totalFrames; frameCounter++) {
			buffer = (Buffer) (webcam.grabFrameBuffer().clone());
			queue.addEvent(new PrioritazibleBuffer(buffer));
			// queue.addEvent(webcam.getImage());
			try {
				Thread.sleep(frameWait);
			} catch (final InterruptedException e) {
			}
		}// for_i
			// System.out.println("Last frame reached!");

		buffer = webcam.grabFrameBuffer();
		lastFrame = new BufferToImage((VideoFormat) buffer.getFormat()).createImage(buffer);
	}// start

	public int getFrameCounter() {
		return frameCounter;
	}// getFrameCounter

	/**
	 * Inner class VideoBufferDispatcher
	 */
	private class VideoBufferDispatcher implements com.linkare.rec.impl.utils.EventQueueDispatcher {

		@Override
		public void dispatchEvent(final Object evt) {

			if (evt instanceof PrioritazibleBuffer) {
				final Buffer readBuffer = ((PrioritazibleBuffer) evt).getBuffer();

				if (readBuffer.getFormat() instanceof VideoFormat) {
					// System.out.println("This is a videoFormat buffer!");

					if (bufferCounter < totalFrames) {
						// System.out.println("Image "+bufferCounter +" => "
						// +readBuffer.toString());
						final java.awt.Image image = (new BufferToImage((VideoFormat) readBuffer.getFormat()))
								.createImage(readBuffer);

						final java.awt.MediaTracker tracker = new java.awt.MediaTracker(new javax.swing.JPanel());
						tracker.addImage(image, 0);
						try {
							tracker.waitForAll();
						} catch (final InterruptedException e) {
							e.printStackTrace();
							System.exit(1);
						}

						image.getGraphics().setColor(java.awt.Color.white);
						image.getGraphics().drawString(
								String.valueOf(System.currentTimeMillis()) + String.valueOf(frameCounter), 0, 0);
						/*
						 * javax.swing.JFrame imagemDialog = new
						 * javax.swing.JFrame();
						 * imagemDialog.setTitle("Imagem "+bufferCounter);
						 * ImageStorePanel imagemCapturadaPanel = new
						 * ImageStorePanel(image);
						 * imagemDialog.getContentPane().
						 * add(imagemCapturadaPanel); int[] dimIm =
						 * imagemCapturadaPanel.imageSize();
						 * imagemDialog.setSize(dimIm[0]+8, dimIm[1]+27);
						 * //imagemDialog.pack();
						 * imagemDialog.setDefaultCloseOperation
						 * (javax.swing.JFrame.DISPOSE_ON_CLOSE);
						 * imagemDialog.show(); imagemDialog.repaint();
						 */
						try {
							aDS.sendMovieFrame(image);
						} catch (final NullPointerException e) {
							e.printStackTrace();
						}

						bufferCounter++;
						System.out.println("Sent frame " + bufferCounter + "!");
					}// if
					else {
						System.out.println("Sent " + bufferCounter + " frames!");
						// /if (readBuffer != null) {
						// lastFrame = new
						// BufferToImage((VideoFormat)readBuffer.getFormat()).createImage(readBuffer);
						// }
						// lastFrame = image;
					}// else
				}// if
			} else {
				System.out.println("Buffered Event can not be handled by this dispatcher... EventClass="
						+ (evt == null ? "NULL" : evt.getClass().getName()));
			}
		}

		@Override
		public int getPriority() {
			return Thread.MAX_PRIORITY - 2;

		}

	}// VideoBufferDispatcher

}
