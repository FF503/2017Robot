package org.usfirst.frc.team503.robot;

import org.usfirst.frc.team503.robot.commands.ToggleIndexerCommand;
import org.usfirst.frc.team503.robot.commands.ToggleIntakeCommand;
import org.usfirst.frc.team503.robot.commands.ShiftToHighGear;
import org.usfirst.frc.team503.robot.commands.ShiftToLowGear;
import org.usfirst.frc.team503.robot.commands.ToggleShooterCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
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
	
	private static Joystick Driverjoystick = new Joystick(0);
	
	//private static JoystickButton aPressed = new JoystickButton(joystick, 1);
	//private static JoystickButton bPressed = new JoystickButton(joystick, 2);
	//private static JoystickButton xPressed = new JoystickButton(joystick, 3);
	//private static JoystickButton safety1 = new JoystickButton(joystick, 5);
	//private static JoystickButton safety2 = new JoystickButton(joystick, 6);
	
	private static JoystickButton intakeReverse = new JoystickButton(Driverjoystick, 1);
	private static JoystickButton intakeButton = new JoystickButton(Driverjoystick, 2);
	private static JoystickButton shooterButton = new JoystickButton(Driverjoystick, 3);
	private static JoystickButton indexerButton = new JoystickButton(Driverjoystick, 4);
	
	private static JoystickButton shiftToLowGearButton = new JoystickButton(Driverjoystick,5);
	private static JoystickButton shiftToHighGearButton = new JoystickButton(Driverjoystick,6);
	
	
	private static JoystickButton indexerReverse = new JoystickButton(Driverjoystick, 9);

	
	public static void initialize(){
		//aPressed.whenPressed(new CyberShootCommand());
		//pressureButton.whenPressed(new CyberPressurizeCommand(Pressure.MID));
		shooterButton.whenPressed(new ToggleShooterCommand());
		intakeButton.whenPressed(new ToggleIntakeCommand());
		intakeReverse.whenPressed(new ToggleIntakeCommand());
		shiftToLowGearButton.whenPressed(new ShiftToLowGear());
		shiftToHighGearButton.whenPressed(new ShiftToHighGear());
		indexerButton.whenPressed(new ToggleIndexerCommand());
	}
	
	public static double getLeftYValue(){
		return Driverjoystick.getRawAxis(1);
	}
	public static double getLeftXValue(){
		return Driverjoystick.getRawAxis(0);
	}
	public static double getRightYValue(){
		return Driverjoystick.getRawAxis(5);
	}
	public static double getRightXValue(){
		return Driverjoystick.getRawAxis(4);
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
	

}

