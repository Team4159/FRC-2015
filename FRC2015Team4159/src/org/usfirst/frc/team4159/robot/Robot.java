package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
//	
//    DriveWheels wheelSet = new DriveWheels(0, 1, 2, 3);
//    DrivePistons pistonSet = new DrivePistons(0, 1, 2, 3);
//    OctoDrive mainDrive = new OctoDrive(wheelSet, pistonSet);
//
//    Joystick leftStick = new Joystick(1);
//    Joystick rightStick = new Joystick(2);
//    Joystick secondaryStick = new Joystick(3);
//    
//    Victor leftLifter = new Victor(4);
//    Victor rightLifter = new Victor(5);
//    ToteLifter elevator = new ToteLifter(leftLifter, rightLifter);
//    
//    GyroITG3200 mainGyro = new GyroITG3200(I2C.Port.kOnboard);
//    gyroSampler averageGyro = new gyroSampler(mainGyro, 25, 10);
//    GyroManager gyroInterfacer = new GyroManager(averageGyro);
//    boolean ifFirstLoop;
//    
////    DigitalOutput testLED =  new DigitalOutput(0);
//    
//    DigitalInput lowSensor = new DigitalInput(8);
//    DigitalInput topSensor = new DigitalInput(9);
//    
    
	boolean isFirstAutoLoop;
	
    public void robotInit() {
    	
    }
    
    public void autonomousInit() {
    	isFirstAutoLoop = true;
    	IO.mainGyro.startGyro();
    }
    
    public void autonomousPeriodic() {
    	if (isFirstAutoLoop) {
    		AutoMethods.instance.autoStraightDrive(0.5, 3);
    		
    		isFirstAutoLoop = false;
    	}
    }
    public void teleopInit() {
    	IO.mainGyro.startGyro();
    }
    
    public void teleopPeriodic() {
    	//TEST GYRO CODE//
    	SmartDashboard.putNumber("Gyro Z Value", IO.mainGyro.getAngle());
    	//TEST GYRO CODE//
    	if (IO.leftStick.getRawButton(3)) { //Changes to tank
    		IO.mainDrive.octoShift(false);
    	}
    	else if (IO.leftStick.getRawButton(2)) { //Changes to mecanum
    		IO.mainDrive.octoShift(true);
    	}
    	
    	IO.mainDrive.manualDrive(IO.leftStick.getX(), IO.leftStick.getY(), IO.rightStick.getX(), IO.rightStick.getY()); //Drives according to tank/mecanum
        
    	if (IO.secondaryStick.getRawButton(3)){
    		IO.elevator.autoLift(1.0);          
    	} 
    	else if (IO.secondaryStick.getRawButton(2)){
    		IO.elevator.autoLift(-1.0);
    	} else {
    		IO.elevator.autoLift(0.0);
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
    
    public void disabledInit() {
    	
    	
    }
}
