package Labyrinth;
import java.io.*;

import javax.sound.sampled.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Music {

	public static Clip PlayMusic;

	public static File soundFile = new File("./music/Background.wav");
	public static File PickUp = new File("./music/pick_up.wav");

	static void PickUp() {
		
		try {

			AudioInputStream audioIn = AudioSystem.getAudioInputStream(PickUp);
			// get a clip
			PlayMusic = AudioSystem.getClip();

			PlayMusic.open(audioIn);

			// music loops for rest of the class screen
			PlayMusic.loop(0);

		} catch (UnsupportedAudioFileException exception) {
			exception.printStackTrace();
		} catch (IOException exception2) {
			exception2.printStackTrace();
		} catch (LineUnavailableException exception3) {
			exception3.printStackTrace();
		}
	
		
	}

	static void EndTurnMusic() {

	}

	static void playBackgroundMusic() {

		try {

			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			// get a clip
			PlayMusic = AudioSystem.getClip();

			PlayMusic.open(audioIn);

			// music loops for rest of the class screen
			PlayMusic.loop(Clip.LOOP_CONTINUOUSLY);

		} catch (UnsupportedAudioFileException exception) {
			exception.printStackTrace();
		} catch (IOException exception2) {
			exception2.printStackTrace();
		} catch (LineUnavailableException exception3) {
			exception3.printStackTrace();
		}
	}

	public static void stopMusic() { // stop the music

		PlayMusic.stop();

	}
}