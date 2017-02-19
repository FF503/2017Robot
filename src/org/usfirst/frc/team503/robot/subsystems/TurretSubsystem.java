package org.usfirst.frc.team503.robot.subsystems;

import org.usfirst.frc.team503.robot.Robot;


import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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
	
	private static double kTurretKp = 0.1246;
	private static double kTurretKi = 0.0;
	private static double kTurretKd = 0.0;
	//private static double rotationsOffset = 11.39501953125;
	private static double kTurretDiameter = 13.0;
	public static double degreesBetweenLimitSwitches = 87.692307;
	public static double degreesInRange = 360 - degreesBetweenLimitSwitches;
	public static double kRotationsInRange = 9.05712890625;
	public static double kTurretDegreesPerRotation =  degreesInRange / kRotationsInRange;
	public static int kTurretOnTargetTolerance = 1;
	public static double kTurretMaxDegrees = degreesInRange;
	public static double kTurretMinDegrees = 0;
	
	
	private double prevDegrees;

	
	public TurretSubsystem(){                               
		turretMotor = new CANTalon(Robot.bot.turretID);                    
		turretMotor.enableBrakeMode(true);
		turretMotor.enableLimitSwitch(true, true);
		turretMotor.ConfigFwdLimitSwitchNormallyOpen(true);        
		turretMotor.ConfigRevLimitSwitchNormallyOpen(true);
		turretMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		turretMotor.setPID(kTurretKp, kTurretKi, kTurretKd);  
		turretMotor.setProfile(0);
		turretMotor.reverseSensor(true);
		turretMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		//turretMotor.setAllowableClosedLoopErr((int)(kTurretOnTargetTolerance /kTurretDegreesPerRotation));
		turretMotor.setForwardSoftLimit(kTurretMaxDegrees / kTurretDegreesPerRotation);
		turretMotor.setReverseSoftLimit(kTurretMinDegrees / kTurretDegreesPerRotation);
		turretMotor.enableForwardSoftLimit(true);
		turretMotor.enableReverseSoftLimit(true);
	
	}                 
	
	
	private static TurretSubsystem instance = new TurretSubsystem();                                         
	                                              
	public static TurretSubsystem getInstance(){                                          
		return instance;
	}
	
	public CANTalon getMotor(){
		return turretMotor;
	}
	
	public synchronized void setSetpoint(double targetAngle) {
		SmartDashboard.putNumber("turret target angle", targetAngle);
		
		if(Math.abs(targetAngle)>degreesInRange) {
			System.out.println("BAD TURRET SETPOINT");
			SmartDashboard.putString("BAD TURRET SETPOINT","CANNOT ATTAIN");
		}								
		else{
			SmartDashboard.putNumber("turret setpoint value", targetAngle / kTurretDegreesPerRotation);
		    turretMotor.changeControlMode(CANTalon.TalonControlMode.Position);
		    SmartDashboard.putBoolean("turret is in position mode", TalonControlMode.Position == turretMotor.getControlMode());
			turretMotor.setSetpoint(targetAngle / kTurretDegreesPerRotation);
		}
	}																										
	
	public synchronized double getAngle() {
		return turretMotor.getPosition() * kTurretDegreesPerRotation;
	}
	
	public synchronized double getSetpoint() {
	    return turretMotor.getSetpoint() * kTurretDegreesPerRotation;
	}
	
	public synchronized double getError() {    
		return turretMotor.getClosedLoopError() * kTurretDegreesPerRotation;
	}
	
	public synchronized boolean isOnTarget() {
	    return (turretMotor.getControlMode() == CANTalon.TalonControlMode.Position && Math.abs(getError()) < kTurretOnTargetTolerance);
	}


	/**
	 * @return If the turret is within its mechanical limits and in the right
	 *         state.
	 */
	public synchronized boolean isSafe() {
	    return (turretMotor.getControlMode() == CANTalon.TalonControlMode.Position && turretMotor.getSetpoint() == 0 && Math.abs(
	            getError()) < kTurretOnTargetTolerance);
	}										
	

	public synchronized void setMotorPower(double turretPower){
		SmartDashboard.putNumber("turrerPower", turretPower);
		
		 turretMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		 
		 if(getLeftLimitSwitch() && turretPower > 0){
				turretMotor.set(0);	
		 }
		 else if(getRightLimitSwitch() && turretPower < 0){
				turretMotor.set(0);
		 }
		 else{
			turretMotor.set(turretPower);    
		 }
		 prevDegrees = getAngle();
	}
	
	public boolean getLeftLimitSwitch(){   
		return turretMotor.isFwdLimitSwitchClosed();
	}
	public boolean getRightLimitSwitch(){
		return turretMotor.isRevLimitSwitchClosed();
	}
	
	public synchronized double getPosition(){
		return turretMotor.getPosition();
	}

	private void resetEncAtRightLimitSwitch(){ 
		turretMotor.setPosition(0); 
	}		
	                               
	private void resetEncAtLeftLimitSwitch(){
		turretMotor.setPosition(kRotationsInRange);   
	}			
	
	public void resetEncoder(){												
		if(TurretSubsystem.getInstance().getRightLimitSwitch()){
    		TurretSubsystem.getInstance().resetEncAtRightLimitSwitch();
    	}
    	else if(TurretSubsystem.getInstance().getLeftLimitSwitch()){
    		TurretSubsystem.getInstance().resetEncAtLeftLimitSwitch();
    	}
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
