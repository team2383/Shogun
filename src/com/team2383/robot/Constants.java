package com.team2383.robot;

import java.util.LinkedList;

public class Constants {
	
	/*
	 * Feeder Constants
	 */
	public static final int kFeederLeftTalonID = 7;
	public static final int kFeederRightTalonID = 8;
	
	/*
	 * Conveyor constants
	 */
	public static final int kConveyorTalonID = 12;
	
	/*
	 * Agitator Constants
	 */
	public static final int kAgitatorTalonID = 13;
	
	/*
	 * Climber Constants
	 */
	public static final int kClimberTalonID = 14;

	/*
	 * Pneumatics Constants
	 */
	public static final int kShifterForward = 0;
	public static final int kShifterReverse = 1;
	public static final int kFlapForward = 2;
	public static final int kFlapBackward = 3;
	
	/*
	 * Turret Constants
	 */
	public static final int kLittleFlywheelTalonID = 9;
	public static final int kBigFlywheelTalonID = 10;
	public static final int kTurretTalonID = 11;
	
	public static final int kTurretTeeth = 128;
	public static final int kTurretPinionTeeth = 24;
	public static final double kTurretPotAngleRatio = ((double) kTurretPinionTeeth/kTurretTeeth);
	

	public static final double kTurretPositionP = 2.0;
	public static final double kTurretPositionI = 0.005;
	public static final double kTurretPositionD = 0.75;
	public static final double kTurretPositionF = 0;
	public static final int kTurretPositionIZone = 40;
	public static final double kTurretTolerance = 0.5/360.0; //turret pot rotations
	
			/**
			 * Last calibration date: NEVER
			 * 
			 * units: raw turret pot rotations
			 **/
	public static final int kTurretReverseLimit = 0;
	public static final int kTurretForwardLimit = 0;
	
	/*
	 * Shooter Constants
	 */
	
	public static class ShooterPreset {
		public final double bigWheelRPM;
		public final double littleWheelRPM;

		/**
		 * @param bigWheelRPM
		 * @param littleWheelRPM	`1q
		 */
		public ShooterPreset(double bigWheelRPM, double littleWheelRPM) {
			this.bigWheelRPM = bigWheelRPM;
			this.littleWheelRPM = littleWheelRPM;
		}
	}

	public static enum Preset {
		close(new ShooterPreset(2900, 1400)), mid(new ShooterPreset(1500, 800)), far(new ShooterPreset(1000, 1300));

		private final ShooterPreset preset;

		Preset(ShooterPreset preset) {
			this.preset = preset;
		}

		public ShooterPreset get() {
			return preset;
		}
	}
	
	public static double kFlywheelRPMTolerance = 30;
	
	public static double kBigFlywheelMaxRPM = 5000;
	public static double kBigFlywheelMinRPM = 1200;
	public static double kBigFlywheelP = 0.06;
	public static double kBigFlywheelI = 0.006;
	public static double kBigFlywheelD = 0.2;
	public static double kBigFlywheelF = 0.035;
	public static int kBigFlywheelIZone = 40;
	
	public static double kLittleFlywheelMaxRPM = 5000;
	public static double kLittleFlywheelMinRPM = 1200;
	public static double kLittleFlywheelP = 0.014;
	public static double kLittleFlywheelI = 0.008;
	public static double kLittleFlywheelD = 0.28;
	public static double kLittleFlywheelF = 0.0435;
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
	
	public static double kPidSetpointWait = 0.15;

	public static double inputExpo = 0.32;
	public static double inputDeadband = 0.05;
}