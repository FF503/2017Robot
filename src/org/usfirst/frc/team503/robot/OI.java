package org.usfirst.frc.team503.robot;

import org.usfirst.frc.team503.commands.ClimbCommand;
import org.usfirst.frc.team503.commands.ClimbFasterCommand;
import org.usfirst.frc.team503.commands.GearPlacerCommand;
import org.usfirst.frc.team503.commands.GoToDeflectorCommand;
import org.usfirst.frc.team503.commands.ShiftToHighGear;
import org.usfirst.frc.team503.commands.ShiftToLowGear;
import org.usfirst.frc.team503.commands.ToggleIndexerCommand;
import org.usfirst.frc.team503.commands.ToggleIntakeCommand;
import org.usfirst.frc.team503.commands.ToggleShooterCommand;
import org.usfirst.frc.team503.commands.TurnTurretCommand;
import org.usfirst.frc.team503.subsystems.DeflectorSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
	private static Joystick driverJoystick = new Joystick(0);
	private static Joystick operatorJoystick = new Joystick(1);
//	private static XboxController  xBox = new XboxController(1);	
	
	private static JoystickButton intakeReverse = new JoystickButton(driverJoystick, 1);
	private static JoystickButton intakeButton = new JoystickButton(driverJoystick, 2);
	private static JoystickButton shooterButton = new JoystickButton(driverJoystick, 3);
	private static JoystickButton indexerButton = new JoystickButton(driverJoystick, 4);
	private static JoystickButton shiftToLowGearButton = new JoystickButton(driverJoystick,5);
	private static JoystickButton shiftToHighGearButton = new JoystickButton(driverJoystick,6);
	
	private static JoystickButton gearOpenButton = new JoystickButton(operatorJoystick, 1);
	private static JoystickButton gearCloseButton = new JoystickButton(operatorJoystick, 2);
	private static JoystickButton climbButton = new JoystickButton(operatorJoystick, 3);
	private static JoystickButton climbFasterButton = new JoystickButton(operatorJoystick, 4);
	private static JoystickButton cameraTrackButton = new JoystickButton(operatorJoystick, 5);
	private static JoystickButton shootRPMButton = new JoystickButton(operatorJoystick, 6);
	private static JoystickButton goToTurretPosition = new JoystickButton(operatorJoystick, 6);
	
	public static void initialize(){
		shooterButton.whenPressed(new ToggleShooterCommand());
		intakeButton.whenPressed(new ToggleIntakeCommand());
		intakeReverse.whenPressed(new ToggleIntakeCommand());
		shiftToLowGearButton.whenPressed(new ShiftToLowGear());
		shiftToHighGearButton.whenPressed(new ShiftToHighGear());
		indexerButton.whenPressed(new ToggleIndexerCommand());
		gearCloseButton.whenPressed(new GearPlacerCommand());
		gearOpenButton.whenPressed(new GearPlacerCommand());
		climbButton.whenPressed(new ClimbCommand());
		climbFasterButton.whenPressed(new ClimbFasterCommand());
		//shootRPMButton.whenPressed(new ShootRPMCommand());
		//goToTurretPosition.whenPressed(new TurnTurretCommand(15, false));
		
	}
	
	public static double getDriverLeftYValue(){
		return driverJoystick.getRawAxis(1);
	}
	
	public static double getDriverLeftXValue(){
		return driverJoystick.getRawAxis(0);
	}
	
	public static double getDriverRightYValue(){
		return driverJoystick.getRawAxis(5);
	}
	
	public static double getDriverRightXValue(){
		return driverJoystick.getRawAxis(4);
	}
	
	public static double getOperatorRightYValue(){
		return operatorJoystick.getRawAxis(5);
	}
	
	public static double getOperatorRightXValue(){
		return operatorJoystick.getRawAxis(4);
	}
	
	public static double getOperatorLeftYValue(){
		return operatorJoystick.getRawAxis(1);
	}
	
	public static double getOperatorLeftXValue(){
		return operatorJoystick.getRawAxis(0);
	}
	
	public static boolean getOperatorLeftTrigger(){
		return operatorJoystick.getRawAxis(2) == 1.0;
	}
	
	public static boolean getOperatorRightTrigger(){
		return operatorJoystick.getRawAxis(3) == 1.0;
	}
	
	public static boolean getDPADUp(){
		return operatorJoystick.getPOV() == 0;
	}
	public static boolean getDPADDown(){
		
		return operatorJoystick.getPOV() == 180;
	}
	
	public static boolean getIntake(){
		return intakeButton.get();
	}

	public static boolean getShooter(){
		return shooterButton.get();
	}

	public static boolean getIndexer(){
		return indexerButton.get();
	}
	
	public static boolean getIntakeReverse(){
		return intakeReverse.get();
	}
	
	public static boolean getGearOpenButton(){
		return gearOpenButton.get();
	}
	
	public static boolean getGearCloseButton(){
		return gearCloseButton.get();
	}
	
	public static boolean getClimbButton(){
		return climbButton.get();
	}
	
	public static boolean getClimbFastButton(){
		return climbFasterButton.get();
	}
	
	public static boolean getCameraTrackButton(){
		return cameraTrackButton.get();
	}
}

