package models;

import lejos.hardware.Button;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.utility.Delay;

/**
 * @author Cristina
 *
 */
public class Verplaatsen {
//	final static int DUUR_RIJDEN = 2000;
//	final static int DUUR_DRAAI = 1500;
	// Maak twee motor objecten om de motoren te controlleren/bedienen
	private UnregulatedMotor motorA;
	private UnregulatedMotor motorB;
	private final int POWER = 50;//motoren default power
	private int powerA;
	private int powerB;

	public Verplaatsen() {
		this.motorA = new UnregulatedMotor(MotorPort.C);
		this.motorB = new UnregulatedMotor(MotorPort.D);
		this.powerA = POWER;
		this.powerB = POWER;
	}

	public void motorPower(int powerA, int powerB) {
		this.powerA = powerA;
		this.powerB = powerB;
	}

	public void rijVooruit() {
		motorA.forward();
		motorB.forward();
		// rij naar voren
		motorA.setPower(powerA);
		motorB.setPower(powerB);

	}

	
	public void stop() {
		motorA.stop();
		motorB.stop();
		motorA.close();
		motorB.close();
	}

}
