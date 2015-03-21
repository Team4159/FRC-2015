package org.usfirst.frc.team4159.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class AutoChooser {
	private static final int MOVE_ONLY = 1;                                                   //Values used to tell which auto mode is chosen
//	private static final int PICK_ONE_TOTE = 2;
//	private static final int PICK_TWO_TOTE_SKIP = 3;
//	private static final int PICK_TWO_TOTE_NOSKIP = 4;
	private static final int PICK_THREE_TOTE = 5;
//	private static final int NOCONTAINER_TOTE = 6;
	
	static SendableChooser autoChooser;

	private AutoChooser() {
		
	}
	
	public static void setUpAuto() {
		autoChooser = new SendableChooser();                            					//Initializes the autonomous chooser
		autoChooser.addDefault("Move Only", new Integer(MOVE_ONLY));                        //Adds the options
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