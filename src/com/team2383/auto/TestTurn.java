package com.team2383.auto;

import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestTurn extends CommandGroup {
	public double expectedAngle;
	public TestTurn() {
		SmartDashboard.putNumber("Expected Gyro Turn Number", expectedAngle);
		addSequential(new GyroTurn(expectedAngle, false));
	}
}