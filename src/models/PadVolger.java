package models;
import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;
import lejos.utility.Delay;
/**
 *  author: Renke
 */
import tools.ColorTools;

public class PadVolger extends TakenModule {
	
	ColorTools padSensor;
	float intensiteit;
	Verplaatsen verplaatsen;
	int vermogen;
	static final int maxIntensiteit = 60;
	static final int intensiteitsPrimer = 200;
	static final double maxDonker = 0.25;
	static final double minLicht = 0.42;
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
		if(intensiteit < maxDonker) {
			verplaatsen.draaiRechts();
			leesLicht();
		}
		if(intensiteit > minLicht) {
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
		verplaatsen.motorPower(((int)(intensiteit * vermogen)), (int)((maxIntensiteit - intensiteit * 100) * vermogen / 100));
		verplaatsen.rijVooruit();
	}
	
	public void rijPadDelta() {
		leesLicht();
		if(intensiteit < maxDonker) {
			verplaatsen.motorPower(vermogen, (int)((vermogen/100) * (intensiteit * intensiteitsPrimer / maxDonker) - 100));
			verplaatsen.rijVooruit();
		}
		if(intensiteit > minLicht) {
			verplaatsen.motorPower((int)((vermogen/100)-((((intensiteit-minLicht)/(maxLicht-minLicht)) * intensiteitsPrimer) - 100)), vermogen);
			verplaatsen.rijVooruit();
		}
		else {
			verplaatsen.motorPower(vermogen, vermogen);
			verplaatsen.rijVooruit();
		}
	}
	
	public void rijNaarPad() {
		while(intensiteit > minLicht) {
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
