package com.team2383.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.AutoShoot;
import com.team2383.robot.commands.DisableFlap;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.EnableDoor;
import com.team2383.robot.commands.EnableFlap;
import com.team2383.robot.commands.EndShoot;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.AutoShoot;
import com.team2383.robot.commands.Spool;
import com.team2383.robot.commands.Wait;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */	
public class RedShootGear extends CommandGroup {

    public RedShootGear() {
    	addSequential(new DisableFlap());
    	addSequential(new DriveDistance(65, 0.9,Gear.LOW, true));
    	addSequential(new GyroTurn(1.0,-67,1.9));
    	//addSequential(new DriveDistance(73, 0.7, Gear.LOW, true));
    	addSequential(new AutoDriveStraight(() -> -0.7,2.8));
    	addParallel(new EnableDoor());
    	addSequential(new AutoDriveStraight(() -> 0.05,0.5));
    	addParallel(new DriveDistance(-68.5, 0.9, Gear.LOW, true));
    	addSequential(new AutoShoot(() -> 3600,10.0));
    }
}
