package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.subsystems.DeflectorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ResetDeflectorCommand extends Command {

    public ResetDeflectorCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(DeflectorSubsystem.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	DeflectorSubsystem.getInstance().setMotorPower(0.1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	DeflectorSubsystem.getInstance().resetEncoder();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return DeflectorSubsystem.getInstance().getLimitSwitch();
    }

    // Called once after isFinished returns true
    protected void end() {
    	DeflectorSubsystem.getInstance().setMotorPower(0.0);
    	DeflectorSubsystem.getInstance().resetEncoder();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
