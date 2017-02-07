package com.team2383.robot;

import com.kauailabs.navx.frc.AHRS;
import com.team2383.robot.subsystems.Drivetrain;
import com.team2383.robot.subsystems.Feeder;
import com.team2383.robot.subsystems.Agitator;
import com.team2383.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.SPI;

public class HAL {
	
	// subsystems
	public static Drivetrain drivetrain = new Drivetrain();
	public static Feeder feeder = new Feeder();
	public static Agitator hopper = new Agitator();
	public static Turret turret = new Turret();
	
	public static AHRS navX = new AHRS(SPI.Port.kMXP);
}
