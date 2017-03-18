package org.usfirst.frc.team503.auton;

import org.usfirst.frc.team503.commands.CloseGearPlacerCommand;
import org.usfirst.frc.team503.commands.GyroTurnCommand;
import org.usfirst.frc.team503.commands.OpenGearPlacerCommand;
import org.usfirst.frc.team503.commands.ShootSequenceCommand;
import org.usfirst.frc.team503.motionProfile.RunMotionProfileCommand;
import org.usfirst.frc.team503.robot.RobotState;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class LeftPegLeftStartRed extends CommandGroup {

    public LeftPegLeftStartRed(boolean dump, boolean shoot) {
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
				{-5.45, 22.5}
		};
    	
    	double[][] backUpFromPin = {
    			{0,0},
    			{5.5,0}
    	};
    	
    	double[][] dumpbinForward = {
				{0, 22.5},
				{3.5, 22.5}
		};
    	
    	double[][] hitBin ={
    			{0,0},
    			{4.5,0}
    	};    	
  
		addSequential(new RunMotionProfileCommand(leftPinLeftStart, 2, 1, true));
		addSequential(new GyroTurnCommand(60));
		addSequential(new AutonDriveCommand());
		addSequential(new OpenGearPlacerCommand());
		addSequential(new CloseGearPlacerCommand());
		if (dump){	  
			addSequential(new RunMotionProfileCommand(dumpbinForward, 2, 1, false));
			addSequential(new GyroTurnCommand(30, true));
			addSequential(new RunMotionProfileCommand(hitBin, 2, 1, false));	
		}
		if(shoot){
			if (dump){
				RobotState.getInstance().setShootingPreset(RobotState.ShootingPresets.HopperRed);
			}
			else{
				RobotState.getInstance().setShootingPreset(RobotState.ShootingPresets.PegNearHopper);
			}
			addSequential(new ShootSequenceCommand());
		}
    }
}