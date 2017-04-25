package org.usfirst.frc.team503.auton;

import org.usfirst.frc.team503.commands.ArcDriveCommand;
import org.usfirst.frc.team503.commands.DriveStraightDistanceCommand;
import org.usfirst.frc.team503.commands.GyroTurnCommand;
import org.usfirst.frc.team503.commands.PlaceGearCommand;
import org.usfirst.frc.team503.commands.RaiseGearPlacer;
import org.usfirst.frc.team503.commands.SetReadyToFire;
import org.usfirst.frc.team503.commands.ShootSequenceCommand;
import org.usfirst.frc.team503.robot.RobotState;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class ArcRightPegRed extends CommandGroup {

    public ArcRightPegRed(boolean shoot, boolean dump) {
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
    	
    	addParallel(new RaiseGearPlacer());
    	addSequential(new ArcDriveCommand(195.0, -60.0, 30.0, 0.35, 3.5, true));//175 first run
    	if(shoot){
    		if(dump){
    	    	RobotState.getInstance().setShootingPreset(RobotState.ShootingPresets.HopperRed);
    		}
    		else{
    			RobotState.getInstance().setShootingPreset(RobotState.ShootingPresets.PegNearHopperRed);
    		}
        	addSequential(new ShootSequenceCommand(true));
        	addParallel(new ShootSequenceCommand(false));
    	}
    	addSequential(new WaitCommand(0.25));
    	addSequential(new PlaceGearCommand());
    	addSequential(new DriveStraightDistanceCommand(24, 0.5, false));
		if(dump){
			addSequential(new GyroTurnCommand(-45, true));
			addSequential(new DriveStraightDistanceCommand(88, 3.0, false));
			if(shoot){
				addSequential(new SetReadyToFire());
			}
		}
		else if(shoot){
			addSequential(new SetReadyToFire());
		}
    }
}
