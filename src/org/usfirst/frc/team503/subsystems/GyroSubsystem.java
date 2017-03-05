package org.usfirst.frc.team503.subsystems;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GyroSubsystem extends Subsystem implements PIDOutput{

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	public AHRS gyro;
	
	private boolean calibration_complete = false;
	private double turnToAngleRate;
	private PIDController turnController;
	private double angle;
	
	 
	    /* The following PID Controller coefficients will need to be tuned */
	    /* to match the dynamics of your drive system.  Note that the      */
	    /* SmartDashboard in Test mode has support for helping you tune    */
	    /* controllers by displaying a form where you can enter new P, I,  */
	    /* and D constants and test the mechanism.                         */
	    
    public GyroSubsystem() {
    	
    	try { 
    		gyro = new AHRS(SPI.Port.kMXP); 
    	} 
    	catch (RuntimeException ex ) {
    		DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
    		System.out.println("ERROR GETTING GYRO");
    	}
    	
    	/* NavX calibration completed ~15 seconds after it is powered on */
    	/* as long as the robot is still. Hold off using Yaw value until */
    	/* calibration is complete										 */
    	
    	while (!calibration_complete) { 
    		calibration_complete = !gyro.isCalibrating();
    		if (!calibration_complete) { 
    			SmartDashboard.putString("NavX","Calibration in Progress");
    		} 
    		else { 
    			SmartDashboard.putString("NavX","Calibration Completed!");
    			//force reset of yaw value 
    			gyro.zeroYaw(); 
    			
    		}
    	}	    	 	
    	
	    turnController = new PIDController(Robot.bot.GYRO_P, Robot.bot.GYRO_I, Robot.bot.GYRO_D, gyro, this);
	    turnController.setInputRange(-180.0,  180.0);
	    turnController.setOutputRange(-0.8,0.8);
	    turnController.setPercentTolerance(Robot.bot.GYRO_TOLERANCE);
	    turnController.setContinuous(true);
    	turnController.enable();
    }
    
    public void setSetpoint(double angle){
    	turnController.setSetpoint(angle);
    }
    
    public double getSetpoint(){
    	return turnController.getSetpoint();
    }
    
    public double getError(){
    	return gyro.getYaw() - getSetpoint();
    }
    
    public boolean isOnTarget(){
    	return turnController.onTarget();
    }
    
    public void resetGyro(){
    	gyro.reset();
    }
    
    private static GyroSubsystem instance = new GyroSubsystem();
    
    public static GyroSubsystem getInstance(){
    	return instance;
    }
    
    public PIDController getController(){
    	return turnController;
    }
    
    public void sendDashboardData(){
        SmartDashboard.putBoolean(  "NavX Connected",        gyro.isConnected());
        SmartDashboard.putBoolean(  "NavX Calibrating",      gyro.isCalibrating());
        SmartDashboard.putNumber(   "NavX Angle",         	 gyro.getAngle());
        SmartDashboard.putNumber(   "NavX Yaw",              gyro.getYaw());	
        SmartDashboard.putNumber(   "NavX FusedHeading",     gyro.getFusedHeading()); 
        SmartDashboard.putNumber(   "NavX CompassHeading",   gyro.getCompassHeading());	       
        SmartDashboard.putNumber("Turn controller setpoint", getSetpoint());
        SmartDashboard.putNumber("Turn controller error", getError());
    }
	
	@Override
	public void pidWrite(double output) {
		turnToAngleRate = output;
	}
	
	public double getTurnToAngleRate(){
		return turnToAngleRate;
	}
	
	public void initDefaultCommand() {
	    // Set the default command for a subsystem here.
	    //setDefaultCommand(new MySpecialCommand());
	}

}

