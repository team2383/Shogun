package com.team2383.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.AutoShoot;
import com.team2383.robot.commands.DisableDoor;
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
public class MiddleGear extends CommandGroup {

    public MiddleGear() {
    	addSequential(new DisableFlap());
    	addSequential(new AutoShoot(() -> 4000,3.5));
    	addSequential(new AutoDriveStraight(() -> -0.7, 3.0));
    	/*
    	 * addSequential(new DriveDistance(70.0, 0.7, Gear.LOW, true));
    	
    	addSequential(new AutoDriveStraight(() -> -0.4,0.8));
    	addSequential(new AutoDriveStraight(() -> 0.00001,0.5));
    	//addSequential(new AutoDriveStraight(() -> -0.5 , 1.0));
    	 * 
    	 */
    	addSequential(new EnableDoor());	
    	addSequential(new AutoDriveStraight(() -> 0.6 , 1.3));
    	/*
    	addSequential(new DriveDistance(-8.0, 0.4, Gear.LOW, true));
    	addSequential(new DriveDistance(57.5,0.7,Gear.LOW,true));
    	addSequential(new AutoDriveStraight(() -> 0.7 , 1.0));
    	*/
    }
}
