package com.team2383.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.EnableFlap;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftGear extends CommandGroup {

    public LeftGear() {
    	addSequential(new EnableFlap());
    	addSequential(new DriveDistance(56.5, 0.4, Gear.LOW, true));
    	addSequential(new GyroTurn(60));
    	addSequential(new DriveDistance(83.5, 0.4, Gear.LOW, true));
    	addSequential(new DriveDistance(-3.0, 0.4, Gear.LOW, true));
    }
}
