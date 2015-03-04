package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class gyroSampler implements Runnable {

	private GyroITG3200 mainGyro; 
	private volatile boolean isLoopRunning;
	private Thread gyroLoop;
	private volatile int gyro_angle;
	private volatile int tmp_angle;
	private volatile short raw_angle;
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
	}
 	public gyroSampler(GyroITG3200 gyro,int smpl_per_s,int smpl_per_a){
 		//this can take parameters for smple count settings
 		smpl_per_sec=smpl_per_s;
 		smpl_per_avg=smpl_per_a;
 		mainGyro = gyro;
 	}
	@Override
	public void run() {
		//avg_buf = new short[N_SMPL_PER_AVG];
		//smpl_buf = new short[N_AVG_SMPL_PER_SEC];
		int angle_change=0;
		gyro_angle = 0;
		mainGyro.initialize();
		while(isLoopRunning){
			//ETHAN, do your gyro sampling magic over here 
			//(remember this is a while loop though)
				short avg_result=0;
				int avg_sum=0;
				tmp_angle=gyro_angle;
				for(int avg_n=0;avg_n<smpl_per_avg;avg_n++){
					short rZ = mainGyro.getRotationZ();
					avg_sum += rZ;
					raw_angle = rZ;
					try {
						Thread.sleep(1000/(smpl_per_avg*smpl_per_sec));
					} catch (InterruptedException e) {
					}
				}
				avg_result= (short) (avg_sum/smpl_per_avg);
				angle_change = (int) (avg_result/smpl_per_sec);
				tmp_angle += angle_change;
				gyro_angle = raw_angle_convert(tmp_angle);//fix for stuff later
				
//				SmartDashboard.putNumber("Gyro Value from Thread", gyro_angle);
//				SmartDashboard.putNumber("Gyro Raw Value form Thread", raw_angle);
			
		}
		
	}
	
	public void startGyro() {
		isLoopRunning = true;
		
	}
	
	public void stopGyro(){
		isLoopRunning = false; //Thread should exit because while loop is finished
	}
	
	public void pauseGyro(){
	}
	
	public int get_angle(){
		return gyro_angle;
	}
	
	public short getRawAngle(){
		return raw_angle;
	}
	
	private int raw_angle_convert(int raw_angle){
		//convert from a raw int angle to a simple angle between 0-360
		int out_angle=0;
		int abs_raw_angle = (raw_angle>=0)?raw_angle:(0-raw_angle);
		abs_raw_angle %= 360;
		out_angle = (raw_angle>=0) ? abs_raw_angle:(360-abs_raw_angle);
		return out_angle;
	}
	
}