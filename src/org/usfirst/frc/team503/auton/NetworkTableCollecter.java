package org.usfirst.frc.team503.auton;

import org.usfirst.frc.team503.robot.RobotState;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class NetworkTableCollecter {
	private NetworkTable table; 
	
	public NetworkTableCollecter(){
		table = NetworkTable.getTable("AutonChoices");		
	}
	
	public void getNetworkTableData(){
		RobotState.getInstance().set
	}
	
	
}
