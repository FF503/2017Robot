package org.usfirst.frc.team503.motionProfile;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.subsystems.DrivetrainSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RunMotionProfileCommand extends Command {
	private PathPlanner planner;
	private double[][] rightProfile;
	private double[][] leftProfile;
	private double time, curveExaggeration;
	boolean reverse;
	boolean calculatingInHouse;
	private ProfileGenerator pathPuller;
	
	public RunMotionProfileCommand(String fileName, boolean reverse) {
		this.calculatingInHouse = false;
    	pathPuller = new ProfileGenerator(fileName, reverse);
        requires(DrivetrainSubsystem.getInstance());
        
    }
	
	public RunMotionProfileCommand(double[][] points, double time, double curveExaggeration, boolean reverse){
		this.calculatingInHouse = true;
		this.time = time;
		this.curveExaggeration = curveExaggeration;
		this.reverse = reverse;
		planner = new PathPlanner(points);
		
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (calculatingInHouse){
    		planner.calculate(time, Robot.bot.CYCLE_TIME, Robot.bot.WHEEL_BASE, curveExaggeration, reverse);
    		SmartDashboard.putNumber("left target", planner.getLeftProfile()[planner.getLeftProfile().length-1][0]);
    		DrivetrainSubsystem.getInstance().runProfileLeftRight(planner.getLeftProfile(), planner.getRightProfile());
    	}
    	else{
    		DrivetrainSubsystem.getInstance().runProfileLeftRight(pathPuller.getLeftProfile(), pathPuller.getRightProfile());
    	}
    	
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