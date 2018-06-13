package models;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import tools.InfraroodTools;

/**
 * @author: Basti�n / Renke deze class voert Rock Paper, Scissors uit
 */

public class RPS extends TakenModule implements Runnable {

	public final static int PAPIERHOEK = 1800;
	public final static int MAX_RONDES = 3;
	public final static int MAX_HAND_OPTIES = 3;
	public final static int INT_CORRECTIE = 1;
	public final static int MIN_DELAY = 1500;
	public final static int MAX_DELAY = MIN_DELAY * 3;
	public final static int DEFAULT_POWER = 50;
	public final static int FULL_POWER = DEFAULT_POWER * 2;
	public final static int SLOW_POWER = DEFAULT_POWER / 2;
	public final static int MIN_RANGE = 100;
	public final static int RANGE_MODE = 0;
	private int scoreTegenspeler;
	private int scoreRobbie;
	private int aantalRondes = 0;
	private float range;
	private int robbieHand;
	private GrijperMotor rpsGrijper;
	private Verplaatsen verplaatsen;
	private InfraroodTools handSensor;
	private Kanon kanon;
	private MusicPlayer musicPlayer;

	public RPS() {
		this.rpsGrijper = new GrijperMotor();
		this.verplaatsen = new Verplaatsen();
		this.handSensor = new InfraroodTools(SensorPort.S2);
		this.kanon = new Kanon();
		this.musicPlayer = new MusicPlayer();
	}

	// Start RPS
	public void voerUit() {
		while (aantalRondes < MAX_RONDES || scoreRobbie == scoreTegenspeler) { // Maximaal aantal rondes = 3
			System.out.println("Druk op enter om de ronde te starten");
			Button.ENTER.waitForPress();
			rpsGrijper.open(); // Het Robbie-equivalent van 1... 2... GO!
			rpsGrijper.sluit();
			rpsGrijper.open();
			handSensor.setMode(RANGE_MODE);

			try {
				range = handSensor.getRange();// hiermee voert Robbie de eerste meting uit, zonder die try/catch geeft
												// hij af
												// en toe een foutmelding aan het einde van het programma
			} catch (Exception e) {
				e.printStackTrace();
			}

			while (!(range < MIN_RANGE)) { // zolang die meting niet minder is dan 100 blijft hij opnieuw meten
				range = handSensor.getRange();
			}
			// als de meting eronder komt, start het programma (dan heeft hij je hand
			// gezien)

			robbieHand = (int) (Math.random() * MAX_HAND_OPTIES) + INT_CORRECTIE;
			switch (robbieHand) {
			case 1:
				System.out.println("Schaar blijft schaar"); // schaar
				musicPlayer.bewegingKlaarMel();
				break;
			case 2:
				rpsGrijper.open(PAPIERHOEK); // papier
				musicPlayer.bewegingKlaarMel();
				break;
			case 3:
				rpsGrijper.sluit(); // steen
				musicPlayer.bewegingKlaarMel();
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
				musicPlayer.bewegingKlaarMel();
				break;
			case 2:
				rpsGrijper.sluit(PAPIERHOEK + GrijperMotor.OPENINGSROTATIE);// opening resetten en teruggaan naar
																			// openingsrotatie
				musicPlayer.bewegingKlaarMel();
				break;
			case 3:
				System.out.println("Was al steen, dus blijft gesloten");
				musicPlayer.bewegingKlaarMel();
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
			musicPlayer.happyMel();
			verplaatsen.motorPower(FULL_POWER, -FULL_POWER);
			verplaatsen.rijVooruit();
			rpsGrijper.open();
			rpsGrijper.sluit();
		}
	}

	public void weesVerdrietig() {
		// robbie zal nee schudden, zich omdraaien en wegrijden. Daarnaast schiet hij
		// met zijn kanon en druipt hij langzaam af...
		musicPlayer.sadMel();
		verplaatsen.motorPower(DEFAULT_POWER, -DEFAULT_POWER); // Links
		verplaatsen.rijVooruit();
		Delay.msDelay(MIN_DELAY);
		verplaatsen.motorPower(-DEFAULT_POWER, DEFAULT_POWER); // Rechts
		verplaatsen.rijVooruit();
		Delay.msDelay(MIN_DELAY);
		verplaatsen.motorPower(DEFAULT_POWER, -DEFAULT_POWER); // Links
		verplaatsen.rijVooruit();
		Delay.msDelay(MIN_DELAY);
		verplaatsen.motorPower(-DEFAULT_POWER, DEFAULT_POWER); // Rechts
		verplaatsen.rijVooruit();
		Delay.msDelay(MAX_DELAY); // Omdraaien
		verplaatsen.motorPower(SLOW_POWER, SLOW_POWER);
		verplaatsen.rijVooruit(); // Langzaam "boos" rijden
		kanon.voerUit(); // Kanon afschieten
		Delay.msDelay(MIN_DELAY);
		kanon.voerUit();
		Delay.msDelay(MIN_DELAY);
		kanon.voerUit();

		while (Button.ENTER.isUp()) {
			verplaatsen.rijVooruit();
		}
	}

	public void stop() {
		verplaatsen.stop();
		handSensor.close();
		rpsGrijper.stop();
	}

	@Override
	public void run() {
		voerUit();
	}
}
