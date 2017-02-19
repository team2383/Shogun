package com.team2383.robot.commands;

import static com.team2383.robot.HAL.turret;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HoldTurret extends Command {

    public HoldTurret() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(turret);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	turret.holdPosition();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
