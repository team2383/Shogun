package com.team2383.robot.commands;

import static com.team2383.robot.HAL.turret;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves arms until canceled or times out
 *
 * @author Matthew Alonso
 *
 */

public class MoveTurret extends Command {
	private final DoubleSupplier turretPower;

	public MoveTurret(DoubleSupplier turretPower) {
		super("Move Turret");
		requires(turret);
		this.turretPower = turretPower;
	}

	public MoveTurret(DoubleSupplier turretPower, double timeout) {
		super("Move Turret", timeout);
		requires(turret);
		this.turretPower = turretPower;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		turret.moveAtSpeed(turretPower.getAsDouble());
	}

	@Override
	protected boolean isFinished() {
		return this.isTimedOut();
	}

	@Override
	protected void end() {
		turret.stop();
	}

	@Override
	protected void interrupted() {
		turret.stop();
	}
}