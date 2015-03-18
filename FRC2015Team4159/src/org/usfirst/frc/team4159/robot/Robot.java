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
 
	
    public void robotInit() {
    	
    }
    
    public void autonomousInit() {
    												//Starts gyro and sets prepares autonomous modes
    	IO.mainGyro.startGyro();
    }
    
    //PROVISIONAL
    Timer testTime;
    
    public void autonomousPeriodic() {
    	
    }
    public void teleopInit() {
    	IO.mainGyro.startGyro();												//Starts gyro, (resets it if it is already on)
    }
    
    public void teleopPeriodic() {
    	//TEST GYRO CODE//
    	SmartDashboard.putNumber("Gyro Z Value", IO.mainGyro.getAngle());
    	SmartDashboard.putNumber("Gyro Pid Value", IO.mainGyro.getPidAngle());
    	//TEST GYRO CODE//
    	if (IO.leftStick.getRawButton(3)) { //Changes to tank
    		IO.mainDrive.octoShift(false);
    	}
    	else if (IO.leftStick.getRawButton(2)) { //Changes to mecanum
    		IO.mainDrive.octoShift(true);
    	}
    	
    	IO.mainDrive.manualDrive(-IO.leftStick.getX(), -IO.leftStick.getY(), 
    			IO.rightStick.getX(), IO.rightStick.getY()); 				  //Drives according to tank/mecanum boolean in OctoDrive
        
    	if (IO.secondaryStick.getRawButton(3)){								  //Moves elevator up
    		IO.elevator.autoLift(1.0);          
    	} 
    	else if (IO.secondaryStick.getRawButton(2)){						  //Moves elevator down
    		IO.elevator.autoLift(-1.0);
    	} else {
    		IO.elevator.autoLift(0.0);										  //Stops elevator if there is no joystick input
    	}
    	
//    	if(lowSensor.get()) { //Some limit switch testing code
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

//=============================================================================================================================//
//AUTONOMOUS//
class autoMethods {
	
	private Timer autoTime;
	
	
	private static double travelTime = 5.0;        //Change based on how the mecanum wheels perform on carpet
	private static double rejoinRouteTime = 3.0; 
	private static double liftTime = 1.0;
	private static double exitTime = 3.0;
	private static double toteDropTime = 1.0;
	
	private static double Kp = 0.0028;				//tune for gyro
	
	public void toteAim() {
		double offset = 0.0;
		IO.imageValues.retrieveValue("XOffset", offset);
		
		while (offset >= -10 && offset <=10) {
			if (offset <= -10) {
				IO.mainDrive.manualDrive(0.5, 0.0, 0.0, 0.0);
			} else if (offset >=10) {
				IO.mainDrive.manualDrive(-0.5, 0.0, 0.0, 0.0);
			}
			IO.imageValues.retrieveValue("XOffset", offset);
		}
		
		IO.mainDrive.manualDrive(0.0, 0.0, 0.0, 0.0);
	}
	
	public void autoStraightDrive(double speed, double durationInSeconds) {
		autoTime.start();
		while (!autoTime.hasPeriodPassed(durationInSeconds)){
			OctoDrive.autoDrive.drive(speed, Kp * -IO.mainGyro.getPidAngle());
		}
		autoTime.stop();
		autoTime.reset();
		OctoDrive.autoDrive.drive(0.0, 0.0);
	}
	
	public void toteGet() {
		while (IO.toteSensor.get()){
			OctoDrive.autoDrive.drive(0.5, Kp * -IO.mainGyro.getPidAngle());
		}
		
		OctoDrive.autoDrive.drive(0.0, 0.0);
	}
	
 	public void toteTimedLift(double liftTime) {
 		IO.elevator.moveLow();
 		autoTime.start();
 		while (!autoTime.hasPeriodPassed(liftTime)) {
 			IO.elevator.autoLift(1.0);
 		}
 		autoTime.stop();
 		autoTime.reset();
 		IO.elevator.autoLift(0.0);
 		
 	}
	 
	public void continuedRoutine() {
		toteAim();
		toteGet();
		toteTimedLift(liftTime);
		IO.mainDrive.manualDrive(0.5, 0.0, 0.0, 0.0);
		Timer.delay(rejoinRouteTime);
		IO.mainDrive.manualDrive(0.0, 0.0, 0.0, 0.0);
		autoStraightDrive(0.5, travelTime);
	}
	
	public void endRouteine() {
		toteAim();
		toteGet();
		toteTimedLift(liftTime);
		IO.mainDrive.manualDrive(0.5, 0.0, 0.0, 0.0);
		Timer.delay(exitTime);
		IO.mainDrive.manualDrive(0.0, 0.0, 0.0, 0.0);
		IO.elevator.moveLow();
		autoStraightDrive(-0.5, toteDropTime);
	}
}
