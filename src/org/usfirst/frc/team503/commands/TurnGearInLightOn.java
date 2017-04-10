package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.subsystems.GearIntakeSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnGearInLightOn extends Command {
	double startTime;
    public TurnGearInLightOn() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(GearIntakeSubsystem.getInstance().isGearIn()){
    		GearIntakeSubsystem.getInstance().setGearLED(true);
    	}
    	else{
    		GearIntakeSubsystem.getInstance().setGearLED(false);	
    	}
    	startTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return ((Timer.getFPGATimestamp() - startTime)>5.0) || OI.getGearIntakeButton() || OI.getReverseGearIntakeButton();
    }

    // Called once after isFinished returns true
    protected void end() {
    	GearIntakeSubsystem.getInstance().setGearLED(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
