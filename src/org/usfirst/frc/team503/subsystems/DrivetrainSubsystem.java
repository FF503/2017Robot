package org.usfirst.frc.team503.subsystems;

import org.usfirst.frc.team503.motionProfile.TrapezoidThread;
import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.utils.Constants;
import org.usfirst.frc.team503.utils.SynchronousPID;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DrivetrainSubsystem extends Subsystem{

   // Put methods for controlling this subsystem
   // here. Call these from Commands.
	
	private CANTalon leftMaster, leftSlave, rightMaster, rightSlave;
	private SynchronousPID pidController;
	private DoubleSolenoid driveSolenoid;
	private double talonLeftPos, talonRightPos, talonLeftRPM, talonRightRPM, rightSpeed, leftSpeed, talonLeftRot, talonRightRot;
	private double curTime, difTime, lastTime;         
	//private TrapezoidThread trapThread;
	private static int currentProfileID;
	public boolean profileHasFinished = false;
	private boolean firstLogFileRun = true;
	private double enc; 
  
   public DrivetrainSubsystem() {
	   	leftMaster = new CANTalon(Robot.bot.leftMasterID);
		leftSlave = new CANTalon(Robot.bot.leftSlaveID);
		rightMaster = new CANTalon(Robot.bot.rightMasterID);
		rightSlave = new CANTalon(Robot.bot.rightSlaveID);
		if (!Robot.bot.getName().equals("ProgrammingBot")){
			driveSolenoid = new DoubleSolenoid(Robot.bot.driveSolenoidID1, Robot.bot.driveSolenoidID2);
		}
		
		leftMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rightMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		
		leftMaster.configEncoderCodesPerRev(Robot.bot.DRIVE_COUNTS_PER_REV);
		rightMaster.configEncoderCodesPerRev(Robot.bot.DRIVE_COUNTS_PER_REV);
		
		setDrivePID(Robot.bot.DRIVE_P, Robot.bot.DRIVE_I, Robot.bot.DRIVE_D);
		
		leftMaster.setF(Robot.bot.LEFT_DRIVE_F);
		rightMaster.setF(Robot.bot.RIGHT_DRIVE_F);
		
		leftSlave.changeControlMode(TalonControlMode.Follower);
	   	rightSlave.changeControlMode(TalonControlMode.Follower);
	   	leftSlave.set(Robot.bot.leftMasterID);
	   	rightSlave.set(Robot.bot.rightMasterID);
		
	   leftMaster.reverseSensor(Robot.bot.REVERSE_LEFT_SENSOR);
	   rightMaster.reverseSensor(Robot.bot.REVERSE_RIGHT_SENSOR);
	   leftMaster.reverseOutput(Robot.bot.REVERSE_LEFT_OUTPUT);
	   rightMaster.reverseOutput(Robot.bot.REVERSE_RIGHT_OUTPUT);
	   
	   pidController = new SynchronousPID();
	   pidController.setPID(Robot.bot.POSITION_P, Robot.bot.POSITION_I, Robot.bot.POSITION_D);
	   pidController.setInputRange(-10000000, 10000000);
	   pidController.setOutputRange(-0.9, 0.9);
	   pidController.reset();
	  
	   setBrakeMode(true);
	   	
	   	    	
	  // 	trapThread = new TrapezoidThread(leftMaster, rightMaster);	
   }
   
   public boolean pidIsOnTarget(){
	   return pidController.onTarget(Constants.DRIVE_PID_TOLERANCE);
   }
   
   public double getError(){
	   return pidController.getError();
   }
   
   public double getPidOutput() {
	   return pidController.calculate(getAvgEncCounts() * Robot.bot.INCHES_PER_COUNT);
   }
 
   public double getAvgEncCounts() {
	   return (-rightMaster.getEncPosition() + leftMaster.getEncPosition()) / 2.0;	//right neg for comp, left neg for practice
   }
   
   public double getAvgEncRotations(){
	   return (rightMaster.getPosition() + leftMaster.getPosition()) / 2.0;
   }
   
   public void setSetpoint(double val){
	   pidController.setSetpoint(val);
   }
   
   public void resetController(){
	   pidController.reset();
   }
   
   public void setBrakeMode(boolean value){
	   rightMaster.enableBrakeMode(value);
	   leftMaster.enableBrakeMode(value);	
	   rightSlave.enableBrakeMode(value);
	   leftSlave.enableBrakeMode(value);
   }
   
   public void setDrivePID(double p, double i, double d){
		leftMaster.setPID(p, i, d);
		rightMaster.setPID(p, i, d);
   }
   
   private void waitForTrapezoidalFinish() {
		while(true){
			if(getTrapStatus().equals("finished") && (getTrapID() == currentProfileID)){
				profileHasFinished = true;
				break;
			}
		}
	}
   
   public CANTalon getLeftMaster(){
	   return leftMaster;
   }
   
   public CANTalon getRightMaster(){
	   return rightMaster;
   }
   
   public void runProfileLeftRight(double[][] leftPoints, double[][] rightPoints){
	   leftMaster.enableBrakeMode(true);
	   rightMaster.enableBrakeMode(true);
   		profileHasFinished = false;
		currentProfileID++;
		startTrapezoidControl(leftPoints, rightPoints, currentProfileID);
		waitForTrapezoidalFinish();
	}
   
	private void startTrapezoidControl(double[][] leftPoints, double[][] rightPoints,int trapID) {	
		//trapThread.activateTrap(leftPoints, rightPoints, trapID);
	}
	
	public void stopTrapezoidControl() {
	//	trapThread.resetTrapezoid();
	}
	
	public synchronized int getTrapID(){
		//return trapThread.getID();
		return 0;
	}

	public synchronized String getTrapStatus(){
		//return trapThread.getStatus();
		return null;
	}
   
   public void percentVoltageMode(){
	   leftMaster.changeControlMode(TalonControlMode.PercentVbus);
	   rightMaster.changeControlMode(TalonControlMode.PercentVbus);
	   leftMaster.setVoltageRampRate(9);   
	   rightMaster.setVoltageRampRate(9);  
   }
   
   public void initDefaultCommand() {
       // Set the default command for a subsystem here.
       //setDefaultCommand(new MySpecialCommand());
   }
    
    public void resetEncoders(){
   	 	leftMaster.setEncPosition(0);
		rightMaster.setEncPosition(0);
	}
    
	private static DrivetrainSubsystem instance = new DrivetrainSubsystem();
	
	public static DrivetrainSubsystem getInstance(){
		return instance;
	}
	
	public void setMotorOutputs(double leftSpeed, double rightSpeed, boolean reverse){		
		if(reverse){
			leftMaster.set(leftSpeed);
			rightMaster.set(-rightSpeed);
			SmartDashboard.putNumber("Left motor power", leftSpeed);
			SmartDashboard.putNumber("Right motor power", -rightSpeed);
		}
		else{
			leftMaster.set(-leftSpeed); 
			rightMaster.set(rightSpeed); 
			SmartDashboard.putNumber("Left motor power", -leftSpeed);
			SmartDashboard.putNumber("Right motor power", rightSpeed);
		}
	}
	
	public void shiftGears(boolean high){
		//true is high, false is low  
		if(RobotState.getInstance().getCurrentGear() != high){
			if(high){
				driveSolenoid.set(Value.kForward);
				RobotState.getInstance().setcurrentGear(true); 
			}
			else{
				driveSolenoid.set(Value.kReverse);
				RobotState.getInstance().setcurrentGear(false);
			}
		}
	}
	
   private static double limit(double num) {
       if (num > 1.0) {
           num= 1.0;
       }
       else if (num < -1.0) {
           num= -1.0;
       }
       	return num;
   }
   
    public void backwardsFullVelocity(){
		setMotorOutputs(1,1,false);
	}
	public void forwardsFullVelocity(){
		setMotorOutputs(-1,-1,false);
	}
   	
	public void arcadeDrive(double moveValue, double rotateValue, boolean reverse) {
       double leftMotorSpeed;
       double rightMotorSpeed;

       moveValue = limit(moveValue);
       rotateValue = limit(rotateValue);

       if (moveValue > 0.0) {
           if (rotateValue > 0.0) {
               leftMotorSpeed = moveValue - rotateValue;
               rightMotorSpeed = Math.max(moveValue, rotateValue);
           } else {
               leftMotorSpeed = Math.max(moveValue, -rotateValue);
               rightMotorSpeed = moveValue + rotateValue;
           }
       } else {
           if (rotateValue > 0.0) {
               leftMotorSpeed = -Math.max(-moveValue, rotateValue);
               rightMotorSpeed = moveValue + rotateValue;
           } else {
               leftMotorSpeed = moveValue - rotateValue;
               rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
           }
       }
       
       setMotorOutputs(leftMotorSpeed, rightMotorSpeed, reverse);       
   }
	
   public void tankDrive(double leftValue, double rightValue, boolean reverse) {

       // square the inputs (while preserving the sign) to increase fine control while permitting full power
       leftValue = limit(leftValue);
       rightValue = limit(rightValue);

       setMotorOutputs(leftValue, rightValue, reverse);
   }    
   
   public TrapezoidThread getTrapThread() {
		//return trapThread;
	   return null;
	}
   
   public void sendDashboardData(){  		  	   	
		SmartDashboard.putNumber("Talon right velocity", -rightMaster.getEncVelocity());//right neg for comp, left neg for practice
		SmartDashboard.putNumber("Talon left velocity", leftMaster.getEncVelocity());
		SmartDashboard.putNumber("Talon left Position", leftMaster.getEncPosition());
		SmartDashboard.putNumber("Average talon pos", getAvgEncCounts());
		SmartDashboard.putNumber("Average talon rotations", getAvgEncRotations());
		SmartDashboard.putNumber("Talon left rpm", leftMaster.getSpeed());
		SmartDashboard.putNumber("Talon right Position", -rightMaster.getEncPosition());
		SmartDashboard.putNumber("Talon right rpm", -rightMaster.getSpeed());
		SmartDashboard.putNumber("Talon left rotations", leftMaster.getPosition());
		SmartDashboard.putNumber("Talon right rotations", rightMaster.getPosition());
		SmartDashboard.putBoolean("Motion profile is finished", profileHasFinished);
   }
   
   public void populateLog(double startTime){
   	if (firstLogFileRun){
   		lastTime = startTime;
   		firstLogFileRun = false;
   	}
   	curTime = Timer.getFPGATimestamp();
   	difTime = curTime - lastTime;
   	
   	if (difTime >= .1){
       	talonLeftPos = leftMaster.getEncPosition();
   		talonLeftRPM = leftMaster.getSpeed();
   		talonRightPos = rightMaster.getEncPosition();
   		talonRightRPM = rightMaster.getSpeed();
   		leftSpeed = leftMaster.getEncVelocity();
   		rightSpeed = rightMaster.getEncVelocity();
   		talonLeftRot = leftMaster.getPosition();
   		talonRightRot = rightMaster.getPosition();
   	
       	System.out.format("%s\n", "Time," + ((double)(curTime-startTime)/1000.0));
   		System.out.format("%s\n", "Talon left position: " + talonLeftPos);
   		System.out.format("%s\n", "Talon left rpm: " +  talonLeftRPM);
   		System.out.format("%s\n", "Talon right position: " + talonRightPos);
   		System.out.format("%s\n", "Talon right rpm: " +  talonRightRPM);
   		System.out.format("%s\n", "Talon right velocity: " + rightSpeed);
   		System.out.format("%s\n", "Talon left velocity: " +  leftSpeed);
   		lastTime = curTime;
   	}   	
   }


}