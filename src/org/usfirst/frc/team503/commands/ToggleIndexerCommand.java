package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.subsystems.IndexerSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleIndexerCommand extends Command {

    public ToggleIndexerCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	RobotState.getInstance().setIndexerStatus(false);
    	requires(IndexerSubsystem.getInstance());
    }

    private static ToggleIndexerCommand instance = new ToggleIndexerCommand();
	
	public static ToggleIndexerCommand getInstance(){
		return instance;
	}
	
    // Called just before this Command runs the first time
    protected void initialize() {
    	if (OI.getIndexer()){
			if (RobotState.getInstance().getIndexerStatus()){
				IndexerSubsystem.getInstance().setMotorPower(0.0);
				RobotState.getInstance().setIndexerStatus(false);
			}
			else{
				IndexerSubsystem.getInstance().setMotorPower(0.9 * Robot.bot.REVERSE_INDEXER);
				RobotState.getInstance().setIndexerStatus(true);
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
