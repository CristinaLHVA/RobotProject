package models;

import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import tools.InfraroodTools;

public class Handler {

	InfraroodTools irSensor;
	Verplaatsen beweeg;

	public Handler() {
		// TODO Auto-generated constructor stub
		this.irSensor = new InfraroodTools(SensorPort.S2);
		// irSensor.getSensor().getRemoteCommand(1);
	}

	public void testRun() {
		beweeg = new Verplaatsen();
		irSensor.setMode(0);
		System.out.printf("%d",irSensor.getMode());
//		float[] range = new float[irSensor.getRanges().length];
//		for (int i = 0; i < range.length; i++) {
//			if (range[i] > 10) {
//				beweeg.motorPower(30, 30);
//				beweeg.rijVooruit();
//				Delay.msDelay(5000);
//			} else {
//			beweeg.motorPower(0, 0);
//			beweeg.rijVooruit();
//			}
//		}
	}
}
