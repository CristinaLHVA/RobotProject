package models;

import java.io.File;

import lejos.hardware.Sound;

public class MusicPlayer extends TakenModule implements Runnable {
	
	int mode;
	public final static int RPS_MODE = 1;
	public final static int PADVOLGER_MODE = 2;
	
	public MusicPlayer() {
		
	}
	
	public MusicPlayer(int mode) {
		this.mode = mode;
	}
	public void voerUit() {
		if(mode == RPS_MODE) {
			playRPSTheme();
		}
		if(mode == PADVOLGER_MODE) {
			playPadVolgerTheme();
		}
	}
	
	public void playRPSTheme() {
		Sound.playSample(new File("eottmono.wav"));
	}
	
	public void playPadVolgerTheme() {
		Sound.playSample(new File("bttfmono.wav"));
	}
	
	public void sadMel() {
		 Sound.playTone(523, 180, 75);
		 Sound.playTone(494, 180, 75);
		 Sound.playTone(466, 180, 75);
		 Sound.playTone(440, 180, 75);
	}
	
	public void happyMel() {
		Sound.playTone(523, 300, 75);
		Sound.playTone(523, 125, 75);
		Sound.playTone(523, 125, 75);
		Sound.playTone(784, 2000, 75);
	}
	
	public void neutralMel() {
		 Sound.playTone(523, 333, 75);
		 Sound.playTone(440, 333, 75);
	}

	@Override
	public void run() {
		voerUit();
	}

	@Override
	public void stop() {
	}

}
