package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooter;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * Spool shooter flywheel
 */
public class Spool extends Command {
	private final DoubleSupplier bigWheelRPMSupplier;
	private final DoubleSupplier littleWheelRPMSupplier;

	/**
	 * 
	 * @param bigWheelRPM
	 * @param littleWheelRPM
	 * @param timeout
	 */
	public Spool(double bigWheelRPM, double littleWheelRPM, double timeout) {
		this(() -> bigWheelRPM, () -> littleWheelRPM, timeout);
	}

	/**
	 * @param bigWheelRPM
	 * @param littleWheelRPM
	 */
	public Spool(double bigWheelRPM, double littleWheelRPM) {
		this(() -> bigWheelRPM, () -> littleWheelRPM);
	}

	public Spool(DoubleSupplier bigWheelRPMSupplier, DoubleSupplier littleWheelRPMSupplier, double timeout) {
		super("Spool To RPM", timeout);
		requires(shooter);
		this.bigWheelRPMSupplier = bigWheelRPMSupplier;
		this.littleWheelRPMSupplier = littleWheelRPMSupplier;
	}

	public Spool(DoubleSupplier bigWheelRPMSupplier, DoubleSupplier littleWheelRPMSupplier) {
		super("Spool To RPM");
		requires(shooter);
		this.bigWheelRPMSupplier = bigWheelRPMSupplier;
		this.littleWheelRPMSupplier = littleWheelRPMSupplier;
	}

	public Spool() {
		super("Spool To RPM");
		requires(shooter);
		this.bigWheelRPMSupplier = null;
		this.littleWheelRPMSupplier = null;
	}

	@Override
	protected void initialize() {
		SmartDashboard.putBoolean("spooling?", false);
		if (bigWheelRPMSupplier != null) {
			shooter.setBigFlywheelRPMSupplier(this.bigWheelRPMSupplier);
		}
		if (littleWheelRPMSupplier != null) {
			shooter.setBigFlywheelRPMSupplier(this.littleWheelRPMSupplier);
		}
	}

	@Override
	protected void execute() {
		SmartDashboard.putBoolean("spooling?", true);
		shooter.spool();
	}

	@Override
	protected boolean isFinished() {
		return this.isTimedOut();
	}

	@Override
	protected void end() {
		SmartDashboard.putBoolean("spooling?", false);
		shooter.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
