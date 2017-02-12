package org.usfirst.frc.team503.robot.subsystems;

import org.usfirst.frc.team503.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
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

	static CANTalon turretMotor;  
	
	private static double kTurretKp = 0.15;
	private static double kTurretKi = 0.0;
	private static double kTurretKd = 0.0;
	private static double turretDiameter = 20.125;
	private static double degreesOffset = 78.58122431931282;
	private static double degreesBetweenMagnets = 64.6671663510227;
	private static double degreesBetweenLimitSwitches = 360 - 337.6136;
	private static double degreesPerRotation = 360 - degreesBetweenMagnets;
	private static double kTicksPerRotation = 61486;
	private static double kTurretDegreesPerTick =  degreesPerRotation / kTicksPerRotation;
	private static int kTurretOnTargetTolerance = 1;
	private static double kSoftMaxTurretAngle = degreesPerRotation/2;																			
	private static double kSoftMinTurretAngle = -degreesPerRotation/2;
	private static double kTurretSafeTolerance = 2.0;
	
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
		turretMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		turretMotor.setAllowableClosedLoopErr((int)(kTurretOnTargetTolerance /kTurretDegreesPerTick));
	}                 
	
	
	private static TurretSubsystem instance = new TurretSubsystem();                                         
	                                              
	public static TurretSubsystem getInstance(){                                          
		return instance;
	}

	public synchronized void setSetpoint(double targetAngle) {
		SmartDashboard.putNumber("turret target angle", targetAngle);
		
		if(Math.abs(targetAngle+degreesOffset)>kSoftMaxTurretAngle) {
			System.out.println("BAD TURRET SETPOINT");
			SmartDashboard.putString("BAD TURRET SETPOINT","CANNOT ATTAIN");
		}								
		else{
		    turretMotor.changeControlMode(CANTalon.TalonControlMode.Position);
			turretMotor.setSetpoint((targetAngle+degreesOffset) / kTurretDegreesPerTick);
		}
	}																										
	
	public synchronized double getAngle() {
		return getEncoderPosition() * kTurretDegreesPerTick - degreesOffset;
	}
	
	public synchronized double getSetpoint() {
	    return turretMotor.getSetpoint() * kTurretDegreesPerTick;
	}
	
	public synchronized double getError() {    
		return turretMotor.getClosedLoopError() * kTurretDegreesPerTick;
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
	            getError()) < kTurretSafeTolerance);
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
	}
	
	public boolean getLeftLimitSwitch(){                 
		return turretMotor.isFwdLimitSwitchClosed();
	}
	public boolean getRightLimitSwitch(){
		return turretMotor.isRevLimitSwitchClosed();
	}
 	
	public double getEncoderVelocity(){
		return turretMotor.getEncVelocity();
	}
	
	public double getEncoderPosition(){
		return -turretMotor.getEncPosition();
	}

	private void resetEncAtRightLimitSwitch(){ 
		turretMotor.setEncPosition((int)(kSoftMaxTurretAngle / kTurretDegreesPerTick));   
	}		
	                               
	private void resetEncAtLeftLimitSwitch(){
		turretMotor.setEncPosition((int)(kSoftMinTurretAngle / kTurretDegreesPerTick));   
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
