package com.team2383.robot;

import java.util.LinkedList;

public class Constants {
	/*
	 * Feeder Constants
	 */
	public static final int kFeederLeftTalonID = 7;
	public static final int kFeederRightTalonID = 8;
	
	/*
	 * Hopper Constants
	 */
	
	public static final int kHopperTalonID = 11;
	public static final int kAgitatorTalonID = 12;
	
	/*
	 * Turret Constants
	 */
	public static final int kLittleFlywheelTalonID = 9;
	public static final int kBigFlywheelTalonID = 10;
	public static final int kTurretTalonID = 10;
	public static final int kRPMTolerance = 100;
	

	public static double kShooterRPMTolerance = 50;
	
	public static double kBigFlywheelMaxRPM = 4500;
	public static double kBigFlywheelMinRPM = 2500;
	public static double kBigFlywheelP = 0.68;
	public static double kBigFlywheelI = 0.0;
	public static double kBigFlywheelD = 0.25;
	public static double kBigFlywheelF = 0.033;
	public static int kBigFlywheelIZone = 40;
	
	public static double kLittleFlywheelMaxRPM = 4500;
	public static double kLittleFlywheelMinRPM = 2500;
	public static double kLittleFlywheelP = 0.68;
	public static double kLittleFlywheelI = 0.0;
	public static double kLittleFlywheelD = 0.25;
	public static double kLittleFlywheelF = 0.033;
	public static int kLittleFlywheelIZone = 40;

	/*
	 * Drive Constants
	 */
	public static final int kLeftMasterTalonID = 1;
	public static final int kLeftSlaveOneTalonID = 2;
	public static final int kLeftSlaveTwoTalonID = 3;
	public static final int kRightMasterTalonID = 4;
	public static final int kRightSlaveOneTalonID = 5;
	public static final int kRightSlaveTwoTalonID = 6;
	
	
	public static double kDriveWheelDiameter = 4;
	public static double kDriveWheelCircumference = kDriveWheelDiameter * Math.PI;
	public static double kDriveInchesPerDegree = kDriveWheelCircumference / 360.0;
	public static double kDriveFeetPerDegree = kDriveInchesPerDegree / 12.0;
	public static double kDriveUpshiftFPSThreshold = 4.0;
	public static double kDriveDownshiftFPSThreshold = 3.0;

	public static double kDriveTurnTolerance = 0.54;
	public static double kDriveTurnP = 0.049;
	public static double kDriveTurnI = 0.010;
	public static double kDriveTurnD = 0.110;
	public static double kDriveTurnIZone = 6;
	public static double kDriveTurnVelocity = 0.95;

	public static double kDriveHeadingMaintainTolerance = 0.25;
	public static double kDriveHeadingMaintainP = 0.13;
	public static double kDriveHeadingMaintainI = 0.0001;
	public static double kDriveHeadingMaintainD = 0.0;
	public static double kDriveHeadingMaintainF = 0;

	public static double kDrivePositionTolerance = 1.2;
	public static double kDrivePositionP = 0.070;
	public static double kDrivePositionI = 0.001;
	public static double kDrivePositionD = 0.048;
	public static double kDrivePositionIZone = kDrivePositionTolerance * 4.0;
	public static double kDrivePositionF = 0;

	public static double kDriveHoldPositionP = 2.3;
	public static double kDriveHoldPositionI = 0.0023;
	public static double kDriveHoldPositionD = 0.0;
	public static double kDriveHoldPositionF = 0;
	public static int kDriveHoldPositionIZone = 50;

	public static double inputExpo = 0.32;
	public static double inputDeadband = 0.05;
}