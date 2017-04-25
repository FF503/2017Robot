package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.subsystems.GyroSubsystem;
import org.usfirst.frc.team503.utils.Constants;

import com.ctre.CANTalon.TalonControlMode;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team503.subsystems.GyroSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArcDriveCommand extends Command {
	private double inchesLeg1;
	private double inchesArc1;
	private double inchesLeg2;
	private double inchesTotal; 
	private double inchesThruArc1;
	private double angleTotal; 	
	private double anglePerInch;
	private double timeout;
	private boolean reverse;
	private double d;
	
	private double currYaw; 
	Timer time;
	private double pMult;

	private double pK; 
	private double pMax;
	private double tolerance; 
	private double pRight; 
	private double pLeft;
	private double thetaSetPoint;
	private double thetaErr;
	private double lastD;
	private double pTurn; 
	private double turnRate;
		
	//For testing - inchesLeg1=30, inchesArc=30, anglePerInch=2, angleTotal = 60, inchesLeg2=12, timeout=4, reverse=false 
    public ArcDriveCommand(double inchesLeg1, double angleTotal1, double inchesLeg2, double anglePerInch, double timeout, boolean reverse) {
    	this.inchesLeg1 = inchesLeg1; 
		this.angleTotal = angleTotal1;    	
		if(angleTotal1<0){
    		this.anglePerInch = -anglePerInch;
		}
		else{
    		this.anglePerInch = anglePerInch;	
		}
    	this.inchesArc1 = Math.abs(angleTotal1/anglePerInch);
    	this.inchesLeg2 = inchesLeg2;
    	this.timeout = timeout;
    	this.reverse = reverse;
    	time = new Timer();
    	GyroSubsystem.resetGyro();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	GyroSubsystem.resetGyro();
    	time.start();
    	DrivetrainSubsystem.getInstance().percentVoltageMode();
     	DrivetrainSubsystem.getInstance().resetController(); 
    	DrivetrainSubsystem.getInstance().resetEncoders();
    	thetaSetPoint = 0;
    	pK = Robot.bot.ARC_DRIVE_P; 
    	pMax = Robot.bot.ARC_DRIVE_MAX;		 				    // maximum power 
    	tolerance = Robot.bot.ARC_GYRO_TOLERANCE;
    	pTurn = Robot.bot.ARC_TURN_P;
    	inchesThruArc1 = inchesLeg1 + inchesArc1; 
    	inchesTotal = inchesLeg1 + inchesArc1 + inchesLeg2; 
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Get distance traveled 
    	if(reverse){
        	d = -DrivetrainSubsystem.getInstance().getAvgEncRotations() * Robot.bot.INCHES_PER_ROTATION;
    	}
    	else{
        	d = DrivetrainSubsystem.getInstance().getAvgEncRotations() * Robot.bot.INCHES_PER_ROTATION;
    	}
    	
    	//test for which leg of the process we are on and set turn angle  
    	//if(d < inchesLeg1) {
    	//	sa = 0;
    	//	pRight = pMax; 
    	//	pLeft = pMax; 
    	//	
    	//} else if(d < inchesThruArc) {
    	//	sa = anglePerInch;
    	//	pRight = pMax - pTurn; 
    	//	pLeft = pMax + pTurn;
    	//} else {
    	//	sa = 0;
    	//	pRight=pMax/2.0;
    	//	pLeft = pMax/2.0; 
    	//}
    	currYaw = GyroSubsystem.gyro.getYaw();
        	
    	if(d<inchesLeg1) {
    		thetaSetPoint = 0;
    	}
    	else if(d<inchesThruArc1){
        	thetaSetPoint = thetaSetPoint + anglePerInch * (d - lastD);
        	if(angleTotal>0 && thetaSetPoint > angleTotal){
        		thetaSetPoint = angleTotal;
        	}
        	else if(angleTotal < 0 && thetaSetPoint < angleTotal) {
        		thetaSetPoint = angleTotal;
        	}
    	}
    	else{
    		thetaSetPoint = angleTotal;
    	}
    	
    	//Calculate our own PID	
    	//currYaw = thetaSetPoint *.9;
    	//for testing 
    	//if(d < inchesLeg1) {
    	//	currYaw = 0;
    	//} else if(d < inchesThruArc) {
    	//	currYaw = anglePerInch;
    	//} else {
    	//	currYaw = 0;
    	//}
    	//End testing 
    	
        	
    	thetaErr = currYaw - thetaSetPoint;	
    	//pMult = Math.pow(Kp,Math.abs(thetaErr));
    	if(d>inchesLeg1 && d<inchesThruArc1){
    		pMult = pTurn*Math.abs(thetaErr);
    	}
    	else{
    		pMult = pK*Math.abs(thetaErr);
    	}
    	if(pMult>1){
    		pMult = 1;
    	}
    	//calculate motor power - check for saturation (i.e. <-1 or >1)
    	if(reverse){
        	if(thetaErr > tolerance) {					// if error greater than deadband 
        		pRight = pMax*(1-pMult);					
        		pLeft = pMax;				
        	} 
        	else if(thetaErr < -tolerance) {			
        		pRight = pMax;			
        		pLeft = pMax*(1-pMult);							
        	} 
        	else {
        		pRight = pMax; 					
        		pLeft = pMax;					
        	}
    	}
    	else{
        	if(thetaErr > tolerance) {					// if error greater than deadband 
        		pRight = pMax;					
        		pLeft = pMax*(1-pMult);				
        	} 
        	else if(thetaErr < -tolerance) {			
        		pRight = pMax*(1-pMult);			
        		pLeft = pMax;							
        	} 
        	else {
        		pRight = pMax; 					
        		pLeft = pMax;					
        	}
    	}
    	
    	//Drive and include a turn (pidvalue = speed, targetAngle = turn degrees)  
        DrivetrainSubsystem.getInstance().tankDrive(-pLeft, -pRight, reverse);
        lastD = d;
      
        sendDashboardData();  
    }
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    //	return DrivetrainSubsystem.getInstance().getAvgEncDistance() > inchesTotal || time.get()>timeout;
    	return d>inchesTotal;
    }

    // Called once after isFinished returns true
    protected void end() {
    	DrivetrainSubsystem.getInstance().tankDrive(0, 0, false);
    	DrivetrainSubsystem.getInstance().resetController();
    	System.out.format("%s\n", "ArcDriveCommand Ended...");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    protected void sendDashboardData(){  		  	   	
    	System.out.format("%s", "Theta setpoint: " + thetaSetPoint);
    	System.out.format("%s", " Theta Error: " + thetaErr);
    	System.out.format("%s", " Current YAW: " + currYaw);
    	System.out.format("%s", " Distance traveled: " + d);
    	System.out.format("%s", " Left power: " + pLeft);
    	System.out.format("%s", " Right power: " + pRight);
    //	System.out.format("%s\n", "Is Finished: " + (DrivetrainSubsystem.getInstance().getAvgEncDistance() > inchesTotal));
    	System.out.format("%s", " Is Finished: " + (d>inchesTotal));
    	System.out.format("%s\n", "");
    	SmartDashboard.putNumber("Theta SetPoint", thetaSetPoint);
		SmartDashboard.putNumber("Theta Error", thetaErr);
		SmartDashboard.putNumber("Current Yaw", currYaw);
		SmartDashboard.putNumber("Distance travelled",d);
		SmartDashboard.putNumber("Left Power", pLeft);
		SmartDashboard.putNumber("Right Power", pRight);
		SmartDashboard.putBoolean("On Target", DrivetrainSubsystem.getInstance().getAvgEncDistance() > inchesTotal);
   }
}