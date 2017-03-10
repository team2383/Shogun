package com.team2383.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.AutoShoot;
import com.team2383.robot.commands.DisableFlap;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.EnableFlap;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftWallShotTime extends CommandGroup {

    public LeftWallShotTime() {        
        addSequential(new EnableFlap());
    	addSequential(new AutoDriveStraight(() -> -0.8, 2.55));
    	addSequential(new GyroTurn(-90));
    	addSequential (new AutoDriveStraight(() -> -0.6, 1.7));
    	addSequential(new DisableFlap());
    	addSequential(new AutoShoot(() -> 3190, 12.0));
    }
}
