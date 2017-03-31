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
public class RightWallShot extends CommandGroup {

    public RightWallShot() {
    	addSequential(new EnableFlap());
    	addSequential(new DriveDistance(116, 0.90, Gear.LOW, true));
    	addSequential(new GyroTurn(1.0,90,5.0));
    	addParallel(new AutoDriveStraight(() -> -1.0, 0.5));
    	//addSequential(new DriveDistance(28, 0.6, Gear.HIGH, true));
    	addSequential(new AutoShoot(() -> 3600,8.0));
    	addSequential(new DisableFlap());
    }
}
