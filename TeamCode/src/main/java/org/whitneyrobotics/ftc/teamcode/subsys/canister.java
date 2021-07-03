package org.whitneyrobotics.ftc.teamcode.subsys;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.whitneyrobotics.ftc.teamcode.lib.util.Toggler;

public class canister {

    private Servo canisterServo;

    public void canister(HardwareMap hardwareMap){
        canisterServo = hardwareMap.servo.get("canisterServo");
    }

    private double[] servoPositions = {0.79, 0.57};

    private enum ServoPositions {
        RESTING, ACTIVE
    }

    private Toggler positionStateToggler = new Toggler(2);

    public void operate(boolean gamepad1) {
        positionStateToggler.changeState(gamepad1);
        if(positionStateToggler.currentState() == 1) {
            canisterServo.setPosition(servoPositions[ServoPositions.ACTIVE.ordinal()]);
        } else {
            canisterServo.setPosition(ServoPositions.RESTING.ordinal());
        }
    }
}
