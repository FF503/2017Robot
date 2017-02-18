package org.usfirst.frc.team503.robot.commands; 
                                       
import org.usfirst.frc.team503.robot.OI;


import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.subsystems.TurretSubsystem;
import org.usfirst.frc.team503.robot.utils.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TeleopTurretCommand extends Command {
	                                                                                           

    public TeleopTurretCommand() {
    	requires(TurretSubsystem.getInstance());
    	//requires(Robot.TurretSubsystem);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);                                                                     
    	
    }
    // Called just before this Command runs the first time
    protected void initialize(){
    }

    // Called repeatedly when this Command is scheduled to run                                          
    protected void execute() {
    	if(Math.abs(OI.getOperatorRightXValue())>Constants.JOYSTICK_TOLERANCE){
        	TurretSubsystem.getInstance().setMotorPower(OI.getOperatorRightXValue());
    	}
    	else{
    		TurretSubsystem.getInstance().setMotorPower(0);
    	}
    	
    	TurretSubsystem.getInstance().resetEncoder();
    	
    	/*if(OI.isPOVLeft()){
    		TurretSubsystem.getInstance().setMotorPower1(0.3);
    	}else if(OI.isPOVRight()){
    		TurretSubsystem.getInstance().setMotorPower1(-0.3);
    	}else{
    		TurretSubsystem.getInstance().setMotorPower1(0);                 
    		}  */
    	//SmartDashboard.putNumber("POV value", OI.getPOV());
    
    }
  
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !DriverStation.getInstance().isOperatorControl();
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
