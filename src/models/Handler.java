package models;

import java.io.File;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import tools.InfraroodTools;

/**
 * @author Ray
 *
 */
/**
* @author Cristina 
*/ 
public class Handler extends TakenModule {

	private InfraroodTools irSensor;
	private Verplaatsen beweeg = new Verplaatsen();
	private GrijperMotor grijper = new GrijperMotor();
	private File beer = new File("Beerx3Mono8b.wav");
	private final static int DISTANCE_CHANNEL = 3;
	private final static int HEADING_CHANNEL = 2;
	

	public Handler() {
		this.irSensor = new InfraroodTools(SensorPort.S2);
	}

	public void pakVoorwerp() {
		// Sensor op distance mode zetten
		irSensor.setMode(0);
		// Afstand opvragen en rijden
		float distance = 0;
		try {
			distance = irSensor.getRange();
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (distance > 28) {//rijden tot bij de voorwerp en afstand opniew opvragen
			beweeg.motorPower(50, 50);
			beweeg.rijVooruit();
			distance = irSensor.getRange();
		}
		// Stoppen binnen bepaalde afstand/bij voorwerp
		beweeg.motorPower(0, 0);
		beweeg.rijVooruit();
		// Armen openen
		grijper.open();
		// Stukje vooruit rijden 
		beweeg.motorPower(20, 20);
		beweeg.rijVooruit();
		Delay.msDelay(3400);
		// Stoppen en object oppaken
		beweeg.motorPower(0, 0);
		beweeg.rijVooruit();
		// Armen weer sluiten
		grijper.sluit();
		// Draaien naar de originele rijrichting
		beweeg.motorPower(50, -50);
		beweeg.rijVooruit();
		Delay.msDelay(2000);//aangepast
		// Stoppen met draaien en recht zetten
		beweeg.motorPower(0, 0);
		beweeg.rijVooruit();
		beweeg.motorPower(-50, 50);//naar links draaien
		beweeg.rijVooruit();
		Delay.msDelay(500);
		beweeg.motorPower(0, 0);//stoppen
		beweeg.rijVooruit();
		// Terug rijden + 'Beer beer beer' afspelen
		beweeg.motorPower(50, 50);//rijdt terug
		beweeg.rijVooruit();
//		Sound.playSample(beer, 100);
		Delay.msDelay(5000);
		// Stoppen en armen open
		beweeg.motorPower(0, 0);//stopt
		beweeg.rijVooruit();
		grijper.open();//open armen
		// Stukje acheteruit rijden
		beweeg.motorPower(-30, -30); //rijdt naar achteruit
		beweeg.rijVooruit();
		Delay.msDelay(4000);
		// Stoppen en armen sluiten
		beweeg.motorPower(0, 0);//stopt
		beweeg.rijVooruit();
		grijper.sluit();//sluit armen
	}

	public void zoekVoorwerp() {
		// Sensor in beacon modus zetten
		irSensor.setMode(1);
		// Afstand en richting van beacon opvragen (kanaal 3)
		float heading = irSensor.getBeacon()[HEADING_CHANNEL];
		float distance = irSensor.getBeacon()[DISTANCE_CHANNEL];//distance in percentage gebaseerd op de proximity range
		Sound.twoBeeps();//klaar om te starten
		System.out.println("Heading: " + heading + "Distance: " + distance);
		beweeg.motorPower(40, 40);//rijdt 2 seconden in de richting van het object/beacon
		beweeg.rijVooruit();
		Delay.msDelay(2000);
		System.out.println("Heading: " + heading + "Distance: " + distance);

		// Rijden naar de beacon 
		while (distance > 5) {
			// if (distance > 20) {
			// power = (int) (40 - (2*heading));
			if (heading < -1 ) {
				beweeg.motorPower((int) (20 + (3 * heading)), 20);//corigeert de afwijking, doel is heading = 0 
				beweeg.rijVooruit();
				heading = irSensor.getBeacon()[HEADING_CHANNEL];
				distance = irSensor.getBeacon()[DISTANCE_CHANNEL];
				System.out.println("Heading: " + heading + "Distance: " + distance);
			} else if (heading > 1 ) {
				beweeg.motorPower(20, (int) (20 - (3 * heading)));//corigeert de afwijking, doel is heading = 0
				beweeg.rijVooruit();
				heading = irSensor.getBeacon()[HEADING_CHANNEL];
				distance = irSensor.getBeacon()[DISTANCE_CHANNEL];
				System.out.println("Heading: " + heading + "Distance: " + distance);
			} else {
				beweeg.motorPower(30, 30);//hij rijdt op de beacon af
				beweeg.rijVooruit();
				heading = irSensor.getBeacon()[HEADING_CHANNEL];
				distance = irSensor.getBeacon()[DISTANCE_CHANNEL];
				System.out.println("Heading: " + heading + "Distance: " + distance);
			}
			// } else if (distance < 20) {
			//// power = (int) (20 - 2*heading);
			// if (heading < -2) {
			// beweeg.motorPower((int)(20 +( 2*heading)), 20);
			// beweeg.rijVooruit();
			// heading = irSensor.getBeacon()[HEADING_CHANNEL];
			// distance = irSensor.getBeacon()[DISTANCE_CHANNEL];
			// System.out.println("Heading: " + heading + "Distance: " + distance);
			// } else if (heading > 2) {
			// beweeg.motorPower(20, (int)(20 - (2*heading)));
			// beweeg.rijVooruit();
			// heading = irSensor.getBeacon()[HEADING_CHANNEL];
			// distance = irSensor.getBeacon()[DISTANCE_CHANNEL];
			// System.out.println("Heading: " + heading + "Distance: " + distance);
			// } else {
			// beweeg.motorPower(20, 20);
			// beweeg.rijVooruit();
			// heading = irSensor.getBeacon()[HEADING_CHANNEL];
			// distance = irSensor.getBeacon()[DISTANCE_CHANNEL];
			// System.out.println("Heading: " + heading + "Distance: " + distance);
			// }
			// } else {
			// break;
			// }
		}
		// Stoppen en armen openen
		beweeg.motorPower(0, 0);//als hij dichter bij is dan 5, hij stopt met zoeken en stopt
		beweeg.rijVooruit();
		grijper.open();//opent de armen
		// Stukje vooruit rijden
		beweeg.motorPower(20, 20);//rijdt naar voren om object/becaon op te pakken
		beweeg.rijVooruit();
		Delay.msDelay(4000);
		// Stoppen en armen sluiten
		beweeg.motorPower(0, 0);//stopt
		beweeg.rijVooruit();
		grijper.sluit();//pakt object/beacon op

		// Draaien in de goede richting (tweede beacon?)
		beweeg.motorPower(50, -50);//hij draait terug
		beweeg.rijVooruit();
		Delay.msDelay(2000);
		beweeg.motorPower(0, 0);//stopt
		beweeg.rijVooruit();
		beweeg.motorPower(-50, 50);//corigeert positie van achter wielen
		beweeg.rijVooruit();
		Delay.msDelay(500);
		// Terug rijden en 'beer beer beer' afspelen 
		beweeg.motorPower(50, 50);//rijdt vooruit met object
		beweeg.rijVooruit();
//		Sound.playSample(beer, 100);
		Delay.msDelay(5000);
		// Stoppen en armen open
		beweeg.motorPower(0, 0);//stopt
		beweeg.rijVooruit();
		grijper.open();//laat object/beacon los
		// Stukje acheteruit rijden
		beweeg.motorPower(-30, -30);//rijdt achteruit
		beweeg.rijVooruit();
		Delay.msDelay(4000);
		// Stoppen en armen sluiten
		beweeg.motorPower(0, 0);//stopt
		beweeg.rijVooruit();
		grijper.sluit();//sluit armen

	}

	@Override
	public void voerUit() {
		int knop = 0;
		while (knop != Button.ID_ESCAPE) {
			System.out.printf("Druk \n-links voor Pak Voorwerp, \n-rechts voor Zoek Object" + "\n-escape voor stop");
			knop = Button.waitForAnyPress();
			if (knop == Button.ID_LEFT) {
				pakVoorwerp();
				stop();
			}
			if (knop == Button.ID_RIGHT) {
				zoekVoorwerp();
				stop();
			}
		}
		System.out.println("Einde programma, druk op een toets om af te sluiten");
		Button.waitForAnyPress();
		
	}

	@Override
	public void stop() {
		beweeg.stop();
		grijper.stop();
		irSensor.close();

	}

}