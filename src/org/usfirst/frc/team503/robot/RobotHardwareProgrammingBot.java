package org.usfirst.frc.team503.robot;

public class RobotHardwareProgrammingBot extends RobotHardware {	
	public final int leftMasterID = 2;  //front left
	public final int leftSlaveID = 1;	//back left
	public final int rightMasterID = 4;	//front right
	public final int rightSlaveID  = 3; //back right
	public final int shooterID = 5;
	public final int intakeID = 0;
	
	public final double WHEEL_DIAMETER = 8.0;
	public final double WHEEL_BASE = 27;
	public final double CYCLE_TIME = 0.05;
	public final int COUNTS_PER_REV = 360;

	public final double DRIVE_P = .00001;
	public final double DRIVE_I = 0;
	public final double DRIVE_D = 0;
	public final double LEFT_DRIVE_F = 1.50220264;
	public final double RIGHT_DRIVE_F = 1.51780415;
	
	public final boolean REVERSE_LEFT_SENSOR = false;
	public final boolean REVERSE_RIGHT_SENSOR = true;
	public final boolean REVERSE_LEFT_OUTPUT = false;
	public final boolean REVERSE_RIGHT_OUTPUT = true;
	
	@Override
	public void initialize(){
	}
	
	public boolean hasSecondIntake(){
		return false;
	}
	
	public boolean hasTurret(){
		return false;
	}
	
	public boolean hasIndexer(){
		return false;
	}
	
	@Override
	public String getName(){
		return "ProgrammingBot";
	}
}
