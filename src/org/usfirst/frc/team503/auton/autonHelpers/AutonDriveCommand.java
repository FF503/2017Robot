package org.usfirst.frc.team503.auton.autonHelpers;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team503.subsystems.GyroSubsystem;
import org.usfirst.frc.team503.subsystems.UltrasonicSubsystem;

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
	double count;
	
    public AutonDriveCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	count = 0;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("in auton command");
    	table = NetworkTable.getTable("LG_Camera");
    	SmartDashboard.putBoolean("Auton drive isFinished", false);
    	GyroSubsystem.getInstance().gyro.reset();
    	DrivetrainSubsystem.getInstance().percentVoltageMode();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	count++;
		SmartDashboard.putNumber("auton drive count", count);
    	angle = -table.getNumber("Degrees", 0.0);
    	SmartDashboard.putNumber("Peg Angle", angle);
    	if(UltrasonicSubsystem.getInstance().getUltrasonicDistance() > 24.0 && angle != 0.0){   //was 36 
    		DrivetrainSubsystem.getInstance().arcadeDrive(.2, angle*.05, false); //0.2 and 0.05
    	} 
    	
    	else {
    	//	difference = UltrasonicSubsystem.getInstance().getRightUltrasonicDistance() - UltrasonicSubsystem.getInstance().getLeftUltrasonicDistance();
    	//	DrivetrainSubsystem.getInstance().arcadeDrive(.20, 	difference*.05, false);
    		DrivetrainSubsystem.getInstance().arcadeDrive(.20, 	0, false);
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
    	SmartDashboard.putBoolean("Auton drive isFinished",(UltrasonicSubsystem.getInstance().getUltrasonicDistance() < 12));
    	return (UltrasonicSubsystem.getInstance().getUltrasonicDistance() < 12.0);
    }	

    protected void end(){
    	System.out.println("finished auton drive command");
    	DrivetrainSubsystem.getInstance().tankDrive(0, 0, false);
    }
    
    protected void interrupted(){
    	end();
    }
}

