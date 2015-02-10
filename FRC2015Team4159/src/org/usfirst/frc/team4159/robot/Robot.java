
package org.usfirst.frc.team4159.robot;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;


public class Robot extends IterativeRobot {
	
    DriveWheels wheelSet = new DriveWheels(0, 1, 2, 3);
    DrivePistons pistonSet = new DrivePistons(0, 1, 2, 3);
    teleopOctoOriginal mainDrive = new teleopOctoOriginal(wheelSet, pistonSet);

    Joystick leftStick = new Joystick(1);
    Joystick rightStick = new Joystick(2);
    
    public void robotInit() {
    	mainDrive.octoShift(true);
    }
    
    public void autonomousPeriodic() {

    }

    
    public void teleopPeriodic() {
    	if (leftStick.getRawButton(3)) {
    		mainDrive.octoShift(false);
    	}
    	if (leftStick.getRawButton(2)) {
    		mainDrive.octoShift(true);
    	}
    	
    	mainDrive.manualDrive(leftStick, rightStick);
        
    }
    
    public void testInit() {
    	
    	
    }
    
    public void testPeriodic() {
    													
    	
    }
    
    public void disabledInit() {                                            //Reset PID
    	
    }
    
}
