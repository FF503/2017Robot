package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.subsystems.IndexerSubsystem;
import org.usfirst.frc.team503.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleShooterCommand extends Command {

    public ToggleShooterCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	RobotState.getInstance().setShooterStatus(false);
    	requires(ShooterSubsystem.getInstance());
    }

    private static ToggleShooterCommand instance = new ToggleShooterCommand();
	
	public static ToggleShooterCommand getInstance(){
		return instance;
	}
	
    // Called just before this Command runs the first time
    protected void initialize() {
    	if (OI.getShootEndButton()){
			if (RobotState.getInstance().getShooterStatus()){
				ShooterSubsystem.getInstance().setMotorPower(0.0);
				RobotState.getInstance().setShooterStatus(false);
			}
			else{
				ShooterSubsystem.getInstance().setMotorPower(1);
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
