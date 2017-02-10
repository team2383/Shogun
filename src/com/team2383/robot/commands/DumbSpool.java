package com.team2383.robot.commands;

import static com.team2383.robot.HAL.turret;


import edu.wpi.first.wpilibj.command.Command;


public class DumbSpool extends Command {


	public DumbSpool() {
		super("Dumb Spool");
		requires(turret);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		turret.dumbSpool();
	}	

	@Override
	protected boolean isFinished() {
		return this.isTimedOut();
	}

	@Override
	protected void end() {
		turret.dumbStop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
