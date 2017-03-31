package com.team2383.robot.commands;

import static com.team2383.robot.HAL.gearFlap;

import com.team2383.robot.subsystems.GearDoor.State;
import static com.team2383.robot.HAL.gearDoor;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class EnableDoor extends Command {
	private boolean isFinished = false;

    public EnableDoor() {
        super("Enable Door");
        requires(gearDoor);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	gearDoor.setState(State.EXTENDED);
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

