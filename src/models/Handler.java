package models;

import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import tools.InfraroodTools;

public class Handler {

	InfraroodTools irSensor;
	Verplaatsen beweeg;
	GrijperMotor grijper;

	public Handler() {
		// TODO Auto-generated constructor stub

		this.irSensor = new InfraroodTools(SensorPort.S2);
		// irSensor.getSensor().getRemoteCommand(1);
	}

	public void testRun() {
		beweeg = new Verplaatsen();
		grijper = new GrijperMotor();
//		grijper.sluit();
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
		beweeg.motorPower(20,20);
		beweeg.rijVooruit();
		Delay.msDelay(2400);
		beweeg.motorPower(0,0);
		beweeg.rijVooruit();
		grijper.sluit();
		beweeg.motorPower(40, 60);
		beweeg.rijVooruit();
		Delay.msDelay(4400);
		beweeg.motorPower(50, 50);
		beweeg.rijVooruit();
		Delay.msDelay(5000);
		beweeg.motorPower(0,0);
		beweeg.rijVooruit();	
		
		beweeg.close();
	
		irSensor.close();
	}

	public void testBeacon() {
		irSensor.setMode(1);
//		irSensor.getBeacon();
		float[] beacon =  {irSensor.getBeacon()[0], irSensor.getBeacon()[1]};
		for (int i = 0; i < beacon.length; i++) {
		System.out.println(beacon[i]);
		}
		
		
	}
	
	
}