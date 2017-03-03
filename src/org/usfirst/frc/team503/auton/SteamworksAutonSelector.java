package org.usfirst.frc.team503.auton;

//import org.usfirst.frc.team503.test.*;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Name:		 SteamworksAutonSelector
 * Purpose:		 This class creates the choosers for the auton mode, test mode, and possible teleop selectors.
 * Author:		 Rodrigo Cardenas
 * Date:		 February 2017 
 * Comments:
 */
public class SteamworksAutonSelector {

	private SendableChooser<AutonChoices.Alliances> allianceChooser;
	private SendableChooser<AutonChoices.GearPosition> gearPosChooser;
	private SendableChooser<AutonChoices.DumpBin> dumpBinChooser;
	private SendableChooser<AutonChoices.Shoot> shootChooser;
	private SendableChooser<AutonChoices.BinPosition> binChooser;
	private SendableChooser<AutonChoices.DoNothing> doSomethingChooser;
	
	
	/**
	 * Constructor for the SteamworksAutonSelectors
	 */
	public SteamworksAutonSelector(){
		//All of the Auton Choosers
		allianceChooser = new SendableChooser<>();
		gearPosChooser = new SendableChooser<>();
		dumpBinChooser = new SendableChooser<>();
		shootChooser = new SendableChooser<>();
		binChooser = new SendableChooser<>();
		doSomethingChooser = new SendableChooser<>();
		
	}
	
	private static SteamworksAutonSelector instance = new SteamworksAutonSelector();
	
	public static SteamworksAutonSelector getInstance() {
		return instance;
	}
	/**
	 * This method puts all of the options into the SendableChoosers and puts them out in the SmartDashboard
	 */
	public void putAutonChoosers() {
		//The Alliance choices
		allianceChooser.addDefault("[R] Red", AutonChoices.Alliances.RED);
		allianceChooser.addObject("[B] Blue", AutonChoices.Alliances.BLUE);
		
		//Whether we do something or not
		doSomethingChooser.addDefault("[S] Do Something", AutonChoices.DoNothing.DO_SOMETHING);
		doSomethingChooser.addObject("[N] Do Nothing", AutonChoices.DoNothing.DO_NOTHING);
		
		//The gear position choices
		gearPosChooser.addObject("[L] Left", AutonChoices.GearPosition.LEFT);
		gearPosChooser.addDefault("[C] Center", AutonChoices.GearPosition.CENTER);
		gearPosChooser.addObject("[R] Right", AutonChoices.GearPosition.RIGHT);
		gearPosChooser.addObject("[N] Do Nothing", AutonChoices.GearPosition.DO_NOTHING);
		
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
		binChooser.addDefault("[N] Don't dump any Bin", AutonChoices.BinPosition.NONE);
		
		//Putting all of the SendableChoosers onto the SmartDashboard
		SmartDashboard.putData("Choose Alliance", allianceChooser);
		SmartDashboard.putData("Choose Gear Position", gearPosChooser);
		SmartDashboard.putData("Dump Bin this Auton?", dumpBinChooser);
		SmartDashboard.putData("Shoot this auton?", shootChooser);
		SmartDashboard.putData("Choose Bin to Dump", binChooser);
		SmartDashboard.putData("Do Something this Auton?", doSomethingChooser);
	}
	
	/**
	 * 
	 */
	public void startAuton() {
		AutonChoices.Alliances allianceChoice = allianceChooser.getSelected();
		AutonChoices.GearPosition gearPositionChoice = gearPosChooser.getSelected();
		AutonChoices.DumpBin dumpBinChoice = dumpBinChooser.getSelected();
		AutonChoices.Shoot shootChoice = shootChooser.getSelected();
		AutonChoices.BinPosition binChoice = binChooser.getSelected();
		AutonChoices.DoNothing doSomethingChoice = doSomethingChooser.getSelected();
		
		Command selectedAutonCommand = null;
		/* Brace yourself for a lot of nested if-else statements
		 * This is where the commands are determined 
		 * 
		 */
		if(allianceChoice == AutonChoices.Alliances.RED) {
			if(doSomethingChoice == AutonChoices.DoNothing.DO_SOMETHING) {
				if(gearPositionChoice == AutonChoices.GearPosition.LEFT) {
					if(shootChoice == AutonChoices.Shoot.SHOOT) {
						if(dumpBinChoice == AutonChoices.DumpBin.DUMP) {
							if(binChoice == AutonChoices.BinPosition.CLOSE_LEFT_BIN) {
								//Red, Left Gear, Shoot, Dump, Close left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.CLOSE_RIGHT_BIN) {
								//Red, Left Gear, Shoot, Dump, Close Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.FAR_LEFT_BIN) {
								//Red, Left Gear, Shoot, Dump, Far left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							} 
							else if (binChoice == AutonChoices.BinPosition.FAR_RIGHT_BIN){
								//Red, Left Gear, Shoot, Dump, Far Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
						}
						else {
							//Red, Left Gear, Shoot, Don't Dump
							SmartDashboard.putBoolean("DummyAuton", false);
						}
					}
					else {
						if(dumpBinChoice == AutonChoices.DumpBin.DUMP) {
							if(binChoice == AutonChoices.BinPosition.CLOSE_LEFT_BIN) {
								//Red, Left Gear, Don't Shoot, Dump, Close left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.CLOSE_RIGHT_BIN) {
								//Red, Left Gear, Don't Shoot, Dump, Close Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.FAR_LEFT_BIN) {
								//Red, Left Gear, Don't Shoot, Dump, Far left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							} 
							else if (binChoice == AutonChoices.BinPosition.FAR_RIGHT_BIN){
								//Red, Left Gear, Don't Shoot, Dump, Far Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
						}
						else {
							//Red, Left Gear, Don't Shoot, Don't Dump
							SmartDashboard.putBoolean("DummyAuton", false);
						}
					}
				}
				else if(gearPositionChoice == AutonChoices.GearPosition.CENTER) {
					if(shootChoice == AutonChoices.Shoot.SHOOT) {
						if(dumpBinChoice == AutonChoices.DumpBin.DUMP) {
							if(binChoice == AutonChoices.BinPosition.CLOSE_LEFT_BIN) {
								//Red, Center Gear, Shoot, Dump, Close left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.CLOSE_RIGHT_BIN) {
								//Red, Center Gear, Shoot, Dump, Close Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.FAR_LEFT_BIN) {
								//Red, Center Gear, Shoot, Dump, Far left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							} 
							else if (binChoice == AutonChoices.BinPosition.FAR_RIGHT_BIN){
								//Red, Center Gear, Shoot, Dump, Far Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
						}
						else {
							//Red, Center Gear, Shoot, Don't Dump
							SmartDashboard.putBoolean("DummyAuton", false);
						}
					}
					else {
						if(dumpBinChoice == AutonChoices.DumpBin.DUMP) {
							if(binChoice == AutonChoices.BinPosition.CLOSE_LEFT_BIN) {
								//Red, Center Gear, Don't Shoot, Dump, Far Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
								}
							else if (binChoice == AutonChoices.BinPosition.CLOSE_RIGHT_BIN) {
								//Red, Center Gear, Don't Shoot, Dump, Close Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.FAR_LEFT_BIN) {
								//Red, Center Gear, Don't Shoot, Dump, Far left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							} 
							else if (binChoice == AutonChoices.BinPosition.FAR_RIGHT_BIN){
								//Red, Center Gear, Don't Shoot, Dump, Far Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
						}
						else {
							//Red, Center Gear, Don't Shoot, Don't Dump
							SmartDashboard.putBoolean("DummyAuton", false);
						}
					}
				} 
				else {
					if(shootChoice == AutonChoices.Shoot.SHOOT) {
						if(dumpBinChoice == AutonChoices.DumpBin.DUMP) {
							if(binChoice == AutonChoices.BinPosition.CLOSE_LEFT_BIN) {
								//Red, Right Gear, Shoot, Dump, Close left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.CLOSE_RIGHT_BIN) {
								//Red, Right Gear, Shoot, Dump, Close Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.FAR_LEFT_BIN) {
								//Red, Right Gear, Shoot, Dump, Far left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							} 
							else if (binChoice == AutonChoices.BinPosition.FAR_RIGHT_BIN){
								//Red, Right Gear, Shoot, Dump, Far Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
						}
						else {
							//Red, Right Gear, Shoot, Don't Dump
							SmartDashboard.putBoolean("DummyAuton", false);
						}
					}
					else {
						if(dumpBinChoice == AutonChoices.DumpBin.DUMP) {
							if(binChoice == AutonChoices.BinPosition.CLOSE_LEFT_BIN) {
								//Red, Right Gear, Shoot, Dump, Close left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.CLOSE_RIGHT_BIN) {
								//Red, Right Gear, Shoot, Dump, Close Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.FAR_LEFT_BIN) {
								//Red, Right Gear, Shoot, Dump, Far left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							} 
							else if (binChoice == AutonChoices.BinPosition.FAR_RIGHT_BIN){
								//Red, Right Gear, Shoot, Dump, Far Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
						}
						else {
							//Red, Right Gear, Don't Shoot, Don't Dump
							SmartDashboard.putBoolean("DummyAuton", false);
						}
					}
				}
			}
			else {
				//Red, Do Nothing
				//selectedAutonCommand = new DummyAuton();
				
			}
		}
		else {
			if(doSomethingChoice == AutonChoices.DoNothing.DO_SOMETHING) {
				if(gearPositionChoice == AutonChoices.GearPosition.LEFT) {
					if(shootChoice == AutonChoices.Shoot.SHOOT) {
						if(dumpBinChoice == AutonChoices.DumpBin.DUMP) {
							if(binChoice == AutonChoices.BinPosition.CLOSE_LEFT_BIN) {
								//Blue, Left Gear, Shoot, Dump, Close left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.CLOSE_RIGHT_BIN) {
								//Blue, Left Gear, Shoot, Dump, Close Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.FAR_LEFT_BIN) {
								//Blue, Left Gear, Shoot, Dump, Far left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							} 
							else if (binChoice == AutonChoices.BinPosition.FAR_RIGHT_BIN){
								//Blue, Left Gear, Shoot, Dump, Far Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
						}
						else {
							//Blue, Left Gear, Shoot, Don't Dump
							SmartDashboard.putBoolean("DummyAuton", false);
						}
					}
					else {
						if(dumpBinChoice == AutonChoices.DumpBin.DUMP) {
							if(binChoice == AutonChoices.BinPosition.CLOSE_LEFT_BIN) {
								//Blue, Left Gear, Don't Shoot, Dump, Close left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.CLOSE_RIGHT_BIN) {
								//Blue, Left Gear, Don't Shoot, Dump, Close Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.FAR_LEFT_BIN) {
								//Blue, Left Gear, Don't Shoot, Dump, Far left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							} 
							else if (binChoice == AutonChoices.BinPosition.FAR_RIGHT_BIN){
								//Blue, Left Gear, Don't Shoot, Dump, Far Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
						}
						else {
							//Blue, Left Gear, Don't Shoot, Don't Dump
							SmartDashboard.putBoolean("DummyAuton", false);
						}
					}
				}
				else if(gearPositionChoice == AutonChoices.GearPosition.CENTER) {
					if(shootChoice == AutonChoices.Shoot.SHOOT) {
						if(dumpBinChoice == AutonChoices.DumpBin.DUMP) {
							if(binChoice == AutonChoices.BinPosition.CLOSE_LEFT_BIN) {
								//Blue, Center Gear, Shoot, Dump, Close left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.CLOSE_RIGHT_BIN) {
								//Blue, Center Gear, Shoot, Dump, Close Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.FAR_LEFT_BIN) {
								//Blue, Center Gear, Shoot, Dump, Far left Bin
								
							} 
							else if (binChoice == AutonChoices.BinPosition.FAR_RIGHT_BIN){
								//Blue, Center Gear, Shoot, Dump, Far Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
						}
						else {
							//Blue, Center Gear, Shoot, Don't Dump
							SmartDashboard.putBoolean("DummyAuton", false);
						}
					}
					else {
						if(dumpBinChoice == AutonChoices.DumpBin.DUMP) {
							if(binChoice == AutonChoices.BinPosition.CLOSE_LEFT_BIN) {
								//Blue, Center Gear, Don't Shoot, Dump, Far Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
								}
							else if (binChoice == AutonChoices.BinPosition.CLOSE_RIGHT_BIN) {
								//Blue, Center Gear, Don't Shoot, Dump, Close Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.FAR_LEFT_BIN) {
								//Blue, Center Gear, Don't Shoot, Dump, Far left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							} 
							else if (binChoice == AutonChoices.BinPosition.FAR_RIGHT_BIN){
								//Blue, Center Gear, Don't Shoot, Dump, Far Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
						}
						else {
							//Blue, Center Gear, Don't Shoot, Don't Dump
							SmartDashboard.putBoolean("DummyAuton", false);
						}
					}
				} 
				else {
					if(shootChoice == AutonChoices.Shoot.SHOOT) {
						if(dumpBinChoice == AutonChoices.DumpBin.DUMP) {
							if(binChoice == AutonChoices.BinPosition.CLOSE_LEFT_BIN) {
								//Blue, Right Gear, Shoot, Dump, Close left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.CLOSE_RIGHT_BIN) {
								//Blue, Right Gear, Shoot, Dump, Close Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.FAR_LEFT_BIN) {
								//Blue, Right Gear, Shoot, Dump, Far left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							} 
							else if (binChoice == AutonChoices.BinPosition.FAR_RIGHT_BIN){
								//Blue, Right Gear, Shoot, Dump, Far Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
						}
						else {
							//Blue, Right Gear, Shoot, Don't Dump
							SmartDashboard.putBoolean("DummyAuton", false);
						}
					}
					else {
						if(dumpBinChoice == AutonChoices.DumpBin.DUMP) {
							if(binChoice == AutonChoices.BinPosition.CLOSE_LEFT_BIN) {
								//Blue, Right Gear, Shoot, Dump, Close left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.CLOSE_RIGHT_BIN) {
								//Blue, Right Gear, Shoot, Dump, Close Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
							else if (binChoice == AutonChoices.BinPosition.FAR_LEFT_BIN) {
								//Blue, Right Gear, Shoot, Dump, Far left Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							} 
							else if (binChoice == AutonChoices.BinPosition.FAR_RIGHT_BIN){
								//Blue, Right Gear, Shoot, Dump, Far Right Bin
								SmartDashboard.putBoolean("DummyAuton", false);
							}
						}
						else {
							//Blue, Right Gear, Don't Shoot, Don't Dump
							SmartDashboard.putBoolean("DummyAuton", false);
						}
					}
				}
			}
			else {
				//Blue, Do Nothing
				SmartDashboard.putBoolean("DummyAuton", false);
			}
		}
		if(selectedAutonCommand != null)
			selectedAutonCommand.start();
		else
			SmartDashboard.putBoolean("DummyAuton", false);
		
	}
	
	/**
	 * This method will put out the test Commands
	 */
	public void putTestCommands() {
		/*SmartDashboard.putData("Test Shooter", new TestShooter());
		SmartDashboard.putData("Test Turret", new DummyTest());
		SmartDashboard.putData("Test Delfector", new TestDeflector());
		SmartDashboard.putData("Test Intake", new DummyTest());
		
		SmartDashboard.putData("Test Climber", new TestClimber());
		SmartDashboard.putData("Test Gear Placer", new TestGearPlacer());
		SmartDashboard.putData("Test Indexer", new TestIndexer());*/
	}
}