package com.team2383.auto;

import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.AutoShoot;
import com.team2383.robot.commands.EnableFlap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BlueShootGear extends CommandGroup {

    public BlueShootGear() {
    	addSequential(new EnableFlap());
    	addSequential(new AutoShoot(() -> 3265,5.0));
        addSequential(new AutoDriveStraight(() -> -0.78, 1.75));
    	addSequential (new AutoDriveStraight(() -> 0.5, 0.65));
    }
}
