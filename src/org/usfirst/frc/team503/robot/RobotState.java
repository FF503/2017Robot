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
	private boolean readyToFire;
	private boolean intakeIsRunning;
	private boolean currentDriveGear;
	private boolean indexerIsRunning;
	private boolean climberIsRunning;
	private boolean climberIsAccelerated;
	private boolean turretIsRunning;
	private boolean driveTrainIsReversed;
	private boolean gearPlacerBack;
	private boolean gearPlacerFront;
	private boolean climberIsRunningSlow;
	private boolean turretIsLocked;
	private State robotState;
	private TurretState turretState;
	private ShootingPresets shootingPreset;
	private double turretAngle;
	private boolean hint;
	private boolean resetAtZero;
	private boolean turretHasReset;
	private boolean intakeGearRunning;
    	
	public RobotState() {
		shooterIsRunning = false; 
		turretIsLocked = false;
		climberIsRunningSlow = false; 
		intakeIsRunning = false;
		currentDriveGear = false;    //low gear
		indexerIsRunning = false;
		climberIsRunning = false;
		climberIsAccelerated = false;
		turretIsRunning = false;
		driveTrainIsReversed = false;
		readyToFire = false;
		gearPlacerBack = false; //closed
		gearPlacerFront = false; //closed
		hint = false;
		resetAtZero = false;
		turretHasReset = false;
		intakeGearRunning = false;
		robotState = State.DISABLED;
		turretState = TurretState.DISABLED;
		shootingPreset = ShootingPresets.HopperRed;
	}	
	
	private static RobotState instance = new RobotState();
	
	public static RobotState getInstance(){
		return instance;
	}
	
	public enum State{
		DISABLED, AUTON, TELEOP, TEST, ENABLED;
	}
	
	public enum TurretState{
		DISABLED, RESET_TURRET, SEEKING_TARGET, TARGET_FOUND, RUNNING_PID, ON_TARGET, TAKING_HINT;
	}
	
	public enum ShootingPresets{
		NoTracking(30.0, 4500, 503), Batter(13.0,3950,285.0), HopperRed(26, 4425, 6.0), CenterPegBlue(29.0,4700, 0.0), CenterPegRed(33.0, 5100, 223.0099056523927), PegNearHopperBlue(28, 4700, 272.0), PegNearHopperRed(30.5, 4550,301), FarPegBlue(35.0,4500,503), HopperBlue(22.0,4200,198.0);
		//pegnearhopperblue 272, 34, 4950
		//hopperred 34, 4400
		
		public double deflectorAngle;
		public int rpm;
		public double turretAngle; 
		private ShootingPresets(double deflectorAngle, int rpm, double turretAngle){
			this.deflectorAngle = deflectorAngle;
			this.rpm = rpm;
			this.turretAngle = turretAngle;
		}
		
	}
	
	public void setReadyToFire(boolean r){
		readyToFire = r;
	}
	public boolean getReadyToFire(){
		return readyToFire;
	}
	
	public void setGearIntakeRunning(boolean run){
		intakeGearRunning = run;
	}
	
	public boolean getGearIntakeRunning(){
		return intakeGearRunning;
	}
	
	public synchronized boolean getHasTurretReset(){
		return turretHasReset;
	}
	
	public synchronized void setHasTurretReset(boolean hasReset){
		turretHasReset = hasReset; 
	}
	
	public synchronized boolean getTurretResetSide(){
		return resetAtZero;
	}
	
	public synchronized void setTurretResetSide(boolean resetAtZero){
		this.resetAtZero = resetAtZero;
	}
	
	public synchronized boolean getTurretIsLocked(){
		return turretIsLocked;
	}
	
	public synchronized void setTurretIsLocked(boolean lock){
		turretIsLocked = lock;
	}
	
	public synchronized double getTurretAngle(){
		return turretAngle;
	}
	
	public synchronized void setTurretAngle(double angle){
		turretAngle = angle;
	}
	
	public synchronized boolean getTurretHint(){
		return hint;
	}
	
	public synchronized void setTurretHint(boolean hint){
		this.hint = hint;
	}
	
	public synchronized TurretState getTurretState(){
		return turretState;
	}
	
	public synchronized void setTurretState(TurretState state){
		System.out.println("Previous state:" + turretState.toString() + "Next state:" + state.toString());
		turretState = state;
	}
	
	public synchronized ShootingPresets getShooterPreset(){
		return shootingPreset;
	}
	
	public synchronized void setShootingPreset(ShootingPresets preset){
		shootingPreset = preset;
	}
	
	public boolean getGearPlacerBack(){
		return gearPlacerBack;
	}
	
	public void setGearPlacerBack(boolean gearPlacer){
		this.gearPlacerBack = gearPlacer;
	}
	
	public boolean getGearPlacerFront(){
		return gearPlacerFront;
	}
	
	public boolean getClimberRunningSlow(){
		return climberIsRunningSlow;
	}
	
	public void setClimberRunningSlow(boolean climber){
		climberIsRunningSlow = climber;
	}
	
	public void setGearPlacerFront(boolean gearPlacer){
		this.gearPlacerFront = gearPlacer;
	}
	
	public State getState(){
		return robotState;
	}
	
	public void setState(State state){
		robotState = state;
	}
	
	public synchronized void setTurretStatus(boolean status){
		turretIsRunning = status;
	}
	
	public synchronized boolean getTurreStatus(){
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
		return (!climberIsAccelerated);
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

