package org.TexasTorque.TexasTorque2013.constants;

public class Constants
{
    //----- Controller -----
    public final static double SPEED_AXIS_DEADBAND = 0.07;
    public final static double TURN_AXIS_DEADBAND = 0.07;
    
    public final static boolean DEFAULT_FIRST_CONTROLLER_TYPE = false;
    public final static boolean DEFAULT_SECOND_CONTROLLER_TYPE = false;
    
    //----- Light States -----
    public final static int WHITE_SOLID = 0;
    public final static int BLUE_SOLID = 1;
    public final static int RED_SOLID = 2;
    public final static int GREEN_SOLID = 3;
    public final static int CYAN_SOLID = 4;
    public final static int YELLOW_SOLID = 5;
    public final static int MAGENTA_SOLID = 6;
    public final static int LIGHTS_OFF = 7;

    //----- Autonomous -----
    public final static int DO_NOTHING_AUTO = 0;
    public final static int MIDDLE_THREE_AUTO = 1;
    public final static int SIDE_THREE_AUTO = 2;
    public final static int MIDDLE_SEVEN_AUTO = 3;
    public final static int RIGHT_SEVEN_AUTO = 4;
    public final static int LEFT_SEVEN_AUTO = 5;
    public final static int RIGHT_THREE_DRIVE_AUTO = 6;
    public final static int DEMO_AUTO = 7;
    public final static int VISION_AUTO = 9;
    public final static int VISION_LOCK_AUTO = 10;
    public final static int VISION_SHORT_AUTO = 11;
    public final static int VISION_CENTER_LEFT_AUTO = 12;
    public final static int VISION_CENTER_RIGHT_AUTO = 13;
    
    //----- Drivebase -----
    public final static double DEFAULT_HIGH_SENSITIVITY = 0.7;
    public final static boolean HIGH_GEAR = true;
    public final static boolean LOW_GEAR = false;
    
    //----- Gyro -----
    public static double GYRO_SENSITIVITY = 0.014;
    
    //----- Tilt -----
    public final static double DEFAULT_STANDARD_TILT_POSITION = 5.0;
    public final static double POTENTIOMETER_LOW_VOLTAGE = 0.27;
    public final static double POTENTIOMETER_HIGH_VOLTAGE = 6.578;
    public final static boolean GATE_EXTENDED = false;
    public final static boolean GATE_RETRACTED = true;
    
    //----- Shooter -----
    public final static double DEFAULT_SHOOTER_RATE = 1000.0;
    public final static double SHOOTER_STOPPED_RATE = 0.0;
    
    //----- Elevator -----
    public final static int ELEVATOR_READY_STATE = 0;
    public final static int ELEVATOR_INDEXING_STATE = 3;
    public final static int ELEVATOR_MANUAL_UP_STATE = 1;
    public final static int ELEVATOR_MANUAL_DOWN_STATE = 2;
    public final static int ELEVATOR_FIRING_STATE = 4;
    public final static double DEFAULT_ELEVATOR_SPEED = .3;
    
    //----- Indexing -----
    public final static int NO_BALL_INTAKE = 0;
    public final static int ONE_BALL_INTAKE = 1;
    public final static int ONE_BALL_TRANSITION = 5;
    public final static int TWO_BALL_INTAKE = 2;
    public final static int FULLLY_INTOOK = 3;
    
    //----- Magazine -----
    public final static boolean MAGAZINE_STORED = false;
    public final static boolean MAGAZINE_LOADING = true;
    
    public final static int MAGAZINE_READY_STATE = 0;
    public final static int MAGAZINE_LOADING_STATE = 1;
    public final static int MAGAZINE_SHOOTING_STATE = 2;
    public final static int MAGAZINE_RESETTING_STATE = 3;
    
    public final static double MAGAZINE_DELTA_TIME = 1.0;
    
    //----- Intake -----
    public static final boolean CROSS_DOWN = true;
    public static final boolean CROSS_UP = false;
    
    //----- Misc -----
    public final static int CYCLES_PER_LOG = 10;
    public final static double MOTOR_STOPPED = 0.0;
    public final static int SHOOTER_FILTER_SIZE = 10;
    public final static double PRESSURE_THRESHOLD = 2.0;
    public final static int RED_ALLIANCE = 0;
    public final static int BLUE_ALLIANCE = 1;
    
}
