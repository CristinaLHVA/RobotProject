package models;
import lejos.hardware.port.SensorPort;
/**
 *  author: Renke
 */
import tools.ColorTools;

public class PadVolger {
	
	
	public PadVolger() {
		ColorTools padSensor = new ColorTools(SensorPort.S1);
		padSensor.setMode(2);
	}

}
