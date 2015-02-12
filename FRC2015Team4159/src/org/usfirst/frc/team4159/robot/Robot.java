
package org.usfirst.frc.team4159.robot;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;


public class Robot extends IterativeRobot {
	
    DriveWheels wheelSet = new DriveWheels(0, 1, 2, 3);
    DrivePistons pistonSet = new DrivePistons(0, 1, 2, 3);
    OctoDrive mainDrive = new OctoDrive(wheelSet, pistonSet);

    Joystick leftStick = new Joystick(1);
    Joystick rightStick = new Joystick(2);
    Joystick secondaryStick = new Joystick(3);
    
    
    Victor leftLifter = new Victor(4);
    Victor rightLifter = new Victor(5);
    ToteLifter elevator = new ToteLifter(leftLifter, rightLifter);
    
//    DigitalOutput testLED =  new DigitalOutput(0);
    
    DigitalInput lowSensor = new DigitalInput(8);
    DigitalInput topSensor = new DigitalInput(9);
    
    public void robotInit() {
    	mainDrive.octoShift(true);
    	elevator.setHighLow(lowSensor, topSensor);
    }
    
    public void autonomousPeriodic() {

    }

    
    public void teleopPeriodic() {
    	System.out.println("teleop Looped!");
    	if (leftStick.getRawButton(3)) { //Changes to tank
    		mainDrive.octoShift(false);
    	}
    	else if (leftStick.getRawButton(2)) { //Changes to mecanum
    		mainDrive.octoShift(true);
    	}
    	
    	mainDrive.manualDrive(leftStick, rightStick); //Drives according to tank/mecanum
        
    	if (secondaryStick.getRawButton(3)){
    		elevator.autoLift(1.0);          
    	} 
    	else if (secondaryStick.getRawButton(2)){
    		elevator.autoLift(-1.0);
    	} else {
    		elevator.autoLift(0.0);
    	}
    	
//    	if(lowSensor.get()) {
//    		testLED.set(true);
//    	}  else {
//    		testLED.set(false);
//    	}
    	
    }
    
    public void testInit() {
    	
    	
    }
    
    public void testPeriodic() {
    	if (topSensor.get() == false) {          //limit switch tester
    		elevator.manualLift(1.0);
    	} else if (lowSensor.get() == false) {
    		elevator.manualLift(-1.0);
    	} else {
    		elevator.manualLift(0.0);
    	}
    	
    }
    
    public void disabledInit() {                                            //Reset PID
    	
    }
    
}
