package org.usfirst.frc.team503.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Solenoid;



public class RobotHardwarePracticeBot extends RobotHardware {	
	
	/***
	 * NEVER CHANGE PORT NUMBERS UNLESS YOU ARE ABSOLUTLY SURE 
	 */
	public final int leftMasterID = 3; //front left
	public final int leftSlaveID = 1;	//back left
	public final int rightMasterID = 4;	//front right
	public final int rightSlaveID  = 2; //back right
	public final int shooterID = 5;
	public final int turretID = 6;
	public final int deflectorID = 7; 
	public final int lowerIntakeID = 0;
	public final int upperIntakeID = 4;
	public final int indexerID = 3;
	public final int gearIntakeID = 5;
	public final int driveSolenoidID1 = 0;
	public final int driveSolenoidID2 = 1;
	public final int liftGearSolenoidID1 = 4;
	public final int liftGearSolenoidID2 = 5;
	public final int extendGearSolenoidID1 = 2;
	public final int extendGearSolenoidID2 = 3;
	public final int climberSpark1Port = 1;
	public final int climberSpark2Port = 2;
	public final int leftUltrasonicPort = 1;     //was 1
	public final int rightUltrasonicPort = 0;    // was 2
	public final int ultrasonicDIOPort = 0;
	public final int gearIRPort = 4;	
	public final int gearLEDPort = 7;
	public final boolean hasLowGoalLight = true;
	public final int lowGoalLightPort = 6;
	
	private final double TURRET_DIAMETER = 13.0;
	public final double TURRET_ROTATIONS_IN_RANGE = 12.203857421875;
	public final double TURRET_TOLERANCE = 0.75;
	public final double TURRET_TEETH_BETWEEN_LIMIT_SWITCHES = 8.5;
	public final double TURRET_TOTAL_TEETH = 156.0;
	public final double TURRET_DEGREES_BETWEEN_LIMIT_SWITCHES = TURRET_TEETH_BETWEEN_LIMIT_SWITCHES/TURRET_TOTAL_TEETH * 360;
	public final double TURRET_DEGREES_IN_RANGE = 360 - TURRET_DEGREES_BETWEEN_LIMIT_SWITCHES;
	public final double TURRET_DEGREES_PER_ROTATION = TURRET_DEGREES_IN_RANGE/TURRET_ROTATIONS_IN_RANGE;
	public final double TURRET_CYCLE_TIME = .005; //.005 seconds

	public final double REVERSE_UPPER_INTAKE = 1.0;
	
	public final double WHEEL_DIAMETER = 4.0;
	public final double WHEEL_BASE = 32.5;
	public final double CYCLE_TIME = 0.01;	 
	public final double INCHES_PER_ROTATION = WHEEL_DIAMETER * Math.PI;
	public final int DRIVE_COUNTS_PER_REV = 512;
	public final double DRIVE_PULSES_PER_REV = DRIVE_COUNTS_PER_REV * 16.0;
	public final double INCHES_PER_COUNT = INCHES_PER_ROTATION/DRIVE_PULSES_PER_REV;
	public final double DRIVE_P = 0.00018;//0.00015;
	public final double DRIVE_I = 0.0000000; //0.0
	public final double DRIVE_D = 6.5; //1.6
	
	public final double POSITION_P = 0.017;//0.00015;
	public final double POSITION_I = 0.0000000; //0.0
	public final double POSITION_D = 0.025;//6.5; //1.6
	public final double LEFT_DRIVE_F  = 0.2157; //.2157
	public final double RIGHT_DRIVE_F = 0.2154; //.2154
	
	public final boolean REVERSE_LEFT_SENSOR = true;
	public final boolean REVERSE_RIGHT_SENSOR = false;
	public final boolean REVERSE_LEFT_OUTPUT = false;
	public final boolean REVERSE_RIGHT_OUTPUT = true;
	
	public final double SHOOT_P = 1.8; //0.7
	public final double SHOOT_I = 0.0; //0.0
	public final double SHOOT_D = 90.0; //100.0
	public final double SHOOT_F = 0.0188;
	public final double SHOOT_TOLERANCE = 50.0;
	
	public final double minAutonDriveTurnPower = 0.05; //0.05
	public final double maxAutonDriveTurnPower = 0.09; //0.1
	public final double ULTRASONIC_CONTROL_P = 140.0;
	public final double AUTON_DRIVE_P = 50;
	public final double DRIVE_COEF = 1.0;
	
	public final double DEFLECTOR_P = .035;
	public final double DEFLECTOR_I = 0.00004;
	public final double DEFLECTOR_D = 0.5;
	public final double DEFLECTOR_MAX_COUNTS = 8.71630859375;
	public final double DEFLECTOR_MIN_ANGLE = 17.0; 
	public final double DEFLECTOR_MAX_ANGLE = 41.0;
	public final double DEFLECTOR_COUNTS_PER_DEGREE = DEFLECTOR_MAX_COUNTS/(DEFLECTOR_MAX_ANGLE - DEFLECTOR_MIN_ANGLE);
	public final double DEFLECTOR_TOLERANCE = 1.0;
	public final boolean DEFLECTOR_REVERSE_SENSOR = true;

	public double GYRO_P = 0.037;     //was 0.035
	public double GYRO_I = 0.0;		//was 0.0
	public double GYRO_D = 0.0600;		//was 0.06
	public double GYRO_TOLERANCE = 1.0;
	
	public final double DISTANCE_BETWEEN_ULTRASONICS = 13.7;
	public final double LEFT_ULTRASONIC_VOLTS_PER_INCH = 0.01020952;                   //was 0.229492164 / 25; 
	public final double RIGHT_ULTRASONIC_VOLTS_PER_INCH = 0.00935872;                       //was 0.218505837 / 25;
	
	public final double REVERSE_INDEXER = -1.0;
	
	public double TURRET_P = 0.38;
	public double TURRET_I = 0.0;
	public double TURRET_D = 5.0;	
	
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
	public boolean hasGearLED(){
		return true;
	}
	
	public boolean hasDriveCamera(){
		return false;
	}
	
	@Override
	public boolean hasGearIR(){
		return true;
	}
	
	@Override 
	public boolean hasGearPlacer(){
		return false;
	}
	
	@Override 
	public boolean hasGearIntake(){
		return true;
	}
	
	@Override 
	public boolean hasClimber(){
		return true;
	}
	
	@Override
	public String getName(){
		return "PracticeBot";
	}
}
