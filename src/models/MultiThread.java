package models;

public class MultiThread extends TakenModule {

	PadVolger padVolger;
	MusicPlayer musicPlayer;
	int mode;

	public MultiThread(int mode) {// Een MultiThread wordt altijd gemaakt met een MusicPlayer om de muziek af te
									// spelen en een Padvolger om het pad te volgen
		this.musicPlayer = new MusicPlayer(mode);
		this.padVolger = new PadVolger();

	}

	public void voerUit() {
		Thread musicThread = new Thread(musicPlayer);
		Thread pvThread = new Thread(padVolger);
		pvThread.start();// Hiermee start je beide threads
		musicThread.start();
		try {
			pvThread.join();// Hiermee wordt er gewacht tot de padvolger thread klaar is om de muziek te
							// stoppen
			musicThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		if (mode == MusicPlayer.RPS_MODE) {
			musicPlayer.stop();

		}
		if (mode == MusicPlayer.PADVOLGER_MODE) {
			musicPlayer.stop();
			padVolger.stop();
		}
	}

}
