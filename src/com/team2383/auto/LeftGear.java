package com.team2383.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.AutoDriveStraight;
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
public class LeftGear extends CommandGroup {

    public LeftGear() {
    	addSequential(new DisableFlap());
    	addSequential(new DriveDistance(88, 0.9,Gear.LOW, true));
    	addSequential(new GyroTurn(1.0,60,1.75));
    	//addSequential(new DriveDistance(73, 0.7, Gear.LOW, true));
    	addSequential(new DriveDistance(83.5, 0.4, Gear.LOW, true));
    	addSequential(new AutoDriveStraight(() -> 0.5 , 0.25));
    }
}
