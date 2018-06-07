package nl.hva.miw.robot.cohort12;

import lejos.hardware.Brick;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import models.*;
import tools.ColorTools;

public class Marvin {
	
	Brick brick;
	
	public Marvin() {
		super();
		brick = LocalEV3.get();
	}
	
	public static void main(String[] args) {
		Marvin marvin = new Marvin();
		
		marvin.run();
		
	}
	
	private void run() {
		System.out.println("druk op ENTER");
		waitForKey(Button.ENTER);
		PadVolger padVolger = new PadVolger();
//		while (Button.ESCAPE.isUp()) {
//			padVolger.printLicht();
//			waitForKey(Button.ENTER);
//		}
		padVolger.printLicht();
		while (Button.ESCAPE.isUp()){
			padVolger.leesLicht();
			if(padVolger.getIntensiteit() > 0.25 && padVolger.getIntensiteit() < 0.42) {
				padVolger.getVerplaatsen().rijVooruit();
			} else {
				padVolger.setVermogen(50);
				padVolger.rijPadDelta();
			}	
		}
		padVolger.stop();
		System.out.println("Einde Programma");
		waitForKey(Button.ENTER);
	}
	
	public void waitForKey(Key key) {
		while(key.isUp()) {
			Delay.msDelay(100);
		}
		while(key.isDown()) {
			Delay.msDelay(100);
		}
	}
	
	
}
