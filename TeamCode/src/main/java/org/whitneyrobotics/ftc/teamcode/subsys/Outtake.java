package org.whitneyrobotics.ftc.teamcode.subsys;

//import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.whitneyrobotics.ftc.teamcode.lib.util.Toggler;

public class Outtake {

    private final int  kP = 0;
    private final int  kI = 0;
    private final int  kD = 0;

    private PID outtakePID = new PID(kP, kI, kD);

    public DcMotorEx flywheelMotor;
    private Toggler flywheelToggler = new Toggler(2);
    //Target RPM = 1453.069
    private double outtakeSpeed = 1453.069/6000;

    public void outtake(HardwareMap hardwareMap) {
        flywheelMotor = hardwareMap.get(DcMotorEx.class,"flywheelMotor");
    }

    public double operate(boolean gamepadInput) {
        flywheelToggler.changeState(gamepadInput);
        if(flywheelToggler.currentState() == 1){
            //PID
            flywheelMotor.setPower(outtakePID.calculate(outtakeSpeed, flywheelMotor.getVelocity()));
            //flywheelMotor.setPower(outtakeSpeed);
        } else {
            flywheelMotor.setPower(0.0);
        }
        return flywheelMotor.getPower();
    }
}
