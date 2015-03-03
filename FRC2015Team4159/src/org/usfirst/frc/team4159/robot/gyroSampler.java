package org.usfirst.frc.team4159.robot;

public class gyroSampler implements Runnable {

	test_ITG3200 mainGyro; 
	private volatile boolean isLoopRunning;
	
	public gyroSampler(test_ITG3200 gyro) { //Maybe change this to the other mentor's code on gitHub
		mainGyro = gyro;
	}
	@Override
	public void run() {
		while(isLoopRunning){
			//ETHAN, do your gyro sampling magic over here (remember this is a while loop though)
		}
		
	}
	
	public void startGyro() {
		Thread gyroLoop = new Thread(new gyroSampler(mainGyro));  //Creates an intance of itself to initialize the thread (might bug)
		isLoopRunning=true; //While loop can run
		
		gyroLoop.start();   //starts thread
	}
	
	public void stopGyro(){
		isLoopRunning=false; //Thread should exit because while loop is finished
	}
	
	
	
}