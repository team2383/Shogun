package com.team2383.auto;

import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.AutoShoot;
import com.team2383.robot.commands.DisableFlap;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.EnableFlap;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightWallShotClose extends CommandGroup {

    public RightWallShotClose() {
    	addSequential(new EnableFlap());
    	addSequential(new DriveDistance(80, 0.9, Gear.LOW, true));
    	addSequential(new GyroTurn(1.0,90,5.0));
    	addParallel(new AutoDriveStraight(() -> 0.6, 1.0));
    	addSequential(new AutoDriveStraight(() -> -0.05,1.0));
    	addSequential(new DriveDistance(-70, 0.9, Gear.LOW, true));
    	addSequential(new GyroTurn(-90));
    	addSequential(new AutoDriveStraight(() -> -0.9, 0.9));
    	//addSequential(new DriveDistance(28, 0.6, Gear.HIGH, true));
    	addSequential(new AutoShoot(() -> 3310,8.0));
    	addSequential(new DisableFlap());
    }
}
