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
public class RightWallShotFar extends CommandGroup {

    public RightWallShotFar() {
    	addSequential(new EnableFlap());
    	addSequential(new DriveDistance(115, 0.90, Gear.LOW, true));
    	addSequential(new GyroTurn(1.0,90,3.0));
    	addParallel(new AutoDriveStraight(() -> -0.6, 1.3));
    	//addSequential(new DriveDistance(28, 0.6, Gear.HIGH, true));
    	addSequential(new AutoShoot(() -> 3630,10.0)); //ITS CA$$H MONEY 
    	addSequential(new DisableFlap());
    }
}
