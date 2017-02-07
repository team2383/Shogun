package com.team2383.ninjaLib;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;


public class StateCommand {
	public static abstract class StatefulSubsystem<StateT> extends Subsystem {
		public abstract void setState(StateT state);
		public abstract void execute();
		public abstract StateT getState();
		
		@Override
		protected void initDefaultCommand() {
			setDefaultCommand(new StateRunner<StateT>(this));
		}
		
		private static class StateRunner<StateT> extends Command {
			StatefulSubsystem<StateT> subsystem;
			
			public StateRunner(StatefulSubsystem<StateT> subsystem) {
				requires(subsystem);
				this.subsystem = subsystem;
			}
			
			@Override
			protected void initialize() {
			}

			@Override
			protected void execute() {
				subsystem.execute();
			}

			@Override
			protected boolean isFinished() {
				return false;
			}
		}
	}
	
	/* StateCommands */
	
	public static class Set<StateT extends Enum<StateT>> extends Command {
		private final StatefulSubsystem<StateT> subsystem;
		private final StateT state;
		private final StateT endState;
		private boolean endInstantly = false;
		
		/**
		 * Sets state, ends instantly.
		 * 
		 * @param state state to set to
		 */
		public Set(StatefulSubsystem<StateT> subsystem, StateT state) {
			this(subsystem, state, state);
			this.endInstantly = true;
		}
		
		/**
		 * Sets state for lifecycle of command, then goes to endState
		 * @param subsystem
		 * @param state
		 * @param endState
		 */
		public Set(StatefulSubsystem<StateT> subsystem, StateT state, StateT endState) {
			super("SetState Command");
			this.subsystem = subsystem;
			this.state = state;
			this.endState = endState;
		}
		
		/**
		 * Sets state for lifecycle of command, then goes to endState
		 * @param subsystem
		 * @param state
		 * @param endState
		 */
		public Set(StatefulSubsystem<StateT> subsystem, StateT state, StateT endState, double timeout) {
			super("SetState Command", timeout);
			this.subsystem = subsystem;
			this.state = state;
			this.endState = endState;
		}
		
		@Override
		protected void initialize() {
		}

		@Override
		protected void execute() {
			subsystem.setState(state);
		}

		@Override
		protected void interrupted() {
			subsystem.setState(endState);
		}
		
		@Override
		protected void end() {
			subsystem.setState(endState);
		}
		
		@Override
		protected boolean isFinished() {
			return endInstantly || this.isTimedOut();
		}
	}
	
	/**
	 * Toggle between state1 and state2, ends immediately
	 * 
	 * @param state1 initial state
	 * @param state2 next state
	 */
	public static class Toggle<StateT extends Enum<StateT>> extends Command {
		private final StatefulSubsystem<StateT> subsystem;
		private final StateT state1;
		private final StateT state2;

		public Toggle(StatefulSubsystem<StateT> subsystem, StateT state1, StateT state2) {
			this.subsystem = subsystem;
			this.state1 = state1;
			this.state2 = state2;
		}
		
		@Override
		protected void initialize() {
		}

		@Override
		protected void execute() {
			if (subsystem.getState() == state1) {
				subsystem.setState(state2);
			} else {
				subsystem.setState(state1);
			}
		}

		@Override
		protected boolean isFinished() {
			return true;
		}
	}
	
	
}
