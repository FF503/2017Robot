package org.usfirst.frc.team503.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.subsystems.ClimberSubsystem;

/**
 *
 */
public class ReverseDriveTrainCommand extends Command {
	
	public ReverseDriveTrainCommand() {
		// Use requires() here to declare subsystem dependencies
		
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if (OI.getDriverReverseButton()){
			RobotState.getInstance().setDriveTrainReversed(!RobotState.getInstance().getDriveTrainReversed());
			
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
