package com.team2383.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static com.team2383.robot.HAL.shooter;
import static com.team2383.robot.HAL.conveyor;
import static com.team2383.robot.HAL.agitator;


/**
 *
 */
public class EndShoot extends Command {

    public EndShoot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	conveyor.stop();
    	agitator.stop();
    	shooter.stop();
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
    	shooter.stop();
    	conveyor.stop();
    	agitator.stop();
    }
}
