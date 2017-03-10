package com.team2383.auto;

import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.AutoShoot;
import com.team2383.robot.commands.DisableFlap;
import com.team2383.robot.commands.EnableFlap;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;


import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightWallShotTime extends CommandGroup {

    public RightWallShotTime() {
    	addSequential(new EnableFlap());
    	addSequential(new AutoDriveStraight(() -> -1.0, 1.925));
    	addSequential(new GyroTurn(90));
    	addSequential (new AutoDriveStraight(() -> 0.6, 1.5));
    	addSequential(new DisableFlap());
    	addSequential(new AutoShoot(() -> 3190, 12.0));
    }
}
