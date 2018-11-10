/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="MainTeleOP", group="FTC")
//@Disabled
public class EchoPulse_MainTeleOP extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor motorSF, motorDF, motorDS, motorSS, motorCarlig = null;

    private double speedDevider = 1;
    private boolean toggleSpeed = false;
    private boolean toggleBrake = false;

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.addData("IT", "FUCKING WORKED");
        telemetry.update();

        motorSF = hardwareMap.get(DcMotor.class, "MotorSF");
        motorDF = hardwareMap.get(DcMotor.class, "MotorDF");
        motorDS = hardwareMap.get(DcMotor.class, "MotorDS");
        motorSS = hardwareMap.get(DcMotor.class, "MotorSS");
        motorCarlig = hardwareMap.get(DcMotor.class, "MotorCarlig");

        motorSF.setDirection(DcMotor.Direction.FORWARD);
        motorDF.setDirection(DcMotor.Direction.FORWARD);
        motorDS.setDirection(DcMotor.Direction.FORWARD);
        motorSS.setDirection(DcMotor.Direction.FORWARD);
        motorCarlig.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        runtime.reset();
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //CADRU
            double sasiuPowerY = (-gamepad1.left_stick_y / 1.5) / speedDevider;
            double sasiuPowerX = (gamepad1.left_stick_x / 1.5) / speedDevider;
            double rotPower = 0.7;

            if(gamepad1.b && toggleBrake) {setBehaviour("Break");toggleBrake = false;sleep(100);}
            else if (gamepad1.b && !toggleBrake) {setBehaviour("Float");toggleBrake = true;sleep(100);}

            if(gamepad1.a && toggleSpeed) {speedDevider = 2.75; rotPower = 0.005;toggleSpeed = false;sleep(100);} //SlowMode ON
            else if(gamepad1.a && !toggleSpeed) { speedDevider = 1; rotPower = 0.7;toggleSpeed = true;sleep(100);} //SlowMode OFF

            if(gamepad1.left_stick_y == 0) {
                motorSF.setPower(0);
                motorDF.setPower(0);
                motorDS.setPower(0);
                motorSS.setPower(0);
            }

            if(-gamepad1.left_stick_y > 0.2 || -gamepad1.left_stick_y < -0.2) {
                motorSF.setPower(-sasiuPowerY);
                motorDF.setPower(sasiuPowerY);
                motorDS.setPower(sasiuPowerY);
                motorSS.setPower(-sasiuPowerY);
            }
            else if(gamepad1.left_stick_x > 0.2 || gamepad1.left_stick_x < -0.2) {
                motorSF.setPower(-sasiuPowerX);
                motorDF.setPower(-sasiuPowerX);
                motorDS.setPower(sasiuPowerX);
                motorSS.setPower(sasiuPowerX);
            }

            if(gamepad1.left_bumper) {
                if(speedDevider == 1)   ///if SlowMode OFF
                {
                    motorSF.setPower(-rotPower);
                    motorDF.setPower(rotPower);
                    motorDS.setPower(rotPower);
                    motorSS.setPower(-rotPower);
                }
                else   //if SlowMode ON
                {
                    motorSF.setPower(-rotPower);
                    motorDS.setPower(rotPower);
                }
            }
            if(gamepad1.right_bumper) {
                if(speedDevider == 1)      ///if SlowMode OFF
                {
                    motorSF.setPower(rotPower);
                    motorDF.setPower(-rotPower);
                    motorDS.setPower(-rotPower);
                    motorSS.setPower(rotPower);
                }
                else    //if SlowMode ON
                {
                    motorSF.setPower(rotPower);
                    motorDS.setPower(-rotPower);
                }
            }
            if (gamepad1.left_stick_button) {
                motorSS.setPower(-rotPower);
                motorDF.setPower(rotPower);
            }
            if (gamepad1.right_stick_button) {
                motorDS.setPower(-rotPower);
                motorSF.setPower(rotPower);
            }

            // Telemetry
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("MotorSF: ", motorSF.getCurrentPosition());
            telemetry.addData("MotorDF: ", motorDF.getCurrentPosition());
            telemetry.addData("MotorDS: ", motorDS.getCurrentPosition());
            telemetry.addData("MotorSS: ", motorSS.getCurrentPosition());
            telemetry.addData("MotorCarlig: ", motorCarlig.getCurrentPosition());
            telemetry.addData("sasiuPowerX ", sasiuPowerX);
            telemetry.update();

            /* Coboara */
            if(gamepad2.left_bumper) {
                runToPoint(motorCarlig, rotPower, 0);
            }
            /* Urca */
            else if(gamepad2.right_bumper) {
                runToPoint(motorCarlig, rotPower, -10000);
            }
            /*
            * Oprire de urgenta, in caz de eroare
            * Dupa teste poate fi stearsa
            */
            else {
                motorCarlig.setPower(0);
            }
        }
    }
    private void runToPoint(DcMotor motor, double power, int distance) {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setTargetPosition(distance);
        motor.setPower(power);
        while (motor.isBusy()) {
            idle();
            if (!opModeIsActive() || Thread.currentThread().isInterrupted()) {
                try {
                    throw new InterruptedException();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        stopMotor(motor);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void stopMotor(DcMotor motor) {
        motor.setPower(0);
    }

    private void setBehaviour(String str){
        if(str.equals("Break")) {
            motorSF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorDF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorDS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorSS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        else if(str.equals("Float")) {
            motorSF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motorDF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motorDS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motorSS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }
}
