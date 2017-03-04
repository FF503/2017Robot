package org.usfirst.frc.team503.subsystems;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.utils.UltrasonicSensor;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class UltrasonicSubsystem extends Subsystem {
	private static UltrasonicSensor leftUltrasonic;
	private static UltrasonicSensor rightUltrasonic;
	
//	public final double diff = leftUltrasonic.getDistance() - rightUltrasonic.getDistance();

	public UltrasonicSubsystem(){
			leftUltrasonic = new UltrasonicSensor(Robot.bot.leftUltrasonicPort);
			rightUltrasonic = new UltrasonicSensor(Robot.bot.rightUltrasonicPort);
	}
	private static UltrasonicSubsystem instance = new UltrasonicSubsystem();                                        
    
	public static UltrasonicSubsystem getInstance(){                                          
		return instance;
	}
	public double getLeftUltrasonicDistance(){
		return leftUltrasonic.getDistance();
	}
	public double getLeftUltrasonicVoltage(){
		return leftUltrasonic.getVoltage();
	}
	public double getRightUltrasonicVoltage(){
		return rightUltrasonic.getVoltage();
	}
	public double getRightUltrasonicDistance(){
		return rightUltrasonic.getDistance();
	}
	
	public double PreferWhichUltrasonic(){
		if(leftUltrasonic.getDistance() > rightUltrasonic.getDistance()){
			return leftUltrasonic.getDistance();
		}else{
			return rightUltrasonic.getDistance();
		}
	}
		
	public void sendDashboardData(){
		SmartDashboard.putNumber("Right ultrasonic distance", getRightUltrasonicDistance());
		SmartDashboard.putNumber("Left ultrasonic voltage", getLeftUltrasonicVoltage());
		SmartDashboard.putNumber("Right ultrasonic voltage", getRightUltrasonicVoltage());		
		SmartDashboard.putNumber("Left ultrasonic distance", getLeftUltrasonicDistance());	
	}
		
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}





