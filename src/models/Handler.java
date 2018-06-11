package models;

import java.io.File;
import java.util.Scanner;

import javax.swing.plaf.BorderUIResource.BevelBorderUIResource;

import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import tools.InfraroodTools;

public class Handler extends TakenModule {

	InfraroodTools irSensor;
	Verplaatsen beweeg = new Verplaatsen();
	GrijperMotor grijper = new GrijperMotor();
	File beer = new File("Robot-C12-D/Beerx3Mono.wav");

	public Handler() {
		this.irSensor = new InfraroodTools(SensorPort.S2);
	}

	public void pakVoorwerp() {
		// Sensor op distance mode zetten
		irSensor.setMode(0);
		// Afstand opvragen en rijden
		float distance = irSensor.getRange();
		while (distance > 30) {
			beweeg.motorPower(50, 50);
			beweeg.rijVooruit();
			distance = irSensor.getRange();
		}
		// Stoppen binnen bepaalde afstand
		beweeg.motorPower(0, 0);
		beweeg.rijVooruit();
		// Armen openen
		grijper.open();
		// Stukje vooruit rijden
		beweeg.motorPower(20, 20);
		beweeg.rijVooruit();
		Delay.msDelay(2400);
		// Stoppen
		beweeg.motorPower(0, 0);
		beweeg.rijVooruit();
		// Armen weer sluiten
		grijper.sluit();
		// Draaien naar de originele rijrichting
		beweeg.motorPower(50, -50);
		beweeg.rijVooruit();
		Delay.msDelay(1500);
		// Stoppen met draaien en recht zetten
		beweeg.motorPower(0, 0);
		beweeg.rijVooruit();
		beweeg.motorPower(-50, 50);
		beweeg.rijVooruit();
		Delay.msDelay(100);
		beweeg.motorPower(0, 0);
		beweeg.rijVooruit();
		// Terug rijden + 'Beer beer beer' afspelen
		beweeg.motorPower(50, 50);
		beweeg.rijVooruit();
		Sound.beepSequenceUp();
		Delay.msDelay(5000);
		// Stoppen en resources afsluiten
		beweeg.motorPower(0, 0);
		beweeg.rijVooruit();

		beweeg.stop();
		irSensor.close();
	}

	public void zoekVoorwerp() {
		// Sensor in beacon modus zetten
		irSensor.setMode(1);
		// Zoeken naar de beacon
		// Afstand en richting van beacon opvragen (kanaal 3)
		float distance = irSensor.getBeacon()[5];
		// Rijden naar de beacon !! met bijsturen waar nodig !!
		while (distance > 38) {
			if (distance > 55) {
				beweeg.motorPower(30, 30);
				beweeg.rijVooruit();
				distance = irSensor.getBeacon()[5];
			} else if (distance < 55 && distance > 38) {
				beweeg.motorPower(20, 20);
				beweeg.rijVooruit();
				distance = irSensor.getBeacon()[5];
			} else {
				break;
			}
		}
		// Stoppen en armen openen
		beweeg.motorPower(0, 0);
		beweeg.rijVooruit();
		grijper.open();
		// Stukje vooruit rijden
		beweeg.motorPower(20, 20);
		beweeg.rijVooruit();
		Delay.msDelay(2400);
		// Stoppen en armen sluiten
		beweeg.motorPower(0, 0);
		beweeg.rijVooruit();
		grijper.sluit();
		// Zoeken naar de beacon
		// Draaien in de goede richting (tweede beacon?)
		// Terug rijden en 'beer beer beer' afspelen !! met bijsturen !!
		beweeg.motorPower(50, 50);
		beweeg.rijVooruit();
		Sound.playSample(beer);
		Delay.msDelay(5000);
		// Stoppen
		beweeg.motorPower(0, 0);
		beweeg.rijVooruit();

		beweeg.stop();
		irSensor.close();

	}

	public void testBeacon() {

		irSensor.setMode(1);
		// irSensor.getBeacon();
		// float[] beacon = { irSensor.getBeacon()[4], irSensor.getBeacon()[5] };
		float distance = irSensor.getBeacon()[1];
		// for (int i = 0; i < beacon.length; i++) {
		// System.out.println(beacon[i]);
		// }
		while (distance > 38) {
			if (distance > 55) {
				beweeg.motorPower(30, 30);
				beweeg.rijVooruit();
				distance = irSensor.getBeacon()[5];
			} else if (distance < 55 && distance > 38) {
				beweeg.motorPower(20, 20);
				beweeg.rijVooruit();
				distance = irSensor.getBeacon()[5];
			} else {
				break;
			}
		}

		beweeg.motorPower(0, 0);
		beweeg.rijVooruit();
		grijper.open();
		beweeg.motorPower(20, 20);
		beweeg.rijVooruit();
		Delay.msDelay(2400);
		beweeg.motorPower(0, 0);
		beweeg.rijVooruit();
		grijper.sluit();
		beweeg.motorPower(30, 70);
		beweeg.rijVooruit();
		Delay.msDelay(4400);
		beweeg.motorPower(60, 60);
		beweeg.rijVooruit();
		Delay.msDelay(5000);
		beweeg.motorPower(0, 0);
		beweeg.rijVooruit();

		beweeg.stop();
		irSensor.close();

	}

	public void testRemote() {
		switch (irSensor.getSensor().getRemoteCommand(1)) {
		case 0:
			System.out.println("Geen knop ingedrukt");
			break;
		case 1:
			System.out.println("Rood Boven ingedrukt");
			break;
		case 2:
			System.out.println("Rood Onder ingedrukt");
			break;
		case 3:
			System.out.println("Blauw Boven ingedrukt");
			break;
		case 4:
			System.out.println("Blauw Onder ingedrukt");
			break;
		case 5:
			System.out.println("Rood Boven EN Blauw Boven ingedrukt");
			break;
		case 6:
			System.out.println("Rood Boven EN Blauw onder ingedrukt");
			break;
		case 7:
			System.out.println("Rood Onder en Blauw Boven ingedrukt");
			break;
		case 8:
			System.out.println("Rood Onder en Blauw Onder ingedrukt");
			break;
		case 9:
			System.out.println("Beacon modus");
			break;
		case 10:
			System.out.println("Rood Boven en Rood Onder ingedrukt");
			break;
		case 11:
			System.out.println("Blauw Boven en Blauw Onder ingedrukt");
			break;
		}
	}

	@Override
	public void voerUit() {
		pakVoorwerp();

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}