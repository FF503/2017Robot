package org.usfirst.frc.team503.utils;

public class Constants {
	public static final double JOYSTICK_TOLERANCE = .0;
	public static final double TURN_RATIO = .05;
	public static final double SHOOTER_SPEED = 3900;
	public static final double SHOOT_TIME_FOR_10_BALLS = 7.5;
	private static final double TURRET_DIAMETER = 13.0;
	public static final double TURRET_ROTATIONS_IN_RANGE = 257.3056640625;
	public static final double TURRET_TOLERANCE = 1.0;
	public static final double TURRET_TEETH_BETWEEN_LIMIT_SWITCHES = 8.5;
	public static final double TURRET_TOTAL_TEETH = 156.0;
	public static final double TURRET_DEGREES_BETWEEN_LIMIT_SWITCHES = TURRET_TEETH_BETWEEN_LIMIT_SWITCHES/TURRET_TOTAL_TEETH * 360;
	public static final double TURRET_DEGREES_IN_RANGE = 360 - TURRET_DEGREES_BETWEEN_LIMIT_SWITCHES;
	public static final double TURRET_DEGREES_PER_ROTATION = TURRET_DEGREES_IN_RANGE/TURRET_ROTATIONS_IN_RANGE;
	
}

