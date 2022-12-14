package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class ConfigureHard {
    private DcMotor frontleftDrive = null;
    private DcMotor frontrightDrive = null;
    private DcMotor backleftDrive = null;
    private DcMotor backrightDrive = null;

    public DcMotor spool = null;

    private ColorSensor sensor1 = null;

    private Servo leftClaw = null;
    private Servo rightClaw = null;

    public void initialize(HardwareMap hardwareMap) {

        frontleftDrive  = hardwareMap.get(DcMotor.class, "front_left_drive");
        frontrightDrive = hardwareMap.get(DcMotor.class, "front_right_drive");
        backleftDrive  = hardwareMap.get(DcMotor.class, "back_left_drive");
        backrightDrive = hardwareMap.get(DcMotor.class, "back_right_drive");

        spool = hardwareMap.get(DcMotor.class, "spool");

        sensor1 = hardwareMap.get(ColorSensor.class, "sensor1");

        leftClaw = hardwareMap.get(Servo.class, "Lclaw");
        rightClaw = hardwareMap.get(Servo.class, "Rclaw");

        spool.setDirection(DcMotor.Direction.FORWARD);
        frontleftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontrightDrive.setDirection(DcMotor.Direction.REVERSE);
        backleftDrive.setDirection(DcMotor.Direction.FORWARD);
        backrightDrive.setDirection(DcMotor.Direction.REVERSE);
    }
    public void AdjMotors(double frontleftPower,double frontrightPower,double backleftPower,double backrightPower){
        frontleftDrive.setPower(0.5*frontleftPower);
        frontrightDrive.setPower(0.5*frontrightPower);
        backleftDrive.setPower(0.5*backleftPower);
        backrightDrive.setPower(0.5*backrightPower);
    }
    public void strafing(boolean right, long t) throws InterruptedException{
        if(!right){
            AdjMotors(-1,-1,1,1);
        }else{
            AdjMotors(1,1,-1,-1);
        }
        Thread.sleep(t);
        AdjMotors(0,0,0,0);
    }
    public void forward(boolean isFastOn,boolean isBackwardsOn, long t) throws InterruptedException{
        if(!isFastOn && !isBackwardsOn){
            AdjMotors(1,1,1,1);
        }else if(isFastOn && !isBackwardsOn){
            AdjMotors(2,2,2,2);
        }else if(isBackwardsOn && !isFastOn){
            AdjMotors(-1,-1,-1,-1);
        }else if(isFastOn && isBackwardsOn){
            AdjMotors(-2,-2,-2,-2);
        }
        Thread.sleep(t);
        AdjMotors(0,0,0,0);
    }
    public void pickGoAndDrop(int t) throws InterruptedException{
        Thread.sleep(250);
        doSpool(0.6);
        Thread.sleep(2150);
        doSpool(0);
        forward(false,false,190);
        Thread.sleep(500);
        ClawMove(false);
        Thread.sleep(250);
        ClawMove(true);
        forward(false,true,t);
        doSpool(-0.6);
        Thread.sleep(1000);
        doSpool(0);
        ClawMove(false);
        Thread.sleep(250);
    }
    // Color Sensor Code. Run grab cone before running this in the order of operations

    public void doAutonomous(String TS) throws InterruptedException{
        ClawMove(false);
        Thread.sleep(1000);
        ClawMove(true);

        if(TS=="f5"){
            strafing(true,600);
        }
        if(TS=="a5"){
            strafing(true,650);
        }
        if(TS=="f2"){
            strafing(true,600);
        }
        if(TS=="a2"){
            strafing(false,600);
        }else{
            Thread.sleep(1000);
        }

        pickGoAndDrop(190);

    }
    public void parkingColorlessAutonomous(String T_S) throws InterruptedException{
        if(T_S=="f5"){
            Thread.sleep(1000);
        }else if(T_S=="a5"){
            Thread.sleep(1000);
        }else if(T_S=="f2"){
            strafing(false,1500);
        }else if(T_S=="a2"){
            strafing(true,1500);
        }else{
            Thread.sleep(1000);}
    }
    public String getColor(){
        int r = red();
        int g = green();
        int b = blue();
        if(r > b && r > g){
            return "Red";
        }
        if(g > b && g > r){
            return "Green";
        }
        if(b > r && b > g){
            return "Blue";
        }else{
            return "ColorUndefined";

        }
    }
    public int red(){
        return sensor1.red();
    }
    public int green(){
        return sensor1.green();
    }
    public int blue(){
        return sensor1.blue();
    }
    public void doSpool(double spoolPower){
        spool.setPower(spoolPower);
    }
    public void ClawMove(boolean isclawmovePower){
        if(isclawmovePower){
            rightClaw.setPosition(0.3);
            leftClaw.setPosition(0.7);
        }else{
            rightClaw.setPosition(0);
            leftClaw.setPosition(1);
        }
    }
    // todo: write your code here
}