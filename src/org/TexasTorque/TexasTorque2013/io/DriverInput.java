package org.TexasTorque.TexasTorque2013.io;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.TexasTorque.TexasTorque2013.constants.Constants;
import org.TexasTorque.TexasTorque2013.constants.Ports;
import org.TexasTorque.TorqueLib.util.GenericController;
import org.TexasTorque.TorqueLib.util.Parameters;

public class DriverInput
{
    private static DriverInput instance;
    private Parameters params;
    public GenericController driveController;
    private GenericController operatorController;
    
    private boolean inOverrideState;
    private boolean inHighGear;
        
    public DriverInput()
    {
        params = Parameters.getTeleopInstance();
        driveController = new GenericController(Ports.DRIVE_CONTROLLER_PORT, Constants.DEFAULT_FIRST_CONTROLLER_TYPE);
        operatorController = new GenericController(Ports.OPERATOR_CONTROLLER_PORT, Constants.DEFAULT_SECOND_CONTROLLER_TYPE);
        
        inOverrideState = false;
        inHighGear = false;
    }
    
    public synchronized static DriverInput getInstance()
    {
        return (instance == null) ? instance = new DriverInput() : instance;
    }
    
    public synchronized void pullJoystickTypes()
    {
        boolean firstControllerType = SmartDashboard.getBoolean("firstControllerIsLogitech", Constants.DEFAULT_FIRST_CONTROLLER_TYPE);
        boolean secondControllerType = SmartDashboard.getBoolean("secondControllerIsLogitech", Constants.DEFAULT_SECOND_CONTROLLER_TYPE);
        
        if(firstControllerType)
        {
            driveController.setAsLogitech();
        }
        else
        {
            driveController.setAsXBox();
        }
        
        if(secondControllerType)
        {
            operatorController.setAsLogitech();
        }
        else
        {
            operatorController.setAsXBox();
        }
    }
    
    public synchronized double getAutonomousDelay()
    {
        return SmartDashboard.getNumber("Autonomous Delay", 0.0);
    }
    
    public synchronized int getAutonomousMode()
    {
        return (int) SmartDashboard.getNumber("AutonomousMode", Constants.DO_NOTHING_AUTO);
    }
    
    public synchronized boolean resetSensors()
    {
        return operatorController.getBottomActionButton();
    }
    
//---------- Drivebase ----------
    
    public synchronized double getThrottle()
    {
        return -1 * driveController.getLeftYAxis();
    }
    
    public synchronized double getTurn()
    {
        return driveController.getRightXAxis();
    }
    
    public synchronized boolean shiftHighGear()
    {
        return driveController.getTopLeftBumper();
    }
    
    public synchronized boolean hasInput()
    {
        return (Math.abs(getThrottle())>.07 || Math.abs(getTurn())>.07);
    }
    
//---------- Manipulator ----------    
    
    public synchronized boolean runIntake()
    {
        return operatorController.getTopLeftBumper();
    }
    
    public synchronized boolean resetIndexingState()
    {
        return operatorController.getLeftActionButton();
    }
    
    public synchronized boolean reverseIntake()
    {
        return operatorController.getTopRightBumper();
    }
    
    public synchronized boolean shootFar()
    {
        return operatorController.getBottomActionButton();
    }
    
    public synchronized boolean shootClose()
    {
        return operatorController.getTopActionButton();
    }
    
    public synchronized boolean fireBasketball()
    {
        return operatorController.getBottomRightBumper();
    }
    
    public synchronized boolean toggleCross()
    {
        return operatorController.getBottomLeftBumper();
    }
    
    public synchronized boolean getBrakesToggle()
    {
        return operatorController.getRightStickClick();
    }
    
//---------- Overrides ----------
    
    public synchronized boolean overrideState()
    {
        if(operatorController.getLeftCenterButton())
        {
            inOverrideState = true;
        }
        else if(operatorController.getRightCenterButton())
        {
            inOverrideState = false;
        }
        
        return inOverrideState;
    }
    
    public synchronized boolean intakeOverride()
    {
        return operatorController.getTopLeftBumper();
    }
    
    public synchronized boolean outtakeOverride()
    {
        return operatorController.getTopRightBumper();
    }
    
    public synchronized boolean shootFarOverride()
    {
        return operatorController.getBottomActionButton();
    }
    
    public synchronized boolean shootCloseOverride()
    {
        return operatorController.getTopActionButton();
    }
    
    public synchronized boolean elevatorTopOverride()
    {
        return (operatorController.getLeftYAxis() < -0.5);
    }
    
    public synchronized boolean elevatorBottomOverride()
    {
        return (operatorController.getLeftYAxis() > 0.5);
    }
    
    public synchronized double getElevatorJoystick()
    {
        return (operatorController.getLeftYAxis());
    }
}
