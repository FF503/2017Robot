package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.subsystems.IndexerSubsystem;
import org.usfirst.frc.team503.subsystems.ShooterSubsystem;
import org.usfirst.frc.team503.utils.Constants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShootSequenceCommand extends Command {
	double startTime, currTime;
    public ShootSequenceCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	ShooterSubsystem.getInstance().setSetpoint(Constants.SHOOTER_SPEED);
    	RobotState.getInstance().setShooterStatus(true);
    	startTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(ShooterSubsystem.getInstance().isOnTarget()){
    		IndexerSubsystem.getInstance().setMotorPower(0.6 * Robot.bot.REVERSE_INDEXER);
			RobotState.getInstance().setIndexerStatus(true);
    	}
    	currTime = Timer.getFPGATimestamp();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return OI.getIndexer();
    }

    // Called once after isFinished returns true
    protected void end() {
    	ShooterSubsystem.getInstance().setMotorPower(0);
    	IndexerSubsystem.getInstance().setMotorPower(0);
    	RobotState.getInstance().setShooterStatus(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

}
