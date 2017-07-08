package org.usfirst.frc.team503.auton;

import org.usfirst.frc.team503.commands.DriveStraightDistanceCommand;
import org.usfirst.frc.team503.commands.PlaceGearCommand;
import org.usfirst.frc.team503.commands.PushFourthWallOutCommand;
import org.usfirst.frc.team503.commands.RaiseGearPlacer;
import org.usfirst.frc.team503.commands.SetReadyToFire;
import org.usfirst.frc.team503.commands.ShootSequenceCommand;
import org.usfirst.frc.team503.robot.RobotState;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterPegCenterStartBlue extends CommandGroup {

    public CenterPegCenterStartBlue(boolean shoot) {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	double[][] centerPinCenterStart = {
				{0, 13.5},
				{-4, 13.5}
		};
    	
    	double[][] backUpFromCenterPin = {
    			{0,13.5},
    			{5,13.5}
    	};
    	
    	addParallel(new RaiseGearPlacer());  
		addSequential(new DriveStraightDistanceCommand(48,2.0,true));
		if(shoot){
			RobotState.getInstance().setShootingPreset(RobotState.ShootingPresets.CenterPegBlue); 
			addSequential(new ShootSequenceCommand(true));
			addParallel(new ShootSequenceCommand(false));
		}
		addSequential(new AutonDriveCommand2());
		addSequential(new PlaceGearCommand());
		
		if(shoot){
			addSequential(new DriveStraightDistanceCommand(72,2.0,false));//48
			addSequential(new SetReadyToFire());
		}
		else{
			addSequential(new DriveStraightDistanceCommand(12,1.0,false));
		}
			
    }
}