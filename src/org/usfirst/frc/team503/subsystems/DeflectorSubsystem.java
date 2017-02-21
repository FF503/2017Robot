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
		deflectorMotor.enableLimitSwitch(false, true);
		deflectorMotor.ConfigRevLimitSwitchNormallyOpen(true);
		deflectorMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		deflectorMotor.setProfile(0);
		deflectorMotor.setPID(Robot.bot.DEFLECTOR_P, Robot.bot.DEFLECTOR_I, Robot.bot.DEFLECTOR_D);  
		deflectorMotor.reverseSensor(Robot.bot.DEFLECTOR_REVERSE_SENSOR);
		deflectorMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		//deflectorMotor.setAllowableClosedLoopErr((int)(kTurretOnTargetTolerance /kTurretDegreesPerTick));
	}                 
	
	
	private static DeflectorSubsystem instance = new DeflectorSubsystem();                                         
	                                              
	public static DeflectorSubsystem getInstance(){                                          
		return instance;
	}

	public synchronized void setSetpoint(double target) {
		SmartDashboard.putNumber("deflector target", target);
		
		if(target>Robot.bot.DEFLECTOR_MAX) {
			System.out.println("BAD DEFLECTOR SETPOINT");
			SmartDashboard.putString("BAD DEFLECTOR SETPOINT","CANNOT ATTAIN");
		}								
		else{
		    deflectorMotor.changeControlMode(CANTalon.TalonControlMode.Position);
			deflectorMotor.setSetpoint(target);
		}
	}					
	
	public enum DeflectorHeight{
		LOW(0.0), MEDIUM(3.0), HIGH(6.0);
		public double height;
		private DeflectorHeight(double height){
			this.height = height;
		}
	}
	
	public synchronized double getSetpoint() {
	    return deflectorMotor.getSetpoint();
	}
	
	public synchronized double getError() {    
		return getSetpoint() - getPosition();
	}
	
	public synchronized boolean isOnTarget() {
	    return (deflectorMotor.getControlMode() == CANTalon.TalonControlMode.Position && Math.abs(getError()) < Robot.bot.DEFLECTOR_TOLERANCE);
	}
										
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
		return -deflectorMotor.getPosition();
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
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}