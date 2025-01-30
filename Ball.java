package com.jasleenbakshi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Ball {
    //private instance variables
    private double radius = 50; //radius of ball
    private double centerX = 100; //center X
    private double centerY = 100; //center Y
    private double speed = 3; //pixels/timer event
    private double directionX = 1;  //1=>right, -1=>left
    private int directionY = 1; //1=>downward, -1=>upward,
    private Color ballColor = Color.PINK;

    public double getCenterX(){return centerX;}
    public double getCenterY(){return centerY;}
    public double getRadius(){return radius;}
    //add whatever get methods are required

    public void advance(){
        centerX += speed*directionX;
        centerY += speed*directionY;
    }
//setting up methods in ball class so it works in main
    public void setDirectionX(FXMouseApplication.Direction aDirection){
        if(aDirection == FXMouseApplication.Direction.RIGHT) directionX =1;
        if(aDirection == FXMouseApplication.Direction.LEFT) directionX =-1;
    }
    public void setDirectionY(FXMouseApplication.Direction aDirection){
        if(aDirection == FXMouseApplication.Direction.DOWN) directionY = 1;
        if(aDirection == FXMouseApplication.Direction.UP) directionY = -1;
    }


    //resetting the ball after failure
    public void resetBall(){
        centerX = 100;
        centerY = 100;
        directionX = 1;
        directionY = 1;

    }
//drawing it out
    public void drawWith(GraphicsContext thePen){
        thePen.setFill(ballColor);
        thePen.fillOval(centerX-radius, centerY-radius, 2*radius, 2*radius);
    }
//changing colour each turn, help from : https://www.nutt.net/java-random-color/
    public void changeColor() {
        ballColor = Color.color(Math.random(),Math.random(),Math.random());
    }


}


