package org.usfirst.frc.team503.auton;

import org.usfirst.frc.team503.commands.ArcDriveCommand;
import org.usfirst.frc.team503.commands.DriveStraightDistanceCommand;
import org.usfirst.frc.team503.commands.GyroTurnCommand;
import org.usfirst.frc.team503.commands.RaiseGearPlacer;
import org.usfirst.frc.team503.commands.SetReadyToFire;
import org.usfirst.frc.team503.commands.ShootSequenceCommand;
import org.usfirst.frc.team503.robot.RobotState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DualHopperRed extends CommandGroup {

    public DualHopperRed(boolean shoot) {
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
		//addSequential(new DriveStraightDistanceCommand(110, 3.0,true));//120 at states
    	addSequential(new ArcDriveCommand(270, 90, 130, .3, 3.0, false));
		if(shoot){
			RobotState.getInstance().setShootingPreset(RobotState.ShootingPresets.HopperRed);
			addSequential(new ShootSequenceCommand(true));
			addParallel(new ShootSequenceCommand(false));
			
		}
		/*addSequential(new GyroTurnCommand(-90));
		addSequential(new DriveStraightDistanceCommand(96,3.0,false));//42
*/		if(shoot){
			addSequential(new SetReadyToFire());
		}
    }
}
