package com.team2383.auto;

import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootOnWall extends CommandGroup {
	public ShootOnWall(){
		addSequential(new Spool());
		Timer.delay(2.0);
		addSequential(new Shoot());
	}
}
