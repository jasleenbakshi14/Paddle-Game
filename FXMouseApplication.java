package com.jasleenbakshi;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class FXMouseApplication extends Application {

    //Direction
    public enum Direction {LEFT, RIGHT, UP, DOWN}

    //private instance variables
    private final Ball ball = new Ball();
    private final Paddle paddle = new Paddle();
    private final ScoreKeeper scoreKeeper = new ScoreKeeper();

    private AnimationTimer timer; //for animating frame based motion
    boolean animationIsRunning = false;

    //GUI elements
    Canvas canvas; //drawing canvas

    //GUI menus
    private MenuBar menubar = new MenuBar();
    private Menu fileMenu = new Menu("Options");
    private Menu runMenu = new Menu("Start/Stop");
    ContextMenu contextMenu = new ContextMenu();

    private void buildMenus(Stage theStage){
        //build the menus for the menu bar

        //Build Run menu items
        MenuItem startMenuItem = new MenuItem("Start Game");
        runMenu.getItems().addAll(startMenuItem);
        startMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                startAnimation();
                repaintCanvas(canvas);
            }
        });
        MenuItem stopMenuItem = new MenuItem("Stop Game");
        runMenu.getItems().addAll(stopMenuItem);
        stopMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                stopAnimation();
                repaintCanvas(canvas);
            }
        });

        MenuItem resetMenuItem = new MenuItem("Reset Game");
        fileMenu.getItems().addAll(resetMenuItem);
        resetMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                scoreKeeper.resetScore();
                ball.resetBall();
                paddle.resetPaddle();
                repaintCanvas(canvas);
                                       }

        });
                //Build File menu items
                MenuItem aboutMenuItem = new MenuItem("About This App");
        fileMenu.getItems().addAll(aboutMenuItem);
        aboutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Ver 1.0 \u00A9 Jasleen 2021");
                alert.showAndWait();
            }
        });


        //Build Popup context menu items
        MenuItem pauseContextMenuItem = new MenuItem("Pause Animation");
        contextMenu.getItems().addAll(pauseContextMenuItem);
        pauseContextMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                stopAnimation();
                repaintCanvas(canvas);
            }
        });

        MenuItem resumeContextMenuItem = new MenuItem("Resume Animation");
        contextMenu.getItems().addAll(resumeContextMenuItem);
        resumeContextMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                startAnimation();
                repaintCanvas(canvas);
            }
        });





    }

    //required by any Application subclass
    @Override
    public void start(Stage mainStage){

        //Here we do most of the initialization for the application
        //This method is called automatically as a result of
        // launching the application

        mainStage.setTitle("Java Pong Game"); //window title

        VBox root = new VBox(); //root node of scene graph
        Scene theScene = new Scene(root); //our GUI scene
        mainStage.setScene(theScene); //add scene to our app's stage

        //build application menus
        //add menus to menu bar object
        menubar.getMenus().add(fileMenu);
        menubar.getMenus().add(runMenu);
        //add menu bar object to application scene root
        root.getChildren().add(menubar); //add menubar to GUI
        buildMenus(mainStage); //add menu items to menus


        canvas = new Canvas(500,600); //GUI element we will draw on
        root.getChildren().add(canvas);

        //add mouse event handler (for popup menu)
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
                    handleMousePressedEvent(e);
                }
        );
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent e) -> {
                    handleMouseReleasedEvent(e);
                }
        );
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent e) -> {
                    handleMouseDraggedEvent(e);
                }
        );

        canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                //listen for arrow keys using key press event
                //arrow keys don't show up in KeyTyped events
                String text = "";
                double moveIncrement = 20;
                if (ke.getCode() == KeyCode.RIGHT) {
                    text += "RIGHT";
                    if(paddle.getTopLeftX()+paddle.getWidth()+moveIncrement< canvas.getWidth())
                        paddle.moveRight(moveIncrement);
                }
                else if (ke.getCode() == KeyCode.LEFT) {
                    text += "LEFT";
                    if(paddle.getTopLeftX()> moveIncrement)
                        paddle.moveLeft(moveIncrement);
                }
                else if (ke.getCode() == KeyCode.UP) {
                    text += "UP";
                    if(paddle.getTopLeftY()> moveIncrement)
                        paddle.moveUp(moveIncrement);
                }
                else if (ke.getCode() == KeyCode.DOWN) {
                    text += "DOWN";
                    if (paddle.getTopLeftY()+paddle.getHeight()+moveIncrement< canvas.getHeight())
                        paddle.moveDown(moveIncrement);
                }

                System.out.println("key press: " + text);
                ke.consume(); //don't let keyboard event propogate
            }
        });




        timer = new AnimationTimer() { //e.g. of anonymous inner subclass
            @Override
            public void handle(long nowInNanoSeconds) {
                //this method will be called about 60 times per second
                //which is default behaviour of the AnimationTimer class


                //check for collision of greeting with canvas bounds
                //and change direction if necessary

                //build a text object to represent the actual width of the greeting string


                //advance the ball
                ball.advance();
                //check if ball should bounce off canvas sides
                if(ball.getCenterX() + ball.getRadius() >= canvas.getWidth()) ball.setDirectionX(Direction.LEFT);
                if(ball.getCenterX() - ball.getRadius() < 0) ball.setDirectionX(Direction.RIGHT);

                if(ball.getCenterY() - ball.getRadius() < 0) ball.setDirectionY(Direction.DOWN);
                if(ball.getCenterY()+ ball.getRadius() >= canvas.getHeight()){
                    ball.resetBall();
                    scoreKeeper.resetScore();
                    ball.changeColor();
                    paddle.changeColor();
                }

                repaintCanvas(canvas); //refesh our canvas rendering

                // making the ball bounce on the paddle code taken from: https://stackoverflow.com/questions/32729013/java-ball-bounce-off-paddle-in-realistic-way
                if(ball.getCenterX() >= paddle.getTopLeftX() && ball.getCenterX() <= paddle.getTopLeftX() + paddle.getWidth()
                        && ball.getCenterY() + ball.getRadius() > paddle.getTopLeftY()){
                    scoreKeeper.currentScore();
                    ball.setDirectionY(Direction.UP);
                }
                repaintCanvas(canvas);


            }
        };


        startAnimation(); //start the animation timer

        mainStage.show(); //show the application window
        repaintCanvas(canvas); //do initial repaint



    }


    private void startAnimation(){
        timer.start();
    }
    private void stopAnimation(){
        timer.stop();
    }

    private void handleMousePressedEvent(MouseEvent e){
        //mouse handler for canvas
        canvas.requestFocus(); //set canvas to receive keyboard events

        //Windows uses mouse release popup trigger
        //Mac uses mouse press popup trigger
        if(e.isPopupTrigger())
            contextMenu.show(canvas, e.getScreenX(), e.getScreenY());
        else {
            contextMenu.hide(); //in case it was left open

            //print out mouse locations for inspection and debugging
            System.out.println("mouse scene: " +
                    e.getSceneX() +
                    "," +
                    e.getSceneY()
            );
            System.out.println("mouse screen: " +
                    e.getScreenX() +
                    "," +
                    e.getScreenY()
            );
            System.out.println("mouse get: " +
                    e.getX() +
                    "," +
                    e.getY()
            );
        }
        repaintCanvas(canvas); //update the GUI canvas
    }

    private void handleMouseReleasedEvent(MouseEvent e){
        //Windows uses mouse release popup trigger
        //Mac uses mouse press popup trigger
        if(e.isPopupTrigger())
            contextMenu.show(canvas, e.getScreenX(), e.getScreenY());

        repaintCanvas(canvas);
    }
    private void handleMouseDraggedEvent(MouseEvent e){
        //nothing to do here
        repaintCanvas(canvas);

    }



    private void repaintCanvas(Canvas aCanvas){
        //repaint the contents of our GUI canvas

        //obtain the graphics context for drawing on the canvas
        GraphicsContext thePen = aCanvas.getGraphicsContext2D();

        //clear the canvas
        double canvasWidth = aCanvas.getWidth();
        double canvasHeight = aCanvas.getHeight();
        thePen.clearRect(0, 0, canvasWidth, canvasHeight);

        //set graphics context for drawing on canvas
        thePen.setFill(Color.RED);
        thePen.setStroke(Color.BLACK);
        thePen.setLineWidth(2);

        //draw the ball
        ball.drawWith(thePen);

        //draw the Paddle
        paddle.drawWith(thePen);
        //draw the scorekeeper
        scoreKeeper.drawWith(thePen);

        canvas.requestFocus(); //request keyboard focus

    }

    public static void main(String[] args) {
        //entry point for javaFX application
        System.out.println("starting main application");
        launch(args); //will cause application's to start and
        // run it's start() method
        System.out.println("main application is finished");
    }


}
