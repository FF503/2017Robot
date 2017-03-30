package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.subsystems.DeflectorSubsystem;
import org.usfirst.frc.team503.subsystems.IndexerSubsystem;
import org.usfirst.frc.team503.subsystems.IntakeSubsystem;
import org.usfirst.frc.team503.subsystems.ShooterSubsystem;
import org.usfirst.frc.team503.subsystems.TurretSubsystem;
import org.usfirst.frc.team503.utils.Constants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    	requires(DeflectorSubsystem.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	DeflectorSubsystem.getInstance().setSetpoint(RobotState.getInstance().getShooterPreset().deflectorAngle);
    	ShooterSubsystem.getInstance().setSetpoint(RobotState.getInstance().getShooterPreset().rpm);
    	RobotState.getInstance().setShooterStatus(true);
    	RobotState.getInstance().setTurretState(RobotState.TurretState.TAKING_HINT);
    	SmartDashboard.putBoolean("Deflector on target", false);
    	startTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	DeflectorSubsystem.getInstance().resetEncoder();
    	SmartDashboard.putBoolean("Deflector on target", DeflectorSubsystem.getInstance().isOnTarget());
		if(ShooterSubsystem.getInstance().isOnTarget() && DeflectorSubsystem.getInstance().isOnTarget()){
			if(TurretSubsystem.getInstance().getThread().getPiAlive() ){
				if(RobotState.getInstance().getState() == RobotState.State.AUTON){
					if(RobotState.getInstance().getTurretIsLocked()){
						shoot();
					}
				}
				else{
					shoot();
				}
			}
			else {
				shoot();
			}
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
    	ShooterSubsystem.getInstance().setMotorPower(0.0);
    	IndexerSubsystem.getInstance().setMotorPower(0.0);
    	IntakeSubsystem.getInstance().setMotorPower(0.0, 0.0);
    	DeflectorSubsystem.getInstance().setMotorPower(0.0);
    	RobotState.getInstance().setTurretHint(false);
    	RobotState.getInstance().setShooterStatus(false);
    	RobotState.getInstance().setIntakeStatus(false);
    	RobotState.getInstance().setIndexerStatus(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    private void shoot(){
    	IndexerSubsystem.getInstance().setMotorPower(0.9 * Robot.bot.REVERSE_INDEXER);//was 0.9 for far shots, 0.6 for boiler shot
		IntakeSubsystem.getInstance().setMotorPower(-.75, -1.0);
		RobotState.getInstance().setIndexerStatus(true);
		RobotState.getInstance().setIntakeStatus(true);
    }

}
