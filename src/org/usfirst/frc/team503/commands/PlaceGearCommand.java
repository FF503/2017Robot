package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.subsystems.GearIntakeSubsystem;
import org.usfirst.frc.team503.utils.Constants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PlaceGearCommand extends Command {
	private Timer timer;
    public PlaceGearCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    	GearIntakeSubsystem.getInstance().setGearIntakeMotorPower(0.8);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get()>Constants.GEAR_DEPLOY_TIME;
    }

    // Called once after isFinished returns true
    protected void end() {
    	GearIntakeSubsystem.getInstance().setGearIntakeMotorPower(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
