package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class AutoMethods {
	private NetworkTable table;
	private OctoDrive mainDrive;
	private ToteLifter mainElevator;
	private DigitalInput toteSensor;
	
	private static final int MOVE_ONLY = 1;
	private static final int PICK_ONE_TOTE = 2;
	private static final int PICK_TWO_TOTE_SKIP = 3;
	private static final int PICK_TWO_TOTE_NOSKIP = 4;
	private static final int PICK_THREE_TOTE = 5;
	private static final int GTFO_TOTE = 6;
	
	double travelTime = 5.0;        //Change based on how the mecanum wheels perform on carpet
	double rejoinRouteTime = 3.0; 
	double liftTime = 1.0;
	
	SendableChooser autoChooser;
	
	public AutoMethods(OctoDrive mainDriveTemp, ToteLifter Elevator) {
		table = NetworkTable.getTable("");
		mainDrive = mainDriveTemp;
		mainElevator = Elevator;
		toteSensor = new DigitalInput(12);
		
		autoChooser = new SendableChooser();
		autoChooser.addDefault("Move Only", new Integer(MOVE_ONLY));
		autoChooser.addObject("1 Tote Pickup", new Integer(PICK_ONE_TOTE));
		autoChooser.addObject("2 Tote Pickup(Skip)", new Integer(PICK_TWO_TOTE_SKIP));
		autoChooser.addObject("2 Tote Pickup(No skip)", new Integer(PICK_TWO_TOTE_NOSKIP));
		autoChooser.addObject("3 Tote Pickup", new Integer(PICK_THREE_TOTE));
		autoChooser.addObject("Straight Shot 3 Tote Pickup", new Integer(GTFO_TOTE));
	}
	
	public void AutoSelected(){
		
	}
	
	public void toteAim() {
		double xOffSet = 0;  //Only for retrieve value purposes
		table.retrieveValue("XOffset", xOffSet);
		while (xOffSet >=-1 &&  xOffSet <= 1) { //Drive right until offset is close to 0 (need a margin)
			mainDrive.autoMecanumDrive(-1.0, 0, 0);
			table.retrieveValue("XOffset", xOffSet);
		}
	}
	
	
	
	public int getChoice() {
		return (((Integer)autoChooser.getSelected()).intValue());
	}
	
	public void toteGet() {
		while(toteSensor.get()){
			mainDrive.autoMecanumDrive(0.0, 1.0, 0.0);
		}
		mainDrive.autoMecanumDrive(0.0, 0.0, 0.0);
	}
	
	public void toteGrabLoop(boolean ifStartingPosition) {
		if (ifStartingPosition==false) {
			mainDrive.autoMecanumDrive(0.0, 1.0, 0.0);
			Timer.delay(travelTime);
			mainDrive.autoMecanumDrive(0.0, 0.0, 0.0);
		}
		toteAim();
		toteGet();
		mainElevator.moveLow();
		mainElevator.autoLift(1.0);
		mainDrive.autoMecanumDrive(1.0, 0.0, 0.0);
		Timer.delay(liftTime);
		mainElevator.autoLift(0.0);
		Timer.delay(rejoinRouteTime-liftTime);
		mainDrive.autoMecanumDrive(0.0, 0.0, 0.0);
	}
	
	
	public void autoLoop() {
		boolean isStage1Enabled;
		boolean isStage2Enabled;
		boolean isStage3Enabled;
		boolean ifGTFO;
		boolean isMoveOnly;
		
		switch(this.getChoice()) {
			case MOVE_ONLY:
				isStage1Enabled=false;
				isStage2Enabled=false;
				isStage3Enabled=false;
				ifGTFO=false;
				isMoveOnly = true;
				break;
			case PICK_ONE_TOTE:
				isStage1Enabled=true;
				isStage2Enabled=false;
				isStage3Enabled=false;
				ifGTFO=false;
				isMoveOnly = false;
				break;
			case PICK_TWO_TOTE_SKIP:
				isStage1Enabled=true;
				isStage2Enabled=false;
				isStage3Enabled=true;
				ifGTFO=false;
				isMoveOnly = false;
				break;
			case PICK_TWO_TOTE_NOSKIP:
				isStage1Enabled=true;
				isStage2Enabled=true;
				isStage3Enabled=false;
				ifGTFO=false;
				isMoveOnly = false;
				break;
			case PICK_THREE_TOTE:
				isStage1Enabled=true;
				isStage2Enabled=true;
				isStage3Enabled=true;
				ifGTFO=false;
				isMoveOnly = false;
				break;
			case GTFO_TOTE:
				isStage1Enabled=false;
				isStage2Enabled=false;
				isStage3Enabled=false;
				ifGTFO=true;
				isMoveOnly = false;
				break;
			default:
				isStage1Enabled=false;
				isStage2Enabled=false;
				isStage3Enabled=false;
				ifGTFO=false;
				isMoveOnly = true;
				break;
		}
		if(isMoveOnly==false) {
			//Add stages
			if (ifGTFO){
				this.toteAim();
				this.toteGet();
				mainElevator.moveLow();
				mainElevator.autoLift(1.0);
				mainDrive.autoMecanumDrive(0.0, 1.0, 0.0);
				Timer.delay(liftTime);
				mainElevator.autoLift(0.0);
				mainDrive.autoMecanumDrive(0.0, 1.0, 0.0);
				Timer.delay(travelTime-liftTime);
				mainDrive.autoMecanumDrive(0.0, 0.0, 0.0);				
			}
		} else {
			mainDrive.autoMecanumDrive(0.0, 1.0, 0.0);
			Timer.delay(3.0);
			mainDrive.autoMecanumDrive(0.0, 0.0, 0.0);
		}
	 
	}
	
}