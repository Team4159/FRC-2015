package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import java.lang.Math;

public class AutoBalancer {
	private static final double OFF_BALANCEANGLE = 25; // Tune for actual robot
														// (during SVR)
	private static final double ON_BALANCEANGLE = 10;
	private static double angle_in_radians;
	private static double driveMagnitude;
	private static BuiltInAccelerometer accel = new BuiltInAccelerometer;

	private AutoBalancer() {
		
	}

	public static void balanceFeed(float angle, boolean limited, boolean stopping) {
		if (angle > OFF_BALANCEANGLE) {
			while (angle > ON_BALANCEANGLE) { // If angle exceeds the
												// off-balance threshold, loop
												// triggers and forces robot to
												// drive backwards
				SmartDashboard.putBoolean("Auto-Balance_Engaged", true); // Until
																			// it
																			// goes
																			// under
																			// a
																			// certain
																			// on-balance
																			// threshold
																			// as
																			// well
				angle_in_radians = Math.toRadians(angle);
				driveMagnitude = -1 * Math.sin(angle_in_radians); // used to
																	// slow down
																	// as the
																	// angle
																	// decreases
																	// (negative
																	// to drive
																	// backwards)

				OctoDrive.autoDrive.drive(driveMagnitude, 0.0);
				angle = -1 * Robot.imu.getRoll(); // Rereads the angle for the
													// next loop
			}
		}
		
		if(Math.sqrt(Math.pow(Math.sqrt(Math.pow(accel.getY(),2)+Math.pow(accel.getZ(),2)),2)+Math.pow(accel.getX(),2)) > 0.5 && limited && !stopping){
			OctoDrive.autoDrive.drive(0, 0.0);
			SmartDashboard.putBoolean("Auto-Balance_Engaged", true);
		}

		SmartDashboard.putBoolean("Auto-Balance_Engaged", false);
	}

}