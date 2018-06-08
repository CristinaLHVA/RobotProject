package models;
import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;
import lejos.utility.Delay;
/**
 *  author: Renke/Bastiën
 */
import tools.ColorTools;

public class PadVolger extends TakenModule {
	
	ColorTools padSensor;
	float intensiteit;
	Verplaatsen verplaatsen;
	int vermogen;
	static final int MAX_INTENSITEIT = 60;
	static final int INTENSITEITSPRIMER = 200;
	static final int MAX_POWER = 100;
	static final double MAX_DONKER = 0.25;
	static final double MIN_LICHT = 0.42;
	double maxLicht;
	
	public Verplaatsen getVerplaatsen() {
		return verplaatsen;
	}

	public PadVolger() {
		this.padSensor = new ColorTools(SensorPort.S1);
		verplaatsen = new Verplaatsen();
		verplaatsen.motorPower(70, 70);
		padSensor.setMode("Red");
	}
	
	public void voerUit() {
		System.out.println("Calibreer wit");
		Button.ENTER.waitForPress();
		System.out.printf("Witcalibratie = %f\n", intensiteit);
		this.maxLicht = intensiteit;
		System.out.println("Ga naar de start");
		Button.ENTER.waitForPress();
		while (Button.ESCAPE.isUp()){
			leesLicht();
			setVermogen(50);
			rijPadDelta();
		}	
		stop();
	}
	
	public void leesLicht() {
		intensiteit = padSensor.getRed();	
	}
	
	public void rijPad() {
		if(intensiteit < MAX_DONKER) {
			verplaatsen.draaiRechts();
			leesLicht();
		}
		if(intensiteit > MIN_LICHT) {
			verplaatsen.draaiLinks();
			leesLicht();
		}
		else {
			verplaatsen.rijVooruit();
			leesLicht();
		}
	}
	
	public void rijPadBeta() {
		leesLicht();
		verplaatsen.motorPower(((int)(intensiteit * vermogen)), (int)((MIN_LICHT - intensiteit * MAX_POWER) * vermogen / MAX_POWER));
		verplaatsen.rijVooruit();
	}
	
	public void rijPadDelta() {
		leesLicht();
		if(intensiteit < MAX_DONKER) {
			verplaatsen.motorPower(vermogen, (int)((vermogen/100) * (intensiteit * INTENSITEITSPRIMER / MAX_DONKER) - MAX_POWER));
			verplaatsen.rijVooruit();
		}
		if(intensiteit > MIN_LICHT) {
			verplaatsen.motorPower((int)((vermogen/MAX_POWER)-((((intensiteit-MIN_LICHT)/(maxLicht-MIN_LICHT)) * INTENSITEITSPRIMER) - MAX_POWER)), vermogen);
			verplaatsen.rijVooruit();
		}
		else {
			verplaatsen.motorPower(vermogen, vermogen);
			verplaatsen.rijVooruit();
		}
	}
	
	public void rijNaarPad() {
		while(intensiteit > MIN_LICHT) {
			verplaatsen.rijVooruit();
			leesLicht();
		}		
	}
	
	public void stop() {
		verplaatsen.stop();
		
	}
	
	public void setVermogen(int vermogen) {
		this.vermogen = vermogen;
	}
	
	public void printLicht() {
		leesLicht();
		System.out.println(intensiteit);
	}

	public float getIntensiteit() {
		return intensiteit;
	}
	
	
}
