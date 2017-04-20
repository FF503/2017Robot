package org.usfirst.frc.team503.auton;

import org.usfirst.frc.team503.commands.ArcDriveCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestAuton extends CommandGroup {

    public TestAuton() {
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
    	
    	//addSequential(new DriveStraightDistanceCommand(55,3.0,true));
    	//addSequential(new GyroTurnCommand(60));
    	
/*    	addSequential(new WaitCommand(3));
		RobotState.getInstance().setShootingPreset(RobotState.ShootingPresets.HopperBlue);
		addSequential(new ShootSequenceCommand(true));
		addSequential(new ShootSequenceCommand(true));
		addParallel(new ShootSequenceCommand());
		addSequential(new SetReadyToFire());*/
    	//addSequential(new AutonDriveCommand2());
    	//addSequential(new PlaceGearCommand());
    	//addSequential(new DriveStraightDistanceCommand(100.0, 5.0, false));
    	//addSequential(new DriveStraightDistanceCommand(48.00,2.0,true));
    	addSequential(new ArcDriveCommand(48.0, -60.0, 12.0, 2.5, true));
   }
}
