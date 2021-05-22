package org.whitneyrobotics.ftc.teamcode.subsys;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.whitneyrobotics.ftc.teamcode.lib.util.Toggler;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

public class Drivetrain {

    private DcMotor frontL;
    private DcMotor frontR;
    private DcMotor backL;
    private DcMotor backR;

    private Toggler directionToggler = new Toggler(2);

    public Drivetrain(HardwareMap hardwareMap){
        frontL = hardwareMap.dcMotor.get("frontL");
        frontR = hardwareMap.dcMotor.get("frontR");
        backL = hardwareMap.dcMotor.get("backL");
        backR = hardwareMap.dcMotor.get("backR");

        frontR.setDirection(DcMotorSimple.Direction.REVERSE);
        backR.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void operate(double stick1Y, double stick2Y){
        if(directionToggler.currentState() == 0){
            frontL.setPower(-stick1Y);
            backL.setPower(-stick1Y);
            frontR.setPower(-stick2Y);
            backR.setPower(-stick2Y);
        } else {
            frontL.setPower(stick1Y);
            backL.setPower(stick1Y);
            frontR.setPower(stick2Y);
            backR.setPower(stick2Y);
        }
    }

//    public void operateDemo(double stick1Y, double stick2Y, boolean gamepad1Y){
//        if(gamepad1Y = false){
//            frontL.setPower(-stick1Y);
//            backL.setPower(-stick1Y);
//            frontR.setPower(-stick2Y);
//            backR.setPower(-stick2Y);
//        } else {
//            frontL.setPower(stick1Y);
//            backL.setPower(stick1Y);
//            frontR.setPower(stick2Y);
//            backR.setPower(stick2Y);
//        }
//    }
//
//    public void loop() {
//        operateDemo(gamepad1.left_stick_y, gamepad1.right_stick_y, gamepad1.y);
//    }


    public void setDirection(boolean gamepad1Y) {
        directionToggler.changeState(gamepad1Y);
    }

    public double[] averageEncoderPositions() {
        double[] encoderPositions = new double[2];
        encoderPositions[0] = (frontL.getCurrentPosition() + backL.getCurrentPosition())/2;
        encoderPositions[1] = (frontR.getCurrentPosition() + backR.getCurrentPosition())/2;
        
        return encoderPositions;
    }




}