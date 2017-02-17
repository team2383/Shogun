package com.team2383.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team2383.ninjaLib.SetState.StatefulSubsystem;
import com.team2383.robot.Constants;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Flap extends com.team2383.ninjaLib.SetState.StatefulSubsystem<Flap.State> {
	private DoubleSolenoid flap = new DoubleSolenoid(Constants.kFlapForward, Constants.kFlapBackward);
	private State state = State.RETRACTED;
	
	public Flap() {
		setState(State.RETRACTED);
	}

	public enum State {
		RETRACTED, EXTENDED
	}

	@Override
	public void setState(State state) {
		switch (state) {
			case EXTENDED:
				flap.set(Value.kForward);
				break;

			default:
			case RETRACTED:
				flap.set(Value.kReverse);
				break;
		}
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
}
