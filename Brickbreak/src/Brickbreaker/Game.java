import Brickbreaker.Ball;
import Brickbreaker.Sprite;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Game extends Application {

    private final double CANVAS_WIDTH = 800; // size of the window set to the size of the image
    private final double CANVAS_HEIGHT = 600;
    private static double BALL_XVEL = 2;
    private static double BALL_YVEL = 6;
    private int levelTracker, lifeTracker;
    private double t = 0;
    private Pane root = new Pane();
    private Brickbreaker.Sprite player = new Brickbreaker.Sprite(330, 550, 100, 50, "paddle", Color.BLUE);
    private Brickbreaker.Sprite powerUp = new Brickbreaker.Sprite(0, 0, 20, 20, "paddle", Color.BLUE);
    private Timeline timeline;
    private Stage window;
    private Pane introPane = new Pane();
    private Pane overPane = new Pane();
    private Pane winPane = new Pane();
    private Scene introScene, firstlvlScene, overScene, winScene;
    private Ball ball = new Ball();
    private ArrayList<Brickbreaker.Sprite> brickList = new ArrayList<>(); // declare to ArrayList

    private Parent createContent(Pane thePane, Ball theBall, Sprite thePaddle, ArrayList<Sprite> theList) {
        thePane.setPrefSize(600, 800);
        thePane.getChildren().addAll(thePaddle, theBall);
        File file = new File("/Users/michael/Desktop/Fall 2019/CS308/game_mc546/src/Assets/basketball-court-pngrepo-com.png");
        Image image = new Image(file.toURI().toString());
        thePaddle.setFill(new ImagePattern(image));

        levelTracker = 1;
        lifeTracker = 3;

        Bounds bounds = root.getBoundsInParent();
        timeline = new Timeline(new KeyFrame(Duration.millis(20),
                new EventHandler<>() {

                    @Override
                    public void handle(ActionEvent e) {

                        theBall.setCenterX(theBall.getCenterX() + BALL_XVEL);
                        theBall.setCenterY(theBall.getCenterY() + BALL_YVEL);


                        if (theBall.getCenterY() > 600) {
                            lifeTracker--;
                            if (lifeTracker == 0) {
                                window.setScene(overScene);
                            }
                        }

                        if (theBall.getCenterX() < 0 || theBall.getCenterX() > 800) {
                            BALL_XVEL = -BALL_XVEL;
                        } else if (theBall.getCenterY() < 0 || theBall.getCenterY() > 600 || theBall.intersects(thePaddle.getBoundsInParent())) {
                            BALL_YVEL= -BALL_YVEL;
                        }
                        for (Sprite brick : theList) {
                            if (theBall.intersects(brick.getBoundsInParent())) {
                                BALL_YVEL = -BALL_YVEL;
                                theList.remove(brick);
                                thePane.getChildren().remove(brick);
                                if (theList.isEmpty()) {
                                    while (levelTracker < 5) {
                                        levelTracker++;
                                        for (int j = 0; j < levelTracker; j++) {
                                            nextLevel();
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                    } }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        return root;
    }

    private void nextLevel() {
            for (int i = 0; i < 6 ; i++) {
                Brickbreaker.Sprite s = new Brickbreaker.Sprite(90 + i*100, 150, 60, 60, "brick", Color.RED);
                File file = new File("/Users/michael/Desktop/Fall 2019/CS308/game_mc546/src/Assets/basketball-basket-png-39950.png");
                Image image = new Image(file.toURI().toString());
                s.setFill(new ImagePattern(image));
                brickList.add(s);
                root.getChildren().add(s);
            }
    }

    @Override

    public void start(Stage stage) throws Exception {
        window = stage;
        final Image introScreen = new Image("Assets/Basketball Theme Brick Breaker Game.png"); // image to the intro screen of the game
        final ImageView introScreenNode = new ImageView();
        introScreenNode.setImage(introScreen); // set the image of the title/intro screen
        introScreenNode.setFitHeight(CANVAS_HEIGHT);
        introScreenNode.setFitWidth(CANVAS_WIDTH);

        final Image winnerScreen = new Image("Assets/duke_champions.jpg"); // image to the intro screen of the game
        final ImageView winnerScreenNode = new ImageView();
        winnerScreenNode.setImage(winnerScreen);
        winPane.getChildren().addAll(winnerScreenNode);
        winScene = new Scene(winPane);


        final Image background = new Image("Assets/background.jpg"); // image to the intro screen of the game
        final ImageView backgroundNode = new ImageView();
        backgroundNode.setImage(background); // set the image of the title/intro screen
        backgroundNode.setFitHeight(CANVAS_HEIGHT);
        backgroundNode.setFitWidth(CANVAS_WIDTH);
        root.getChildren().addAll(backgroundNode);

        final Image finalScreen = new Image("Assets/end.jpg"); // image to the intro screen of the game
        final ImageView finalScreenNode = new ImageView();
        finalScreenNode.setImage(finalScreen); // set the image of the title/intro screen
        finalScreenNode.setFitHeight(CANVAS_HEIGHT);
        finalScreenNode.setFitWidth(CANVAS_WIDTH);
        overPane.getChildren().addAll(finalScreenNode);

        window.setTitle("Game"); // popup title
        window.getIcons().add(finalScreen); // show the stage

        Button startButton = new Button ("Start Game");
        startButton.setOnAction(e -> window.setScene(firstlvlScene));
        startButton.setTranslateX(360);
        startButton.setTranslateY(550);

        introPane.getChildren().addAll(introScreenNode, startButton);
        introScene = new Scene(introPane, CANVAS_WIDTH, CANVAS_HEIGHT);
        overScene = new Scene(overPane, CANVAS_WIDTH, CANVAS_HEIGHT);


        firstlvlScene = new Scene(createContent(root,ball,player, brickList), CANVAS_WIDTH,CANVAS_HEIGHT);
        firstlvlScene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    player.moveLeft();
                    break;
                case RIGHT:
                    player.moveRight();
                    break;
                case E:
                    window.setScene(overScene);
                case I:
                    window.setScene(introScene);
            }
        });
        nextLevel();
        window.setScene(introScene);
        window.setResizable(false);
        window.show();
    }
    public static void main(String[] args) {
        launch(args);
    }


}
