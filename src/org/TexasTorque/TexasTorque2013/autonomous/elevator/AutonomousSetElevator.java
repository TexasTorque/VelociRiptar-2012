package org.TexasTorque.TexasTorque2013.autonomous.elevator;

import org.TexasTorque.TexasTorque2013.autonomous.AutonomousCommand;
import org.TexasTorque.TexasTorque2013.constants.Constants;

public class AutonomousSetElevator extends AutonomousCommand
{
    private int elevatorState;
    
    public AutonomousSetElevator()
    {
        super();
        elevatorState = Constants.ELEVATOR_READY_STATE;
    }
    public AutonomousSetElevator(int state)
    {
        elevatorState = state;
    }
    
    public void reset()
    {
    }
    
    public boolean run()
    {
        elevator.setState(elevatorState);
        return true;
    }
}
