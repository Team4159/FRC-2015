package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;

import java.lang.Math;

public class OctoDrive {


	private Victor frontLeft;
	private Victor rearLeft;
	private Victor frontRight;
	private Victor rearRight;
		
	private DrivePistons octoShift;
	
	private boolean isMecanum;
	private boolean frontLeftInverted;
	private boolean rearLeftInverted;
	private boolean frontRightInverted;
	private boolean rearRightInverted;
	private boolean leftSideInverted;
	private boolean rightSideInverted;
	
	public static RobotDrive autoDrive;
	
	public OctoDrive(DriveWheels wheelSet, DrivePistons pistonSet) {
		frontLeft = wheelSet.frontLeftMotor;
		rearLeft = wheelSet.rearLeftMotor;
		frontRight = wheelSet.frontRightMotor;
		rearRight = wheelSet.rearRightMotor;
		
		frontLeftInverted = false;
		rearLeftInverted = false;
		frontRightInverted = false;
		rearRightInverted = false;
		
		octoShift = pistonSet;
		
		autoDrive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
	}
	
	
	public OctoDrive octoShift(boolean state) {
		isMecanum = state;
		if (isMecanum) {
			octoShift.linearActuate(false);
		} else {
			octoShift.linearActuate(true);
		}
		
		return this;
	}
	
	
	public void manualDrive(double xVal1, double yVal1, double xVal2, double yVal2){
		if (isMecanum) {
			double right = xVal1;
			double forward = yVal1;
			double clockwise = xVal2;
		
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
			
			if(frontLeftInverted) {
				front_left = -front_left;
				
			}
			if(rearLeftInverted) {
				rear_left = -rear_left;
			}
			if(frontRightInverted){
				front_right = -front_right;
			}
			if(rearRightInverted){
				rear_right = -rear_right;
			}
		

			frontLeft.set(front_left);
			rearLeft.set(rear_left);
			frontRight.set(front_right);
			rearRight.set(rear_right);

			
		} else {
			double leftVelocity = yVal1;
			double rightVelocity = yVal2;
			
			if(leftSideInverted) {
				leftVelocity = -leftVelocity;
			}
			if(rightSideInverted) {
				rightVelocity = -rightVelocity;
			}
			

			frontLeft.set(leftVelocity);
			rearLeft.set(leftVelocity);
			frontRight.set(rightVelocity);
			rearRight.set(rightVelocity);
			
		}
	}
	
	public OctoDrive invertMotor(String motorName, boolean isInverted) {
		switch (motorName) {
			case "frontLeft":
				frontLeftInverted = isInverted;
				break;
			case "rearleft":
				rearLeftInverted = isInverted;
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
		
		return this;
	}
}	
	
