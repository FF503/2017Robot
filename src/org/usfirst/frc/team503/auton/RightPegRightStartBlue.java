package org.usfirst.frc.team503.auton;

import org.usfirst.frc.team503.commands.DriveStraightDistanceCommand;
import org.usfirst.frc.team503.commands.GyroTurnCommand;
import org.usfirst.frc.team503.commands.PlaceGearCommand;
import org.usfirst.frc.team503.commands.RaiseGearPlacer;
import org.usfirst.frc.team503.commands.SetReadyToFire;
import org.usfirst.frc.team503.commands.ShootSequenceCommand;
import org.usfirst.frc.team503.robot.RobotState;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightPegRightStartBlue extends CommandGroup {

    public RightPegRightStartBlue(boolean dump, boolean shoot) {
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
		//addSequential(new RunMotionProfileCommand(leftPinLeftStart, 2, 1, true));
    	addSequential(new DriveStraightDistanceCommand(58.5, 3.0, true));
		addSequential(new GyroTurnCommand(-60));
		
		addSequential(new DriveStraightDistanceCommand(36.0, 2.0, true));
		addSequential(new AutonDriveCommand2());
		addSequential(new PlaceGearCommand());
		
		if(shoot){
			RobotState.getInstance().setShootingPreset(RobotState.ShootingPresets.PegNearHopperBlue);
			addSequential(new DriveStraightDistanceCommand(48.0, 2.0, false));
			addSequential(new GyroTurnCommand(120));
			addSequential(new ShootSequenceCommand(true));
			addParallel(new ShootSequenceCommand());
			addSequential(new DriveStraightDistanceCommand(60.0,3.0,false));
			addSequential(new SetReadyToFire());
		}
		else{
			addSequential(new DriveStraightDistanceCommand(12,1.0,false));
		}
    }
}