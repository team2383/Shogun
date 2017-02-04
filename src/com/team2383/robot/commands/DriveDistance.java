package com.team2383.robot.commands;

import com.team2383.ninjaLib.NullPIDOutput;
import com.team2383.robot.Constants;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static com.team2383.robot.HAL.drivetrain;
import static com.team2383.robot.HAL.navX;

/**
 *	For Autonomous
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
		this(distance, velocity, Constants.kDrivePositionTolerance, gear, brake);
	}

	public DriveDistance(double distance, double velocity, Gear gear, boolean brake, boolean finish) {
		this(distance, velocity, Constants.kDrivePositionTolerance, gear, brake);
		this.finish = false;
	}

	public DriveDistance(double distance, double velocity, double tolerance, Gear gear, boolean brake) {
		this(distance, velocity, Constants.kDrivePositionTolerance, Constants.kPidSetpointWait, gear, brake);
	}
	
    public DriveDistance(double distance, double velocity, double tolerance, double wait, Gear gear, boolean brake) {
		super("Drive Distance");
		
		this.distance = distance;
		this.gear = gear;
		this.brake = brake;
		
		/*
		 *  initializing distance PIDController and turn PIDController
		 */
		distanceController = new PIDController(Constants.kDrivePositionP, Constants.kDrivePositionI,
				Constants.kDrivePositionD, Constants.kDrivePositionF, drivetrain, new NullPIDOutput());
		
		distanceController.setSetpoint(distance);
		distanceController.setOutputRange(-velocity, velocity);
		
		SmartDashboard.putData("Distance Controller", distanceController);
		
		navX.reset();
		
		turnController = new PIDController(Constants.kDriveHeadingMaintainP, Constants.kDriveHeadingMaintainI,
				Constants.kDriveHeadingMaintainD, Constants.kDriveHeadingMaintainF, drivetrain, new NullPIDOutput());
		
		turnController.setInputRange(-180.0, 180.0);
		turnController.setOutputRange(-1.0, 1.0);
		
		turnController.setContinuous();
		turnController.setSetpoint(0.0); // we dont want any deviation in angle
		
		this.tolerance = tolerance;
		this.wait = wait;
		
		SmartDashboard.putData("Maintain Heading Controller", turnController);
		
		requires(drivetrain);
	}

	// Called just before this Command runs the first time
    protected void initialize() {
    	this.distanceController.enable();
    	this.turnController.enable();
    	drivetrain.resetEncoders();
    	navX.reset();
    	drivetrain.shiftTo(gear);
    	drivetrain.setBrake(brake);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (this.timeSinceInitialized() > 0.1) {
			drivetrain.arcade(distanceController.get(), turnController.get());
		} else {
			System.out.println("Waiting for reset " + this.timeSinceInitialized());
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
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
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.tank(0, 0);
		drivetrain.resetEncoders();
		this.distanceController.disable();
		this.turnController.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
