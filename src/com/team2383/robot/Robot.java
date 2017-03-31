	
package com.team2383.robot;

import com.team2383.auto.BlueShootGear;
import com.team2383.auto.LeftGear;
import com.team2383.auto.LeftGearTime;
import com.team2383.auto.LeftWallShot;
import com.team2383.auto.LeftWallShotTime;
import com.team2383.auto.MiddleGear;
import com.team2383.auto.MiddleGearTime;
import com.team2383.auto.RightGearTime;
import com.team2383.auto.RightWallShot;
import com.team2383.auto.RightWallShotTime;
import com.team2383.auto.TestDrive;
import com.team2383.auto.TestTurn;
import com.team2383.auto.RedShootGear;
import com.team2383.auto.RightGear;
import com.team2383.ninjaLib.NullCommand;
import com.team2383.robot.commands.GeneralPeriodic;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	Command autoCommand;
	Command generalPeriodicCommand = new GeneralPeriodic();
	SendableChooser<Command> autoChooser;
	@SuppressWarnings("unused")
	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
		HAL hal = new HAL();
		Constants constants = new Constants();
		OI oi = new OI();
		
		
		
		autoChooser = new SendableChooser<Command>();
		autoChooser.addObject("null auto", new NullAuto());
		
		autoChooser.addObject("Middle Gear Time", new MiddleGearTime());
		autoChooser.addObject("Middle Gear", new MiddleGear());
		
		autoChooser.addObject("Left Gear Time", new LeftGearTime());
		autoChooser.addObject("Left Gear", new LeftGear());
		
		autoChooser.addObject("Right Gear Time", new RightGearTime());
		autoChooser.addObject("Right Gear", new RightGear());
		
		autoChooser.addObject("Left Wall Shot Time", new LeftWallShotTime());
		autoChooser.addObject("Left Wall Shot", new LeftWallShot());
		
		autoChooser.addObject("Right Wall Shot Time", new RightWallShotTime());
		autoChooser.addObject("Right Wall Shot", new RightWallShot());
		
		autoChooser.addObject("Red Wall Shot", new RedShootGear());
		autoChooser.addObject("Blue Wall Shot", new BlueShootGear());
		
		autoChooser.addObject("Test Drive", new TestDrive());
		autoChooser.addObject("Test Turn", new TestTurn());
		SmartDashboard.putData("Auto Chooser", autoChooser);
		
	}

	@Override
	public void disabledInit() {
		if (!generalPeriodicCommand.isRunning()) {
			generalPeriodicCommand.start();
		}
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		HAL.navX.reset();
		autoCommand = (Command) autoChooser.getSelected();
		if (autoCommand != null) {
			autoCommand.start();
		}

		if (!generalPeriodicCommand.isRunning()) {
			generalPeriodicCommand.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		
		HAL.navX.reset();
		
		if (!generalPeriodicCommand.isRunning()) {
			generalPeriodicCommand.start();
		}
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autoCommand != null) {
			autoCommand.cancel();
		}
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
