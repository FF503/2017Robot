package org.usfirst.frc.team503.commands;

import org.usfirst.frc.team503.auton.AutonDriveCommand;
import org.usfirst.frc.team503.auton.CenterPegCenterStart;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterPegAuton extends CommandGroup {

    public CenterPegAuton() {
        // Add Commands here:
    		addSequential(new CenterPegCenterStart());
    		//addSequential(new AutonDriveCommand());
    	addSequential(new GyroTurnCommand(3.0));
    	
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
    }
}
