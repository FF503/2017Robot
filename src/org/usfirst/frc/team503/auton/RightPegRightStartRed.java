package org.usfirst.frc.team503.auton;

import org.usfirst.frc.team503.commands.DriveStraightDistanceCommand;
import org.usfirst.frc.team503.commands.GyroTurnCommand;
import org.usfirst.frc.team503.commands.PlaceGearCommand;
import org.usfirst.frc.team503.commands.RaiseGearPlacer;
import org.usfirst.frc.team503.commands.ShootSequenceCommand;
import org.usfirst.frc.team503.robot.RobotState;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightPegRightStartRed extends CommandGroup {

    public RightPegRightStartRed(boolean dump, boolean shoot) {
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
    	if(shoot){
			if (dump){
				RobotState.getInstance().setShootingPreset(RobotState.ShootingPresets.HopperRed);
			}
			else{
				RobotState.getInstance().setShootingPreset(RobotState.ShootingPresets.PegNearHopperRed);
			}
			
		}
    	addParallel(new RaiseGearPlacer());    	
		//addSequential(new RunMotionProfileCommand(leftPinLeftStart, 2, 1, true));
    	addSequential(new DriveStraightDistanceCommand(58.5, 4.0, true));
		addSequential(new GyroTurnCommand(-60));
		/*if (shoot){
		//	addParallel(new SpinUpShooterCommnad());
		}
		if(shoot&&!dump){
			addSequential(new AutonDriveCommand());
			addParallel(new PlaceGearCommand());
		}*/
	
			addSequential(new AutonDriveCommand());
			addSequential(new PlaceGearCommand());
		
		if (dump){
		//	addSequential(new RunMotionProfileCommand(dumpBinForward, 2, 1, false)); //1.5
			addSequential(new DriveStraightDistanceCommand(66, 5.0,false));
			addSequential(new GyroTurnCommand(30, true));
			addParallel(new DriveStraightDistanceCommand(44.4, 3.5, false));
			//addSequential(new RunMotionProfileCommand(hitBin, 2, 1, false));	//1
		}
		if (shoot){
			addSequential(new ShootSequenceCommand());
		}
		
    }
}