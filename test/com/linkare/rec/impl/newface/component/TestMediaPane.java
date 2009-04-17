package com.linkare.rec.impl.newface.component;

import com.linkare.rec.impl.newface.UserInterfaceTest;
import com.linkare.rec.impl.newface.component.media.AudioCodecs;
import com.linkare.rec.impl.newface.component.media.Muxers;
import com.linkare.rec.impl.newface.component.media.TranscodingConfig;
import com.linkare.rec.impl.newface.component.media.VideoViewerController;
import com.linkare.rec.impl.newface.component.media.VideoBox;
import com.linkare.rec.impl.newface.component.media.VideoCodecs;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author bcatarino
 */
public class TestMediaPane extends UserInterfaceTest {

    private static final String MRL = "/home/bcatarino/Documentos/NetBeansProjects/xpto.avi";
    
    VideoViewerController controller = VideoViewerController.getInstance();
    VideoBox vbox;

    JTextField tf;

    int screenNumber = 0;

    public static void main(String[] args) {
		new TestMediaPane().run();
	}

    public void setComponents(final MediaPane mp) {
        mp.add(getPlayButton());
        mp.add(getPauseButton());
        mp.add(getStopButton());
        mp.add(getDetachButton());
        mp.add(getPrevButton());
        mp.add(getNextButton());
        mp.add(getButtonCapture());
        mp.add(getButtonSave());
        mp.add(getButton3());
        mp.add(tf = getLinkText());
        mp.add(getButtonOpen());
    }

    private TestSlider getMySlider() {

        TestSlider ts = new TestSlider();
        ts.setMaximum(1000000);
        ts.setSize(300, 50);
        ts.setLocation(40, 440);
        ts.setEnabled(true);
        ts.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                controller.adjustVideoPosition();
            }
        });
        return ts;
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
                
                JFrame frame = new JFrame("Video");
                frame.setSize(800, 600);
                frame.setVisible(true);

                VideoBox newBox = new VideoBox();
                newBox.changeSize(800, 600);
                newBox.setVisible(true);
                frame.add(newBox);
                
                controller.setVideoOutput(newBox.getVideoOutput());
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
                controller.captureScreen("Image" + screenNumber++ + ".jpeg", 800, 600);
            }
        });
        return button;
    }

    private JButton getButtonSave() {
        JButton button = new JButton();
        button.setVisible(true);
        button.setSize(80, 30);
        button.setLocation(140, 400);
        button.setText("save");
        button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TranscodingConfig config = new TranscodingConfig();
                config.setMuxer(Muxers.MP4);
                config.setVideoCodec(VideoCodecs.MP4V);
                config.setVideoBitrate(800);
                config.setVideoScale(1);
                config.setAudioCodec(AudioCodecs.MPGA);
                config.setAudioBitrate(128);
                config.setSoundChannels(2);
                controller.streamToFile(config, "myVideo.avi");
            }
        });
        return button;
    }

    private JButton getButton3() {
        JButton button = new JButton();
        button.setVisible(true);
        button.setSize(80, 30);
        button.setLocation(240, 400);
        button.setText(">>");
        button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                controller.move(1);
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
//        field.addActionListener(new java.awt.event.ActionListener() {
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                controller.move(1);
//            }
//        });
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
                controller.setMediaToPlay(tf.getText());
            }
        });
        return button;
    }

	@Override
	public void runUserInterface() {

        MediaPane mp = new MediaPane();
        setComponents(mp);
        TestSlider marker = getMySlider();
        mp.add(marker);
        mp.getVideoBox().getVideoOutput().setVisible(true);

        vbox = mp.getVideoBox();
		DefaultDialog<MediaPane> dialog = new DefaultDialog<MediaPane>(mp);

        controller.setMediaToPlay(MRL);
        controller.setVideoOutput(vbox.getVideoOutput());
        controller.registerMarker(marker);

		dialog.setVisible(true);
		System.exit(STATUS_SUCCESS);
	}
}
