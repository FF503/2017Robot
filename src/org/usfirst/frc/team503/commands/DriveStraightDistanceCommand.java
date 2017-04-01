package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.subsystems.GyroSubsystem;
import org.usfirst.frc.team503.utils.Constants;

import com.ctre.CANTalon.TalonControlMode;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team503.subsystems.GyroSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraightDistanceCommand extends Command {
	private double inches;
	private double initAngle;
	private double timeout;
	private double pidvalue; 
	private boolean reverse;
	Timer time;
	
    public DriveStraightDistanceCommand(double inches, double timeout, boolean reverse) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	DrivetrainSubsystem.getInstance().resetEncoders();
    	DrivetrainSubsystem.getInstance().percentVoltageMode();
    	this.inches = inches;
    	this.timeout = timeout;
    	this.reverse = reverse;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	time = new Timer();
    	time.start();
    	
    	if(reverse){
    		DrivetrainSubsystem.getInstance().setSetpoint(-inches);
    	}
    	else{
    		DrivetrainSubsystem.getInstance().setSetpoint(inches);
    	}
    	SmartDashboard.putNumber("Position PID Setpoint", inches);
        GyroSubsystem.resetGyro();
    	initAngle = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//go get the output of the PID controller 
		pidvalue = -DrivetrainSubsystem.getInstance().getPidOutput();

    	SmartDashboard.putNumber("Position PID output", pidvalue);
    	SmartDashboard.putNumber("Position PID error", DrivetrainSubsystem.getInstance().getError());
    	SmartDashboard.putBoolean("Position PID On Target", DrivetrainSubsystem.getInstance().pidIsOnTarget());
    	if(reverse){
    		if(GyroSubsystem.gyro.getYaw()>(initAngle+Constants.DRIVE_HEADING_TOLERANCE)){
        		DrivetrainSubsystem.getInstance().tankDrive(pidvalue, pidvalue*0.75, false);
        	}
        	else if(GyroSubsystem.gyro.getYaw()<(initAngle-Constants.DRIVE_HEADING_TOLERANCE)){
        		DrivetrainSubsystem.getInstance().tankDrive(pidvalue*0.75, pidvalue, false);
        	}
        	else{
        		DrivetrainSubsystem.getInstance().tankDrive(pidvalue, pidvalue, false);
        	}
    	}
    	else{
    		if(GyroSubsystem.gyro.getYaw()>(initAngle+Constants.DRIVE_HEADING_TOLERANCE)){
        		DrivetrainSubsystem.getInstance().tankDrive((pidvalue * 0.75),pidvalue, false);
        	}
        	else if(GyroSubsystem.gyro.getYaw()<(initAngle-Constants.DRIVE_HEADING_TOLERANCE)){
        		DrivetrainSubsystem.getInstance().tankDrive(pidvalue, (pidvalue * 0.75), false);
        	}
        	else{
        		DrivetrainSubsystem.getInstance().tankDrive(pidvalue, pidvalue, false);
        	}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return DrivetrainSubsystem.getInstance().pidIsOnTarget() || time.get()>timeout;
    }

    // Called once after isFinished returns true
    protected void end() {
    	DrivetrainSubsystem.getInstance().resetController();
    	DrivetrainSubsystem.getInstance().tankDrive(0, 0, false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
