package com.team2383.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestDrive extends CommandGroup {
	public TestDrive() {
		addSequential(new DriveDistance(125, 0.8, Gear.HIGH, true));
		
	
		
	}
}