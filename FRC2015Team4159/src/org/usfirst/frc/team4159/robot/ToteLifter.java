package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
//Hopefully PID-free class for the toteLifter
public class ToteLifter {
	Victor leftLifter;
	Victor rightLifter;
	
	DigitalInput lowLimit;
	DigitalInput topLimit;
	
	
	public ToteLifter(Victor leftMotor, Victor rightMotor) { //Motor inputs are declared
		leftLifter = leftMotor;
		rightLifter = rightMotor;
		
		
	}
	public ToteLifter(int leftChannel, int rightChannel){
		leftLifter = new Victor(leftChannel);
		rightLifter = new Victor(rightChannel);
		
		
	}
	
	public void manualLift(double velocity) {               //Moves the elevator WITHOUT regards to the touch sensors
		leftLifter.set(-velocity);							//WARNING: Motors can overshoot
		rightLifter.set(velocity);
	}
	
	public void setHighLow(DigitalInput lowSensor, DigitalInput topSensor) {
		lowLimit = lowSensor;                               //Sensor input objects passed over to be set
		topLimit = topSensor;
		
	
	}
	
	public void setHighLow(int lowChannel, int topChannel) {
		lowLimit = new DigitalInput(lowChannel);            //Sensor input objects declared based on channels
		topLimit = new DigitalInput(topChannel);
		
	}
	
	public void autoLift(double velocity) {                 //Takes input from sensors to see if they are pressed
			if ((lowLimit.get() == false) && velocity < 0) {
				manualLift(0.0);
			} if((topLimit.get() == false) && velocity > 0) {
				manualLift(0.0);
			} if(velocity == 0.0) {
				manualLift(0.0);
			} else {
				manualLift(velocity);
			}
	}
	
}
	