package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

public class Robot extends IterativeRobot {
	
    DriveWheels wheelSet = new DriveWheels(0, 1, 2, 3);
    DrivePistons pistonSet = new DrivePistons(0, 1, 2, 3);
    Gyro gyro = new Gyro(0);
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
    
    Double gyroAngle;
    Double Kp = 0.3;
    
    public void robotInit() {
    	mainDrive.octoShift(true);
    	elevator.setHighLow(lowSensor, topSensor);
    	mainDrive.invertMotor("rearRight", true);
    	mainDrive.invertMotor("frontRight", true);
    	mainDrive.invertMotor("leftSide", true);
    }
    
    public void autonomousInit() {
    	gyro.reset();
    }
    
    public void autonomousPeriodic() {
    	gyroAngle = gyro.getAngle();
    	mainDrive.notMainDrive.drive(0.5, -gyroAngle*Kp);
    }

    public void teleopInit() {
    	
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
    }	
    public void disabledInit() {                                            //Reset PID
    }
}
