package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;
import static com.team2383.robot.HAL.navX;

import com.team2383.ninjaLib.NullPIDOutput;
import com.team2383.robot.Constants;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drives in a straight line for a distance in inches
 *
 * @author Matthew Alonso
 *
 */

public class DriveDistance extends Command {
	private final PIDController turnController;
	private final PIDController distanceController;
	private final boolean brake;
	private final Gear gear;
	private double lastCheck;
	private double timeAtSetpoint;
	private final double tolerance;
	private final double wait;
	private boolean finish = true;
	private final double distance;

	public DriveDistance(double distance, double velocity, Gear gear, boolean brake) {
		this(velocity, distance, Constants.kDrivePositionTolerance, gear, brake);
	}

	public DriveDistance(double distance, double velocity, Gear gear, boolean brake, boolean finish) {
		this(velocity, distance, Constants.kDrivePositionTolerance, gear, brake);
		//this.finish = false;
	}

	public DriveDistance(double distance, double velocity, double tolerance, Gear gear, boolean brake) {
		this(velocity, distance, Constants.kDrivePositionTolerance, Constants.kPidSetpointWait, gear, brake);
	}

	public DriveDistance(double distance, double velocity, double tolerance, double wait, Gear gear, boolean brake) {
		super("Drive Distance");
		this.gear = gear;
		this.brake = brake;
		this.distance = distance;

		distanceController = new PIDController(Constants.kDrivePositionP, Constants.kDrivePositionI,
				Constants.kDrivePositionD, 0.0, drivetrain, new NullPIDOutput());
		distanceController.setSetpoint(distance);
		distanceController.setOutputRange(-velocity, velocity);

		SmartDashboard.putData("Distance Controller", distanceController);
		
    	//LiveWindow.addActuator("Drive","Distance", distanceController);


		navX.reset();
		turnController = new PIDController(Constants.kDriveHeadingMaintainP, Constants.kDriveHeadingMaintainI,
				Constants.kDriveHeadingMaintainD, Constants.kDriveHeadingMaintainF, navX, new NullPIDOutput());
		turnController.setInputRange(-180.0, 180.0);
		turnController.setOutputRange(-1.0, 1.0); // changed from .5 if auto
													// is fucked
		//turnController.setContinuous();
		turnController.setSetpoint(0);

		this.tolerance = tolerance;
		this.wait = wait;

		SmartDashboard.putData("MaintainHeading Controller", turnController);
    	//LiveWindow.addActuator("Drive", "Heading", turnController);

		requires(drivetrain);
	}

	@Override
	protected void initialize() {
		this.turnController.enable();
		this.distanceController.enable();
		drivetrain.resetEncoders();
		navX.reset();
		drivetrain.shiftTo(gear);
		drivetrain.setBrake(brake);
	}

	@Override
	protected void execute() {
		if (this.timeSinceInitialized() > 0.1) {
			drivetrain.arcade(-distanceController.get(), -turnController.get());
		} else {
			System.out.println("Waiting for reset " + this.timeSinceInitialized());
		}
	}

	@Override
	protected boolean isFinished() {
		Timer.delay(2.0);
		/*
		if (Math.abs(distanceController.getError()) <= tolerance) {
			timeAtSetpoint += this.timeSinceInitialized() - lastCheck;
		} else {
			timeAtSetpoint = 0;
		}
		SmartDashboard.putNumber("error", distanceController.getError());
		SmartDashboard.putNumber("Tolerance", tolerance);
		SmartDashboard.putNumber("timeAtSetpoint", timeAtSetpoint);
		lastCheck = this.timeSinceInitialized();
		return finish && timeAtSetpoint >= wait;
		*/
		return true;
	}

	@Override
	protected void end() {
		drivetrain.tank(0, 0);
		drivetrain.resetEncoders();
		this.turnController.disable();
		this.distanceController.disable();
	}

	@Override
	protected void interrupted() {
		end();
	}
}