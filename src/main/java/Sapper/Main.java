package Sapper;

import Sapper.Controler.GameWindow;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX Sapper.App
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        GameWindow gameWindow = new GameWindow(stage);
        gameWindow.build();
    }

    public static void main(String[] args) {
        launch();
    }
}

