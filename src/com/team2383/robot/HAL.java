package com.team2383.robot;

import com.team2383.robot.subsystems.Drivetrain;
import com.team2383.robot.subsystems.Feeder;
import com.team2383.robot.subsystems.Hopper;
import com.team2383.robot.subsystems.Turret;

public class HAL {
	
	// subsystems
	public static Drivetrain drivetrain = new Drivetrain();
	public static Feeder feeder = new Feeder();
	public static Hopper hopper = new Hopper();
	public static Turret turret = new Turret();
}
