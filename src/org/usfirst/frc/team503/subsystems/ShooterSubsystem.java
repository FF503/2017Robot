package org.usfirst.frc.team503.subsystems;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotState;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private CANTalon shooterMotor;
		 
	public ShooterSubsystem() {
		shooterMotor = new CANTalon(Robot.bot.shooterID);
		shooterMotor.enableBrakeMode(false);
		shooterMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooterMotor.configEncoderCodesPerRev(1024);
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
	
	public double getSetpoint(){
		return shooterMotor.getSetpoint();
	}
	
	public double getSpeed() {
		return shooterMotor.getSpeed();
	}
	
	public double getCurrent(){
		return shooterMotor.getOutputCurrent();
	}
	
	public double getError(){
		return getSetpoint() - getSpeed();
	}
	
	public boolean isOnTarget(){
		return (shooterMotor.getControlMode() == TalonControlMode.Speed && getError() < Robot.bot.SHOOT_TOLERANCE);
	}
	
	public void sendDashboardData(){
		SmartDashboard.putNumber("Shooter RPM", ShooterSubsystem.getInstance().getSpeed());
		SmartDashboard.putNumber("Shooter position", ShooterSubsystem.getInstance().getPosition());
		SmartDashboard.putNumber("Shooter Current", ShooterSubsystem.getInstance().getCurrent());
		SmartDashboard.putString("Shooting preset", RobotState.getInstance().getShooterPreset().toString());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

