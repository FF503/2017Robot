package org.usfirst.frc.team503.subsystems;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.utils.LVMaxSonarEZ4;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class UltrasonicSubsystem extends Subsystem {
	private static LVMaxSonarEZ4 leftUltrasonic;
	private static LVMaxSonarEZ4 rightUltrasonic;
	
//	public final double diff = leftUltrasonic.getDistance() - rightUltrasonic.getDistance();

	public UltrasonicSubsystem(){
			leftUltrasonic = new LVMaxSonarEZ4(Robot.bot.leftUltrasonicPort);
			rightUltrasonic = new LVMaxSonarEZ4(Robot.bot.rightUltrasonicPort);
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
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}





