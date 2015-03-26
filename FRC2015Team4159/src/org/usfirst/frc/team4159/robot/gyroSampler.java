package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class gyroSampler implements Runnable {

	private GyroITG3200 mainGyro; 
	private volatile boolean isLoopRunning;
	private volatile int gyro_angle;
	private volatile int tmp_angle;
	private volatile short raw_angle;
	private static final int N_AVG_SMPL_PER_SEC	=	10;
	private static final int N_SMPL_PER_AVG		=	10;
	private volatile boolean reset_angle_b = true;
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
 		//this can take parameters for sample count settings
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
		long smpl_delay_ms = (long)(1000/(smpl_per_avg*smpl_per_sec));
		mainGyro.initialize();
		while(isLoopRunning){
			SmartDashboard.putBoolean("If Thread Active", true);
			
				short avg_result=0;
				int avg_sum=0;
				tmp_angle=gyro_angle;
				for(int avg_n=0;avg_n<smpl_per_avg;avg_n++){
					short rZ = mainGyro.getRotationZ();
					avg_sum += rZ;
					raw_angle = rZ;
					try {
						//Thread.sleep(1000/(smpl_per_avg*smpl_per_sec));
						Thread.sleep(smpl_delay_ms);
					} catch (InterruptedException e) {
					}
				}
				avg_result = (short) (avg_sum/smpl_per_avg);
				angle_change = (int) (avg_result/smpl_per_sec);
				tmp_angle += angle_change;
//				if(!reset_angle_b){
				gyro_angle = raw_angle_convert(tmp_angle);//fix for stuff later
//					SmartDashboard.putBoolean("Is resetting", false);
//				} else {
//					gyro_angle = 0;
//					reset_angle_b = false;
//					SmartDashboard.putBoolean("Is resetting", true);
//				}
//				SmartDashboard.putNumber("Gyro Value from Thread", gyro_angle);
//				SmartDashboard.putNumber("Gyro Raw Value form Thread", raw_angle);
				SmartDashboard.putBoolean("If Thread Active", false);
		}
	}
	
	public void gyroInit() {
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
	
	public int pidAngle() {
		//Converts angle from 0-360 to the range of -179 - 180 where 0 is the center
		int pidAngle = 0;
		if (gyro_angle == 0){
			pidAngle = 0;
		} else if (gyro_angle >180) {
			pidAngle = gyro_angle - 360;
		} else if (gyro_angle < 180) {
			pidAngle = gyro_angle;
		} else if (gyro_angle == 180) {
			gyro_angle = 180;
		} else {
			gyro_angle = 0;
		}
		
		return pidAngle;
		
	}
	
	private int raw_angle_convert(int raw_angle){
		//convert from a raw int angle to a simple angle between 0-360
		raw_angle = raw_angle;
		int out_angle=0;
		int abs_raw_angle = (raw_angle>=0)?raw_angle:(0-raw_angle);
		abs_raw_angle %= 360;
		out_angle = (raw_angle>=0) ? abs_raw_angle:(360-abs_raw_angle);
		return out_angle;
	}
	
	public void reset(){
		//reset_angle_b = true;
		gyro_angle = 0;
		tmp_angle = 0;
//		mainGyro.reset();
//		Timer.delay(0.005);
//		mainGyro.initialize();
	}
}
