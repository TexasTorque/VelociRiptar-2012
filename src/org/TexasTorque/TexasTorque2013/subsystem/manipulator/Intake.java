package org.TexasTorque.TexasTorque2013.subsystem.manipulator;

import org.TexasTorque.TexasTorque2013.TorqueSubsystem;
import org.TexasTorque.TexasTorque2013.constants.Constants;

public class Intake extends TorqueSubsystem
{
    private static Intake instance;
    
    private double intakeMotorSpeed;
    private boolean crossPosition;
    
    public static double intakeSpeed;
    public static double outtakeSpeed;
   
    public static Intake getInstance()
    {
        return (instance == null) ? instance = new Intake() : instance;
    }
    
    private Intake()
    {
        super();
        intakeMotorSpeed = Constants.MOTOR_STOPPED;
        crossPosition = Constants.CROSS_UP;
    }
    
    public void run()
    {
    }
    
    public void setToRobot()
    {
        robotOutput.setIntakeMotor(intakeMotorSpeed);
        robotOutput.setIntakePneumatic(crossPosition);
        if(crossPosition == Constants.CROSS_DOWN)
        {
            robotOutput.setCrossMotor(intakeMotorSpeed);
        }
        else
        {
            robotOutput.setCrossMotor(Constants.MOTOR_STOPPED);
        }
    }
    
    public void setIntakeSpeed(double speed)
    {
        intakeMotorSpeed = speed;
    }
    
    public void setCross(boolean position)
    {
        crossPosition = position;
    }
    
    public String getKeyNames()
    {
        String names = "IntakeMotorSpeed,";
        
        return names;
    }
    
     public String logData()
    {
        String data = intakeMotorSpeed + ",";
        
        return data;
    }
    
    public void loadParameters()
    {
        intakeSpeed = params.getAsDouble("I_IntakeSpeed", 1.0);
        outtakeSpeed = params.getAsDouble("I_OuttakeSpeed", -1.0);
    }
}
