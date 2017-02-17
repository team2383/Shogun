package com.team2383.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team2383.ninjaLib.SetState.StatefulSubsystem;
import com.team2383.robot.Constants;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Feeder extends com.team2383.ninjaLib.SetState.StatefulSubsystem<Feeder.State> {
	private final CANTalon feederLeft = new CANTalon(Constants.kFeederLeftTalonID);
	private final CANTalon feederRight = new CANTalon(Constants.kFeederRightTalonID);
	private State state = State.STOPPED;
	
	public Feeder() {
		feederRight.changeControlMode(TalonControlMode.Follower);
		feederRight.set(feederLeft.getDeviceID());
	}

	public enum State {
		FEEDING, OUTFEEDING, STOPPED, OUTFEEDINGSLOW
	}

	public void feedIn() {
		feederLeft.set(1);
	}

	public void feedOut() {
		feederLeft.set(-1);
	}

	public void feedOutSlow() {
		feederLeft.set(-0.6);
	}

	public void stop() {
		feederLeft.set(0);
	}

	@Override
	public void setState(State state) {
		switch (state) {
			case FEEDING:
				feedIn();
				break;
	
			case OUTFEEDING:
				feedOut();
				break;
	
			default:
			case STOPPED:
				stop();
				break;
		}
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

}
