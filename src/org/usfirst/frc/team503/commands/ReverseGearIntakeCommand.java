package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.subsystems.GearIntakeSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReverseGearIntakeCommand extends Command {

    public ReverseGearIntakeCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(OI.getReverseGearIntakeButton()){
    		GearIntakeSubsystem.getInstance().setGearIntakeMotorPower(0.8);
    	}    	
    	else{
    		GearIntakeSubsystem.getInstance().setGearIntakeMotorPower(0.0);
    	}
    	if(GearIntakeSubsystem.getInstance().isGearIn()){
    		GearIntakeSubsystem.getInstance().setGearLED(true);
    	}
    	else{
    		GearIntakeSubsystem.getInstance().setGearLED(false);	
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
