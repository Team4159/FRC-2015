package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoBalancer {
	private static final double OFFBALANCEANGLE = 40;       //Tune for actual robot (during SVR)
	private static final double ONBALANCEANGLE = 10;
	private static double angle_in_radians;
	private static double driveMagnitude;
	
	private AutoBalancer() {
		
	}
	
	public static void balanceFeed(float angle) {
		while (angle >= OFFBALANCEANGLE && angle > ONBALANCEANGLE) {		//If angle exceeds the off-balance threshold, loop triggers and forces robot to drive backwards
			SmartDashboard.putBoolean("Auto-Balance_Engaged", true);		//Until it goes under a certain on-balance threshold as well
			angle_in_radians = Math.toRadians(angle);
			driveMagnitude = -1 * Math.sin(angle_in_radians); //used to slow down as the angle decreases (negative to drive backwards)
			
			OctoDrive.autoDrive.drive(driveMagnitude, 0.0);
			angle = IO.imu.getPitch(); //Rereads the angle for the next loop
		}

		SmartDashboard.putBoolean("Auto-Balance_Engaged", false);
	}
	
}