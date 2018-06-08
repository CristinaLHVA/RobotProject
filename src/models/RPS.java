package models;

import lejos.hardware.Button;
import lejos.utility.Delay;

/**
 * author: Bastiën
 */

public class RPS extends TakenModule {

	final static int papierHoek = -300; // deze waarde is puur op de gok, wanneer deze verandert moet dit ook hieronder
										// veranderd worden
	int scoreTegenspeler;
	int scoreRobbie;
	int aantalRondes = 0;
	GrijperMotor rpsGrijper;
	Verplaatsen eindeSpelBeweging;

	public RPS() {
		this.rpsGrijper = new GrijperMotor();
		this.eindeSpelBeweging = new Verplaatsen();
	}

	public void voerUit() {
		while (aantalRondes <= 3 || scoreRobbie == scoreTegenspeler) {
			System.out.println("Druk op enter om de ronde te starten");
			Button.ENTER.waitForPress();

			// Het Robbie-equivalent van 1... 2... GO!
			rpsGrijper.open();
			rpsGrijper.sluit();
			rpsGrijper.open();

			int robbieHand = (int) (Math.random() * 3) + 1;
			switch (robbieHand) {
			case 1:
				System.out.println("Schaar blijft schaar"); // schaar
				break;
			case 2:
				rpsGrijper.open(papierHoek); // papier
				break;
			case 3:
				rpsGrijper.sluit(); // steen
				break;
			}
			System.out.println("Wie heeft er gewonnen? Druk links voor Robbie, rechts voor de tegenspeler:");

			if (Button.LEFT.isDown()) {
				scoreRobbie++;
			}
			if (Button.RIGHT.isDown()) {
				scoreTegenspeler++;
			}

			// Vervolgens gaan we de actie weer ongedaan maken zodat we aan de eventuele
			// volgende ronde kunnen beginnen
			switch (robbieHand) {
			case 1:
				rpsGrijper.sluit();
				break;
			case 2:
				rpsGrijper.sluit(300); // eerst sluit je hem tot standaard opening en daarna sluit je hem volledig.
				rpsGrijper.sluit();
				break;
			case 3:
				System.out.println("Was al steen, dus blijft gesloten");
				break;
			}
		}
		if (scoreRobbie > scoreTegenspeler) { // Robbie gaat juichen!
			eindeSpelBeweging.draaiRechts();
			while (Button.ENTER.isUp()) {
				rpsGrijper.open();
				rpsGrijper.sluit();
			}
		} else {
			// robbie zal nee schudden, zich omdraaien en wegrijden
			eindeSpelBeweging.draaiRechts();
			Delay.msDelay(500);
			eindeSpelBeweging.draaiLinks();
			Delay.msDelay(500);
			eindeSpelBeweging.draaiRechts();
			Delay.msDelay(500);
			eindeSpelBeweging.draaiLinks();
			Delay.msDelay(1500); // deze draai moeten we nog even goed bepalen
			while (Button.ENTER.isUp()) {
				eindeSpelBeweging.rijVooruit();
			}
		}
		stop();
	}

	public void stop() {
		eindeSpelBeweging.stop();
	}

}
