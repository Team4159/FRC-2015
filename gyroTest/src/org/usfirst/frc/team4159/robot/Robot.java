
package org.usfirst.frc.team4159.robot;


import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends SampleRobot {
    GyroITG3200 gyro;
    short gyroValueX;
    short gyroValueY;
    short gyroValueZ;
    
    boolean isGyroEnabled;
    
    public Robot() {
    	gyro = new GyroITG3200(I2C.Port.kOnboard);
    	gyro.initialize();
    }

    public void autonomous() {
       
    }

   
    public void operatorControl() {
    	gyro.reset();
    	while (isEnabled( )&& isOperatorControl()){
    		isGyroEnabled = gyro.testConnection();
    		if (isGyroEnabled) {
    			gyroValueX = gyro.getRotationX();
    			gyroValueY = gyro.getRotationY();
    			gyroValueZ = gyro.getRotationZ();
    			SmartDashboard.putNumber("gyroValueX", gyroValueX);
    			SmartDashboard.putNumber("gyroValueY", gyroValueY);
    			SmartDashboard.putNumber("gyroValueZ", gyroValueZ);
    		}
    		SmartDashboard.putBoolean("IsGyroEnabled", isGyroEnabled);
    		Timer.delay(0.20);
    	}
       
        
    }
    
    public void test() {
    }
}
