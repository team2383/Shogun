package com.team2383.robot.commands;

import java.util.function.DoubleSupplier;

import com.team2383.ninjaLib.NullPIDOutput;
import com.team2383.robot.Constants;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

import static com.team2383.robot.HAL.drivetrain;
import static com.team2383.robot.HAL.navX;

/**
 *
 */
public class AutoDriveStraight extends Command {
	private final DoubleSupplier throttle;
	private final double time;
	private final PIDController headingController;
	private boolean finish;
	private Timer timer;

	public AutoDriveStraight(DoubleSupplier throttle, double time) {
		super("Auto Drive");
		requires(drivetrain);
		this.throttle = throttle;
		this.time = time;
		headingController = new PIDController(Constants.kDriveHeadingMaintainP, Constants.kDriveHeadingMaintainI, Constants.kDriveHeadingMaintainD, 0.0,
				navX, new NullPIDOutput());
		headingController.setInputRange(-180.0, 180.0);
		headingController.setOutputRange(-1.0, 1.0);
		headingController.setContinuous();
		headingController.setAbsoluteTolerance(Constants.kDriveHeadingMaintainTolerance);
		headingController.setSetpoint(0);
		finish = true;
	}
	

	@Override
	protected void initialize() {
		navX.reset();
		headingController.enable();
		timer = new Timer();
		timer.reset();
		timer.start();
	}

	@Override
	protected void execute() {
		if (this.timeSinceInitialized() > 0.1) {
			drivetrain.arcade(throttle.getAsDouble(), -headingController.get());
		} else {
			drivetrain.arcade(throttle.getAsDouble(), 0);
			System.out.println("Waiting for reset " + this.timeSinceInitialized());
		}
	}

	@Override
	protected boolean isFinished() {
		return finish && (timer.get() >= time);
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
}
