package org.TexasTorque.TexasTorque2013.subsystem.manipulator;

import org.TexasTorque.TexasTorque2013.TorqueSubsystem;
import org.TexasTorque.TexasTorque2013.constants.Constants;
import org.TexasTorque.TorqueLib.controlLoop.TorquePID;

public class Shooter extends TorqueSubsystem
{   
    private static Shooter instance;
    
    private TorquePID shooterPID;
    
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
        
        shooterPID = new TorquePID();
        
        shooterMotorSpeed = Constants.MOTOR_STOPPED;
        desiredShooterRate = Constants.SHOOTER_STOPPED_RATE;
        highShot = false;
        lowShot = true;
        hoodCurrentState = lowShot;
    }
    
    public void run()
    {
        double shooterSpeed = shooterPID.calculate(sensorInput.getShooterRate());
        
        shooterMotorSpeed = limitShooterSpeed(shooterSpeed);
        
        if(desiredShooterRate == Constants.SHOOTER_STOPPED_RATE)
        {
            shooterMotorSpeed = Constants.MOTOR_STOPPED;
        }
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
            shooterPID.setSetpoint(desiredShooterRate);
        }
    }
    
    public void stopShooter()
    {
        setShooterRates(Constants.SHOOTER_STOPPED_RATE);
    }
    
    public boolean isSpunUp()
    {
        return (shooterPID.isDone());
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

        double p = params.getAsDouble("S_ShooterP", 0.0);
        double d = params.getAsDouble("S_ShooterD", 0.0);
        double ff = params.getAsDouble("S_ShooterKV", 0.0);
        double r = params.getAsDouble("S_ShooterDoneRange", 300.0);
        
        shooterPID.setPIDGains(p, 0.0, d);
        shooterPID.setFeedForward(ff);
        shooterPID.setDoneRange(r);
        shooterPID.setMinDoneCycles(1);
        shooterPID.reset();
        
    }
    
}
