package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.RobotMap;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.subsystems.IntakeSubsystem;
import org.usfirst.frc.team503.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ToggleIntakeCommand extends Command {
	
    public ToggleIntakeCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(IntakeSubsystem.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (OI.getIntake()) {
			if (RobotState.getInstance().getIntakeStatus()){
				IntakeSubsystem.getInstance().setMotorPower(0.0);
				RobotState.getInstance().setIntakeStatus(false);
			} else {
				IntakeSubsystem.getInstance().setMotorPower(-1.0);
				RobotState.getInstance().setIntakeStatus(true);
			}
    	} 
    	
    	if (OI.getIntakeReverse()) {
    		if (RobotState.getInstance().getIntakeStatus()){
    			//intake is running - turn it off 
    			IntakeSubsystem.getInstance().setMotorPower(0.0);
    			RobotState.getInstance().setIntakeStatus(false);
    		} else {
    			IntakeSubsystem.getInstance().setMotorPower(1.0);
    			RobotState.getInstance().setIntakeStatus(true);
    		}
    	}
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