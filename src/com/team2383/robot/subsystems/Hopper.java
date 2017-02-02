package com.team2383.robot.subsystems;

import com.ctre.CANTalon;
import com.team2383.ninjaLib.SetState.StatefulSubsystem;
import com.team2383.robot.Constants;

/*
 * This subsystem includes the agitator streaming balls into the hopper, and
 *  the internal "hopper" feeder that propels the stream into the shooter
 */


public class Hopper extends StatefulSubsystem<Hopper.State> {

	private CANTalon hopper;
	private CANTalon agitator;
	
	public Hopper(){
		hopper = new CANTalon(Constants.kHopperTalonID);
		agitator = new CANTalon(Constants.kAgitatorTalonID);
	}
	
	public enum State{
		FEEDING, OUTFEEDING, STOPPED, OUTFEEDINGSLOW
	}
	
	public void feedIn() {
		System.out.println("Agitating!");
		hopper.set(1);
		agitator.set(1);
	}

	public void feedOut() {
		hopper.set(-1);
		agitator.set(-1);
	}

	public void feedOutSlow() {
		hopper.set(-0.6);
		agitator.set(-0.6);
	}

	public void stop() {
		hopper.set(0);
		agitator.set(0);
	}

	@Override
	public void setState(State state) {
		switch(state){
		case FEEDING:
			feedIn();
			break;
		case OUTFEEDING:
			feedOut();
			break;
		case STOPPED:
			stop();
			break;
		case OUTFEEDINGSLOW:
			feedOutSlow();
			break;
		}	
	}

	@Override
	protected void initDefaultCommand() {		
	}
}
