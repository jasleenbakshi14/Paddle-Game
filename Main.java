package jasleenbakshi;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
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

public  class Main extends Application{

    private Ball ball = new Ball();
    private Paddle paddle = new Paddle();
    private ScoreKeeper score = new ScoreKeeper();


    private Ball ball2 = new Ball();

    private Color bgColor = Color.BLACK;

    private AnimationTimer timer;
    private Timeline timeline;

    private Canvas canvas;

    private MenuBar menubar = new MenuBar();
    private Menu fileMenu = new Menu("Paddle Game");
    private Menu runMenu = new Menu("Run");
    private ContextMenu contextMenu = new ContextMenu();
    private Menu optionMenu = new Menu("Options");
    private ColorPicker colorPicker = new ColorPicker();
    private ToolBar toolBar = new ToolBar();
    private Label colorSelection = new Label(" " + "Background Color"+ " ");

    private void buildMenus(Stage theStage);
    //Build Run menu items
    MenuItem startMenuItem = new MenuItem("Start Game");
            optionMenu.getItems().addAll(startMenuItem);
            startMenuItem.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            startAnimation();
            repaintCanvas(canvas);
        }
    });
    MenuItem stopMenuItem = new MenuItem("Stop Timer");
            runMenu.getItems().addAll(stopMenuItem);
            stopMenuItem.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            stopAnimation();
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
            alert.setContentText("Ver 1.0 \u00A9 L.D. Nel 2015");
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
