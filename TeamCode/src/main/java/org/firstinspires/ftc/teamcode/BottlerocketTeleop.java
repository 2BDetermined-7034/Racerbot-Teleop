package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="RacerBot Bottlerocket Standard", group="Linear OpMode")

public class BottlerocketTeleop extends LinearOpMode {

    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        //Construct motor objects to ports
        DcMotor omniMotor = hardwareMap.get(DcMotor.class, "frontMotor");
        DcMotor driveMotor = hardwareMap.get(DcMotor.class, "backMotor");

        omniMotor.setDirection(DcMotor.Direction.REVERSE);
        driveMotor.setDirection(DcMotor.Direction.FORWARD);

        omniMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        //Initialize variables
        boolean underclockStatus = false;

        double limiter = 1;
        double underclockValue;
        double lateral;
        double steer;
        boolean xHeld = false;

        while (opModeIsActive()) {

            if (gamepad1.left_trigger > 0 && limiter < 3.5) {
                limiter += .00005;
            }
            else if (gamepad1.right_trigger > 0 && limiter > 1) {
                limiter -= .00005;
            }

            //Take input from joysticks
            lateral = gamepad1.left_stick_y;
            steer =  gamepad1.right_stick_x;

            //Send power to motors
            omniMotor.setPower(steer / limiter);
            driveMotor.setPower(lateral / limiter);

            //Telemetry log
            telemetry.addData("Status", "Run Time = " + runtime + "\n");
            telemetry.addData("Limiting value ",String.format("%.2g%n", limiter));
            telemetry.addData("Motor status (front,back) : ", steer + ", " + lateral);
            telemetry.update();
        }
    }}
