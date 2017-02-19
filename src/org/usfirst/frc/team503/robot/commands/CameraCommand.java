package org.usfirst.frc.team503.robot.commands;

import org.usfirst.frc.team503.robot.OI;
import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.subsystems.TurretSubsystem;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class CameraCommand extends Command {
	
	private NetworkTable table;

	private boolean found = true;
	double startTime = Timer.getFPGATimestamp();
	private double offset;
	
	public CameraCommand() {
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
		if(checkTargetFound()) {
			return offset;
		}
		return 0.0;
	}

	protected void initialize(){
	}
	
	protected void execute(){
		updateOffset();
		double currAngle = TurretSubsystem.getInstance().getAngle();
		double angle = getAngle();
    	if(angle != 0.0) {
    		if(TurretSubsystem.getInstance().getMotor().getControlMode() == TalonControlMode.PercentVbus){
        		TurretSubsystem.getInstance().setSetpoint(angle+currAngle);
    		}
    		else if(!TurretSubsystem.getInstance().isOnTarget()){
        		TurretSubsystem.getInstance().setSetpoint(angle+currAngle);
    		}
    	}	
    	else{
    		TurretSubsystem.getInstance().setMotorPower(OI.getOperatorRightXValue());
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
