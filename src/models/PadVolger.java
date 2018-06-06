package models;
import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;
/**
 *  author: Renke
 */
import tools.ColorTools;

public class PadVolger extends TakenModule {
	
	ColorTools padSensor;
	
	public PadVolger() {
		this.padSensor = new ColorTools(SensorPort.S1);
		padSensor.setMode("Red");
	}
	
	public void leesLicht() {
		System.out.println("Ga lezen!");
		Button.waitForAnyPress();
		//padSensor.setFloodLight(Color.RED);
		float intensiteit = padSensor.getRed();	
		System.out.println("Huidige modus: " + padSensor.getSensor().getName());
		System.out.printf("de intensiteit is %f", intensiteit);
	}

}
