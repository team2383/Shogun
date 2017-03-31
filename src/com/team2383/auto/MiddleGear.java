package com.team2383.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.DisableFlap;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.EnableDoor;
import com.team2383.robot.commands.EnableFlap;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MiddleGear extends CommandGroup {

    public MiddleGear() {
    	addSequential(new DisableFlap());
    	addSequential(new DriveDistance(95.0, 0.7, Gear.LOW, true));
    	addSequential(new EnableDoor());
    	Timer.delay(2.0);
    	addSequential(new DriveDistance(-8.0, 0.4, Gear.LOW, true));
    }
}
