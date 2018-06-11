package models;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import tools.InfraroodTools;

/**
 * author: Bastiën / Renke
 */

public class RPS extends TakenModule {

	final static int PAPIERHOEK = 1700; // deze waarde is puur op de gok, wanneer deze verandert moet dit ook hieronder
										// veranderd worden
	int scoreTegenspeler;
	int scoreRobbie;
	int aantalRondes = 0;
	float range;
	GrijperMotor rpsGrijper;
	Verplaatsen eindeSpelBeweging;
	InfraroodTools handSensor;

	public RPS() {
		this.rpsGrijper = new GrijperMotor();
		this.eindeSpelBeweging = new Verplaatsen();
		this.handSensor = new InfraroodTools(SensorPort.S2);
	}

	public void voerUit() {
		while (aantalRondes <= 3 || scoreRobbie == scoreTegenspeler) {
			System.out.println("Druk op enter om de ronde te starten");
			Button.ENTER.waitForPress();
			rpsGrijper.open(); 	// Het Robbie-equivalent van 1... 2... GO!
			rpsGrijper.sluit();
			rpsGrijper.open(); 
			handSensor.setMode(0);
			range = handSensor.getRange();//hiermee voer ik de eerste meting uit
			while(!(range < 100)) { //zolang die meting niet minder is dan 100 blijft hij opnieuw meten
				range = handSensor.getRange();
			}
			//als de meting eronder komt, start het programma

			int robbieHand = (int) (Math.random() * 3) + 1;
			switch (robbieHand) {
			case 1:
				System.out.println("Schaar blijft schaar"); // schaar
				break;
			case 2:
				rpsGrijper.open(PAPIERHOEK); // papier
				break;
			case 3:
				rpsGrijper.sluit(); // steen
				break;
			}
			System.out.println("Wie heeft er gewonnen? Druk links voor Robbie, rechts voor de tegenspeler:");
			int knop = Button.waitForAnyPress();
			if (knop == Button.ID_LEFT) {
				scoreRobbie++;
				happyMel();
			}
			if (knop == Button.ID_RIGHT) {
				scoreTegenspeler++;
				sadMel();
			}
			else neutralMel();

			// Vervolgens gaan we de actie weer ongedaan maken zodat we aan de eventuele
			// volgende ronde kunnen beginnen
			switch (robbieHand) {
			case 1:
				rpsGrijper.sluit();
				break;
			case 2:
				rpsGrijper.sluit(PAPIERHOEK); // eerst sluit je hem tot standaard opening en daarna sluit je hem volledig.
				rpsGrijper.sluit();
				break;
			case 3:
				System.out.println("Was al steen, dus blijft gesloten");
				break;
			}
			aantalRondes++;
			System.out.printf("Robbie: %d, Mens: %d", scoreRobbie, scoreTegenspeler);
		}

		if (scoreRobbie > scoreTegenspeler) { // Robbie gaat juichen!
			juich();
		} else
			weesVerdrietig();
		stop();
	}

	public void juich() {
			while (Button.ENTER.isUp()) {
				eindeSpelBeweging.motorPower(100, -100);
				eindeSpelBeweging.rijVooruit();
				rpsGrijper.open();
				rpsGrijper.sluit();
		}
	}

	public void weesVerdrietig() {
		// robbie zal nee schudden, zich omdraaien en wegrijden
		eindeSpelBeweging.motorPower(50, -50);
		eindeSpelBeweging.rijVooruit();
		Delay.msDelay(500);
		eindeSpelBeweging.motorPower(-50, 50);
		eindeSpelBeweging.rijVooruit();
		Delay.msDelay(500);
		eindeSpelBeweging.motorPower(50, -50);
		eindeSpelBeweging.rijVooruit();
		Delay.msDelay(500);
		eindeSpelBeweging.motorPower(-50, 50);
		eindeSpelBeweging.rijVooruit();
		Delay.msDelay(1500); // deze draai moeten we nog even goed bepalen
		eindeSpelBeweging.motorPower( 25, 25);
		while (Button.ENTER.isUp()) {
			eindeSpelBeweging.rijVooruit();
		}
	}

	public void stop() {
		eindeSpelBeweging.stop();
		handSensor.close();
	}
	
	public void sadMel() {
		 Sound.playTone(523, 333, 75);
		 Sound.playTone(494, 333, 75);
		 Sound.playTone(466, 333, 75);
		 Sound.playTone(440, 333, 75);
	}
	
	public void happyMel() {
		Sound.playTone(523, 333, 75);
		Sound.playTone(523, 50, 75);
		Sound.playTone(523, 50, 75);
		Sound.playTone(784, 1500, 75);
	}
	
	public void neutralMel() {
		 Sound.playTone(523, 333, 75);
		 Sound.playTone(440, 333, 75);
	}

}
