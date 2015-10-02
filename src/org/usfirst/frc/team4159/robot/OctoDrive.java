package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;

import java.lang.Math;

public class OctoDrive {

	private Victor frontLeft;
	private Victor rearLeft;
	private Victor frontRight;
	private Victor rearRight;

	private DrivePistons octoShift;

	private int isMecanum;
	private boolean frontLeftInverted;
	private boolean rearLeftInverted;
	private boolean frontRightInverted;
	private boolean rearRightInverted;
	private boolean leftSideInverted;
	private boolean rightSideInverted;

	public static RobotDrive autoDrive;
	public static RobotDrive strafeDrive;

	public static final int MECANUM_DRIVE = 1;
	public static final int TANK_DRIVE = 2;
	public static final int BACK_EXTEND_DRIVE = 3;

	public OctoDrive(DriveWheels wheelSet, DrivePistons pistonSet) { // Takes in
																		// wheelseet
																		// and
																		// pistonSet
																		// parameters
		frontLeft = wheelSet.frontLeftMotor;
		rearLeft = wheelSet.rearLeftMotor;
		frontRight = wheelSet.frontRightMotor;
		rearRight = wheelSet.rearRightMotor;

		frontLeftInverted = false;
		rearLeftInverted = false;
		frontRightInverted = false;
		rearRightInverted = false;

		octoShift = pistonSet;

		autoDrive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight); // For
																				// autonomous
																				// driving
																				// turning
																				// method
		strafeDrive = new RobotDrive(frontRight, frontLeft, rearRight, rearLeft);
	}

	public OctoDrive octoShift(int state) { // Changes boolean value for
											// mecanum/tank state. The code will
											// react accordingly
		isMecanum = state;
		if (isMecanum == MECANUM_DRIVE) {
			octoShift.linearActuate(false); // Changes to mecanum
		} else if (isMecanum == TANK_DRIVE) {
			octoShift.linearActuate(true); // Changes to tank (full)
		} else {
			octoShift.backActuate(true); // Extends back pistons
		}

		return this; // For method chaining
	}

	public void manualDrive(double xVal1, double yVal1, double xVal2, double yVal2) { // Drives
																						// the
																						// robot
																						// in
																						// a
																						// mecanum/tank
																						// mode
		if (isMecanum == MECANUM_DRIVE) { // Value Dictates the type of drive
											// you are using
			double right = (xVal1 > 0 ? xVal1 * xVal1 : -(xVal1 * xVal1)); // Acceleration
																			// curve
			double forward = (yVal1 > 0 ? yVal1 * yVal1 : -(yVal1 * yVal1));
			double clockwise = xVal2 * -0.75; // Decreased sensitivity

			double front_left = forward + clockwise + right;
			double front_right = forward - clockwise - right;
			double rear_left = forward + clockwise - right;
			double rear_right = forward - clockwise + right;

			double max = Math.abs(front_left);
			if (Math.abs(front_right) > max) {
				max = Math.abs(front_right);
			}
			if (Math.abs(rear_left) > max) {
				max = Math.abs(rear_left);
			}
			if (Math.abs(rear_right) > max) {
				max = Math.abs(rear_right);
			}

			if (max > 1) {
				front_left /= max;
				front_right /= max;
				rear_left /= max;
				rear_right /= max;
			}

			if (frontLeftInverted) { // Motor inversion
				front_left = -front_left;

			}
			if (rearLeftInverted) {
				rear_left = -rear_left;
			}
			if (frontRightInverted) {
				front_right = -front_right;
			}
			if (rearRightInverted) {
				rear_right = -rear_right;
			}

			frontLeft.set(front_left);
			rearLeft.set(rear_left);
			frontRight.set(front_right);
			rearRight.set(rear_right);

		} else { // Tank driving code
			double leftVelocity = (yVal1 > 0 ? yVal1 * yVal1 : -(yVal1 * yVal1));
			double rightVelocity = (yVal2 > 0 ? yVal2 * yVal2 : -(yVal2 * yVal2));

			if (leftSideInverted) {
				leftVelocity = -leftVelocity;
			}
			if (rightSideInverted) {
				rightVelocity = -rightVelocity;
			}

			frontLeft.set(leftVelocity);
			rearLeft.set(leftVelocity);
			frontRight.set(rightVelocity);
			rearRight.set(rightVelocity);

		}
	}

	public OctoDrive invertMotor(String motorName, boolean isInverted) { // Motor
																			// inversion
																			// method,
																			// changes
																			// boolean
																			// value
		switch (motorName) { // to indicated a motor's inversion, the driving
								// code
		case "frontLeft": // Will react accordingly
			frontLeftInverted = isInverted;
			break; // Note: the frontLeft, rearLeft... etc methods are for
		case "rearLeft": // Mecanum inversion, leftSide and rightSide for tank
			rearLeftInverted = isInverted; // Motor inversion
			break;
		case "frontRight":
			frontRightInverted = isInverted;
			break;
		case "rearRight":
			rearRightInverted = isInverted;
			break;
		case "leftSide":
			leftSideInverted = isInverted;
			break;
		case "rightSide":
			rightSideInverted = isInverted;
			break;
		default:
			System.out.println("No motors were inverted.");
			break;
		}

		return this; // For method chaining
	}
}
