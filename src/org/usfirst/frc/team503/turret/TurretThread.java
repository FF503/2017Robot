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
 		RobotState.getInstance().setTurretState(RobotState.TurretState.ROBOT_MOVING);
 		startTurret = true;
 	}
 	
 	public synchronized void stopTurret(){
 		RobotState.getInstance().setTurretStatus(false);
 		RobotState.getInstance().setTurretState(RobotState.TurretState.DISABLED);
 		startTurret = false;
 	}
 	
	private synchronized void turretControl(){
		table.putBoolean("Discard", discardImage);
		if(!discardImage){
			cameraOffset = getCameraAngle();
		}
		SmartDashboard.putNumber("get camera angle", getCameraAngle());
		SmartDashboard.putNumber("camera offset", cameraOffset);
		SmartDashboard.putString("Turret state", RobotState.getInstance().getTurretState().toString());
		System.out.println("Time: " + Timer.getFPGATimestamp() + " Angle: " + cameraOffset + " " + isRobotMoving());
		turretSpeed = TurretSubsystem.getInstance().getMotor().getSpeed();
		switch(RobotState.getInstance().getTurretState()){
			case DISABLED:
				TurretSubsystem.getInstance().setMotorPower(0);
				break;
			case ROBOT_MOVING:
				cameraOffset = 0.0;
				if(discardImage == false){
					System.out.println("discard image was false setting to to true");
					discardImage = true;
				}
				if(RobotState.getInstance().getState() == RobotState.State.TELEOP){
					TurretSubsystem.getInstance().setMotorPower(OI.getOperatorRightXValue());
					TurretSubsystem.getInstance().resetEncoder();
				}
				if(!isRobotMoving() &&  turretSpeed == 0){
					System.out.println("robot not moving setting discard image to false. going to seeking target");
					discardImage = false;
					RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
				}
				break;
			case SEEKING_TARGET:
				if(isRobotMoving()){
					System.out.println("Robot was moving while seeking target going back to robot moving state");
					RobotState.getInstance().setTurretState(RobotState.TurretState.ROBOT_MOVING);
				}
				else{
					Timer.delay(2);
					cameraOffset = getCameraAngle();
					if(cameraOffset == 503){
						System.out.println("found 503 in table turning discard image off");
						cameraOffset = 0.0;
						discardImage = false;
					}
					else if(cameraOffset != 0.0){
						System.out.println("found a camera target setting state to target found and discard image to true");
						RobotState.getInstance().setTurretState(RobotState.TurretState.TARGET_FOUND);
						discardImage = true;
					}
					else {
						System.out.println("lost target going back to robot moving state");
						RobotState.getInstance().setTurretState(RobotState.TurretState.ROBOT_MOVING);
					}
				}
				break;
			case TARGET_FOUND:
				if(isRobotMoving()) {
					System.out.println("robot moving after we found target");
					RobotState.getInstance().setTurretState(RobotState.TurretState.ROBOT_MOVING);
				}
				else if(cameraOffset != 0.0){
					System.out.println("target aquired starting PID");
					double angle = TurretSubsystem.getInstance().getAngle();
					TurretSubsystem.getInstance().setSetpoint(angle + cameraOffset);
					RobotState.getInstance().setTurretState(RobotState.TurretState.RUNNING_PID);
					PIDStartTime = Timer.getFPGATimestamp();
				}
				else {
					System.out.println("somehow lost target after we found it, going back to robot moving");
					RobotState.getInstance().setTurretState(RobotState.TurretState.ROBOT_MOVING);
				}
				break;
			case RUNNING_PID:
		 		TurretSubsystem.getInstance().resetEncoder();
		 		PIDCurrTime = Timer.getFPGATimestamp();
		 		if(isRobotMoving()){
		 			System.out.println("robot moving while running PID go back to robot moving state");
					RobotState.getInstance().setTurretState(RobotState.TurretState.ROBOT_MOVING);
				}
		 		else if ((PIDCurrTime - PIDStartTime) > 0.5){
		 			System.out.println("PID Timed out. going back to seeking target setting discard image to false");
		 			RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
		 			cameraOffset = 503;
		 			discardImage = false;
		 		}
		 		else if(TurretSubsystem.getInstance().isOnTarget()){
		 				System.out.println("PID is on target setting discard image to false");
						cameraOffset = 503;
						RobotState.getInstance().setTurretState(RobotState.TurretState.ON_TARGET);
						discardImage = true;
				}
				else if(cameraOffset == 0.0){
					System.out.println("lost target while running PID going back to robot moving");
					RobotState.getInstance().setTurretState(RobotState.TurretState.ROBOT_MOVING);
				}
				else{
					System.out.println("running PID normally");
				}
				break;
			case ON_TARGET:
				if(isRobotMoving()){
					System.out.println("robot moved after getting on target going to robot moving");
					RobotState.getInstance().setTurretState(RobotState.TurretState.ROBOT_MOVING);
				}
				else if (cameraOffset == 0.0) {
					System.out.println("lost target after getting on target going to robot moving");
					RobotState.getInstance().setTurretState(RobotState.TurretState.ROBOT_MOVING);
				}
				else if(cameraOffset != 503){
					System.out.println("camera offset is updated");
					if (Math.abs(cameraOffset) >= TurretSubsystem.kTurretOnTargetTolerance){
						RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
						System.out.println("camera offset was valid going to target found state");
					}
					else{
						System.out.println("camera offset was less than tolerance");
					}
				}
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