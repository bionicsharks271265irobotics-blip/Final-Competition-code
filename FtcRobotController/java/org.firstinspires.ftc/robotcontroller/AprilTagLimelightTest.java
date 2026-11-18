package org.firstinspires.ftc.robotcontroller;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
@TeleOp (name = "LimelightTest1")
public class AprilTagLimelightTest extends OpMode {
    private Limelight3A limelight;
    private IMU imu;

    @Override
    public void init() {
        limelight= hardwareMap.get(Limelight3A.class, "Limelight" );
        limelight.pipelineSwitch(0); // april tag#33 pipeline
        limelight.start();
        telemetry.addData("Starting","0");
        imu = hardwareMap.get(IMU.class,"imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
        telemetry.addData("Initializing","0");
    }

    @Override
    public void start() {
        telemetry.addData("Start","0");
    }

    @Override
    public void loop() {
        telemetry.addData("Loop","0");
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight.updateRobotOrientation(orientation.getYaw());
        LLResult llResult = limelight.getLatestResult();
        telemetry.addData("Initializing","0");
        if (llResult != null && llResult.isValid()) {
            telemetry.addData("Detected an AprilTag","0");
        }
        else {
            telemetry.addData("Didn't detect an AprilTag","0");
        }

    }
}
