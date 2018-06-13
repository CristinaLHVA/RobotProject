package models;

import java.io.File;
import lejos.hardware.Sound;

/**
 * @author Bastiën deze class zorgt voor (achtergrond)geluiden
 */

public class MusicPlayer extends TakenModule implements Runnable {

	int mode;
	public final static int RPS_MODE = 1;
	public final static int PADVOLGER_MODE = 2;
	public final static int G = 784;
	public final static int F = 698;
	public final static int D = 587;
	public final static int C = 523;
	public final static int B = 494;
	public final static int Bb = 466;
	public final static int A = 440;
	public final static int shortDur = 150;
	public final static int midDur = 333;
	public final static int longDur = 1200;
	public final static int defaultVol = 100;

	public MusicPlayer() {
		// Deze is nodig om de RPS-muziek af te spelen
	}

	public void voerUit() {
		playPadVolgerTheme();
	}

	public void playPadVolgerTheme() {
		Sound.playSample(new File("bttfmono.wav"));
	}

	// hieronder volgen de losstaande melodieën die aangeroepen worden bij RPS

	public void sadMel() { // Speelt een verdrietig melodietje
		Sound.playTone(C, shortDur, defaultVol);
		Sound.playTone(B, shortDur, defaultVol);
		Sound.playTone(Bb, shortDur, defaultVol);
		Sound.playTone(A, shortDur, defaultVol);
	}

	public void happyMel() { // Speelt een blij melodietje
		Sound.playTone(C, midDur, defaultVol);
		Sound.playTone(C, shortDur, defaultVol);
		Sound.playTone(C, shortDur, defaultVol);
		Sound.playTone(G, longDur, defaultVol);
	}

	public void neutralMel() { // Speelt een neutraal melodietje
		Sound.playTone(C, midDur, defaultVol);
		Sound.playTone(A, midDur, defaultVol);
	}

	public void bewegingKlaarMel() { // Speelt een notificatie
		Sound.playTone(C, midDur, defaultVol);
		Sound.playTone(D, midDur, defaultVol);
		Sound.playTone(F, midDur, defaultVol);
	}
	
	public void startToon() {
		Sound.playTone(C, shortDur, defaultVol);
	}

	@Override
	public void run() {
		voerUit();
	}

	@Override
	public void stop() {
	}

}
