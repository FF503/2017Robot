package org.usfirst.frc.team503.auton;

import org.usfirst.frc.team503.commands.DriveStraightDistanceCommand;
import org.usfirst.frc.team503.commands.GyroTurnCommand;
import org.usfirst.frc.team503.commands.PlaceGearCommand;
import org.usfirst.frc.team503.commands.PushFourthWallOutCommand;
import org.usfirst.frc.team503.commands.RaiseGearPlacer;
import org.usfirst.frc.team503.commands.SetReadyToFire;
import org.usfirst.frc.team503.commands.ShootSequenceCommand;
import org.usfirst.frc.team503.robot.RobotState;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DumpBinRed extends CommandGroup {

    public DumpBinRed(boolean shoot) {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems
    	//that each member

        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	double[][] dumpbinForward = {
				{0, 22.5},
				{-4.3, 22.5} //6.6
		};
    	
    	double[][] hitBin ={
    			{0,0},
    			{3.9,0}
    	};
    	
    	addParallel(new RaiseGearPlacer()); 
    	addParallel(new PushFourthWallOutCommand());
		addSequential(new DriveStraightDistanceCommand(126, 3.0,true));//110 at worlds //120 at states
		if(shoot){
			RobotState.getInstance().setShootingPreset(RobotState.ShootingPresets.HopperRed);
			addSequential(new ShootSequenceCommand(true));
			addParallel(new ShootSequenceCommand(false));
			
		}
		addSequential(new GyroTurnCommand(-90));
		addSequential(new DriveStraightDistanceCommand(48,2.5,false));//42
		addSequential(new SetReadyToFire());
		
		
    }
}