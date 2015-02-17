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
	
	private static final int MOVE_ONLY = 1;                                                   //Values used to tell which auto mode is chosen
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
		table = NetworkTable.getTable("");                              //Initializes the table under name ""
		mainDrive = mainDriveTemp;										//Gets the octoCanum class to drive
		mainElevator = Elevator;                                        //Gets the elevator class to lift
		toteSensor = new DigitalInput(12);                              //Tells if tote is inside the robot
		
		autoChooser = new SendableChooser();                            //Initializes the autonomous chooser
		autoChooser.addDefault("Move Only", new Integer(MOVE_ONLY));                              //Adds the options
		autoChooser.addObject("1 Tote Pickup", new Integer(PICK_ONE_TOTE));                //You have to BOX the integers as the parameter for addDefault and addObject
		autoChooser.addObject("2 Tote Pickup(Skip)", new Integer(PICK_TWO_TOTE_SKIP));       //need objects
		autoChooser.addObject("2 Tote Pickup(No skip)", new Integer(PICK_TWO_TOTE_NOSKIP));
		autoChooser.addObject("3 Tote Pickup", new Integer(PICK_THREE_TOTE));
		autoChooser.addObject("Straight Shot 3 Tote Pickup", new Integer(GTFO_TOTE));
	}
	
	
	
	public int getChoice() {
		return (((Integer)autoChooser.getSelected()).intValue());       //Returns integer value of the chosen autonomus mode UNBOXED
	}
	

	
	
	
	
	public void autoLoop() {
		boolean isStage1Enabled;                                             //Variables control the behavior of autonomous based on choices
		boolean isStage2Enabled;
		boolean isStage3Enabled;
		boolean ifGTFO;
		boolean isMoveOnly;
		
		switch(this.getChoice()) { //Gets choice from SmartDashboard
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
	 
	}
	
}