package com.team2383.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team2383.ninjaLib.SetState.StatefulSubsystem;
import com.team2383.robot.Constants;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Feeder extends com.team2383.ninjaLib.SetState.StatefulSubsystem<Feeder.State> {
	private final CANTalon feederLeft;
	private final CANTalon feederRight;
	private final DoubleSolenoid GearManipulator;
	private State state;
	
	public Feeder() {
		feederLeft = new CANTalon(Constants.kFeederLeftTalonID);
		feederRight = new CANTalon(Constants.kFeederRightTalonID);
		feederRight.changeControlMode(TalonControlMode.Follower);
		feederRight.set(feederLeft.getDeviceID());
		GearManipulator = new DoubleSolenoid(Constants.kGearManipulatorForward, Constants.kGearManipulatorBackward);
		
		this.state = State.STOPPED;
	}
	
	public enum FlapState {
		CLOSED, OPEN;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	public enum State {
		FEEDING, OUTFEEDING, STOPPED, OUTFEEDINGSLOW
	}

	public void feedIn() {
		System.out.println("Feeding!");
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
	
	public void flapChange(){
		if(getFlap() == FlapState.OPEN){
			flapTo(FlapState.CLOSED);
		}
		else{flapTo(FlapState.OPEN);}
	}
	
	public void flapTo(FlapState flapState){
		switch (flapState) {
		default:
		case OPEN:
			GearManipulator.set(Value.kForward);
			break;
		case CLOSED:
			GearManipulator.set(Value.kReverse);
			break;
		}
	}
	
	public FlapState getFlap() {
		switch (GearManipulator.get()) {
		case kForward:
			return FlapState.OPEN;
		default:
		case kReverse:
			return FlapState.CLOSED;
		}
	}
	
	

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

}
