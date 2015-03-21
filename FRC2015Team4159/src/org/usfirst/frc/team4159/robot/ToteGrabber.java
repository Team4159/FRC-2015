package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.Victor;

public class ToteGrabber {
	Victor leftGrabber;
	Victor rightGrabber;
	
	
	public ToteGrabber(Victor tmp_leftGrab, Victor tmp_rightGrab) {
		leftGrabber = tmp_leftGrab;
		rightGrabber = tmp_rightGrab;
	}
	
	public ToteGrabber(int leftChannel, int rightChannel) {
		leftGrabber = new Victor(leftChannel);
		rightGrabber = new Victor(rightChannel);
	}
	
	public void toteGrab(double velocity) {
		leftGrabber.set(-velocity);
		rightGrabber.set(velocity);
	}
	
}