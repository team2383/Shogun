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
	
	private double value = 0;
	
	private double lastTick = 0;
	private double totalHoldTime = 0;
	private double tickTime = 0;
	
	private class TunerCommand extends Command {
		private final double tickIncrement;
		
		public TunerCommand(double tickIncrement) {
			this.tickIncrement = tickIncrement;
		}
		
		@Override
		protected void initialize() {
			lastTick = 0;
			totalHoldTime = 0;
			value += this.tickIncrement;
		}

		@Override
		protected void execute() {
			value = shooter.getBigWheelRPMSetpoint();
			tickTime += this.timeSinceInitialized() - lastTick;
			totalHoldTime += tickTime;
			if(totalHoldTime >= minHoldTime && tickTime >= tickLength) {
				tickTime = 0;
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
	public Tuner(double value, double tickIncrement, double tickLength, double minHoldTime) {
		this.tickIncrement = Math.abs(tickIncrement);
		this.tickLength = tickLength;
		this.minHoldTime = minHoldTime;
		this.value = value;
	}
	
	/**
	 * Creates a new tuner
	 * @param tickIncrement how many RPM to increase by in one tick (a negative value will be made postive)
	 * @param tickLength how long a tick is in seconds (holding to adjust)
	 * @param holdTime how long to hold before constant adjustment
	 */
	public Tuner(double tickIncrement, double tickLength, double minHoldTime) {
		this(0, tickIncrement, tickLength, minHoldTime);
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
