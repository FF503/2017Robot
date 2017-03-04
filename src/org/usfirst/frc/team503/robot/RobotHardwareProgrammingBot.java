package org.usfirst.frc.team503.robot;

public class RobotHardwareProgrammingBot extends RobotHardware {	
	public static final double SHOOT_TOLERANCE = 0;
	public static final double DEFLECTOR_P = 0;
	public static final double DEFLECTOR_I = 0;
	public static final double DEFLECTOR_D = 0;
	public static final boolean DEFLECTOR_REVERSE_SENSOR = false;
	public static final double DEFLECTOR_TOLERANCE = 0;
	public static final double DEFLECTOR_MAX = 0;
	public static final double TURRET_CYCLE_TIME = 0;
	public static final double TURRET_P = 0;
	public static final double TURRET_I = 0;
	public static final double TURRET_D = 0;
	public static final double REVERSE_INDEXER = 0;
	public final int leftMasterID = 2;  //front left
	public final int leftSlaveID = 1;	//back left
	public final int rightMasterID = 4;	//front right
	public final int rightSlaveID  = 3; //back right
	public final int shooterID = 5;
	public final int intakeID = 0;
	public final int leftUltrasonicPort = 0;
	public final int rightUltrasonicPort = 1;
	
	public final double DISTANCE_BETWEEN_ULTRASONICS = 13.7;
	public final double LEFT_ULTRASONIC_VOLTS_PER_INCH = 0.00966389875;
	public final double RIGHT_ULTRASONIC_VOLTS_PER_INCH = 0.0095621735;
	
	public final double WHEEL_DIAMETER = 8.0;
	public final double WHEEL_BASE = 27;
	public final double CYCLE_TIME = 0.05;
	public final int DRIVE_COUNTS_PER_REV  = 360;

	public final double DRIVE_P = 0.000051;
	public final double DRIVE_I = 0.0000002383;
	public final double DRIVE_D = 0.00001;
	public final double LEFT_DRIVE_F = 1.50220264;
	public final double RIGHT_DRIVE_F = 1.51780415;
	
	public final boolean REVERSE_LEFT_SENSOR = false;
	public final boolean REVERSE_RIGHT_SENSOR = true;
	public final boolean REVERSE_LEFT_OUTPUT = false;
	public final boolean REVERSE_RIGHT_OUTPUT = true;
	
	public final double SHOOT_P = 0;
	public final double SHOOT_I = 0;
	public final double SHOOT_D = 0;
	public final double SHOOT_F = 0;
	
	public int driveSolenoidID1;
	public int driveSolenoidID2;
	public int deflectorID;
	public int outerGearSolenoidID1;
	public int outerGearSolenoidID2;
	public int innerGearSolenoidID1;
	public int innerGearSolenoidID2;
	public int indexerID;
	public int upperIntakeID;
	public int lowerIntakeID;
	public int turretID = 6;
	
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
