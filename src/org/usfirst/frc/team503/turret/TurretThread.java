package org.usfirst.frc.team503.turret;
 
 import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team503.subsystems.TurretSubsystem;
import org.usfirst.frc.team503.utils.Constants;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
 
  public class TurretThread implements Runnable{
	private NetworkTable table;
	private double lastHeartbeat, curHeartbeat;
	private double heartbeatUpdateStartTime, heartbeatUpdateTime;
	private double cameraOffset;
 	private double PIDStartTime, PIDCurrTime;
 	private double leftSpeed, rightSpeed, turretSpeed;
 	private boolean discardImage;
	private double startTime;
 	private Notifier handler;
 	private boolean startTurret;
 	private boolean piIsAlive;
 	

 	
 	public TurretThread (){
 		handler = new Notifier(this);
 		handler.startPeriodic(Robot.bot.TURRET_CYCLE_TIME);
		table = NetworkTable.getTable("HG_Camera");
		startTime = Timer.getFPGATimestamp();
		startTurret = false;
		discardImage = false;
		lastHeartbeat = 0.0;
		curHeartbeat = 0.0;
 	}
 	
 	public void run(){
 		if(startTurret){
 			turretControl();
 		}
 	}
 	
 	public synchronized void startTurret(){
 		RobotState.getInstance().setTurretStatus(true);
 		startTurret = true;
 		if(RobotState.getInstance().getState()==RobotState.State.AUTON){
 			RobotState.getInstance().setTurretState(RobotState.TurretState.RESET_TURRET);
 		}
 		else{
 			RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
 		}
 	}
 	
 	public synchronized void stopTurret(){
 		RobotState.getInstance().setTurretStatus(false);
 		RobotState.getInstance().setTurretState(RobotState.TurretState.DISABLED);
 		startTurret = false;
 	}
 	
 	//Automatic tracking with discarding images
	private synchronized void turretControl(){
		//checks to see if rasberry pi is still alive by checking heart beat
		piIsAlive = isPiAlive();
		//Tells pi if it wants angle values or not
		table.putBoolean("Discard", discardImage);
		//gets camera angle from pi
		cameraOffset = getCameraAngle();
		//populate smart dashboard with some useful data
		SmartDashboard.putNumber("get camera angle", getCameraAngle());
		SmartDashboard.putNumber("camera offset", cameraOffset);
		SmartDashboard.putBoolean("discard image", discardImage);
		SmartDashboard.putBoolean("pi is alive", piIsAlive);
		SmartDashboard.putString("Turret state", RobotState.getInstance().getTurretState().toString());
		//System.out.println("Time: " + Timer.getFPGATimestamp() + " Angle: " + cameraOffset + " " + isRobotMoving());
		
		//state machine
		switch(RobotState.getInstance().getTurretState()){
			//disabled state
			case DISABLED:
				TurretSubsystem.getInstance().setMotorPower(0);
				break;
			//Turret is in teleop control and is looking to find a target
			case RESET_TURRET:
				TurretSubsystem.getInstance().resetTurret(false);
				if(TurretSubsystem.getInstance().getFwdLimitSwitch()||TurretSubsystem.getInstance().getRevLimitSwitch()){
					TurretSubsystem.getInstance().getMotor().enableForwardSoftLimit(true);
					TurretSubsystem.getInstance().getMotor().enableReverseSoftLimit(true);
					RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
				}
				break;
			case SEEKING_TARGET:
				if(discardImage){
					discardImage = false;
				}
				if(cameraOffset == 503){
					cameraOffset = 0.0;			
				}
				/*if(RobotState.getInstance().getTurretHint()){
					RobotState.getInstance().setTurretState(RobotState.TurretState.TAKING_HINT);
				}*/
				if((cameraOffset != 0.0) && piIsAlive){
					RobotState.getInstance().setTurretState(RobotState.TurretState.TARGET_FOUND);
				}
				else {
					TurretSubsystem.getInstance().setMotorPower(OI.getOperatorRightXValue());
				}
				break;
			//Turret finds target and finds a new more accurate angle
			case TARGET_FOUND:
				//delay to account for pi lag
				Timer.delay(.4);
				cameraOffset = getCameraAngle();
				if(!piIsAlive){
					RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
				}
				else if(cameraOffset != 0.0){
					discardImage = true;
					double angle = TurretSubsystem.getInstance().getAngle();
					TurretSubsystem.getInstance().setSetpoint(angle + cameraOffset);
					RobotState.getInstance().setTurretState(RobotState.TurretState.RUNNING_PID);
					PIDStartTime = Timer.getFPGATimestamp();
				}
				else {
					RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
				}
				break;
			case RUNNING_PID:
		 		TurretSubsystem.getInstance().resetEncoderAtLimitSwitch();
		 		PIDCurrTime = Timer.getFPGATimestamp();
		 		RobotState.getInstance().setTurretIsLocked(false);
		 		if(!piIsAlive){
					RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
				}
		 		else if ((PIDCurrTime - PIDStartTime) > 2.0){
		 			RobotState.getInstance().setTurretState(RobotState.TurretState.ON_TARGET);
		 			cameraOffset = 503;		 			
		 			discardImage = false;
		 		}
		 		else if(TurretSubsystem.getInstance().isOnTarget()){
					cameraOffset = 503;
					RobotState.getInstance().setTurretState(RobotState.TurretState.ON_TARGET);
					discardImage = false;
				}
				else if((cameraOffset == 0.0)&&(!RobotState.getInstance().getTurretHint())){
					RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
				}
				break;
			case ON_TARGET:
				if(!piIsAlive){
					RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
				}
				else if (cameraOffset == 0.0 && !RobotState.getInstance().getTurretHint() ) {
					RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
				}
				else{
					if((cameraOffset != 503) && (Math.abs(cameraOffset) >= Constants.CAMERA_TOLERANCE)){
						RobotState.getInstance().setTurretState(RobotState.TurretState.TARGET_FOUND);
						RobotState.getInstance().setTurretIsLocked(false);
						RobotState.getInstance().setTurretHint(false);
					}
					else {
						RobotState.getInstance().setTurretIsLocked(true);
					}
				}
				break;
			case TAKING_HINT:
				TurretSubsystem.getInstance().setSetpoint(RobotState.getInstance().getTurretAngle());
				PIDStartTime = Timer.getFPGATimestamp() - 0.5;  //workaround to give us an extra 0.5 seconds to achieve target
				RobotState.getInstance().setTurretState(RobotState.TurretState.RUNNING_PID);
				RobotState.getInstance().setTurretHint(true);
				break;
		}
	}
	
	
	private synchronized boolean isRobotMoving(){
		leftSpeed = DrivetrainSubsystem.getInstance().getLeftMaster().getEncVelocity();
		rightSpeed = DrivetrainSubsystem.getInstance().getRightMaster().getEncVelocity();
		return Math.abs(leftSpeed) > 10.0 || Math.abs(rightSpeed) > 10.0;
	}
 	
	private synchronized boolean checkTargetFound(double cameraOffset) {
		if((cameraOffset == 0.0 && (Timer.getFPGATimestamp()-startTime)>1)) {
			return false;
		}
		else if(cameraOffset != 0.0){
			startTime = Timer.getFPGATimestamp();
		}
		return true;
	}
	
	private synchronized double getCameraAngle() {
		double offset = table.getNumber("Degrees", 0.0);
		if(checkTargetFound(offset)) {
			return offset;
		}
		return 0.0;
	}
	  
	private synchronized boolean isPiAlive(){
		lastHeartbeat = curHeartbeat;
		curHeartbeat = table.getNumber("Heartbeat", 0.0);
		SmartDashboard.putNumber("High goal Pi heartbeat", curHeartbeat);
		if (curHeartbeat != lastHeartbeat){
			heartbeatUpdateStartTime = Timer.getFPGATimestamp();
		}
		heartbeatUpdateTime = Timer.getFPGATimestamp() - heartbeatUpdateStartTime;
		SmartDashboard.putNumber("heartbeat update time", heartbeatUpdateTime);
		if (heartbeatUpdateTime >= 2.0){
			table.putNumber("Degrees", 503);
			RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
			return false;
		}
		else{
			return true;
		}
	}
}
 
