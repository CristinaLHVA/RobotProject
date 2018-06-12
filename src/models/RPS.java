package models;
import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import tools.InfraroodTools;

/**
 * author: Bastiën / Renke
 */

public class RPS extends TakenModule implements Runnable {

	final static int PAPIERHOEK = 1800; // deze waarde is puur op de gok, wanneer deze verandert moet dit ook hieronder
										// veranderd worden
	public final static int MAXRONDES = 3;
	private int scoreTegenspeler;
	private int scoreRobbie;
	private int aantalRondes = 0;
	private float range;
	private int robbieHand;
	private GrijperMotor rpsGrijper;
	private Verplaatsen eindeSpelBeweging;
	private InfraroodTools handSensor;
	private Kanon kanon;
	private MusicPlayer musicPlayer;

	public RPS() {
		this.rpsGrijper = new GrijperMotor();
		this.eindeSpelBeweging = new Verplaatsen();
		this.handSensor = new InfraroodTools(SensorPort.S2);
		this.kanon = new Kanon();
		this.musicPlayer = new MusicPlayer();
	}

	public void voerUit() {
		while (aantalRondes < MAXRONDES || scoreRobbie == scoreTegenspeler) { // Maximaal aantal rondes = 3
			System.out.println("Druk op enter om de ronde te starten");
			Button.ENTER.waitForPress();
			rpsGrijper.open(); // Het Robbie-equivalent van 1... 2... GO!
			rpsGrijper.sluit();
			rpsGrijper.open();
			handSensor.setMode(0);

			try {
				range = handSensor.getRange();// hiermee voer ik de eerste meting uit, zonder die try/catch geeft hij af
												// en toe een foutmelding aan het einde van het programma
			} catch (Exception e) {
				e.printStackTrace();
			}

			while (!(range < 100)) { // zolang die meting niet minder is dan 100 blijft hij opnieuw meten
				range = handSensor.getRange();
			}
			// als de meting eronder komt, start het programma (dan heeft hij je hand
			// gezien)

			robbieHand = (int) (Math.random() * 3) + 1;
			switch (robbieHand) {
			case 1:
				System.out.println("Schaar blijft schaar"); // schaar
				musicPlayer.bewegingKlaarToon();
				break;
			case 2:
				rpsGrijper.open(PAPIERHOEK); // papier
				musicPlayer.bewegingKlaarToon();
				break;
			case 3:
				rpsGrijper.sluit(); // steen
				musicPlayer.bewegingKlaarToon();
				break;
			}
			System.out.println("Wie heeft er gewonnen?");
			System.out.printf("Links = Robbie\nRechts = tegenspeler: ");
			int knop = Button.waitForAnyPress();
			if (knop == Button.ID_LEFT) {
				scoreRobbie++;
				musicPlayer.happyMel();
			}
			if (knop == Button.ID_RIGHT) {
				scoreTegenspeler++;
				musicPlayer.sadMel();
			}
			if (knop == Button.ID_ENTER) {
				musicPlayer.neutralMel();
			}
			// Vervolgens gaan we de actie weer ongedaan maken zodat we aan de eventuele
			// volgende ronde kunnen beginnen
			switch (robbieHand) {
			case 1:
				rpsGrijper.sluit();
				musicPlayer.bewegingKlaarToon();
				break;
			case 2:
				rpsGrijper.sluit(PAPIERHOEK + GrijperMotor.OPENINGSROTATIE);// opening resetten en teruggaan naar
																			// openingsrotatie
				musicPlayer.bewegingKlaarToon();
				break;
			case 3:
				System.out.println("Was al steen, dus blijft gesloten");
				musicPlayer.bewegingKlaarToon();
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
		// robbie zal nee schudden, zich omdraaien en wegrijden. Daarnaast schiet hij
		// met zijn kanon
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
		Delay.msDelay(1900); // deze draai moeten we nog even goed bepalen
		kanon.voerUit();
		eindeSpelBeweging.motorPower(50, -50);
		eindeSpelBeweging.rijVooruit();
		Delay.msDelay(700);
		kanon.voerUit();
		eindeSpelBeweging.motorPower(-50, 50);
		eindeSpelBeweging.rijVooruit();
		Delay.msDelay(500);
		kanon.voerUit();
		eindeSpelBeweging.motorPower(25, 25);
		while (Button.ENTER.isUp()) {
			eindeSpelBeweging.rijVooruit();
		}
	}

	public void stop() {
		eindeSpelBeweging.stop();
		handSensor.close();
		rpsGrijper.stop();
	}

	@Override
	public void run() {
		voerUit();
	}
}
