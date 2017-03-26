package com.team2383.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team2383.ninjaLib.SetState.StatefulSubsystem;
import com.team2383.robot.Constants;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class GearDoor extends com.team2383.ninjaLib.SetState.StatefulSubsystem<GearDoor.State> {
	private DoubleSolenoid gearDoor = new DoubleSolenoid(Constants.kGearDoorForward, Constants.kGearDoorBackward);
	private State state = State.RETRACTED;
	
	public GearDoor() {
		setState(State.RETRACTED);
	}

	public enum State {
		RETRACTED, EXTENDED
	}

	@Override
	public void setState(State state) {
		switch (state) {
			case EXTENDED:
				gearDoor.set(Value.kForward);
				break;

			default:
			case RETRACTED:
				gearDoor.set(Value.kReverse);
				break;
		}
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
}
