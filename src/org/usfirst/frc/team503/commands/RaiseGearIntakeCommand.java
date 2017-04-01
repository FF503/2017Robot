package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.subsystems.GearIntakeSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RaiseGearIntakeCommand extends Command {

    public RaiseGearIntakeCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(GearIntakeSubsystem.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	GearIntakeSubsystem.getInstance().raiseGearMech();
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
