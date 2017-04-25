package org.usfirst.frc.team503.auton;

import org.usfirst.frc.team503.commands.DriveStraightDistanceCommand;
import org.usfirst.frc.team503.commands.GyroTurnCommand;
import org.usfirst.frc.team503.commands.PlaceGearCommand;
import org.usfirst.frc.team503.commands.RaiseGearPlacer;
import org.usfirst.frc.team503.commands.SetReadyToFire;
import org.usfirst.frc.team503.commands.ShootSequenceCommand;
import org.usfirst.frc.team503.robot.RobotState;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftPegLeftStartRed extends CommandGroup {

    public LeftPegLeftStartRed(boolean dump, boolean shoot) {
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
    	double[][] leftPinLeftStart = {
    			{0, 22.5},
				{-5.6, 22.5}
		};
    	
    	double[][] backUpFromPin = {
    			{0,0},
    			{5.5,0}
    	};
    	
    	double[][] dumpbinForward = {
				{0, 22.5},
				{3.5, 22.5}
		};
    	
    	double[][] hitBin ={
    			{0,0},
    			{4.5,0}
    	};    
    	
    	addParallel(new RaiseGearPlacer());    	
		//addSequential(new RunMotionProfileCommand(leftPinLeftStart, 2, 1, true));
    	addSequential(new DriveStraightDistanceCommand(58.5, 3.0, true));
    	if(shoot && !dump){
    		RobotState.getInstance().setShootingPreset(RobotState.ShootingPresets.FarPegRed);
    		addSequential(new ShootSequenceCommand(true));
    		addParallel(new ShootSequenceCommand(false));
    	}
		addSequential(new GyroTurnCommand(55));
		addSequential(new DriveStraightDistanceCommand(36,2.0,true));
		addSequential(new AutonDriveCommand2());
		addSequential(new PlaceGearCommand());
		
		if (dump){
		//	addSequential(new RunMotionProfileCommand(dumpBinForward, 2, 1, false)); //1.5
			addSequential(new DriveStraightDistanceCommand(66, 3.5, false));
			addSequential(new GyroTurnCommand(30, true));
			addSequential(new DriveStraightDistanceCommand(44.4, 3.5, false));
			if(shoot){
				
			}
		}
		else if(shoot){
			addSequential(new DriveStraightDistanceCommand(24,1.0,false));
			addSequential(new GyroTurnCommand(-55));
			addSequential(new DriveStraightDistanceCommand(80, 4.0, false));
			addSequential(new SetReadyToFire());
		}
		else{
			addSequential(new DriveStraightDistanceCommand(12,1.0,false));
			
		}
    }
}