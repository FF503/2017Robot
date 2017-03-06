package org.usfirst.frc.team503.auton.autonHelpers;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 * Name:		 AutonChoices 
 * Purpose:		 This class contains all of the possible choices that an Auton period can consist of.
 * Author:		 Rodrigo Cardenas
 * Date:		 February 2017 
 * Comments:
 */

public class AutonChoices{
	
	public enum Alliances implements Sendable {
		RED, BLUE,;

		@Override
		public void initTable(ITable subtable) { }
		@Override
		public ITable getTable() { return null; }
		@Override
		public String getSmartDashboardType() { return null; }
	}
	
	public enum GearPosition implements Sendable {
		LEFT, CENTER, RIGHT, DO_NOTHING;

		@Override
		public void initTable(ITable subtable) { }
		@Override
		public ITable getTable() { return null; }
		@Override
		public String getSmartDashboardType() { return null; }
	}
	
	public enum DumpBin implements Sendable {
		DUMP, DONT_DUMP;

		@Override
		public void initTable(ITable subtable) { }
		@Override
		public ITable getTable() { return null; }
		@Override
		public String getSmartDashboardType() { return null; }
	}
	
	public enum Shoot implements Sendable {
		SHOOT, DONT_SHOOT;

		@Override
		public void initTable(ITable subtable) { }
		@Override
		public ITable getTable() { return null; }
		@Override
		public String getSmartDashboardType() { return null; }
	}
	
	public enum BinPosition implements Sendable {
		CLOSE_LEFT_BIN, CLOSE_RIGHT_BIN, FAR_LEFT_BIN, FAR_RIGHT_BIN, NONE;

		@Override
		public void initTable(ITable subtable) { }
		@Override
		public ITable getTable() { return null; }
		@Override
		public String getSmartDashboardType() { return null; }
		
	}
	
	public enum DoNothing implements Sendable {
		DO_SOMETHING, DO_NOTHING;

		@Override
		public void initTable(ITable subtable) { }

		@Override
		public ITable getTable() { return null; }

		@Override
		public String getSmartDashboardType() { return null; }
		
	}
}
