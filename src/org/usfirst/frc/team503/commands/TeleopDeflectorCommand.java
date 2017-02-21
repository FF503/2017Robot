
package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.subsystems.DeflectorSubsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleopDeflectorCommand extends Command {

    public TeleopDeflectorCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    /*	if(OI.getDPADUp()){
    		DeflectorSubsystem.getInstance().setMotorPower(0.2);
    	}
    	else if(OI.getDPADDown()){
    		DeflectorSubsystem.getInstance().setMotorPower(-0.2);
    	}
    	else{
    		DeflectorSubsystem.getInstance().setMotorPower(0);                 
		}  */
    	DeflectorSubsystem.getInstance().setMotorPower(OI.getOperatorLeftYValue());
    	DeflectorSubsystem.getInstance().resetEncoder();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !DriverStation.getInstance().isOperatorControl();
    }

    // Called once after isFinished returns true
    protected void end() {
    	DeflectorSubsystem.getInstance().setMotorPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}