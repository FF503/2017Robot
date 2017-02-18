package org.usfirst.frc.team503.robot.subsystems;

import org.usfirst.frc.team503.robot.Robot;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class CameraThread implements Runnable {
	
	private static NetworkTable table;
	private Notifier cameraLoop;
	
	boolean found = true;
	long startTime = System.currentTimeMillis();;
	double offset;
	
	public CameraThread() {
		table = NetworkTable.getTable("Camera");
		cameraLoop = new Notifier(this);
		cameraLoop.startPeriodic(Robot.bot.TURRET_CYCLE_TIME);
	}
	
	public double updateOffset() {
		offset = table.getNumber("Degrees", 0.0);
		
		if(checkTargetFound()) {
			return offset;
		}
		return 0;
	}
		
	public boolean checkTargetFound() {
		if(offset == 0.0 && (System.currentTimeMillis()-startTime>1000)) {
			return false;
		}
		else if(offset != 0.0){
			startTime = System.currentTimeMillis();
		}
		return true;
	}
	
	public double getAngle() {
		if(checkTargetFound()) {
			return offset;
		}
		return 0.0;
	}
	
	@Override
	public void run() {
		updateOffset();
	}
}
