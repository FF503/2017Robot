package org.usfirst.frc.team503.robot;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Name:		RobotState 
 * Purpose:		
 * Author: 		Jyotsna Joshi
 * Date:		February 2017 
 * Comments:
 */
public class RobotState extends Subsystem {

    // Put methods for controlling this subsystem
	private boolean shooterIsRunning;
	private boolean intakeIsRunning;
	private boolean currentDriveGear;
	private boolean indexerIsRunning;
	private boolean climberIsRunning;
	private boolean climberIsAccelerated;
	private boolean turretIsRunning;
	private boolean driveTrainIsReversed;
	private boolean gearPlacer;
	private State robotState;
	private TurretState turretState;
    	
	public RobotState() {
		shooterIsRunning = false; 
		intakeIsRunning = false;
		currentDriveGear = false;    //low gear
		indexerIsRunning = false;
		climberIsRunning = false;
		climberIsAccelerated = false;
		turretIsRunning = false;
		driveTrainIsReversed = false;
		gearPlacer = false; //closed
		robotState = State.DISABLED;
		turretState = TurretState.DISABLED;
	}
	
	private static RobotState instance = new RobotState();
	
	public static RobotState getInstance(){
		return instance;
	}
	
	public enum State{
		DISABLED, AUTON, TELEOP, TEST, ENABLED;
	}
	
	public enum TurretState{
		DISABLED, SEEKING_TARGET, TARGET_FOUND, RUNNING_PID, ON_TARGET;
	}
	
	public boolean getGearPlacer(){
		return gearPlacer;
	}
	
	public void setGearPlacer(boolean gearPlacer){
		this.gearPlacer = gearPlacer;
	}
	
	public TurretState getTurretState(){
		return turretState;
	}
	
	public void setTurretState(TurretState state){
		turretState = state;
	}
	
	public State getState(){
		return robotState;
	}
	
	public void setState(State state){
		robotState = state;
	}
	
	public void setTurretStatus(boolean status){
		turretIsRunning = status;
	}
	
	public boolean getTurreStatus(){
		return turretIsRunning;
	}
	
	public void setDriveTrainReversed(boolean reverse){
		driveTrainIsReversed = reverse;
	}
	
	public boolean getDriveTrainReversed(){
		return driveTrainIsReversed;
	}
	
	public void setShooterStatus(boolean status) {
		shooterIsRunning = status; 
	}
	
	public boolean getShooterStatus() {
		return shooterIsRunning; 
	}
	
	public void setClimberStatus(boolean status){
		climberIsRunning = status;
	}
	
	public void setClimberAccelerationStatus(boolean status){
		climberIsAccelerated = status;
	}
	
	public boolean getClimberStatus(){
		return climberIsRunning;
	}
	
	public boolean getClimberCanAccelerate(){
		return (climberIsRunning && (!climberIsAccelerated));
	}
	
	public void setcurrentGear(boolean status) {
		currentDriveGear = status; 
	}
	
	public boolean getCurrentGear() {
		return currentDriveGear; 
	}
	
	public void setIntakeStatus(boolean status) {
		intakeIsRunning = status; 
	}
	
	public boolean getIntakeStatus() {
		return intakeIsRunning; 
	}
	
	public void setIndexerStatus(boolean status) {
		indexerIsRunning = status; 
	}
	
	public boolean getIndexerStatus() {
		return indexerIsRunning; 
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

