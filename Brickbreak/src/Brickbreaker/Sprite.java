package Brickbreaker;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {
    public final String type;

    public Sprite(int x, int y, int w, int h, String type, Color color) {
        super(w, h, color);

        this.type = type;
        setTranslateX(x);
        setTranslateY(y);

    }

    public void moveLeft() {
        setTranslateX(getTranslateX() - 55);
    }
    public void moveRight() {
        setTranslateX(getTranslateX() + 55);
    }
    public void moveUp() {
        setTranslateY(getTranslateY() - 5);
    }

    public void moveDown() {
        setTranslateY(getTranslateY() + 5);
    }



}