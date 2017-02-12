package org.usfirst.frc.team503.robot.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.subsystems.DrivetrainSubsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TankDriveCommand extends Command {

    public TankDriveCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(DrivetrainSubsystem.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	DrivetrainSubsystem.getInstance().tankDrive(OI.getLeftYValue(), OI.getRightYValue(),false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !DriverStation.getInstance().isOperatorControl();
    }

    // Called once after isFinished returns true
    protected void end() {
    	DrivetrainSubsystem.getInstance().tankDrive(0, 0, false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
