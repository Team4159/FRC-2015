package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class AutoChooser {
	public static final int NO_MOVE = 0;
	public static final int MOVE_ONLY = 1;                                                   //Values used to tell which auto mode is chosen
	public static final int PICK_ONE_TOTE = 2;
	public static final int PICK_TWO_TOTE_SKIP = 3;
	public static final int PICK_TWO_TOTE_NOSKIP = 4;
	public static final int PICK_THREE_TOTE = 5;
	public static final int NOCONTAINER_TOTE = 6;
	
	private static SendableChooser autoChooser;

	private AutoChooser() {
		
	}
	
	public static void setup() {
		autoChooser = new SendableChooser();                                                //Initializes the autonomous chooser
		autoChooser.addDefault("Do Nothing", new Integer(NO_MOVE));
		autoChooser.addObject("Move Only", new Integer(MOVE_ONLY));                        //Adds the options
//		autoChooser.addObject("1 Tote Pickup", new Integer(PICK_ONE_TOTE));                 //You have to BOX the integers as the parameter for addDefault and addObject
//		autoChooser.addObject("2 Tote Pickup(Skip)", new Integer(PICK_TWO_TOTE_SKIP));      //need objects
//		autoChooser.addObject("2 Tote Pickup(No skip)", new Integer(PICK_TWO_TOTE_NOSKIP));
		autoChooser.addObject("3 Tote Pickup", new Integer(PICK_THREE_TOTE));
//		autoChooser.addObject("Straight Shot 3 Tote Pickup", new Integer(NOCONTAINER_TOTE));
	
	}


	public static int getChoice() {
		return ((Integer) autoChooser.getSelected()).intValue();       //Returns integer value of the chosen autonomus mode UNBOXED
	}
}