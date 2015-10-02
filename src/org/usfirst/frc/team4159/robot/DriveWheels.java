package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.Victor;

//Gets all driving motors and groups them for easier and more convenient use
public class DriveWheels {

	public Victor frontLeftMotor;
	public Victor rearLeftMotor;
	public Victor frontRightMotor;
	public Victor rearRightMotor;

	public DriveWheels(Victor frontLeft, Victor rearLeft, Victor frontRight, Victor rearRight) {
		frontLeftMotor = frontLeft;
		rearLeftMotor = rearLeft;
		frontRightMotor = frontRight;
		rearRightMotor = rearRight;

	}

	public DriveWheels(int channel1, int channel2, int channel3, int channel4) {
		frontLeftMotor = new Victor(channel1);
		rearLeftMotor = new Victor(channel2);
		frontRightMotor = new Victor(channel3);
		rearRightMotor = new Victor(channel4);
	}

	public void stopAll() {
		frontLeftMotor.set(0);
		rearLeftMotor.set(0);
		frontRightMotor.set(0);
		rearRightMotor.set(0);
	}

}