package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooter;

import com.team2383.robot.Constants.Preset;
import com.team2383.robot.Constants.ShooterPreset;

import edu.wpi.first.wpilibj.command.Command;

public class UsePreset extends Command {

	private final ShooterPreset preset;
	private final boolean finish;

	public UsePreset(Preset preset) {
		super("UsePreset");
		requires(shooter);
		this.preset = preset.get();
		this.finish = false;
	}

	public UsePreset(Preset preset, boolean finish) {
		super("UsePreset");
		requires(shooter);
		this.preset = preset.get();
		this.finish = finish;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		shooter.setBigFlywheelRPMSupplier(() -> preset.bigWheelRPM);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return finish;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}
}