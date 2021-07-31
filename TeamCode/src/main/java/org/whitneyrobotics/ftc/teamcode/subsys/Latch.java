package org.whitneyrobotics.ftc.teamcode.subsys;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.whitneyrobotics.ftc.teamcode.lib.util.Functions;
import org.whitneyrobotics.ftc.teamcode.lib.util.Toggler;

public class Latch {
    private DcMotor latchMotor;

    public Latch(HardwareMap hardwareMap){
        latchMotor = hardwareMap.dcMotor.get("latchMotor");
    }
    //These positions are really random

    //4 revolutions
    private double grabbingPos = 2150.4;
    //0 revolutions
    private double retraction = 0;
    //5 revolutions
    private double extension = 2688;

    private Toggler latchToggler = new Toggler(3);

    private boolean latchingInProgress = false;
    private boolean firstLatchCycle = true;

    private double error;

    public boolean latchingInProgress() {return latchingInProgress;}

    private PID latchController = new PID(20.1,2,12.4);

    public void extendAndRetractArm(double target){
        error = target - latchMotor.getCurrentPosition();
        if(firstLatchCycle){
            latchingInProgress = true;
            latchController.init();
            firstLatchCycle = false;
        }

        double power = Functions.map(Math.abs(latchController.calculate(target,latchMotor.getCurrentPosition())),0,1800,0.15,0.99);
        if (error < 0){
            power = -power;
        } else if (power > 0){
            power = Math.abs(power);
        }
        if (Math.abs(power) > 3){
            latchingInProgress = true;
            latchMotor.setPower(power);
        } else{
            latchingInProgress = false;
            firstLatchCycle = true;
            latchMotor.setPower(0.0);
        }
    }

    private String latchStatus = "";
    public String latchStatus() {return latchStatus;}

    private int engageStep = 0;

    public void operate(boolean extensionInput){
        if (!latchingInProgress) {
            latchToggler.changeState(extensionInput);
        }
        switch (latchToggler.currentState()){
            case 0:
                reset();
            case 1:
                latchStatus = "Engaging latch";
                switch (engageStep){
                    case 0:
                        extendAndRetractArm(grabbingPos);
                        if (!latchingInProgress){
                            engageStep++;
                        }
                        break;
                    case 1:
                        extendAndRetractArm(retraction);
                        break;
                }
            case 2:
                engageStep = 0;
                latchStatus = "Disengaging latch";
                extendAndRetractArm(extension);
                break;
        }
    }
    public void reset(){
        latchStatus = "Resetting latch mechanism";
        extendAndRetractArm(retraction);
    }

}
