package com.team2383.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.AutoShoot;
import com.team2383.robot.commands.DisableFlap;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.EnableDoor;
import com.team2383.robot.commands.EnableFlap;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;
import com.team2383.robot.commands.Wait;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightGear extends CommandGroup {

    public RightGear() {
    	addSequential(new DisableFlap());
    	addSequential(new DriveDistance(68, 0.9,Gear.LOW, true));
    	addSequential(new GyroTurn(1.0,-64,1.75));
    	//addSequential(new DriveDistance(73, 0.7, Gear.LOW, true));
    	addSequential(new AutoDriveStraight(() -> -0.7,2.0));
    	addParallel(new EnableDoor());
    	addSequential(new AutoDriveStraight(() -> 0.01,1.5));
    	addSequential(new DriveDistance(-60.0, 0.6, Gear.LOW, true));
    }
}
