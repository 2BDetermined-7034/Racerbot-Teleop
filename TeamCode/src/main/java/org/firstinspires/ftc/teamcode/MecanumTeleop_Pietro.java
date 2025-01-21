package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.helpers.Debounce;

@TeleOp(name="RacerBot MecanumDrive Special:Pietro", group="Linear OpMode")
public class MecanumTeleop_Pietro extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;
        double y;
        double rx;
        double x;
        double modulator;
        final double minSpeed = .5;
        final double maxSpeed = 1;
        boolean lowPowerMode = false;

        Debounce sqaureDebounce = new Debounce();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            if (sqaureDebounce.isPressed(gamepad1.square)) {
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
            telemetry.addLine("Low Power Mode?: " + lowPowerMode);

            telemetry.update();
        }
    }
}
