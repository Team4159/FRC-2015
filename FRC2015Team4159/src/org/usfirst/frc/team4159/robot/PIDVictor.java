package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.Victor;

public class PIDVictor extends Victor {

	private Encoder pidEncoder;
	
	private double Kp;
	private double Ki;
	private double Kd;
  //private double Kf;
	
	private PIDController motorPID;
	
	public PIDVictor(int channel, int portA, int portB) {
		super(channel);
		pidEncoder = new Encoder(portA, portB);
	}
	
	public PIDVictor(int channel, Encoder tempEncoder) {
		super(channel);
		pidEncoder = tempEncoder;
	}
	
	public void setPidValues (double p, double i, double d) {  // (double f) -> may not use feedforward  //Sets PID gains from main method
		Kp = p;
		Ki = i;
		Kd = d;
	//	Kf = f;
				
		motorPID = new PIDController(Kp, Ki, Kd, pidEncoder, this);
	}
	
	public void pidStart(double pulsesPerRevolution, boolean isRate) {  //Check encoders if this is the right count
		double revolutionsPerPulse = 1/ pulsesPerRevolution;
		pidEncoder.setDistancePerPulse(revolutionsPerPulse);
		if (isRate) {
			pidEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
		} else {
			pidEncoder.setPIDSourceParameter(PIDSourceParameter.kAngle);
		}
		
		pidEncoder.reset();
		
		motorPID.enable();
		
	}
	
	public void pidStop() { //KILLS DA PID
		motorPID.reset();
		pidEncoder.reset();
		
	}
	
	public void pidSet(double velocity) { //Scaling method for setpoints - takes the -1.0-1.0 parameters
		//motorPID.setSetpoint((velocity) * 2.5);  //FIX the scaling equation
		motorPID.setSetpoint(velocity); //Input in RPS (10.8 for max speed)
	}
	
}