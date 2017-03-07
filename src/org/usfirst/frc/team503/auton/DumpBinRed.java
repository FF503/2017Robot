package org.usfirst.frc.team503.auton;

import org.usfirst.frc.team503.commands.GyroTurnCommand;
import org.usfirst.frc.team503.commands.ShootSequenceCommand;
import org.usfirst.frc.team503.motionProfile.RunMotionProfileCommand;

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

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	double[][] dumpbinForward = {
				{0, 22.5},
				{-6.6, 22.5}
		};
    	
    	double[][] hitBin ={
    			{0,0},
    			{3.3,0}
    	};
  
		addSequential(new RunMotionProfileCommand(dumpbinForward, 2, 1, true));
		addSequential(new GyroTurnCommand(-90));
		addSequential(new RunMotionProfileCommand(hitBin, 2, 1, false));
		
		if(shoot){
			addSequential(new ShootSequenceCommand());
		}
    }
}