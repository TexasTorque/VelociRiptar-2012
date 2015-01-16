package org.TexasTorque.TexasTorque2013.io;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import java.util.Vector;
import org.TexasTorque.TexasTorque2013.constants.Ports;
import org.TexasTorque.TorqueLib.component.Motor;
import org.TexasTorque.TorqueLib.component.VeloLights;

public class RobotOutput
{
    private static RobotOutput instance;
    
    private VeloLights lights;
    private Vector lightsVector;
    
    //----- Pneumatics -----
    private Compressor compressor;
    private DoubleSolenoid driveShifter;
    private DoubleSolenoid brakes;
    private DoubleSolenoid intake;
    private Solenoid hood;
    private Solenoid bridgeDropper;
    //----- Drive Motors -----
    private Motor frontLeftDriveMotor;
    private Motor rearLeftDriveMotor;
    private Motor frontRightDriveMotor;
    private Motor rearRightDriveMotor;
    //----- Shooter Motors -----
    private Motor frontShooterMotor;
    private Motor rearShooterMotor;
    //----- Misc Motors -----
    private Motor intakeMotor;
    private Motor elevatorMotor;
    private Motor crossMotor;
    
    public RobotOutput()
    {   
        
        lightsVector = new Vector();
        lightsVector.addElement(new DigitalOutput(Ports.SIDECAR_ONE, Ports.LIGHTS_R_PORT));
        lightsVector.addElement(new DigitalOutput(Ports.SIDECAR_ONE, Ports.LIGHTS_G_PORT));
        lightsVector.addElement(new DigitalOutput(Ports.SIDECAR_ONE, Ports.LIGHTS_B_PORT));
        lights = new VeloLights(lightsVector);
        //----- Pneumatics -----
        compressor = new Compressor(Ports.SIDECAR_ONE, Ports.PRESSURE_SWITCH_PORT, Ports.SIDECAR_ONE, Ports.COMPRESSOR_RELAY_PORT);
        driveShifter = new DoubleSolenoid(Ports.DRIVE_SHIFTER_PORT_A, Ports.DRIVE_SHIFTER_PORT_B);
        intake = new DoubleSolenoid(Ports.INTAKE_A_PORT, Ports.INTAKE_B_PORT);
        bridgeDropper = new Solenoid(Ports.BRIDGE_LOWERER_PORT);
        hood = new Solenoid(Ports.HOOD_SOLINOID_PORT);
        brakes = new DoubleSolenoid(Ports.BRAKE_A_PORT, Ports.BRAKE_B_PORT);
        
        
        //----- Drive Motors -----
        frontLeftDriveMotor = new Motor(new Victor(Ports.SIDECAR_ONE, Ports.FRONT_LEFT_DRIVE_MOTOR_PORT), false, true);
        rearLeftDriveMotor = new Motor(new Victor(Ports.SIDECAR_ONE, Ports.REAR_LEFT_DRIVE_MOTOR_PORT), false, true);
        frontRightDriveMotor = new Motor(new Victor(Ports.SIDECAR_ONE, Ports.FRONT_RIGHT_DRIVE_MOTOR_PORT), true, true);
        rearRightDriveMotor = new Motor(new Victor(Ports.SIDECAR_ONE, Ports.REAR_RIGHT_DRIVE_MOTOR_PORT), true, true);
        //----- Shooter Subsystem Motors-----
        frontShooterMotor = new Motor(new Victor(Ports.SIDECAR_ONE, Ports.FRONT_SHOOTER_MOTOR_PORT), true, true);
        rearShooterMotor = new Motor(new Victor(Ports.SIDECAR_ONE, Ports.REAR_SHOOTER_MOTOR_PORT), true, true);
        //----- Misc Motors -----
        intakeMotor = new Motor(new Victor(Ports.SIDECAR_ONE, Ports.INTAKE_MOTOR_PORT), false, true);
        elevatorMotor = new Motor(new Victor(Ports.SIDECAR_ONE, Ports.ELEVATOR_MOTOR_PORT), true, true);
        crossMotor = new Motor(new Victor(Ports.SIDECAR_ONE, Ports.CROSS_MOTOR_PORT), false, true);
        //----- Misc Misc -----
        compressor.start();
    }
 
    public synchronized static RobotOutput getInstance()
    {
        return (instance == null) ? instance = new RobotOutput() : instance;
    }
    
    public void setLightsState(int state)
    {
        lights.setDesiredState(state);
    }
    
    public void runLights()
    {
        lights.run();
    }
    
    // ----- Motors -----
    public void setDriveMotors(double leftSpeed, double rightSpeed)
    {
        frontLeftDriveMotor.Set(leftSpeed);
        rearLeftDriveMotor.Set(leftSpeed);
        frontRightDriveMotor.Set(rightSpeed);
        rearRightDriveMotor.Set(rightSpeed);
    }
    
    public void setShooterMotors(double speed)
    {
        frontShooterMotor.Set(speed);
        rearShooterMotor.Set(speed);
    }
    
    public void setCrossMotor(double speed)
    {
        crossMotor.Set(speed);
    }
    
    public void setIntakeMotor(double speed)
    {
        intakeMotor.Set(speed);
    }
    
    public void setElevatorMotors(double speed)
    {
        elevatorMotor.Set(speed);
    }
    
    //----- Pneumatics -----
    public void setShifters(boolean highGear)
    {
        if(highGear)
        {
            driveShifter.set(DoubleSolenoid.Value.kForward);
        }
        else
        {
            driveShifter.set(DoubleSolenoid.Value.kReverse);
        }    
    }
    
    public void setHood(boolean lowered)
    {
        hood.set(!lowered);
    }
    
    public void setIntakePneumatic(boolean lowered)
    {
        if(lowered)
        {
            intake.set(DoubleSolenoid.Value.kForward);
        }
        else
        {
            intake.set(DoubleSolenoid.Value.kReverse);
        }
    }
    
    public void setBridgeLowerer(boolean lower)
    {
        bridgeDropper.set(lower);
    }
    
    public void setBrakes(boolean brake)
    {
        if(brake)
        {
            brakes.set(DoubleSolenoid.Value.kForward);
        }
        else
        {
            brakes.set(DoubleSolenoid.Value.kReverse);
        }
    }
}
