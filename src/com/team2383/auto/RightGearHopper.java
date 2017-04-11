package com.team2383.auto;

import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.AutoShoot;
import com.team2383.robot.commands.DisableFlap;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.EnableDoor;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightGearHopper extends CommandGroup {

    public RightGearHopper() {
    	 // Add Commands here:
        addSequential(new DisableFlap());
    	addSequential(new DriveDistance(90, 0.9,Gear.LOW, true));
    	addSequential(new GyroTurn(1.0,-55,1.75));
    	//addSequential(new DriveDistance(73, 0.7, Gear.LOW, true));
    	addSequential(new AutoDriveStraight(() -> -0.9,1.4));
    	addParallel(new EnableDoor());
    	addSequential(new AutoDriveStraight(() -> 0.01,1));
    	addSequential(new DriveDistance(-85.0, 0.9, Gear.LOW, true));
    	addSequential(new GyroTurn(1.0,120,2.0));
    	addParallel(new AutoDriveStraight(() -> -0.8, 1.0));
    	addSequential(new AutoShoot(() -> 3360,10.0));
    }
}
