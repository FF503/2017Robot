package org.usfirst.frc.team503.turret;
 
 import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team503.subsystems.TurretSubsystem;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
 
  public class TurretThread implements Runnable{
	private NetworkTable table;
	private double cameraOffset;
 	private double PIDStartTime, PIDCurrTime;
 	private double leftSpeed, rightSpeed, turretSpeed;
 	private boolean discardImage;
	private double startTime;
 	private Notifier handler;
 	private boolean startTurret;
 	
 	public TurretThread (){
 		handler = new Notifier(this);
 		handler.startPeriodic(Robot.bot.TURRET_CYCLE_TIME);
		table = NetworkTable.getTable("HG_Camera");
		startTime = Timer.getFPGATimestamp();
		startTurret = false;
		discardImage = false;
 	}
 	
 	public void run(){
 		if(startTurret){
 			turretControl();
 		}
 	}
 	
 	public synchronized void startTurret(){
 		RobotState.getInstance().setTurretStatus(true);
 		RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
 		startTurret = true;
 	}
 	
 	public synchronized void stopTurret(){
 		RobotState.getInstance().setTurretStatus(false);
 		RobotState.getInstance().setTurretState(RobotState.TurretState.DISABLED);
 		startTurret = false;
 	}
 	
 	//Automatic tracking with discarding images
	private synchronized void turretControl(){
		table.putBoolean("Discard", discardImage);
		cameraOffset = getCameraAngle();
		SmartDashboard.putNumber("get camera angle", getCameraAngle());
		SmartDashboard.putNumber("camera offset", cameraOffset);
		SmartDashboard.putBoolean("discard image", discardImage);
		SmartDashboard.putString("Turret state", RobotState.getInstance().getTurretState().toString());
		System.out.println("Time: " + Timer.getFPGATimestamp() + " Angle: " + cameraOffset + " " + isRobotMoving());
		switch(RobotState.getInstance().getTurretState()){
			case DISABLED:
				TurretSubsystem.getInstance().setMotorPower(0);
				break;
			case SEEKING_TARGET:
				if(discardImage){
					discardImage = false;
				}
				if(cameraOffset == 503){
					cameraOffset = 0.0;
				}
				else if(cameraOffset != 0.0){
					RobotState.getInstance().setTurretState(RobotState.TurretState.TARGET_FOUND);
				}
				else {
					TurretSubsystem.getInstance().setMotorPower(OI.getOperatorRightXValue());
				}
				break;
			case TARGET_FOUND:
				Timer.delay(.3);
				cameraOffset = getCameraAngle();
				if(cameraOffset != 0.0){
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
		 		TurretSubsystem.getInstance().resetEncoder();
		 		PIDCurrTime = Timer.getFPGATimestamp();
		 		if ((PIDCurrTime - PIDStartTime) > 0.5){
		 			System.out.println("PID Timed out. going back to seeking target setting discard image to false");
		 			RobotState.getInstance().setTurretState(RobotState.TurretState.ON_TARGET);
		 			cameraOffset = 503;
		 			discardImage = false;
		 		}
		 		else if(TurretSubsystem.getInstance().isOnTarget()){
		 				System.out.println("PID is on target setting discard image to false");
						cameraOffset = 503;
						RobotState.getInstance().setTurretState(RobotState.TurretState.ON_TARGET);
						discardImage = false;
				}
				else if(cameraOffset == 0.0){
					System.out.println("lost target while running PID going back to robot moving");
					RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
				}
				break;
			case ON_TARGET:
				if (cameraOffset == 0.0) {
					System.out.println("lost target after getting on target going to robot moving");
					RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
				}
				else{
					if((cameraOffset != 503) && (Math.abs(cameraOffset) >= TurretSubsystem.kTurretOnTargetTolerance)){
						RobotState.getInstance().setTurretState(RobotState.TurretState.TARGET_FOUND);
						System.out.println("camera offset was valid going to target found state");
					}
				}
				break;
			default:
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
}
 