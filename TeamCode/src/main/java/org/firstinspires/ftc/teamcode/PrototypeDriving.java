package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "drivingproto")
public class PrototypeDriving extends LinearOpMode {

    private DcMotor br;
    private DcMotor fl;
    private DcMotor bl;
    private DcMotor fr;
    private DcMotor arm;
    private DcMotor bar;
    private Servo leftServo;
    private Servo rightServo;
    private Servo grabberServo;
    private int speed = 1;
    private int mode = 0;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {

        br = hardwareMap.get(DcMotor.class, "br");
        fl = hardwareMap.get(DcMotor.class, "fl");
        bl = hardwareMap.get(DcMotor.class, "bl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        arm = hardwareMap.get(DcMotor.class, "arm");
        bar = hardwareMap.get(DcMotor.class, "bar");
        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");
        grabberServo = hardwareMap.get(Servo.class, "grabberServo");


        // Put initialization blocks here.
        // br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.


            while (opModeIsActive()) {

                double r = Math.hypot(-gamepad1.left_stick_y, gamepad1.left_stick_x);
                double robotAngle = Math.atan2(gamepad1.left_stick_x, -gamepad1.left_stick_y) - Math.PI / 4;
                double rightX = gamepad1.right_stick_x;
                final double flv = r * Math.cos(robotAngle) + rightX;
                final double frv = r * Math.sin(robotAngle) + rightX;
                final double blv = r * Math.sin(robotAngle) - rightX;
                final double brv = r * Math.cos(robotAngle) - rightX;

                ((DcMotorEx) bl).setPower(blv * speed * 0.5);
                ((DcMotorEx) br).setPower(brv * speed * 0.5);
                ((DcMotorEx) fl).setPower(flv * speed * 0.5);
                ((DcMotorEx) fr).setPower(frv * speed * 0.5);


                // ((DcMotorEx) bl).setVelocity(537.6 * blv * speed);
                // ((DcMotorEx) br).setVelocity(537.6 * brv * speed);
                // ((DcMotorEx) fl).setVelocity(623 * flv * speed);
                // ((DcMotorEx) fr).setVelocity(623 * frv * speed);


                if(gamepad1.a && arm.getCurrentPosition() > -15700){
                    ((DcMotorEx) arm).setVelocity(537.6 * 1);
                }else if (gamepad1.b && arm.getCurrentPosition() < -100){
                    ((DcMotorEx) arm).setVelocity(-537.6 * 1);
                }else{
                    ((DcMotorEx) arm).setVelocity(0);
                }

                if(gamepad1.left_bumper) {
                    leftServo.setPosition(leftServo.getPosition() + 0.01);
                    rightServo.setPosition(rightServo.getPosition() - 0.01);
                } else if(gamepad1.right_bumper){
                    leftServo.setPosition(leftServo.getPosition() + 0.01);
                    rightServo.setPosition(rightServo.getPosition() - 0.01);
                }

                if(gamepad1.left_trigger != 0){
                    grabberServo.setPosition(grabberServo.getPosition() + (gamepad1.left_trigger / 100));
                } else if (gamepad1.right_trigger != 0){
                    grabberServo.setPosition(grabberServo.getPosition() - (gamepad1.right_trigger / 100));
                }

                if(gamepad1.dpad_up){
                    bar.setPower(1);
                }else if (gamepad1.dpad_down){
                    bar.setPower(-1);
                }else{
                    bar.setPower(0);
                }

                if(gamepad1.x){
                    speed = 1;
                }
                if(gamepad1.y){
                    speed = 2;
                }

                telemetry.addData("fl", fl.getCurrentPosition());
                telemetry.addData("bl", bl.getCurrentPosition());
                telemetry.addData("fr", fr.getCurrentPosition());
                telemetry.addData("br", br.getCurrentPosition());
                telemetry.addData("arm", arm.getCurrentPosition());

                telemetry.update();
            }
        }
    }
}