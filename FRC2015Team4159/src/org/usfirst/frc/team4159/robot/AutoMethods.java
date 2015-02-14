package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class AutoMethods {
	private NetworkTable table;
	private OctoDrive mainDrive;
	private ToteLifter mainElevator;
	
	private static final Integer MOVE_ONLY = 1;
	private static final Integer PICK_ONE_TOTE = 2;
	private static final Integer PICK_TWO_TOTE_SKIP = 3;
	private static final Integer PICK_TWO_TOTE_NOSKIP = 4;
	private static final Integer PICK_THREE_TOTE = 5;
	private static final Integer GTFO_TOTE = 6;
	
	
	SendableChooser autoChooser;
	
	public AutoMethods(OctoDrive mainDriveTemp, ToteLifter Elevator) {
		table = NetworkTable.getTable("");
		mainDrive = mainDriveTemp;
		mainElevator = Elevator;
		
		autoChooser = new SendableChooser();
		autoChooser.addDefault("Move Only", MOVE_ONLY);
		autoChooser.addObject("1 Tote Pickup", PICK_ONE_TOTE);
		autoChooser.addObject("2 Tote Pickup(Skip)", PICK_TWO_TOTE_SKIP);
		autoChooser.addObject("2 Tote Pickup(No skip)", PICK_TWO_TOTE_NOSKIP);
		autoChooser.addObject("3 Tote Pickup", PICK_THREE_TOTE);
		autoChooser.addObject("Straight Shot 3 Tote Pickup", GTFO_TOTE);
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