package models;

import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

/*
 * @author Bastiën
 */

public class Kanon extends TakenModule{
	
	private UnregulatedMotor kanonMotor;
	private final int POWER = 100;//default motorpower voor het kanon 
	
	
	public Kanon() {
		this.kanonMotor = new UnregulatedMotor(MotorPort.B);
	}
	
	public void schiet() {
		kanonMotor.setPower(POWER);
		kanonMotor.backward();
		Delay.msDelay(1200);
		kanonMotor.forward();
		Delay.msDelay(1200);
		stop();
	}

	@Override
	public void voerUit() {
		this.schiet();
	}

	@Override
	public void stop() {
		kanonMotor.close();
	}
	
	

}
