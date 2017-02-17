package com.team2383.robot.subsystems;

import com.ctre.CANTalon;
import com.team2383.ninjaLib.SetState.StatefulSubsystem;
import com.team2383.robot.Constants;

/*
 * This subsystem includes the conveyor streaming balls into the shooter
 */


public class Conveyor extends com.team2383.ninjaLib.SetState.StatefulSubsystem<Conveyor.State> {

	private CANTalon conveyor = new CANTalon(Constants.kConveyorTalonID);
	private State state = State.STOPPED;
	
	public Conveyor(){}
	
	public enum State {
		FEEDING, UNJAM, STOPPED
	}
	
	public void feed() {
		if(true /*if conveyor sensor says we should agitate*/) {
			conveyor.set(1);
		}
	}

	public void unjam() {
		conveyor.set(-0.8);
	}

	public void stop() {
		conveyor.set(0);
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
