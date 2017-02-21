package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.subsystems.TurretSubsystem;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraTurnCommand extends Command {
	
	private NetworkTable table;

	double startTime = Timer.getFPGATimestamp();
	private double offset;
	private double currAngle;
	
	public CameraTurnCommand() {
		table = NetworkTable.getTable("Camera");
		requires(TurretSubsystem.getInstance());
	}
	
	private void updateOffset() {
		offset = table.getNumber("Degrees", 0.0);
	}
		
	private boolean checkTargetFound() {
		if(offset == 0.0 && (Timer.getFPGATimestamp()-startTime>1)) {
			return false;
		}
		else if(offset != 0.0){
			startTime = Timer.getFPGATimestamp();
		}
		return true;
	}
	
	private double getAngle() {
		updateOffset();
		if(checkTargetFound()) {
			return offset;
		}
		return 0.0;
	}

	protected void initialize(){
	}
	
	protected void execute(){
		double angle = getAngle();
		currAngle = TurretSubsystem.getInstance().getAngle();
		if(angle != 0.0) {
			Timer.delay(2);
			angle = getAngle();
			SmartDashboard.putNumber("Camera offset", angle);
			if(!TurretSubsystem.getInstance().isOnTarget()){
				TurretSubsystem.getInstance().setSetpoint(angle+currAngle);
			}
    	}	
    	else{
    		if(RobotState.getInstance().getState() == RobotState.State.TELEOP){
        		TurretSubsystem.getInstance().setMotorPower(OI.getOperatorRightXValue());
    		}
    	}
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
		
	}
}
