package org.usfirst.frc.team503.robot.commands;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.subsystems.ShooterSubsystem;
import org.usfirst.frc.team503.robot.utils.Constants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShootRPMCommand extends Command {

    public ShootRPMCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	ShooterSubsystem.getInstance().setSetpoint(Constants.SHOOTER_SPEED);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return ShooterSubsystem.getInstance().isOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	ShooterSubsystem.getInstance().setSetpoint(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
