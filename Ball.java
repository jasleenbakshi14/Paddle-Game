package jasleenbakshi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball {
    private double radius = 50; //radius of ball
    private double centerX = 100; //center X
    private double centerY = 100; //center Y
    private double speed = 3; //pixels/timer event
    private double directionX = 1;  //1=>right, -1=>left
    private double directionY = 1; //1=>downward, -1=>upward,
    private Color ballColor = Color.DEEPPINK;

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getRadius() {
        return radius;
    }

    public void backToPositon() {
        centerX = 100;
        centerY = 100;
    }

    public void advance(){
        centerX += speed*directionX;
        centerY += speed*directionY;
    }
    public void setDirectionX(Direction aDirection){
        if(aDirection == RIGHT) directionX = 1;
        if(aDirection== LEFT) directionX = -1;
    }
    public void setDirectionY(Direction aDirection){
        if(aDirection == UP) directionX = 1;
        if(aDirection== DOWN) directionX = -1;
    }
    public void setDirectionY(double aDirection){directionY = aDirection;}
}

