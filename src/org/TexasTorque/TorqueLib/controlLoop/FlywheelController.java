package org.TexasTorque.TorqueLib.controlLoop;

import org.TexasTorque.TorqueLib.util.Matrix;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * Full state feedback controller for a flywheel
 * 
 * Based on work by 254
 */

public class FlywheelController extends StateSpaceController {

    double velGoal; // Radians per second
    Matrix y;
    Matrix r;
    double prevPos;
    double goal;
    double curVel;
    double outputVoltage = 0.0;
    public double doneRange = 9;

    public FlywheelController(String name, StateSpaceGains gains) {
        this(name, gains, 1 / 100.0);
    }

    public FlywheelController(String name, StateSpaceGains gains, double period) {
        super(name, 1, 1, 2, gains, period);
        this.velGoal = 0.0;
        this.y = new Matrix(1, 1);
        this.r = new Matrix(2, 1);
        this.period = period;
    }

    public void capU() {
        double deltaU = U.get(0);
        double u_max = Umax.get(0);
        double u_min = Umin.get(0);
        double u = Xhat.get(0);

        double upWindow = u_max - outputVoltage;
        double downWindow = u_min - outputVoltage;

        if (deltaU > upWindow) {
            deltaU = upWindow;
        } else if (deltaU < downWindow) {
            deltaU = downWindow;
        }
        outputVoltage += deltaU;
        U.set(0, deltaU);
    }

    public double update(double currentValue) {

        curVel = currentValue;

        this.y.set(0, 0, currentValue);

        r.set(0, 0, (velGoal * (1 - A.get(1, 1))) / A.get(1, 0));
        r.set(1, 0, velGoal);

        // Update state space controller
        update(r, y);

        double voltage = DriverStation.getInstance().getBatteryVoltage();
        if (voltage < 4.5) {
            voltage = 12.0;
        }

        if (velGoal < 1.0) {
            goal = currentValue;
            return 0.0;
        } else {
            return (outputVoltage / voltage) * 1.0;
        }
    }

    public double getVelocity() {
        return curVel;
    }

    public void setVelocityGoal(double v) {
        velGoal = v;
    }

    public void setDoneRange(double range)
    {
        doneRange = range;
    }
    
    public boolean isDoneRaw() {
        return Math.abs(Xhat.get(1) - velGoal) < doneRange;
    }
    boolean isDone = false;

    public boolean isDone() {
        return isDone;
    }

    public double getVelocityGoal() {
        return velGoal;
    }

    public void setGoal(double goal) {
    }

    public double getGoal() {
        return getVelocityGoal();
    }

    public void reset() {
        velGoal = 0;
    }
}
