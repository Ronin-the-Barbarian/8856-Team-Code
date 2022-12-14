/*
Copyright 2022 FIRST Tech Challenge Team 8856

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Remove a @Disabled the on the next line or two (if present) to add this opmode to the Driver Station OpMode list,
 * or add a @Disabled annotation to prevent this OpMode from being added to the Driver Station
 */
@Autonomous

public class AutoColor extends LinearOpMode {
    private ConfigureHard conf = new ConfigureHard();

    @Override
    public void runOpMode() throws InterruptedException {
        conf.initialize(hardwareMap);
        String T_S;
        T_S="a2";
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Color", conf.getColor());
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        conf.strafing(false,25);
        conf.forward(false,false,550);
        Thread.sleep(1000);

        if (conf.getColor()=="Red"){
            telemetry.addData("Color:"," r");
            telemetry.update();
            gotored();
        }else if(conf.getColor()=="Green"){
            telemetry.addData("Color:"," g");
            telemetry.update();
            gotogreen();
        }else if(conf.getColor()=="Blue"){
            telemetry.addData("Color:"," b");
            telemetry.update();
            gotoblue();
        }else{
            telemetry.addData("Color:"," N/A");
            telemetry.update();
        }
        sleep(5000);
    }
    //1
    public void gotored() throws InterruptedException{
        conf.forward(false,true,750);
        conf.strafing(true,1000);
        conf.forward(false,false,1000);
    }
    //2
    public void gotogreen() throws InterruptedException{
        conf.forward(false,true,750);
        conf.strafing(true,1000);
        conf.forward(false,false,1000);
        conf.strafing(false,1000);
    }
    //3
    public void gotoblue() throws InterruptedException{
        conf.forward(false,true,750);
        conf.strafing(false,1000);
        conf.forward(false,false,1000);
    }
}
