package com.team2383.robot;

import com.kauailabs.navx.frc.AHRS;
import com.team2383.robot.subsystems.Drivetrain;
import com.team2383.robot.subsystems.Feeder;
import com.team2383.robot.subsystems.GearDoor;
import com.team2383.robot.subsystems.GearFlap;
import com.team2383.robot.subsystems.Conveyor;
import com.team2383.robot.subsystems.Agitator;
import com.team2383.robot.subsystems.Climber;
import com.team2383.robot.subsystems.Turret;
import com.team2383.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.SPI;

public class HAL {
	
	// subsystems
	public static Drivetrain drivetrain = new Drivetrain();
	public static Feeder feeder = new Feeder();
	public static GearDoor gearDoor = new GearDoor();
	public static GearFlap gearFlap = new GearFlap();
	public static Conveyor conveyor = new Conveyor();
	public static Agitator agitator = new Agitator();
	public static Turret turret = new Turret();
	public static Shooter shooter = new Shooter();
	public static Climber climber = new Climber();
	
	public static AHRS navX = new AHRS(SPI.Port.kMXP);
}
