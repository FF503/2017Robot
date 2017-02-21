package org.usfirst.frc.team503.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 *
 */
public class ClimberSubsystem extends Subsystem {
    // Put methods for controlling this subsystem
    private Spark spark1;
    private Spark spark2; 
    
	public ClimberSubsystem(){
		spark1 = new Spark(1);
		spark2 = new Spark(2);
	}
	private static ClimberSubsystem instance = new ClimberSubsystem();
	
	public static ClimberSubsystem getInstance(){
		return instance;
	}
	public void setPower(double power){
		spark1.set(power);
		spark2.set(power);
	}
    public void initDefaultCommand(){
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}