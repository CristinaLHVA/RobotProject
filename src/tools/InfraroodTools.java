package tools;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RangeFinder;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

/**
 * @author Ray this is a library class for the Infrared Sensor module
 *
 */

public class InfraroodTools implements RangeFinder {

	private static final int OFFSET = 0;
	EV3IRSensor sensor;
	SampleProvider sp;
	float[] sample;
	private static final int HALF_SECOND = 500;
	private static final int ITERATION_THRESHOLD = 50;

	/*
	 * Creates Infraredsensor object. This is a wrapper class for EV3IRSensor.
	 * 
	 * @param port SensorPort of EV3IRSensor device
	 */

	public InfraroodTools(Port port) {
		sensor = new EV3IRSensor(port);
	}

	/**
	 * Enable IR sensor.
	 */

	public void setMode(int mode) {
		/*
		 * Supported modes are: 0. Proximity (outputs proximity to an object in %) 1.
		 * Beacon Proximity (outputs proximity in % and heading with values between -25
		 * and 25) 2. Remote (outputs ID of the button pressed)
		 */
		sensor.setCurrentMode(mode);
		if (mode == 0) {
			sp = sensor.getDistanceMode();
		} else if (mode == 1) {
			sp = sensor.getSeekMode();
		} else {
			System.out.printf("No correct mode selected");
		}
		sample = new float[sp.sampleSize()];
	}
	
	

	/**
	 * Returns the underlying EV3IRSensor object.
	 * 
	 * @return Sensor object reference.
	 */
	public EV3IRSensor getSensor() {
		return sensor;
	}

	/**
	 * Get range (distance) to object detected by UltraSonic sensor.
	 * 
	 * @return Distance in meters.
	 */

	@Override
	public float getRange() {
		int distanceValue = 0;
		for (int i = 0; i <= ITERATION_THRESHOLD; i++) {
			sample = new float[sp.sampleSize()];
			sp.fetchSample(sample, OFFSET);
			distanceValue = (int) sample[0];

			System.out.printf("Iteration: %d", i);
			System.out.printf("Distance: %d", distanceValue);

			Delay.msDelay(HALF_SECOND);
		}

		return sample[OFFSET];
	}

	/**
	 * Get range (distance) to object detected by UltraSonic sensor.
	 * 
	 * @return Distance in meters. Only one distance value is returned.
	 */

	@Override
	public float[] getRanges() {
		int distanceValue = 0;
		for (int i = 0; i <= ITERATION_THRESHOLD; i++) {
			sample = new float[sp.sampleSize()];
			sp.fetchSample(sample, OFFSET);
			distanceValue = (int) sample[0];

			System.out.printf("Iteration: %d", i);
			System.out.printf("Distance: %d", distanceValue);

			Delay.msDelay(HALF_SECOND);
		}

		return sample;
	}

	public float[] getBeacon() {
		int bHeading1, bDistance1, bHeading2, bDistance2, bHeading3, bDistance3, bHeading4, bDistance4;

		for (int i = 0; i <= ITERATION_THRESHOLD; i++) {
			sample = new float[sp.sampleSize()];
			sp.fetchSample(sample, OFFSET);

			bHeading1 = (int) sample[0];
			bDistance1 = (int) sample[1];

//			bHeading2 = (int) sample[2];
//			bDistance2 = (int) sample[3];
//
//			bHeading3 = (int) sample[4];
//			bDistance3 = (int) sample[5];
//
//			bHeading4 = (int) sample[6];
//			bDistance4 = (int) sample[7];
			
			System.out.printf("Iteration: %d\n", i);
			System.out.printf("Beacon Channel 1: Heading %d, Distance %d\n", bHeading1, bDistance1);
//			System.out.printf("Beacon Channel 2: Heading %d, Distance %d\n", bHeading2, bDistance2);
//			System.out.printf("Beacon Channel 3: Heading %d, Distance %d\n", bHeading3, bDistance3);
//			System.out.printf("Beacon Channel 4: Heading %d, Distance %d\n", bHeading4, bDistance4);
			
			Delay.msDelay(HALF_SECOND);
		}
		return sample;
	}

	/**
	 * Determine which sensor mode is enabled.
	 * 
	 * @return .
	 */

	public int getMode() {
		return sensor.getCurrentMode();
	}

	/**
	 * Release resources.
	 */
	public void close() {
		sensor.close();
	}

}
