package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team503.subsystems.GyroSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class GyroTurnCommand extends Command {
	NetworkTable table;
	double angle;
    public GyroTurnCommand(double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);   	
    	this.angle = angle;
    }
    public GyroTurnCommand(double angle, boolean relativeToCamera){
    	table = NetworkTable.getTable("LG_Camera");
    	double camOffset = table.getNumber("Degrees",0.0);
    	
    	if (relativeToCamera){
    		if (angle != 0.0){
    			this.angle = angle - camOffset;
    		}
    		else{
    			this.angle = angle;
    		}
    		
    	}
    	else{
    		this.angle = angle;
    	}
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("in gyro");
    	GyroSubsystem.getInstance().resetGyro();
    	SmartDashboard.putBoolean("turn on target", false);
	    GyroSubsystem.getInstance().setSetpoint(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	DrivetrainSubsystem.getInstance().tankDrive(-GyroSubsystem.getInstance().getTurnToAngleRate() , GyroSubsystem.getInstance().getTurnToAngleRate() , false);
    	SmartDashboard.putNumber("Gyro PID RotatetoAngle=", GyroSubsystem.getInstance().getTurnToAngleRate());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	SmartDashboard.putBoolean("turn on target", GyroSubsystem.getInstance().isOnTarget());
    	return (GyroSubsystem.getInstance().isOnTarget());
    }

    // Called once after isFinished returns true
    protected void end() {
    	DrivetrainSubsystem.getInstance().tankDrive(0, 0, false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
