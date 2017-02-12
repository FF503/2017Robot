
package org.usfirst.frc.team503.robot;

import org.usfirst.frc.team503.auton.LeftPegLeftStartAuton;
import org.usfirst.frc.team503.auton.SteamworksChoosers;
import org.usfirst.frc.team503.robot.commands.ArcadeDriveCommand;
import org.usfirst.frc.team503.robot.commands.TeleopTurretCommand;
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
	public static SteamworksChoosers chooser;


	
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
		
		chooser = SteamworksChoosers.getInstance();
		chooser.autonInitChooser();

	}
 
	/**
	 * disabledInit - fires when disabled mode is pressed.
	 */
	@Override
	public void disabledInit() {
		DrivetrainSubsystem.getInstance().stopTrapezoidControl();    	
		DrivetrainSubsystem.getInstance().percentVoltageMode();
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
		(new LeftPegLeftStartAuton()).start();
	}

	/**
	 * AutonomousPeriodic - This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		//DrivetrainSubsystem.getInstance()trainSubsystem.getInstance().populateLog(startTime);
		Scheduler.getInstance().run();
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
    	(new ArcadeDriveCommand()).start();
    	(new TeleopTurretCommand()).start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
	//	DrivetrainSubsystem.getInstance().populateLog(startTime);		
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Shooter Motor Speed", ShooterSubsystem.getInstance().getSpeed());
		SmartDashboard.putBoolean("Turret Right Limit", TurretSubsystem.getInstance().getRightLimitSwitch());
		SmartDashboard.putBoolean("Turret Left Limit", TurretSubsystem.getInstance().getLeftLimitSwitch());
		SmartDashboard.putNumber("Turret Encoder Position", TurretSubsystem.getInstance().getAngle());
		SmartDashboard.putNumber("Turret error", TurretSubsystem.getInstance().getAngle());
		
	}
	
	/**
	 * This function is called to initialize the test mode 
	 */
	@Override
	public void testInit(){
		Logger.froglog("In Roborio Test Mode...initiating Power On Self Test (POST) Diagnostics ...");
	}
	
	/**
	 * testPeriodic - This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();
	}
	
}
