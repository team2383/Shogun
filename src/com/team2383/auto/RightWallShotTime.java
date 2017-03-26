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
    	addSequential(new AutoDriveStraight(() -> -0.8, 1.6));
    	addSequential(new GyroTurn(-90));
    	addSequential (new AutoDriveStraight(() -> 0.6, 1.725));
    	addSequential(new AutoShoot(() -> 3130,8.0));
    	addSequential(new DisableFlap());
    }
}
 