package models;
import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;

/**
 *  author: Renke/Bastiën
 */
import tools.ColorTools;

public class PadVolger extends TakenModule implements Runnable {
	
	private ColorTools padSensor;
	private double intensiteit;
	private Verplaatsen verplaatsen;
	private int vermogenBocht;
	private int vermogenRechtdoor;
	private double maxLicht;
	private int vanPadTimer;
	private int spiraalTimer;

	public static final int INTENSITEITSPRIMER = 200;
	public static final int MAX_POWER = 100;
	public static final double MAX_DONKER = 0.25; //maximale hoeveelheid donker voor we het zwart noemen
										//Hiertussen ligt de sweetspot waar Robbie continu naar moet streven
	public static final double MIN_LICHT = 0.42; //de minste hoeveelheid intensiteit voor we het wit noemen
	public static final int ACHTERUITPOWER = 100;
	public static final int MAX_TIJD_VAN_PAD = 10000;

	public PadVolger() {
		this.padSensor = new ColorTools(SensorPort.S1);
		this.verplaatsen = new Verplaatsen();
		this.verplaatsen.motorPower(70, 70);
		this.padSensor.setMode("Red");
		this.vanPadTimer = MAX_TIJD_VAN_PAD;
		this.spiraalTimer = 0;
	}
	
	public void voerUit() {
//		System.out.println("Calibreer wit");
//		Button.ENTER.waitForPress();
		leesLicht();
		this.maxLicht = intensiteit;
		System.out.printf("Witcalibratie = %f\n", intensiteit);
//		System.out.println("Druk op enter bij de start");
//		Button.ENTER.waitForPress();
		System.out.println("Druk omhoog om te stoppen");
		while (Button.UP.isUp()){
			setVermogenBocht(30);
			setVermogenRechtdoor(30);
			rij();
		}
		System.out.println("vanPadTimer: " + vanPadTimer);
		System.out.println("spiraalTimer: " + spiraalTimer);
		Button.ENTER.waitForPress();
		stop();
	}
	
	public void leesLicht() {
		intensiteit = padSensor.getRed();	
	}
	

	public void rij () {
		if (vanPadTimer >= MAX_TIJD_VAN_PAD) {
			zoekPad();
		}
		else rijPad();
	}
	
	public void zoekPad() {
		leesLicht();
		if (intensiteit > MIN_LICHT) {
			spiraalTimer++;
			verplaatsen.motorPower((int) (((-2/Math.pow(spiraalTimer, 5)) + 1) * vermogenBocht), vermogenBocht);
			verplaatsen.rijVooruit();
		}
		else {
			spiraalTimer = 0;
			vanPadTimer = 0;
		}
		
	}
	
	public void rijPad() {
		leesLicht();
		if(intensiteit < MAX_DONKER) {
			verplaatsen.motorPower(vermogenBocht, (int)((vermogenBocht/MAX_POWER) * ((intensiteit/ MAX_DONKER) * (MAX_POWER+ACHTERUITPOWER)) - ACHTERUITPOWER));
			verplaatsen.rijVooruit();
			vanPadTimer = 0;
		}
		if(intensiteit > MIN_LICHT) {
			verplaatsen.motorPower((int)((vermogenBocht/MAX_POWER)-((((intensiteit-MIN_LICHT)/(maxLicht-MIN_LICHT)) * (MAX_POWER+ACHTERUITPOWER)) - ACHTERUITPOWER)), vermogenBocht);
			verplaatsen.rijVooruit();
			if (intensiteit > vanPadLicht()) {
				vanPadTimer ++;
			}
			else vanPadTimer = 0;
		}
		else {
			verplaatsen.motorPower(vermogenRechtdoor, vermogenRechtdoor);
			verplaatsen.rijVooruit();
			vanPadTimer = 0;
		}
	}
	
	// deze method zou ervoor moeten kunnen zorgen dan Robbie weer richting het pad gaat. Nog niet gebruikt/getest
	// deze method zouden we kunnen gebruiken om te starten
	public void rijNaarPad() {
		while(intensiteit > MIN_LICHT) {
			verplaatsen.rijVooruit();
			leesLicht();
		}		
	}
	
	public void stop() {
		verplaatsen.stop();
		padSensor.close();
		
	}
	
	public void printLicht() {
		leesLicht();
		System.out.println(intensiteit);
	}
	
	public void setVermogenBocht(int vermogen) {
		this.vermogenBocht = vermogen;
	}

	public void setVermogenRechtdoor(int vermogenRechtdoor) {
		this.vermogenRechtdoor = vermogenRechtdoor;
	}
	
	public double getIntensiteit() {
		return intensiteit;
	}
	
	public double vanPadLicht() {
		return (MIN_LICHT + maxLicht*3)/4;
	}

	@Override
	public void run() {
		voerUit();
	}
	
	
	
}
