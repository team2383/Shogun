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

	/**
	 * 
	 * @param bigWheelRPM
	 * @param timeout
	 */
	public Spool(double bigWheelRPM, double timeout) {
		this(() -> bigWheelRPM, timeout);
	}

	/**
	 * @param bigWheelRPM
	 * @param littleWheelRPM2
	 */
	public Spool(double bigWheelRPM) {
		this(() -> bigWheelRPM);
	}
	

	public Spool(DoubleSupplier bigWheelRPMSupplier, double timeout) {
		super("Spool To RPM", timeout);
		requires(shooter);
		this.bigWheelRPMSupplier = bigWheelRPMSupplier;
	}

	public Spool(DoubleSupplier bigWheelRPMSupplier) {
		super("Spool To RPM");
		requires(shooter);
		this.bigWheelRPMSupplier = bigWheelRPMSupplier;
	}

	public Spool() {
		super("Spool To RPM");
		requires(shooter);
		this.bigWheelRPMSupplier = null;
	}

	@Override
	protected void initialize() {
		SmartDashboard.putBoolean("spooling?", false);
		if (bigWheelRPMSupplier != null) {
			shooter.setBigFlywheelRPMSupplier(this.bigWheelRPMSupplier);
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
