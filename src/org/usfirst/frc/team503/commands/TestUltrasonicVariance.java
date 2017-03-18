package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team503.subsystems.GyroSubsystem;
import org.usfirst.frc.team503.subsystems.UltrasonicSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestUltrasonicVariance extends Command {
	double minLeft, maxLeft, curValLeft, maxRight, minRight, curValRight;
	
    public TestUltrasonicVariance() {
        // Use requires() here to declare subsystem dependencies
        // eg. requ ires(chassis);
    	requires(UltrasonicSubsystem.getInstance());
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	minLeft = 10000000;
    	maxLeft = -1;
    	minRight = minLeft;
    	maxRight = maxLeft;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	curValLeft = UltrasonicSubsystem.getInstance().getLeftUltrasonicDistance();
    	curValRight = UltrasonicSubsystem.getInstance().getLeftUltrasonicDistance();
    	
    	if (curValLeft > maxLeft){
    		maxLeft = curValLeft;
    	}
    	else if (curValLeft < minLeft){
    		minLeft = curValLeft;
    	}
    	
    	if (curValRight > maxRight){
    		maxRight = curValRight;
    	}
    	else if (curValRight < minRight){
    		minRight = curValRight; 
    	}
    	
    	SmartDashboard.putNumber("Right Ultrasonic variance", maxRight - minRight);
    	SmartDashboard.putNumber("Left Ultrasonic variance", maxLeft - minLeft);
    }
    	

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	 
    	return false;
    }	

    protected void end(){
    	System.out.println("finished auton drive command");
    	DrivetrainSubsystem.getInstance().tankDrive(0, 0, false);
    }
    
    protected void interrupted(){
    	end();
    }
}

