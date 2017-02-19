package org.usfirst.frc.team503.robot.turret;
 
 import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team503.robot.subsystems.TurretSubsystem;
import org.usfirst.frc.team503.robot.utils.Constants;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
 
 public class TurretThread implements Runnable{
	private NetworkTable table;
	private double cameraOffset;
 	private double prevRightEncoder, prevLeftEncoder, currRightEncoder, currLeftEncoder;
 	
	private double startTime;
 	private Notifier handler;
 	private boolean startTurret;
 	
 	public TurretThread (){
 		handler = new Notifier(this);
 		handler.startPeriodic(Robot.bot.TURRET_CYCLE_TIME);
		table = NetworkTable.getTable("Camera");
		startTime = Timer.getFPGATimestamp();
		startTurret = false;
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
 	
	private synchronized void turretControl(){
		SmartDashboard.putString("Turret state", RobotState.getInstance().getTurretState().toString());
		cameraOffset = getCameraAngle();
		SmartDashboard.putNumber("Camera offset", cameraOffset);
		switch(RobotState.getInstance().getTurretState()){
			case DISABLED:
				TurretSubsystem.getInstance().setMotorPower(0);
				break;
			case SEEKING_TARGET:
				if(cameraOffset != 0.0){
					RobotState.getInstance().setTurretState(RobotState.TurretState.TARGET_FOUND);
				}
				else if(RobotState.getInstance().getState() == RobotState.State.TELEOP){
					TurretSubsystem.getInstance().setMotorPower(OI.getOperatorRightXValue());
				}
				break;
			case TARGET_FOUND:
				if(cameraOffset == 0.0){
					RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
				}
				double angle = TurretSubsystem.getInstance().getAngle();
				TurretSubsystem.getInstance().setSetpoint(angle + cameraOffset);
				RobotState.getInstance().setTurretState(RobotState.TurretState.RUNNING_PID);
				break;
			case RUNNING_PID:
		 		TurretSubsystem.getInstance().resetEncoder();
				if(TurretSubsystem.getInstance().isOnTarget()){
					RobotState.getInstance().setTurretState(RobotState.TurretState.ON_TARGET);
					prevRightEncoder = DrivetrainSubsystem.getInstance().getRightMaster().getPosition();
					prevLeftEncoder = DrivetrainSubsystem.getInstance().getLeftMaster().getPosition();
				}
				else if(cameraOffset == 0.0){
					RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
				}
				break;
			case ON_TARGET:
				currRightEncoder = DrivetrainSubsystem.getInstance().getRightMaster().getPosition();
				currLeftEncoder = DrivetrainSubsystem.getInstance().getLeftMaster().getPosition();
				if(Math.abs(currRightEncoder-prevRightEncoder)>100 || Math.abs(currLeftEncoder-prevLeftEncoder)>100){
					RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
				}
				break;
		}
	}
 	
	private boolean checkTargetFound() {
		if(cameraOffset == 0.0 && (Timer.getFPGATimestamp()-startTime)>1) {
			return false;
		}
		else if(cameraOffset != 0.0){
			startTime = Timer.getFPGATimestamp();
		}
		return true;
	}
	
	private double getCameraAngle() {
		double offset = table.getNumber("Degrees", 0.0);
		if(checkTargetFound()) {
			return offset;
		}
		return 0.0;
	}
 }