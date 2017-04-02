package org.usfirst.frc.team503.subsystems;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.utils.Constants;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */


public class GearIntakeSubsystem extends Subsystem {
	private DoubleSolenoid extendGearSolenoid;
	private DoubleSolenoid liftGearSolenoid;
	private Spark gearIntakeMotor;
	private AnalogInput gearIRSensor;
	private Solenoid gearLED;
	
	public GearIntakeSubsystem(){
		if (Robot.bot.hasGearIntake()){
			extendGearSolenoid = new DoubleSolenoid(Robot.bot.extendGearSolenoidID1, Robot.bot.extendGearSolenoidID2);
			liftGearSolenoid = new DoubleSolenoid(Robot.bot.liftGearSolenoidID1, Robot.bot.liftGearSolenoidID2);
			gearIntakeMotor = new Spark(Robot.bot.gearIntakeID);
		}
		else{
			extendGearSolenoid = null;
			liftGearSolenoid = null;
		}
		if(Robot.bot.hasGearIR()){
			gearIRSensor = new AnalogInput(Robot.bot.gearIRPort);
			if(Robot.bot.hasGearLED()){
				gearLED = new Solenoid(Robot.bot.gearLEDPort);
			}
		}
	}    
    private static GearIntakeSubsystem instance = new GearIntakeSubsystem();                                        
    
	public static GearIntakeSubsystem getInstance(){                                          
		return instance;
	}
	
	public void raiseGearMech(){
		liftGearSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void lowerGearMech(){
		liftGearSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void extendGearMech(){
		extendGearSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void retractGearMech(){
		extendGearSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public double getIRVoltage(){
		if (Robot.bot.hasGearIR()){
			return gearIRSensor.getVoltage();
		}
		else{
			return 0.0;
		}
		
	}
	
	public boolean isGearIn(){
		return getIRVoltage() > Constants.GEAR_IR_THRESHOLD;
	}
	
	public void setGearLED(boolean on){
		gearLED.set(on);
	}
	
	public void setGearIntakeMotorPower(double power){
		gearIntakeMotor.set(power);
	}
	
	public void sendDashboardData(){
		SmartDashboard.putNumber("Gear IR Voltage", getIRVoltage());
		SmartDashboard.putBoolean("Gear In", isGearIn());
	}
	
    public void initDefaultCommand() {
       
    }
}

