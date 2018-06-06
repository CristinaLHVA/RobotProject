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
//			Delay.msDelay(200);
			leesLicht();
		}
		if(intensiteit > 0.5) {
			verplaatsen.draaiLinks();
//			Delay.msDelay(200);
			leesLicht();
		}
		else {
			verplaatsen.rijVooruit();
//			System.out.println("Is aan het rijden");
	//		Delay.msDelay(200);
			leesLicht();
		}
	}
	
	public void rijNaarPad() {
		while(intensiteit > 0.5) {
			verplaatsen.rijVooruit();
		//	Delay.msDelay(200);
			leesLicht();
		}		
	}

}
