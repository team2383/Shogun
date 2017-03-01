package com.team2383.auto;

import java.util.function.DoubleSupplier;

import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VishAuto extends CommandGroup {
	DoubleSupplier throttle = () -> 0.5;
	double time = 1.0;
    public VishAuto() {
    	addSequential(new AutoDriveStraight(throttle,time));
    }
}
