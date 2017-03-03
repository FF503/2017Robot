package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.RobotMap;
import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team503.subsystems.GyroSubsystem;
import org.usfirst.frc.team503.utils.UltrasonicSensor;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class GyroCommand extends Command {
	double angle, turnP, turnI, turnD;
	double rotateToAngleRate;
    public GyroCommand(double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);   	
    	this.angle = angle;
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	GyroSubsystem.getInstance().resetGyro();
    	
    	SmartDashboard.putBoolean("turn on target", false);
    	//angle = SmartDashboard.getNumber("Turn by", 0.0);
    	turnP = SmartDashboard.getNumber("Turn P", Robot.bot.GYRO_P);
    	turnI = SmartDashboard.getNumber("Turn I", Robot.bot.GYRO_I);
    	turnD = SmartDashboard.getNumber("Turn D", Robot.bot.GYRO_D);
    	Robot.bot.GYRO_P = turnP;
    	Robot.bot.GYRO_I = turnI;
    	Robot.bot.GYRO_D = turnD;
	    LiveWindow.addActuator("DriveTurn", "RotateController",GyroSubsystem.getInstance().getController());
	    GyroSubsystem.getInstance().setSetpoint(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	GyroSubsystem.getInstance().sendDashboardData();
    	DrivetrainSubsystem.getInstance().tankDrive(-GyroSubsystem.getInstance().getTurnToAngleRate(), GyroSubsystem.getInstance().getTurnToAngleRate(), false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	SmartDashboard.putBoolean("turn on target", GyroSubsystem.getInstance().isOnTarget());
    	return (GyroSubsystem.getInstance().isOnTarget());
    }

    // Called once after isFinished returns true
    protected void end() {
    	DrivetrainSubsystem.getInstance().tankDrive(0, 0, false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

