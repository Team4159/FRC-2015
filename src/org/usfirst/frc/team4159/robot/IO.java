package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

public class IO {
	public static Joystick leftStick = new Joystick(1); // Joystick Declaration
	public static Joystick rightStick = new Joystick(2);
	public static Joystick secondaryStick = new Joystick(3);

	public static DriveWheels wheelSet = new DriveWheels(0, 1, 2, 3); // Drivetrain
																		// Declarations
	private static DrivePistons pistonSet = new DrivePistons(0, 1, 2, 3);
	public static OctoDrive mainDrive = new OctoDrive(wheelSet, pistonSet).octoShift(1).invertMotor("rearRight", false)
			.invertMotor("frontRight", false).invertMotor("frontLeft", true).invertMotor("rearLeft", true)
			.invertMotor("leftSide", true);

	public static ToteLifter elevator = new ToteLifter(4, 5); // ToteLifter
																// Declarations
	public static DigitalInput lowLimit = new DigitalInput(8);
	public static DigitalInput highLimit = new DigitalInput(9);

	static {
		elevator.setHighLow(lowLimit, highLimit);
	}

	public static DigitalInput toteSensor = new DigitalInput(7);
}