	
package com.team2383.robot;

import com.team2383.auto.ShootAtWall;
import com.team2383.auto.TestDrive;
import com.team2383.auto.TestTurn;
import com.team2383.auto.VishAuto;
import com.team2383.ninjaLib.NullCommand;
import com.team2383.robot.commands.GeneralPeriodic;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	private UsbCamera camera0;

	Command autoCommand;
	Command generalPeriodicCommand = new GeneralPeriodic();
	SendableChooser<Command> autoChooser;

	@SuppressWarnings("unused")
	@Override
	public void robotInit() {
		HAL hal = new HAL();
		Constants constants = new Constants();
		OI oi = new OI();
		
		
		autoChooser = new SendableChooser<Command>();
		autoChooser.addObject("null auto", new NullAuto());
		autoChooser.addObject("Test Drive", new TestDrive());
		autoChooser.addObject("Test Turn", new TestTurn());
		autoChooser.addObject("Shoot At Wall", new ShootAtWall());
		autoChooser.addObject("Vish Auto", new VishAuto());
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
	
		CameraServer.getInstance().startAutomaticCapture();
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
