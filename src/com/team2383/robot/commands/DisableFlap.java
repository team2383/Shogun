package com.team2383.robot.commands;

import static com.team2383.robot.HAL.gearFlap;

import com.team2383.robot.subsystems.GearFlap.State;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DisableFlap extends Command {
	private boolean isFinished = false;

    public DisableFlap() {
        super("Disable Flap");
        requires(gearFlap);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	gearFlap.setState(State.RETRACTED);
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
