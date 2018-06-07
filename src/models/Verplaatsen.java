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
	final static int DUUR_RIJDEN = 2000;
	final static int DUUR_DRAAI = 1500;
	// Maak twee motor objecten om de motoren te controlleren/bedienen
	private UnregulatedMotor motorA;
	private UnregulatedMotor motorB;
	private int powerA;
	private int powerB;

	public Verplaatsen() {
		this.motorA = new UnregulatedMotor(MotorPort.C);
		this.motorB = new UnregulatedMotor(MotorPort.D);
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
//		System.out.println("Rijvooruit");
		
//		motorA.stop();
//		motorB.stop();

	}

	public void rijAchteruit() {
		motorA.backward();
		motorB.backward();
		// rij naar achteren
		motorA.setPower(powerA);
		motorB.setPower(powerB);
		System.out.println("Rijachteruit");
	}

	public void draaiRechts() {
		motorA.backward();
		motorB.forward();

		// voer draai uit
		motorA.setPower(powerA);
		motorB.setPower(powerB);

	}

	public void draaiLinks() {
		motorA.forward();
		motorB.backward();

		// voer draai uit
		motorA.setPower(powerA);
		motorB.setPower(powerB);
	}

	public void rijVooruitTest() {
		System.out.println("Rij naar voren\n en Stop\n");
		System.out.println("Druk een key om te starten");

		Button.LEDPattern(4); // flits groen led wanneer klaar

		Button.waitForAnyPress(); // wacht voor een druk op een knop om te beginen met bewegen

		motorA.forward();
		motorB.forward();

		// zet motors op 50% power
		motorA.setPower(50);
		motorB.setPower(50);

		// wacht 2 seconden
		Delay.msDelay(DUUR_RIJDEN);

		// stop motors met rem aan
		motorA.stop();
		motorB.stop();

		// //motor resources vrijgeven
		// motorA.close();
		// motorB.close();
		//
	}

	public void rijAchteruitTest() {

		System.out.println("Rij naar achter\n en Stop\n");
		System.out.println("Druk een key om te starten");

		Button.LEDPattern(4); // flits groen led wanneer klaar

		Button.waitForAnyPress(); // wacht voor een druk op een knop om te beginen met bewegen

		motorA.backward();
		motorB.backward();

		// set motors to 50% power
		motorA.setPower(50);
		motorB.setPower(50);

		// wait 2 seconds.
		Delay.msDelay(DUUR_RIJDEN);

		// stop motors with brakes on
		motorA.stop();
		motorB.stop();

		// // motor resources vrijgeven
		// motorA.close();
		// motorB.close();
		//

	}

	public void draaiRechtsTest() {

		System.out.println("Draai rechts\n en Stop\n");
		System.out.println("Druk een key om te starten");

		Button.LEDPattern(4); // flits groen led wanneer klaar

		Button.waitForAnyPress(); // wacht voor een druk op een knop om te beginen met bewegen

		// draai rechts door het rechter motor achteruit laten draaien
		motorA.backward();
		motorB.forward();

		// voer draai uit
		motorA.setPower(50);
		motorB.setPower(50);

		// pas tijd aan om een 90% draai te krijgen
		Delay.msDelay(DUUR_DRAAI);

		motorA.stop();
		motorB.stop();

		// zet rechter motor terug naar voowaarts bewegen
		// motorA.forward();
		// motorB.forward();

		
	}

	public void draaiLinksTest() {

		System.out.println("Draai links\n en Stop\n");
		System.out.println("Druk een key om te starten");

		// draai links door het linker motor achteruit laten draaien
		motorA.forward();
		motorB.backward();

		// voer draai uit
		motorA.setPower(50);
		motorB.setPower(50);

		// pas tijd aan om een 90% draai te krijgen
		Delay.msDelay(DUUR_DRAAI);

		motorA.stop();
		motorB.stop();

		// // zet linker motor terug naar voowaarts bewegen
		// motorA.forward();
		// motorB.forward();

		// // motor resources vrijgeven
		// motorA.close();
		// motorB.close();
		//

	}
	
	public void close () {
		motorA.close();
		motorB.close();
	}

	// public void draaiInCirkel() {
	// // zet motors op verschillende power instellingen. Moet aangepast worden om
	// een cirkel beweging te doen.
	// motorA.setPower(70);
	// motorB.setPower(30);
	//
	// }
	
	public void stop() {
		motorA.stop();
		motorB.stop();
		motorA.close();
		motorB.close();
	}

}
