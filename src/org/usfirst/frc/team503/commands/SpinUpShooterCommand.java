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
public class SpinUpShooterCommand extends Command {
	double startTime, currTime;
	boolean startHint, autonStart;
	
    public SpinUpShooterCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(ShooterSubsystem.getInstance());  	
    }
    


    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	ShooterSubsystem.getInstance().setSetpoint(RobotState.getInstance().getShooterPreset().rpm);
    	RobotState.getInstance().setShooterStatus(true);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
   
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return ShooterSubsystem.getInstance().isOnTarget();
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