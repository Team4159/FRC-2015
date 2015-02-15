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
	
	public void toteAim() {
		double xOffSet = 0;  //Only for retrieve value purposes
		table.retrieveValue("XOffset", xOffSet);                         //Gets initial values
		while (xOffSet >=-1 &&  xOffSet <= 1) {                          //Drive right until offset is close to 0 (need a margin)
			mainDrive.autoMecanumDrive(-1.0, 0, 0);                        
			table.retrieveValue("XOffset", xOffSet);                     //ReUpdates to keep track of progress
		}
	}
	
	
	
	public int getChoice() {
		return (((Integer)autoChooser.getSelected()).intValue());       //Returns integer value of the chosen autonomus mode UNBOXED
	}
	
	public void toteGet() {
		while(toteSensor.get()){
			mainDrive.autoMecanumDrive(0.0, 1.0, 0.0);                  //Drives forward until tote is sufficiently inside the drivetrain (back will hit limit switch)
		}
		mainDrive.autoMecanumDrive(0.0, 0.0, 0.0);
	}
	
	public void toteGrabLoop(boolean ifStartingPosition) {              //This is the loop that will aim, grab tote, and drive to be ready for next loop
		if (ifStartingPosition==false) {
			mainDrive.autoMecanumDrive(0.0, 1.0, 0.0); 					//If the robot is not in the starting position, it will drive forward to acquire the next tote
			Timer.delay(travelTime);                   //Drives forward seconds given by variable travelTime (initialized at the top)
			mainDrive.autoMecanumDrive(0.0, 0.0, 0.0);
		}
		toteAim(); //Aims for tote
		toteGet(); //Drives so that tote is completely inside drivetrain
		mainElevator.moveLow(); //Moves the elevator to lowest position
		mainElevator.autoLift(1.0); //Starts lifting tote and driving
		mainDrive.autoMecanumDrive(1.0, 0.0, 0.0);
		Timer.delay(liftTime);
		mainElevator.autoLift(0.0); //Stops lifting tote according to how long liftTime is (initialized at top)
		Timer.delay(rejoinRouteTime-liftTime); //Continues driving according to rejoinRouteTime (initialized at top)
		mainDrive.autoMecanumDrive(0.0, 0.0, 0.0); //Stops for next loop
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
		if(isMoveOnly==false) { //If the robot is not selected as move_only, then it will undergo the stages
			if (isStage1Enabled) {
				toteGrabLoop(true);
				isStage1Enabled=false; //set to false so that stage1 won't be repeated a second time
			} else if(isStage2Enabled){
				toteGrabLoop(false);
				isStage2Enabled=false;
			} else if(isStage3Enabled){
				toteGrabLoop(false);
				isStage3Enabled=false; 
			} else {                 
				mainDrive.autoMecanumDrive(0.0, 1.0, 0.0);
				Timer.delay(travelTime);  //This code will move the robot forward enough to skip the process of grabbing the tote
				mainDrive.autoMecanumDrive(0.0, 0.0, 0.0);
			}
			if (ifGTFO){ //Code for a straight shot with the containers out of the way
				this.toteAim(); 									//aims at tote
				this.toteGet(); 									//acquires tote
				mainElevator.moveLow(); 							//sets elevator to lowest position
				mainElevator.autoLift(1.0); 						//starts lifting
				mainDrive.autoMecanumDrive(0.0, 1.0, 0.0); 			//starts driving
				Timer.delay(liftTime); 								//delays lifting times
				mainElevator.autoLift(0.0); 						//Stops lifting (tote is sufficiently lifted)
				mainDrive.autoMecanumDrive(0.0, 1.0, 0.0); 			//Continues driving
				Timer.delay(travelTime-liftTime); 					//Continues driving for the value of travelTime (liftTime is compensated for)
				mainDrive.autoMecanumDrive(0.0, 0.0, 0.0); 			//Stops for next loop				
			}
		} else {
			mainDrive.autoMecanumDrive(0.0, 1.0, 0.0); //Just moves the robot to the autozone (if move_only is selected)
			Timer.delay(3.0);
			mainDrive.autoMecanumDrive(0.0, 0.0, 0.0);
		}
	 
	}
	
}