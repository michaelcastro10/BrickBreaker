package Brickbreaker;

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;

public class Ball extends Circle {
    private Timeline timeline = new Timeline();


    public Ball() {
        setRadius(15);
        setCenterX(0);
        setCenterY(0);
        relocate(0, 0);
        File file = new File("/Users/michael/Desktop/Fall 2019/CS308/game_mc546/src/Assets/basketball-ball.png");
        Image image = new Image(file.toURI().toString());
        setFill(new ImagePattern(image));
    }
}
