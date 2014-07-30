package org.TexasTorque.TorqueLib.component;

import edu.wpi.first.wpilibj.DigitalOutput;
import java.util.Vector;
import org.TexasTorque.TexasTorque2013.constants.Constants;

/*
 * This class is used to control the ADAfruit lights.
 * A Vector of digital outputs encodes the desired state
 * in binary.
 */

public class VeloLights
{
    private Vector outputVector;
    private int currentState;
    private int desiredState;
    
    /*
     * Vector of the digital outputs in which to encode the state.
     */
    public VeloLights(Vector outputs)
    {
        outputVector = outputs;
        currentState = Constants.LIGHTS_OFF;
        desiredState = Constants.WHITE_SOLID;
    }
    
    /*
     * Desired state to be sent to the arduino.
     */
    public void setDesiredState(int state)
    {
        desiredState = state;
    }
    
    /*
     * Converts the state into binary and then encodes it
     * into the vector of digital outputs.
     */
    private void setState()
    {
        if(currentState == Constants.RED_SOLID || currentState == Constants.YELLOW_SOLID || currentState == Constants.MAGENTA_SOLID || currentState == Constants.WHITE_SOLID)
        {
            ((DigitalOutput)outputVector.elementAt(0)).set(true);
        }
        else
        {
            ((DigitalOutput)outputVector.elementAt(0)).set(false);
        }
        if(currentState == Constants.GREEN_SOLID || currentState == Constants.CYAN_SOLID || currentState == Constants.YELLOW_SOLID || currentState == Constants.WHITE_SOLID)
        {
            ((DigitalOutput)outputVector.elementAt(1)).set(true);
        }
        else
        {
            ((DigitalOutput)outputVector.elementAt(1)).set(false);
        }
        if(currentState == Constants.BLUE_SOLID || currentState == Constants.CYAN_SOLID || currentState == Constants.MAGENTA_SOLID || currentState == Constants.WHITE_SOLID)
        {
            ((DigitalOutput)outputVector.elementAt(2)).set(true);
        }
        else
        {
            ((DigitalOutput)outputVector.elementAt(2)).set(false);
        }
    }
    
    /*
     * This is called continually to manage the signal to the arduino.
     */
    public void run()
    {
        if(currentState != desiredState)
        {
            currentState = desiredState;
            setState();
        }
    }
}