package org.usfirst.frc.team503.subsystems;

import org.usfirst.frc.team503.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */


public class GearIntakeSubsystem extends Subsystem {
	private DoubleSolenoid extendGearSolenoid;
	private DoubleSolenoid liftGearSolenoid;
	private Spark gearIntakeMotor;
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
	
	public void setGearIntakeMotorPower(double power){
		gearIntakeMotor.set(power);
	}
	
	
    public void initDefaultCommand() {
       
    }
}

