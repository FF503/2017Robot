package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.subsystems.GearPlacerSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleGearPlacerFront extends Command {
	private static boolean justOpen; 
    public ToggleGearPlacerFront(boolean justOpen) {
    	this.justOpen = justOpen; 
    	requires(GearPlacerSubsystem.getInstance());
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	if(OI.getGearButtonFront() || OI.getPlaceGear()){
    		if((justOpen)||(RobotState.getInstance().getGearPlacerFront()==false)){
        		GearPlacerSubsystem.getInstance().moveGearClampBackOpen();
        		RobotState.getInstance().setGearPlacerFront(true);
    		}
    		else{
        		GearPlacerSubsystem.getInstance().moveGearClampBackClose();
        		RobotState.getInstance().setGearPlacerFront(false);
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
    	end();
    }
}
