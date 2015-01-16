package org.TexasTorque.TexasTorque2013.constants;

public class Ports
{
    //----- Misc -----
    public final static int SIDECAR_ONE = 1;
    //public final static int SIDECAR_TWO = 2;
    
    //----- Controllers -----
    public final static int DRIVE_CONTROLLER_PORT = 1;
    public final static int OPERATOR_CONTROLLER_PORT = 2;
    
    //----- Motors -----
        //----- Sidecar 1 -----
        public final static int FRONT_RIGHT_DRIVE_MOTOR_PORT = 8;
        public final static int REAR_RIGHT_DRIVE_MOTOR_PORT = 6;
        public final static int REAR_SHOOTER_MOTOR_PORT = 9;
        public final static int FRONT_SHOOTER_MOTOR_PORT = 5;
        
        //----- Sidecar 2 -----
        public final static int FRONT_LEFT_DRIVE_MOTOR_PORT = 4;
        public final static int REAR_LEFT_DRIVE_MOTOR_PORT = 10;
        public final static int INTAKE_MOTOR_PORT = 2;
        public final static int ELEVATOR_MOTOR_PORT = 3;
        public final static int CROSS_MOTOR_PORT = 1;
    
    //----- Solenoids -----
    public final static int INTAKE_A_PORT = 3;
    public final static int INTAKE_B_PORT = 4;
    public final static int BRAKE_A_PORT = 5;
    public final static int BRAKE_B_PORT = 6;
    public final static int DRIVE_SHIFTER_PORT_A = 1;
    public final static int DRIVE_SHIFTER_PORT_B = 2;
    public final static int BRIDGE_LOWERER_PORT = 7;
    public final static int HOOD_SOLINOID_PORT = 8;
    
    //----- Digital Inputs -----
        //----- Sidecar 1 -----
        public final static int SHOOTER_ENCODER_PORT_A = 1;
        public final static int SHOOTER_ENCODER_PORT_B = 2;
        public final static int RIGHT_DRIVE_ENCODER_A_PORT = 13;
        public final static int RIGHT_DRIVE_ENCODER_B_PORT = 14;
        
        //----- Sidecar 2 -----
        public final static int LEFT_DRIVE_ENCODER_A_PORT = 8;
        public final static int LEFT_DRIVE_ENCODER_B_PORT = 12;
        public final static int LIGHTS_R_PORT = 10;
        public final static int LIGHTS_G_PORT = 9;
        public final static int LIGHTS_B_PORT = 11;
        public final static int PRESSURE_SWITCH_PORT = 4;
        public final static int BOTTOM_LIMIT_SWITCH = 3;
        public final static int MIDDLE_LIMIT_SWITCH = 6;
        public final static int LIGHT_SENSOR_PORT = 5; 
    
    //----- Analog Inputs -----
    public final static int GYRO_PORT = 2;
    public final static int ANALOG_PRESSURE_PORT = 1;
    
    //----- Relays -----
        //----- Sidecar 1 -----
        public final static int COMPRESSOR_RELAY_PORT = 1;
        //----- Sidecar 2 -----
    
}
