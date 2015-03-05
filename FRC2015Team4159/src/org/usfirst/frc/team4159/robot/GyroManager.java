package org.usfirst.frc.team4159.robot;
//Cleans up the usage of gyroSampler
public class GyroManager {
	
	gyroSampler gyroFunction;
	Thread gyroLoop;
	
	public GyroManager(gyroSampler tmp_gyroFunction) {
		gyroFunction = tmp_gyroFunction;
		gyroLoop = new Thread(gyroFunction);
	}
	
	public void startGyro() {
		if (!gyroLoop.isAlive()){
		gyroFunction.gyroInit();
		gyroLoop.start(); }
//		} else {
//			gyroFunction.reset();
//			
//		}
	}
	
	public void stopGyro() {
		gyroFunction.stopGyro();
	}
	
	public int getAngle(){
		return gyroFunction.get_angle();
		
	}
	
	
	
}