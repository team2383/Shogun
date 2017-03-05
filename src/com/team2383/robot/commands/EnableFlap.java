package com.team2383.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import static com.team2383.robot.HAL.flap;

import com.team2383.robot.subsystems.Flap.State;

/**
 *
 */
public class EnableFlap extends Command {
	private boolean isFinished = false;

    public EnableFlap() {
        super("Enable Flap");
        requires(flap);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	flap.setState(State.EXTENDED);
    	end();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	isFinished = true;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
