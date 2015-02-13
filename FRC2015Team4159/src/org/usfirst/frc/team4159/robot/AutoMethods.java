package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class AutoMethods {
	private NetworkTable table;
	private OctoDrive mainDrive;
	private ToteLifter mainElevator;
	
	public AutoMethods(OctoDrive mainDriveTemp, ToteLifter Elevator) {
		table = NetworkTable.getTable("");
		mainDrive = mainDriveTemp;
		mainElevator = Elevator;
	}
	
	public void AutoSelected(){
		
	}
	
	public void toteAim() {
		double xOffSet = 0;  //Only for retrieve value purposes
		table.retrieveValue("XOffset", xOffSet);
		while (xOffSet >=-1 &&  xOffSet <= 1) { //Drive right until offset is close to 0 (need a margin)
			mainDrive.autoMecanum(-1.0, 0, 0);
			table.retrieveValue("XOffset", xOffSet);
		}
	}
}