package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class AutoMethods {

	public static AutoMethods instance = new AutoMethods();
	
	private static final int MOVE_ONLY = 1;                                                   //Values used to tell which auto mode is chosen
	private static final int PICK_ONE_TOTE = 2;
	private static final int PICK_TWO_TOTE_SKIP = 3;
	private static final int PICK_TWO_TOTE_NOSKIP = 4;
	private static final int PICK_THREE_TOTE = 5;
	private static final int NOCONTAINER_TOTE = 6;
	
	private static Timer autoTime;
	
	private double travelTime = 5.0;        //Change based on how the mecanum wheels perform on carpet
	private double rejoinRouteTime = 3.0; 
	private double liftTime = 1.0;
	private double Kp = 0.3;				//tune for gyro
	
	SendableChooser autoChooser;
	
	public AutoMethods() {
		autoChooser = new SendableChooser();                            					//Initializes the autonomous chooser
		autoChooser.addDefault("Move Only", new Integer(MOVE_ONLY));                        //Adds the options
		autoChooser.addObject("1 Tote Pickup", new Integer(PICK_ONE_TOTE));                 //You have to BOX the integers as the parameter for addDefault and addObject
		autoChooser.addObject("2 Tote Pickup(Skip)", new Integer(PICK_TWO_TOTE_SKIP));      //need objects
		autoChooser.addObject("2 Tote Pickup(No skip)", new Integer(PICK_TWO_TOTE_NOSKIP));
		autoChooser.addObject("3 Tote Pickup", new Integer(PICK_THREE_TOTE));
		autoChooser.addObject("Straight Shot 3 Tote Pickup", new Integer(NOCONTAINER_TOTE));
	}
	
	
	
	public int getChoice() {
		return (((Integer) autoChooser.getSelected()).intValue());       //Returns integer value of the chosen autonomus mode UNBOXED
	}
	

	public void toteAim() {
		double offset = 0.0;
		IO.imageValues.retrieveValue("XOffset", offset);
		
		while (offset >= 0 && offset <=10) {
			IO.mainDrive.manualDrive(-0.5, 0.0, 0.0, 0.0);
			IO.imageValues.retrieveValue("XOffset", offset);
		}
		
		IO.mainDrive.manualDrive(0.0, 0.0, 0.0, 0.0);
	}
	
	public void autoStraightDrive(double speed, double durationInSeconds) {
		autoTime.start();
		while (!autoTime.hasPeriodPassed(durationInSeconds)){
			OctoDrive.autoDrive.drive(speed, Kp * -IO.mainGyro.getAngle());
		}
		autoTime.stop();
		autoTime.reset();
		OctoDrive.autoDrive.drive(0.0, 0.0);
	}
	
	public void toteGet() {
		while (IO.toteSensor.get()){
			OctoDrive.autoDrive.drive(0.5, Kp * -IO.mainGyro.getAngle());
		}
		
		OctoDrive.autoDrive.drive(0.0, 0.0);
	}
	
 	public void toteSingleLift() {
 		IO.elevator.moveLow();
 		autoTime.start();
 		while (!autoTime.hasPeriodPassed(1.0)) {
 			IO.elevator.autoLift(1.0);
 		}
 		autoTime.stop();
 		autoTime.reset();
 		IO.elevator.autoLift(0.0);
 		
 	}
	 
	
	
}