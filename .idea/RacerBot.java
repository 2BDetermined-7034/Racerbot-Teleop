//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class RacerBot extends LinearOpMode {
    public RacerBot() {
    }

    public void runOpMode() {
        DcMotor driveMotor = (DcMotor)this.hardwareMap.dcMotor.get("driveMotor");
        DcMotor steerMotor = (DcMotor)this.hardwareMap.dcMotor.get("steerMotor");
        this.waitForStart();
        if (!this.isStopRequested()) {
            while(this.opModeIsActive()) {
                //Left stick handles lateral
                double lateral = (double)(-this.gamepad2.left_stick_y) * 0.5;
                //Right stick handles steer
                double steer = (double) this.gamepad1.left_stick_x * 0.5;
                //Motor power assignment
                driveMotor.setPower(lateral);
                steerMotor.setPower(steer);
            }

        }
    }
}