package com.team2383.auto;

import com.team2383.robot.commands.EnableFlap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FlapOpen extends CommandGroup {

    public FlapOpen() {
       addSequential(new EnableFlap());
    }
}
