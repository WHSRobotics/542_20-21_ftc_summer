package org.whitneyrobotics.ftc.teamcode.autoop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.whitneyrobotics.ftc.teamcode.lib.geometry.Coordinate;
import org.whitneyrobotics.ftc.teamcode.lib.geometry.Position;
import org.whitneyrobotics.ftc.teamcode.subsys.WHSRobotImpl;


public class demoAutoOp1 extends OpMode {
    WHSRobotImpl robot = new WHSRobotImpl(hardwareMap);
    int driveStep = 0;
    String telemetryMsg;
    Position craterCenterBlock = new Position(-1400,-1400);
    Position center = new Position(0,0);
    Position crater = new Position(-1600,-1600);
    Position depot = new Position(-1650,1650);
    //assuming that crater corner is in the top left (-1800, -1800) and depot is in the top right (-1800,1800)
    Coordinate starting = new Coordinate(-200,-200,45);


    @Override
    public void init() {
        robot.setCoordinate(starting);
    }

    @Override
    public void loop() {
        robot.estimatePosition();
        robot.estimateHeading();

        switch (driveStep){
            case 0:
                telemetryMsg = "Start on Crater Side of the Lander";
                //do some unlatching stuff here
                boolean latchingInProgress = false;
                if(!latchingInProgress){
                    driveStep++;
                }
                break;
            case 1:
                telemetryMsg = "Knocking over Crater Middle Block";
                robot.driveToTarget(craterCenterBlock,false);
                if(!robot.rotateToTargetInProgress()){
                    driveStep++;
                }
                break;
            case 2:
                telemetryMsg = "Returning to Center without Rotation";
                robot.driveToTarget(center,true);
                if(!robot.driveToTargetInProgress()){
                    driveStep++;
                }
                break;
            case 3:
                telemetryMsg = "Drive to Depot";
                robot.driveToTarget(depot,false);
                if(!robot.driveToTargetInProgress()){
                    driveStep++;
                }
                break;
            case 4:
                telemetryMsg = "Drive to crater";
                robot.driveToTarget(crater,false);
                if(!robot.driveToTargetInProgress()){
                    driveStep++;
                }
                break;
            default:
                telemetryMsg = "Parked";
                break;
        }
    }
}
