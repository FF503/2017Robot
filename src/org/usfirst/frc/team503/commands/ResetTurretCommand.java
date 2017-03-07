package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.subsystems.TurretSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ResetTurretCommand extends Command {

    public ResetTurretCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	TurretSubsystem.getInstance().setMotorPower(-.4);
    	TurretSubsystem.getInstance().resetEncoder();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return TurretSubsystem.getInstance().getRevLimitSwitch() || TurretSubsystem.getInstance().getFwdLimitSwitch();
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
