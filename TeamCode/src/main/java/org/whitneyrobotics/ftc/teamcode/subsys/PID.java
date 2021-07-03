package org.whitneyrobotics.ftc.teamcode.subsys;

public class PID {
    double recordedTime = System.nanoTime();
    double lastError = 0;
    double lastI = 0;
    double kP;
    double kI;
    double kD;

    public PID(double kP, double kI, double kD){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public double calculate(double target, double current) {
        double error = target - current;


        double deltaTime = System.nanoTime() - recordedTime;
        recordedTime = System.nanoTime();

        double pResult = error * kP;

        double I = deltaTime * error + lastI;
        lastI = I;
        double iResult = I * kI;

        double deltaError = error - lastError;
        lastError = error;

        double dResult = kD * (deltaError/deltaTime);

        double PID = pResult + iResult + dResult;
        return PID;

    }
}
