package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;

import java.lang.Math;

public class OctoDrive implements Runnable {
	
//	private PIDVictor frontLeft;
//	private PIDVictor rearLeft;
//	private PIDVictor frontRight;
//	private PIDVictor rearRight;
	
	//PROVISIONAL//
	private Victor frontLeft;
	private Victor rearLeft;
	private Victor frontRight;
	private Victor rearRight;
	//PROVISIONAL//
	
	private DrivePistons octoShift;
	
	private boolean isMecanum;
	private boolean frontLeftInverted;
	private boolean rearLeftInverted;
	private boolean frontRightInverted;
	private boolean rearRightInverted;
	private boolean leftSideInverted;
	private boolean rightSideInverted;
	
	
	RobotDrive notMainDrive;
	public Gyro gyro;
	private double Kp = 0.3; //Test to change
	boolean ifForward;
	boolean ifSideways;
	DriverStation ds;
	double autoVelocity;
	
	public OctoDrive(DriveWheels wheelSet, DrivePistons pistonSet, Gyro tempGyro) {
		frontLeft = wheelSet.frontLeftMotor;
		rearLeft = wheelSet.rearLeftMotor;
		frontRight = wheelSet.frontRightMotor;
		rearRight = wheelSet.rearRightMotor;
		
		frontLeftInverted = false;
		rearLeftInverted = false;
		frontRightInverted = false;
		rearRightInverted = false;
		
		octoShift = pistonSet;
		
		notMainDrive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
		gyro = tempGyro;
	}
	
	public OctoDrive(DriveWheels wheelSet, DrivePistons pistonSet, int gyroPort) {
		frontLeft = wheelSet.frontLeftMotor;
		rearLeft = wheelSet.rearLeftMotor;
		frontRight = wheelSet.frontRightMotor;
		rearRight = wheelSet.rearRightMotor;
		
		frontLeftInverted = false;
		rearLeftInverted = false;
		frontRightInverted = false;
		rearRightInverted = false;
		
		octoShift = pistonSet;
		
		notMainDrive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
		gyro = new Gyro(gyroPort);
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
			double rightVelocity = turnStick.getY();
			
			if(leftSideInverted) {
				leftVelocity = -leftVelocity;
			}
			if(rightSideInverted) {
				rightVelocity = -rightVelocity;
			}
			
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
	
	public void invertMotor(String motorName, boolean isInverted) {
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
	}
	
	
	public void autoMecanumDrive(double velocity, boolean ifDriveStraight) {
		if (ifDriveStraight) {
			autoVelocity=velocity;
			ifForward=true;
		}
	}
	
	public void autoStop() {
		ifForward=false;
		ifSideways=false;
		autoVelocity=0.0;
	}

	
	public void run() {
		double gyroAngle;
		while (ds.isEnabled()) {
			gyroAngle = gyro.getAngle();
			if (ifForward) {
				notMainDrive.drive(autoVelocity, -gyroAngle*Kp);
			} else {
				notMainDrive.drive(0.0, 0.0);
			}
		
		}
	}
	
	public void startAutoThread() {
		Thread autoThread = new Thread(this);
		autoThread.start();
	}
}