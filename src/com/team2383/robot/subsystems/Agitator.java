package com.team2383.robot.subsystems;

import com.ctre.CANTalon;
import com.team2383.ninjaLib.SetState.StatefulSubsystem;
import com.team2383.robot.Constants;

/*
 * This subsystem includes the agitator streaming balls into the conveyor
 */

//3320 


public class Agitator extends com.team2383.ninjaLib.SetState.StatefulSubsystem<Agitator.State> {

	private CANTalon agitator = new CANTalon(Constants.kAgitatorTalonID);
	private State state = State.STOPPED;
	
	public Agitator(){
	}
	
	public enum State {
		FEEDING, UNJAM, STOPPED
	}
	
	public void feed() {
		if(true /*if agitator sensor says we should agitate*/) {
			agitator.set(0.6);
		}
	}

	public void unjam() {
		agitator.set(-0.8);
	}

	public void stop() {
		agitator.set(0);
	}

	@Override
	public void setState(State state) {
		switch (state) {
			case FEEDING:
				feed();
				break;
	
			case UNJAM:
				unjam();
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
