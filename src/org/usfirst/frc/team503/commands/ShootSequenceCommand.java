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
    	requires(ShooterSubsystem.getInstance());
    	requires(IndexerSubsystem.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	ShooterSubsystem.getInstance().setSetpoint(RobotState.getInstance().getShooterPreset().rpm);
    	RobotState.getInstance().setShooterStatus(true);
    	startTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(ShooterSubsystem.getInstance().isOnTarget()){
    		IndexerSubsystem.getInstance().setMotorPower(0.6 * Robot.bot.REVERSE_INDEXER);//was 0.9 for far shots, 0.6 for boiler shot
			RobotState.getInstance().setIndexerStatus(true);
    	}
    	currTime = Timer.getFPGATimestamp();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(RobotState.getInstance().getState()==RobotState.State.TELEOP){
            return OI.getEndShoot();
    	}
    	else{
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	ShooterSubsystem.getInstance().setMotorPower(0);
    	IndexerSubsystem.getInstance().setMotorPower(0);
    	RobotState.getInstance().setShooterStatus(false);
    	RobotState.getInstance().setIndexerStatus(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

}
