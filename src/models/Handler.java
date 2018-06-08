package models;

import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import tools.InfraroodTools;

public class Handler extends TakenModule {

	InfraroodTools irSensor;
	Verplaatsen beweeg = new Verplaatsen();
	GrijperMotor grijper = new GrijperMotor();

	public Handler() {
		// TODO Auto-generated constructor stub

		this.irSensor = new InfraroodTools(SensorPort.S2);
		// irSensor.getSensor().getRemoteCommand(1);
	}

	public void testRun() {
		// grijper.sluit();
		irSensor.setMode(0);
		System.out.println("Mode is: " + irSensor.getMode());
		float distance = irSensor.getRange();
		System.out.println(irSensor.getRange());
		while (distance > 33) {
			beweeg.motorPower(50, 50);
			beweeg.rijVooruit();
			distance = irSensor.getRange();
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
		beweeg.motorPower(40, 60);
		beweeg.rijVooruit();
		Delay.msDelay(4400);
		beweeg.motorPower(50, 50);
		beweeg.rijVooruit();
		Delay.msDelay(5000);

		beweeg.motorPower(0, 0);
		beweeg.rijVooruit();

		beweeg.stop();
		irSensor.close();
	}

	public void testBeacon() {
		irSensor.setMode(1);
		// irSensor.getBeacon();
//		float[] beacon = { irSensor.getBeacon()[4], irSensor.getBeacon()[5] };
		float distance = irSensor.getBeacon()[1];
//		for (int i = 0; i < beacon.length; i++) {
//			System.out.println(beacon[i]);
//		}
		while (distance > 38) {
			if (distance > 60) {
				beweeg.motorPower(30, 30);
				beweeg.rijVooruit();
				distance = irSensor.getBeacon()[5];
			} else if (distance < 50 && distance > 38) {
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
		beweeg.motorPower(40, 60);
		beweeg.rijVooruit();
		Delay.msDelay(4400);
		beweeg.motorPower(50, 50);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}
	
	
}