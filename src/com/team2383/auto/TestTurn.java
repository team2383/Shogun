package com.team2383.auto;

import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestTurn extends CommandGroup {
	public TestTurn() {
		addSequential(new GyroTurn(90, false));
	}
}