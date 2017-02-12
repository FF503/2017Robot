package org.usfirst.frc.team503.robot.subsystems;

import org.usfirst.frc.team503.robot.Robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IndexerSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
	private Spark indexerMotor;
		 
	public IndexerSubsystem() {
		if(Robot.bot.hasIndexer()){
			indexerMotor = new Spark(Robot.bot.indexerID);
		}
	}
	
	private static IndexerSubsystem instance = new IndexerSubsystem();
	
	public static IndexerSubsystem getInstance(){
		return instance;
	}
	
	public void setMotorPower(double x){
		if(Robot.bot.hasIndexer()){
			indexerMotor.set(x);
		}
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

