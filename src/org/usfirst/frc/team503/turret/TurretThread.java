package org.usfirst.frc.team503.turret;
 
 import org.usfirst.frc.team503.auton.AutonChoices;
import org.usfirst.frc.team503.auton.AutonSelector;
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
	private double onTargetStartTime, onTargetCurrTime;
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
		onTargetStartTime = startTime;
 	}
 	
 	public void run(){
 		if(startTurret){
 			turretControl();
 		}	
 	}
 	
 	public synchronized boolean getStartTurret(){
 		return startTurret;
 	}
 	public synchronized void setStartTurret(boolean val){
 		startTurret = val;
 	}
 	public synchronized void startTurret(){
 		RobotState.getInstance().setTurretStatus(true);
 		startTime = Timer.getFPGATimestamp();
 		onTargetStartTime = startTime;
 		if(RobotState.getInstance().getState()==RobotState.State.AUTON){
 			if(AutonSelector.getInstance().allianceChooser.getSelected() == AutonChoices.Alliances.RED){
 				if(AutonSelector.getInstance().startPosChooser.getSelected() == AutonChoices.StartPosition.RIGHT){
 						if(AutonSelector.getInstance().binChooser.getSelected() == AutonChoices.BinPosition.RIGHT_BIN){
 							RobotState.getInstance().setTurretResetSide(true);
 						}
 						else{
 							RobotState.getInstance().setTurretResetSide(false);
 						}
 				}
 				else if(AutonSelector.getInstance().startPosChooser.getSelected() == AutonChoices.StartPosition.BIN){
 					RobotState.getInstance().setTurretResetSide(true);
 				}
 				else if (AutonSelector.getInstance().startPosChooser.getSelected() == AutonChoices.StartPosition.CENTER){
 					RobotState.getInstance().setTurretResetSide(false);
 				}
 				else if (AutonSelector.getInstance().startPosChooser.getSelected() == AutonChoices.StartPosition.LEFT){
 					RobotState.getInstance().setTurretResetSide(false);
 				}
 				else if(AutonSelector.getInstance().startPosChooser.getSelected() == AutonChoices.StartPosition.DUAL_BIN){
 					RobotState.getInstance().setTurretResetSide(true);
 				}
 			}
 			else{
 				if(AutonSelector.getInstance().startPosChooser.getSelected() == AutonChoices.StartPosition.LEFT){
						RobotState.getInstance().setTurretResetSide(false);
				}
				else if(AutonSelector.getInstance().startPosChooser.getSelected() == AutonChoices.StartPosition.BIN){
					RobotState.getInstance().setTurretResetSide(false);
				}
				else if(AutonSelector.getInstance().startPosChooser.getSelected() == AutonChoices.StartPosition.CENTER){
					RobotState.getInstance().setTurretResetSide(true);
				}
				else if(AutonSelector.getInstance().startPosChooser.getSelected() == AutonChoices.StartPosition.RIGHT){
					RobotState.getInstance().setTurretResetSide(true);
				}
				else if (AutonSelector.getInstance().startPosChooser.getSelected() == AutonChoices.StartPosition.DUAL_BIN){
					RobotState.getInstance().setTurretResetSide(false);
				}
 			}
 			RobotState.getInstance().setTurretState(RobotState.TurretState.RESET_TURRET);
 		}
 		else{
 			RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
 		}
 		startTurret = true;
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
		/*if (RobotState.getInstance().getState() == RobotState.State.AUTON){
			cameraOffset = 0.0;
		}
		else*/
			cameraOffset = getCameraAngle();
			
		
		//populate smart dashboard with some useful data
		SmartDashboard.putNumber("get camera angle", getCameraAngle());
		SmartDashboard.putNumber("camera offset", cameraOffset);
		SmartDashboard.putBoolean("discard image", discardImage);
		SmartDashboard.putBoolean("pi is alive", piIsAlive);
		SmartDashboard.putString("Turret state", RobotState.getInstance().getTurretState().toString());
		
		//state machine
		//System.out.println( " turret state: " + RobotState.getInstance().getTurretState().toString());
		switch(RobotState.getInstance().getTurretState()){
			//disabled state
			case DISABLED:
				TurretSubsystem.getInstance().setMotorPower(0);
				break;
			case RESET_TURRET:
				TurretSubsystem.getInstance().resetTurret(RobotState.getInstance().getTurretResetSide());
				if(TurretSubsystem.getInstance().getFwdLimitSwitch()||TurretSubsystem.getInstance().getRevLimitSwitch()){
					TurretSubsystem.getInstance().getMotor().enableForwardSoftLimit(true);
					TurretSubsystem.getInstance().getMotor().enableReverseSoftLimit(true);
					TurretSubsystem.getInstance().setMotorPower(0);
		    		RobotState.getInstance().setHasTurretReset(true);
					//RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
				}
				break;
			//Turret is in teleop control and is looking to find a target
			case SEEKING_TARGET:
				if(discardImage){
					discardImage = false;
				}
				if(cameraOffset == 503){
					cameraOffset = 0.0;			
				}
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
				isPiAlive();
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
		 		TurretSubsystem.getInstance().resetEncoderAtLimitSwitch();
		 		PIDCurrTime = Timer.getFPGATimestamp();
		 		RobotState.getInstance().setTurretIsLocked(false);
				discardImage = true;
		 		if ((PIDCurrTime - PIDStartTime) > 1.0){
		 			RobotState.getInstance().setTurretState(RobotState.TurretState.ON_TARGET);
		 			cameraOffset = 503;		 			
		 			discardImage = false;
					onTargetStartTime = Timer.getFPGATimestamp();
		 		}
		 		else if(TurretSubsystem.getInstance().isOnTarget()){
					cameraOffset = 503;
					RobotState.getInstance().setTurretState(RobotState.TurretState.ON_TARGET);
					discardImage = false;
					onTargetStartTime = Timer.getFPGATimestamp();
				}
				break;
			case ON_TARGET:
				onTargetCurrTime = Timer.getFPGATimestamp();
				if (cameraOffset == 0.0) {
					if (!RobotState.getInstance().getTurretHint()){
						RobotState.getInstance().setTurretState(RobotState.TurretState.SEEKING_TARGET);
					}
					else if((onTargetCurrTime - onTargetStartTime) > .3){
						RobotState.getInstance().setTurretIsLocked(true);
					}
				}
				else{
					if(cameraOffset != 503 && (Math.abs(cameraOffset) >= Robot.bot.TURRET_TOLERANCE)){
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
				TurretSubsystem.getInstance().setSetpoint(RobotState.getInstance().getShooterPreset().turretAngle);
				PIDStartTime = Timer.getFPGATimestamp() + 2.0;  //workaround to give us an extra 0.5 seconds to achieve target
				RobotState.getInstance().setTurretState(RobotState.TurretState.RUNNING_PID);
				RobotState.getInstance().setTurretHint(true);
				discardImage = true;
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
			if(offset!=0.0){
				return offset+Constants.TURRET_OFFSET;
			}
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
			table.putNumber("Degrees", 0.0);
			return false;
		}
		
		/*if (RobotState.getInstance().getState() == RobotState.State.AUTON){
			return false;
		}*/
		else{
			return true;
		}
	}
	
	public synchronized boolean getPiAlive(){
		return piIsAlive;
	}
}
 
