package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

import java.lang.Math;

public class OctoDrive {
	
//	private PIDVictor frontLeft;
//	private PIDVictor rearLeft;
//	private PIDVictor frontRight;
//	private PIDVictor rearRight;
	
	//PROVISIONAL//
	private Victor frontLeft;
	private Victor rearLeft;
	private Victor frontRight;
	private Victor rearRight;
	
	private DrivePistons octoShift;
	
	private boolean isMecanum;
	
	public OctoDrive(DriveWheels wheelSet, DrivePistons pistonSet) {
		frontLeft = wheelSet.frontLeftMotor;
		rearLeft = wheelSet.rearLeftMotor;
		frontRight = wheelSet.frontRightMotor;
		rearRight = wheelSet.rearRightMotor;
		
		octoShift = pistonSet;
	}
	
	public void octoShift(boolean state) {
		isMecanum = state;
		if (isMecanum) {
			octoShift.linearActuate(false);
		} else {
			octoShift.linearActuate(true);
		}
		
	}
	
	
	public void manualDrive(Joystick driveStick, Joystick turnStick){
		if (isMecanum) {
			double right = driveStick.getX();
			double forward = -driveStick.getY();
			double clockwise = turnStick.getX();
		
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
		
			if (max>1) {
				front_left/=max;
				front_right/=max;
				rear_left/=max;
				rear_right/=max;
			}
		
//			frontLeft.pidSet(front_left);
//			rearLeft.pidSet(rear_left);
//			frontRight.pidSet(front_right);
//			rearRight.pidSet(rear_right);
			
			//PROVISIONAL//
			frontLeft.set(front_left);
			rearLeft.set(rear_left);
			frontRight.set(front_right);
			rearRight.set(rear_right);
			//PROVISIONAL//
			
		} else {
			double leftVelocity = driveStick.getY();
			double rightVelocity = driveStick.getY();
			
//			frontLeft.pidSet(leftVelocity);
//			rearLeft.pidSet(leftVelocity);
//			frontRight.pidSet(rightVelocity);
//			rearRight.pidSet(rightVelocity);
			
			//PROVISIONAL//
			frontLeft.set(leftVelocity);
			rearLeft.set(leftVelocity);
			frontRight.set(rightVelocity);
			rearRight.set(rightVelocity);
			//PROVISIONAL//
		}
	}
}