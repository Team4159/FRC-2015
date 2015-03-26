package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoBalancer {
	private static final double OFFBALANCEANGLE = 40;       //Tune for actual robot (during SVR)
	private static final double ONBALANCEANGLE = 10;
	
	private AutoBalancer() {
		
	}
	
	public static void balanceFeed(float angle) {
		while (angle >= OFFBALANCEANGLE && angle > ONBALANCEANGLE) {
			SmartDashboard.putBoolean("Auto-Balance_Engaged", true);
			OctoDrive.autoDrive.drive(-0.5, 0.0);
			angle = IO.imu.getPitch();
		}
		while (angle <= -OFFBALANCEANGLE && angle < -ONBALANCEANGLE) {
			SmartDashboard.putBoolean("Auto-Balance_Engaged", true);
			OctoDrive.autoDrive.drive(0.5, 0.0);
			angle = IO.imu.getPitch();
		}
		
		SmartDashboard.putBoolean("Auto-Balance_Engaged", false);
	}
	
}