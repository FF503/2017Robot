package org.usfirst.frc.team503.motionProfile;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RunMotionProfileCommand extends Command {
	private PathPlanner planner;
	private double time, curveExaggeration;
	boolean reverse;
	private ProfileGenerator pathPuller;
	
	public RunMotionProfileCommand(String fileName) {
    	pathPuller = new ProfileGenerator(fileName);
    	
        requires(DrivetrainSubsystem.getInstance());
    }
	
	public RunMotionProfileCommand(double[][] points, double time, double curveExaggeration, boolean reverse){
		this.time = time;
		this.curveExaggeration = curveExaggeration;
		this.reverse = reverse;
		planner = new PathPlanner(points);
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	planner.calculate(time, Robot.bot.CYCLE_TIME, Robot.bot.WHEEL_BASE, curveExaggeration, reverse);
    	SmartDashboard.putNumber("left target", planner.getLeftProfile()[planner.getLeftProfile().length-1][0]);
    	DrivetrainSubsystem.getInstance().runProfileLeftRight(planner.rightProfile, planner.leftProfile);
    	//DrivetrainSubsystem.getInstance().runProfileLeftRight(pathPuller.getLeftProfile(), pathPuller.getRightProfile());
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