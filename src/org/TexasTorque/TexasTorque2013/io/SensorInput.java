package org.TexasTorque.TexasTorque2013.io;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Watchdog;
import org.TexasTorque.TexasTorque2013.constants.Constants;
import org.TexasTorque.TexasTorque2013.constants.Ports;
import org.TexasTorque.TorqueLib.component.TorqueEncoder;
import org.TexasTorque.TorqueLib.util.TorqueUtil;

public class SensorInput
{
    private static SensorInput instance;
    private Watchdog watchdog;
    //----- Encoder -----
    private TorqueEncoder leftDriveEncoder;
    private TorqueEncoder rightDriveEncoder;
    private TorqueEncoder shooterEncoder;
    //----- Analog -----
    private AnalogChannel pressureSensor;
    private AnalogChannel gyroChannel;
    public Gyro gyro;
    //----- Digital -----
    private DigitalInput bottomLimitSwitch;
    private DigitalInput middleLimitSwitch;
    private DigitalInput lightSwitch;

    public SensorInput()
    {
        watchdog = Watchdog.getInstance();
        //----- Encoders/Counters -----
        leftDriveEncoder = new TorqueEncoder(Ports.SIDECAR_ONE, Ports.LEFT_DRIVE_ENCODER_A_PORT, Ports.SIDECAR_ONE, Ports.LEFT_DRIVE_ENCODER_B_PORT, false);
        rightDriveEncoder = new TorqueEncoder(Ports.SIDECAR_ONE, Ports.RIGHT_DRIVE_ENCODER_A_PORT, Ports.SIDECAR_ONE, Ports.RIGHT_DRIVE_ENCODER_B_PORT, false);
        shooterEncoder = new TorqueEncoder(Ports.SIDECAR_ONE, Ports.SHOOTER_ENCODER_PORT_A, Ports.SIDECAR_ONE, Ports.SHOOTER_ENCODER_PORT_B, false);
        //----- Gyro -----
        gyroChannel = new AnalogChannel(Ports.GYRO_PORT);
        gyro = new Gyro(gyroChannel);
        gyro.reset();
        gyro.setSensitivity(Constants.GYRO_SENSITIVITY);
        //----- Digital -----
        bottomLimitSwitch = new DigitalInput(Ports.SIDECAR_ONE, Ports.BOTTOM_LIMIT_SWITCH);
        middleLimitSwitch = new DigitalInput(Ports.SIDECAR_ONE, Ports.MIDDLE_LIMIT_SWITCH);
        lightSwitch = new DigitalInput(Ports.SIDECAR_ONE, Ports.LIGHT_SENSOR_PORT);
        //----- Misc -----
        pressureSensor = new AnalogChannel(Ports.ANALOG_PRESSURE_PORT);
        startEncoders();
    }
    
    public synchronized static SensorInput getInstance()
    {
        return (instance == null) ? instance = new SensorInput() : instance;
    }
    
    private void startEncoders()
    {
        // 1 foot = 958 clicks
        leftDriveEncoder.start();
        rightDriveEncoder.start();        
        shooterEncoder.start();
    }
    
    public void resetEncoders()
    {
        leftDriveEncoder.reset();
        rightDriveEncoder.reset();
        shooterEncoder.reset();
    }
    
    public void calcEncoders()
    {
        leftDriveEncoder.calc();
        rightDriveEncoder.calc();
        shooterEncoder.calc();
    }
    
    public void resetGyro()
    {
        gyro.reset();
        gyro.setSensitivity(Constants.GYRO_SENSITIVITY);
    }
    
    public double getLeftDriveEncoder()
    {
        return (leftDriveEncoder.get() / 958.0) * 12; 
    }
    
    public double getRightDriveEncoder()
    {
        return (rightDriveEncoder.get() / 958.0) * 12;
    }
    
    public double getLeftDriveEncoderRate()
    {
        return (leftDriveEncoder.getRate() / 958.0) * 12;
    }
    
    public double getRightDriveEncoderRate()
    {
        return (rightDriveEncoder.getRate() / 958.0) * 12;
    }
    
    public double getLeftDriveEncoderAcceleration()
    {
        return (leftDriveEncoder.getAcceleration() / 958.0) * 12;
    }
    
    public double getRightDriveEncoderAcceleration()
    {
        return (rightDriveEncoder.getAcceleration() / 958.0) * 12;
    }
    
    public double getShooterRate()
    {
        return TorqueUtil.convertToRMP(shooterEncoder.getRate(), 100);
    }
    
    public boolean getBottomLimitSwitch()
    {
        return !bottomLimitSwitch.get();
    }
    
    public boolean getMiddleLimitSwitch()
    {
        return !middleLimitSwitch.get();
    }
    
    public boolean getLightSensor()
    {
        return lightSwitch.get();
    }
    
    public double getPSI()
    {
        return pressureSensor.getVoltage();
    }
    
    public double getGyroAngle()
    {
        return limitGyroAngle(-gyro.getAngle() * 2);
    }
    
    public double limitGyroAngle(double angle)
    {
        while(angle >= 360.0)
        {
            watchdog.feed();
            angle -= 360.0;
        }
        while(angle < 0.0)
        {
            watchdog.feed();
            angle += 360.0;
        }
        if(angle > 180)
        {
            angle -= 360;
        }
        return angle;
    }
    
}