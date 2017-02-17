package com.team2383.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.team2383.robot.Constants;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {
	private CANTalon littleFlywheel = new CANTalon(Constants.kLittleFlywheelTalonID);
	private CANTalon bigFlywheel = new CANTalon(Constants.kBigFlywheelTalonID);

	private DoubleSupplier littleFlywheelRPMSupplier = () -> 0;
	private DoubleSupplier bigFlywheelRPMSupplier = () -> 0;
	
	public Shooter() {
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
	 * continually updates flywheels to follow setpoint
	 * @param rpm
	 */
	public void spool() {
		bigFlywheel.changeControlMode(TalonControlMode.Speed);
		bigFlywheel.enable();
		bigFlywheel.setSetpoint(bigFlywheelRPMSupplier.getAsDouble());
		bigFlywheel.enableBrakeMode(false);
		
		littleFlywheel.changeControlMode(TalonControlMode.Speed);
		littleFlywheel.enable();
		littleFlywheel.setSetpoint(littleFlywheelRPMSupplier.getAsDouble());
		littleFlywheel.enableBrakeMode(false);
	}
	
	/**
	 * stops both flywheels and sets turret to hold position.
	 */
	public void stop() {	
		bigFlywheel.enableBrakeMode(true);
		bigFlywheel.disable();
	
		littleFlywheel.enableBrakeMode(true);
		littleFlywheel.disable();
	}
	
	public void dumbSpool(){
		littleFlywheel.changeControlMode(TalonControlMode.PercentVbus);
		bigFlywheel.changeControlMode(TalonControlMode.PercentVbus);
		bigFlywheel.enableBrakeMode(false);
		littleFlywheel.enableBrakeMode(false);
		littleFlywheel.set(-0.7);
		bigFlywheel.set(-0.9);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	//flywheel methods
	public boolean areFlywheelsAtSetpoint() {
		return isBigWheelAtSetpoint() && isLittleWheelAtSetpoint();
	}
	
	public boolean isBigWheelAtSetpoint() {
		return Math.abs(getBigWheelRPM() - bigFlywheel.getSetpoint()) <= Constants.kFlywheelRPMTolerance;
	}
	
	public boolean isLittleWheelAtSetpoint() {
		return Math.abs(getLittleWheelRPM() - littleFlywheel.getSetpoint()) <= Constants.kFlywheelRPMTolerance;
	}
	
	public void setBigFlywheelRPMSupplier(DoubleSupplier rpmSupplier) {
		bigFlywheelRPMSupplier = rpmSupplier;
	}
	
	public void setLittleFlywheelRPMSupplier(DoubleSupplier rpmSupplier) {
		littleFlywheelRPMSupplier = rpmSupplier;
	}
	
	public double getBigWheelRPM() {
		return bigFlywheel.getSpeed();
	}
	
	public double getBigWheelRPMSetpoint() {
		return bigFlywheel.getSetpoint();
	}
	
	public double getLittleWheelRPM() {
		return littleFlywheel.getSpeed();
	}

	public double getLittleWheelRPMSetpoint() {
		return littleFlywheel.getSetpoint();
	}
}
