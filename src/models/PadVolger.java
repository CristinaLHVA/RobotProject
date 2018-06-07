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
	
	public Verplaatsen getVerplaatsen() {
		return verplaatsen;
	}

	public PadVolger() {
		this.padSensor = new ColorTools(SensorPort.S1);
		verplaatsen = new Verplaatsen();
		verplaatsen.motorPower(50, 50);
		padSensor.setMode("Red");
	}
	
	public void leesLicht() {
		intensiteit = padSensor.getRed();	
	}
	
	public void rijPad() {
		if(intensiteit < 0.25) {
			verplaatsen.draaiRechts();
			leesLicht();
		}
		if(intensiteit > 0.5) {
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
	
	public void rijNaarPad() {
		while(intensiteit > 0.5) {
			verplaatsen.rijVooruit();
		//	Delay.msDelay(200);
			leesLicht();
		}		
	}
	
	public void stop() {
		verplaatsen.motorPower(0, 0);
		verplaatsen.rijVooruit();
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
