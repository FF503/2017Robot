package org.usfirst.frc.team503.motionProfile;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RunMotionProfileCommand extends Command {
	private PathPlanner planner;
	public RunMotionProfileCommand(double[][] waypoints, double maxTime, double curveExageration, boolean runReverse) {
    	planner = new PathPlanner(waypoints);
    	planner.calculate(maxTime, Robot.bot.CYCLE_TIME, Robot.bot.WHEEL_BASE/12.0, curveExageration, runReverse);
        requires(DrivetrainSubsystem.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	SmartDashboard.putNumber("left target", planner.getLeftProfile()[planner.getLeftProfile().length-1][0]);
    	DrivetrainSubsystem.getInstance().runProfileLeftRight(planner.getLeftProfile(), planner.getRightProfile());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {	
    	return DrivetrainSubsystem.getInstance().profileHasFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	DrivetrainSubsystem.getInstance().stopTrapezoidControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}