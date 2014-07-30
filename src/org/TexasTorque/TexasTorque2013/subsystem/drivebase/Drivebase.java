package org.TexasTorque.TexasTorque2013.subsystem.drivebase;

import org.TexasTorque.TexasTorque2013.TorqueSubsystem;
import org.TexasTorque.TexasTorque2013.constants.Constants;
import org.TexasTorque.TorqueLib.util.TorqueToggle;
import org.TexasTorque.TorqueLib.util.TorqueUtil;

public class Drivebase extends TorqueSubsystem
{   
    private static Drivebase instance;
    
    private double leftDriveSpeed;
    private double rightDriveSpeed;
    
    private boolean shiftState;
    private TorqueToggle brakeState;
    
    public static Drivebase getInstance()
    {
        return (instance == null) ? instance = new Drivebase() : instance;
    }
            
    private Drivebase()
    {
        super();
        
        leftDriveSpeed = Constants.MOTOR_STOPPED;
        rightDriveSpeed = Constants.MOTOR_STOPPED;
        
        shiftState = Constants.LOW_GEAR;
        brakeState = new TorqueToggle();
    }
    
    public void run()
    {
        if(dashboardManager.getDS().isOperatorControl())
        {
           mixChannels(driverInput.getThrottle(), driverInput.getTurn());
           shiftState = driverInput.shiftHighGear();
           brakeState.calc(driverInput.getBrakesToggle());
        }
    }
    
    public void setToRobot()
    {
        robotOutput.setShifters(shiftState);
        robotOutput.setBrakes(brakeState.get());
        robotOutput.setDriveMotors(leftDriveSpeed, rightDriveSpeed);
    }
    
    public void setDriveSpeeds(double leftSpeed, double rightSpeed)
    {
        leftDriveSpeed = leftSpeed;
        rightDriveSpeed = rightSpeed;
    }
    
    public void setShifters(boolean highGear)
    {
        if(highGear != shiftState)
        {
            shiftState = highGear;
        }
    }
    
    private void mixChannels(double yAxis, double xAxis)
    {
        yAxis = TorqueUtil.applyDeadband(yAxis, Constants.SPEED_AXIS_DEADBAND);
        xAxis = TorqueUtil.applyDeadband(xAxis, Constants.TURN_AXIS_DEADBAND);
        
        simpleDrive(yAxis, xAxis);
    }
    
    private void simpleDrive(double yAxis, double xAxis)
    {
        yAxis = TorqueUtil.sqrtHoldSign(yAxis);
        xAxis = TorqueUtil.sqrtHoldSign(xAxis);
        
        double leftSpeed = yAxis + xAxis;
        double rightSpeed = yAxis - xAxis;
        
        setDriveSpeeds(leftSpeed, rightSpeed);
    }
    
    public String getKeyNames()
    {
        String names = "LeftDriveSpeed,LeftDriveEncoderPosition,LeftDriveEncoderVelocity,"
                + "RightDriveSpeed,RightDriveEncoderPosition,RightDriveEncoderVelocity,"
                + "GyroAngle,ShiftState";
        
        return names;
    }
    
    public String logData()
    {
        String data = leftDriveSpeed + ",";
        data += sensorInput.getLeftDriveEncoder() + ",";
        data += sensorInput.getLeftDriveEncoderRate() + ",";
        
        data += rightDriveSpeed + ",";
        data += sensorInput.getRightDriveEncoder() + ",";
        data += sensorInput.getRightDriveEncoderRate() + ",";
        
        data += sensorInput.getGyroAngle() + ",";
        data += shiftState + ",";
        
        return data;
    }
    
    public void loadParameters()
    {   
        
    }
}
