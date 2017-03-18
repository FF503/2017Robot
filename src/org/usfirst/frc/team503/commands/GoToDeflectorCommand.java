package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.robot.RobotState.ShootingPresets;
import org.usfirst.frc.team503.subsystems.DeflectorSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GoToDeflectorCommand extends Command {
	double angle;
    public GoToDeflectorCommand(double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.angle = angle;
    	requires(DeflectorSubsystem.getInstance());
    }

 
	// Called just before this Command runs the first time
    protected void initialize() {
    	DeflectorSubsystem.getInstance().setSetpoint(angle);
    	SmartDashboard.putBoolean("Deflector on target", false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	DeflectorSubsystem.getInstance().resetEncoder();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return DeflectorSubsystem.getInstance().isOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	DeflectorSubsystem.getInstance().setMotorPower(0);
    	SmartDashboard.putBoolean("Deflector on target", true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
