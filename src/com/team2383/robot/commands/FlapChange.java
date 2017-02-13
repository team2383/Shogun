package com.team2383.robot.commands;

import com.team2383.robot.subsystems.Feeder.FlapState;

import edu.wpi.first.wpilibj.command.Command;
import static com.team2383.robot.HAL.feeder;

/**
 *
 */
public class FlapChange extends Command {
	
    public FlapChange() {
    	super("FlapChange");
    }

    @Override
    protected void initialize() {
    	feeder.flapChange();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	feeder.flapChange();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
