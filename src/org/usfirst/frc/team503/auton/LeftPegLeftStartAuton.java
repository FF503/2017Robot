package org.usfirst.frc.team503.auton;

import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftPegLeftStartAuton extends CommandGroup {

    public LeftPegLeftStartAuton() {
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
				{2,21.5},
				{8.8,21.5},
				{9.8,21.75}
		};
    	double[][] leftPinToHopper = {
    			{0,0},
    			{2.29,0},
    			{3,-6}
    	};
    	//DrivetrainSubsystem.getInstance().setDriveDirectionBackward();
		addSequential(new RunMotionProfileCommand(leftPinLeftStart, 2.7, 10, true));
		//DrivetrainSubsystem.getInstance().setDriveDirectionForward();
		//addSequential(new RunMotionProfileCommand(leftPinToHopper, 2, 3,false));
		
    }
}