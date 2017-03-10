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
public class MiddleGearTime extends CommandGroup {

    public MiddleGearTime() {
    	addSequential(new EnableFlap());
    	addSequential(new AutoDriveStraight(() -> Constants.kdriveStraightTestThrottle, Constants.kdriveStraightTestTime));
    	addSequential (new AutoDriveStraight(() -> 0.5, 0.7));
    }
}
