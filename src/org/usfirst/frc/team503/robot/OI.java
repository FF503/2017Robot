package org.usfirst.frc.team503.robot;

import org.usfirst.frc.team503.commands.AutomaticGearIntakeCommand;
import org.usfirst.frc.team503.commands.ChangeShooterPresetCommand;
import org.usfirst.frc.team503.commands.ClimbCommand;
import org.usfirst.frc.team503.commands.ClimbFasterCommand;
import org.usfirst.frc.team503.commands.LowerGearPlacer;
import org.usfirst.frc.team503.commands.PumpFourthWall;
import org.usfirst.frc.team503.commands.RaiseGearPlacer;
import org.usfirst.frc.team503.commands.ResetAutonCommand;
import org.usfirst.frc.team503.commands.ReverseDriveTrainCommand;
import org.usfirst.frc.team503.commands.ReverseGearIntakeCommand;
import org.usfirst.frc.team503.commands.SetReadyToFire;
import org.usfirst.frc.team503.commands.ShiftToHighGear;
import org.usfirst.frc.team503.commands.ShiftToLowGear;
import org.usfirst.frc.team503.commands.ShootSequenceCommand;
import org.usfirst.frc.team503.commands.ToggleIntakeCommand;
import org.usfirst.frc.team503.commands.ToggleShootRPMCommand;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
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
	
	private static XboxController driverJoystick = new XboxController(0);
	private static Joystick operatorJoystick = new Joystick(1);
	
	private static JoystickButton intakeButton = new JoystickButton(driverJoystick, 1);
	private static JoystickButton intakeReverse = new JoystickButton(driverJoystick, 2);
	private static JoystickButton intakeGearButton = new JoystickButton(driverJoystick, 3);
	private static JoystickButton reverseIntakeGearButton = new JoystickButton(driverJoystick, 4);
	private static JoystickButton shiftToLowGearButton = new JoystickButton(driverJoystick,5);
	private static JoystickButton shiftToHighGearButton = new JoystickButton(driverJoystick,6);
	private static JoystickButton reverseDriveTrainButton = new JoystickButton(driverJoystick, 7);
	
	private static JoystickButton placeGearButton = new JoystickButton(driverJoystick, 8);
	private static JoystickButton turnPIDButton = new JoystickButton(driverJoystick, 8);
	
	private static JoystickButton openGearMechButton = new JoystickButton(operatorJoystick, 1);
	private static JoystickButton closeGearMechButton = new JoystickButton(operatorJoystick, 2);
	private static JoystickButton backGearButton = new JoystickButton(operatorJoystick, 3);
	private static JoystickButton frontGearButton = new JoystickButton(operatorJoystick, 4);
	
	private static JoystickButton raiseGearMechButton = new JoystickButton(operatorJoystick, 1);
	private static JoystickButton lowerGearMechButton = new JoystickButton(operatorJoystick, 2);
	private static JoystickButton climbButton = new JoystickButton(operatorJoystick, 3);
	private static JoystickButton climbFasterButton = new JoystickButton(operatorJoystick, 4);
	private static JoystickButton shootEndButton = new JoystickButton(operatorJoystick, 5);
	private static JoystickButton pumpIndexerButton = new JoystickButton(operatorJoystick, 6);
	private static JoystickButton decrementPreset = new JoystickButton(operatorJoystick, 7);
	private static JoystickButton incrementPreset = new JoystickButton(operatorJoystick, 8);
	private static JoystickButton resetAutonButton = new JoystickButton(operatorJoystick, 9);
	
	private static Button shootRPMTrigger = new Button(){
		@Override
		public boolean get(){
			return operatorJoystick.getRawAxis(2) == 1.0;
		}
	};
	
	private static Button indexerTrigger = new Button(){
		@Override
		public boolean get(){
			return operatorJoystick.getRawAxis(3) == 1.0;
		}
	};
	
	private static JoystickButton shootPowerButton = new JoystickButton(operatorJoystick, 6);
	private static JoystickButton goToTurretPosition = new JoystickButton(operatorJoystick, 7);
	private static JoystickButton goToDeflectorButton = new JoystickButton(operatorJoystick, 7);

	public static void initialize(){
		//shootPowerButton.whenPressed(new ToggleShootRPMCommand());
		shootRPMTrigger.whenPressed(new ShootSequenceCommand());
		indexerTrigger.whenPressed(new SetReadyToFire());
		intakeButton.whenPressed(new ToggleIntakeCommand());
		pumpIndexerButton.whenPressed(new PumpFourthWall());
		intakeReverse.whenPressed(new ToggleIntakeCommand());
		shiftToLowGearButton.whenPressed(new ShiftToLowGear());
		shiftToHighGearButton.whenPressed(new ShiftToHighGear());
		//backGearButton.whenPressed(new ToggleGearPlacerBack(false));
		//frontGearButton.whenPressed(new ToggleGearPlacerFront(false));
	//	openGearMechButton.whenPressed(new OpenGearPlacerCommand());
		//closeGearMechButton.whenPressed(new CloseGearPlacerCommand());
		raiseGearMechButton.whenPressed(new RaiseGearPlacer());
		lowerGearMechButton.whenPressed(new LowerGearPlacer());
		intakeGearButton.whenPressed(new AutomaticGearIntakeCommand());
		reverseIntakeGearButton.whenPressed(new ReverseGearIntakeCommand());
		reverseIntakeGearButton.whenReleased(new ReverseGearIntakeCommand());
		resetAutonButton.whenPressed(new ResetAutonCommand());
		decrementPreset.whenPressed(new ChangeShooterPresetCommand());
		incrementPreset.whenPressed(new ChangeShooterPresetCommand());
		climbButton.whenPressed(new ClimbCommand());
		reverseDriveTrainButton.whenPressed(new ReverseDriveTrainCommand());
		climbFasterButton.whenPressed(new ClimbFasterCommand());
		//turnPIDButton.whenPressed(new GyroTurnCommand(90));
		//goToDeflectorButton.whenPressed(new GoToDeflectorCommand(30.0));
		//goToTurretPosition.whenPressed(new TurnTurretCommand(50.0, false));	
	}
	
	public static boolean getIncrementPreset(){
		return incrementPreset.get();
	}
	
	public static boolean getDecrementPreset(){
		return decrementPreset.get();
	}
	
	public static double getDriverLeftYValue(){
		return driverJoystick.getRawAxis(1);
	}
	
	public static void vibrateDriverController(){
		driverJoystick.setRumble(RumbleType.kLeftRumble, 0.5);
		driverJoystick.setRumble(RumbleType.kRightRumble, 0.5);
		System.out.println("vibrating");
	}
	
	public static void stopVibrateDriverController(){
		driverJoystick.setRumble(RumbleType.kLeftRumble, 0);
		driverJoystick.setRumble(RumbleType.kRightRumble, 0);
	}
	
	public static double getDriverLeftXValue(){
		return driverJoystick.getRawAxis(0);
	}
	
	public static double getDriverRightYValue(){
		return driverJoystick.getRawAxis(5);
	}
	
	public static boolean getDriverReverseButton(){
		return reverseDriveTrainButton.get();
	}

	public static double getDriverRightXValue(){
		return driverJoystick.getRawAxis(4);
	}
	
	public static boolean getDriverLeftTrigger(){
		return driverJoystick.getRawAxis(2) == 1.0;
	}
	
	public static boolean getDriverRightTrigger(){
		return driverJoystick.getRawAxis(3) == 1.0;
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
	
	public static boolean getShootEndButton(){
		return shootEndButton.get();
	}
	
	public static boolean getIntakeReverse(){
		return intakeReverse.get();
	}
	
	public static boolean getGearButtonBack(){
		return backGearButton.get();
	}
	
	public static boolean getGearButtonFront(){
		return frontGearButton.get();
	}
	
	public static boolean getClimbButton(){
		return climbButton.get();
	}
	
	public static boolean getShootPowerButton(){
		return shootPowerButton.get();
	}
	
	public static boolean getClimbFastButton(){
		return climbFasterButton.get();
	}
	
	public static boolean getOpenGearMech(){
		return openGearMechButton.get();
	}
	
	public static boolean getCloseGearMech(){
		return closeGearMechButton.get();
	}
	
	public static boolean getRaiseGearMech(){
		return raiseGearMechButton.get();
	}
	
	public static boolean getLowerGearMech(){
		return lowerGearMechButton.get();
	}
	
	public static boolean getGearIntakeButton(){
		return intakeGearButton.get();
	}
	
	public static boolean getReverseGearIntakeButton(){
		return reverseIntakeGearButton.get();
	}
	
	public static boolean getPlaceGear(){
		return placeGearButton.get();
	}
}
