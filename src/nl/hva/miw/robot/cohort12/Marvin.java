package nl.hva.miw.robot.cohort12;
import lejos.hardware.Brick;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.ev3.LocalEV3;
import lejos.utility.Delay;
import models.*;

public class Marvin {
	
	Brick brick;
	 
	public Marvin() {
		super();
		brick = LocalEV3.get();
	}
	
	public static void main(String[] args) {
		Marvin marvin = new Marvin();
		
		marvin.run();
		
	}
	
	private void run() {
		int knop = 0;
		while (knop != Button.ID_ESCAPE) {
			wisScherm();
			System.out.printf("links = Padvolger, \nbeneden = Handler, \nrechts = RPS, \nomhoog = Kanon"
					+ "\n-escape voor stop");
			knop = Button.waitForAnyPress();
			wisScherm();
			if (knop == Button.ID_LEFT) {
				TakenModule padVolger = new MultiThread();
				padVolger.voerUit();
				padVolger.stop();
			}
			if (knop == Button.ID_DOWN){
				TakenModule handler = new Handler();
				handler.voerUit();
				handler.stop();
			}
			if (knop == Button.ID_RIGHT) {
				TakenModule rps = new RPS();
				rps.voerUit();
				rps.stop();
			}
			if (knop == Button.ID_UP) {
				TakenModule kanon = new Kanon();
				kanon.voerUit();
				kanon.stop();
			}
		}
		System.out.println("Einde programma, druk op een toets om af te sluiten");
		Button.waitForAnyPress();
	}	

	public void waitForKey(Key key) {
		while(key.isUp()) {
			Delay.msDelay(100);
		}
		while(key.isDown()) {
			Delay.msDelay(100);
		}
	}
	
	public void wisScherm() {
		for(int regel = 0; regel < 8; regel++) {
			System.out.println();
		}
	}
	
	
}
