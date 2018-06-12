package models;

public class MultiThread extends TakenModule {

	RPS rps;
	PadVolger padVolger;
	MusicPlayer musicPlayer;
	int mode;

	public MultiThread(int mode) {
		this.mode = mode;
		if (mode == MusicPlayer.RPS_MODE) {
			this.musicPlayer = new MusicPlayer(mode);
			this.rps = new RPS();
		} else if (mode == MusicPlayer.PADVOLGER_MODE) {
			this.musicPlayer = new MusicPlayer(mode);
			this.padVolger = new PadVolger();
		}
	}

	public void voerUit() {
		if (mode == MusicPlayer.PADVOLGER_MODE) {
			Thread musicThread = new Thread(musicPlayer);
			Thread pvThread = new Thread(padVolger);
			pvThread.start();
			musicThread.start();
			try {
				pvThread.join();
				musicThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public void stop() {
		if (mode == MusicPlayer.RPS_MODE) {
			musicPlayer.stop();
			rps.stop();

		}
		if (mode == MusicPlayer.PADVOLGER_MODE) {
			musicPlayer.stop();
			padVolger.stop();
		}
	}

}
