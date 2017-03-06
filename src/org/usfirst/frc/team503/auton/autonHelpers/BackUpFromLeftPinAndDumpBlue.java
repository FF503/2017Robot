package org.usfirst.frc.team503.auton.autonHelpers;

import org.usfirst.frc.team503.commands.GyroTurnCommand;
import org.usfirst.frc.team503.motionProfile.RunMotionProfileCommand;
import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team503.subsystems.GyroSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class BackUpFromLeftPinAndDumpBlue extends CommandGroup {

    public BackUpFromLeftPinAndDumpBlue() {
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
				{3.5, 22.5}
		};
    	
    	double[][] hitBin ={
    			{0,0},
    			{4.5,0}
    	};
  
		addSequential(new RunMotionProfileCommand(dumpbinForward, 2, 1, false));
	
		addSequential(new GyroTurnCommand(30, true));
		
		addSequential(new RunMotionProfileCommand(hitBin, 2, 1, false));
    }
}