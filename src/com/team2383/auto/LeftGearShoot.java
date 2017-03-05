package com.team2383.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.EnableFlap;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftGearShoot extends CommandGroup {

    public LeftGearShoot() {
    	addSequential(new EnableFlap());
    	addSequential(new AutoDriveStraight(() -> 1.0, Constants.kdriveStraightTestTime));
    	addSequential(new GyroTurn(30));
    	addSequential(new AutoDriveStraight(() -> 0.4, 1.0));
    	addSequential(new Spool(3500,2.0));
        addParallel(new Spool(3500));
        addParallel(new Shoot());
    }
}
