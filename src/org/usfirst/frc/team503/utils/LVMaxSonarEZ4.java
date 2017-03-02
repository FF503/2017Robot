package org.usfirst.frc.team503.utils;
import org.usfirst.frc.team503.robot.Robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class LVMaxSonarEZ4 {
	
	private static enum Sensor {
		LEFT, RIGHT;
	}
	private Sensor side;
	private static double VOLTS_PER_INCH;
	private AnalogInput analogSensor;
	
	public LVMaxSonarEZ4(int port){
		analogSensor = new AnalogInput(port);
		
		if(port == Robot.bot.leftUltrasonicPort) {
			side = Sensor.LEFT;
			VOLTS_PER_INCH = Robot.bot.LEFT_ULTRASONIC_VOLTS_PER_INCH;
		}
		else if(port == Robot.bot.rightUltrasonicPort) {
			side = Sensor.RIGHT;
			VOLTS_PER_INCH = Robot.bot.RIGHT_ULTRASONIC_VOLTS_PER_INCH;
		}
	}

	public double getVoltage()
	{                                                 
		return analogSensor.getVoltage();
	}
	
	public Sensor getSide() {
		return side;
	}
	//This next method has not been tested yet
	//Double check please!
	public double getDistance(){
		return analogSensor.getVoltage()/VOLTS_PER_INCH;
	}
}