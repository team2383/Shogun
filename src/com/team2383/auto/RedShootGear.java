package com.team2383.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.AutoShoot;
import com.team2383.robot.commands.EnableFlap;
import com.team2383.robot.commands.EndShoot;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.AutoShoot;
import com.team2383.robot.commands.Spool;
import com.team2383.robot.commands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RedShootGear extends CommandGroup {

    public RedShootGear() {
    	addSequential(new EnableFlap());
    	addSequential(new AutoShoot(() -> 3240,5.0));
        addSequential(new AutoDriveStraight(() -> -0.78, 2.0));
    	addSequential (new AutoDriveStraight(() -> -0.3, 0.65));
    }
}
