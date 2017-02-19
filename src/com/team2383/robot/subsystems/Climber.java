package com.team2383.robot.subsystems;

import com.ctre.CANTalon;
import com.team2383.ninjaLib.SetState.StatefulSubsystem;
import com.team2383.robot.Constants;

/*
 * This subsystem includes the conveyor streaming balls into the shooter
 */


public class Climber extends com.team2383.ninjaLib.SetState.StatefulSubsystem<Climber.State> {

	private CANTalon climber = new CANTalon(Constants.kClimberTalonID);
	private State state = State.STOPPED;
	
	public Climber(){
		climber.reverseOutput(true);
	}
	
	public enum State {
		CLIMB, STOPPED
	}
	
	public void climb(){
		climber.enableBrakeMode(false);
			climber.set(1);
	}
	public void stop() {
		climber.enableBrakeMode(true);
		climber.set(0);
	}

	@Override
	public void setState(State state) {
		switch (state) {
			case CLIMB:
				climb();
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
