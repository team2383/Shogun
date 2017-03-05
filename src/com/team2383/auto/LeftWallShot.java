package com.team2383.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.DisableFlap;
import com.team2383.robot.commands.EnableFlap;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftWallShot extends CommandGroup {

    public LeftWallShot() {
    	addSequential(new EnableFlap());
    	addSequential(new AutoDriveStraight(() -> -0.8, 2.55)); // 0.6 , 5.2
    	addSequential(new GyroTurn(90));
    	addSequential (new AutoDriveStraight(() -> 0.6, 3));
    	addSequential(new DisableFlap());
    	addSequential(new Spool(3080,2.0));
        addParallel(new Spool(3080));
        addParallel(new Shoot());
    }
}
