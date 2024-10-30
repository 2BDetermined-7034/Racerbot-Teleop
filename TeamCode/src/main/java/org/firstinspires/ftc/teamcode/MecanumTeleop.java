package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="RacerBot MecanumDrive Standard", group="Linear OpMode")
public class MecanumTeleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        double limiter = 1;

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            if (gamepad1.left_trigger > 0 && limiter < 3.5) {
                limiter += .00005;
            }
            else if (gamepad1.right_trigger > 0 && limiter > 1) {
                limiter -= .00005;
            }

            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.left_stick_x * 1.1;
            double x = -gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower/limiter);
            backLeftMotor.setPower(backLeftPower/limiter);
            frontRightMotor.setPower(frontRightPower/limiter);
            backRightMotor.setPower(backRightPower/limiter);

            telemetry.addData("Status","Online");
            telemetry.addData("Limiting value ",String.format("%.2g%n", limiter));
            telemetry.update();
        }
    }
}