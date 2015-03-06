package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class DrivePistons{
	DoubleSolenoid piston1;
	DoubleSolenoid piston2;
	
	public DrivePistons(DoubleSolenoid solenoid1, DoubleSolenoid solenoid2, DoubleSolenoid solenoid3, DoubleSolenoid solenoid4) {
		piston1 = solenoid1;
		piston2 = solenoid2;

	}
	
	public DrivePistons(int channel1, int channel2, int channel3, int channel4) {
		piston1 = new DoubleSolenoid(channel1, channel2);
		piston2 = new DoubleSolenoid(channel3, channel4);
	}
	
	public void linearActuate(boolean isExtended){
		if (isExtended) {
			piston1.set(DoubleSolenoid.Value.kForward);
			piston2.set(DoubleSolenoid.Value.kForward);
		
		} else {
			piston1.set(DoubleSolenoid.Value.kReverse);
			piston2.set(DoubleSolenoid.Value.kReverse);
		
		}
		
	}
	
	
}