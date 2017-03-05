package com.team2383.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.team2383.robot.Constants;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {
	public CANTalon bigFlywheel = new CANTalon(Constants.kBigFlywheelTalonID);

	private DoubleSupplier bigFlywheelRPMSupplier = () -> 0;
	
	public Shooter() {
		
		bigFlywheel.enableBrakeMode(false);
		bigFlywheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		bigFlywheel.changeControlMode(TalonControlMode.Speed);
		bigFlywheel.reverseOutput(false);
		bigFlywheel.reverseSensor(false);
		bigFlywheel.configPeakOutputVoltage(12.0, -3.0);
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
	}
	
	/**
	 * stops both flywheels and sets turret to hold position.
	 */
	public void stop() {	
		bigFlywheel.enableBrakeMode(true);
		bigFlywheel.disable();
	
	}
	
	public void dumbSpool(){
		bigFlywheel.changeControlMode(TalonControlMode.PercentVbus);
		bigFlywheel.enableBrakeMode(false);
		bigFlywheel.set(-0.9);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	//flywheel methods
	public boolean areFlywheelsAtSetpoint() {
		return isBigWheelAtSetpoint();
	}
	
	public boolean isBigWheelAtSetpoint() {
		return Math.abs(getBigWheelRPM() - bigFlywheel.getSetpoint()) <= Constants.kFlywheelRPMTolerance;
	}
	
	
	public void setBigFlywheelRPMSupplier(DoubleSupplier rpmSupplier) {
		bigFlywheelRPMSupplier = rpmSupplier;
	}
	
	
	public double getBigWheelRPM() {
		return bigFlywheel.getSpeed();
	}
	
	public double getBigWheelRPMSetpoint() {
		return bigFlywheelRPMSupplier.getAsDouble();
	}
}
