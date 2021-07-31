package org.whitneyrobotics.ftc.teamcode.subsys;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.whitneyrobotics.ftc.teamcode.lib.util.Functions;
import org.whitneyrobotics.ftc.teamcode.lib.util.Toggler;

public class OmniArm {

    private DcMotor pitch;
    private DcMotor intake;
    private Servo gate;

    public OmniArm(HardwareMap hardwareMap){
        pitch = hardwareMap.dcMotor.get("pitch");
        intake = hardwareMap.dcMotor.get("intake");
        gate = hardwareMap.servo.get("gate");
    }

    private double degreesToTicks(double degrees){ return degrees*(360/537.6); }

    private boolean firstArmRotation = true;
    private boolean rotateOmniArmInProgress = false;

    private Toggler intakeToggler = new Toggler(2);
    private Toggler positionToggler = new Toggler(2);

    //assuming that the droppoff point is the default position of the omniArm
    private final double dropOff = 0;
    private final double intakePos = 120;

    private double intakePower = 0.99;

    private PID omniController = new PID(1,3,2);

    public void rotateArmToTarget(double dest){

        double error = dest - pitch.getCurrentPosition();

        if (firstArmRotation){
            rotateOmniArmInProgress = true;
            omniController.init();
            firstArmRotation = false;
        }
        double power = Functions.map(Math.abs(omniController.calculate(dest, pitch.getCurrentPosition())),0.01,180,0.1,0.99);
        if (error < 0) {
            power = -power;
        } else if (error > 0) {
            power = Math.abs(power);
        }
        if (Math.abs(error) > 0.5) {
            rotateOmniArmInProgress = true;
            pitch.setPower(power);
        } else {
            pitch.setPower(0.0);
            rotateOmniArmInProgress = false;
            firstArmRotation = true;
        }


    }

    public void setToDropoff(){
        rotateArmToTarget(dropOff);
    }

    public void setToIntake(){
        rotateArmToTarget(intakePos);
    }
    public boolean rotateOmniArmInProgress(){ return rotateOmniArmInProgress;}

    private enum ServoPositions {
        CLOSED, OPEN
    }

    private double[] servoPositions = {
        0.24,0.75
    };

    private String intakeStatus;
    public String intakeStatus(){return intakeStatus;}
    private String positionStatus;
    public String positionStatus(){return positionStatus;}

    public void operate(boolean intakeInput, boolean directionInput, boolean positionInput){
        intakeToggler.changeState(intakeInput);
        if(!rotateOmniArmInProgress){
            positionToggler.changeState(positionInput);
        }
        if (directionInput){
            intake.setPower(-intakePower);
            intakeStatus = "Intake Reverse";
        } else{
            switch (intakeToggler.currentState()){
                case 0:
                    intake.setPower(intakePower);
                    intakeStatus = "Intake Forwards";
                    break;
                default:
                    intake.setPower(0.0);
                    intakeStatus = "Intake Off";
                    break;
            }
        }
        switch (positionToggler.currentState()){
            case 0:
                setToDropoff();
                positionStatus = "Arm at lander";
                break;
            case 1:
                setToIntake();
                positionStatus = "Arm grounded";
                break;
            default:
                setToDropoff();
                positionStatus = "Arm at lander";
                break;
        }

    }


}
