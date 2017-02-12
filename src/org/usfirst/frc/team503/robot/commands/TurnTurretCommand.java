package org.usfirst.frc.team503.robot.commands;

import org.usfirst.frc.team503.robot.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnTurretCommand extends Command {
	double currAngle;
	double angle;
	boolean goTo;
	
    public TurnTurretCommand(double angle, boolean goTo) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(TurretSubsystem.getInstance());
    	this.angle = angle;
    	this.goTo = goTo;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	currAngle = TurretSubsystem.getInstance().getAngle();
    	if(goTo){
        	TurretSubsystem.getInstance().setSetpoint(angle);
    	}
    	else{
    		TurretSubsystem.getInstance().setSetpoint(angle+currAngle);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	TurretSubsystem.getInstance().resetEncoder();
    }

	// Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	System.out.println("turn turret finished. is on target");
        return TurretSubsystem.getInstance().isOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	TurretSubsystem.getInstance().setMotorPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
