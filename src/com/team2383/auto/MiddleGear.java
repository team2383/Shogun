package com.team2383.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.EnableFlap;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MiddleGear extends CommandGroup {

    public MiddleGear() {
    	addSequential(new EnableFlap());
    	addSequential(new DriveDistance(80.0, 0.4, Gear.LOW, true));
    	addSequential(new DriveDistance(-3.0, 0.4, Gear.LOW, true));
    }
}
