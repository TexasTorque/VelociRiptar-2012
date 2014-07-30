package org.TexasTorque.TexasTorque2013.autonomous;

import org.TexasTorque.TexasTorque2013.constants.Constants;
import org.TexasTorque.TexasTorque2013.subsystem.drivebase.Drivebase;
import org.TexasTorque.TexasTorque2013.subsystem.manipulator.Elevator;
import org.TexasTorque.TexasTorque2013.subsystem.manipulator.Intake;
import org.TexasTorque.TexasTorque2013.subsystem.manipulator.Shooter;
import org.TexasTorque.TorqueLib.util.Parameters;

public class AutonomousManager
{
    private AutonomousBuilder autoBuilder;
    
    private AutonomousCommand[] autoList;
    
    private Drivebase drivebase;
    private Intake intake;
    private Elevator elevator;
    private Shooter shooter;
    private Parameters params;
    
    private int autoMode;
    private double autoDelay;
    private int currentIndex;
    private boolean firstCycle;
    private boolean loaded;
    
    public AutonomousManager()
    {
        autoBuilder = new AutonomousBuilder();
        
        autoList = null;
        
        drivebase = Drivebase.getInstance();
        intake = Intake.getInstance();
        elevator = Elevator.getInstance();
        shooter = Shooter.getInstance();
        params = Parameters.getTeleopInstance();
        
        autoMode = Constants.DO_NOTHING_AUTO;
        autoDelay = 0.0;
        currentIndex = 0;
        firstCycle = true;
        loaded = false;
    }
    
    public void reset()
    {
        loaded = false;
    }
    
    public void setAutoMode(int mode)
    {
        autoMode = mode;
    }
    
    public void addAutoDelay(double delay)
    {
        autoDelay = delay;
    }
    
    public void loadAutonomous()
    {
        switch(autoMode)
        {
            case Constants.DO_NOTHING_AUTO:
                doNothingAuto();
                break;
            default:
                doNothingAuto();
                break;
        }
        
        firstCycle = true;
        currentIndex = 0;
        autoList = autoBuilder.getAutonomousList();
        loaded = true;
    }
    
    public void runAutonomous()
    {
        if(loaded)
        {
            if(firstCycle)
            {
                firstCycle = false;
                autoList[currentIndex].reset();
            }

            boolean commandFinished = autoList[currentIndex].run();

            if(commandFinished)
            {
                currentIndex++;
                autoList[currentIndex].reset();
            }

            drivebase.run();
            intake.run();
            elevator.run();
            shooter.run();
        }
    }
    
    public void doNothingAuto()
    {
        autoBuilder.clearCommands();
    }
}
