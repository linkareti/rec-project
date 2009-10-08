package com.linkare.rec.impl.newface.component;

import com.linkare.rec.impl.newface.ReCApplication;
import com.linkare.rec.impl.newface.component.media.events.MediaStoppedEvent;
import com.linkare.rec.impl.newface.component.media.events.MediaTimeChangedEvent;
import com.linkare.rec.impl.newface.component.media.transcoding.AudioCodecs;
import com.linkare.rec.impl.newface.component.media.transcoding.Muxers;
import com.linkare.rec.impl.newface.component.media.transcoding.TranscodingConfig;
import com.linkare.rec.impl.newface.component.media.transcoding.VideoCodecs;
import com.linkare.rec.impl.newface.component.media.VideoViewerController;
import com.linkare.rec.impl.newface.component.media.events.MediaTimeChangedEventListener;
import com.linkare.rec.impl.newface.component.media.MediaSetup;
import com.linkare.rec.impl.newface.component.media.events.MediaApplicationEventListener;
import java.awt.Window;
import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JTextField;
import org.jdesktop.application.Application;

/**
 * Classe de teste do módulo de vídeo.
 * 
 * @author bcatarino
 */
public class TestMediaPane extends AbstractContentPane {

    //Unix Local
    //    private static final String MRL = "/home/bcatarino/Documentos/NetBeansProjects/xpto.avi";
    //Windows Local
    //    private static final String MRL = "C:\\Development\\NetBeansProjects\\xpto.avi";
    //rtsp 
    private static final String MRL = "rtsp://elabmc.ist.utl.pt/radiare.sdp";

    VideoViewerController controller;

    private boolean attached = true;

    //    private String mrl;

    public static void main(final String[] args) {

	System.out.println("A listar system properties.... ");

	for (Map.Entry<Object, Object> map : System.getProperties().entrySet())
	    System.out.println(map.getKey() + " = " + map.getValue());

	MediaSetup.setup();

	Application.launch(MediaPaneVisualCheck.class, args);
    }

    public TestMediaPane() {
	this(null);
    }

    public TestMediaPane(Window container) {

	super(container);
	String[] arg = MediaSetup.getDefaultMediaParameters();
	controller = VideoViewerController.getInstance(arg);
	controller.setMediaToPlay(MRL);
	initComponents();
    }

    public void initComponents() {

	jLabel1 = new javax.swing.JLabel();
	vbox = new com.linkare.rec.impl.newface.component.media.VideoBox();
	jLabel1.setText("MediaPane");

	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
	this.setLayout(layout);
	layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		layout.createSequentialGroup().addContainerGap().addGroup(
			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel1)
				.addComponent(vbox, javax.swing.GroupLayout.PREFERRED_SIZE,
					javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
			.addContainerGap(50, Short.MAX_VALUE)));
	layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addPreferredGap(
			javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(vbox,
			javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
			javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(328, Short.MAX_VALUE)));

	this.add(vbox);
	controller.addMediaApplicationEventListener(new MediaApplicationEventListener() {

	    @Override
	    public void notConnected(MediaStoppedEvent evt) {
		slider.setValue(0);
	    }

	    @Override
	    public void stopped(MediaStoppedEvent evt) {
		slider.setValue(0);
	    }

	    @Override
	    public void timeChanged(MediaTimeChangedEvent evt) {
		adjustSlider();
	    }
	});
	this.add(playButton = getPlayButton());
	this.add(pauseButton = getPauseButton());
	this.add(stopButton = getStopButton());
	this.add(detachButton = getDetachButton());
	this.add(prevButton = getPrevButton());
	this.add(nextButton = getNextButton());
	this.add(captureButton = getButtonCapture());
	this.add(cb = getCheckBox());
	this.add(frame0Button = getButtonFrame0());
	this.add(gotoButton = getButtonGotoFrame());
	this.add(tf = getLinkText());
	this.add(openButton = getButtonOpen());
	this.add(slider = getSlider());
    }

    private JButton getPlayButton() {
	JButton button = new JButton();
	button.setVisible(true);
	button.setSize(80, 30);
	button.setLocation(40, 300);
	button.setText("Play");
	button.addActionListener(new java.awt.event.ActionListener() {
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent evt) {

		//                System.out.println("Vai tocar " + controller.getCurrentMedia());

		if (!controller.hasVideoOutput())
		    setVideoOutput();

		controller.play();
	    }
	});
	return button;
    }

    private JButton getPauseButton() {
	JButton button = new JButton();
	button.setVisible(true);
	button.setSize(80, 30);
	button.setLocation(140, 300);
	button.setText("Pause");
	button.addActionListener(new java.awt.event.ActionListener() {
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		controller.pause();
	    }
	});
	return button;
    }

    private JButton getStopButton() {
	JButton button = new JButton();
	button.setVisible(true);
	button.setSize(80, 30);
	button.setLocation(240, 300);
	button.setText("Stop");
	button.addActionListener(new java.awt.event.ActionListener() {
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		controller.stop();
	    }
	});
	return button;
    }

    private JButton getDetachButton() {
	JButton button = new JButton();
	button.setVisible(true);
	button.setSize(80, 30);
	button.setLocation(40, 350);
	button.setText("Dtch");
	button.addActionListener(new java.awt.event.ActionListener() {
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent evt) {

		if (attached) {
		    JFrame frame = new JFrame("Video");
		    frame.setSize(800, 600);
		    frame.setVisible(true);

		    com.linkare.rec.impl.newface.component.media.VideoBox newBox = new com.linkare.rec.impl.newface.component.media.VideoBox();
		    newBox.changeSize(800, 600);
		    //                    newBox.changeVideoOutputSize(800, 600);
		    newBox.setVisible(true);
		    newBox.getVideoOutput().setSize(800, 600);
		    frame.add(newBox);

		    controller.setVideoOutput(newBox.getVideoOutput());
		} else {
		    setVideoOutput();
		}
		attached = !attached;
	    }
	});
	return button;
    }

    private JButton getPrevButton() {
	JButton button = new JButton();
	button.setVisible(true);
	button.setSize(80, 30);
	button.setLocation(140, 350);
	button.setText("<<");
	button.addActionListener(new java.awt.event.ActionListener() {
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		controller.move(-1);
	    }
	});
	return button;
    }

    private JButton getNextButton() {
	JButton button = new JButton();
	button.setVisible(true);
	button.setSize(80, 30);
	button.setLocation(240, 350);
	button.setText(">>");
	button.addActionListener(new java.awt.event.ActionListener() {
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		controller.move(1);
	    }
	});
	return button;
    }

    private JButton getButtonCapture() {
	JButton button = new JButton();
	button.setVisible(true);
	button.setSize(80, 30);
	button.setLocation(40, 400);
	button.setText("prtsc");
	button.addActionListener(new java.awt.event.ActionListener() {
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		String formatedDate = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
		String userHome = System.getProperty("user.home") + File.separator + ".eLab" + File.separator
			+ "screeshots" + File.separator;
		controller.captureScreen(userHome + "Pic" + formatedDate + ".png", 800, 600);
	    }
	});
	return button;
    }

    private JCheckBox getCheckBox() {

	JCheckBox cb = new JCheckBox();
	cb.setText("Save to File");
	cb.setVisible(true);
	cb.setLocation(140, 400);
	cb.setSize(80, 30);
	cb.addActionListener(new java.awt.event.ActionListener() {
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		//TODO passar para o código principal?
		if (TestMediaPane.this.cb.isSelected()) {
		    TranscodingConfig config = new TranscodingConfig();
		    config.setMuxer(Muxers.MP4);
		    config.setVideoCodec(VideoCodecs.MP4V);
		    config.setVideoBitrate(800);
		    config.setVideoScale(1);
		    config.setAudioCodec(AudioCodecs.MPGA);
		    config.setAudioBitrate(128);
		    config.setSoundChannels(2);
		    controller.streamToFile(config, System.getProperty("user.home") + File.separator + ".eLab"
			    + File.separator + "savedExperiences" + File.separator + "myVideo.avi");
		} else
		    controller.stopStreaming();

	    }
	});
	return cb;
    }

    private JSlider getSlider() {

	final JSlider ts = new JSlider();
	ts.setMaximum(1000000);
	ts.setSize(300, 50);
	ts.setLocation(40, 440);
	ts.setEnabled(true);
	ts.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		controller.adjustVideoPosition(ts.getValue(), ts.getMaximum());
	    }
	});
	return ts;
    }

    private JButton getButtonFrame0() {
	JButton button = new JButton();
	button.setVisible(true);
	button.setSize(80, 30);
	button.setLocation(40, 530);
	button.setText("frm0");
	button.addActionListener(new java.awt.event.ActionListener() {
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 1970);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DAY_OF_MONTH, 1);
		controller.setFrame0(c.getTimeInMillis());
	    }
	});
	return button;
    }

    private JButton getButtonGotoFrame() {
	JButton button = new JButton();
	button.setVisible(true);
	button.setSize(80, 30);
	button.setLocation(140, 530);
	button.setText("goto");
	button.addActionListener(new java.awt.event.ActionListener() {
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 1970);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DAY_OF_MONTH, 1);
		// No eLab, ao carregar numa experiência, numa amostra da experiência, deve ir para o timestamp dessa amostra.
		controller.moveTo(c.getTimeInMillis());
	    }
	});
	return button;
    }

    private JTextField getLinkText() {
	JTextField field = new JTextField();
	field.setVisible(true);
	field.setSize(300, 30);
	field.setLocation(40, 480);
	field.setText("");
	return field;
    }

    private JButton getButtonOpen() {
	JButton button = new JButton();
	button.setVisible(true);
	button.setSize(80, 30);
	button.setLocation(260, 530);
	button.setText("Open");
	button.addActionListener(new java.awt.event.ActionListener() {
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		controller.releaseMedia();
		if (cb.isSelected()) {
		    TranscodingConfig config = new TranscodingConfig();
		    config.setMuxer(Muxers.MP4);
		    config.setVideoCodec(VideoCodecs.MP4V);
		    config.setVideoBitrate(800);
		    config.setVideoScale(1);
		    config.setAudioCodec(AudioCodecs.MPGA);
		    config.setAudioBitrate(128);
		    config.setSoundChannels(2);
		    controller.setMediaToPlay(tf.getText(), config, System.getProperty("user.home") + File.separator
			    + ".eLab" + File.separator + "savedExperiences" + File.separator + "myVideo.mp4");
		} else {
		    controller.setMediaToPlay(tf.getText());
		}
	    }
	});
	return button;
    }

    protected void setVideoOutput() {
	controller.setVideoOutput(vbox.getVideoOutput());
    }

    private void setMediaToPlay(String mrl) {
	controller.setMediaToPlay(mrl);
    }

    private void adjustSlider() {

	System.out.println("A aceder ao Marker " + Thread.currentThread());
	int max = slider.getMaximum();
	long current = controller.getCurrentMediaTime();
	long length = controller.getTotalMediaTime();
	if (length == 0)
	    length = 1;

	int val = (int) (max * (current) / length);

	slider.setValue(val);
    }

    private javax.swing.JLabel jLabel1;
    private com.linkare.rec.impl.newface.component.media.VideoBox vbox;
    private JButton playButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JButton detachButton;
    private JButton prevButton;
    private JButton nextButton;
    private JButton captureButton;
    private JButton frame0Button;
    private JButton gotoButton;
    private JButton openButton;
    private JTextField tf;
    private JCheckBox cb;
    private JSlider slider;

    /*
     * Construct the Application object. The following complications, relative to just calling
     * applicationClass.newInstance(), allow a privileged app to have a private static inner Application subclass.
     */
    private static class MediaPaneVisualCheck extends ReCApplication {

	public MediaPaneVisualCheck() {
	    // Required for AppFramework instatiation
	}

	@Override
	protected void showView() {
	    TestMediaPane tmp = new TestMediaPane();
	    DefaultDialog<TestMediaPane> dialog = new DefaultDialog<TestMediaPane>(tmp);
	    dialog.setVisible(true);
	    System.exit(0);
	}
    }
}
