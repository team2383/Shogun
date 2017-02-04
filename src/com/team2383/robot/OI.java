package com.team2383.robot;

import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;

import com.team2383.ninjaLib.DPadButton;
import com.team2383.ninjaLib.DPadButton.Direction;
import com.team2383.ninjaLib.Gamepad;
import com.team2383.ninjaLib.LambdaButton;
import com.team2383.ninjaLib.Values;
import com.team2383.ninjaLib.SetState;

import com.team2383.robot.subsystems.Drivetrain.Gear;
import com.team2383.robot.subsystems.Feeder;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.

	/* Sticks */

	private static DoubleUnaryOperator inputExpo = (x) -> {
		return Constants.inputExpo * Math.pow(x, 3) + (1 - Constants.inputExpo) * x;
	};

	private static DoubleUnaryOperator deadband = (x) -> {
		return Math.abs(x) > Constants.inputDeadband ? x : 0;
	};

	public static Gamepad gamepad = new Gamepad(0);

	public static Button shiftDown = gamepad.getLeftShoulder();
	public static Button shiftUp = gamepad.getRightShoulder();
	public static Button toggleAutoShift = gamepad.getButtonA();

	public static DoubleSupplier leftStick = () -> deadband.applyAsDouble(gamepad.getLeftY());
	public static DoubleSupplier rightStick = () -> deadband.applyAsDouble(gamepad.getRightX());

	public static Joystick operator = new Joystick(2);

	public static Button feedIn = new JoystickButton(operator, 8);

	public OI() {
		//shiftDown.whileHeld(new ShiftTo(Gear.LOW));
		//shiftUp.whileHeld(new ShiftTo(Gear.HIGH));

		feedIn.whileHeld(new SetState<Feeder.State>(feeder, Feeder.State.FEEDING, Feeder.State.STOPPED));
	}
}
