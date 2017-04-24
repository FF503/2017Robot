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
	boolean startHint, autonStart;
    public ShootSequenceCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(ShooterSubsystem.getInstance());
    	requires(IndexerSubsystem.getInstance());
    	requires(DeflectorSubsystem.getInstance());
    	startHint = false;
    	autonStart = false;
    }
    
    public ShootSequenceCommand(boolean autonStart){
    	requires(ShooterSubsystem.getInstance());
    	requires(IndexerSubsystem.getInstance());
    	requires(DeflectorSubsystem.getInstance());
    	startHint = false;
    	this.autonStart = autonStart;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	DeflectorSubsystem.getInstance().setSetpoint(RobotState.getInstance().getShooterPreset().deflectorAngle);
    	ShooterSubsystem.getInstance().setSetpoint(RobotState.getInstance().getShooterPreset().rpm);
		RobotState.getInstance().setTurretState(RobotState.TurretState.TAKING_HINT);
		RobotState.getInstance().setShooterStatus(true);
    	SmartDashboard.putBoolean("Deflector on target", false);
    	startTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    //proof that ankith can't program
    //yeah whatever
    protected void execute() {
    	if(RobotState.getInstance().getState() == RobotState.State.TELEOP){
    		DeflectorSubsystem.getInstance().resetEncoder();
        	SmartDashboard.putBoolean("Deflector on target", DeflectorSubsystem.getInstance().isOnTarget());
        	if(ShooterSubsystem.getInstance().isOnTarget() && DeflectorSubsystem.getInstance().isOnTarget() && RobotState.getInstance().getReadyToFire()){
        		DeflectorSubsystem.getInstance().setMotorPower(0.0);
    			shoot();
        	}
    		if (OI.getDPADUp()){
        		DeflectorSubsystem.getInstance().setSetpoint(DeflectorSubsystem.getInstance().getAngle() + 2.0);
        	}
        	else if (OI.getDPADDown()){
        		DeflectorSubsystem.getInstance().setSetpoint(DeflectorSubsystem.getInstance().getAngle() - 2.0);
        	} 		
    	}
    	else{
           	/*if(RobotState.getInstance().getTurretHint()){
    			startHint = true;
    		}
    		if(RobotState.getInstance().getHasTurretReset() && !startHint){
    		RobotState.getInstance().setTurretState(RobotState.TurretState.TAKING_HINT);
    		}*/
    		SmartDashboard.putBoolean("Deflector on target", DeflectorSubsystem.getInstance().isOnTarget());
    		if(ShooterSubsystem.getInstance().isOnTarget() && DeflectorSubsystem.getInstance().isOnTarget() && RobotState.getInstance().getShooterStatus()){
    			if(TurretSubsystem.getInstance().getThread().getPiAlive()){
    				if(RobotState.getInstance().getTurretIsLocked()){
    					DeflectorSubsystem.getInstance().setMotorPower(0.0);
    					shoot();
    				}
    			}
    			else {
    				DeflectorSubsystem.getInstance().setMotorPower(0.0);
					shoot();
    			}
    		}
    	}
    	currTime = Timer.getFPGATimestamp();
    }
    	
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(RobotState.getInstance().getState() == RobotState.State.TELEOP){
            return OI.getShootRPMButton();
    	}
    	else if(RobotState.getInstance().getState() == RobotState.State.AUTON){
    		return autonStart;
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
    	RobotState.getInstance().setReadyToFire(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    private void shoot(){
    	IndexerSubsystem.getInstance().setMotorPower(0.7 * Robot.bot.REVERSE_INDEXER);//was 0.9 at states, 0.7 for testing
		IntakeSubsystem.getInstance().setMotorPower(-.75, -1.0);
		RobotState.getInstance().setIndexerStatus(true);
		RobotState.getInstance().setIntakeStatus(true);
    }

}
