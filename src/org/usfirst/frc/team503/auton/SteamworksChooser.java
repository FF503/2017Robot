package org.usfirst.frc.team503.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Name:		 SteamworksChoosers 
 * Purpose:		 This class creates the choosers for the auton mode, test mode, and possible teleop selectors.
 * @author:		 Rodrigo Cardenas
 * Date:		 February 2017 
 * Comments:
 */

public class SteamworksChooser {

	private static SendableChooser<AutonChoices.Alliances> allianceChooser;
	private static SendableChooser<AutonChoices.GearPosition> gearPosChooser;
	private static SendableChooser<AutonChoices.DumpBin> dumpBinChooser;
	private static SendableChooser<AutonChoices.Shoot> shootChooser;
	private static SendableChooser<AutonChoices.BinPosition> binChooser;
	
	private static SendableChooser<Command> testMotorChooser;
	
	/**
	 * Constructor for the SteamworksChoosers
	 */
	public SteamworksChooser(){
		//All of the Auton Choosers
		allianceChooser = new SendableChooser<>();
		gearPosChooser = new SendableChooser<>();
		dumpBinChooser = new SendableChooser<>();
		shootChooser = new SendableChooser<>();
		binChooser = new SendableChooser<>();
		
		//The singular Test Mode Chooser
		testMotorChooser = new SendableChooser<>();
	}
	
	private static SteamworksChooser instance = new SteamworksChooser();
	
	public static SteamworksChooser getInstance() {
		return instance;
	}
	/**
	 * This method puts all of the options into the SendableChoosers and puts them out in the SmartDashboard
	 */
	public void autonInitChooser() {
		//The Alliance choices
		allianceChooser.addDefault("[R] Red", AutonChoices.Alliances.RED);
		allianceChooser.addObject("[B] Blue", AutonChoices.Alliances.BLUE);
		
		//The gear position choices
		gearPosChooser.addObject("[L] Left", AutonChoices.GearPosition.LEFT);
		gearPosChooser.addDefault("[C] Center", AutonChoices.GearPosition.CENTER);
		gearPosChooser.addObject("[R] Right", AutonChoices.GearPosition.RIGHT);
		
		//Whether or not to dump the bin
		dumpBinChooser.addDefault("[Y] Dump the Bin", AutonChoices.DumpBin.DUMP);
		dumpBinChooser.addObject("[N] Don't Dump the Bin", AutonChoices.DumpBin.DONT_DUMP);
		
		//Whether or not to shoot
		shootChooser.addDefault("[Y] Shoot", AutonChoices.Shoot.SHOOT);
		shootChooser.addObject("[N] Don't Shoot", AutonChoices.Shoot.DONT_SHOOT);
		
		//If we dump a bin, then which bin
		binChooser.addObject("[CL] Close Left Bin", AutonChoices.BinPosition.CLOSE_LEFT_BIN);
		binChooser.addObject("[CR] Close Right Bin", AutonChoices.BinPosition.CLOSE_RIGHT_BIN);
		binChooser.addObject("[FL] Far Left Bin", AutonChoices.BinPosition.FAR_LEFT_BIN);
		binChooser.addObject("[FR] Far Right Bin", AutonChoices.BinPosition.FAR_RIGHT_BIN);
		binChooser.addObject("[N] Don't dump any Bin", null);
		
		SmartDashboard.putData("Choose Alliance", allianceChooser);
		SmartDashboard.putData("Choose Gear Position", gearPosChooser);
		SmartDashboard.putData("Dump Bin this Auton?", dumpBinChooser);
		SmartDashboard.putData("Shoot this auton?", shootChooser);
		SmartDashboard.putData("Choose Bin to Dump", binChooser);
	}
	
	/**
	 * 
	 */
	public void executeAuton() {
		AutonChoices.Alliances allianceChoice = allianceChooser.getSelected();
		AutonChoices.GearPosition gearPositionChoice = gearPosChooser.getSelected();
		AutonChoices.DumpBin dumpBinChoice = dumpBinChooser.getSelected();
		AutonChoices.Shoot shootChoice = shootChooser.getSelected();
		AutonChoices.BinPosition binChoice = binChooser.getSelected();
		//For now, the following if-else statements print what the program would've done during the auton to the Log
		
		//This block of code handles the Shoot choice
		if(shootChoice == AutonChoices.Shoot.SHOOT) {
			System.out.println("[SELECTED] Shoot This Auton");
		} 
		else {
			System.out.println("[SELECTED] Don't Shoot This Auton");
		}
		
		//This block of code handles the Alliance Choice
		if(allianceChoice == AutonChoices.Alliances.RED) {
			System.out.println("[SELECTED] Red Alliance");
		} 
		else {
			System.out.println("[SELECTED] Blue Alliance");
		}
		
		//This block of code handles the Gear position choice
		if(gearPositionChoice == AutonChoices.GearPosition.LEFT) {
			System.out.println("[SELECTED] Go for the Left Gear");
		} 
		else if(gearPositionChoice == AutonChoices.GearPosition.CENTER) {
			System.out.println("[SELECTED] Go for the Center Gear");
		} 
		else {
			System.out.println("[SELECTED] Go for the Right Gear");
		}
		
		//This block of code handles whether or not we dump a bin
		if(dumpBinChoice == AutonChoices.DumpBin.DUMP) { 
			System.out.println("[SELECTED] Dump the Bin");
		} 
		else {
			System.out.println("[SELECTED] Don't Dump The Bin");
		}
		
		//This handles which bin to choose
		if(binChoice == AutonChoices.BinPosition.CLOSE_LEFT_BIN) {
			System.out.println("[SELECTED] Dump the Close Left Bin");
		} 
		else if(binChoice == AutonChoices.BinPosition.CLOSE_RIGHT_BIN) {
			System.out.println("[SELECTED] Dump the Close Right Bin");
		} 
		else if(binChoice == AutonChoices.BinPosition.FAR_LEFT_BIN) {
			System.out.println("[SELECTED] Dump the Far Left Bin");
		} 
		else if(binChoice == AutonChoices.BinPosition.FAR_RIGHT_BIN){
			System.out.println("[SELECTED] Dump the Far Right Bin");
		} 
		else {
			System.out.println("[SELECTED] Don't Dump Any Bins");	
		}
		
	
		
	}
	
	public void testInitChooser() {
		
	//	SmartDashboard.putData("Choose Motor to Test", testMotorChooser);
	}
	
	public void executeTest() {
		testMotorChooser.getSelected().start();
	}
}