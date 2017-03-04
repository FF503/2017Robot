package org.usfirst.frc.team503.auton;

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
	double angle;
	NetworkTable table;
	
    public AutonDriveCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(GyroSubsystem.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	table = NetworkTable.getTable("LG_Camera");
    	GyroSubsystem.getInstance().gyro.reset();
    	DrivetrainSubsystem.getInstance().percentVoltageMode();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	angle = -table.getNumber("Degrees", 0.0);
    	SmartDashboard.putNumber("Peg Angle", angle);
    	if(UltrasonicSubsystem.getInstance().getRightUltrasonicDistance() > 12.0 ){   //was 36 
    		DrivetrainSubsystem.getInstance().arcadeDrive(.10, angle*.1, false);    //was angle 0.45
    	} 
    	else {
			/*if(Math.abs(GyroSubsystem.getInstance().gyro.getYaw()) > Robot.bot.GYRO_TOLERANCE){
				if(GyroSubsystem.getInstance().gyro.getYaw()< -Robot.bot.GYRO_TOLERANCE){
					DrivetrainSubsystem.getInstance().arcadeDrive(0, .3, false);
				}
				else if(GyroSubsystem.getInstance().gyro.getYaw() > Robot.bot.GYRO_TOLERANCE){
					DrivetrainSubsystem.getInstance().arcadeDrive(0, -.3, false);
				}	
			}*/
		}
    	SmartDashboard.putBoolean("Auton drive isFinished", isFinished());
    	Timer.delay(.3);
    }
    	

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (UltrasonicSubsystem.getInstance().getRightUltrasonicDistance() < 12 && Math.abs(GyroSubsystem.getInstance().gyro.getYaw()) < 3);
    }	

    protected void end(){
    	DrivetrainSubsystem.getInstance().tankDrive(0, 0, false);
    }
    
    protected void interrupted(){
    	end();
    }
}

