package org.usfirst.frc.team4159.unused;
//Cleans up the usage of gyroSampler
public class GyroManager {
	
	gyroSampler gyroFunction;
	Thread gyroLoop;
	
	public GyroManager(gyroSampler tmp_gyroFunction) {
		gyroFunction = tmp_gyroFunction;
		gyroLoop = new Thread(gyroFunction);
	}
	
	public void startGyro() {
		if (!gyroLoop.isAlive()){  //Starts gyro thread if not started alread
		gyroFunction.gyroInit();
		gyroLoop.start(); 
		} else {				   //If gyro is already running, gyro resets instead
			gyroFunction.reset();
			
		}
	}
	
	public void stopGyro() {
		gyroFunction.stopGyro();  //Stops gyro thread
	}
	
	public void reset() {
		gyroFunction.reset();	  //Resets gyro values
	}
	
	public int getAngle(){        //Gets angle from 0-360
		return gyroFunction.get_angle();
		
	}
	
	public int getPidAngle() {    //Gets angle from -180 - 0 - 180
		return gyroFunction.pidAngle();
	}
	
}