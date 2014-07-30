package org.TexasTorque.TexasTorque2013.subsystem.manipulator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.TexasTorque.TexasTorque2013.TorqueSubsystem;
import org.TexasTorque.TexasTorque2013.constants.Constants;

public class Elevator extends TorqueSubsystem {

    private static Elevator instance;

    private int elevatorState;
    private double elevatorMotorSpeed;
    private double defaultMotorSpeed;
    private double shootingMotorSpeed;

    public static Elevator getInstance() {
        return (instance == null) ? instance = new Elevator() : instance;
    }

    private Elevator() {
        super();

        elevatorState = Constants.ELEVATOR_READY_STATE;
        elevatorMotorSpeed = Constants.MOTOR_STOPPED;
        defaultMotorSpeed = Constants.DEFAULT_ELEVATOR_SPEED;
    }

    public void run() {
        if (elevatorState == Constants.ELEVATOR_READY_STATE) {
            elevatorMotorSpeed = Constants.MOTOR_STOPPED;
        } else if (elevatorState == Constants.ELEVATOR_FIRING_STATE) {
            elevatorMotorSpeed = shootingMotorSpeed;
        } else if (elevatorState == Constants.ELEVATOR_INDEXING_STATE) {
            if (!sensorInput.getLightSensor()) {
                elevatorMotorSpeed = defaultMotorSpeed;
            } else {
                elevatorMotorSpeed = Constants.MOTOR_STOPPED;
            }
        } else if (elevatorState == Constants.ELEVATOR_MANUAL_UP_STATE) {
            elevatorMotorSpeed = shootingMotorSpeed;
        } else if (elevatorState == Constants.ELEVATOR_MANUAL_DOWN_STATE) {
            elevatorMotorSpeed = -1 * shootingMotorSpeed;
        }
    }

    public void setState(int assignState) {
        if (assignState != elevatorState) {
            elevatorState = assignState;
        }
        SmartDashboard.putNumber("ElevatorState", elevatorState);
    }

    public void setToRobot() {
        robotOutput.setElevatorMotors(elevatorMotorSpeed);
    }

    public String getKeyNames() {
        String names = "ElevatorMotorSpeed,";

        return names;
    }

    public String logData() {
        String data = elevatorMotorSpeed + ",";

        return data;
    }

    public void loadParameters() {
        defaultMotorSpeed = params.getAsDouble("E_DefaultSpeed", Constants.DEFAULT_ELEVATOR_SPEED);
        shootingMotorSpeed = params.getAsDouble("E_FireSpeed", .7);
    }

}
