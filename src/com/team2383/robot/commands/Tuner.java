package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooter;

import java.util.function.DoubleSupplier;

import com.team2383.robot.Constants.Preset;
import com.team2383.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Tuner {
	private final double tickIncrement;
	private final double tickLength;
	private final double minHoldTime;
	
	private double value;
	
	private double lastTick = 0;
	private double holdTime;
	
	private class TunerCommand extends Command {
		private final double tickIncrement;
		
		public TunerCommand(double tickIncrement) {
			this.tickIncrement = tickIncrement;
		}
		
		@Override
		protected void initialize() {
			lastTick = 0;
			holdTime = 0;
			value += this.tickIncrement;
		}

		@Override
		protected void execute() {
			holdTime += this.timeSinceInitialized() - lastTick;
			if(holdTime >= minHoldTime) {
				holdTime = 0;
				value += tickIncrement;
			}
			
			
			lastTick = this.timeSinceInitialized();
			
		}

		@Override
		protected boolean isFinished() {
			return false;
		}

		@Override
		protected void end() {
		}

		@Override
		protected void interrupted() {
			end();
		}
	}
	
	/**
	 * Creates a new tuner
	 * @param tickIncrement how many RPM to increase by in one tick (a negative value will be made postive)
	 * @param tickLength how long a tick is in seconds (holding to adjust)
	 * @param holdTime how long to hold before constant adjustment
	 */
	public Tuner(double tickIncrement, double tickLength, double minHoldTime) {
		this.tickIncrement = Math.abs(tickIncrement);
		this.tickLength = tickLength;
		this.minHoldTime = minHoldTime;
	}
	
	public Command getIncrementCommand() {
		return new TunerCommand(tickIncrement);
	}
	
	public Command getDecrementCommand() {
		return new TunerCommand(-tickIncrement);
	}
	
	public double getValue() {
		return value;
	}
}
