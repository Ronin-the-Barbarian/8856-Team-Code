package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="SDoU in work", group="Iterative Opmode")

public class Index extends OpMode
{
    private ConfigureHard conf = new ConfigureHard();
    private boolean isFast = false;
    private boolean fastButtonOn = false;
    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).

        // Do we need to change?
        conf.initialize(hardwareMap);


        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop(){
        // Setup a variable for each drive wheel to save power level for telemetry
        double frontleftPower;
        double frontrightPower;
        double backleftPower;
        double backrightPower;
        int r = conf.red();
        int g = conf.green();
        int b = conf.blue();
        if(r>=g && r>=b){
            telemetry.addData("Color","Red");
        }else if(b>=r&&b>=g){
            telemetry.addData("Color","Blue");
        }else if(g>=r&&g>=b){
            telemetry.addData("Color","Green");
        }else{
            telemetry.addData("Color","N/A");
        }
        //telemetry.addData("Color:",conf.getColor());
        //
        boolean isclawmovePower;
        // Choose to drive using either Tank Mode, or POV Mode
        // Comment out the method that's not used.  The default below is POV.

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.right_stick_x;
        double strafe = gamepad1.left_stick_x;
        frontleftPower    = Range.clip(drive + turn+strafe, -1.0, 1.0) ;
        frontrightPower   = Range.clip(drive - turn+strafe, -1.0, 1.0) ;
        backleftPower     = Range.clip(drive + turn-strafe, -1.0, 1.0) ;
        backrightPower    = Range.clip(drive - turn-strafe, -1.0, 1.0) ;
        /*
        double drive = -gamepad1.dpad.left;
        */
        // Tank Mode uses one stick to control each wheel.
        // - This requires no math, but it is hard to drive forward slowly and keep straight.
        // leftPower  = -gamepad1.left_stick_y ;
        // rightPower = -gamepad1.right_stick_y ;

        // Send calculated power to wheels
        if(!fastButtonOn && gamepad1.x){
            fastButtonOn= true;
            isFast = !isFast;
        } else if (fastButtonOn && !gamepad1.x){
            fastButtonOn=false;
        }

        if(isFast){
            conf.AdjMotors(2*frontleftPower, 2*frontrightPower, 2*backleftPower, 2*backrightPower);
        }else{
            conf.AdjMotors(frontleftPower, frontrightPower, backleftPower, backrightPower);
        }
        isclawmovePower = gamepad1.b;//
        conf.ClawMove(isclawmovePower);
        if(gamepad1.dpad_up){//
            conf.doSpool(1);
        }else if(gamepad1.dpad_down){//
            conf.doSpool(-0.5);
        }else{
            conf.doSpool(0);
        }
        // Show the elapsed game time and wheel power.
        telemetry.addData("Red","%d",conf.red());
        telemetry.addData("Blue","%d",conf.blue());
        telemetry.addData("Green","%d",conf.green());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", frontleftPower, frontrightPower);
        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
