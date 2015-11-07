package org.usfirst.frc.team4159.robot;

import com.kauailabs.navx_mxp.AHRS;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	public static AHRS imu; // navX object declaration
	public static SerialPort serial_port;
	int autoChoice;
	boolean first_iteration;
	boolean is_calibrating;
	double elevatorValue;

	public static final double autoMotorPower = 0.5; // Do not exceed 50% or
														// robot go bye bye
	public static final double autoMotorTime = 2.0;
	public static final boolean autoPickup = true;
	public static final double autoPickupTime = 2.0;

	// Timer testTime = new Timer();
	public void robotInit() {
		// AutoChooser.setup();
		try {
			serial_port = new SerialPort(57600, SerialPort.Port.kUSB);
			byte update_rate_hz = 50;
			imu = new AHRS(serial_port, update_rate_hz);
			first_iteration = true;
		} catch (Exception ex) {
			first_iteration = false;
		}

		if (imu != null) {
			SmartDashboard.putBoolean("First Iteration Of Gyro Complete", true);
			LiveWindow.addSensor("IMU", "Gyro", imu);
		} else {
			SmartDashboard.putBoolean("First Iteration Of Gyro Complete", false);
		}
	}

	boolean firstRun;

	public void autonomousInit() {
		// autoChoice = AutoChooser.getChoice();
		IO.mainDrive.octoShift(OctoDrive.MECANUM_DRIVE); // Shifts to mecanum
		// testTime.reset();
		// testTime.start();
		firstRun = true;

	}

	public void autonomousPeriodic() {
		if (first_iteration && !imu.isCalibrating())
			;
		{ // Wait for imu gyro to calibrate
			Timer.delay(0.3);
			imu.zeroYaw();
			first_iteration = false;
		}

		if (firstRun) { // Drive teh auto!!!
			if (autoPickup)
				toteTimedLift(autoPickupTime);
			straightDrive(autoMotorPower, autoMotorTime);
			firstRun = false;
		}
		//
		//
		// SmartDashboard.putBoolean("IMU_Connected", imu.isConnected());
		// SmartDashboard.putBoolean("IMU_IsCalibrating", imu.isCalibrating());
		//
		// runRoutine(autoChoice);
		// if(testTime.get() > 2.0) {
		// OctoDrive.autoDrive.drive(0.0, 0.0);
		// testTime.stop();
		// } else {
		// OctoDrive.autoDrive.drive(0.5, 0.0);
		// }
		// SmartDashboard.putNumber("Auto Time", testTime.get());
	}

	boolean ifSixDown;
	boolean ifSevenDown;

	public void teleopInit() {
		elevatorValue = 1.0;
		ifSixDown = false;
		ifSevenDown = false;
	}

	public void teleopPeriodic() {
		// is_calibrating = imu.isCalibrating();
		// if (first_iteration && !is_calibrating); {
		// Timer.delay(0.3);
		// imu.zeroYaw();
		// first_iteration = false;
		// }

		SmartDashboard.putNumber("Yaw Value", imu.getYaw());
		SmartDashboard.putNumber("Pitch Value", imu.getPitch());
		SmartDashboard.putNumber("Roll Value", imu.getRoll());

		SmartDashboard.putBoolean("Tote Sensed?", !IO.toteSensor.get());

		if (IO.leftStick.getRawButton(3)) { // Changes to tank

			IO.mainDrive.octoShift(OctoDrive.TANK_DRIVE);
			SmartDashboard.putString("Drive State:", "Traction/Tank");

		} else if (IO.leftStick.getRawButton(2)) { // Changes to mecanum

			IO.mainDrive.octoShift(OctoDrive.MECANUM_DRIVE);
			SmartDashboard.putString("Drive State:", "Mecanum");

		} else if (IO.leftStick.getRawButton(5)) { // Raises back set piston set
													// and changes control to
													// tank

			IO.mainDrive.octoShift(OctoDrive.BACK_EXTEND_DRIVE);
			SmartDashboard.putString("Drive State:", "Back Traction, Front Mecanum/Tank");

		}

		IO.mainDrive.manualDrive(-IO.leftStick.getX(), IO.leftStick.getY(), IO.rightStick.getX(), IO.rightStick.getY()); // Drives
																															// according
																															// to
																															// tank/mecanum
																															// boolean
																															// in
																															// OctoDrive

		if (!ifSixDown && IO.secondaryStick.getRawButton(6)) {
			if (elevatorValue < 1.0) {
				elevatorValue = elevatorValue + 0.2;
			}
			if (elevatorValue > 1.0) {
				elevatorValue = 1.0;
			}
			ifSixDown = true;
		} else if (ifSixDown && !IO.secondaryStick.getRawButton(6)) {
			ifSixDown = false;
		}

		if (!ifSevenDown && IO.secondaryStick.getRawButton(7)) {
			if (elevatorValue > 0.6) {
				elevatorValue = elevatorValue - 0.2;
			}
			if (elevatorValue < 0.6) {
				elevatorValue = 0.6;
			}
			ifSevenDown = true;
		} else if (ifSevenDown && !IO.secondaryStick.getRawButton(7)) {
			ifSevenDown = false;
		}

		if (IO.secondaryStick
				.getRawButton(3) || IO.rightStick.getRawButton(3) ) { // Moves
																			// elevator
																			// up
			IO.elevator.autoLift(elevatorValue);
		} else if (IO.secondaryStick
				.getRawButton(2) || IO.rightStick.getRawButton(2) ) { // Moves
																			// elevator
																			// down
			IO.elevator.autoLift(-elevatorValue);
		} else {
			IO.elevator.autoLift(0.0); // Stops elevator if there is no joystick
										// input
		}

		AutoBalancer.balanceFeed(-1 * imu.getRoll());

		SmartDashboard.putNumber("Elevator_Speed", elevatorValue * 100);
		SmartDashboard.putBoolean("Upper Limit Reached", !IO.highLimit.get());
		SmartDashboard.putBoolean("Lower Limit Reached", !IO.lowLimit.get());

	}

	public void testInit() {
		IO.mainDrive.octoShift(OctoDrive.MECANUM_DRIVE); // Shifts to mecanum
		firstRun = true;
	}

	public void testPeriodic() {
		// if(lowSensor.get()) { //Some limit switch testing code
		// testLED.set(true);
		// } else {
		// testLED.set(false);
		// }
		autoStrafe(0.5, 1.0);
	}

	public void disabledInit() {

	}

	// FIX UP AUTO STRAFE TO TURN ALL WHEELS IN RIGHT DIRECTION
	// =============================================================================================================================//
	// AUTONOMOUS//

	private static Timer autoTime = new Timer();

	private static double travelTime = 1.0; // Change based on how the mecanum
											// wheels perform on carpet
	private static double rejoinRouteTime = 1.0;
	private static double liftTime = 1.0;
	private static double exitTime = 3.0;
	private static double toteDropTime = 1.0;
	private static double giveupTime = 3.0;

	private static double Kp = 0.00278; // tune for gyro
	private static double drivetrainOffset = 0.1;

	public static void toteTimedLift(double liftTime) {
		IO.elevator.moveLow();
		autoTime.reset();
		autoTime.start();
		while (autoTime.get() < liftTime) {
			IO.elevator.autoLift(1.0);
		}
		autoTime.stop();
		autoTime.reset();
		IO.elevator.autoLift(0.0);

	}

	public static void straightDrive(double speed, double duration) {
		autoTime.reset();
		autoTime.start();
		while (autoTime.get() < duration) {
			OctoDrive.autoDrive.drive(speed, -Kp * imu.getYaw());
		}
		OctoDrive.autoDrive.drive(0.0, 0.0);
		autoTime.stop();
	}

	public static void toteGet(double speed) {
		autoTime.reset();
		autoTime.start();
		while (IO.toteSensor.get()) {
			OctoDrive.autoDrive.drive(speed, -Kp * Robot.imu.getYaw());
			if (autoTime.get() > giveupTime) {
				break;
			}
		}
		OctoDrive.autoDrive.drive(0.0, 0.0);
		autoTime.stop();

	}

	// ================================//
	// METHODS THAT DO NOT REQUIRE GYRO//
	// ================================//
	public static void straightDrive(double speed, double duration, double offset) {
		autoTime.reset();
		autoTime.start();
		while (autoTime.get() < duration) {
			OctoDrive.autoDrive.drive(speed, offset);
		}
		OctoDrive.autoDrive.drive(0.0, 0.0);
		autoTime.stop();
		autoTime.reset();
	}

	public static void toteGet(double speed, double offset) {
		autoTime.reset();
		autoTime.start();
		while (IO.toteSensor.get()) {
			OctoDrive.autoDrive.drive(speed, offset);

			if (autoTime.get() > giveupTime) {
				break;
			}
		}
		OctoDrive.autoDrive.drive(0.0, 0.0);
		autoTime.stop();
	}

	public static void autoStrafe(double speed, double duration) {
		autoTime.reset();
		autoTime.start();

		while (autoTime.get() < duration) {
			OctoDrive.strafeDrive.drive(0.5, -Kp * imu.getYaw());
		}
		autoTime.stop();
		autoTime.reset();
		IO.wheelSet.stopAll();
	}

	public static void continuedRoutine(boolean ifGyro) {
		if (ifGyro) {
			toteGet(0.5);
			IO.elevator.moveLow();
			toteTimedLift(liftTime);
			autoStrafe(0.5, rejoinRouteTime);
			straightDrive(0.5, travelTime);
			autoStrafe(-0.5, rejoinRouteTime);

		} else {
			toteGet(0.5, drivetrainOffset);
			IO.elevator.moveLow();
			toteTimedLift(liftTime);
			autoStrafe(0.5, rejoinRouteTime);
			straightDrive(0.5, travelTime, drivetrainOffset);
			autoStrafe(-0.5, rejoinRouteTime);
		}
	}

	public static void endRoutine(boolean ifGyro) {
		if (ifGyro) {
			toteGet(0.5);
			IO.elevator.moveLow();
			toteTimedLift(liftTime);
			autoStrafe(0.5, exitTime);
			IO.elevator.moveLow();
			straightDrive(-0.5, toteDropTime);
		} else {
			toteGet(0.5, drivetrainOffset);
			IO.elevator.moveLow();
			toteTimedLift(liftTime);
			autoStrafe(0.5, exitTime);
			IO.elevator.moveLow();
			straightDrive(-0.5, toteDropTime, drivetrainOffset);
		}
	}

	public static void runRoutine(int autoChoice) {
		switch (autoChoice) {
		case AutoChooser.MOVE_ONLY:
			straightDrive(0.5, 2, -0.05);
			break;
		case AutoChooser.PICK_THREE_TOTE:
			continuedRoutine(Robot.imu.isConnected());
			continuedRoutine(Robot.imu.isConnected());
			endRoutine(Robot.imu.isConnected());
			break;
		default:
			break;
		}
	}
}
