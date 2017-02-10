package com.team2383.robot.subsystems;

import com.ctre.CANTalon;
import com.team2383.ninjaLib.SetState.StatefulSubsystem;
import com.team2383.robot.Constants;

/*
 * This subsystem includes the agitator streaming balls into the shooter
 */


public class Agitator extends com.team2383.ninjaLib.SetState.StatefulSubsystem<Agitator.State> {

	private CANTalon agitator;
	private CANTalon stream;
	private State state;
	
	public Agitator(){
		agitator = new CANTalon(Constants.kAgitatorTalonID);
		
		this.state = State.STOPPED;
	}
	
	public enum State{
		FEEDING, UNJAM, STOPPED
	}
	
	public void feed() {
		if(true /*if agitator sensor says we should agitate*/) {
			agitator.set(1);
			stream.set(1);
		}
	}

	public void unjam() {
		agitator.set(-0.8);
	}

	public void stop() {
		agitator.set(0);
		stream.set(0);
	}

	@Override
	public void setState(State state) {
		switch (this.state) {
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
