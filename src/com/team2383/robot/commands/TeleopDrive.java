package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.team2383.ninjaLib.CheesyDriveHelper;
import com.team2383.robot.HAL;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleopDrive extends Command {
	private final DoubleSupplier turn;
	private final DoubleSupplier throttle;
	private final BooleanSupplier toggleAutoShift;
	private final BooleanSupplier lowGear;
	private final BooleanSupplier highGear;
	private final CheesyDriveHelper cdh;
	private boolean autoShift;
	private boolean dirty;
	private double timeSlow;
	private double lastCheck;
	private Gear gear;

	public TeleopDrive(DoubleSupplier throttle, DoubleSupplier turn, BooleanSupplier toggleAutoShift, BooleanSupplier lowGear,  BooleanSupplier highGear) {
		super("Teleop Drive");
		requires(drivetrain);
		this.throttle = throttle;
		this.turn = turn;
		this.toggleAutoShift = toggleAutoShift;
		this.lowGear = lowGear;
		this.highGear = highGear;
		this.autoShift = false;
		this.gear = Gear.LOW;
		this.dirty = false;
		this.timeSlow = 0.0;
		this.lastCheck = 0.0;
		this.cdh = new CheesyDriveHelper();
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		boolean notTurning = Math.abs(turn.getAsDouble()) < 0.2;
		DoubleSupplier _throttle = throttle;
		if (this.toggleAutoShift.getAsBoolean() && !dirty) {
			autoShift = !autoShift;
			dirty = true;
		} else if (!this.toggleAutoShift.getAsBoolean()) {
			dirty = false;
		}
		if (this.autoShift) {
			if(Math.abs(drivetrain.getSpeed()) > 3.0 && notTurning) {
				gear = Gear.HIGH;
				timeSlow = 0.0;
			} else if (Math.abs(drivetrain.getSpeed()) < 1.5 && notTurning) {
				timeSlow += this.timeSinceInitialized() - lastCheck;
				//wait 0.3 seconds at below 1.5fps before downshifting
				if (timeSlow >= 0.3) {
					gear = Gear.LOW;
				}
			} else {
				timeSlow = 0.0;
			}
		} else {
			if (lowGear.getAsBoolean()) {
				gear = Gear.LOW;
			}
			if (highGear.getAsBoolean()) {
				gear = Gear.HIGH;
			}
		}
		if (gear == Gear.HIGH) {
			_throttle = () -> {
				double val = throttle.getAsDouble();
				return (val > 1.0) ? 1.0 : val;
			};
		}
		drivetrain.shiftTo(gear);
		cdh.cheesyDrive(drivetrain, throttle.getAsDouble(), turn.getAsDouble(), gear == Gear.HIGH);
		
		SmartDashboard.putBoolean("Automatic Shifting", autoShift);
		SmartDashboard.putBoolean("Dirty Automatic Shifting", dirty);
		SmartDashboard.putBoolean("Toggle Automatic Shifting", this.toggleAutoShift.getAsBoolean());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		drivetrain.tank(0, 0);
	}

	@Override
	protected void interrupted() {
		drivetrain.tank(0, 0);
	}
}
