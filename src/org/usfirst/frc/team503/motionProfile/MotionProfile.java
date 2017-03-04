package org.usfirst.frc.team503.motionProfile;

import org.usfirst.frc.team503.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Notifier;

public class MotionProfile {

	private CANTalon.MotionProfileStatus status = new CANTalon.MotionProfileStatus();
	private CANTalon talon;
	private double [][] profilePoints;

	//state machine setup	
    private int state = 0;
	private int loopTimeout = -1;
	private CANTalon.SetValueMotionProfile setValue = CANTalon.SetValueMotionProfile.Disable;
	
	//used to maintain timing
	private static final int kMinPointsInTalon = 10;
	private static final int kNumLoopsTimeout = 10;
	
	//nested class for thread which continually pushes points to MPB
	private class PeriodicRunnable implements Runnable {
	    public void run() {
	    	talon.processMotionProfileBuffer();    
	    }
	}
	
	private Notifier notifer = new Notifier(new PeriodicRunnable());

	
	public MotionProfile(CANTalon talon) {
		this.talon = talon;
		
		this.talon.changeMotionControlFramePeriod((int)(Robot.bot.CYCLE_TIME*500)); //5	
        notifer.startPeriodic(Robot.bot.CYCLE_TIME/2.0); //.005	
	}
	
	public void setProfile(double [][] points) {
		profilePoints = points;
	}
	
	public void reset() {
		talon.clearMotionProfileTrajectories();
		talon.set(CANTalon.SetValueMotionProfile.Disable.value);
		state = -1;
		loopTimeout = -1;
	}
	
	public void startMotionProfile(){
		state = 0;
	}
	
	public int getState(){
		return state;
	}
	
	//Repeated control loop that determines when to execute profile
	public void control() {	
		talon.getMotionProfileStatus(status);

		if (loopTimeout > 0) {
			loopTimeout--;
		}
		
		if (talon.getControlMode() != TalonControlMode.MotionProfile) {
			state = -1;
			loopTimeout = -1;
		} 	
		else {
			//enter state machine
			switch (state) {
				case -1:
					setValue = CANTalon.SetValueMotionProfile.Disable;
					break;
				case 0:
					setValue = CANTalon.SetValueMotionProfile.Disable;
					startFilling();
					state = 1;
					loopTimeout = kNumLoopsTimeout;
					break;
				case 1:
					if (status.btmBufferCnt > kMinPointsInTalon) {
						setValue = CANTalon.SetValueMotionProfile.Enable;
						state = 2;
						loopTimeout = kNumLoopsTimeout;
					}
					break;
				case 2:
					if (status.isUnderrun == false) {
						loopTimeout = kNumLoopsTimeout;
					}

					if (status.activePointValid && status.activePoint.isLastPoint) {
						setValue = CANTalon.SetValueMotionProfile.Hold;
						state = 3;
						loopTimeout = -1;
					}
					break;
			}
		}
		talon.set(setValue.value);
	}

	private void startFilling() {
		CANTalon.TrajectoryPoint point = new CANTalon.TrajectoryPoint();
		
		if (status.hasUnderrun) {
			talon.clearMotionProfileHasUnderrun();
		}
	
		talon.clearMotionProfileTrajectories();

	
		for (int i = 0; i < profilePoints.length; ++i) {
			point.position = profilePoints[i][0];
			point.velocity = profilePoints[i][1];
			point.timeDurMs = (int) profilePoints[i][2];
			point.profileSlotSelect = 0; 
			point.velocityOnly = false; 
										
			/*WE DO NOT USE ZEROPOS, because we do not want to continually reset encoders*/
			point.isLastPoint = ((i + 1) == profilePoints.length);
			talon.pushMotionProfileTrajectory(point);
		}
	}
	
	public CANTalon.SetValueMotionProfile getSetValue() {
		return setValue;
	}
}