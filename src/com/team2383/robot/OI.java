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
import static com.team2383.robot.HAL.flap;
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
 *		Turret: Twist stick
 *		Distance Presets: buttons 7,9,11 (left row of side buttons) close to far
 *		Hopper controls: button 8 toggle on/off, button 10 unjam
 *		Feeder controls: button 3 in, 4 out
 *		Flap controls: depending on where we feed from button 12
 *		Flywheel controls: Thumb spool (2), trigger shoot (1)
 *		Climber controls: button 6
 *
 *	Operator stick 2 (tuning stick)
 *		button 
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
	
	public static Button visionAim = driver.getLeftTriggerClick();
	public static Button visionShoot = driver.getRightTriggerClick();
	
	public static Button toggleAutoShift = driver.getButtonA();
	
	public static Button gearBackward = driver.getButtonB();

	public static DoubleSupplier throttle = () -> deadband.applyAsDouble(driver.getLeftY());
	public static DoubleSupplier turn = () -> deadband.applyAsDouble(driver.getRightX());
	
	public static Button precisionDrive = driver.getLeftShoulder();
	
	
	//Operator
	public static Joystick operator = new Joystick(2);
	
	public static DoubleSupplier turretStick = () -> deadband.applyAsDouble(operator.getTwist());
	
	public static Button changeFlap = new JoystickButton(operator, 12);
	
	//Tuner
	public static Joystick tuner = new Joystick(3);
	
	public static Button bigFlywheelIncrement = new JoystickButton(tuner, 7);
	public static Button bigFlywheelDecrement = new JoystickButton(tuner, 9);
	
	public static Button littleFlywheelIncrement = new JoystickButton(tuner, 8);
	public static Button littleFlywheelDecrement = new JoystickButton(tuner, 10);
	
	public static Button enableTuning = WPILambdas.createButton(() -> {
		boolean result = false;
		for(int i = 7; i <= 10; i++) {
			if (tuner.getRawButton(i)) result = true;
		}
		return result;
	});

	
	/*
	 * Preset buttons will replace this comment
	 */
	public static Button shoot = new JoystickButton(operator, 1);
	public static Button spool = new JoystickButton(operator, 2);

	public static Button feedIn = new JoystickButton(operator, 3);
	public static Button feedOut = new JoystickButton(operator, 4);
	
	public static Button agitatorOn = new JoystickButton(operator, 8);
	public static Button agitatorUnjam = new JoystickButton(operator, 10);
	
	public static Button moveTurret = new OnChangeButton(OI.turretStick, 0.2);
	
	public static Button presetClose = new JoystickButton(operator, 7);
	public static Button presetMid = new JoystickButton(operator, 9);
	public static Button presetFar = new JoystickButton(operator, 11);
	
	public static Button climb = new JoystickButton(operator, 6);
	
	
	public OI() {
		
		precisionDrive.whileHeld(new PrecisionDrive(throttle, turn, toggleAutoShift::get, shiftDown::get, shiftUp::get));
		driveStraight.whileHeld(new TeleopDriveStraight(throttle));
		
		gearBackward.whenPressed((new AutoDriveStraight(() -> 0.4, 0.6)));
		
		
		feedIn.whileHeld(new SetState<Feeder.State>(feeder, Feeder.State.FEEDING, Feeder.State.STOPPED));
		feedOut.whileHeld(new SetState<Feeder.State>(feeder, Feeder.State.OUTFEEDING, Feeder.State.STOPPED));
		
		//TODO: add sensor to agitator so operator can just toggle on/off when necessary and agitator handles rest.
		agitatorOn.whileHeld(new SetState<Agitator.State>(agitator, Agitator.State.FEEDING, Agitator.State.STOPPED));
		agitatorUnjam.whileHeld(new SetState<Agitator.State>(agitator, Agitator.State.UNJAM, Agitator.State.STOPPED));
		
		climb.whileHeld(new SetState<Climber.State>(climber, Climber.State.CLIMB, Climber.State.STOPPED));
		
		moveTurret.whileHeld(new MoveTurret(OI.turretStick));
		
		
		changeFlap.toggleWhenActive(new SetState<Flap.State>(flap, Flap.State.EXTENDED, Flap.State.RETRACTED));
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
