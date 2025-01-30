package com.jasleenbakshi;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Paddle {
    private double width = 100; //radius of ball
    private double height = 25; //radius of ball
    private double topLeftX = 200;
    private double topLeftY = 450;

    public double getTopLeftX(){return topLeftX;}
    public double getTopLeftY(){return topLeftY;}
    public double getWidth(){return width;}
    public double getHeight(){return height;}
    //add whatever get methods are required

    public void moveRight(double increment){topLeftX += increment;}
    public void moveLeft(double increment){topLeftX -= increment;}
    public void moveUp(double increment){topLeftY -= increment;}
    public void moveDown(double increment){topLeftY += increment;}


    public void resetPaddle(){
        topLeftX = 200;
        topLeftY = 450;
    }
    public void drawWith(GraphicsContext thePen) {
        Paint paddleColor = null;
        thePen.setFill(paddleColor);
        thePen.fillRect(topLeftX, topLeftY, width, height);
    }

    public void changeColor() {
        Color paddleColor = Color.color(Math.random(), Math.random(), Math.random());
    }

}
