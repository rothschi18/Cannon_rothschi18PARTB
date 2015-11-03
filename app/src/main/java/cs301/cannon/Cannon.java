package cs301.cannon;


import android.view.MotionEvent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;


/**
 * Class represents a Cannon: A Cannon can fire, and reload, and calculate the position of its shot.
 *
 *
 *
 * @author Daniel Rothschilds
 * @version November 1 2015
 *
 *
 */
public class Cannon implements Animator {

    // instance variables
    private int count = 0;                  // counts the number of logical clock ticks
    private int score = 0;                  //Keeps track of the players score
    private boolean goBackwards = false;    //whether clock is ticking backwards
    private boolean isDrawn = false;        //Draw the targets only once
    public boolean reloadAndFire = false;   //Determines whether the player can fire
    boolean isFlying = false;               //Keep track of whether their is already a shot made
    int startX = 150;                       //Initial starting x
    int startY = 1200;                      //Initial starting y
    //Set a default ball w/ angle pi/4 and normal gravity
    public static Ball shot = new Ball(100, 0, 50, 3.1415/4, 9.81/5);
    //X and Y variables for Targets
    private int x1;
    private int x2;
    private int x3;

    private int y1;
    private int y2;
    private int y3;

    public static boolean isHit = false;    //Keeps track of whether the cannon is destroyed

    /**
     * Interval between animation frames: .03 seconds (i.e., about 33 times
     * per second).
     *
     * @return the time interval between frames, in milliseconds.
     */
    public int interval() {
        return 30;
    }

    /**
     * The background color: a light blue.
     *
     * @return the background color onto which we will draw the image.
     */
    public int backgroundColor() {
        // create/return the background color
        return Color.rgb(180, 200, 255);
    }

    /*
    *   Draws the targets to the canvas
     */
    public void drawTargets(Canvas g)
    {
        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);


        x1 = 1500;
        x2 = 700;
        x3 = 1200;

        y1 = 100;
        y2 = 300;
        y3 = 700;

        g.drawCircle(x1, y1, 100, blackPaint);
        g.drawCircle(x2, y2, 50,blackPaint);
        g.drawCircle(x3, y3, 25, blackPaint);

    }


    /**
     * Action to perform on clock tick
     *
     * @param g the graphics object on which to draw
     */
    public void tick(Canvas g) {
        //Draw the targets to shoot at
        drawTargets(g);




        //define the paint to use
        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);

        //if we can fire then proceed
        if(reloadAndFire) {
            count++;

            //calculate the x and y coordinates of the ball
            int x = (int) (shot.getxVelocity() * Math.cos(shot.getTheta()) * count) + startX;
            int y = -(int) (shot.getVelocity() * Math.sin(shot.getTheta()) * count) + (int) (.5 * shot.getGravity() * count * count) + startY;
            //Set the shots x and y positions
            shot.setX(x);
            shot.setY(y);
            //Draw the circles on the canvas
            g.drawCircle(shot.getX(), shot.getY(), 20, redPaint);
        }
        //check to see if the ball is fired straight up, and has hit the cannon
        if(shot.getTheta() == 3.1415*.5 && shot.getY() >1220) {
            //The cannon is hit
            isHit = true;
            shot.setY(0);
            shot.setX(0);
            count = 0;
            reloadAndFire = false;
            isFlying = false;

        }


        if(shot.getY()>1240){
            //restart the time with the balls x value so it will bounce
            count = 0;
            startX = shot.getX();
            //start Y is the height to bounce off of
            startY = 1250;
            shot.setVelocity(shot.getVelocity()/1.2);
            isFlying = true;
        }

        //If the shot is outside of the desired boundaries clear the shot from the screen and mark that we are reloading
        if(shot.getX()> 1800)
        {
            isFlying = false;
            count = 0;
            reloadAndFire = false;
            shot.setY(0);
            shot.setX(0);
            startX = 150;
            startY = 1200;
        }

        //If the ball hits any of the targets, increment score and reset the ball and count
        if (shot.getX()> x1-100 && shot.getX() < x1+100 && shot.getY()>y1-100 && shot.getY() < y1+100) {
            score += 1;
            shot.setY(0);
            shot.setX(0);
            count = 0;
            reloadAndFire = false;
            isFlying = false;

        }
        if (shot.getX()> x2-50 && shot.getX() < x2+50 && shot.getY()>y2-50 && shot.getY() < y2+50) {
            score += 1;
            shot.setY(0);
            shot.setX(0);
            count = 0;
            reloadAndFire = false;
            isFlying = false;
        }
        if (shot.getX()> x3-25 && shot.getX() < x3+25 && shot.getY()>y3-25 && shot.getY() < y3+25) {
            score += 1;
            shot.setY(0);
            shot.setX(0);
            count = 0;
            reloadAndFire = false;
            isFlying = false;
        }

        //Paint the score onto the screen
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        g.drawText("Score = " + score, 200, 50, paint);


    }


/*
* Method allows a person to shoot a ball as long as there isnt one already in flight
 */
    @Override
    public void reloadAndFire(double velocity, double theta, double gravity) {
        if(!isFlying&&!isHit){
            shot.setGravity(gravity);
            shot.setTheta(theta);
            shot.setVelocity(velocity);
            reloadAndFire = true;
        }
    }
    /*
    Reset method clears the ball and moves it back to the beginning
     */
    public void reset(){
        isFlying = false;
        reloadAndFire = false;
        shot.setX(0);
        shot.setY(0);
        count = 0;
        startX = 150;
        startY = 1200;
        isHit = false;
    }

    /**
     * Tells that we never pause.
     *
     * @return indication of whether to pause
     */
    public boolean doPause() {
        return false;
    }

    /**
     * Tells that we never stop the animation.
     *
     * @return indication of whether to quit.
     */
    public boolean doQuit() {
        return false;
    }

    /**
     * reverse the ball's direction when the screen is tapped
     */
    public void onTouch(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            goBackwards = !goBackwards;
        }
    }



}//class TextAnimator
