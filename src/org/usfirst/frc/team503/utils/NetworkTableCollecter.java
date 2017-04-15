package org.usfirst.frc.team503.utils;

import org.usfirst.frc.team503.robot.RobotState;
import org.usfirst.frc.team503.robot.RobotState.AllianceColor;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class NetworkTableCollecter {
	private  NetworkTable table; 
	private  String rawAlliance, rawPeg;
	private  boolean rawShoot, rawBin, rawDoNothing;
	
	public NetworkTableCollecter(){
		table = NetworkTable.getTable("AutonChoices");	
	}
	
	public void collectNetworkTableDate(){
		//get raw primitive type vars from network tables
		rawAlliance = table.getString("alliance","");
		rawPeg = table.getString("peg","");
		rawShoot = table.getBoolean("shoot", false);
		rawBin = table.getBoolean("hopper",false);
		rawDoNothing = table.getBoolean("doNothing", true);
		
		//convert to RobotState settings
		if (rawAlliance.equals("blue")){
			RobotState.getInstance().setAllianceColorOption(RobotState.AllianceColor.BLUE);
		}
		else {
			RobotState.getInstance().setAllianceColorOption(RobotState.AllianceColor.RED);
		}
		
		if (rawDoNothing){
			RobotState.getInstance().setDoNothingAuton(RobotState.DoNothingAuton.DO_NOTHING);
		}
		else{
			RobotState.getInstance().setDoNothingAuton(RobotState.DoNothingAuton.DO_SOMETHING);
		}
		
		if(rawPeg.equals("left")){
			RobotState.getInstance().setPegOption(RobotState.Peg.LEFT);
		}
		else if(rawPeg.equals("right")){
			RobotState.getInstance().setPegOption(RobotState.Peg.RIGHT);
		}
		else if(rawPeg.equals("center")){
			RobotState.getInstance().setPegOption(RobotState.Peg.CENTER);
		}
		else{
			RobotState.getInstance().setPegOption(RobotState.Peg.NONE);
		}
		
		if (rawBin){
			RobotState.getInstance().setDumpBinOption(RobotState.DumpBin.DUMP_BIN);
		}
		else{
			RobotState.getInstance().setDumpBinOption(RobotState.DumpBin.DONT_DUMP_BIN);
		}
		
		if(rawShoot){
			RobotState.getInstance().setShootOption(RobotState.Shoot.SHOOT);
		}
		else{
			RobotState.getInstance().setShootOption(RobotState.Shoot.DONT_SHOOT);
		}
	}
	
	
}
