package com.team2383.robot.subsystems;


import com.team2383.robot.Constants;
import com.team2383.robot.OI;
import com.team2383.robot.commands.TeleopDrive;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem implements PIDSource {
	private final RobotDrive robotDrive;
	
	private CANTalon leftEncoder;
	private CANTalon leftTwo;
	private CANTalon leftThree;
	
	private CANTalon rightEncoder;
	private CANTalon rightTwo;
	private CANTalon rightThree;
	
	private DoubleSolenoid shifter;

	
	public enum Gear {
		LOW, HIGH;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	public Drivetrain() {
		super("Drivetrain");

		leftEncoder.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		leftEncoder.reverseSensor(false);
		leftEncoder.setPID(Constants.kDriveHoldPositionP, Constants.kDriveHoldPositionI, Constants.kDriveHoldPositionD,
				Constants.kDriveHoldPositionF, Constants.kDriveHoldPositionIZone, 0, 1);
		leftTwo.changeControlMode(TalonControlMode.Follower);
		leftTwo.set(leftEncoder.getDeviceID());
		leftThree.changeControlMode(TalonControlMode.Follower);
		leftThree.set(leftEncoder.getDeviceID());

		rightEncoder.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		rightEncoder.reverseSensor(true);
		rightEncoder.setPID(Constants.kDriveHoldPositionP, Constants.kDriveHoldPositionI, Constants.kDriveHoldPositionD,
				Constants.kDriveHoldPositionF, Constants.kDriveHoldPositionIZone, 0, 1);
		rightTwo.changeControlMode(TalonControlMode.Follower);
		rightTwo.set(rightEncoder.getDeviceID());
		rightThree.changeControlMode(TalonControlMode.Follower);
		rightThree.set(rightEncoder.getDeviceID());

		this.robotDrive = new RobotDrive(leftEncoder, rightEncoder);
		robotDrive.setSafetyEnabled(false);
	}

	public void tank(double leftValue, double rightValue) {
		leftEncoder.changeControlMode(TalonControlMode.PercentVbus);
		rightEncoder.changeControlMode(TalonControlMode.PercentVbus);
		robotDrive.tankDrive(leftValue, rightValue);
	}

	public void arcade(double driveSpeed, double turnSpeed) {
		leftEncoder.changeControlMode(TalonControlMode.PercentVbus);
		rightEncoder.changeControlMode(TalonControlMode.PercentVbus);
		robotDrive.arcadeDrive(driveSpeed, turnSpeed);
	}

	public void shiftTo(Gear gear) {
		switch (gear) {
		default:
		case HIGH:
			enableBrake();
			shifter.set(Value.kForward);
			break;
		case LOW:
			disableBrake();
			shifter.set(Value.kReverse);
			break;
		}
	}

	public void shift() {
		if (getGear() == Gear.HIGH) {
			shiftTo(Gear.LOW);
		} else {
			shiftTo(Gear.HIGH);
		}
	}

	public void resetEncoders() {
		leftEncoder.setPosition(0);
		rightEncoder.setPosition(0);
	}

	public Gear getGear() {
		switch (shifter.get()) {
		case kForward:
			return Gear.HIGH;
		default:
		case kReverse:
			return Gear.LOW;
		}
	}

	public double getRotations() {
		double rotations;
		if (leftEncoder.isSensorPresent(
				FeedbackDevice.CtreMagEncoder_Relative) == FeedbackDeviceStatus.FeedbackStatusNotPresent)
			return 0;
		if (rightEncoder.isSensorPresent(
				FeedbackDevice.CtreMagEncoder_Relative) == FeedbackDeviceStatus.FeedbackStatusNotPresent)
			return 0;
		try {
			rotations = (leftEncoder.getPosition() + rightEncoder.getPosition()) / 2.0;
		} catch (Throwable e) {
			System.out.println("Failed to get encoder rotations of drivetrain");
			rotations = 0;
		}
		return rotations;
	}

	public double getVelocity() {
		double rotations;
		if (leftEncoder.isSensorPresent(
				FeedbackDevice.CtreMagEncoder_Relative) == FeedbackDeviceStatus.FeedbackStatusNotPresent)
			return 0;
		if (rightEncoder.isSensorPresent(
				FeedbackDevice.CtreMagEncoder_Relative) == FeedbackDeviceStatus.FeedbackStatusNotPresent)
			return 0;
		try {
			rotations = (leftEncoder.getSpeed() + rightEncoder.getSpeed())/2.0;
		} catch (Throwable e) {
			System.out.println("Failed to get encoder speed of drivetrain");
			rotations = 0;
		}
		return rotations;
	}

	public double getInches() {
		return getRotations() * Constants.kDriveWheelCircumference;
	}

	// Feet per Seconds
	public double getSpeed() {
		return getVelocity() * Constants.kDriveWheelCircumference / 12.0 / 60.0;
	}

	@Override
	protected void initDefaultCommand() {
		this.setDefaultCommand(new TeleopDrive(OI.leftStick, OI.rightStick, () -> OI.toggleAutoShift.get(),
				() -> OI.shiftDown.get(), () -> OI.shiftUp.get()));
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		return getInches();
	}

	public void setBrake(boolean brake) {
		leftEncoder.enableBrakeMode(brake);
		rightEncoder.enableBrakeMode(brake);
	}

	public void enableBrake() {
		setBrake(true);
	}

	public void disableBrake() {
		setBrake(false);
	}
}
