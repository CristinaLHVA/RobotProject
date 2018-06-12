package models;

public class MultiThread {
	
	RPS rps;
	PadVolger padVolger;
	MusicPlayer musicPlayer;
	int mode;

	
	public MultiThread(RPS rps, MusicPlayer musicPlayer) {
		this.musicPlayer = musicPlayer;
		this.rps = rps;
		}
	
	public MultiThread(PadVolger padVolger, MusicPlayer musicPlayer) {
		this.padVolger = padVolger;
		this.musicPlayer = musicPlayer;
		
	}
	
	public void voerUit() {
		(new Thread(rps)).start();
		(new Thread(musicPlayer)).start();
	}

}
