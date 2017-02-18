package org.usfirst.frc.team503.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class RobotHardware {
	
	public abstract void initialize();
	public abstract String getName();
	//Define objects for motors on this bot 
	
	private CANTalon dummyCANTalon;
	private Talon dummyTalon;
	private Spark dummySpark;
	private DigitalInput dummyDIO;
	private RobotHardware dummyBot;
	private AnalogInput dummyAIO;
	private AnalogAccelerometer dummyAccel;
	private Solenoid dummySolenoid;
	

	public void logSmartDashboard()
	{
		SmartDashboard.putString("Current Robot", Robot.bot.getName());
	}
	
	public CANTalon getCANTalonObj(int CANTalonID) 
	{
	    return dummyCANTalon;  	
	}
	
	public Talon getTalonObj(int TalonID){
		return dummyTalon;
	}
	
	public Spark getSparkObj(int SparkID){
		return dummySpark;
	}
	
	public Solenoid getSolenoidObject(int solenoidID){
		return dummySolenoid;
	}
	
	public DigitalInput getDigitalObj (int DigitalID){
		return dummyDIO;
	}
	
	public AnalogInput getAnalogObj(int AnalogID){
		return dummyAIO;
	}
	
	public AnalogAccelerometer getAccelObj(int AccelID){
		return dummyAccel;
	}

	public boolean usesNavX()
	{
		return false;
	}
	
	public boolean usesCamera()
	{
		return false;
	}
	
	public boolean hasGearPlacer(){
		return false;
	}
	
	public boolean hasClimber(){
		return false;
	}
	
	public boolean usesDriveCamera()
	{
		return false;
	}

	public RobotHardware()
	{
		dummyBot = this;
	}
	
	public RobotHardware getBot()
	{
		return dummyBot;
	}
	
	public void setBot(RobotHardware r)
	{
		dummyBot = r;
	}
}
