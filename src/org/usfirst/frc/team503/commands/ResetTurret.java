package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.subsystems.TurretSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ResetTurret extends Command {

    public ResetTurret() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	TurretSubsystem.getInstance().setMotorPower(-.4);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return TurretSubsystem.getInstance().getRightLimitSwitch();
    }

    // Called once after isFinished returns true
    protected void end() {
    	TurretSubsystem.getInstance().resetEncoder();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
