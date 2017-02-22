package org.usfirst.frc.team503.subsystems;

import org.usfirst.frc.team503.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private CANTalon shooterMotor;
		 
	public ShooterSubsystem() {
		shooterMotor = new CANTalon(Robot.bot.shooterID);
		shooterMotor.enableBrakeMode(true);
		shooterMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		shooterMotor.changeControlMode(TalonControlMode.PercentVbus);
		shooterMotor.setProfile(0);
		shooterMotor.setPID(Robot.bot.SHOOT_P, Robot.bot.SHOOT_I, Robot.bot.SHOOT_D);
		shooterMotor.setF(Robot.bot.SHOOT_F);
	}
	
	private static ShooterSubsystem instance = new ShooterSubsystem();
	
	public static ShooterSubsystem getInstance(){
		return instance;
	}
	
	public void setMotorPower(double motorPower){
		shooterMotor.changeControlMode(TalonControlMode.PercentVbus);
		shooterMotor.set(motorPower);
	}
	
	public void setSetpoint(double rpm){
		shooterMotor.changeControlMode(TalonControlMode.Speed);
		shooterMotor.set(rpm);
	}

	public double getPosition(){
		return shooterMotor.getPosition();
	}
	
	public double getSpeed() {
		return shooterMotor.getSpeed();
	}
	
	public double getError(){
		return shooterMotor.getClosedLoopError();
	}
	
	public boolean isOnTarget(){
		return (shooterMotor.getControlMode() == TalonControlMode.Speed && getError() < Robot.bot.SHOOT_TOLERANCE);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

