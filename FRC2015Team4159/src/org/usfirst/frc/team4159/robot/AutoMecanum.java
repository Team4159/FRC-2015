//package org.usfirst.frc.team4159.robot;
//
//public class AutoMecanum {
//	
//	DriveWheels mainDrive;
//	
//	public AutoMecanum (DriveWheels wheelSet) {
//		mainDrive = wheelSet;
//	}
//	
//	public void driveLeft(double velocity) {
//		mainDrive.frontLeftMotor.pidSet(-velocity);
//		mainDrive.rearLeftMotor.pidSet(velocity);
//		mainDrive.frontRightMotor.pidSet(velocity);
//		mainDrive.rearRightMotor.pidSet(-velocity);
//	}
//	
//	public void driveRight(double velocity) {
//		mainDrive.frontLeftMotor.pidSet(velocity);
//		mainDrive.rearLeftMotor.pidSet(-velocity);
//		mainDrive.frontRightMotor.pidSet(-velocity);
//		mainDrive.rearRightMotor.pidSet(velocity);
//		
//	}
//	
//	public void driveForward(double velocity) {
//		mainDrive.frontLeftMotor.pidSet(velocity);
//		mainDrive.rearLeftMotor.pidSet(velocity);
//		mainDrive.frontRightMotor.pidSet(velocity);
//		mainDrive.rearRightMotor.pidSet(velocity);
//
//	}
//	
//	public void driveBack(double velocity) {
//		mainDrive.frontLeftMotor.pidSet(-velocity);
//		mainDrive.rearLeftMotor.pidSet(-velocity);
//		mainDrive.frontRightMotor.pidSet(-velocity);
//		mainDrive.rearRightMotor.pidSet(-velocity);
//
//	}
//	
//	public void driveStop() {
//		mainDrive.frontLeftMotor.pidSet(0);
//		mainDrive.rearLeftMotor.pidSet(0);
//		mainDrive.frontRightMotor.pidSet(0);
//		mainDrive.rearRightMotor.pidSet(0);
//
//	}
//	
//	public void driveForwardLeft(double velocity) {
//		mainDrive.rearLeftMotor.pidSet(velocity);
//		mainDrive.frontRightMotor.pidSet(velocity);
//	}
//	
//	public void driveForwardRight(double velocity) {
//		mainDrive.frontLeftMotor.pidSet(velocity);
//		mainDrive.rearRightMotor.pidSet(velocity);
//	}
//	
//	public void driveBackLeft(double velocity) {
//		mainDrive.frontLeftMotor.pidSet(-velocity);
//		mainDrive.rearRightMotor.pidSet(-velocity);
//	}
//	
//	public void driveBackRight(double velocity) {
//		mainDrive.rearLeftMotor.pidSet(-velocity);
//		mainDrive.frontRightMotor.pidSet(-velocity);
//	}
//	
//}