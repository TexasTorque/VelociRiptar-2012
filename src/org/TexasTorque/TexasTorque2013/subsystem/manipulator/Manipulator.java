package org.TexasTorque.TexasTorque2013.subsystem.manipulator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.TexasTorque.TexasTorque2013.TorqueSubsystem;
import org.TexasTorque.TexasTorque2013.constants.Constants;
import org.TexasTorque.TexasTorque2013.subsystem.drivebase.Drivebase;

public class Manipulator extends TorqueSubsystem {

    private static Manipulator instance;

    private Drivebase drivebase;
    private Shooter shooter;
    private Elevator elevator;
    private Intake intake;

    private int currentIndexingState;
    private boolean noAction;

    public static Manipulator getInstance() {
        return (instance == null) ? instance = new Manipulator() : instance;
    }

    private Manipulator() {
        super();

        shooter = Shooter.getInstance();
        elevator = Elevator.getInstance();
        intake = Intake.getInstance();
        drivebase = Drivebase.getInstance();
        currentIndexingState = Constants.NO_BALL_INTAKE;

        noAction = true;
    }

    public void run() {
        noAction = true;
        if (!driverInput.overrideState()) {
            //----- Normal Ops -----

            if (driverInput.runIntake()) {
                intake();
                noAction = false;
            } else if (driverInput.resetIndexingState()) {
                currentIndexingState = Constants.NO_BALL_INTAKE;
                noAction = false;
            } else if (driverInput.reverseIntake()) {
                reverseIntake();
                noAction = false;
            } else if (driverInput.fireBasketball()) {
                fire();
            } else {
                elevator.setState(Constants.ELEVATOR_READY_STATE);
                intake.setIntakeSpeed(Constants.MOTOR_STOPPED);
            }
            if (driverInput.shootClose()) {
                shootClose();
            } else if (driverInput.shootFar()) {
                shootFar();
            } else {
                shooter.stopShooter();
            }
            
            if (noAction)
            {
                intake.setCross(Constants.CROSS_UP);
            }

            intake.run();
            shooter.run();
            elevator.run();
        } else {
            calcOverrides();
        }
        SmartDashboard.putNumber("IndexingState", currentIndexingState);
        SmartDashboard.putBoolean("BottomLimitSwitch", sensorInput.getBottomLimitSwitch());
        SmartDashboard.putBoolean("MiddleLimitSwitch", sensorInput.getMiddleLimitSwitch());
        SmartDashboard.putBoolean("LightSensor", sensorInput.getLightSensor());
    }

    public void setToRobot() {
        elevator.setToRobot();
        intake.setToRobot();
        shooter.setToRobot();
    }

    public String getKeyNames() {
        String names = "InOverrideState,";
        names += intake.getKeyNames();
        names += elevator.getKeyNames();
        names += shooter.getKeyNames();

        return names;
    }

    public String logData() {
        String data = driverInput.overrideState() + ",";
        data += intake.logData();
        data += elevator.logData();
        data += shooter.logData();

        return data;
    }

    public void loadParameters() {
        shooter.loadParameters();
        elevator.loadParameters();
        intake.loadParameters();
    }

    private void calcOverrides() {
        if (driverInput.intakeOverride()) {
            intake.setIntakeSpeed(Intake.intakeSpeed);
            intake.setCross(Constants.CROSS_DOWN);
            elevator.setState(Constants.ELEVATOR_INDEXING_STATE);
        } else if (driverInput.outtakeOverride()) {
            reverseIntake();
        } else if (driverInput.shootCloseOverride()) {
            double shooterSpeed = Shooter.shooterRate;
            shooter.setShooterRates(shooterSpeed);
            shooter.setHoodState(Shooter.highShot);
            intake.setIntakeSpeed(Constants.MOTOR_STOPPED);
        } else if (driverInput.shootFarOverride()) {
            intake.setIntakeSpeed(Constants.MOTOR_STOPPED);
            shooter.setShooterRates(Shooter.shooterRate);
            shooter.setHoodState(Shooter.lowShot);
        } else {
            intake.setIntakeSpeed(Constants.MOTOR_STOPPED);
            intake.setCross(Constants.CROSS_UP);
            shooter.stopShooter();
        }

        //----- Manual Elevator Controls -----
        if (driverInput.elevatorTopOverride()) {
            robotOutput.setElevatorMotors(-1 * driverInput.getElevatorJoystick());
        } else if (driverInput.elevatorBottomOverride()) {
            robotOutput.setElevatorMotors(-1 * driverInput.getElevatorJoystick());
        } else {
            robotOutput.setElevatorMotors(Constants.MOTOR_STOPPED);
        }

        intake.run();
        elevator.run();
        shooter.run();
    }

    public void intake() {
        if (currentIndexingState != Constants.FULLLY_INTOOK) {
            intake.setIntakeSpeed(Intake.intakeSpeed);
            intake.setCross(Constants.CROSS_DOWN);
            if (sensorInput.getBottomLimitSwitch() && currentIndexingState == Constants.TWO_BALL_INTAKE) {
                currentIndexingState = Constants.FULLLY_INTOOK;
                intake.setIntakeSpeed(Constants.MOTOR_STOPPED);
            }
        } else {
            intake.setIntakeSpeed(Constants.MOTOR_STOPPED);
        }

        if (currentIndexingState == Constants.NO_BALL_INTAKE) {
            elevator.setState(Constants.ELEVATOR_INDEXING_STATE);
            if (sensorInput.getMiddleLimitSwitch()) {
                elevator.setState(Constants.ELEVATOR_READY_STATE);
                currentIndexingState = Constants.ONE_BALL_INTAKE;
            }
        } else if (currentIndexingState == Constants.ONE_BALL_INTAKE) {
            if (sensorInput.getBottomLimitSwitch()) {
                elevator.setState(Constants.ELEVATOR_INDEXING_STATE);
                intake.setIntakeSpeed(Intake.intakeSpeed);
                currentIndexingState = Constants.ONE_BALL_TRANSITION;
            }
        } else if (currentIndexingState == Constants.ONE_BALL_TRANSITION) {
            if (sensorInput.getLightSensor()) {
                elevator.setState(Constants.ELEVATOR_READY_STATE);
                intake.setIntakeSpeed(Intake.intakeSpeed);
                currentIndexingState = Constants.TWO_BALL_INTAKE;
            }
        }

    }

    public void reverseIntake() {
        intake.setCross(Constants.CROSS_UP);
        intake.setIntakeSpeed(Intake.outtakeSpeed);
        elevator.setState(Constants.ELEVATOR_MANUAL_DOWN_STATE);
        shooter.stopShooter();
    }

    public void shootClose() {
        shooter.setHoodState(Shooter.highShot);
        shooter.setShooterRates(Shooter.shooterRate);
    }

    public void shootFar() {
        shooter.setHoodState(Shooter.lowShot);
        shooter.setShooterRates(Shooter.shooterRate);
    }

    public void fire() {
        if (shooter.isSpunUp()) {
            elevator.setState(Constants.ELEVATOR_FIRING_STATE);
            intake.setIntakeSpeed(Intake.intakeSpeed);
            currentIndexingState = Constants.NO_BALL_INTAKE;
        } else {
            if (!sensorInput.getLightSensor()) {
                elevator.setState(Constants.ELEVATOR_FIRING_STATE);
                intake.setIntakeSpeed(Intake.intakeSpeed);
            } else {
                elevator.setState(Constants.ELEVATOR_READY_STATE);
                intake.setIntakeSpeed(Constants.MOTOR_STOPPED);
            }
            if (!sensorInput.getBottomLimitSwitch() && !sensorInput.getMiddleLimitSwitch() && !sensorInput.getLightSensor()) {
                currentIndexingState = Constants.NO_BALL_INTAKE;
            }
        }
    }

    public void setLightsNormal() {
        double currentAlliance = dashboardManager.getDS().getAlliance().value;
        if (currentAlliance == Constants.BLUE_ALLIANCE) {
            robotOutput.setLightsState(Constants.BLUE_SOLID);
        } else if (currentAlliance == Constants.RED_ALLIANCE) {
            robotOutput.setLightsState(Constants.RED_SOLID);
        }
        if (currentIndexingState == Constants.FULLLY_INTOOK) {
            robotOutput.setLightsState(Constants.GREEN_SOLID);
        }
    }

}
