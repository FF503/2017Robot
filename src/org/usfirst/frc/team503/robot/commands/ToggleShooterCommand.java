package org.usfirst.frc.team503.robot.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.RobotMap;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.robot.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleShooterCommand extends Command {
	ShooterSubsystem shooter;
	
    public ToggleShooterCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	RobotState.getInstance().setShooterStatus(false);
    	requires(ShooterSubsystem.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (OI.getShooter()){
			if (RobotState.getInstance().getShooterStatus()){
				ShooterSubsystem.getInstance().setMotorPower(0);
				RobotState.getInstance().setShooterStatus(false);
			}
			else{
				ShooterSubsystem.getInstance().setMotorPower(1.0);
				RobotState.getInstance().setShooterStatus(true);
			}
    	}
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
