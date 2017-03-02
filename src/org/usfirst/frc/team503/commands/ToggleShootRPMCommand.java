package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.subsystems.ShooterSubsystem;
import org.usfirst.frc.team503.utils.Constants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ToggleShootRPMCommand extends Command {

    public ToggleShootRPMCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(OI.getShootRPMButton()){
    		if(RobotState.getInstance().getShooterStatus()){
    			ShooterSubsystem.getInstance().setSetpoint(0);
        		RobotState.getInstance().setShooterStatus(false);
        		System.out.println("shooter status not right");
        	}
        	else{
        		System.out.println("setting output");
        		ShooterSubsystem.getInstance().setSetpoint(Constants.SHOOTER_SPEED);
            	RobotState.getInstance().setShooterStatus(true);
        	}
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
