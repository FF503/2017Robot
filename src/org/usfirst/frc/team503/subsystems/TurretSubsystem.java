package org.usfirst.frc.team503.subsystems;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.turret.TurretThread;

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

	private static double kTurretKp = 0.40;
	private static double kTurretKi = 0.0;
	private static double kTurretKd = 8.0;
	//private static double rotationsOffset = 11.39501953125;
	private static double kTurretDiameter = 13.0;
	public static double degreesBetweenLimitSwitches = 87.692307;
	public static double degreesInRange = 360 - degreesBetweenLimitSwitches;
	public static double kRotationsInRange = 9.05712890625;
	public static double kTurretDegreesPerRotation =  degreesInRange / kRotationsInRange;
	public static double kTurretOnTargetTolerance = 1.0;
	public static double kTurretMaxDegrees = degreesInRange;
	public static double kTurretMinDegrees = 0;
	  
	private TurretThread turretThread;

	
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
		if(Math.abs(targetAngle)>degreesInRange) {
			System.out.println("BAD TURRET SETPOINT");
			SmartDashboard.putString("BAD TURRET SETPOINT","CANNOT ATTAIN");
		}								
		else{
			SmartDashboard.putNumber("Turret setpoint", targetAngle / kTurretDegreesPerRotation);
		    turretMotor.changeControlMode(CANTalon.TalonControlMode.Position);
			turretMotor.setSetpoint(targetAngle / kTurretDegreesPerRotation);
			RobotState.getInstance().setTurretStatus(true);
		}
	}																										
	
	public synchronized double getAngle() {
		return turretMotor.getPosition() * kTurretDegreesPerRotation;
	}
	
	public synchronized double getSetpoint() {
	    return setpoint;
	}
	
	public synchronized double getError() {    
		return getSetpoint() - getAngle();
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
		 resetEncoder();
		 
		 if(getLeftLimitSwitch() && turretPower > 0){
				turretMotor.set(0);	
		 }
		 else if(getRightLimitSwitch() && turretPower < 0){
				turretMotor.set(0);
		 }
		 else{
			turretMotor.set(turretPower);    
		 }
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
	
	public void sendDashboardData(){
		SmartDashboard.putBoolean("Turret Right Limit", TurretSubsystem.getInstance().getRightLimitSwitch());
		SmartDashboard.putBoolean("Turret Left Limit", TurretSubsystem.getInstance().getLeftLimitSwitch());
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
