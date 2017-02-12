package org.usfirst.frc.team503.robot.motionProfile;


import org.usfirst.frc.team503.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Notifier;

public class TrapezoidThread implements Runnable{

	private CANTalon leftTalon;
	private CANTalon rightTalon;
	
	private double[][] leftPoints;
	private double[][] rightPoints;	
	
	private boolean startTrap = false;
	
	private Notifier trapLoop;
	
	private MotionProfile leftProfile;
	private MotionProfile rightProfile;
	
	private int id;
	private String status = "";

	public TrapezoidThread(CANTalon leftTalon, CANTalon rightTalon) {
		this.leftTalon = leftTalon;
		this.rightTalon = rightTalon;
		
		leftProfile = new MotionProfile(this.leftTalon);
		rightProfile = new MotionProfile(this.rightTalon);
		
		trapLoop = new Notifier(this);
		trapLoop.startPeriodic(Robot.bot.CYCLE_TIME);
	}
	
	public void run() {	
		if(startTrap) {

			leftProfile.control();
			rightProfile.control();
		
			if((leftProfile.getState() == 3) && (rightProfile.getState() == 3)) {
					resetTrapezoid();
					status = "finished";
			}
			else {
				status = "running";
			}			
		}
	}
	
	public synchronized void activateTrap(double[][] leftPoints, double[][] rightPoints, int id) {
		System.out.println("activate trap");
		this.leftPoints = leftPoints;
		this.rightPoints = rightPoints;
		
		//use this for trap network table
		this.id = id;
		
		status = "initialized";
		
		initializeTalons();		
		resetTrapezoid();
		setProfiles();
	
		leftProfile.startMotionProfile();
		rightProfile.startMotionProfile();
		
		startTrap = true;
		
		System.out.println("trap activated. ID: " + id);
	}

	private void setProfiles() {
		System.out.println("set profiles");
		leftProfile.setProfile(leftPoints);
		rightProfile.setProfile(rightPoints);
	}
	
	private void initializeTalons() {	
		System.out.println("initialize talons");
	 	leftTalon.changeControlMode(TalonControlMode.MotionProfile);
	 	rightTalon.changeControlMode(TalonControlMode.MotionProfile);
	}
	
	public void resetTrapezoid() {
		System.out.println("reset trapezoid");
		startTrap = false;
		leftProfile.reset();
		rightProfile.reset();
	}
	
	public String getStatus(){
		return status;
	}
	
	public int getID(){
		return id;
	}
}
