package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.robot.RobotState.ShootingPresets;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChangeShooterPresetCommand extends Command {

    public ChangeShooterPresetCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (OI.getIncrementPreset()){
			if (RobotState.getInstance().getShooterPreset().ordinal() == RobotState.ShootingPresets.values().length-1){
				RobotState.getInstance().setShootingPreset(RobotState.ShootingPresets.values()[0]);
			}
			else{
				RobotState.getInstance().setShootingPreset(ShootingPresets.values()[RobotState.getInstance().getShooterPreset().ordinal() + 1]);
			}
		}
		/*else if (OI.getDecrementPreset()){
			if (RobotState.getInstance().getShooterPreset().ordinal() == 0){
				RobotState.getInstance().setShootingPreset(RobotState.ShootingPresets.values()[RobotState.ShootingPresets.values().length-1]);
			}
			else{
				RobotState.getInstance().setShootingPreset(ShootingPresets.values()[RobotState.getInstance().getShooterPreset().ordinal() - 1]);
			}
		}*/
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
    }
}
