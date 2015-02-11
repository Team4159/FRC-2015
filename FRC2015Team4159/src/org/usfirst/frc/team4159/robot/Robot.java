
package org.usfirst.frc.team4159.robot;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;


public class Robot extends IterativeRobot {
	
    DriveWheels wheelSet = new DriveWheels(0, 1, 2, 3);
    DrivePistons pistonSet = new DrivePistons(0, 1, 2, 3);
    OctoDrive mainDrive = new OctoDrive(wheelSet, pistonSet);

    Joystick leftStick = new Joystick(1);
    Joystick rightStick = new Joystick(2);
    Joystick secondaryStick = new Joystick(3);
    
    ToteLifter elevator = new ToteLifter(4, 5);
    
    public void robotInit() {
    	mainDrive.octoShift(true);
    	elevator.setHighLow(10, 11);
    }
    
    public void autonomousPeriodic() {

    }

    
    public void teleopPeriodic() {
    	if (leftStick.getRawButton(3)) { //Changes to tank
    		mainDrive.octoShift(false);
    	}
    	if (leftStick.getRawButton(2)) { //Changes to mecanum
    		mainDrive.octoShift(true);
    	}
    	
    	mainDrive.manualDrive(leftStick, rightStick); //Drives according to tank/mecanum
        
    	if (secondaryStick.getRawButton(3)){
    		elevator.autoLift(1.0);          
    	} else if (secondaryStick.getRawButton(2)){
    		elevator.autoLift(-1.0);
    	} else {
    		elevator.autoLift(0.0);
    	}
    	
    }
    
    public void testInit() {
    	
    	
    }
    
    public void testPeriodic() {
    													
    	
    }
    
    public void disabledInit() {                                            //Reset PID
    	
    }
    
}
