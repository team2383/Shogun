package com.team2383.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.HoldTurret;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import com.team2383.ninjaLib.WPILambdas;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Turret extends Subsystem {
	private CANTalon turret = new CANTalon(Constants.kTurretTalonID);
	private DoubleSupplier angleSupplier = () -> 0;
	
	/**
	 * the turret aiming motor is a 100:1 versaplanetary driven by a BAG motor, using a 10 turn pot on the versaplanetary output to close the loop.
	 * the turret has a 128tooth pulley, the versaplanetary output has a 24 tooth pulley
	 * 
	 * the turret is controlled using functional supplier functions for each parameter.
	 * for constant updates to the turret angle, update must be called within a loop
	 * the turret angle functions allows for manual, non loop control for autonomous/testing/tuning purposes,
	 */
	public Turret() {
		turret.enableBrakeMode(true);
		turret.setFeedbackDevice(FeedbackDevice.AnalogPot);
		turret.changeControlMode(TalonControlMode.Position);
		turret.setPID(Constants.kTurretPositionP, Constants.kTurretPositionI, Constants.kTurretPositionD,
				Constants.kTurretPositionF, Constants.kTurretPositionIZone, 0, 0);
		
		turret.setReverseSoftLimit(Constants.kTurretReverseLimit);
		turret.setForwardSoftLimit(Constants.kTurretForwardLimit);
		turret.configPotentiometerTurns(10);
		turret.enableForwardSoftLimit(false);
		turret.enableReverseSoftLimit(false);
		turret.reverseOutput(false);
		turret.reverseSensor(true);
	}

	/**
	 * continually updates turret to follow it's setpoint
	 *
	 */
	public void update() {
		turret.changeControlMode(TalonControlMode.Position);
		turret.enable();
		setAngle(angleSupplier.getAsDouble());
	}

	@Override
	protected void initDefaultCommand() {
		this.setDefaultCommand(new HoldTurret());
	}
	
	//turret methods
	
	public boolean isAtSetpoint() {
		return Math.abs(getPotRotations() - getPotRotationsSetpoint()) <= Constants.kTurretTolerance;
	}
	
	public void moveAtSpeed(double speed) {
		turret.changeControlMode(TalonControlMode.PercentVbus);
		turret.set(speed);
	}
	
	public void stop() {
		turret.changeControlMode(TalonControlMode.PercentVbus);
		turret.set(0);
	}

	public void holdPosition() {
		this.setAngle(this.getAngle());
	}
	
	/**
	 * Sets the turret angle supplier for realtime turret control
	 * @param 
	 */
	public void setAngleSupplier(DoubleSupplier angleSupplier) {
		angleSupplier = angleSupplier;
	}
	
	
	/**
	 * Sets the turret position setpoint to the passed in angle
	 * @param angle desired turret setpoint
	 */
	public void setAngle(double angle) {
		turret.changeControlMode(TalonControlMode.Position);
		turret.setSetpoint(turretAngleToPotAngle(angle));
	}
	
	/**
	 * get turret current aim
	 * @return the angle (in degrees) of the turret's current aim,
	 * 0 degrees being the counterclockwise hard stop,
	 * 270 degrees being the clockwise hard stop
	 */
	public double getAngle() {
		return potAngleToTurretAngle(getPotAngle());
	}
	
	/**
	 * get turret desired aim
	 * @return the angle (in degrees) of the turret's current aim,
	 * 0 degrees being the counterclockwise hard stop,
	 * 270 degrees being the clockwise hard stop
	 */
	public double getAngleSetpoint() {
		return potAngleToTurretAngle(getPotAngleSetpoint());
	}

	/**
	 * get turret pot angle in degrees
	 * @return turret pot angle in degrees
	 */
	public double getPotAngle() {
		return getPotRotations() * 360.0;
	}
	
	
	/**
	 * get turret pot angle in degrees
	 * @return turret pot angle in degrees
	 */
	public double getPotAngleSetpoint() {
		return getPotRotationsSetpoint() * 360.0;
	}
	
	
	/**
	 * get raw turret pot angle in degrees
	 * @return raw pot angle in degrees
	 */
	public double getPotRotations() {
		return turret.getPosition();
	}
	
	/**
	 * get desired raw turret pot angle in degrees
	 * @return desired raw pot angle in degrees
	 */
	public double getPotRotationsSetpoint() {
		return turret.getSetpoint();
	}
	
	//turret unit conversion methods
	
	/**
	 * 
	 * @param turret angle in turret degrees
	 * @return equivalent angle in potentiometer degrees
	 */
	public static double turretAngleToPotAngle(double turret) {
		return turret * (1.0/Constants.kTurretPotAngleRatio);
	}
	
	/**
	 * 
	 * @param pot the current degree value of the pop
	 * @return the current degree angle of the turret
	 */
	public static double potAngleToTurretAngle(double pot) {
		return pot * Constants.kTurretPotAngleRatio;
	}
}
