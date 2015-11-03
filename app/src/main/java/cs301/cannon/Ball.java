package cs301.cannon;

/**
 * Ball Class represents a ball on the canvas. It has velocity, obeys gravity
 * Created by rothschi18 on 11/1/2015.
 *
 * @author  Daniel Rothschilds
 * @version November 1st 2015
 *
 */
public class Ball {
    /*
    * These instance variables describe the rules the ball must obey, very simple class
     */
    private int x;              //The x coordinate of the ball
    private int y;              //The y coordinate of the ball
    private double xVelocity;   //The x Velocity of the ball
    private double velocity;    //The total Velocity of the ball
    private double theta;       //The angle too shoot the ball at
    private double gravity;     //The gravitational constant the ball obeys

    /*
    * Sets the values of the balls variables
     */
    public Ball(int x, int y, double velocity, double theta, double gravity)
    {
        this.x = x;
        this.y = y;
        this.theta = theta;
        this.velocity = velocity;
        this.gravity = gravity;
        xVelocity = Math.cos(this.theta)*velocity;
    }
    /*
    * Setters and Getters for instance variables
     */
    public int getX()
    {
        return x;
    }

    public double getxVelocity(){ return xVelocity;}

    public int getY()
    {
        return y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public double getVelocity() { return velocity; }

    public void setVelocity(double velocity) { this.velocity = velocity; }

    public void setTheta(double theta) { this.theta = theta; }

    public double getTheta() { return theta; }

    public double getGravity() { return gravity; }

    public void setGravity(double gravity) { this.gravity = gravity; }


}
