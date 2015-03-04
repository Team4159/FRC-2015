package org.usfirst.frc.team4159.robot;

public class gyroSampler implements Runnable {

	private GyroITG3200 mainGyro; 
	private volatile boolean isLoopRunning;
	private Thread gyroLoop;
	private int gyro_angle;
	private int tmp_angle;
	private static final int N_AVG_SMPL_PER_SEC	=	10;
	private static final int N_SMPL_PER_AVG		=	10;
	
	private int smpl_per_sec;
	private int smpl_per_avg;
	//private short[] avg_buf;
	//private short[] smpl_buf;
	
	public gyroSampler(GyroITG3200 gyro) { //Maybe change this to the other mentor's code on gitHub
		smpl_per_sec=N_AVG_SMPL_PER_SEC;
		smpl_per_avg=N_SMPL_PER_AVG;
		mainGyro = gyro;
		gyroLoop = new Thread(new gyroSampler(mainGyro)); //initializes thread
	}
 	public gyroSampler(GyroITG3200 gyro,int smpl_per_s,int smpl_per_a){
 		//this can take parameters for smple count settings
 		smpl_per_sec=smpl_per_s;
 		smpl_per_avg=smpl_per_a;
 		mainGyro = gyro;
 		gyroLoop = new Thread(new gyroSampler(mainGyro)); //initializes thread
 	}
	@Override
	public void run() {
		//avg_buf = new short[N_SMPL_PER_AVG];
		//smpl_buf = new short[N_AVG_SMPL_PER_SEC];
		int angle_change=0;
		gyro_angle = 0;
		while(isLoopRunning){
			//ETHAN, do your gyro sampling magic over here 
			//(remember this is a while loop though)
			mainGyro.initialize();
			//for(int smpl_n=0;smpl_n<smpl_buf.length;smpl_n++){
				short avg_result=0;
				int avg_sum=0;
				tmp_angle=gyro_angle;
				for(int avg_n=0;avg_n<smpl_per_avg;avg_n++){
					short rZ = mainGyro.getRotationZ();
					avg_sum += rZ;
					gyroLoop.sleep(1000/(smpl_per_avg*smpl_per_sec));
				}
				avg_result= avg_sum/smpl_per_avg;
				angle_change = avg_result/smpl_per_sec;
				tmp_angle += angle_change;
				gyro_angle = (tmp_angle);//fix for stuff later
				//gyroLoop.sleep(1000/N_AVG_SMPL_PER_SEC);
			//}
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
	public int get_angle(){
		return gyro_angle;
		
	}
	
}