package org.TexasTorque.TexasTorque2013.autonomous.elevator;

import org.TexasTorque.TexasTorque2013.autonomous.AutonomousCommand;
import org.TexasTorque.TexasTorque2013.constants.Constants;

public class AutonomousFireElevator extends AutonomousCommand
{
    private boolean hasBeenFalse;
    public AutonomousFireElevator()
    {
        super();
        hasBeenFalse = false;
    }
    public void reset()
    {
        hasBeenFalse = false;
    }
    
    public boolean run()
    {
        if(sensorInput.getLightSensor() == false)
        {
            hasBeenFalse = true;
        }
        elevator.setState(Constants.ELEVATOR_FIRING_STATE);
        
        if (hasBeenFalse && sensorInput.getLightSensor())
        {
            elevator.setState(Constants.ELEVATOR_READY_STATE);
            return true;
        }
        return false;
    }
}
