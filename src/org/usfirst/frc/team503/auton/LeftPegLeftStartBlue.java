package org.usfirst.frc.team503.auton;

import org.usfirst.frc.team503.auton.autonHelpers.AutonDriveCommand;
import org.usfirst.frc.team503.auton.autonHelpers.BackUpFromLeftPinAndDumpBlue;
import org.usfirst.frc.team503.commands.GyroTurnCommand;
import org.usfirst.frc.team503.motionProfile.RunMotionProfileCommand;
import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftPegLeftStartBlue extends CommandGroup {

    public LeftPegLeftStartBlue(boolean dump) {
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
				{-5.9, 22.5}
		};
    	
    	double[][] backUpFromPin = {
    			{0,0},
    			{5.5,0}
    	};
    	
  
		addSequential(new RunMotionProfileCommand(leftPinLeftStart, 2, 1, true));
	
		addSequential(new GyroTurnCommand(60));
		
		addSequential(new AutonDriveCommand());
		
		if (dump){
			addSequential(new BackUpFromLeftPinAndDumpBlue());
		}
		
		
		
    }
}