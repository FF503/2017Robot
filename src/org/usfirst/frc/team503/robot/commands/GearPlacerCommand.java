package org.usfirst.frc.team503.robot.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.subsystems.GearPlacerSubsystem;
import org.usfirst.frc.team503.robot.subsystems.TurretSubsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearPlacerCommand extends Command {

    public GearPlacerCommand() {
    	requires(GearPlacerSubsystem.getInstance());
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(OI.gearIntakeButton()){
    		GearPlacerSubsystem.getInstance().moveGearClampOuterBackward();
    		GearPlacerSubsystem.getInstance().moveGearClampInnerBackward();
    	}
    	else if(OI.gearCloseButton()){
    		GearPlacerSubsystem.getInstance().moveGearClampOuterForward();
    		GearPlacerSubsystem.getInstance().moveGearClampInnerForward();
    	}
    	
    }
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !DriverStation.getInstance().isOperatorControl();

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
