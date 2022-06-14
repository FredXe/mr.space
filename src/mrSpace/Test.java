package mrSpace;

import java.io.*;
import javax.sound.sampled.Clip;
import javax.sound.sampled.*;

public class Test {

	static Clip clip;
	static AudioInputStream inputStream;

	public static void main(String[] args) {
		try {
			set_music();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	public static void set_music() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		File close_file = new File("src/sounds/move.wav");
		AudioInputStream close_audioStream = AudioSystem.getAudioInputStream(close_file);
		clip = AudioSystem.getClip();
		clip.open(close_audioStream);
	}
}
