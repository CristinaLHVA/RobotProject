package models;

import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

/**
 * @author Bastiën kanon
 */

public class Kanon extends TakenModule {

	private UnregulatedMotor kanonMotor;
	public final int POWER = 100;// default motorpower voor het kanon
	public final int maxDelay = 1200; 

	public Kanon() {
		this.kanonMotor = new UnregulatedMotor(MotorPort.B);
	}

	public void schiet() {
		kanonMotor.setPower(POWER);
		kanonMotor.backward();
		Delay.msDelay(maxDelay); // De motor draait achteruit voor 1200ms
		kanonMotor.forward();
		Delay.msDelay(maxDelay); // De motor draait vooruit voor 1200ms
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
