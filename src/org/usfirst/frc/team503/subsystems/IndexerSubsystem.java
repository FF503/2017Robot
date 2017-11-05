package org.usfirst.frc.team503.subsystems;

import org.usfirst.frc.team503.robot.Robot;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IndexerSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
	private Spark indexerMotor;
	private Spark fourthWallSolenoid;
		 
	public IndexerSubsystem() {
		if(Robot.bot.hasIndexer()){
			indexerMotor = new Spark(Robot.bot.indexerID);
		}
		if(Robot.bot.hasFourthWallSolenoid){
			System.out.println("has spike");
			fourthWallSolenoid = new Spark(Robot.bot.fourthWallSparkID);
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
	
	public void pushFourthWallOut(){
		//fourthWallSpike.set(Value.kOn);
		if(Robot.bot.hasFourthWallSolenoid){
			fourthWallSolenoid.set(1.0);
		}
	}
	
	public void bringFourthWallIn(){
		if(Robot.bot.hasFourthWallSolenoid){
			fourthWallSolenoid.set(0.0);
		}
		//fourthWallSpike.set(Value.kOff);
		//fourthWallSpike.set(Value.kOff);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

