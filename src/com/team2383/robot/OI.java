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
import com.team2383.robot.subsystems.Flap;
import com.team2383.robot.Constants.Preset;
import com.team2383.robot.commands.DumbSpool;
import com.team2383.robot.commands.MoveTurret;
import com.team2383.robot.commands.UsePreset;
import com.team2383.robot.subsystems.Agitator;
import com.team2383.ninjaLib.SetState;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


import static com.team2383.robot.HAL.feeder;
import static com.team2383.robot.HAL.agitator;
import static com.team2383.robot.HAL.flap;



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
 * 		DriveStraight -> left or right shoulder
 * 		Shift -> push down on sticks, left low, right high
 *	
 *	Operator stick 1 ->
 *		Turret: Twist stick
 *		Distance Presets: buttons 7,9,11 (left row of side buttons) close to far
 *		Hopper controls: button 8 toggle on/off, button 10 unjam
 *		Feeder controls: button 3 in, 4 out
 *		Flap controls: depending on where we feed from button 12
 *		Flywheel controls: Thumb spool (2), trigger shoot (1)
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
	
	public static Button driveStraight = WPILambdas.createButton(() -> {
		return driver.getRawButton(Gamepad.BUTTON_SHOULDER_LEFT) || driver.getRawButton(Gamepad.BUTTON_SHOULDER_RIGHT);
	});
	
	public static Button visionAim = driver.getLeftTriggerClick();
	public static Button visionShoot = driver.getRightTriggerClick();
	
	public static Button toggleAutoShift = driver.getButtonA();

	public static DoubleSupplier throttle = () -> deadband.applyAsDouble(driver.getLeftY());
	public static DoubleSupplier turn = () -> deadband.applyAsDouble(driver.getRightX());
	
	
	//Operator
	public static Joystick operator = new Joystick(2);
	
	public static DoubleSupplier turretStick = () -> deadband.applyAsDouble(operator.getTwist());
	
	public static Button manualSpool = new JoystickButton(operator, 2);
	
	public static Button changeFlap = new JoystickButton(operator, 12);

	
	/*
	 * Preset buttons will replace this comment
	 */
	public static Button shoot = new JoystickButton(operator, 1);
	public static Button spool = new JoystickButton(operator, 2);

	public static Button feedIn = new JoystickButton(operator, 3);
	public static Button feedOut = new JoystickButton(operator, 4);
	
	public static Button agitatorOn = new JoystickButton(operator, 8);
	public static Button agitatorUnjam = new JoystickButton(operator, 10);
	
	public static Button moveTurret = new OnChangeButton(OI.turretStick, 0.08);
	
	public static Button presetClose = new JoystickButton(operator, 7);
	public static Button presetMid = new JoystickButton(operator, 9);
	public static Button presetFar = new JoystickButton(operator, 11);
	
	// Vision Operator
	public static Joystick visionStick = new Joystick(3);
	
	public OI() {
		
		feedIn.whileHeld(new SetState<Feeder.State>(feeder, Feeder.State.FEEDING, Feeder.State.STOPPED));
		feedOut.whileHeld(new SetState<Feeder.State>(feeder, Feeder.State.OUTFEEDING, Feeder.State.STOPPED));
		
		//TODO: add sensor to agitator so operator can just toggle on/off when necessary and agitator handles rest.
		agitatorOn.whileHeld(new SetState<Agitator.State>(agitator, Agitator.State.FEEDING, Agitator.State.STOPPED));
		agitatorUnjam.whileHeld(new SetState<Agitator.State>(agitator, Agitator.State.UNJAM, Agitator.State.STOPPED));
		
		moveTurret.whileHeld(new MoveTurret(OI.turretStick));
		
		changeFlap.whileHeld(new SetState<Flap.State>(flap, Flap.State.EXTENDED, Flap.State.RETRACTED));
		
		manualSpool.whileHeld(new DumbSpool());
		

		presetClose.whenPressed(new UsePreset(Preset.close));
		presetMid.whenPressed(new UsePreset(Preset.mid));
		presetFar.whenPressed(new UsePreset(Preset.far));
	}
}
