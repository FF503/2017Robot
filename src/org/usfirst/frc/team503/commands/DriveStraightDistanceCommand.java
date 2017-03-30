/*package org.usfirst.frc.team503.robot.commands;

import org.usfirst.frc.team503.robot.RobotMap;
import org.usfirst.frc.team503.robot.subsystems.NavSensorSubsystem;
import org.usfirst.frc.team503.robot.subsystems.NewDrivetrainSubsystem;
import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team503.subsystems.GyroSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraightDistanceCommand extends Command {
	private double inches;
	private double initAngle;
	private double timeout;
	Timer time;
	
    public DriveStraightDistanceCommand(double inches, double timeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.inches = inches;
    	this.timeout = timeout;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	time = new Timer();
    	time.start();
    	
    	DrivetrainSubsystem.getInstance().setSetpoint(inches);
    	DrivetrainSubsystem.getInstance().enable();  
    	SmartDashboard.putNumber("Setpoint=", inches);
        GyroSubsystem.resetGyro();
    	
    	initAngle=0;
    //	NewDrivetrainSubsystem.instance.tankDrive(-RobotMap.AUTON_DRIVE_SPEED, -RobotMap.AUTON_DRIVE_SPEED, false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("pidOutput=", RobotMap.drivePIDOutput);
    	SmartDashboard.putNumber("encoder kounts", NewDrivetrainSubsystem.instance.getEncoderCount());
    	SmartDashboard.putNumber("HOW FAR DID IT GO??????", NewDrivetrainSubsystem.instance.getEncoderDistance());
    	SmartDashboard.putNumber("GETYAW HAHAHAHA",NavSensorSubsystem.ahrs.getYaw() );
    	
    	if(NavSensorSubsystem.ahrs.getYaw()>(initAngle+RobotMap.COMPASS_TOLERANCE)){
    		NewDrivetrainSubsystem.instance.tankDrive((-RobotMap.drivePIDOutput*3.0/4),-RobotMap.drivePIDOutput, false);
    	}
    	else if(NavSensorSubsystem.ahrs.getYaw()<(initAngle-RobotMap.COMPASS_TOLERANCE)){
    		NewDrivetrainSubsystem.instance.tankDrive(-RobotMap.drivePIDOutput, (-RobotMap.drivePIDOutput *3/4), false);
    	}
    	else{
    		NewDrivetrainSubsystem.instance.tankDrive(-RobotMap.drivePIDOutput, -RobotMap.drivePIDOutput, false);
    	}
		//NewDrivetrainSubsystem.instance.tankDrive(-RobotMap.drivePIDOutput, -RobotMap.drivePIDOutput, false);
    	NavSensorSubsystem.instance.sendDashboardData();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       boolean wearedone = false; 
    	// if actual distance traveled on encoders > than set point we have reached destination  
    //	if (NewDrivetrainSubsystem.driveEncoder.getDistance() >= NewDrivetrainSubsystem.instance.getSetpoint()-1.0) 
    	if (NewDrivetrainSubsystem.instance.onTarget()) {
    	    wearedone = true; 
    	} else {
    		wearedone = false; 
    	}
    	
    	if(time.get()> timeout){
    		wearedone = true;
    	}
    	//SmartDashboard.putBoolean("Drive On target?", wearedone);
    	//return NewDrivetrainSubsystem.instance.onTarget();
    	//return RobotMap.drivePIDOutput < .06;
    	return wearedone; 
        //return NewDrivetrainSubsystem.instance.isEncoderStopped();

    }

    // Called once after isFinished returns true
    protected void end() {
    	//SmartDashboard.putString("At End", "Yes");
    	NewDrivetrainSubsystem.instance.tankDrive(0, 0, false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
*/