package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.subsystems.GearPlacerSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OpenGearPlacerCommand extends Command {

    public OpenGearPlacerCommand() {
    	requires(GearPlacerSubsystem.getInstance());
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	if (RobotState.getInstance().getState() == RobotState.State.TELEOP){
	    	if(OI.getOpenGearMech()){
	       		GearPlacerSubsystem.getInstance().moveGearClampFrontClose();
	       		GearPlacerSubsystem.getInstance().moveGearClampBackClose();
	       		RobotState.getInstance().setGearPlacerBack(true);
	       		RobotState.getInstance().setGearPlacerFront(true);
	    	}
    	}
    	else{
    		GearPlacerSubsystem.getInstance().moveGearClampFrontClose();
       		GearPlacerSubsystem.getInstance().moveGearClampBackClose();
       		RobotState.getInstance().setGearPlacerBack(true);
       		RobotState.getInstance().setGearPlacerFront(true);	
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
    	end();
    }
}
