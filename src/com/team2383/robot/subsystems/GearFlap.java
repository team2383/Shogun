package com.team2383.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team2383.ninjaLib.SetState.StatefulSubsystem;
import com.team2383.robot.Constants;
import com.team2383.robot.subsystems.Drivetrain.Gear;
import com.team2383.robot.subsystems.GearDoor.State;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class GearFlap extends com.team2383.ninjaLib.SetState.StatefulSubsystem<GearFlap.State> {
	private DoubleSolenoid gearFlap = new DoubleSolenoid(Constants.kGearFlapForward, Constants.kGearFlapBackward);
	private State state = State.RETRACTED;
	
	public GearFlap() {
		setState(State.RETRACTED);
	}

	public enum State {
		RETRACTED, EXTENDED
	}

	@Override
	public void setState(State state) {
		switch (state) {
			case EXTENDED:
				gearFlap.set(Value.kForward);
				break;

			default:
			case RETRACTED:
				gearFlap.set(Value.kReverse);
				break;
		}
	}
	
	public boolean isClosed(){
		if(gearFlap.get() == Value.kForward){
			return false;
		}
		else if( gearFlap.get() == Value.kReverse){
			return true;
		}
		else
			return false;
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

}
