package org.TexasTorque.TexasTorque2013.autonomous.shooter;

import org.TexasTorque.TexasTorque2013.autonomous.AutonomousCommand;

public class AutonomousCustomShooter extends AutonomousCommand
{
    private double rate;
    
    public AutonomousCustomShooter(double rate)
    {
        super();
        
        this.rate = rate;
    }
    
    public void reset()
    {
    }
    
    public boolean run()
    {
        shooter.setShooterRates(rate);
        return true;
    }
}
