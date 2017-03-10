package com.team2383.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.EnableFlap;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightGearTime extends CommandGroup {
	    public RightGearTime() {
	    	addSequential(new EnableFlap());
	    	addSequential(new AutoDriveStraight(() -> Constants.kdriveStraightTestThrottle, Constants.kdriveStraightTestTime));
	    	addSequential(new GyroTurn(-30));
	    	addSequential(new AutoDriveStraight(() -> 0.4, 1.0));
	    }
}
