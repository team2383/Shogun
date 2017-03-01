package com.team2383.auto;

import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestDrive extends CommandGroup {
	public TestDrive() {
		addSequential(new DriveDistance(0.016, 1.0, Gear.LOW, false, true));
	}
}