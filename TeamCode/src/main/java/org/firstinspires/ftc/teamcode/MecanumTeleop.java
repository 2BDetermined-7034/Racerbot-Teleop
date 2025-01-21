package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.helpers.Debounce;

@TeleOp(name="RacerBot MecanumDrive Standard", group="Linear OpMode")
public class MecanumTeleop extends LinearOpMode {
    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        double limiter = 1;
        double y;
        double x;
        double rx;
        double modulator;
        boolean lowPowerMode = false;
        double minSpeed = .5;
        double maxSpeed = 1;
        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        Debounce debounceSqaure = new Debounce();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            if (debounceSqaure.isPressed(gamepad1.square)) {
                lowPowerMode = !lowPowerMode;
            }

            modulator = lowPowerMode ? minSpeed : maxSpeed;

            y = -gamepad1.left_stick_y;
            x = gamepad1.left_stick_x * 1.1;
            rx = gamepad1.right_stick_x;

            frontLeftPower = ((y + x + rx)*modulator);
            frontRightPower = ((y - x - rx)*modulator);
            backLeftPower = ((y - x + rx)*modulator);
            backRightPower = ((y + x - rx)*modulator);

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            telemetry.addLine("Status: " + "Online");
            telemetry.addLine("Low Power Mode: " + lowPowerMode);
            telemetry.update();
        }
    }
}