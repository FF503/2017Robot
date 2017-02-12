package org.usfirst.frc.team503.robot.subsystems;

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

	CANTalon shooterMotor;
	//CANTalon indexerMotor; //Change to Spark Motor if applicable.
	//CANTalon deflectorMotor; //Change to Bag Motor if applicable.
	
	//Encoder deflectorEncoder;
		 
	public ShooterSubsystem() {
		shooterMotor = new CANTalon(Robot.bot.shooterID);
		shooterMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooterMotor.changeControlMode(TalonControlMode.PercentVbus);
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
	
	public double getSpeed() {
		return shooterMotor.getSpeed();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

