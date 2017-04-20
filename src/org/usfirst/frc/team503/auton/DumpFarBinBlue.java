package org.usfirst.frc.team503.auton;

import org.usfirst.frc.team503.commands.DriveStraightDistanceCommand;
import org.usfirst.frc.team503.commands.GyroTurnCommand;
import org.usfirst.frc.team503.commands.RaiseGearPlacer;
import org.usfirst.frc.team503.commands.SetReadyToFire;
import org.usfirst.frc.team503.commands.ShootSequenceCommand;
import org.usfirst.frc.team503.robot.RobotState;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DumpFarBinBlue extends CommandGroup {

    public DumpFarBinBlue(boolean shoot) {
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
		addSequential(new DriveStraightDistanceCommand(210,2.5,true));//81 at livonia //was 57
		addSequential(new GyroTurnCommand(90));
		if(shoot){
			RobotState.getInstance().setShootingPreset(RobotState.ShootingPresets.HopperBlue);
			addSequential(new ShootSequenceCommand(true));
			addParallel(new ShootSequenceCommand());
		}
		addSequential(new DriveStraightDistanceCommand(48,2.5,false)); //was 35 at livonia	
		addSequential(new SetReadyToFire());
    }
}