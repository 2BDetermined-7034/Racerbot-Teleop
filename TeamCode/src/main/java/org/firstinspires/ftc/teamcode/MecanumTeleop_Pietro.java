package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name="RacerBot MecanumDrive Special:Pietro", group="Linear OpMode")
public class MecanumTeleop_Pietro extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        DcMotor shaftMotor = hardwareMap.dcMotor.get("shaftMotor");

        ColorSensor colorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        NormalizedRGBA normalColorsCache;

        double powerLimiter = 1;
        double denominator;
        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        String shaftMotorStatus;

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            normalColorsCache = ((NormalizedColorSensor) colorSensor).getNormalizedColors();

            if (gamepad1.left_trigger > 0 && powerLimiter < 3.5) {
                powerLimiter += .00005;
            }
            else if (gamepad1.right_trigger > 0 && powerLimiter > 1) {
                powerLimiter -= .00005;
            }

            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.left_stick_x * 1.1;
            double x = gamepad1.right_stick_x;

            denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            frontLeftPower = ((y + x + rx) / denominator)/powerLimiter;
            backLeftPower = ((y - x + rx) / denominator)/powerLimiter;
            frontRightPower = ((y - x - rx) / denominator)/powerLimiter;
            backRightPower = ((y + x - rx) / denominator)/powerLimiter;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(-backRightPower);

            if (gamepad1.left_bumper) {
                shaftMotor.setPower(1);
            }
            else if (gamepad1.right_bumper) {
                shaftMotor.setPower(-1);
            }
            else {
                shaftMotor.setPower(0);
            }

            shaftMotorStatus = shaftMotor.getPower() != 0 ? "Spinning" : "Inactive";

            telemetry.addData("Status","Online");
            telemetry.addData("Shaft Motor Status",shaftMotorStatus);
            telemetry.addData("Limiting value",String.format("%.2g%n", powerLimiter));

            telemetry.addData("Red", Double.parseDouble(JavaUtil.formatNumber(normalColorsCache.red, 3)));
            telemetry.addData("Green", Double.parseDouble(JavaUtil.formatNumber(normalColorsCache.green, 3)));
            telemetry.addData("Blue", Double.parseDouble(JavaUtil.formatNumber(normalColorsCache.blue, 3)));

            telemetry.update();
        }
    }
}
