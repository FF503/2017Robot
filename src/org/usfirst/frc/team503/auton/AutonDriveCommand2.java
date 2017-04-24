package org.usfirst.frc.team503.auton;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team503.subsystems.GyroSubsystem;
import org.usfirst.frc.team503.subsystems.UltrasonicSubsystem;
import org.usfirst.frc.team503.utils.Constants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutonDriveCommand2 extends Command {
	double angle, difference;
	NetworkTable table;
	double startTime, currTime;
	double turnMultiplier;
	
    public AutonDriveCommand2() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	table = NetworkTable.getTable("LG_Camera");    	
    	SmartDashboard.putBoolean("Auton drive isFinished", false);
    	GyroSubsystem.getInstance().gyro.reset();
    	DrivetrainSubsystem.getInstance().percentVoltageMode();
    	DrivetrainSubsystem.getInstance().setBrakeMode(true);
    	startTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currTime = Timer.getFPGATimestamp();
    	angle = -table.getNumber("Degrees",0.0);
    	SmartDashboard.putNumber("Peg Angle", angle);

    	if(UltrasonicSubsystem.getInstance().getUltrasonicDistance() > Constants.DISTANCE_TO_PEG && angle != 0.0){   //was 36 
    		DrivetrainSubsystem.getInstance().arcadeDrive(.25, angle*.07, false);  // 0.25
    	} 
    	else {
    		DrivetrainSubsystem.getInstance().arcadeDrive(0.25, 0, false);//.2
		}
    }
    	

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	SmartDashboard.putBoolean("Auton drive isFinished",(UltrasonicSubsystem.getInstance().getUltrasonicDistance() < Constants.DISTANCE_TO_PEG));
    	return (UltrasonicSubsystem.getInstance().getUltrasonicDistance()< Constants.DISTANCE_TO_PEG);
    }	

    protected void end(){
    	DrivetrainSubsystem.getInstance().tankDrive(0, 0, false);
    }
    
    protected void interrupted(){
    	end();
    }
}

