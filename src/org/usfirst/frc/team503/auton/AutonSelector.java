package org.usfirst.frc.team503.auton;

import org.usfirst.frc.team503.robot.RobotState;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class creates the choosers for the auton mode, test mode, and possible teleop selectors.
 * @author Rodrigo Cardenas
 */
public class AutonSelector {

	public SendableChooser<AutonChoices.Alliances> allianceChooser;
	public SendableChooser<AutonChoices.StartPosition> startPosChooser;
	public SendableChooser<AutonChoices.Shoot> shootChooser;
	public SendableChooser<AutonChoices.BinPosition> binChooser;
	
	
	/**
	 * Constructor for the SteamworksAutonSelectors
	 */
	public AutonSelector(){
		//All of the Auton Choosers
		allianceChooser = new SendableChooser<>();
		startPosChooser = new SendableChooser<>();
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
		startPosChooser.addObject("[L] Left Peg", AutonChoices.StartPosition.LEFT);
		startPosChooser.addDefault("[C] Center Peg", AutonChoices.StartPosition.CENTER);
		startPosChooser.addObject("[R] Right Peg", AutonChoices.StartPosition.RIGHT);
		startPosChooser.addObject("[B] Bin", AutonChoices.StartPosition.BIN);
		startPosChooser.addObject("[D] Dual Bin", AutonChoices.StartPosition.DUAL_BIN);
		
		
		//Whether or not to shoot
		shootChooser.addDefault("[Y] Shoot", AutonChoices.Shoot.SHOOT);
		shootChooser.addObject("[N] Don't Shoot", AutonChoices.Shoot.DONT_SHOOT);
		
		//If we dump a bin, then which bin
		binChooser.addObject("[L] Left Bin", AutonChoices.BinPosition.LEFT_BIN);
		binChooser.addObject("[R] Right Bin", AutonChoices.BinPosition.RIGHT_BIN);
		binChooser.addDefault("[N] Don't dump any Bin", AutonChoices.BinPosition.NO_BIN);
		
		//Putting all of the SendableChoosers onto the SmartDashboard
		SmartDashboard.putData("Choose Alliance", allianceChooser);
		SmartDashboard.putData("Choose Gear Position", startPosChooser);
		SmartDashboard.putData("Shoot This auton?", shootChooser);
		SmartDashboard.putData("Choose Bin to Dump", binChooser);
	}
	
	/**
	 * 
	 */
	public void startAuton() {
//		switch(RobotState.getInstance().getDoNothingOption()){
//		case DO_NOTHING:
//			break;
//		case DO_SOMETHING:
//			switch(RobotState.getInstance().getAllianceColorOption()){
//			case RED:
//				switch(RobotState.getInstance().getPegOption()){
//				case CENTER:
//					switch(RobotState.getInstance().getShootOption()){
//					case SHOOT:
//						switch(RobotState.getInstance().getDumpBinOption()){
//						case DUMP_BIN:
//							System.out.println("Running Red, center, shoot, dump");
//							break;
//						case DONT_DUMP_BIN:
//							System.out.println("Running Red, center, shoot, dont dump");
//							break;
//						}
//						break;
//					case DONT_SHOOT:
//						switch(RobotState.getInstance().getDumpBinOption()){
//						case DUMP_BIN:
//							System.out.println("Running Red, center, dont shoot, dump");
//							break;
//						case DONT_DUMP_BIN:
//							System.out.println("Running Red, center, dont shoot, dont dump");
//							break;
//						}
//						break;
//					}
//					break;
//				case LEFT:
//					switch(RobotState.getInstance().getShootOption()){
//					case SHOOT:
//						switch(RobotState.getInstance().getDumpBinOption()){
//						case DUMP_BIN:
//							System.out.println("Running Red, left, shoot, dump");
//							break;
//						case DONT_DUMP_BIN:
//							System.out.println("Running Red, left, shoot, dont dump");
//							break;
//						}
//						break;
//					case DONT_SHOOT:
//						switch(RobotState.getInstance().getDumpBinOption()){
//						case DUMP_BIN:
//							System.out.println("Running Red, left, dont shoot, dump");
//							break;
//						case DONT_DUMP_BIN:
//							System.out.println("Running Red, left, dont shoot, dont dump");
//							break;
//						}
//						break;
//					}
//					break;
//				case RIGHT:
//					switch(RobotState.getInstance().getShootOption()){
//					case SHOOT:
//						switch(RobotState.getInstance().getDumpBinOption()){
//						case DUMP_BIN:
//							System.out.println("Running Red, right, shoot, dump");
//							break;
//						case DONT_DUMP_BIN:
//							System.out.println("Running Red, right, shoot, dont dump");
//							break;
//						}
//						break;
//					case DONT_SHOOT:
//						switch(RobotState.getInstance().getDumpBinOption()){
//						case DUMP_BIN:
//							System.out.println("Running Red, right, dont shoot, dump");
//							break;
//						case DONT_DUMP_BIN:
//							System.out.println("Running Red, right, shoot, dont dump");
//							break;
//						}
//						break;
//					}
//					break;
//				case NONE:
//					switch(RobotState.getInstance().getShootOption()){
//					case SHOOT:
//						switch(RobotState.getInstance().getDumpBinOption()){
//						case DUMP_BIN:
//							System.out.println("Running Red, no peg, shoot, dump");
//							break;
//						case DONT_DUMP_BIN:
//							System.out.println("Running Red, no peg, shoot, dont dump");
//							break;
//						}
//						break;
//					case DONT_SHOOT:
//						switch(RobotState.getInstance().getDumpBinOption()){
//						case DUMP_BIN:
//							System.out.println("Running Red, no peg, dont shoot, dump");
//							break;
//						case DONT_DUMP_BIN:
//							System.out.println("Running Red, no peg, dont shoot, dont dump");
//							break;
//						}
//						break;
//					}
//					break;
//				}
//				break;
//			case BLUE:
//				switch(RobotState.getInstance().getPegOption()){
//				case CENTER:
//					switch(RobotState.getInstance().getShootOption()){
//					case SHOOT:
//						switch(RobotState.getInstance().getDumpBinOption()){
//						case DUMP_BIN:
//							System.out.println("Running Blue,Center, shoot, dump");
//							break;
//						case DONT_DUMP_BIN:
//							System.out.println("Running Blue,Center, shoot, dont dump");
//							break;
//						}
//						break;
//					case DONT_SHOOT:
//						switch(RobotState.getInstance().getDumpBinOption()){
//						case DUMP_BIN:
//							System.out.println("Running Blue,Center, dont shoot, dump");
//							break;
//						case DONT_DUMP_BIN:
//							System.out.println("Running Blue,Center, dont shoot, dont dump");
//							break;
//						}
//						break;
//					}
//					break;
//				case LEFT:
//					switch(RobotState.getInstance().getShootOption()){
//					case SHOOT:
//						switch(RobotState.getInstance().getDumpBinOption()){
//						case DUMP_BIN:
//							System.out.println("Running Blue, Left, shoot, dump");
//							break;
//						case DONT_DUMP_BIN:
//							System.out.println("Running Blue, Left, shoot, dont dump");
//							break;
//						}
//						break;
//					case DONT_SHOOT:
//						switch(RobotState.getInstance().getDumpBinOption()){
//						case DUMP_BIN:
//							System.out.println("Running Blue,left, dont shoot, dump");
//							break;
//						case DONT_DUMP_BIN:
//							System.out.println("Running Blue,left, dont shoot, dont dump");
//							break;
//						}
//						break;
//					}
//					break;
//				case RIGHT:
//					switch(RobotState.getInstance().getShootOption()){
//					case SHOOT:
//						switch(RobotState.getInstance().getDumpBinOption()){
//						case DUMP_BIN:
//							System.out.println("Running Blue,Right, shoot, dump");
//							break;
//						case DONT_DUMP_BIN:
//							System.out.println("Running Blue,Right, shoot, dont dump");
//							break;
//						}
//						break;
//					case DONT_SHOOT:
//						switch(RobotState.getInstance().getDumpBinOption()){
//						case DUMP_BIN:
//							System.out.println("Running Blue, right, dont shoot, dump");
//							break;
//						case DONT_DUMP_BIN:
//							System.out.println("Running Blue, right, dont shoot, dont dump");
//							break;
//						}
//						break;
//					}
//					break;
//				case NONE:
//					switch(RobotState.getInstance().getShootOption()){
//					case SHOOT:
//						switch(RobotState.getInstance().getDumpBinOption()){
//						case DUMP_BIN:
//							System.out.println("Running Blue, no peg, shoot, dump");
//							break;
//						case DONT_DUMP_BIN:
//							System.out.println("Running Blue,no peg,  shoot,  dont dump");
//							break;
//						}
//						break;
//					case DONT_SHOOT:
//						switch(RobotState.getInstance().getDumpBinOption()){
//						case DUMP_BIN:
//							System.out.println("Running Blue, no peg, dont shoot, dump");
//							break;
//						case DONT_DUMP_BIN:
//							System.out.println("Running Blue, no peg, dont shoot, dont dump");
//							break;
//						}
//						break;
//					}
//					break;
//				}
//				break;
//			}
//			break;
//		}
		
		AutonChoices.Alliances allianceChoice = allianceChooser.getSelected();
		AutonChoices.StartPosition gearPositionChoice = startPosChooser.getSelected();
		AutonChoices.Shoot shootChoice = shootChooser.getSelected();
		AutonChoices.BinPosition binChoice = binChooser.getSelected();
		
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
						(new CenterPegCenterStartRed(true)).start();
						break;
					case DONT_SHOOT:
						//Red Alliance, Center Gear, Don't Dump Any Bin, Don't Shoot
						(new CenterPegCenterStartRed(false)).start();
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
						(new ArcRightPegRed(true, true)).start();
						break;
					case DONT_SHOOT:
						//Red Alliance, Right Gear, Dump Right Bin, Don't Shoot
						(new ArcRightPegRed(false, true)).start();
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
			case BIN:
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
			case DUAL_BIN:
				switch(binChoice){
				case RIGHT_BIN:
					switch(shootChoice){
					case SHOOT:
						(new DualHopperRed(true)).start();
						break;
					case DONT_SHOOT:
						(new DualHopperRed(false)).start();
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
						(new ArcLeftPegBlue(true, true)).start();
						break;
					case DONT_SHOOT:
						//Blue Alliance, Left Gear, Dump Left Bin, Don't Shoot
						(new ArcLeftPegBlue(false, true)).start();
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
						(new CenterPegCenterStartBlue(true)).start();
						break;
					case DONT_SHOOT:
						//Blue Alliance, Center Gear, Don't Dump Any Bin, Don't Shoot
						(new CenterPegCenterStartBlue(false)).start();
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
			case BIN:
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
			case DUAL_BIN:
				switch(binChoice){
				case LEFT_BIN:
					switch(shootChoice){
						case SHOOT:
							(new DualHopperBlue(true)).start();
							break;
						case DONT_SHOOT:
							(new DualHopperBlue(false)).start();
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