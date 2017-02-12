package org.usfirst.frc.team503.robot.subsystems;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotMap;
import org.usfirst.frc.team503.robot.utils.Logger;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Name:		IntakeSubSystem 
 * Purpose:		
 * Author: 		Jayesh Mate
 * Date:		February 10, 2017 
 * Comments:
 */
public class IntakeSubsystem extends Subsystem {
	
	private Spark upperIntakeMotor;
	private Talon lowerIntakeMotor;
	
	public IntakeSubsystem(){
		upperIntakeMotor = new Spark(Robot.bot.upperIntakeID);
		if(Robot.bot.hasSecondIntake()){
			lowerIntakeMotor = new Talon(Robot.bot.lowerIntakeID);
		}
	}
	
	private static IntakeSubsystem instance = new IntakeSubsystem();
	
	public static IntakeSubsystem getInstance(){
		return instance;
	}
	
	public void setMotorPower(double x){
		upperIntakeMotor.set(-x);
		
		if(Robot.bot.hasSecondIntake()) {
			lowerIntakeMotor.set(x);
		}
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here i.e. setDefaultCommand(new MySpecialCommand());
    }
}

