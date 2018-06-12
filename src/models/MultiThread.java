package models;

public class MultiThread extends TakenModule {

	RPS rps;
	PadVolger padVolger;
	MusicPlayer musicPlayer;
	int mode;

//	public MultiThread(RPS rps, MusicPlayer musicPlayer) {
//		this.musicPlayer = musicPlayer;
//		this.rps = rps;
//	}
	
	public MultiThread(int mode) {
		this.mode = mode;
		if(mode == MusicPlayer.RPS_MODE) {
			this.musicPlayer = new MusicPlayer(mode);
			this.rps = new RPS();
		}
		else if(mode == MusicPlayer.PADVOLGER_MODE) {
			this.musicPlayer = new MusicPlayer(mode);
			this.padVolger = new PadVolger();
		}
	}


	public MultiThread(PadVolger padVolger, MusicPlayer musicPlayer) {
		this.padVolger = padVolger;
		this.musicPlayer = musicPlayer;

	}

	public void voerUit() {
		(new Thread(rps)).start();
		(new Thread(musicPlayer)).start();
	}

	@Override
	public void stop() {
		if (mode == musicPlayer.RPS_MODE) {
			musicPlayer.stop();
			rps.stop();

		}
		if (mode == musicPlayer.PADVOLGER_MODE) {
			musicPlayer.stop();
			padVolger.stop();
		}
	}

}
