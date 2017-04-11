package com.team2383.robot;

import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;


import com.team2383.ninjaLib.DPadButton;
import com.team2383.ninjaLib.DPadButton.Direction;
import com.team2383.ninjaLib.Gamepad;
import com.team2383.ninjaLib.LambdaButton;
import com.team2383.ninjaLib.OnChangeButton;
import com.team2383.ninjaLib.SetState;
import com.team2383.ninjaLib.Values;
import com.team2383.ninjaLib.WPILambdas;
import com.team2383.robot.subsystems.Feeder;
import com.team2383.robot.subsystems.GearDoor;
import com.team2383.robot.subsystems.GearFlap;
import com.team2383.robot.Constants.Preset;
import com.team2383.robot.commands.AutoDriveStraight;
import com.team2383.robot.commands.DumbSpool;
import com.team2383.robot.commands.Tuner;
import com.team2383.robot.commands.MoveTurret;
import com.team2383.robot.commands.PrecisionDrive;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;
import com.team2383.robot.commands.TeleopDriveStraight;
import com.team2383.robot.commands.UsePreset;
import com.team2383.robot.subsystems.Agitator;
import com.team2383.robot.subsystems.Climber;
import com.team2383.ninjaLib.SetState;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

import static com.team2383.robot.HAL.shooter;
import static com.team2383.robot.HAL.feeder;
import static com.team2383.robot.HAL.agitator;
import static com.team2383.robot.HAL.gearDoor;
import static com.team2383.robot.HAL.gearFlap;
import static com.team2383.robot.HAL.climber;



/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

/*
 * OI Controls:
 * 
 *  Driver->
 * 		Arcade drive, left stick throttle, right stick turn
 * 		Shooting ->
 * 			Vision: LT aim, RT shoot
 * 		DriveStraight -> right shoulder
 * 		Precision Drive -> left shoulder
 * 		Shift -> push down on sticks, left low, right high
 *	
 *	Operator stick 1 ->
 *		Hopper controls: button 8 toggle on/off, button 10 unjam
 *		Feeder controls: button 3 in, 4 out
 *		Climber controls: button 6
 *		Gear Door controls: button 5
 *		Gear Flap controls: button 12
 *
 *	Operator stick 2 (tuning stick)
 *		Turret: Twist stick
 *		Distance Presets: buttons 8,10,11 (left row of side buttons) close to far
 *		Flywheel controls: Thumb spool (2), trigger shoot (1)
 *		Tuning Controls: buttons 7,9 (tune up/down)
 *
 *
 */
@SuppressWarnings("unused")
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.

	/* Sticks functions */

	private static DoubleUnaryOperator inputExpo = (x) -> {
		return Constants.inputExpo * Math.pow(x, 3) + (1 - Constants.inputExpo) * x;
	};

	private static DoubleUnaryOperator deadband = (x) -> {
		return Math.abs(x) > Constants.inputDeadband ? x : 0;
	};
	
	//Driver
	public static Gamepad driver = new Gamepad(0);

	public static Button shiftDown = driver.getLeftStickClick();
	public static Button shiftUp = driver.getRightStickClick();
	
	public static Button driveStraight = driver.getRightShoulder();
	public static Button backUp = driver.getButtonB();
	
	public static Button visionAim = driver.getLeftTriggerClick();
	public static Button visionShoot = driver.getRightTriggerClick();
	
	public static Button toggleAutoShift = driver.getButtonA();
	

	public static DoubleSupplier throttle = () -> deadband.applyAsDouble(driver.getLeftY());
	public static DoubleSupplier turn = () -> deadband.applyAsDouble(driver.getRightX());
	
	public static Button precisionDrive = driver.getLeftShoulder();
	
	
	//Operator
	public static Joystick operator = new Joystick(2);
	
	
	public static Button feedIn = new JoystickButton(operator, 3);
	public static Button feedOut = new JoystickButton(operator, 4);
	
	public static Button agitatorOn = new JoystickButton(operator, 8);
	public static Button agitatorUnjam = new JoystickButton(operator, 10);
	
	public static Button climb = new JoystickButton(operator, 6);
	public static Button actuateGearDoor = new JoystickButton(operator, 5);
	public static Button actuateGearFlap = new JoystickButton(operator, 12);
	
	
	
	//Tuner
	public static Joystick tuner = new Joystick(3);
	public static DoubleSupplier turretStick = () -> deadband.applyAsDouble(tuner.getTwist());
	
	public static Button bigFlywheelIncrement = new JoystickButton(tuner, 7);
	public static Button bigFlywheelDecrement = new JoystickButton(tuner, 9);
	
	public static Button shoot = new JoystickButton(tuner, 1);
	public static Button spool = new JoystickButton(tuner, 2);

	
	
	public static Button moveTurret = new JoystickButton(tuner,3);
	
	public static Button presetClose = new JoystickButton(tuner, 8);
	public static Button presetMid = new JoystickButton(tuner, 10);
	public static Button presetFar = new JoystickButton(tuner, 11);
	
	
	
	
	public static Button enableTuning = WPILambdas.createButton(() -> {
		boolean result = false;
		for(int i = 7; i <= 10; i++) {
			if (tuner.getRawButton(i)) result = true;
		}
		return result;
	});
	
	
	
	
	public OI() {
		
		precisionDrive.whileHeld(new PrecisionDrive(throttle, turn, toggleAutoShift::get, shiftDown::get, shiftUp::get));
		driveStraight.whileHeld(new TeleopDriveStraight(throttle));
		backUp.whenPressed((new AutoDriveStraight(() -> 0.5 , 0.25)));
		
		
		
		feedIn.whileHeld(new SetState<Feeder.State>(feeder, Feeder.State.FEEDING, Feeder.State.STOPPED));
		feedOut.whileHeld(new SetState<Feeder.State>(feeder, Feeder.State.OUTFEEDING, Feeder.State.STOPPED));
		
		//TODO: add sensor to agitator so operator can just toggle on/off when necessary and agitator handles rest.
		agitatorOn.whileHeld(new SetState<Agitator.State>(agitator, Agitator.State.FEEDING, Agitator.State.STOPPED));
		agitatorUnjam.whileHeld(new SetState<Agitator.State>(agitator, Agitator.State.UNJAM, Agitator.State.STOPPED));
		
		climb.whileHeld(new SetState<Climber.State>(climber, Climber.State.CLIMB, Climber.State.STOPPED));
		
		moveTurret.whileHeld(new MoveTurret(OI.turretStick));
		
		
		actuateGearDoor.toggleWhenActive(new SetState<GearDoor.State>(gearDoor, GearDoor.State.EXTENDED, GearDoor.State.RETRACTED));
		actuateGearFlap.toggleWhenActive(new SetState<GearFlap.State>(gearFlap, GearFlap.State.EXTENDED, GearFlap.State.RETRACTED));
		
		spool.whileHeld(new Spool());
		shoot.whileHeld(new Shoot());
		
		Tuner bigFlywheelTuner = new Tuner(shooter.getBigWheelRPMSetpoint(), 10, 0.02, 0.5);
		Command enableFlywheelTuning = WPILambdas.runOnceCommand(() -> {
			shooter.setBigFlywheelRPMSupplier(bigFlywheelTuner::getValue);
		}, false);
		
		bigFlywheelIncrement.whileHeld(bigFlywheelTuner.getIncrementCommand());
		bigFlywheelDecrement.whileHeld(bigFlywheelTuner.getDecrementCommand());
		enableTuning.whenPressed(enableFlywheelTuning);
		
		
		presetClose.whileHeld(new UsePreset(Preset.close));
		presetMid.whileHeld(new UsePreset(Preset.mid));
		presetFar.whileHeld(new UsePreset(Preset.far));
	}
}
