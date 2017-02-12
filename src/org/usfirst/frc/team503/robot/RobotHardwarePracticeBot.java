package org.usfirst.frc.team503.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Solenoid;



public class RobotHardwarePracticeBot extends RobotHardware {	
	public final int leftMasterID = 3; //front left
	public final int leftSlaveID = 1;	//back left
	public final int rightMasterID = 4;	//front right
	public final int rightSlaveID  = 2; //back right
	public final int shooterID = 7;
	public final int turretID = 6;
	public final int deflectorID = 5;
	public final int lowerIntakeID = 4;
	public final int upperIntakeID = 0;
	public final int indexerID = 3;
	public final int driveSolenoidID1 = 0;
	public final int driveSolenoidID2 = 1;
	public final int outerGearSolenoidID = 2;
	public final int innerGearSolenoidID = 3;
	
	public final double WHEEL_DIAMETER = 4.0;
	public final double WHEEL_BASE = 32.5;
	public final double CYCLE_TIME = 0.05;	
	public final int DRIVE_COUNTS_PER_REV = 512;
	
	public final double DRIVE_P = 0;
	public final double DRIVE_I = 0;
	public final double DRIVE_D = 0;
	public final double LEFT_DRIVE_F = 0.1040480065;
	public final double RIGHT_DRIVE_F = 0.101528384;
	
	public final boolean REVERSE_LEFT_SENSOR = true;
	public final boolean REVERSE_RIGHT_SENSOR = false;
	public final boolean REVERSE_LEFT_OUTPUT = false;
	public final boolean REVERSE_RIGHT_OUTPUT = true;
	
	public final int SHOOT_COUNTS_PER_REV = 1024;
	public final double SHOOT_P = 0;
	public final double SHOOT_I = 0;
	public final double SHOOT_D = 0;
	public final double SHOOT_F = 0;

	
	@Override
	public void initialize(){
	}
	
	public boolean hasSecondIntake(){
		return true;
	}
	
	public boolean hasTurret(){
		return true;
	}
	
	public boolean hasIndexer(){
		return true;
	}
	
	@Override
	public String getName(){
		return "PracticeBot";
	}
}
