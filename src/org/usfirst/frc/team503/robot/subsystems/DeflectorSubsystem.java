package org.usfirst.frc.team503.robot.subsystems;

import org.usfirst.frc.team503.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DeflectorSubsystem extends Subsystem {

	CANTalon deflectorMotor;

	public DeflectorSubsystem() {
		deflectorMotor = new CANTalon(Robot.bot.deflectorID);
		deflectorMotor.changeControlMode(TalonControlMode.PercentVbus);
	}
	private static DeflectorSubsystem instance = new DeflectorSubsystem();
	
	public static DeflectorSubsystem getInstance(){
		return instance;
	}
	
	public void setMotorPower(double motorPower){
		deflectorMotor.changeControlMode(TalonControlMode.PercentVbus);
		deflectorMotor.set(motorPower);
	}
	
	public double getSpeed() {
		return deflectorMotor.getSpeed();
	}
	
	public void setSetpoint(){
		deflectorMotor.changeControlMode(TalonControlMode.Position);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

