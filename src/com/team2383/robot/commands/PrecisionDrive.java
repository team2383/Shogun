package com.team2383.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PrecisionDrive extends TeleopDrive {
	
	public PrecisionDrive(DoubleSupplier throttle, DoubleSupplier turn, BooleanSupplier toggleAutoShift,
			BooleanSupplier lowGear, BooleanSupplier highGear) {
		super((() -> (throttle.getAsDouble() / 2)) , (() -> (turn.getAsDouble() / 2)), toggleAutoShift, lowGear, highGear);
	}
	
	protected void initialize() {
		SmartDashboard.putBoolean("Precision Mode", true);
	}

	//Override processAxis from JoystickDrive to half axis values by 2
	@Override
	protected void execute(){
		super.execute();
	}

	// Called once after isFinished returns true
	protected void end() {
		SmartDashboard.putBoolean("Precision Mode", false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		SmartDashboard.putBoolean("Precision Mode", false);
	}
}
