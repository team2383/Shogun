/*package com.team2383.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
/*public class TeleopDriveStraight extends Command {
	private final DoubleSupplier throttle;
	private final PIDController headingController;

	public TeleopDriveStraight(DoubleSupplier throttle) {
		super("Teleop Drive");
		requires(drivetrain);
		this.throttle = throttle;
		headingController = new PIDController(Constants.driveTurnP, Constants.driveTurnI, Constants.driveTurnD, 0.0,
				navX, new NullPIDOutput());
		headingController.setInputRange(-180.0, 180.0);
		headingController.setOutputRange(-1.0, 1.0);
		headingController.setContinuous();
		headingController.setAbsoluteTolerance(Constants.driveTurnTolerance);
		headingController.setSetpoint(0);
	}

	@Override
	protected void initialize() {
		navX.reset();
		headingController.enable();
	}

	@Override
	protected void execute() {
		if (this.timeSinceInitialized() > 0.1) {
			drivetrain.arcade(leftStick.getAsDouble(), headingController.get());
		} else {
			drivetrain.arcade(leftStick.getAsDouble(), 0);
			System.out.println("Waiting for reset " + this.timeSinceInitialized());
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		drivetrain.tank(0, 0);
		headingController.disable();
	}

	@Override
	protected void interrupted() {
		drivetrain.tank(0, 0);
		headingController.disable();
	}
}*/
