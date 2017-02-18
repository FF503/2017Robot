package org.usfirst.frc.team503.robot.subsystems;

import org.usfirst.frc.team503.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

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
public class DeflectorSubsystem extends Subsystem {

	private CANTalon deflectorMotor;  
	
	public DeflectorSubsystem(){                               
		deflectorMotor = new CANTalon(Robot.bot.deflectorID);                    
		deflectorMotor.enableBrakeMode(true);
		deflectorMotor.enableLimitSwitch(false, true);
		deflectorMotor.ConfigRevLimitSwitchNormallyOpen(true);
		deflectorMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		//deflectorMotor.setPID(kDeflectorKd, kDeflectorKi, kDeflectorKp);  
		deflectorMotor.setProfile(0);
		deflectorMotor.reverseSensor(true);
		deflectorMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		//deflectorMotor.setAllowableClosedLoopErr((int)(kTurretOnTargetTolerance /kTurretDegreesPerTick));
	}                 
	
	
	private static DeflectorSubsystem instance = new DeflectorSubsystem();                                         
	                                              
	public static DeflectorSubsystem getInstance(){                                          
		return instance;
	}

	/*public synchronized void setSetpoint(double targetAngle) {
		SmartDashboard.putNumber("turret target angle", targetAngle);
		
		if(Math.abs(targetAngle+degreesOffset)>kSoftMaxTurretAngle) {
			System.out.println("BAD TURRET SETPOINT");
			SmartDashboard.putString("BAD TURRET SETPOINT","CANNOT ATTAIN");
		}								
		else{
		    deflectorMotor.changeControlMode(CANTalon.TalonControlMode.Position);
			deflectorMotor.setSetpoint((targetAngle+degreesOffset) / kTurretDegreesPerTick);
		}
	}							
	*/																			
	
	/*public synchronized double getAngle() {
		return getEncoderPosition() * kTurretDegreesPerTick - degreesOffset;
	}
	*/
	
	/*public synchronized double getSetpoint() {
	    return deflectorMotor.getSetpoint() * kTurretDegreesPerTick;
	}
	
	public synchronized double getError() {    
		return deflectorMotor.getClosedLoopError() * kTurretDegreesPerTick;
	}
	
	public synchronized boolean isOnTarget() {
	    return (deflectorMotor.getControlMode() == CANTalon.TalonControlMode.Position && Math.abs(getError()) < kTurretOnTargetTolerance);
	}
*/

	/**
	 * @return If the turret is within its mechanical limits and in the right
	 *         state.
	 */
	/*public synchronized boolean isSafe() {
	    return (deflectorMotor.getControlMode() == CANTalon.TalonControlMode.Position && deflectorMotor.getSetpoint() == 0 && Math.abs(
	            getError()) < kTurretSafeTolerance);
	}										
	*/
	
	public synchronized void setMotorPower(double deflectorPower){
		deflectorMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		SmartDashboard.putNumber("deflectorPower", deflectorPower);
		 if(getLimitSwitch() && deflectorPower > 0){
				deflectorMotor.set(0);	
				resetEncoder();
		 }
		 else{
			deflectorMotor.set(deflectorPower);                                                                     
		 }
	}
	
	public boolean getLimitSwitch(){                 
		return !deflectorMotor.isRevLimitSwitchClosed();
	}
	
	public double getPosition(){
		return deflectorMotor.getPosition();
	}
	                               
	public void resetEncoder(){												
    	deflectorMotor.setPosition(0);
	}


    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}