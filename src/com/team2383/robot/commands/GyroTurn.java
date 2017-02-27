package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;
import static com.team2383.robot.HAL.navX;

import com.team2383.robot.Constants;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroTurn extends PIDCommand {

	private double timeAtSetpoint;
	private double lastCheck;
	private final double tolerance;
	private final double wait;
	private boolean finish = true;

	public GyroTurn(double angle) {
		this(Constants.kDriveTurnVelocity, angle);
	}

	public GyroTurn(double angle, boolean finish) {
		this(Constants.kDriveTurnVelocity, angle);
		this.finish = false;
	}

	public GyroTurn(double velocity, double angle) {
		this(velocity, angle, Constants.kDriveTurnTolerance);
	}

	public GyroTurn(double velocity, double angle, double tolerance) {
		this(velocity, angle, tolerance, Constants.kPidSetpointWait);
	}

	public GyroTurn(double velocity, double angle, double tolerance, double wait) {
		super("Set Heading", Constants.kDriveTurnP, 0.0, Constants.kDriveTurnD);
		requires(drivetrain);
		this.getPIDController().setInputRange(-180.0, 180.0);
		this.getPIDController().setOutputRange(-velocity, velocity);
		this.getPIDController().setContinuous();
		this.getPIDController().setSetpoint(angle);
		this.tolerance = tolerance;
		this.wait = wait;
		SmartDashboard.putData("Turn Controller", this.getPIDController());
	}

	@Override
	protected void initialize() {
		navX.reset();
		drivetrain.enableBrake();
		drivetrain.shiftTo(Gear.LOW); // High gear now for max speed
	}

	@Override
	protected void execute() {
		/*
		 * IZone check
		 */
		if (Math.abs(this.getPIDController().getError()) <= Constants.kDriveTurnIZone) {
			this.getPIDController().setPID(Constants.kDriveTurnP, Constants.kDriveTurnI, Constants.kDriveTurnD);
		} else if(Math.abs(this.getPIDController().getError()) <= 1.0) {
			this.getPIDController().setPID(Constants.kDriveTurnP, 0.0, Constants.kDriveTurnD);
		}
	}

	@Override
	protected boolean isFinished() {
		if (Math.abs(this.getPIDController().getError()) <= tolerance) {
			timeAtSetpoint += this.timeSinceInitialized() - lastCheck;
		} else {
			timeAtSetpoint = 0;
		}
		SmartDashboard.putNumber("error", this.getPIDController().getError());
		SmartDashboard.putNumber("Tolerance", tolerance);
		SmartDashboard.putNumber("timeAtSetpoint", timeAtSetpoint);
		lastCheck = this.timeSinceInitialized();
		return finish && timeAtSetpoint >= wait;
	}

	@Override
	protected void end() {
		drivetrain.tank(0, 0);
	}

	@Override
	protected void interrupted() {
		drivetrain.tank(0, 0);
	}

	@Override
	protected double returnPIDInput() {
		return navX.getYaw();
	}

	@Override
	protected void usePIDOutput(double output) {
		if (this.timeSinceInitialized() > 0.1) {
			drivetrain.tank(output, -output); // vish's fix for gyro
		} else {
			System.out.println("Waiting for reset");
		}
	}

}