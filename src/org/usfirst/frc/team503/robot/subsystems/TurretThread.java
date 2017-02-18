package org.usfirst.frc.team503.robot.subsystems;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.robot.utils.Constants;

import edu.wpi.first.wpilibj.Notifier;

public class TurretThread implements Runnable{
	private CameraThread camThread;
	private Notifier handler;
	private double cameraOffset = 0.0;
	private boolean startTurret = false;
	private double currAngle = 0;
	
	public TurretThread (){
		camThread = new CameraThread();
		handler = new Notifier(this);
		handler.startPeriodic(Robot.bot.TURRET_CYCLE_TIME);
	}
	
	public void run(){
		if(startTurret){
			//turnTurret(camThread.getAngle(), false);
			//turnTurret(45, true);
			waitForOnTarget();
			stopTurret();
		}
		else{
			if(Math.abs(OI.getOperatorRightXValue()) > Constants.JOYSTICK_TOLERANCE){
	        	TurretSubsystem.getInstance().setMotorPower(OI.getOperatorRightXValue());
	    	}
	    	else{
	    		TurretSubsystem.getInstance().setMotorPower(0);
	    	}
		}
		TurretSubsystem.getInstance().resetEncoder();
	}
	
	public synchronized void startTurret(){
		RobotState.getInstance().setTurretStatus(true);
		startTurret = true;
	}
	
	public synchronized void stopTurret(){
		TurretSubsystem.getInstance().setMotorPower(0);
		RobotState.getInstance().setTurretStatus(false);
		startTurret = false;
	}
	
	private synchronized void turnTurret(double degrees, boolean goTo){
		currAngle = TurretSubsystem.getInstance().getAngle();
		if(goTo){
			TurretSubsystem.getInstance().setSetpoint(degrees);
		}
		else{
			TurretSubsystem.getInstance().setSetpoint(degrees+currAngle);
		}
	}
	
	private synchronized void waitForOnTarget(){
		while(!TurretSubsystem.getInstance().isOnTarget()){
		}
	}
}

	
