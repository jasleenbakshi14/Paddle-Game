package com.jasleenbakshi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoreKeeper {
    //private instance variables
    private int currentScore = 0;//each time ball hits the paddle +1 point is added
    private int increaseingScore = 1;
    private String scoreText;
    private double textX =300;
    private double textY = 20;
    private Font scoreFont = Font.font( "Times New Roman", 20 );

    public void currentScore(){
        currentScore++;
    }
    public void resetScore(){
        currentScore = 0;
    }
    public void drawWith(GraphicsContext thePen){
        scoreText = "Score :" + currentScore;
        thePen.setFont(scoreFont);
        thePen.setFill(Color.LAVENDER);
        thePen.setStroke(Color.POWDERBLUE);
        thePen.setLineWidth(2);
        thePen.fillText(scoreText, textX, textY);
        thePen.strokeText(scoreText, textX, textY);
    }

    public int getScore(){
        return currentScore;

    }

}


