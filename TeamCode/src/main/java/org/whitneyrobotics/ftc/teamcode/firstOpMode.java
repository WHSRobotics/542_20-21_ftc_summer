package org.whitneyrobotics.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.whitneyrobotics.ftc.teamcode.subsys.Outtake;

public class firstOpMode extends OpMode {
    Outtake outtake = new Outtake();

    @Override
    public void init() {
        telemetry.addData("I'm alive", true);
    }

    @Override
    public void loop() {
        telemetry.addData("Motor Power", outtake.flywheelMotor.getPower());
    }
}
