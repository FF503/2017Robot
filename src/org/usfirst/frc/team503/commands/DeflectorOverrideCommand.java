package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.subsystems.DeflectorSubsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DeflectorOverrideCommand extends Command {

    public DeflectorOverrideCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(DeflectorSubsystem.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (OI.getDPADUp()){
    		System.out.println("going up");
    		DeflectorSubsystem.getInstance().setSetpoint(DeflectorSubsystem.getInstance().getAngle() + 2.0);
    		System.out.println(DeflectorSubsystem.getInstance().getAngle() + "," + (DeflectorSubsystem.getInstance().getAngle() + 2.0));
    	}
    	else if (OI.getDPADDown()){
    		System.out.println("going down");
    		DeflectorSubsystem.getInstance().setSetpoint(DeflectorSubsystem.getInstance().getAngle() - 2.0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !DriverStation.getInstance().isOperatorControl();
    }

    // Called once after isFinished returns true
    protected void end() {
    	DeflectorSubsystem.getInstance().setMotorPower(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
