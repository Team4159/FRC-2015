package org.usfirst.frc.team4159.robot;

public class gyroSampler implements Runnable {

	private test_ITG3200 mainGyro; 
	private volatile boolean isLoopRunning;
	private Thread gyroLoop;
	
	public gyroSampler(test_ITG3200 gyro) { //Maybe change this to the other mentor's code on gitHub
		mainGyro = gyro;
		gyroLoop = new Thread(new gyroSampler(mainGyro)); //initializes thread
	}
	@Override
	public void run() {
		while(isLoopRunning){
			//ETHAN, do your gyro sampling magic over here (remember this is a while loop though)
		}
		
	}
	
	public void startGyro() {
		isLoopRunning = true;
		gyroLoop.start(); //Starts thread
		
	}
	
	public void stopGyro(){
		isLoopRunning = false; //Thread should exit because while loop is finished
	}
	
	public void pauseGyro(){
	}
	
	
}