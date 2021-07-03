package org.whitneyrobotics.ftc.teamcode.subsys;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.whitneyrobotics.ftc.teamcode.lib.util.Toggler;

public class IntakePerry {
    private DcMotor intake1;
    private DcMotor intake2;
    private Servo intakePusher;
    private Toggler intakePowerToggler = new Toggler(2);
    private Toggler dropdownToggler = new Toggler(2);
    public String dropdownStatus;
    public String stateDesc;

    final private double intakePower = 1.0;

    private enum DropdownPositions {
        UP, DOWN
    }

    private double[] dropdownPositions = {0.97, 0.66};

    public void intake(HardwareMap hardwareMap){
        intake1 = hardwareMap.dcMotor.get("intake1");
        intake2 = hardwareMap.dcMotor.get("intake2");
        intakePusher = hardwareMap.servo.get("intakePusher");
    }
    //auto
    public void autoDropdown(DropdownPositions position) {
            intakePusher.setPosition(dropdownPositions[position.ordinal()]);
    }

    //teleop
    public void operate(boolean toggleInput, boolean directionInput, boolean dropdownInput){
        intakePowerToggler.changeState(toggleInput);
        dropdownToggler.changeState(dropdownInput);
        if(dropdownToggler.currentState() != 1){
            intakePusher.setPosition(dropdownPositions[DropdownPositions.UP.ordinal()]);
            dropdownStatus = "Intake Up";
        } else if(dropdownToggler.currentState() == 1){
            intakePusher.setPosition(dropdownPositions[DropdownPositions.DOWN.ordinal()]);
            dropdownStatus = "Intake Down";
        }

        if(intakePowerToggler.currentState() != 1){
            intake1.setPower(0.0);
            intake2.setPower(0.0);
            stateDesc = "Intake Off";
        } else if(intakePowerToggler.currentState() == 1){
            intake1.setPower(intakePower);
            intake2.setPower(intakePower);
            stateDesc = "Intake Forwards";
        } else if(directionInput){
            intake1.setPower(-intakePower);
            intake2.setPower(-intakePower);
            stateDesc = "Intake Reverse";
        }
    }

}
