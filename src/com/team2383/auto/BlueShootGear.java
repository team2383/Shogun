package com.team2383.auto;

import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.AutoShoot;
import com.team2383.robot.commands.DisableFlap;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.EnableDoor;
import com.team2383.robot.commands.EnableFlap;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BlueShootGear extends CommandGroup {
    public BlueShootGear() {
    	addSequential(new DisableFlap());
    	addSequential(new DriveDistance(72, 0.9,Gear.LOW, true));
    	addSequential(new GyroTurn(0.8,57,1.9));
    	//addSequential(new DriveDistance(73, 0.7, Gear.LOW, true));
    	//addSequential(new DriveDistance(60.0, 0.6, Gear.LOW, true));
    	addSequential(new AutoDriveStraight(() -> -0.8,1.0));
    	//addSequential(new AutoDriveStraight(() -> 0.5 , 0.25));
    	addParallel(new EnableDoor());
    	addSequential(new AutoDriveStraight(() -> 0.01,1.5));
    	addParallel(new DriveDistance(-32.5, 0.9, Gear.LOW, true));
    	addSequential(new AutoShoot(() -> 3600,10.0));
    }
}
