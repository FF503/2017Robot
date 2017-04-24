package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.utils.Constants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *@author God
 *@purpose To vibrate the joysticks when a gear is in the intake. This command will win us worlds.
 *
 */
public class RumbleJoystickCommand extends Command {
	double startTime, currTime;
    public RumbleJoystickCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//this command will win us Worlds
    	OI.vibrateDriverController();
    	startTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currTime = Timer.getFPGATimestamp();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (currTime - startTime) > Constants.GEAR_VIBRATE_TIME;
    }

    // Called once after isFinished returns true
    protected void end() {
    	OI.stopVibrateDriverController();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
