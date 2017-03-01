package com.team2383.auto;

import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootAtWall extends CommandGroup {

    public ShootAtWall() {
        addSequential(new Spool(3750));
        Timer.delay(2.0);
        addParallel(new Shoot(10));
    }
}
