package org.usfirst.frc.team503.subsystems;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.turret.TurretThread;
import org.usfirst.frc.team503.utils.Constants;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * 
 *
 */
/**
 * @author Owner
 *
 */
public class TurretSubsystem extends Subsystem {

	private static CANTalon turretMotor;  
	private double setpoint;	  
	private TurretThread turretThread;

	
	public TurretSubsystem(){                               
		turretMotor = new CANTalon(Robot.bot.turretID);                    
		turretMotor.enableBrakeMode(true);
		turretMotor.enableLimitSwitch(true, true);
		turretMotor.ConfigFwdLimitSwitchNormallyOpen(true);        
		turretMotor.ConfigRevLimitSwitchNormallyOpen(true);
		turretMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		turretMotor.setPID(Robot.bot.TURRET_P, Robot.bot.TURRET_I, Robot.bot.TURRET_D);  
		turretMotor.setProfile(0);
		turretMotor.reverseSensor(true);
		turretMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
//		turretMotor.setAllowableClosedLoopErr((int)(kTurretOnTargetTolerance /kTurretDegreesPerRotation));
//		turretMotor.setForwardSoftLimit(kRotationsInRange);
//		turretMotor.setReverseSoftLimit(0.0);
		turretMotor.setVoltageRampRate(6);
	    turretMotor.enableForwardSoftLimit(false);
	    turretMotor.enableReverseSoftLimit(false);
		turretThread = new TurretThread();
	}                 
	
	
	private static TurretSubsystem instance = new TurretSubsystem();                                         
	                                               
	public static TurretSubsystem getInstance(){                                          
		return instance;
	}
	
	public CANTalon getMotor(){
		return turretMotor;
	}
	
	public TurretThread getThread(){
		return turretThread;
	}
	
	public synchronized void setSetpoint(double targetAngle) {
		SmartDashboard.putNumber("turret target angle", targetAngle);
		setpoint = targetAngle;
		if(Math.abs(targetAngle)>Constants.TURRET_DEGREES_IN_RANGE) {
			System.out.println("BAD TURRET SETPOINT");
			SmartDashboard.putString("BAD TURRET SETPOINT","CANNOT ATTAIN");
		}								
		else{
			SmartDashboard.putNumber("Turret setpoint", targetAngle / Constants.TURRET_DEGREES_PER_ROTATION);
		    turretMotor.changeControlMode(CANTalon.TalonControlMode.Position);
			turretMotor.setSetpoint(targetAngle / Constants.TURRET_DEGREES_PER_ROTATION);
			RobotState.getInstance().setTurretStatus(true);
		}
	}																										
	
	public synchronized double getAngle() {
		return turretMotor.getPosition() * Constants.TURRET_DEGREES_PER_ROTATION;
	}
	
	public synchronized double getSetpoint() {
	    return setpoint;
	}
	
	public synchronized double getError() {    
		return getSetpoint() - getAngle();
	}
	
	public synchronized boolean isOnTarget() {
	    return (turretMotor.getControlMode() == CANTalon.TalonControlMode.Position && Math.abs(getError()) < Constants.TURRET_TOLERANCE);
	}								
	

	public synchronized void setMotorPower(double turretPower){
		SmartDashboard.putNumber("turretPower", turretPower);
		turretMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		resetEncoder();
		turretMotor.set(turretPower);    
	}
	
	public boolean getFwdLimitSwitch(){   
		return turretMotor.isFwdLimitSwitchClosed();
	}
	public boolean getRevLimitSwitch(){
		return turretMotor.isRevLimitSwitchClosed();
	}
	
	public synchronized double getPosition(){
		return turretMotor.getPosition();
	}		
	
	public void resetEncoder(){												
		if(TurretSubsystem.getInstance().getRevLimitSwitch()){
			turretMotor.setPosition(0); 
    	}
    	else if(TurretSubsystem.getInstance().getFwdLimitSwitch()){
    		turretMotor.setPosition(Constants.TURRET_ROTATIONS_IN_RANGE);   
    	}
	}
	
	public void sendDashboardData(){
		SmartDashboard.putBoolean("Turret reverse Limit", TurretSubsystem.getInstance().getRevLimitSwitch());
		SmartDashboard.putBoolean("Turret forward Limit", TurretSubsystem.getInstance().getFwdLimitSwitch());
		SmartDashboard.putNumber("Turret get Position", TurretSubsystem.getInstance().getPosition());
		SmartDashboard.putNumber("Turret angle", TurretSubsystem.getInstance().getAngle());
		SmartDashboard.putNumber("Turret error", TurretSubsystem.getInstance().getError());
		SmartDashboard.putBoolean("Turret onTarget", isOnTarget());
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
