
package org.usfirst.frc.team503.robot;

import java.lang.Thread.State;

import org.usfirst.frc.team503.auton.CenterPegCenterStart;
import org.usfirst.frc.team503.auton.LeftPegLeftStartAuton;
import org.usfirst.frc.team503.auton.SteamworksChooser;
import org.usfirst.frc.team503.robot.commands.ArcadeDriveCommand;
import org.usfirst.frc.team503.robot.commands.CameraCommand;
import org.usfirst.frc.team503.robot.commands.TeleopDeflectorCommand;
import org.usfirst.frc.team503.robot.commands.TeleopTurretCommand;
import org.usfirst.frc.team503.robot.commands.TurnTurretCommand;
import org.usfirst.frc.team503.robot.subsystems.DeflectorSubsystem;
import org.usfirst.frc.team503.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team503.robot.subsystems.ShooterSubsystem;
import org.usfirst.frc.team503.robot.subsystems.TurretSubsystem;
import org.usfirst.frc.team503.robot.utils.Logger;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static RobotHardwarePracticeBot bot = null;
	private static double startTime;
	public static SteamworksChooser chooser;


	
	/**
	 * RobotInit - Fires when robot is powered-up 
	 */
	@Override
	public void robotInit() {
        NetworkTable.globalDeleteAll(); //Removes unused garbage from SmartDashboard
        NetworkTable.initialize();      //Initialize Network Tables
		
        bot = new RobotHardwarePracticeBot();
		bot.initialize();
		bot.logSmartDashboard();         /*put name of selected bot on smartdashboard */
		OI.initialize();
		
		chooser = SteamworksChooser.getInstance();
		chooser.autonInitChooser();
		RobotState.getInstance().setState(RobotState.State.DISABLED);
	}
 
	/**
	 * disabledInit - fires when disabled mode is pressed.
	 */
	@Override
	public void disabledInit() {
		DrivetrainSubsystem.getInstance().stopTrapezoidControl();    	
		DrivetrainSubsystem.getInstance().percentVoltageMode();
		RobotState.getInstance().setState(RobotState.State.DISABLED);
	}

	/**
	 * disabledPeriodic - fires when disabled mode is pressed.
	 */
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * autonomousInit - fires when Auto mode is selected  
	 */
	@Override
	public void autonomousInit() {
		chooser.executeAuton();
		startTime = Timer.getFPGATimestamp();
		(new CenterPegCenterStart()).start();
		RobotState.getInstance().setState(RobotState.State.AUTON);
		//(new TurnTurretCommand(50.3, true)).start();
	}

	/**
	 * AutonomousPeriodic - This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		//DrivetrainSubsystem.getInstance()trainSubsystem.getInstance().populateLog(startTime);
		SmartDashboard.putBoolean("isOnTarget", TurretSubsystem.getInstance().isOnTarget());
		Scheduler.getInstance().run();
		if (!Robot.bot.getName().equals("ProgrammingBot")){
			SmartDashboard.putNumber("Shooter Motor Speed", ShooterSubsystem.getInstance().getSpeed());
			SmartDashboard.putBoolean("Deflector limit switch", DeflectorSubsystem.getInstance().getLimitSwitch());
			SmartDashboard.putNumber("Deflector encoder", DeflectorSubsystem.getInstance().getPosition());
			SmartDashboard.putBoolean("Turret Right Limit", TurretSubsystem.getInstance().getRightLimitSwitch());
			SmartDashboard.putBoolean("Turret Left Limit", TurretSubsystem.getInstance().getLeftLimitSwitch());
			SmartDashboard.putNumber("Turret get Position", TurretSubsystem.getInstance().getPosition());
			SmartDashboard.putNumber("Turret Setpoint", TurretSubsystem.getInstance().getSetpoint());
			SmartDashboard.putNumber("Turret angle", TurretSubsystem.getInstance().getAngle());
			SmartDashboard.putNumber("Turret error", TurretSubsystem.getInstance().getError());
		}
	}

	/**
	 * teleopInit - first with Telop is selected 
	 */
	@Override
	public void teleopInit() {
		// Ensure the autonomous commards are cancelled if not finished 
		//if (autonomousCommand != null)
		//	autonomousCommand.cancel();
		DrivetrainSubsystem.getInstance().stopTrapezoidControl();    	
		DrivetrainSubsystem.getInstance().percentVoltageMode();
    	DrivetrainSubsystem.getInstance().resetEncoders();
	    startTime = Timer.getFPGATimestamp();
	    
	    //start commands that use joysticks and dpads manually from Robot.java
    	(new ArcadeDriveCommand()).start();
    	
    	if (!Robot.bot.getName().equals("ProgrammingBot")){
    		//(new TeleopTurretCommand()).start();
        	(new TeleopDeflectorCommand()).start();
        	(new CameraCommand()).start();
    	}
		RobotState.getInstance().setState(RobotState.State.TELEOP);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
	//	DrivetrainSubsystem.getInstance().populateLog(startTime);		
		Scheduler.getInstance().run();
		DrivetrainSubsystem.getInstance().populateLog(startTime);
		DrivetrainSubsystem.getInstance().printEncCounts();
		if (!Robot.bot.getName().equals("ProgrammingBot")){
			SmartDashboard.putNumber("Shooter Motor Speed", ShooterSubsystem.getInstance().getSpeed());
			SmartDashboard.putBoolean("Deflector limit switch", DeflectorSubsystem.getInstance().getLimitSwitch());
			SmartDashboard.putNumber("Deflector encoder", DeflectorSubsystem.getInstance().getPosition());
			SmartDashboard.putNumber("Deflector error", DeflectorSubsystem.getInstance().getError());
			SmartDashboard.putNumber("Deflector setpoint", DeflectorSubsystem.getInstance().getSetpoint());
			SmartDashboard.putBoolean("Turret Right Limit", TurretSubsystem.getInstance().getRightLimitSwitch());
			SmartDashboard.putBoolean("Turret Left Limit", TurretSubsystem.getInstance().getLeftLimitSwitch());
			SmartDashboard.putNumber("Turret get Position", TurretSubsystem.getInstance().getPosition());
			SmartDashboard.putNumber("Turret Setpoint", TurretSubsystem.getInstance().getSetpoint());
			SmartDashboard.putNumber("Turret angle", TurretSubsystem.getInstance().getAngle());
			SmartDashboard.putNumber("Turret error", TurretSubsystem.getInstance().getError());
			SmartDashboard.putNumber("left operator trigger", OI.getOperatorLeftTrigger());
		}
	}
	
	/**
	 * This function is called to initialize the test mode 
	 */
	@Override
	public void testInit(){
		Logger.froglog("In Roborio Test Mode...initiating Power On Self Test (POST) Diagnostics ...");
		RobotState.getInstance().setState(RobotState.State.TEST);
	}
	
	/**
	 * testPeriodic - This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();
	}
	
}
