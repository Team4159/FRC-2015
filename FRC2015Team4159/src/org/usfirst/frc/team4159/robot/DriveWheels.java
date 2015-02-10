package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.Victor;

public class DriveWheels {
	
//	public PIDVictor frontLeftMotor;
//	public PIDVictor rearLeftMotor;
//	public PIDVictor frontRightMotor;
//	public PIDVictor rearRightMotor;
//	
	
	//PROVISIONAL//
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
	
	
}