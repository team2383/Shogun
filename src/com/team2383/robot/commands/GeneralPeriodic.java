package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GeneralPeriodic extends Command {

	// Called just before this Command runs the first time
    protected void initialize() {
		//SmartDashboard.putData("little flywheel", shooter.littleFlywheel);
		//SmartDashboard.putData("big flywheel", shooter.bigFlywheel);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("BigWheelSpeed", shooter.getBigWheelRPM());
    	SmartDashboard.putNumber("LittleWheelSpeed", shooter.getLittleWheelRPM());
    	SmartDashboard.putNumber("BigWheelSetpoint setpoint", shooter.getBigWheelRPMSetpoint());
    	SmartDashboard.putNumber("LittleWheelSpeed setpoint", shooter.getLittleWheelRPMSetpoint());
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
