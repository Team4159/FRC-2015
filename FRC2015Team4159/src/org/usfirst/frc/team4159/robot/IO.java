package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class IO {
	public static Joystick leftStick = new Joystick(1);                           //Joystick Declaration
	public static Joystick rightStick = new Joystick(2);
	public static Joystick secondaryStick = new Joystick(3);
	
	public static DriveWheels wheelSet = new DriveWheels(0, 1, 2, 3);			  //Drivetrain Declarations
	public static DrivePistons pistonSet = new DrivePistons(0, 1, 2, 3);		  
	public static OctoDrive mainDrive = new OctoDrive(wheelSet, pistonSet);
	static {
		mainDrive.octoShift(true);
		mainDrive.invertMotor("rearRight", true);
    	mainDrive.invertMotor("frontRight", true);
    	mainDrive.invertMotor("leftSide", true);
	}
	
	public static ToteLifter elevator = new ToteLifter(4, 5);					  //ToteLifter Declarations
	public static DigitalInput lowLimit = new DigitalInput(8);
	public static DigitalInput highLimit = new DigitalInput(9);
	static {
		elevator.setHighLow(lowLimit, highLimit);
	}
	
	public static GyroManager mainGyro = new GyroManager(new gyroSampler          //Gyro Declaration
				(new GyroITG3200(I2C.Port.kOnboard)));
	
	
	public static NetworkTable imageValues;											  //RoboRealm NetworkTable Declaration
	static {
		imageValues = NetworkTable.getTable("");
	}
	
}