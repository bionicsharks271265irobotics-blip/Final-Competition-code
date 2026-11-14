package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "shoot_park")
public class shoot_park extends LinearOpMode {

    private DcMotor fLeft, bLeft, fRight, bRight;

    private DcMotorEx shooterMotor , storage1, storage2, intake;


    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 537.6;
    static final double DRIVE_GEAR_REDUCTION = 1.0;
    static final double WHEEL_DIAMETER_INCHES = 4.0;
    static final double COUNTS_PER_INCH =
            (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                    (WHEEL_DIAMETER_INCHES * 3.1415);


    @Override
    public void runOpMode() {

        fLeft  = hardwareMap.get(DcMotor.class, "fLeft");
        bLeft  = hardwareMap.get(DcMotor.class, "bLeft");
        fRight = hardwareMap.get(DcMotor.class, "fRight");
        bRight = hardwareMap.get(DcMotor.class, "bRight");

        shooterMotor =  hardwareMap.get(DcMotorEx.class, "shooterMotor");
        storage1 =  hardwareMap.get(DcMotorEx.class, "storage1");
        storage2 =  hardwareMap.get(DcMotorEx.class, "storage2");
        intake = hardwareMap.get(DcMotorEx.class, "intake");



        fLeft.setDirection(DcMotor.Direction.FORWARD);
        bLeft.setDirection(DcMotor.Direction.FORWARD);
        fRight.setDirection(DcMotor.Direction.REVERSE);
        bRight.setDirection(DcMotor.Direction.REVERSE);

        shooterMotor.setDirection(DcMotorEx.Direction.FORWARD);
        storage1.setDirection(DcMotorEx.Direction.FORWARD);
        storage2.setDirection(DcMotorEx.Direction.REVERSE);
        intake.setDirection(DcMotorEx.Direction.FORWARD);

        fLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        shooterMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        storage1.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        storage2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);



        fLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        shooterMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        storage1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        storage2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);



        waitForStart();

        // turn right keep false
        // turn left keep true
        // move forwards keep inches negative
        // move backward keep inches positve




        // shoot pollen 4x

        encoderShooter(1472.5, 110, -100, 5);
        //move backward
        encoderDrive(1,  24, 6);
        // turn left
        encoderDriveTurn(1,18,4,true);
        // move forward
        encoderDrive(1, -48, 6);



    }

    public void encoderDrive(double speed, double inches, double timeoutS) {

        int moveCounts = (int) (inches * COUNTS_PER_INCH);

        fLeft.setTargetPosition(fLeft.getCurrentPosition() + moveCounts);
        fRight.setTargetPosition(fRight.getCurrentPosition() + moveCounts);
        bLeft.setTargetPosition(bLeft.getCurrentPosition() + moveCounts);
        bRight.setTargetPosition(bRight.getCurrentPosition() + moveCounts);


        fLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        fLeft.setPower(Math.abs(speed));
        fRight.setPower(Math.abs(speed));
        bLeft.setPower(Math.abs(speed));
        bRight.setPower(Math.abs(speed));

        while (opModeIsActive() &&
                fLeft.isBusy() && fRight.isBusy() &&
                bLeft.isBusy() && bRight.isBusy()) {
            telemetry.addData("Driving", "Running");
            telemetry.update();
        }

        stopMotors();
        resetEncoders();
    }

    public void encoderShooter(double velocity, double velocity2 ,double inches, double timeouts){
        int moveCounts = (int) (inches * COUNTS_PER_INCH);
        int leftTarget = (int)(inches * COUNTS_PER_INCH);
        int rightTarget = (int)(inches * COUNTS_PER_INCH);


        shooterMotor.setTargetPosition(leftTarget);
        storage1.setTargetPosition(rightTarget);
        storage2.setTargetPosition(rightTarget);
        intake.setTargetPosition(rightTarget);


        shooterMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        storage1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        storage2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        shooterMotor.setVelocity(velocity);
        storage1.setVelocity(velocity2);
        storage2.setVelocity(velocity2);
        intake.setVelocity(velocity2);





        while (opModeIsActive() && shooterMotor.isBusy() && storage1.isBusy() && storage2.isBusy() && intake.isBusy()) {
            telemetry.addData("spinning", "Running");
            telemetry.update();

        }
        stopMotors();
        resetEncoders();
    }

    public void encoderDriveTurn(double speed, double inches, double timeoutS, boolean right) {

        int moveCounts = (int) (inches * COUNTS_PER_INCH);

        if (right) {
            fLeft.setTargetPosition(moveCounts);
            bLeft.setTargetPosition(moveCounts);
            fRight.setTargetPosition(-moveCounts);
            bRight.setTargetPosition(-moveCounts);
        } else {
            fLeft.setTargetPosition(-moveCounts);
            bLeft.setTargetPosition(-moveCounts);
            fRight.setTargetPosition(moveCounts);
            bRight.setTargetPosition(moveCounts);
        }

        fLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        fLeft.setPower(Math.abs(speed));
        fRight.setPower(Math.abs(speed));
        bLeft.setPower(Math.abs(speed));
        bRight.setPower(Math.abs(speed));

        while (opModeIsActive() &&
                fLeft.isBusy() && fRight.isBusy() &&
                bLeft.isBusy() && bRight.isBusy()) {
            telemetry.addData("Turning", "Running");
            telemetry.update();
        }

        stopMotors();
        resetEncoders();
    }

    private void stopMotors() {
        fLeft.setPower(0);
        fRight.setPower(0);
        bLeft.setPower(0);
        bRight.setPower(0);
        shooterMotor.setVelocity(0);
        storage1.setVelocity(0);
        storage2.setVelocity(0);
        intake.setVelocity(0);

    }

    private void resetEncoders() {
        fLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        shooterMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        storage1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        storage2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);



    }
}
