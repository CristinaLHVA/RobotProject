package nl.hva.miw.robot.cohort12;

import lejos.hardware.Brick;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.SensorPort;
import lejos.remote.ev3.RMIRemoteTextLCD;
import lejos.remote.ev3.RemoteTextLCD;
import lejos.utility.Delay;
import models.*;
import tools.ColorTools;

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
			System.out.printf("Druk \n-links voor Padvolger, \n-beneden voor Handler, \n-rechts voor RPS, \n-omhoog voor Kanon"
					+ "\n-escape voor stop");
			knop = Button.waitForAnyPress();
			wisScherm();
			if (knop == Button.ID_LEFT) {
				TakenModule padVolger = new PadVolger();
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
		
//		waitForKey(Button.ENTER);
//		PadVolger padVolger = new PadVolger();
//		padVolger.voerUit();
//		while (Button.ESCAPE.isUp()) {
//			padVolger.printLicht();
//			waitForKey(Button.ENTER);
//		}

//		System.out.println("Einde Programma");

//		waitForKey(("Beacon zoeken gestart...");
//		Handler handler = new Handler();
//		while (Button.ESCAPE.isUp()){
//			handler.testRun();
//			Delay.msDeButto.ENTER);

//		System.out.printlnlay(5000);
//			handler.testBeacon();
//			Delay.msDelay(5000);

//		}
//		RPS rps = new RPS();
//		rps.voerUit();
//
//	}


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
