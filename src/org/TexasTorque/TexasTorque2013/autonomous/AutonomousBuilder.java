package org.TexasTorque.TexasTorque2013.autonomous;

import java.util.Vector;
import org.TexasTorque.TexasTorque2013.autonomous.util.AutonomousWait;

public class AutonomousBuilder
{
    private Vector commands;
    
    public AutonomousBuilder()
    {
        commands = new Vector();
    }
    
    public void addCommand(AutonomousCommand cmd)
    {
        commands.addElement(cmd);
    }
    
    public AutonomousCommand[] getAutonomousList()
    {
        System.err.println("getAutonomousList");
        AutonomousCommand[] result = new AutonomousCommand[commands.size()];
        commands.copyInto(result);
        return result;
    }
    
    public void clearCommands()
    {
        commands.removeAllElements();
    }
    
    public void addAutonomousDelay(double seconds)
    {
        addCommand(new AutonomousWait(seconds));
    }
}
