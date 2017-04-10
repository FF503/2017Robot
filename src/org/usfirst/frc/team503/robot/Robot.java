
package org.usfirst.frc.team503.robot;

import org.usfirst.frc.team503.auton.AutonSelector;
import org.usfirst.frc.team503.commands.ArcadeDriveCommand;
import org.usfirst.frc.team503.commands.DeflectorOverrideCommand;
import org.usfirst.frc.team503.commands.TeleopDeflectorCommand;
import org.usfirst.frc.team503.subsystems.DeflectorSubsystem;
import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team503.subsystems.GearIntakeSubsystem;
import org.usfirst.frc.team503.subsystems.GyroSubsystem;
//import org.usfirst.frc.team503.subsystems.GyroSubsystem;
import org.usfirst.frc.team503.subsystems.IndexerSubsystem;
import org.usfirst.frc.team503.subsystems.ShooterSubsystem;
import org.usfirst.frc.team503.subsystems.TurretSubsystem;
import org.usfirst.frc.team503.subsystems.UltrasonicSubsystem;
import org.usfirst.frc.team503.utils.Logger;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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

	public static RobotHardwareCompBot bot = null;

	private Command autonCommand = null; 
	private NetworkTable table;
	private Solenoid lightSolenoid; 
	private double startTime;

	/**
	 * RobotInit - Fires when robot is powered-up 
	 */
	@Override
	public void robotInit() {
        NetworkTable.globalDeleteAll(); //Removes unused garbage from SmartDashboard
        NetworkTable.initialize();      //Initialize Network Tables
        bot = new RobotHardwareCompBot();
		bot.initialize();
		bot.logSmartDashboard();         /*put name of selected bot on smartdashboard */
		OI.initialize();
		table = NetworkTable.getTable("LG_Camera");  
		AutonSelector.getInstance().putAutonChoosers();
		RobotState.getInstance().setState(RobotState.State.DISABLED);
		if(Robot.bot.hasDriveCamera()){
			CameraServer.getInstance().startAutomaticCapture();
		}
		if (Robot.bot.hasLowGoalLight){
			lightSolenoid = new Solenoid(Robot.bot.lowGoalLightPort);
		}
	}
 
	/**
	 * disabledInit - fires when disabled mode is pressed.
	 */
	@Override
	public void disabledInit() {
		if (autonCommand != null){
			autonCommand.cancel();
		}
	//	DrivetrainSubsystem.getInstance().stopTrapezoidControl();    	
		DrivetrainSubsystem.getInstance().percentVoltageMode();
		ShooterSubsystem.getInstance().setMotorPower(0.0);
		IndexerSubsystem.getInstance().setMotorPower(0.0);
		DeflectorSubsystem.getInstance().setMotorPower(0.0);
		DrivetrainSubsystem.getInstance().tankDrive(0.0,0.0,false);
		TurretSubsystem.getInstance().getThread().stopTurret();
		RobotState.getInstance().setState(RobotState.State.DISABLED);
	}

	/**s
	 * disabledPeriodic - fires when disabled mode is pressed.
	 */
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * autonomousInit - fires when Auto mode is selected   nv
	 */
	@Override
	public void autonomousInit() {	
		if (Robot.bot.hasLowGoalLight){
			lightSolenoid.set(true);
		}
		startTime = Timer.getFPGATimestamp();
		UltrasonicSubsystem.getInstance().enableUltrasonicSensors();
		RobotState.getInstance().setState(RobotState.State.AUTON);
		TurretSubsystem.getInstance().getThread().startTurret();
		AutonSelector.getInstance().startAuton();
		
		//(new TestAuton()).start();
	}

	/**
	 * AutonomousPeriodic - This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		//DrivetrainSubsystem.getInstance()trainSubsystem.getInstance().populateLog(startTime);
		Scheduler.getInstance().run();
		
		if (!Robot.bot.getName().equals("ProgrammingBot")){
			SmartDashboard.putNumber("Peg Angle", table.getNumber("Degrees", 0.0));
			ShooterSubsystem.getInstance().sendDashboardData();
			UltrasonicSubsystem.getInstance().sendDashboardData();
			DeflectorSubsystem.getInstance().sendDashboardData();
			TurretSubsystem.getInstance().sendDashboardData();
			DrivetrainSubsystem.getInstance().sendDashboardData();
			GyroSubsystem.getInstance().sendDashboardData();
			GearIntakeSubsystem.getInstance().sendDashboardData();
		}
	}

	/**
	 * teleopInit - first with Telop is selected 
	 */
	@Override
	public void teleopInit() {
		// Ensure the autonomous commands are cancelled if not finished 
		LiveWindow.setEnabled(false);
		if (autonCommand != null){
			autonCommand.cancel();
		}
		//DrivetrainSubsystem.getInstance().stopTrapezoidControl();    	
    	DrivetrainSubsystem.getInstance().resetEncoders();
		RobotState.getInstance().setState(RobotState.State.TELEOP);
		UltrasonicSubsystem.getInstance().enableUltrasonicSensors();
	    startTime = Timer.getFPGATimestamp();
	    //start commands that use joysticks and dpads manually from Robot.java
    	(new ArcadeDriveCommand()).start();
    	if (!Robot.bot.getName().equals("ProgrammingBot")){
    		TurretSubsystem.getInstance().getThread().startTurret();
//    		System.out.println("coming out of start turret teleop" + TurretSubsystem.getInstance().getThread().getStartTurret());
    		//(new TeleopTurretCommand()).start();
        	//(new TeleopDeflectorCommand()).start();
        	//(new DeflectorOverrideCommand()).start();
    	}
    	startTime = Timer.getFPGATimestamp();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		//DrivetrainSubsystem.getInstance().populateLog(startTime);
		SmartDashboard.putNumber("Time Remaining", 150 - (Timer.getFPGATimestamp() - startTime));
		if (!Robot.bot.getName().equals("ProgrammingBot")){
			SmartDashboard.putNumber("Peg Angle", table.getNumber("Degrees", 0.0));
			ShooterSubsystem.getInstance().sendDashboardData();
			DeflectorSubsystem.getInstance().sendDashboardData();
			TurretSubsystem.getInstance().sendDashboardData();
			UltrasonicSubsystem.getInstance().sendDashboardData();
			GyroSubsystem.getInstance().sendDashboardData();
			SmartDashboard.putBoolean("Climber is Running",RobotState.getInstance().getClimberStatus());
			DrivetrainSubsystem.getInstance().sendDashboardData();
			GearIntakeSubsystem.getInstance().sendDashboardData();
		}
	}
	
	/**
	 * This function is called to initialize the test mode 
	 */
	@Override
	public void testInit(){
    	if(Robot.bot.hasLowGoalLight){
    		lightSolenoid.set(true);
    	}
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
