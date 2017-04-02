package org.usfirst.frc.team503.auton;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team503.subsystems.GyroSubsystem;
import org.usfirst.frc.team503.subsystems.UltrasonicSubsystem;
import org.usfirst.frc.team503.utils.Constants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutonDriveCommand extends Command {
	double angle, difference;
	NetworkTable table;
	double startTime, currTime;
	double turnMultiplier;
	
    public AutonDriveCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	table = NetworkTable.getTable("LG_Camera");    	
    	SmartDashboard.putBoolean("Auton drive isFinished", false);
    	GyroSubsystem.getInstance().gyro.reset();
    	DrivetrainSubsystem.getInstance().percentVoltageMode();
    	DrivetrainSubsystem.getInstance().setBrakeMode(true);
    	startTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currTime = Timer.getFPGATimestamp();
    	angle = -table.getNumber("Degrees",0.0);
    	SmartDashboard.putNumber("Peg Angle", angle);
    	if(/*UltrasonicSubsystem.getInstance().getUltrasonicDistance() > 2 4.0 && */angle != 0.0){   //was 36 
    		//DrivetrainSubsystem.getInstance().arcadeDrive(.2, angle*.05, false);
    		turnMultiplier = 1-((UltrasonicSubsystem.getInstance().getUltrasonicDistance() - Constants.DISTANCE_TO_PEG)/(Robot.bot.AUTON_DRIVE_P-Constants.DISTANCE_TO_PEG));
    		if (turnMultiplier < Robot.bot.minAutonDriveTurnPower){
    			turnMultiplier = Robot.bot.minAutonDriveTurnPower;
    		}
    		else if (turnMultiplier > Robot.bot.maxAutonDriveTurnPower){
    			turnMultiplier = Robot.bot.maxAutonDriveTurnPower;
    		}
    		SmartDashboard.putNumber("mult", turnMultiplier);
    		DrivetrainSubsystem.getInstance().arcadeDrive(0.3, turnMultiplier * angle , false);
    	} 
    	
    	else {
    	//	difference = UltrasonicSubsystem.getInstance().getRightUltrasonicDistance() - UltrasonicSubsystem.getInstance().getLeftUltrasonicDistance();
    	//	DrivetrainSubsystem.getInstance().arcadeDrive(.20, 	difference*.05, false);
    		DrivetrainSubsystem.getInstance().arcadeDrive(0.3, 0, false);
    		//	if(Math.abs(GyroSubsystem.getInstance().gyro.getYaw()) > Robot.bot.GYRO_TOLERANCE){
		//		if(GyroSubsystem.getInstance().gyro.getYaw()< -Robot.bot.GYRO_TOLERANCE){
		//			DrivetrainSubsystem.getInstance().arcadeDrive(0, .3, false);
		//		}
		//		else if(GyroSubsystem.getInstance().gyro.getYaw() > Robot.bot.GYRO_TOLERANCE){
		//			DrivetrainSubsystem.getInstance().arcadeDrive(0, -.3, false);
		//		}	
		//	}
		}
    }
    	

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	SmartDashboard.putBoolean("Auton drive isFinished",(UltrasonicSubsystem.getInstance().getUltrasonicDistance() < Constants.DISTANCE_TO_PEG));
    	return (UltrasonicSubsystem.getInstance().getUltrasonicDistance()< Constants.DISTANCE_TO_PEG);
    }	

    protected void end(){
    	System.out.println("finished auton drive command");
    	DrivetrainSubsystem.getInstance().tankDrive(0, 0, false);
    }
    
    protected void interrupted(){
    	end();
    }
}

