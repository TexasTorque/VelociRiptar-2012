package org.TexasTorque.TexasTorque2013.subsystem.manipulator;

import org.TexasTorque.TexasTorque2013.TorqueSubsystem;
import org.TexasTorque.TexasTorque2013.constants.Constants;
import org.TexasTorque.TorqueLib.controlLoop.FlywheelController;

public class Shooter extends TorqueSubsystem
{   
    private static Shooter instance;
    
    private FlywheelController shooterController;
    
    private double shooterMotorSpeed;
    private double desiredShooterRate;
    private boolean hoodCurrentState;
    public static double shooterRate;
    public static boolean highShot;
    public static boolean lowShot;
    
    public static Shooter getInstance()
    {
        return (instance == null) ? instance = new Shooter() : instance;
    }
    
    private Shooter()
    {
        super();
        
        shooterController = new FlywheelController("shooter",Constants.SHOOTER_GAINS, .004);
        
        shooterMotorSpeed = Constants.MOTOR_STOPPED;
        desiredShooterRate = Constants.SHOOTER_STOPPED_RATE;
        highShot = false;
        lowShot = true;
        hoodCurrentState = lowShot;
    }
    
    public void run()
    {
        shooterMotorSpeed = shooterController.update(sensorInput.getShooterRate());
    }
    
    public void setToRobot()
    {
        robotOutput.setShooterMotors(shooterMotorSpeed);
        robotOutput.setHood(hoodCurrentState);
    }
    
    public void setShooterRates(double shooterRate)
    {
        if(shooterRate != desiredShooterRate)
        {
            desiredShooterRate = shooterRate;
            shooterController.setVelocityGoal(desiredShooterRate);
        }
    }
    
    public void stopShooter()
    {
        setShooterRates(Constants.SHOOTER_STOPPED_RATE);
    }
    
    public boolean isSpunUp()
    {
        return (shooterController.isDoneRaw());
    }
    
    public void setHoodState(boolean desired)
    {
        hoodCurrentState = desired;
    }
    
    private double limitShooterSpeed(double shooterSpeed)
    {
        if(shooterSpeed < 0.0)
        {
            return Constants.MOTOR_STOPPED;
        }
        else
        {
            return shooterSpeed;
        }
    }
    
    public String getKeyNames()
    {
        String names = "DesiredShooterRate,ShooterMotorSpeed,FrontShooterRate,";
        
        return names;
    }
    
    public String logData()
    {
        String data = desiredShooterRate + ",";
        data += shooterMotorSpeed + ",";
        data += sensorInput.getShooterRate() + ",";
        
        return data;
    }
    
    public void loadParameters()
    {   
        shooterRate = params.getAsDouble("S_ShooterRate", Constants.DEFAULT_SHOOTER_RATE);

        double r = params.getAsDouble("S_ShooterDoneRange", 300.0);
        shooterController.setDoneRange(r);
    }
    
}
