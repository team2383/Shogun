package com.team2383.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.DisableFlap;
import com.team2383.robot.commands.EnableFlap;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MiddleGearShoot extends CommandGroup {

    public MiddleGearShoot() {
    	addSequential(new EnableFlap());
    	addSequential(new AutoDriveStraight(() -> Constants.kdriveStraightTestThrottle, Constants.kdriveStraightTestTime));
    	addSequential (new AutoDriveStraight(() -> -0.3, 0.65));
    	/*
    	addSequential(new Spool(3680,2.0));
        addParallel(new Spool(3680));
        addParallel(new Shoot());
        */
    }
}
