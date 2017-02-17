package com.team2383.robot.commands;

import static com.team2383.robot.HAL.conveyor;
import static com.team2383.robot.HAL.agitator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Activates the conveyor belt below the turret to feed balls into the shooter
 *
 * @author Matthew Alonso
 *
 */
public class Shoot extends Command {
	public Shoot() {
		super("Kick Ball");
		requires(conveyor);
		requires(agitator);
	}

	public Shoot(double timeout) {
		super("Kick Ball", timeout);
		requires(conveyor);
		requires(agitator);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		conveyor.feed();
		agitator.feed();
	}

	@Override
	protected boolean isFinished() {
		return this.isTimedOut();
	}

	@Override
	protected void end() {
		conveyor.stop();
		agitator.stop();
	}

	@Override
	protected void interrupted() {
		conveyor.stop();
		agitator.stop();
	}
}
