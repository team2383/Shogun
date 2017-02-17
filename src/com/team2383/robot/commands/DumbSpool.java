package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooter;


import edu.wpi.first.wpilibj.command.Command;


public class DumbSpool extends Command {


	public DumbSpool() {
		super("Dumb Spool");
		requires(shooter);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		shooter.dumbSpool();
	}	

	@Override
	protected boolean isFinished() {
		return this.isTimedOut();
	}

	@Override
	protected void end() {
		shooter.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
