package org.usfirst.frc.team503.subsystems;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

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
		deflectorMotor.enableLimitSwitch(true, false);
		deflectorMotor.ConfigFwdLimitSwitchNormallyOpen(true);
		deflectorMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		deflectorMotor.setProfile(0);
		deflectorMotor.setPID(Robot.bot.DEFLECTOR_P, Robot.bot.DEFLECTOR_I, Robot.bot.DEFLECTOR_D);  
		deflectorMotor.reverseSensor(Robot.bot.DEFLECTOR_REVERSE_SENSOR);
		deflectorMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		deflectorMotor.setForwardSoftLimit(0.0);
		deflectorMotor.setReverseSoftLimit(-Robot.bot.DEFLECTOR_MAX_COUNTS);
		deflectorMotor.enableReverseSoftLimit(true);
		deflectorMotor.enableForwardSoftLimit(false);
		//deflectorMotor.setAllowableClosedLoopErr((int)(kTurretOnTargetTolerance /kTurretDegreesPerTick));
	}                 
	
	
	private static DeflectorSubsystem instance = new DeflectorSubsystem();                                         
	                                              
	public static DeflectorSubsystem getInstance(){                                          
		return instance;
	}

	public synchronized void setSetpoint(double target) {
		SmartDashboard.putNumber("deflector target angle", target);
		if(target>Robot.bot.DEFLECTOR_MAX_ANGLE) {
			System.out.println("BAD DEFLECTOR SETPOINT");
			SmartDashboard.putString("BAD DEFLECTOR SETPOINT","CANNOT ATTAIN");
		}								
		else{
		    deflectorMotor.changeControlMode(CANTalon.TalonControlMode.Position);
		    deflectorMotor.clearIAccum();
			deflectorMotor.setSetpoint((target-Robot.bot.DEFLECTOR_MIN_ANGLE) * Robot.bot.DEFLECTOR_COUNTS_PER_DEGREE * -1.0);
		}
	}					
	
	public synchronized double getSetpoint() {
	    return -deflectorMotor.getSetpoint();
	}
	
	public synchronized double getError() {    
		return getSetpoint() - getPosition();
	}
	
	public synchronized boolean isOnTarget() {
	    return (deflectorMotor.getControlMode() == CANTalon.TalonControlMode.Position && Math.abs(getError()) < (Robot.bot.DEFLECTOR_TOLERANCE * Robot.bot.DEFLECTOR_COUNTS_PER_DEGREE));
	}
										
	public synchronized void setMotorPower(double deflectorPower){
		deflectorMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		SmartDashboard.putNumber("deflectorPower", deflectorPower);
		 if(getLimitSwitch() && deflectorPower > 0){
	//			deflectorMotor.set(0);	
				resetEncoder();
		 }
		 else{
			 deflectorMotor.set(deflectorPower);
		 }
	}
	
	public boolean getLimitSwitch(){                 
		return deflectorMotor.isFwdLimitSwitchClosed();
	}
	
	public double getPosition(){
		return -deflectorMotor.getPosition();
	}
	
	public double getAngle(){
		return (getPosition() / Robot.bot.DEFLECTOR_COUNTS_PER_DEGREE) + Robot.bot.DEFLECTOR_MIN_ANGLE;
	}
	                               
	public void resetEncoder(){												
    	if(getLimitSwitch()){
    		deflectorMotor.setPosition(0);
    	}
	}
	
	public void sendDashboardData(){
		SmartDashboard.putBoolean("Deflector limit switch", DeflectorSubsystem.getInstance().getLimitSwitch());
		SmartDashboard.putNumber("Deflector encoder", DeflectorSubsystem.getInstance().getPosition());
		SmartDashboard.putNumber("Deflector error", DeflectorSubsystem.getInstance().getError());
		SmartDashboard.putNumber("Deflector setpoint", DeflectorSubsystem.getInstance().getSetpoint());
		SmartDashboard.putNumber("Deflector angle", getAngle());
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}