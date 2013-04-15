package org.videolan.jvlc.example;

import org.videolan.jvlc.Audio;
import org.videolan.jvlc.JVLC;
import org.videolan.jvlc.MediaDescriptor;
import org.videolan.jvlc.MediaPlayer;
import org.videolan.jvlc.Video;
import org.videolan.jvlc.event.MediaPlayerListener;

public class VLCExample {

	public static void main(final String[] args) throws InterruptedException {
		System.out.println("== Starting VLCExample ==");
		if (args.length == 0) {
			System.out.print("Creating a JVLC instance without args");
		} else {
			System.out.println("Creating a JVLC instance with args: ");
			for (int i = 0; i < args.length; i++) {
				System.out.println(i + ") " + args[i]);
			}
		}
		final JVLC jvlc = new JVLC(args);
		System.out.println("... done.");

		final MediaDescriptor mediaDescriptor = new MediaDescriptor(jvlc, "rtsp://elabmc.ist.utl.pt/planck.sdp");
		final MediaPlayer mediaPlayer = mediaDescriptor.getMediaPlayer();

		mediaPlayer.addListener(new MediaPlayerListener() {

			@Override
			public void endReached(final MediaPlayer mediaPlayer) {
				System.out.println("Media instance end reached. MRL: " + mediaPlayer.getMediaDescriptor().getMrl());
			}

			@Override
			public void paused(final MediaPlayer mediaPlayer) {
				System.out.println("Media instance paused. MRL: " + mediaPlayer.getMediaDescriptor().getMrl());
			}

			@Override
			public void playing(final MediaPlayer mediaPlayer) {
				System.out.println("Media instance played. MRL: " + mediaPlayer.getMediaDescriptor().getMrl());
			}

			@Override
			public void positionChanged(final MediaPlayer mediaPlayer) {
				// TODO Auto-generated method stub
			}

			@Override
			public void timeChanged(final MediaPlayer mediaPlayer, final long newTime) {
				System.out.println("new time: " + newTime);
			}

			@Override
			public void stopped(final MediaPlayer mediaPlayer) {
				System.out.println("Media player stopped. MRL: " + mediaPlayer.getMediaDescriptor().getMrl());
			}

			@Override
			public void errorOccurred(final MediaPlayer mediaPlayer) {
				System.out.println("An error has occurred.");
			}
		});
		mediaPlayer.play();

		while (!mediaPlayer.hasVideoOutput()) {
			Thread.sleep(100);
		}

		final Video video = new Video(jvlc);
		System.out.print(video.getWidth(mediaPlayer));
		System.out.print("x");
		System.out.println(video.getHeight(mediaPlayer));
		System.out.print("Fullscreen... ");
		video.setFullscreen(mediaPlayer, true);
		Thread.sleep(3000);
		System.out.println("real size.");
		video.setFullscreen(mediaPlayer, false);
		System.out.print("Taking snapshot... ");
		video.getSnapshot(mediaPlayer, System.getProperty("user.dir") + "/snap.png", 0, 0);
		System.out.println("taken. (see " + System.getProperty("user.dir") + "/snap.png )");
		Thread.sleep(2000);
		System.out.println("Resizing to 300x300");
		video.setSize(300, 300);

		System.out.print("Muting...");
		final Audio audio = new Audio(jvlc);
		audio.setMute(true);
		Thread.sleep(3000);
		System.out.println("unmuting.");
		audio.setMute(false);
		Thread.sleep(3000);
		System.out.println("Volume is: " + audio.getVolume());
		System.out.print("Setting volume to 150... ");
		audio.setVolume(150);
		System.out.println("done");
		System.out.println("== AUDIO INFO ==");
		System.out.println("Audio track number: " + audio.getTrack(mediaPlayer));
		System.out.println("Audio channel info: " + audio.getChannel());
		Thread.sleep(3000);
		System.out.println("MEDIA PLAYER INFORMATION");
		System.out.println("--------------------------");
		System.out.println("Total length (ms) :\t" + mediaPlayer.getLength());
		System.out.println("Input time (ms) :\t" + mediaPlayer.getTime());
		System.out.println("Input position [0-1]:\t" + mediaPlayer.getPosition());
		System.out.println("Input FPS :\t" + mediaPlayer.getFPS());

		System.out.println("Everything fine ;)");
		return;
	}
}
