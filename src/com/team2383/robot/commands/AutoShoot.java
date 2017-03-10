package com.team2383.robot.commands;

import static com.team2383.robot.HAL.conveyor;
import static com.team2383.robot.HAL.agitator;
import static com.team2383.robot.HAL.shooter;

import java.util.function.DoubleSupplier;

import com.team2383.robot.Constants;
import com.team2383.robot.HAL;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * Spool shooter flywheel
 */
public class AutoShoot extends Command {
	double timeAtSetpoint;
	double lastCheck;
	double shootStartTime;
	boolean setShootStart;
	boolean done;
	private final DoubleSupplier bigFlywheelRPMSupplier;
	private double time;

	public AutoShoot(DoubleSupplier bigFlywheelRPMSupplier, double time) {
		super("Auto Shoot");
		requires(shooter);
		this.bigFlywheelRPMSupplier = bigFlywheelRPMSupplier;
		this.time = time;
		SmartDashboard.putBoolean("spooling?", false);
	}

	@Override
	protected void initialize() {
		timeAtSetpoint = 0.0;
		lastCheck = 0.0;
		shootStartTime = 0.0;
		done = false;
		setShootStart = false;
		shooter.setBigFlywheelRPMSupplier(bigFlywheelRPMSupplier);
	}

	@Override
	protected void execute() {
		SmartDashboard.putBoolean("spooling?", true);
		shooter.spool();

		if (readyToShoot() && !setShootStart) {
			// shooter has reached rpm
			// so record when we started so we know when
			// to stop running the feeder
			this.shootStartTime = this.timeSinceInitialized();
			this.setShootStart = true;
		}

		if (setShootStart && this.shootStartTime != 0.0) {
			// we set a shootStartTime
			// so we know we are readyToShoot
			// so we should run the feeder to shoot
			conveyor.feed();
			agitator.feed();

			// we are done and can stop the feeder and scommand
			if (this.timeSinceInitialized() - shootStartTime > time) {
				conveyor.stop();
				agitator.stop();
				done = true;
			}
		} else {
			// we havent set a start or it was invalid so reset
			this.shootStartTime = 0.0;
			this.setShootStart = false;
		}
	}

	@Override
	protected boolean isFinished() {
		return done;
	}

	@Override
	protected void end() {
		SmartDashboard.putBoolean("spooling?", false);
		shooter.stop();
		agitator.stop();
		conveyor.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}

	private boolean readyToShoot() {
		if (shooter.isBigWheelAtSetpoint()) {
			timeAtSetpoint += this.timeSinceInitialized() - lastCheck;
		} else {
			timeAtSetpoint = 0;
		}
		lastCheck = this.timeSinceInitialized();
		return timeAtSetpoint >= Constants.kShooterWaitTime;
	}
}