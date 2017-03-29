package org.usfirst.frc.team503.auton;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class creates the choosers for the auton mode, test mode, and possible teleop selectors.
 * @author Rodrigo Cardenas
 */
public class AutonSelector {

	private SendableChooser<AutonChoices.Alliances> allianceChooser;
	private SendableChooser<AutonChoices.GearPosition> gearPosChooser;
	private SendableChooser<AutonChoices.Shoot> shootChooser;
	private SendableChooser<AutonChoices.BinPosition> binChooser;
	
	
	/**
	 * Constructor for the SteamworksAutonSelectors
	 */
	public AutonSelector(){
		//All of the Auton Choosers
		allianceChooser = new SendableChooser<>();
		gearPosChooser = new SendableChooser<>();
		shootChooser = new SendableChooser<>();
		binChooser = new SendableChooser<>();
	}
	
	private static AutonSelector instance = new AutonSelector();
	
	public static AutonSelector getInstance() {
		return instance;
	}
	
	/**
	 * This method puts all of the options into the SendableChoosers and puts them out in the SmartDashboard
	 */
	public void putAutonChoosers() {
		//The Alliance choices
	
		allianceChooser.addDefault("[R] Red", AutonChoices.Alliances.RED);
		allianceChooser.addObject("[B] Blue", AutonChoices.Alliances.BLUE);
		
		//The gear position choices
		gearPosChooser.addObject("[L] Left", AutonChoices.GearPosition.LEFT);
		gearPosChooser.addDefault("[C] Center", AutonChoices.GearPosition.CENTER);
		gearPosChooser.addObject("[R] Right", AutonChoices.GearPosition.RIGHT);
		gearPosChooser.addObject("[N] Do Nothing", AutonChoices.GearPosition.DO_NOTHING);
		
		
		//Whether or not to shoot
		shootChooser.addDefault("[Y] Shoot", AutonChoices.Shoot.SHOOT);
		shootChooser.addObject("[N] Don't Shoot", AutonChoices.Shoot.DONT_SHOOT);
		
		//If we dump a bin, then which bin
		binChooser.addObject("[L] Left Bin", AutonChoices.BinPosition.LEFT_BIN);
		binChooser.addObject("[R] Right Bin", AutonChoices.BinPosition.RIGHT_BIN);
		binChooser.addDefault("[N] Don't dump any Bin", AutonChoices.BinPosition.NO_BIN);
		
		//Putting all of the SendableChoosers onto the SmartDashboard
		SmartDashboard.putData("Choose Alliance", allianceChooser);
		SmartDashboard.putData("Choose Gear Position", gearPosChooser);
		SmartDashboard.putData("Shoot This auton?", shootChooser);
		SmartDashboard.putData("Choose Bin to Dump", binChooser);
		System.out.println("im in here");
	}
	
	/**
	 * 
	 */
	public void startAuton() {
		AutonChoices.Alliances allianceChoice = allianceChooser.getSelected();
		AutonChoices.GearPosition gearPositionChoice = gearPosChooser.getSelected();
		AutonChoices.Shoot shootChoice = shootChooser.getSelected();
		AutonChoices.BinPosition binChoice = binChooser.getSelected();
		
		CommandGroup selectedAuton = null;
		/* Brace yourself for a lot of nested if-else statements
		 * This is where the commands are determined 
		 * 
		 */
		SmartDashboard.putString("Auton choice", allianceChoice.toString() + " " + gearPositionChoice.toString() + " " + shootChoice.toString() + " " + binChoice.toString());
		switch(allianceChoice){
		case RED: 
			switch(gearPositionChoice){
			case LEFT:
				switch(binChoice){
				case LEFT_BIN:
					switch(shootChoice) {
					case SHOOT:
						//Red Alliance, Left Gear, Dump Left Bin, Shoot
						(new LeftPegLeftStartRed(true, true)).start();
						break;
					case DONT_SHOOT:
						//Red Alliance, Left Gear, Dump Left Bin, Don't Shoot
						(new LeftPegLeftStartRed(true, true)).start();
						break;
					}
					break;
					
				case NO_BIN: 
					switch(shootChoice) {
					case SHOOT:
						//Red Alliance, Left Gear, Don't Dump Any Bin, Shoot
						(new LeftPegLeftStartRed(false,true)).start();
						break;
					case DONT_SHOOT:
						//Red Alliance, Left Gear, Don't Dump Any Bin, Don't Shoot
						(new LeftPegLeftStartRed(false,false)).start();
						break;
					}
					break;
				}
				break;
				
			case CENTER:
				switch(binChoice){
				case NO_BIN: 
					switch(shootChoice) {
					case SHOOT:
						//Red Alliance, Center Gear, Don't Dump Any Bin, Shoot
						(new CenterPegCenterStart(true)).start();
						break;
					case DONT_SHOOT:
						//Red Alliance, Center Gear, Don't Dump Any Bin, Don't Shoot
						(new CenterPegCenterStart(false)).start();
						break;
					}
					break;
				}
				break;
			case RIGHT:
				switch(binChoice){
				case RIGHT_BIN:
					switch(shootChoice) {
					case SHOOT:
						//Red Alliance, Right Gear, Dump Right Bin, Shoot
						(new RightPegRightStartRed(true, true)).start();
						break;
					case DONT_SHOOT:
						//Red Alliance, Right Gear, Dump Right Bin, Don't Shoot
						(new RightPegRightStartRed(true, false)).start();
						break;
					}
					break;
				case NO_BIN: 
					switch(shootChoice) {
					case SHOOT:
						//Red Alliance, Right Gear, Don't Dump Any Bin, Shoot
						(new RightPegRightStartRed(false, true)).start();
						break;
					case DONT_SHOOT:
						//Red Alliance, Right Gear, Don't Dump Any Bin, Don't Shoot
						(new RightPegRightStartRed(false, false)).start();
						break;
					}
					break;
				}
				break;
			case DO_NOTHING:
				//Red Alliance, Do Nothing
				switch(binChoice){
				case RIGHT_BIN:
					switch(shootChoice){
					case SHOOT:
						(new DumpBinRed(true)).start();
						break;
					case DONT_SHOOT:
						(new DumpBinRed(false)).start();
						break;
					}
					break;
				}
				break;
			}
			break;
		case BLUE:
			switch(gearPositionChoice){
			case LEFT:
				switch(binChoice){
				case LEFT_BIN:
					switch(shootChoice) {
					case SHOOT:
						//Blue Alliance, Left Gear, Dump Left Bin, Shoot
						(new LeftPegLeftStartBlue(true, true)).start();
						break;
					case DONT_SHOOT:
						//Blue Alliance, Left Gear, Dump Left Bin, Don't Shoot
						(new LeftPegLeftStartBlue(true, false)).start();
						break;
					}
					break;
				case NO_BIN: 
					switch(shootChoice) {
					case SHOOT:
						//Blue Alliance, Left Gear, Don't Dump Any Bin, Shoot
						(new LeftPegLeftStartBlue(false, true)).start();
						break;
					case DONT_SHOOT:
						//Blue Alliance, Left Gear, Don't Dump Any Bin, Don't Shoot
						(new LeftPegLeftStartBlue(false, false)).start();
						break;
					}
					break;
				}
				break;
			case CENTER:
				switch(binChoice){
				case NO_BIN: 
					switch(shootChoice) {
					case SHOOT:
						//Blue Alliance, Center Gear, Don't Dump Any Bin, Shoot
						(new CenterPegCenterStart(true)).start();
						break;
					case DONT_SHOOT:
						//Blue Alliance, Center Gear, Don't Dump Any Bin, Don't Shoot
						(new CenterPegCenterStart(false)).start();
						SmartDashboard.putString("starting auton","reached it");
						break;
					}
					break;
				}
				break;
			case RIGHT:
				switch(binChoice){
				case RIGHT_BIN:
					switch(shootChoice) {
					case SHOOT:
						//Blue Alliance, Right Gear, Dump Right Bin, Shoot
						(new RightPegRightStartBlue(true, true)).start();
						break;
					case DONT_SHOOT:
						//Blue Alliance, Right Gear, Dump Right Bin, Don't Shoot
						(new RightPegRightStartBlue(true, false)).start();
						break;
					}
					break;
				case NO_BIN: 
					switch(shootChoice) {
					case SHOOT:
						//Blue Alliance, Right Gear, Don't Dump Any Bin, Shoot
						(new RightPegRightStartBlue(false, true)).start();
						break;
					case DONT_SHOOT:
						//Blue Alliance, Right Gear, Don't Dump Any Bin, Don't Shoot
						(new RightPegRightStartBlue(false, false)).start();
						break;
					}
					break;
				}
				break;
			case DO_NOTHING:
				//Blue Alliance, Do Nothing
				switch(binChoice){
				case LEFT_BIN:
					switch(shootChoice){
					case SHOOT:
						//Blue Alliance, No Gear, Dump Left Bin, Shoot
						(new DumpBinBlue(true)).start();
						break;
					case DONT_SHOOT:
						//Blue Alliance, No Gear, Dump Left Bin, Don't Shoot
						(new DumpBinBlue(false)).start();
						break;
					}
					break;
				}
				break;
			}
			break;
		}
	}
}