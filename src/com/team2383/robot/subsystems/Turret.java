package com.team2383.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.team2383.robot.Constants;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Turret extends Subsystem {

	private CANTalon littleFlywheel;
	private CANTalon bigFlywheel;
	private CANTalon turret;
	
	private DoubleSupplier rpmSupplier = () -> 0;

	public Turret() {
		littleFlywheel = new CANTalon(Constants.kLittleFlywheelTalonID);
		bigFlywheel = new CANTalon(Constants.kBigFlywheelTalonID);
		turret = new CANTalon(Constants.kTurretTalonID);
		
		littleFlywheel.enableBrakeMode(false);
		littleFlywheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		littleFlywheel.changeControlMode(TalonControlMode.Speed);
		littleFlywheel.reverseOutput(true);
		littleFlywheel.reverseSensor(false);
		littleFlywheel.configPeakOutputVoltage(3.0, -12.0);
		littleFlywheel.setPID(Constants.kLittleFlywheelP, Constants.kLittleFlywheelI, Constants.kLittleFlywheelD,
				Constants.kLittleFlywheelF, Constants.kLittleFlywheelIZone, 0, 0);
		littleFlywheel.enable();
		
		bigFlywheel.enableBrakeMode(false);
		bigFlywheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		bigFlywheel.changeControlMode(TalonControlMode.Speed);
		bigFlywheel.reverseOutput(true);
		bigFlywheel.reverseSensor(false);
		bigFlywheel.configPeakOutputVoltage(3.0, -12.0);
		bigFlywheel.setPID(Constants.kBigFlywheelP, Constants.kBigFlywheelI, Constants.kBigFlywheelD,
				Constants.kBigFlywheelF, Constants.kBigFlywheelIZone, 0, 0);
		bigFlywheel.enable();
	}

	/**
	 * call periodically to spool the shooter to a certain RPM BANG-BANG
	 * controller: Off if at or above setpoint, On otherwise.
	 *
	 * @param rpm
	 */
	public void spoolToSetpoint() {
		double setRPM = rpmSupplier.getAsDouble();
		bigFlywheel.changeControlMode(TalonControlMode.Speed);
		bigFlywheel.enable();
		bigFlywheel.setSetpoint(setRPM);
		bigFlywheel.enableBrakeMode(false);
		
		littleFlywheel.changeControlMode(TalonControlMode.Speed);
		littleFlywheel.enable();
		littleFlywheel.setSetpoint(setRPM);
		littleFlywheel.enableBrakeMode(false);
	}

	public void stop() {
		
		bigFlywheel.enableBrakeMode(true);
		bigFlywheel.disable();
	
		littleFlywheel.enableBrakeMode(true);
		littleFlywheel.disable();
	}
	
	public void dumbStop(){
		littleFlywheel.changeControlMode(TalonControlMode.PercentVbus);
		bigFlywheel.changeControlMode(TalonControlMode.PercentVbus);
		littleFlywheel.set(0.0);
		bigFlywheel.set(0.0);
	}
	
	public void dumbSpool(){
		littleFlywheel.changeControlMode(TalonControlMode.PercentVbus);
		bigFlywheel.changeControlMode(TalonControlMode.PercentVbus);
		bigFlywheel.enableBrakeMode(false);
		littleFlywheel.enableBrakeMode(false);
		littleFlywheel.set(-0.7);
		bigFlywheel.set(-0.9);
	}

	public void setRPM(double rpm) {
		setRPM(() -> rpm);
	}

	public void setRPM(DoubleSupplier rpmSupplier) {
		this.rpmSupplier = rpmSupplier;
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	public boolean isBigWheelAtSetpoint() {

		return Math.abs(getBigWheelRPM() - bigFlywheel.getSetpoint()) <= Constants.kShooterRPMTolerance;
	}
	
	public boolean isLittleWheelAtSetpoint() {
		return Math.abs(getLittleWheelRPM() - littleFlywheel.getSetpoint()) <= Constants.kShooterRPMTolerance;
	}
	
	public boolean isTurretAtSetpoint() {
		return false; //Math.abs(getRPM() - bigFlywheel.getSetpoint()) <= Constants.shooterRPMTolerance;
	}

	public double getBigWheelRPM() {
		return bigFlywheel.getSpeed();
	}
	
	public double getLittleWheelRPM() {
		return littleFlywheel.getSpeed();
	}

	public double getSuppliedSetpoint() {
		return rpmSupplier.getAsDouble();
	}

	public double getBigWheelSetpoint() {
		return bigFlywheel.getSetpoint();
	}
	
	public double getLittleWheelSetpoint() {
		return littleFlywheel.getSetpoint();
	}
}
